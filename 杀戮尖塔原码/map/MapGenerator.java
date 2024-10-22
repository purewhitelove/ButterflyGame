// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MapGenerator.java

package com.megacrit.cardcrawl.map;

import com.megacrit.cardcrawl.daily.mods.CertainFuture;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.random.Random;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.map:
//            MapRoomNode, MapEdge, EdgeComparator

public class MapGenerator
{

    public MapGenerator()
    {
    }

    public static ArrayList generateDungeon(int height, int width, int pathDensity, Random rng)
    {
        ArrayList map = createNodes(height, width);
        if(ModHelper.isModEnabled("Uncertain Future"))
            map = createPaths(map, 1, rng);
        else
            map = createPaths(map, pathDensity, rng);
        map = filterRedundantEdgesFromRow(map);
        return map;
    }

    private static ArrayList filterRedundantEdgesFromRow(ArrayList map)
    {
        ArrayList existingEdges = new ArrayList();
        ArrayList deleteList = new ArrayList();
        Iterator iterator = ((ArrayList)map.get(0)).iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            MapRoomNode node = (MapRoomNode)iterator.next();
            if(node.hasEdges())
            {
                MapEdge edge;
label0:
                for(Iterator iterator1 = node.getEdges().iterator(); iterator1.hasNext(); existingEdges.add(edge))
                {
                    edge = (MapEdge)iterator1.next();
                    Iterator iterator3 = existingEdges.iterator();
                    do
                    {
                        if(!iterator3.hasNext())
                            continue label0;
                        MapEdge prevEdge = (MapEdge)iterator3.next();
                        if(edge.dstX == prevEdge.dstX && edge.dstY == prevEdge.dstY)
                            deleteList.add(edge);
                    } while(true);
                }

                MapEdge edge;
                for(Iterator iterator2 = deleteList.iterator(); iterator2.hasNext(); node.delEdge(edge))
                    edge = (MapEdge)iterator2.next();

                deleteList.clear();
            }
        } while(true);
        return map;
    }

    private static ArrayList createNodes(int height, int width)
    {
        ArrayList nodes = new ArrayList();
        for(int y = 0; y < height; y++)
        {
            ArrayList row = new ArrayList();
            for(int x = 0; x < width; x++)
                row.add(new MapRoomNode(x, y));

            nodes.add(row);
        }

        return nodes;
    }

    private static ArrayList createPaths(ArrayList nodes, int pathDensity, Random rng)
    {
        int first_row = 0;
        int row_size = ((ArrayList)nodes.get(first_row)).size() - 1;
        int firstStartingNode = -1;
        for(int i = 0; i < pathDensity; i++)
        {
            int startingNode = randRange(rng, 0, row_size);
            if(i == 0)
                firstStartingNode = startingNode;
            for(; startingNode == firstStartingNode && i == 1; startingNode = randRange(rng, 0, row_size));
            _createPaths(nodes, new MapEdge(startingNode, -1, startingNode, 0), rng);
        }

        return nodes;
    }

    private static MapEdge getMaxEdge(ArrayList edges)
    {
        Collections.sort(edges, new EdgeComparator());
        if(!$assertionsDisabled && edges.isEmpty())
            throw new AssertionError("Somehow the edges are empty. This shouldn't happen.");
        else
            return (MapEdge)edges.get(edges.size() - 1);
    }

    private static MapEdge getMinEdge(ArrayList edges)
    {
        Collections.sort(edges, new EdgeComparator());
        if(!$assertionsDisabled && edges.isEmpty())
            throw new AssertionError("Somehow the edges are empty. This shouldn't happen.");
        else
            return (MapEdge)edges.get(0);
    }

    private static MapRoomNode getNodeWithMaxX(ArrayList nodes)
    {
        if(!$assertionsDisabled && nodes.isEmpty())
            throw new AssertionError("The nodes are empty, this shouldn't happen.");
        MapRoomNode max = (MapRoomNode)nodes.get(0);
        Iterator iterator = nodes.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            MapRoomNode node = (MapRoomNode)iterator.next();
            if(node.x > max.x)
                max = node;
        } while(true);
        return max;
    }

    private static MapRoomNode getNodeWithMinX(ArrayList nodes)
    {
        if(!$assertionsDisabled && nodes.isEmpty())
            throw new AssertionError("The nodes are empty, this shouldn't happen.");
        MapRoomNode min = (MapRoomNode)nodes.get(0);
        Iterator iterator = nodes.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            MapRoomNode node = (MapRoomNode)iterator.next();
            if(node.x < min.x)
                min = node;
        } while(true);
        return min;
    }

    private static MapRoomNode getCommonAncestor(MapRoomNode node1, MapRoomNode node2, int max_depth)
    {
        if(!$assertionsDisabled && node1.y != node2.y)
            throw new AssertionError();
        if(!$assertionsDisabled && node1 == node2)
            throw new AssertionError();
        MapRoomNode l_node;
        MapRoomNode r_node;
        if(node1.x < node2.y)
        {
            l_node = node1;
            r_node = node2;
        } else
        {
            l_node = node2;
            r_node = node1;
        }
        for(int current_y = node1.y; current_y >= 0 && current_y >= node1.y - max_depth; current_y--)
        {
            if(l_node.getParents().isEmpty() || r_node.getParents().isEmpty())
                return null;
            l_node = getNodeWithMaxX(l_node.getParents());
            r_node = getNodeWithMinX(r_node.getParents());
            if(l_node == r_node)
                return l_node;
        }

        return null;
    }

    private static ArrayList _createPaths(ArrayList nodes, MapEdge edge, Random rng)
    {
        MapRoomNode currentNode = getNode(edge.dstX, edge.dstY, nodes);
        if(edge.dstY + 1 >= nodes.size())
        {
            MapEdge newEdge = new MapEdge(edge.dstX, edge.dstY, currentNode.offsetX, currentNode.offsetY, 3, edge.dstY + 2, 0.0F, 0.0F, true);
            currentNode.addEdge(newEdge);
            Collections.sort(currentNode.getEdges(), new EdgeComparator());
            return nodes;
        }
        int row_width = ((ArrayList)nodes.get(edge.dstY)).size();
        int row_end_node = row_width - 1;
        int min;
        int max;
        if(edge.dstX == 0)
        {
            min = 0;
            max = 1;
        } else
        if(edge.dstX == row_end_node)
        {
            min = -1;
            max = 0;
        } else
        {
            min = -1;
            max = 1;
        }
        int newEdgeX = edge.dstX + randRange(rng, min, max);
        int newEdgeY = edge.dstY + 1;
        MapRoomNode targetNodeCandidate = getNode(newEdgeX, newEdgeY, nodes);
        int min_ancestor_gap = 3;
        int max_ancestor_gap = 5;
        ArrayList parents = targetNodeCandidate.getParents();
        if(!parents.isEmpty())
        {
            Iterator iterator = parents.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                MapRoomNode parent = (MapRoomNode)iterator.next();
                if(parent != currentNode)
                {
                    MapRoomNode ancestor = getCommonAncestor(parent, currentNode, max_ancestor_gap);
                    if(ancestor != null)
                    {
                        int ancestor_gap = newEdgeY - ancestor.y;
                        if(ancestor_gap < min_ancestor_gap)
                        {
                            if(targetNodeCandidate.x > currentNode.x)
                            {
                                newEdgeX = edge.dstX + randRange(rng, -1, 0);
                                if(newEdgeX < 0)
                                    newEdgeX = edge.dstX;
                            } else
                            if(targetNodeCandidate.x == currentNode.x)
                            {
                                newEdgeX = edge.dstX + randRange(rng, -1, 1);
                                if(newEdgeX > row_end_node)
                                    newEdgeX = edge.dstX - 1;
                                else
                                if(newEdgeX < 0)
                                    newEdgeX = edge.dstX + 1;
                            } else
                            {
                                newEdgeX = edge.dstX + randRange(rng, 0, 1);
                                if(newEdgeX > row_end_node)
                                    newEdgeX = edge.dstX;
                            }
                            targetNodeCandidate = getNode(newEdgeX, newEdgeY, nodes);
                        } else
                        if(ancestor_gap < max_ancestor_gap);
                    }
                }
            } while(true);
        }
        if(edge.dstX != 0)
        {
            MapRoomNode left_node = (MapRoomNode)((ArrayList)nodes.get(edge.dstY)).get(edge.dstX - 1);
            if(left_node.hasEdges())
            {
                MapEdge right_edge_of_left_node = getMaxEdge(left_node.getEdges());
                if(right_edge_of_left_node.dstX > newEdgeX)
                    newEdgeX = right_edge_of_left_node.dstX;
            }
        }
        if(edge.dstX < row_end_node)
        {
            MapRoomNode right_node = (MapRoomNode)((ArrayList)nodes.get(edge.dstY)).get(edge.dstX + 1);
            if(right_node.hasEdges())
            {
                MapEdge left_edge_of_right_node = getMinEdge(right_node.getEdges());
                if(left_edge_of_right_node.dstX < newEdgeX)
                    newEdgeX = left_edge_of_right_node.dstX;
            }
        }
        targetNodeCandidate = getNode(newEdgeX, newEdgeY, nodes);
        MapEdge newEdge = new MapEdge(edge.dstX, edge.dstY, currentNode.offsetX, currentNode.offsetY, newEdgeX, newEdgeY, targetNodeCandidate.offsetX, targetNodeCandidate.offsetY, false);
        currentNode.addEdge(newEdge);
        Collections.sort(currentNode.getEdges(), new EdgeComparator());
        targetNodeCandidate.addParent(currentNode);
        return _createPaths(nodes, newEdge, rng);
    }

    private static MapRoomNode getNode(int x, int y, ArrayList nodes)
    {
        return (MapRoomNode)((ArrayList)nodes.get(y)).get(x);
    }

    private static String paddingGenerator(int length)
    {
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < length; i++)
            str.append(" ");

        return str.toString();
    }

    public static String toString(ArrayList nodes)
    {
        return toString(nodes, Boolean.valueOf(false));
    }

    public static String toString(ArrayList nodes, Boolean showRoomSymbols)
    {
        StringBuilder str = new StringBuilder();
        int row_num = nodes.size() - 1;
        int left_padding_size = 5;
        do
        {
            if(row_num < 0)
                break;
            str.append("\n ").append(paddingGenerator(left_padding_size));
            String left;
            String mid;
            String right;
label0:
            for(Iterator iterator = ((ArrayList)nodes.get(row_num)).iterator(); iterator.hasNext(); str.append(left).append(mid).append(right))
            {
                MapRoomNode node = (MapRoomNode)iterator.next();
                left = mid = right = " ";
                Iterator iterator3 = node.getEdges().iterator();
                do
                {
                    if(!iterator3.hasNext())
                        continue label0;
                    MapEdge edge = (MapEdge)iterator3.next();
                    if(edge.dstX < node.x)
                        left = "\\";
                    if(edge.dstX == node.x)
                        mid = "|";
                    if(edge.dstX > node.x)
                        right = "/";
                } while(true);
            }

            str.append("\n").append(row_num).append(" ");
            str.append(paddingGenerator(left_padding_size - String.valueOf(row_num).length()));
            String node_symbol;
label1:
            for(Iterator iterator1 = ((ArrayList)nodes.get(row_num)).iterator(); iterator1.hasNext(); str.append(" ").append(node_symbol).append(" "))
            {
                MapRoomNode node = (MapRoomNode)iterator1.next();
                node_symbol = " ";
                if(row_num == nodes.size() - 1)
                {
                    Iterator iterator2 = ((ArrayList)nodes.get(row_num - 1)).iterator();
                    do
                    {
                        if(!iterator2.hasNext())
                            continue label1;
                        MapRoomNode lower_node = (MapRoomNode)iterator2.next();
                        Iterator iterator4 = lower_node.getEdges().iterator();
                        while(iterator4.hasNext()) 
                        {
                            MapEdge edge = (MapEdge)iterator4.next();
                            if(edge.dstX == node.x)
                                node_symbol = node.getRoomSymbol(showRoomSymbols);
                        }
                    } while(true);
                }
                if(node.hasEdges())
                    node_symbol = node.getRoomSymbol(showRoomSymbols);
            }

            row_num--;
        } while(true);
        return str.toString();
    }

    private static int randRange(Random rng, int min, int max)
    {
        if(rng == null)
        {
            logger.info("RNG WAS NULL, REPORT IMMEDIATELY");
            rng = new Random(Long.valueOf(1L));
        }
        return rng.random(max - min) + min;
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/map/MapGenerator.getName());
    static final boolean $assertionsDisabled = !com/megacrit/cardcrawl/map/MapGenerator.desiredAssertionStatus();

}
