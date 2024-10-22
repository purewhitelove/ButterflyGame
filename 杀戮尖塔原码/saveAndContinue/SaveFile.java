// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SaveFile.java

package com.megacrit.cardcrawl.saveAndContinue;

import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.metrics.MetricData;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rewards.RewardSave;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.trials.AbstractTrial;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SaveFile
{
    public static final class SaveType extends Enum
    {

        public static SaveType[] values()
        {
            return (SaveType[])$VALUES.clone();
        }

        public static SaveType valueOf(String name)
        {
            return (SaveType)Enum.valueOf(com/megacrit/cardcrawl/saveAndContinue/SaveFile$SaveType, name);
        }

        public static final SaveType ENTER_ROOM;
        public static final SaveType POST_NEOW;
        public static final SaveType POST_COMBAT;
        public static final SaveType AFTER_BOSS_RELIC;
        public static final SaveType ENDLESS_NEOW;
        private static final SaveType $VALUES[];

        static 
        {
            ENTER_ROOM = new SaveType("ENTER_ROOM", 0);
            POST_NEOW = new SaveType("POST_NEOW", 1);
            POST_COMBAT = new SaveType("POST_COMBAT", 2);
            AFTER_BOSS_RELIC = new SaveType("AFTER_BOSS_RELIC", 3);
            ENDLESS_NEOW = new SaveType("ENDLESS_NEOW", 4);
            $VALUES = (new SaveType[] {
                ENTER_ROOM, POST_NEOW, POST_COMBAT, AFTER_BOSS_RELIC, ENDLESS_NEOW
            });
        }

        private SaveType(String s, int i)
        {
            super(s, i);
        }
    }


    public SaveFile()
    {
    }

    public SaveFile(SaveType type)
    {
        AbstractPlayer p = AbstractDungeon.player;
        name = p.name;
        current_health = p.currentHealth;
        max_health = p.maxHealth;
        max_orbs = p.masterMaxOrbs;
        gold = p.gold;
        hand_size = p.masterHandSize;
        red = p.energy.energyMaster;
        green = 0;
        blue = 0;
        monsters_killed = CardCrawlGame.monstersSlain;
        elites1_killed = CardCrawlGame.elites1Slain;
        elites2_killed = CardCrawlGame.elites2Slain;
        elites3_killed = CardCrawlGame.elites3Slain;
        champions = CardCrawlGame.champion;
        perfect = CardCrawlGame.perfect;
        overkill = CardCrawlGame.overkill;
        combo = CardCrawlGame.combo;
        cheater = CardCrawlGame.cheater;
        gold_gained = CardCrawlGame.goldGained;
        mystery_machine = CardCrawlGame.mysteryMachine;
        play_time = (long)CardCrawlGame.playtime;
        cards = p.masterDeck.getCardDeck();
        obtained_cards = CardHelper.obtainedCards;
        relics = new ArrayList();
        relic_counters = new ArrayList();
        AbstractRelic r;
        for(Iterator iterator = p.relics.iterator(); iterator.hasNext(); relic_counters.add(Integer.valueOf(r.counter)))
        {
            r = (AbstractRelic)iterator.next();
            relics.add(r.relicId);
        }

        is_endless_mode = Settings.isEndless;
        blights = new ArrayList();
        blight_counters = new ArrayList();
        AbstractBlight b;
        for(Iterator iterator1 = p.blights.iterator(); iterator1.hasNext(); blight_counters.add(Integer.valueOf(b.counter)))
        {
            b = (AbstractBlight)iterator1.next();
            blights.add(b.blightID);
        }

        endless_increments = new ArrayList();
        AbstractBlight b;
        for(Iterator iterator2 = p.blights.iterator(); iterator2.hasNext(); endless_increments.add(Integer.valueOf(b.increment)))
            b = (AbstractBlight)iterator2.next();

        potion_slots = AbstractDungeon.player.potionSlots;
        potions = new ArrayList();
        AbstractPotion pot;
        for(Iterator iterator3 = AbstractDungeon.player.potions.iterator(); iterator3.hasNext(); potions.add(pot.ID))
            pot = (AbstractPotion)iterator3.next();

        is_ascension_mode = AbstractDungeon.isAscensionMode;
        ascension_level = AbstractDungeon.ascensionLevel;
        chose_neow_reward = false;
        level_name = AbstractDungeon.id;
        floor_num = AbstractDungeon.floorNum;
        act_num = AbstractDungeon.actNum;
        monster_list = AbstractDungeon.monsterList;
        elite_monster_list = AbstractDungeon.eliteMonsterList;
        boss_list = AbstractDungeon.bossList;
        event_list = AbstractDungeon.eventList;
        one_time_event_list = AbstractDungeon.specialOneTimeEventList;
        potion_chance = AbstractRoom.blizzardPotionMod;
        event_chances = type != SaveType.POST_COMBAT ? EventHelper.getChances() : EventHelper.getChancesPreRoll();
        save_date = Calendar.getInstance().getTimeInMillis();
        seed = Settings.seed.longValue();
        if(Settings.specialSeed != null)
            special_seed = Settings.specialSeed.longValue();
        seed_set = Settings.seedSet;
        is_daily = Settings.isDailyRun;
        is_final_act_on = Settings.isFinalActAvailable;
        has_ruby_key = Settings.hasRubyKey;
        has_emerald_key = Settings.hasEmeraldKey;
        has_sapphire_key = Settings.hasSapphireKey;
        daily_date = Settings.dailyDate;
        is_trial = Settings.isTrial;
        daily_mods = ModHelper.getEnabledModIDs();
        if(AbstractPlayer.customMods == null)
            if(CardCrawlGame.trial != null)
                AbstractPlayer.customMods = CardCrawlGame.trial.dailyModIDs();
            else
                AbstractPlayer.customMods = new ArrayList();
        custom_mods = AbstractPlayer.customMods;
        boss = AbstractDungeon.bossKey;
        purgeCost = ShopScreen.purgeCost;
        monster_seed_count = AbstractDungeon.monsterRng.counter;
        event_seed_count = AbstractDungeon.eventRng.counter;
        merchant_seed_count = AbstractDungeon.merchantRng.counter;
        card_seed_count = AbstractDungeon.cardRng.counter;
        card_random_seed_randomizer = AbstractDungeon.cardBlizzRandomizer;
        treasure_seed_count = AbstractDungeon.treasureRng.counter;
        relic_seed_count = AbstractDungeon.relicRng.counter;
        potion_seed_count = AbstractDungeon.potionRng.counter;
        path_x = AbstractDungeon.pathX;
        path_y = AbstractDungeon.pathY;
        if(AbstractDungeon.nextRoom == null || type == SaveType.ENDLESS_NEOW)
        {
            room_x = AbstractDungeon.getCurrMapNode().x;
            room_y = AbstractDungeon.getCurrMapNode().y;
            current_room = AbstractDungeon.getCurrRoom().getClass().getName();
        } else
        {
            room_x = AbstractDungeon.nextRoom.x;
            room_y = AbstractDungeon.nextRoom.y;
            current_room = AbstractDungeon.nextRoom.room.getClass().getName();
        }
        spirit_count = AbstractDungeon.bossCount;
        logger.info((new StringBuilder()).append("Next Room: ").append(current_room).toString());
        common_relics = AbstractDungeon.commonRelicPool;
        uncommon_relics = AbstractDungeon.uncommonRelicPool;
        rare_relics = AbstractDungeon.rareRelicPool;
        shop_relics = AbstractDungeon.shopRelicPool;
        boss_relics = AbstractDungeon.bossRelicPool;
        post_combat = false;
        mugged = false;
        smoked = false;
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$rewards$RewardItem$RewardType[];
            static final int $SwitchMap$com$megacrit$cardcrawl$saveAndContinue$SaveFile$SaveType[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$saveAndContinue$SaveFile$SaveType = new int[SaveType.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$saveAndContinue$SaveFile$SaveType[SaveType.AFTER_BOSS_RELIC.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$saveAndContinue$SaveFile$SaveType[SaveType.ENTER_ROOM.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$saveAndContinue$SaveFile$SaveType[SaveType.POST_COMBAT.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$saveAndContinue$SaveFile$SaveType[SaveType.POST_NEOW.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                $SwitchMap$com$megacrit$cardcrawl$rewards$RewardItem$RewardType = new int[com.megacrit.cardcrawl.rewards.RewardItem.RewardType.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$rewards$RewardItem$RewardType[com.megacrit.cardcrawl.rewards.RewardItem.RewardType.SAPPHIRE_KEY.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$rewards$RewardItem$RewardType[com.megacrit.cardcrawl.rewards.RewardItem.RewardType.EMERALD_KEY.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror5) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$rewards$RewardItem$RewardType[com.megacrit.cardcrawl.rewards.RewardItem.RewardType.CARD.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror6) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$rewards$RewardItem$RewardType[com.megacrit.cardcrawl.rewards.RewardItem.RewardType.GOLD.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror7) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$rewards$RewardItem$RewardType[com.megacrit.cardcrawl.rewards.RewardItem.RewardType.POTION.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror8) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$rewards$RewardItem$RewardType[com.megacrit.cardcrawl.rewards.RewardItem.RewardType.RELIC.ordinal()] = 6;
                }
                catch(NoSuchFieldError nosuchfielderror9) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$rewards$RewardItem$RewardType[com.megacrit.cardcrawl.rewards.RewardItem.RewardType.STOLEN_GOLD.ordinal()] = 7;
                }
                catch(NoSuchFieldError nosuchfielderror10) { }
            }
        }

label0:
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.saveAndContinue.SaveFile.SaveType[type.ordinal()])
        {
        case 1: // '\001'
        case 2: // '\002'
        default:
            break;

        case 3: // '\003'
            post_combat = true;
            mugged = AbstractDungeon.getCurrRoom().mugged;
            smoked = AbstractDungeon.getCurrRoom().smoked;
            combat_rewards = new ArrayList();
            Iterator iterator4 = AbstractDungeon.getCurrRoom().rewards.iterator();
            do
            {
                if(!iterator4.hasNext())
                    break label0;
                RewardItem i = (RewardItem)iterator4.next();
                switch(_cls1..SwitchMap.com.megacrit.cardcrawl.rewards.RewardItem.RewardType[i.type.ordinal()])
                {
                case 1: // '\001'
                case 2: // '\002'
                case 3: // '\003'
                    combat_rewards.add(new RewardSave(i.type.toString(), null));
                    break;

                case 4: // '\004'
                    combat_rewards.add(new RewardSave(i.type.toString(), null, i.goldAmt, i.bonusGold));
                    break;

                case 5: // '\005'
                    combat_rewards.add(new RewardSave(i.type.toString(), i.potion.ID));
                    break;

                case 6: // '\006'
                    combat_rewards.add(new RewardSave(i.type.toString(), i.relic.relicId));
                    break;

                case 7: // '\007'
                    combat_rewards.add(new RewardSave(i.type.toString(), null, i.goldAmt, 0));
                    break;
                }
            } while(true);

        case 4: // '\004'
            chose_neow_reward = true;
            break;
        }
        if(AbstractDungeon.player.hasRelic("Bottled Flame"))
        {
            if(((BottledFlame)AbstractDungeon.player.getRelic("Bottled Flame")).card != null)
                bottled_flame = ((BottledFlame)AbstractDungeon.player.getRelic("Bottled Flame")).card.cardID;
            else
                bottled_flame = null;
        } else
        {
            bottled_flame = null;
        }
        if(AbstractDungeon.player.hasRelic("Bottled Lightning"))
        {
            if(((BottledLightning)AbstractDungeon.player.getRelic("Bottled Lightning")).card != null)
                bottled_lightning = ((BottledLightning)AbstractDungeon.player.getRelic("Bottled Lightning")).card.cardID;
            else
                bottled_lightning = null;
        } else
        {
            bottled_lightning = null;
        }
        if(AbstractDungeon.player.hasRelic("Bottled Tornado"))
        {
            if(((BottledTornado)AbstractDungeon.player.getRelic("Bottled Tornado")).card != null)
                bottled_tornado = ((BottledTornado)AbstractDungeon.player.getRelic("Bottled Tornado")).card.cardID;
            else
                bottled_tornado = null;
        } else
        {
            bottled_tornado = null;
        }
        metric_campfire_rested = CardCrawlGame.metricData.campfire_rested;
        metric_campfire_upgraded = CardCrawlGame.metricData.campfire_upgraded;
        metric_purchased_purges = CardCrawlGame.metricData.purchased_purges;
        metric_potions_floor_spawned = CardCrawlGame.metricData.potions_floor_spawned;
        metric_potions_floor_usage = CardCrawlGame.metricData.potions_floor_usage;
        metric_current_hp_per_floor = CardCrawlGame.metricData.current_hp_per_floor;
        metric_max_hp_per_floor = CardCrawlGame.metricData.max_hp_per_floor;
        metric_gold_per_floor = CardCrawlGame.metricData.gold_per_floor;
        metric_path_per_floor = CardCrawlGame.metricData.path_per_floor;
        metric_path_taken = CardCrawlGame.metricData.path_taken;
        metric_items_purchased = CardCrawlGame.metricData.items_purchased;
        metric_item_purchase_floors = CardCrawlGame.metricData.item_purchase_floors;
        metric_items_purged = CardCrawlGame.metricData.items_purged;
        metric_items_purged_floors = CardCrawlGame.metricData.items_purged_floors;
        metric_card_choices = CardCrawlGame.metricData.card_choices;
        metric_event_choices = CardCrawlGame.metricData.event_choices;
        metric_boss_relics = CardCrawlGame.metricData.boss_relics;
        metric_potions_obtained = CardCrawlGame.metricData.potions_obtained;
        metric_relics_obtained = CardCrawlGame.metricData.relics_obtained;
        metric_campfire_choices = CardCrawlGame.metricData.campfire_choices;
        metric_damage_taken = CardCrawlGame.metricData.damage_taken;
        metric_build_version = CardCrawlGame.TRUE_VERSION_NUM;
        metric_seed_played = Settings.seed.toString();
        metric_floor_reached = AbstractDungeon.floorNum;
        metric_playtime = (long)CardCrawlGame.playtime;
        neow_bonus = CardCrawlGame.metricData.neowBonus;
        neow_cost = CardCrawlGame.metricData.neowCost;
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/saveAndContinue/SaveFile.getName());
    public String name;
    public String loadout;
    public int current_health;
    public int max_health;
    public int max_orbs;
    public int gold;
    public int hand_size;
    public int potion_slots;
    public int red;
    public int green;
    public int blue;
    public ArrayList cards;
    public HashMap obtained_cards;
    public ArrayList relics;
    public ArrayList relic_counters;
    public ArrayList blights;
    public ArrayList blight_counters;
    public ArrayList potions;
    public boolean is_ascension_mode;
    public int ascension_level;
    public boolean chose_neow_reward;
    public String level_name;
    public long play_time;
    public long save_date;
    public long daily_date;
    public int floor_num;
    public int act_num;
    public long seed;
    public long special_seed;
    public boolean seed_set;
    public boolean is_trial;
    public boolean is_daily;
    public boolean is_final_act_on;
    public boolean has_ruby_key;
    public boolean has_emerald_key;
    public boolean has_sapphire_key;
    public ArrayList custom_mods;
    public ArrayList daily_mods;
    public int monster_seed_count;
    public int event_seed_count;
    public int merchant_seed_count;
    public int card_seed_count;
    public int treasure_seed_count;
    public int relic_seed_count;
    public int potion_seed_count;
    public int monster_hp_seed_count;
    public int ai_seed_count;
    public int shuffle_seed_count;
    public int card_random_seed_count;
    public int card_random_seed_randomizer;
    public int potion_chance;
    public int purgeCost;
    public ArrayList monster_list;
    public ArrayList elite_monster_list;
    public ArrayList boss_list;
    public ArrayList event_list;
    public ArrayList one_time_event_list;
    public ArrayList event_chances;
    public ArrayList path_x;
    public ArrayList path_y;
    public int room_x;
    public int room_y;
    public int spirit_count;
    public String boss;
    public String current_room;
    public ArrayList common_relics;
    public ArrayList uncommon_relics;
    public ArrayList rare_relics;
    public ArrayList shop_relics;
    public ArrayList boss_relics;
    public String bottled_flame;
    public String bottled_lightning;
    public String bottled_tornado;
    public int bottled_flame_upgrade;
    public int bottled_lightning_upgrade;
    public int bottled_tornado_upgrade;
    public int bottled_flame_misc;
    public int bottled_lightning_misc;
    public int bottled_tornado_misc;
    public boolean is_endless_mode;
    public ArrayList endless_increments;
    public boolean post_combat;
    public boolean mugged;
    public boolean smoked;
    public ArrayList combat_rewards;
    public int monsters_killed;
    public int elites1_killed;
    public int elites2_killed;
    public int elites3_killed;
    public int champions;
    public int perfect;
    public boolean overkill;
    public boolean combo;
    public boolean cheater;
    public int gold_gained;
    public int mystery_machine;
    public int metric_campfire_rested;
    public int metric_campfire_upgraded;
    public int metric_campfire_rituals;
    public int metric_campfire_meditates;
    public int metric_purchased_purges;
    public ArrayList metric_potions_floor_spawned;
    public ArrayList metric_potions_floor_usage;
    public ArrayList metric_current_hp_per_floor;
    public ArrayList metric_max_hp_per_floor;
    public ArrayList metric_gold_per_floor;
    public ArrayList metric_path_per_floor;
    public ArrayList metric_path_taken;
    public ArrayList metric_items_purchased;
    public ArrayList metric_item_purchase_floors;
    public ArrayList metric_items_purged;
    public ArrayList metric_items_purged_floors;
    public ArrayList metric_card_choices;
    public ArrayList metric_event_choices;
    public ArrayList metric_boss_relics;
    public ArrayList metric_damage_taken;
    public ArrayList metric_potions_obtained;
    public ArrayList metric_relics_obtained;
    public ArrayList metric_campfire_choices;
    public String metric_build_version;
    public String metric_seed_played;
    public int metric_floor_reached;
    public long metric_playtime;
    public String neow_bonus;
    public String neow_cost;

}
