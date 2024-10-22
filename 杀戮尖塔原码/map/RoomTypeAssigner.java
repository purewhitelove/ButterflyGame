// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RoomTypeAssigner.java

package com.megacrit.cardcrawl.map;

import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.*;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.map:
//            MapRoomNode, MapEdge

public class RoomTypeAssigner
{

    public RoomTypeAssigner()
    {
    }

    public static void assignRowAsRoomType(ArrayList row, Class c)
    {
        Iterator iterator = row.iterator();
_L2:
        MapRoomNode n;
        if(!iterator.hasNext())
            break; /* Loop/switch isn't completed */
        n = (MapRoomNode)iterator.next();
        if(n.getRoom() != null)
            continue; /* Loop/switch isn't completed */
        try
        {
            n.setRoom((AbstractRoom)c.newInstance());
        }
        catch(ReflectiveOperationException e)
        {
            e.printStackTrace();
        }
        if(true) goto _L2; else goto _L1
_L1:
    }

    private static int getConnectedNonAssignedNodeCount(List map)
    {
        int count = 0;
        for(Iterator iterator = map.iterator(); iterator.hasNext();)
        {
            ArrayList row = (ArrayList)iterator.next();
            Iterator iterator1 = row.iterator();
            while(iterator1.hasNext()) 
            {
                MapRoomNode node = (MapRoomNode)iterator1.next();
                if(node.hasEdges() && node.getRoom() == null)
                    count++;
            }
        }

        return count;
    }

    private static ArrayList getSiblings(List map, ArrayList parents, MapRoomNode n)
    {
        ArrayList siblings = new ArrayList();
        for(Iterator iterator = parents.iterator(); iterator.hasNext();)
        {
            MapRoomNode parent = (MapRoomNode)iterator.next();
            Iterator iterator1 = parent.getEdges().iterator();
            while(iterator1.hasNext()) 
            {
                MapEdge parentEdge = (MapEdge)iterator1.next();
                MapRoomNode siblingNode = (MapRoomNode)((ArrayList)map.get(parentEdge.dstY)).get(parentEdge.dstX);
                if(!siblingNode.equals(n))
                    siblings.add(siblingNode);
            }
        }

        return siblings;
    }

    private static boolean ruleSiblingMatches(ArrayList siblings, AbstractRoom roomToBeSet)
    {
        List applicableRooms = Arrays.asList(new Class[] {
            com/megacrit/cardcrawl/rooms/RestRoom, com/megacrit/cardcrawl/rooms/MonsterRoom, com/megacrit/cardcrawl/rooms/EventRoom, com/megacrit/cardcrawl/rooms/MonsterRoomElite, com/megacrit/cardcrawl/rooms/ShopRoom
        });
        for(Iterator iterator = siblings.iterator(); iterator.hasNext();)
        {
            MapRoomNode siblingNode = (MapRoomNode)iterator.next();
            if(siblingNode.getRoom() != null && applicableRooms.contains(roomToBeSet.getClass()) && roomToBeSet.getClass().equals(siblingNode.getRoom().getClass()))
                return true;
        }

        return false;
    }

    private static boolean ruleParentMatches(ArrayList parents, AbstractRoom roomToBeSet)
    {
        List applicableRooms = Arrays.asList(new Class[] {
            com/megacrit/cardcrawl/rooms/RestRoom, com/megacrit/cardcrawl/rooms/TreasureRoom, com/megacrit/cardcrawl/rooms/ShopRoom, com/megacrit/cardcrawl/rooms/MonsterRoomElite
        });
        for(Iterator iterator = parents.iterator(); iterator.hasNext();)
        {
            MapRoomNode parentNode = (MapRoomNode)iterator.next();
            AbstractRoom parentRoom = parentNode.getRoom();
            if(parentRoom != null && applicableRooms.contains(roomToBeSet.getClass()) && roomToBeSet.getClass().equals(parentRoom.getClass()))
                return true;
        }

        return false;
    }

    private static boolean ruleAssignableToRow(MapRoomNode n, AbstractRoom roomToBeSet)
    {
        List applicableRooms = Arrays.asList(new Class[] {
            com/megacrit/cardcrawl/rooms/RestRoom, com/megacrit/cardcrawl/rooms/MonsterRoomElite
        });
        List applicableRooms2 = Collections.singletonList(com/megacrit/cardcrawl/rooms/RestRoom);
        if(n.y <= 4 && applicableRooms.contains(roomToBeSet.getClass()))
            return false;
        return n.y < 13 || !applicableRooms2.contains(roomToBeSet.getClass());
    }

    private static AbstractRoom getNextRoomTypeAccordingToRules(ArrayList map, MapRoomNode n, ArrayList roomList)
    {
        ArrayList parents = n.getParents();
        ArrayList siblings = getSiblings(map, parents, n);
        for(Iterator iterator = roomList.iterator(); iterator.hasNext();)
        {
            AbstractRoom roomToBeSet = (AbstractRoom)iterator.next();
            if(ruleAssignableToRow(n, roomToBeSet))
            {
                if(!ruleParentMatches(parents, roomToBeSet) && !ruleSiblingMatches(siblings, roomToBeSet))
                    return roomToBeSet;
                if(n.y == 0)
                    return roomToBeSet;
            }
        }

        return null;
    }

    private static void lastMinuteNodeChecker(ArrayList map, MapRoomNode n)
    {
        for(Iterator iterator = map.iterator(); iterator.hasNext();)
        {
            ArrayList row = (ArrayList)iterator.next();
            Iterator iterator1 = row.iterator();
            while(iterator1.hasNext()) 
            {
                MapRoomNode node = (MapRoomNode)iterator1.next();
                if(node != null && node.hasEdges() && node.getRoom() == null)
                {
                    logger.info((new StringBuilder()).append("INFO: Node=").append(node.toString()).append(" was null. Changed to a MonsterRoom.").toString());
                    node.setRoom(new MonsterRoom());
                }
            }
        }

    }

    private static void assignRoomsToNodes(ArrayList map, ArrayList roomList)
    {
        for(Iterator iterator = map.iterator(); iterator.hasNext();)
        {
            ArrayList row = (ArrayList)iterator.next();
            Iterator iterator1 = row.iterator();
            while(iterator1.hasNext()) 
            {
                MapRoomNode node = (MapRoomNode)iterator1.next();
                if(node != null && node.hasEdges() && node.getRoom() == null)
                {
                    AbstractRoom roomToBeSet = getNextRoomTypeAccordingToRules(map, node, roomList);
                    if(roomToBeSet != null)
                        node.setRoom((AbstractRoom)roomList.remove(roomList.indexOf(roomToBeSet)));
                }
            }
        }

    }

    public static ArrayList distributeRoomsAcrossMap(Random rng, ArrayList map, ArrayList roomList)
    {
        int nodeCount;
        for(nodeCount = getConnectedNonAssignedNodeCount(map); roomList.size() < nodeCount; roomList.add(new MonsterRoom()));
        if(roomList.size() > nodeCount)
            logger.info("WARNING: the roomList is larger than the number of connected nodes. Not all desired roomTypes will be used.");
        Collections.shuffle(roomList, rng.random);
        assignRoomsToNodes(map, roomList);
        logger.info("#### Unassigned Rooms:");
        AbstractRoom r;
        for(Iterator iterator = roomList.iterator(); iterator.hasNext(); logger.info(r.getClass()))
            r = (AbstractRoom)iterator.next();

        lastMinuteNodeChecker(map, null);
        return map;
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/map/RoomTypeAssigner.getName());

}
