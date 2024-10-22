// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MetricData.java

package com.megacrit.cardcrawl.metrics;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import java.util.ArrayList;
import java.util.HashMap;

public class MetricData
{

    public MetricData()
    {
        campfire_rested = 0;
        campfire_upgraded = 0;
        purchased_purges = 0;
        win_rate = 0.5F;
        potions_floor_spawned = new ArrayList();
        potions_floor_usage = new ArrayList();
        current_hp_per_floor = new ArrayList();
        max_hp_per_floor = new ArrayList();
        gold_per_floor = new ArrayList();
        path_per_floor = new ArrayList();
        path_taken = new ArrayList();
        items_purchased = new ArrayList();
        item_purchase_floors = new ArrayList();
        items_purged = new ArrayList();
        items_purged_floors = new ArrayList();
        card_choices = new ArrayList();
        event_choices = new ArrayList();
        boss_relics = new ArrayList();
        damage_taken = new ArrayList();
        potions_obtained = new ArrayList();
        relics_obtained = new ArrayList();
        campfire_choices = new ArrayList();
        neowBonus = "";
        neowCost = "";
    }

    public void clearData()
    {
        campfire_rested = 0;
        campfire_upgraded = 0;
        purchased_purges = 0;
        potions_floor_spawned.clear();
        potions_floor_usage.clear();
        current_hp_per_floor.clear();
        max_hp_per_floor.clear();
        gold_per_floor.clear();
        path_per_floor.clear();
        path_taken.clear();
        items_purchased.clear();
        item_purchase_floors.clear();
        items_purged.clear();
        items_purged_floors.clear();
        card_choices.clear();
        event_choices.clear();
        damage_taken.clear();
        potions_obtained.clear();
        relics_obtained.clear();
        campfire_choices.clear();
        boss_relics.clear();
        neowBonus = "";
        neowCost = "";
    }

    public void addEncounterData()
    {
        HashMap combat = new HashMap();
        combat.put("floor", Integer.valueOf(AbstractDungeon.floorNum));
        combat.put("enemies", AbstractDungeon.lastCombatMetricKey);
        combat.put("damage", Integer.valueOf(GameActionManager.damageReceivedThisCombat));
        combat.put("turns", Integer.valueOf(GameActionManager.turn));
        damage_taken.add(combat);
    }

    public void addPotionObtainData(AbstractPotion potion)
    {
        HashMap obtainInfo = new HashMap();
        obtainInfo.put("key", potion.ID);
        obtainInfo.put("floor", Integer.valueOf(AbstractDungeon.floorNum));
        potions_obtained.add(obtainInfo);
    }

    public void addRelicObtainData(AbstractRelic relic)
    {
        HashMap obtainInfo = new HashMap();
        obtainInfo.put("key", relic.relicId);
        obtainInfo.put("floor", Integer.valueOf(AbstractDungeon.floorNum));
        relics_obtained.add(obtainInfo);
    }

    public void addCampfireChoiceData(String choiceKey)
    {
        addCampfireChoiceData(choiceKey, null);
    }

    public void addCampfireChoiceData(String choiceKey, String data)
    {
        HashMap choice = new HashMap();
        choice.put("floor", Integer.valueOf(AbstractDungeon.floorNum));
        choice.put("key", choiceKey);
        if(data != null)
            choice.put("data", data);
        campfire_choices.add(choice);
    }

    public void addShopPurchaseData(String key)
    {
        if(items_purchased.size() == item_purchase_floors.size())
            item_purchase_floors.add(Integer.valueOf(AbstractDungeon.floorNum));
        items_purchased.add(key);
    }

    public void addPurgedItem(String key)
    {
        if(items_purged.size() == items_purged_floors.size())
            items_purged_floors.add(Integer.valueOf(AbstractDungeon.floorNum));
        items_purged.add(key);
        purchased_purges++;
    }

    public void addNeowData(String bonus, String cost)
    {
        neowBonus = bonus;
        neowCost = cost;
    }

    public int campfire_rested;
    public int campfire_upgraded;
    public int purchased_purges;
    public float win_rate;
    public ArrayList potions_floor_spawned;
    public ArrayList potions_floor_usage;
    public ArrayList current_hp_per_floor;
    public ArrayList max_hp_per_floor;
    public ArrayList gold_per_floor;
    public ArrayList path_per_floor;
    public ArrayList path_taken;
    public ArrayList items_purchased;
    public ArrayList item_purchase_floors;
    public ArrayList items_purged;
    public ArrayList items_purged_floors;
    public ArrayList card_choices;
    public ArrayList event_choices;
    public ArrayList boss_relics;
    public ArrayList damage_taken;
    public ArrayList potions_obtained;
    public ArrayList relics_obtained;
    public ArrayList campfire_choices;
    public String neowBonus;
    public String neowCost;
}
