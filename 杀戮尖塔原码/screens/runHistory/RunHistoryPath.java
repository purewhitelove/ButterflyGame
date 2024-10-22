// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RunHistoryPath.java

package com.megacrit.cardcrawl.screens.runHistory;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.GameCursor;
import com.megacrit.cardcrawl.screens.stats.*;
import java.util.*;

// Referenced classes of package com.megacrit.cardcrawl.screens.runHistory:
//            RunPathElement

public class RunHistoryPath
{

    public RunHistoryPath()
    {
        rows = 0;
        pathElements = new ArrayList();
    }

    public void setRunData(RunData newData)
    {
        runData = newData;
        List labels = new ArrayList();
        int choiceIndex = 0;
        int outcomeIndex = 0;
        List choices = runData.path_taken;
        List outcomes = runData.path_per_floor;
        if(choices == null)
            choices = new LinkedList();
        if(outcomes == null)
            outcomes = new LinkedList();
        int bossTreasureCount = 0;
        while(choiceIndex < choices.size() || outcomeIndex < outcomes.size()) 
        {
            String choice = choiceIndex >= choices.size() ? "_" : (String)choices.get(choiceIndex);
            String outcome = outcomeIndex >= outcomes.size() ? "_" : (String)outcomes.get(outcomeIndex);
            if(outcome == null)
            {
                outcomeIndex++;
                if(bossTreasureCount < 2)
                {
                    labels.add("T!");
                    bossTreasureCount++;
                } else
                if(bossTreasureCount == 2)
                {
                    labels.add("<3");
                    bossTreasureCount++;
                } else
                {
                    labels.add("null");
                }
            } else
            {
                if(choice.equals("?") && !outcome.equals("?"))
                    labels.add((new StringBuilder()).append("?(").append(outcome).append(")").toString());
                else
                    labels.add(choice);
                choiceIndex++;
                outcomeIndex++;
            }
        }
        HashMap battlesByFloor = new HashMap();
        BattleStats battle;
        for(Iterator iterator = runData.damage_taken.iterator(); iterator.hasNext(); battlesByFloor.put(Integer.valueOf(battle.floor), battle))
            battle = (BattleStats)iterator.next();

        HashMap eventsByFloor = new HashMap();
        EventStats event;
        for(Iterator iterator1 = runData.event_choices.iterator(); iterator1.hasNext(); eventsByFloor.put(Integer.valueOf(event.floor), event))
            event = (EventStats)iterator1.next();

        HashMap cardsByFloor = new HashMap();
        CardChoiceStats choice;
        for(Iterator iterator2 = runData.card_choices.iterator(); iterator2.hasNext(); cardsByFloor.put(Integer.valueOf(choice.floor), choice))
            choice = (CardChoiceStats)iterator2.next();

        HashMap relicsByFloor = new HashMap();
        if(runData.relics_obtained != null)
        {
            ObtainStats relicData;
            for(Iterator iterator3 = runData.relics_obtained.iterator(); iterator3.hasNext(); ((List)relicsByFloor.get(Integer.valueOf(relicData.floor))).add(relicData.key))
            {
                relicData = (ObtainStats)iterator3.next();
                if(!relicsByFloor.containsKey(Integer.valueOf(relicData.floor)))
                    relicsByFloor.put(Integer.valueOf(relicData.floor), new ArrayList());
            }

        }
        HashMap potionsByFloor = new HashMap();
        if(runData.potions_obtained != null)
        {
            ObtainStats potionData;
            for(Iterator iterator4 = runData.potions_obtained.iterator(); iterator4.hasNext(); ((List)potionsByFloor.get(Integer.valueOf(potionData.floor))).add(potionData.key))
            {
                potionData = (ObtainStats)iterator4.next();
                if(!potionsByFloor.containsKey(Integer.valueOf(potionData.floor)))
                    potionsByFloor.put(Integer.valueOf(potionData.floor), new ArrayList());
            }

        }
        HashMap campfireChoicesByFloor = new HashMap();
        if(runData.campfire_choices != null)
        {
            CampfireChoice choice;
            for(Iterator iterator5 = runData.campfire_choices.iterator(); iterator5.hasNext(); campfireChoicesByFloor.put(Integer.valueOf(choice.floor), choice))
                choice = (CampfireChoice)iterator5.next();

        }
        HashMap purchasesByFloor = new HashMap();
        if(runData.items_purchased != null && runData.item_purchase_floors != null && runData.items_purchased.size() == runData.item_purchase_floors.size())
        {
            for(int i = 0; i < runData.items_purchased.size(); i++)
            {
                int floor = ((Integer)runData.item_purchase_floors.get(i)).intValue();
                String key = (String)runData.items_purchased.get(i);
                if(!purchasesByFloor.containsKey(Integer.valueOf(floor)))
                    purchasesByFloor.put(Integer.valueOf(floor), new ArrayList());
                ((ArrayList)purchasesByFloor.get(Integer.valueOf(floor))).add(key);
            }

        }
        HashMap purgesByFloor = new HashMap();
        if(runData.items_purged != null && runData.items_purged_floors != null && runData.items_purged.size() == runData.items_purged_floors.size())
        {
            for(int i = 0; i < runData.items_purged.size(); i++)
            {
                int floor = ((Integer)runData.items_purged_floors.get(i)).intValue();
                String key = (String)runData.items_purged.get(i);
                if(!purgesByFloor.containsKey(Integer.valueOf(floor)))
                    purgesByFloor.put(Integer.valueOf(floor), new ArrayList());
                ((ArrayList)purgesByFloor.get(Integer.valueOf(floor))).add(key);
            }

        }
        pathElements.clear();
        rows = 0;
        int bossRelicChoiceIndex = 0;
        int tmpColumn = 0;
        for(int i = 0; i < labels.size(); i++)
        {
            String label = (String)labels.get(i);
            int floor = i + 1;
            RunPathElement element = new RunPathElement(label, floor);
            if(runData.current_hp_per_floor != null && runData.max_hp_per_floor != null && i < runData.current_hp_per_floor.size() && i < runData.max_hp_per_floor.size())
                element.addHpData((Integer)runData.current_hp_per_floor.get(i), (Integer)runData.max_hp_per_floor.get(i));
            if(runData.gold_per_floor != null && i < runData.gold_per_floor.size())
                element.addGoldData((Integer)runData.gold_per_floor.get(i));
            if(battlesByFloor.containsKey(Integer.valueOf(floor)))
                element.addBattleData((BattleStats)battlesByFloor.get(Integer.valueOf(floor)));
            if(eventsByFloor.containsKey(Integer.valueOf(floor)))
                element.addEventData((EventStats)eventsByFloor.get(Integer.valueOf(floor)));
            if(cardsByFloor.containsKey(Integer.valueOf(floor)))
                element.addCardChoiceData((CardChoiceStats)cardsByFloor.get(Integer.valueOf(floor)));
            if(relicsByFloor.containsKey(Integer.valueOf(floor)))
                element.addRelicObtainStats((List)relicsByFloor.get(Integer.valueOf(floor)));
            if(potionsByFloor.containsKey(Integer.valueOf(floor)))
                element.addPotionObtainStats((List)potionsByFloor.get(Integer.valueOf(floor)));
            if(campfireChoicesByFloor.containsKey(Integer.valueOf(floor)))
                element.addCampfireChoiceData((CampfireChoice)campfireChoicesByFloor.get(Integer.valueOf(floor)));
            if(purchasesByFloor.containsKey(Integer.valueOf(floor)))
                element.addShopPurchaseData((ArrayList)purchasesByFloor.get(Integer.valueOf(floor)));
            if(purgesByFloor.containsKey(Integer.valueOf(floor)))
                element.addPurgeData((ArrayList)purgesByFloor.get(Integer.valueOf(floor)));
            if(label.equals("T!") && runData.boss_relics != null && bossRelicChoiceIndex < runData.boss_relics.size())
            {
                element.addRelicObtainStats(((BossRelicChoiceStats)runData.boss_relics.get(bossRelicChoiceIndex)).picked);
                bossRelicChoiceIndex++;
            }
            pathElements.add(element);
            element.col = tmpColumn++;
            element.row = rows;
            if(isActEndNode(element) || i == labels.size() - 1)
            {
                tmpColumn = 0;
                rows++;
            }
        }

        rows = Math.max(rows, pathElements.size() / 20);
    }

    public void update()
    {
        boolean isHovered = false;
        Iterator iterator = pathElements.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            RunPathElement element = (RunPathElement)iterator.next();
            element.update();
            if(element.isHovered())
                isHovered = true;
        } while(true);
        if(isHovered)
            CardCrawlGame.cursor.changeType(com.megacrit.cardcrawl.core.GameCursor.CursorType.INSPECT);
    }

    private boolean isActEndNode(RunPathElement element)
    {
        return element.nodeType == RunPathElement.PathNodeType.BOSS_TREASURE || element.nodeType == RunPathElement.PathNodeType.HEART;
    }

    public void render(SpriteBatch sb, float x, float y)
    {
        float offsetX = x;
        float offsetY = y;
        int sinceOffset = 0;
        for(Iterator iterator = pathElements.iterator(); iterator.hasNext();)
        {
            RunPathElement element = (RunPathElement)iterator.next();
            element.position(offsetX, offsetY);
            if(isActEndNode(element) || sinceOffset > 20)
            {
                offsetX = x;
                offsetY -= RunPathElement.getApproximateHeight();
                sinceOffset = 0;
            } else
            {
                offsetX += RunPathElement.getApproximateWidth();
                sinceOffset++;
            }
        }

        RunPathElement element;
        for(Iterator iterator1 = pathElements.iterator(); iterator1.hasNext(); element.render(sb))
            element = (RunPathElement)iterator1.next();

    }

    public float approximateHeight()
    {
        return (float)rows * RunPathElement.getApproximateHeight();
    }

    public float approximateMaxWidth()
    {
        return 16.5F * RunPathElement.getApproximateWidth();
    }

    public static final String BOSS_TREASURE_LABEL = "T!";
    public static final String HEART_LABEL = "<3";
    public static final int MAX_NODES_PER_ROW = 20;
    private RunData runData;
    public List pathElements;
    private int rows;
}
