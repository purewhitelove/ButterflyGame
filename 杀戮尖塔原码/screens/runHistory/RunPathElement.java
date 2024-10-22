// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RunPathElement.java

package com.megacrit.cardcrawl.screens.runHistory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.events.beyond.SecretPortal;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.screens.stats.*;
import java.util.*;

public class RunPathElement
{
    public static final class PathNodeType extends Enum
    {

        public static PathNodeType[] values()
        {
            return (PathNodeType[])$VALUES.clone();
        }

        public static PathNodeType valueOf(String name)
        {
            return (PathNodeType)Enum.valueOf(com/megacrit/cardcrawl/screens/runHistory/RunPathElement$PathNodeType, name);
        }

        public static final PathNodeType ERROR;
        public static final PathNodeType MONSTER;
        public static final PathNodeType ELITE;
        public static final PathNodeType EVENT;
        public static final PathNodeType BOSS;
        public static final PathNodeType TREASURE;
        public static final PathNodeType BOSS_TREASURE;
        public static final PathNodeType CAMPFIRE;
        public static final PathNodeType SHOP;
        public static final PathNodeType UNKNOWN_MONSTER;
        public static final PathNodeType UNKNOWN_SHOP;
        public static final PathNodeType UNKNOWN_TREASURE;
        public static final PathNodeType HEART;
        private static final PathNodeType $VALUES[];

        static 
        {
            ERROR = new PathNodeType("ERROR", 0);
            MONSTER = new PathNodeType("MONSTER", 1);
            ELITE = new PathNodeType("ELITE", 2);
            EVENT = new PathNodeType("EVENT", 3);
            BOSS = new PathNodeType("BOSS", 4);
            TREASURE = new PathNodeType("TREASURE", 5);
            BOSS_TREASURE = new PathNodeType("BOSS_TREASURE", 6);
            CAMPFIRE = new PathNodeType("CAMPFIRE", 7);
            SHOP = new PathNodeType("SHOP", 8);
            UNKNOWN_MONSTER = new PathNodeType("UNKNOWN_MONSTER", 9);
            UNKNOWN_SHOP = new PathNodeType("UNKNOWN_SHOP", 10);
            UNKNOWN_TREASURE = new PathNodeType("UNKNOWN_TREASURE", 11);
            HEART = new PathNodeType("HEART", 12);
            $VALUES = (new PathNodeType[] {
                ERROR, MONSTER, ELITE, EVENT, BOSS, TREASURE, BOSS_TREASURE, CAMPFIRE, SHOP, UNKNOWN_MONSTER, 
                UNKNOWN_SHOP, UNKNOWN_TREASURE, HEART
            });
        }

        private PathNodeType(String s, int i)
        {
            super(s, i);
        }
    }


    public RunPathElement(String roomKey, int floorNum)
    {
        cachedTooltip = null;
        hb = new Hitbox(ICON_SIZE, ICON_SIZE);
        nodeType = pathNodeTypeForRoomKey(roomKey);
        floor = floorNum;
    }

    public void addHpData(Integer current, Integer max)
    {
        currentHP = current;
        maxHP = max;
    }

    public void addGoldData(Integer gold)
    {
        this.gold = gold;
    }

    public void addBattleData(BattleStats battle)
    {
        battleStats = battle;
    }

    public void addEventData(EventStats event)
    {
        eventStats = event;
    }

    public void addCardChoiceData(CardChoiceStats choice)
    {
        cardChoiceStats = choice;
    }

    public void addRelicObtainStats(List relicKeys)
    {
        relicsObtained = relicKeys;
    }

    public void addRelicObtainStats(String relicKey)
    {
        addRelicObtainStats(Arrays.asList(new String[] {
            relicKey
        }));
    }

    public void addPotionObtainStats(List potionKey)
    {
        potionsObtained = potionKey;
    }

    public void addCampfireChoiceData(CampfireChoice choice)
    {
        campfireChoice = choice;
    }

    public void addShopPurchaseData(ArrayList keys)
    {
        shopPurchases = keys;
    }

    public void addPurgeData(ArrayList keys)
    {
        shopPurges = keys;
    }

    public void update()
    {
        hb.update();
        if(hb.hovered)
        {
            float tipX = hb.x + 64F * Settings.scale;
            float tipY = hb.y + ICON_SIZE / 2.0F;
            String header = String.format(TEXT_SIMPLE_FLOOR_FORMAT, new Object[] {
                Integer.valueOf(floor)
            });
            TipHelper.renderGenericTip(tipX, tipY, header, getTipDescriptionText());
        }
    }

    private String getTipHeaderWithRoomTypeText()
    {
        return String.format(TEXT_FLOOR_FORMAT, new Object[] {
            Integer.toString(floor), stringForType()
        });
    }

    private String getTipDescriptionText()
    {
        if(cachedTooltip != null)
            return cachedTooltip;
        StringBuilder sb = new StringBuilder();
        sb.append(stringForType());
        boolean displayHP = currentHP != null && maxHP != null;
        if(displayHP)
        {
            if(sb.length() > 0)
                sb.append(" NL ");
            sb.append(String.format(TEXT_COMBAT_HP_FORMAT, new Object[] {
                currentHP, maxHP
            }));
            sb.append(" TAB ");
        }
        boolean displayGold = gold != null;
        if(displayGold)
            sb.append(String.format(TEXT_GOLD_FORMAT, new Object[] {
                gold
            }));
        if(eventStats != null)
        {
            if(sb.length() > 0)
                sb.append(" NL ");
            sb.append(localizedEventNameForKey(eventStats.event_name));
            if(!eventStats.player_choice.equals("Took Portal") && eventStats.max_hp_gain == 0 && eventStats.max_hp_loss == 0 && eventStats.gold_loss == 0 && eventStats.gold_gain == 0 && eventStats.damage_taken == 0 && eventStats.damage_healed == 0 && eventStats.cards_removed == null && eventStats.cards_obtained == null && eventStats.cards_transformed == null && eventStats.cards_upgraded == null && eventStats.relics_obtained == null && eventStats.relics_lost == null && eventStats.potions_obtained == null && battleStats == null)
            {
                sb.append(" NL ");
                sb.append(" TAB ").append(TEXT_IGNORED);
            }
            if(eventStats.relics_lost != null && !eventStats.relics_lost.isEmpty())
            {
                sb.append(" NL ");
                for(int i = 0; i < eventStats.relics_lost.size(); i++)
                {
                    String relicID = (String)eventStats.relics_lost.get(i);
                    String relicName = RelicLibrary.getRelic(relicID).name;
                    sb.append(" TAB ").append(String.format(TEXT_LOST_RELIC, new Object[] {
                        relicName
                    }));
                    if(i < eventStats.relics_lost.size() - 1)
                        sb.append(" NL ");
                }

            }
            if(eventStats.max_hp_loss != 0)
            {
                sb.append(" NL ");
                sb.append(" TAB ").append(TEXT_LOST);
                sb.append(String.format(TEXT_GENERIC_MAX_HP_FORMAT, new Object[] {
                    Integer.valueOf(eventStats.max_hp_loss)
                }));
            }
            if(eventStats.damage_taken != 0)
            {
                sb.append(" NL ");
                sb.append(" TAB ").append(TEXT_TOOK);
                sb.append(String.format(TEXT_EVENT_DAMAGE, new Object[] {
                    Integer.valueOf(eventStats.damage_taken)
                }));
            }
            if(eventStats.gold_loss != 0)
            {
                sb.append(" NL ");
                sb.append(" TAB ").append(TEXT_SPENT);
                sb.append(String.format(TEXT_GOLD_FORMAT, new Object[] {
                    Integer.valueOf(eventStats.gold_loss)
                }));
            }
            if(eventStats.max_hp_gain != 0)
            {
                sb.append(" NL ");
                sb.append(" TAB ").append(TEXT_GAINED);
                sb.append(String.format(TEXT_GENERIC_MAX_HP_FORMAT, new Object[] {
                    Integer.valueOf(eventStats.max_hp_gain)
                }));
            }
            if(eventStats.damage_healed != 0)
            {
                sb.append(" NL ");
                sb.append(" TAB ").append(TEXT_HEALED);
                sb.append(String.format(TEXT_GENERIC_HP_FORMAT, new Object[] {
                    Integer.valueOf(eventStats.damage_healed)
                }));
            }
            if(eventStats.gold_gain != 0)
            {
                sb.append(" NL ");
                sb.append(" TAB ").append(TEXT_GAINED);
                sb.append(String.format(TEXT_GOLD_FORMAT, new Object[] {
                    Integer.valueOf(eventStats.gold_gain)
                }));
            }
            if(eventStats.cards_removed != null && !eventStats.cards_removed.isEmpty())
            {
                sb.append(" NL ");
                for(int i = 0; i < eventStats.cards_removed.size(); i++)
                {
                    String cardID = (String)eventStats.cards_removed.get(i);
                    String cardName = CardLibrary.getCardNameFromMetricID(cardID);
                    sb.append(" TAB ").append(String.format(TEXT_REMOVE_OPTION, new Object[] {
                        cardName
                    }));
                    if(i < eventStats.cards_removed.size() - 1)
                        sb.append(" NL ");
                }

            }
            if(eventStats.cards_upgraded != null && !eventStats.cards_upgraded.isEmpty())
            {
                sb.append(" NL ");
                for(int i = 0; i < eventStats.cards_upgraded.size(); i++)
                {
                    String cardID = (String)eventStats.cards_upgraded.get(i);
                    String cardName = CardLibrary.getCardNameFromMetricID(cardID);
                    sb.append(" TAB ").append(String.format(TEXT_UPGRADED, new Object[] {
                        cardName
                    }));
                    if(i < eventStats.cards_upgraded.size() - 1)
                        sb.append(" NL ");
                }

            }
            if(eventStats.cards_transformed != null && !eventStats.cards_transformed.isEmpty())
            {
                sb.append(" NL ");
                for(int i = 0; i < eventStats.cards_transformed.size(); i++)
                {
                    String cardID = (String)eventStats.cards_transformed.get(i);
                    String cardName = CardLibrary.getCardNameFromMetricID(cardID);
                    sb.append(" TAB ").append(String.format(TEXT_TRANSFORMED, new Object[] {
                        cardName
                    }));
                    if(i < eventStats.cards_transformed.size() - 1)
                        sb.append(" NL ");
                }

            }
            if((eventStats.cards_obtained != null && !eventStats.cards_obtained.isEmpty() || eventStats.relics_obtained != null && !eventStats.relics_obtained.isEmpty() || eventStats.potions_obtained != null && !eventStats.potions_obtained.isEmpty()) && relicsObtained == null && battleStats == null && cardChoiceStats == null && potionsObtained == null)
            {
                sb.append(" NL ").append(TEXT_OBTAIN_HEADER);
                if(eventStats.relics_obtained != null && !eventStats.relics_obtained.isEmpty())
                {
                    if(sb.length() > 0)
                        sb.append(" NL ");
                    for(int i = 0; i < eventStats.relics_obtained.size(); i++)
                    {
                        String name = RelicLibrary.getRelic((String)eventStats.relics_obtained.get(i)).name;
                        if(i > 0)
                            sb.append(" NL ");
                        sb.append(" TAB ").append(TEXT_OBTAIN_TYPE_RELIC).append(name);
                    }

                }
                if(eventStats.cards_obtained != null && !eventStats.cards_obtained.isEmpty() && !eventStats.cards_obtained.isEmpty() && !eventStats.cards_obtained.equals("SKIP"))
                {
                    if(sb.length() > 0)
                        sb.append(" NL ");
                    for(int i = 0; i < eventStats.cards_obtained.size(); i++)
                    {
                        String name = CardLibrary.getCardNameFromMetricID((String)eventStats.cards_obtained.get(i));
                        if(i > 0)
                            sb.append(" NL ");
                        sb.append(" TAB ").append(TEXT_OBTAIN_TYPE_CARD).append(name);
                    }

                }
                if(eventStats.potions_obtained != null && !eventStats.potions_obtained.isEmpty())
                {
                    if(sb.length() > 0)
                        sb.append(" NL ");
                    String key;
                    for(Iterator iterator = eventStats.potions_obtained.iterator(); iterator.hasNext(); sb.append(" TAB ").append(TEXT_OBTAIN_TYPE_POTION).append(PotionHelper.getPotion(key).name))
                        key = (String)iterator.next();

                }
            }
        }
        if(battleStats != null)
        {
            if(sb.length() > 0)
                sb.append(" NL ");
            sb.append(localizedEnemyNameForKey(battleStats.enemies));
            sb.append(" NL ");
            sb.append(" TAB ").append(String.format(TEXT_DAMAGE_FORMAT, new Object[] {
                Integer.valueOf(battleStats.damage)
            }));
            sb.append(" NL ");
            sb.append(" TAB ").append(String.format(TEXT_TURNS_FORMAT, new Object[] {
                Integer.valueOf(battleStats.turns)
            }));
        }
        if(campfireChoice != null)
        {
            if(sb.length() > 0)
                sb.append(" NL ");
            String s = campfireChoice.key;
            byte byte0 = -1;
            switch(s.hashCode())
            {
            case 2511828: 
                if(s.equals("REST"))
                    byte0 = 0;
                break;

            case 79018979: 
                if(s.equals("SMITH"))
                    byte0 = 1;
                break;

            case 76494987: 
                if(s.equals("PURGE"))
                    byte0 = 2;
                break;

            case 67682: 
                if(s.equals("DIG"))
                    byte0 = 3;
                break;

            case 2336523: 
                if(s.equals("LIFT"))
                    byte0 = 4;
                break;

            case -1881593071: 
                if(s.equals("RECALL"))
                    byte0 = 5;
                break;
            }
            switch(byte0)
            {
            case 0: // '\0'
                sb.append(TEXT_REST_OPTION);
                break;

            case 1: // '\001'
                sb.append(String.format(TEXT_SMITH_OPTION, new Object[] {
                    CardLibrary.getCardNameFromMetricID(campfireChoice.data)
                }));
                break;

            case 2: // '\002'
                sb.append(String.format(TEXT_TOKE_OPTION, new Object[] {
                    CardLibrary.getCardNameFromMetricID(campfireChoice.data)
                }));
                break;

            case 3: // '\003'
                sb.append(TEXT_DIG_OPTION);
                break;

            case 4: // '\004'
                sb.append(String.format(TEXT_LIFT_OPTION, new Object[] {
                    campfireChoice.data, Integer.valueOf(3)
                }));
                break;

            case 5: // '\005'
                sb.append(TEXT_RECALL_OPTION);
                break;

            default:
                sb.append(TEXT_MISSING_INFO);
                break;
            }
        }
        boolean showRelic = relicsObtained != null;
        boolean showPotion = potionsObtained != null;
        boolean showCards = cardChoiceStats != null && !cardChoiceStats.picked.equals("SKIP");
        if(showRelic || showPotion || showCards)
            sb.append(" NL ").append(TEXT_OBTAIN_HEADER);
        if(showRelic)
        {
            if(sb.length() > 0)
                sb.append(" NL ");
            for(int i = 0; i < relicsObtained.size(); i++)
            {
                String name = RelicLibrary.getRelic((String)relicsObtained.get(i)).name;
                if(i > 0)
                    sb.append(" NL ");
                sb.append(" TAB ").append(TEXT_OBTAIN_TYPE_RELIC).append(name);
            }

        }
        if(showCards && !cardChoiceStats.picked.isEmpty() && !cardChoiceStats.picked.equals("SKIP"))
        {
            String text;
            if(cardChoiceStats.picked.equals("Singing Bowl"))
                text = (new StringBuilder()).append(TEXT_OBTAIN_TYPE_SPECIAL).append(TEXT_SINGING_BOWL_CHOICE).toString();
            else
                text = (new StringBuilder()).append(TEXT_OBTAIN_TYPE_CARD).append(CardLibrary.getCardNameFromMetricID(cardChoiceStats.picked)).toString();
            if(sb.length() > 0)
                sb.append(" NL ");
            sb.append(" TAB ").append(text);
        }
        if(showPotion)
        {
            if(sb.length() > 0)
                sb.append(" NL ");
            String key;
            for(Iterator iterator1 = potionsObtained.iterator(); iterator1.hasNext(); sb.append(" TAB ").append(TEXT_OBTAIN_TYPE_POTION).append(PotionHelper.getPotion(key).name))
                key = (String)iterator1.next();

        }
        if(cardChoiceStats != null)
        {
            sb.append(" NL ");
            sb.append(TEXT_SKIP_HEADER);
            sb.append(" NL ");
            for(int i = 0; i < cardChoiceStats.not_picked.size(); i++)
            {
                String cardID = (String)cardChoiceStats.not_picked.get(i);
                String cardName = CardLibrary.getCardNameFromMetricID(cardID);
                sb.append(" TAB ").append(TEXT_OBTAIN_TYPE_CARD).append(cardName);
                if(i < cardChoiceStats.not_picked.size() - 1)
                    sb.append(" NL ");
            }

        }
        if(shopPurchases != null)
        {
            sb.append(" NL ").append(TEXT_PURCHASED);
            Iterator iterator2 = shopPurchases.iterator();
            do
            {
                if(!iterator2.hasNext())
                    break;
                String key = (String)iterator2.next();
                String text = null;
                if(CardLibrary.isACard(key))
                    text = (new StringBuilder()).append(TEXT_OBTAIN_TYPE_CARD).append(CardLibrary.getCardNameFromMetricID(key)).toString();
                else
                if(RelicLibrary.isARelic(key))
                    text = (new StringBuilder()).append(TEXT_OBTAIN_TYPE_RELIC).append(RelicLibrary.getRelic(key).name).toString();
                else
                if(PotionHelper.isAPotion(key))
                    text = (new StringBuilder()).append(TEXT_OBTAIN_TYPE_POTION).append(PotionHelper.getPotion(key).name).toString();
                if(text != null)
                    sb.append(" NL ").append(" TAB ").append(text);
            } while(true);
        }
        if(shopPurges != null)
        {
            String key;
            for(Iterator iterator3 = shopPurges.iterator(); iterator3.hasNext(); sb.append(" NL ").append(String.format(TEXT_REMOVE_OPTION, new Object[] {
    CardLibrary.getCardNameFromMetricID(key)
})))
                key = (String)iterator3.next();

        }
        if(sb.length() > 0)
        {
            cachedTooltip = sb.toString();
            return cachedTooltip;
        } else
        {
            return TEXT_MISSING_INFO;
        }
    }

    public boolean isHovered()
    {
        return hb.hovered;
    }

    public void position(float x, float y)
    {
        hb.move(x, y - ICON_SIZE);
    }

    public static float getApproximateWidth()
    {
        return ICON_SIZE;
    }

    public static float getApproximateHeight()
    {
        return ICON_SIZE;
    }

    public void render(SpriteBatch sb)
    {
        Texture image = imageForType(nodeType);
        if(isHovered())
        {
            float hoverSize = ICON_SIZE * 2.0F;
            float offset = (hoverSize - ICON_SIZE) / 2.0F;
            sb.draw(image, hb.x - offset, hb.y - offset, hoverSize, hoverSize);
        } else
        {
            sb.draw(image, hb.x, hb.y, ICON_SIZE, ICON_SIZE);
        }
        hb.render(sb);
    }

    public String localizedEnemyNameForKey(String enemyId)
    {
        return MonsterHelper.getEncounterName(enemyId);
    }

    public String localizedEventNameForKey(String eventId)
    {
        return EventHelper.getEventName(eventId);
    }

    public PathNodeType pathNodeTypeForRoomKey(String roomKey)
    {
        String s = roomKey;
        byte byte0 = -1;
        switch(s.hashCode())
        {
        case 77: // 'M'
            if(s.equals("M"))
                byte0 = 0;
            break;

        case 69: // 'E'
            if(s.equals("E"))
                byte0 = 1;
            break;

        case 63: // '?'
            if(s.equals("?"))
                byte0 = 2;
            break;

        case 66: // 'B'
            if(s.equals("B"))
                byte0 = 3;
            break;

        case 2044781: 
            if(s.equals("BOSS"))
                byte0 = 4;
            break;

        case 84: // 'T'
            if(s.equals("T"))
                byte0 = 5;
            break;

        case 2637: 
            if(s.equals("T!"))
                byte0 = 6;
            break;

        case 82: // 'R'
            if(s.equals("R"))
                byte0 = 7;
            break;

        case 36: // '$'
            if(s.equals("$"))
                byte0 = 8;
            break;

        case 1917701: 
            if(s.equals("?(M)"))
                byte0 = 9;
            break;

        case 1916430: 
            if(s.equals("?($)"))
                byte0 = 10;
            break;

        case 1917918: 
            if(s.equals("?(T)"))
                byte0 = 11;
            break;

        case 1918259: 
            if(s.equals("?(_)"))
                byte0 = 12;
            break;

        case 1911: 
            if(s.equals("<3"))
                byte0 = 13;
            break;
        }
        switch(byte0)
        {
        case 0: // '\0'
            return PathNodeType.MONSTER;

        case 1: // '\001'
            return PathNodeType.ELITE;

        case 2: // '\002'
            return PathNodeType.EVENT;

        case 3: // '\003'
        case 4: // '\004'
            return PathNodeType.BOSS;

        case 5: // '\005'
            return PathNodeType.TREASURE;

        case 6: // '\006'
            return PathNodeType.BOSS_TREASURE;

        case 7: // '\007'
            return PathNodeType.CAMPFIRE;

        case 8: // '\b'
            return PathNodeType.SHOP;

        case 9: // '\t'
            return PathNodeType.UNKNOWN_MONSTER;

        case 10: // '\n'
            return PathNodeType.UNKNOWN_SHOP;

        case 11: // '\013'
            return PathNodeType.UNKNOWN_TREASURE;

        case 12: // '\f'
            return PathNodeType.EVENT;

        case 13: // '\r'
            return PathNodeType.HEART;
        }
        return PathNodeType.ERROR;
    }

    public Texture imageForType(PathNodeType nodeType)
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$screens$runHistory$RunPathElement$PathNodeType[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$screens$runHistory$RunPathElement$PathNodeType = new int[PathNodeType.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$runHistory$RunPathElement$PathNodeType[PathNodeType.MONSTER.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$runHistory$RunPathElement$PathNodeType[PathNodeType.ELITE.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$runHistory$RunPathElement$PathNodeType[PathNodeType.EVENT.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$runHistory$RunPathElement$PathNodeType[PathNodeType.BOSS.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$runHistory$RunPathElement$PathNodeType[PathNodeType.TREASURE.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$runHistory$RunPathElement$PathNodeType[PathNodeType.BOSS_TREASURE.ordinal()] = 6;
                }
                catch(NoSuchFieldError nosuchfielderror5) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$runHistory$RunPathElement$PathNodeType[PathNodeType.CAMPFIRE.ordinal()] = 7;
                }
                catch(NoSuchFieldError nosuchfielderror6) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$runHistory$RunPathElement$PathNodeType[PathNodeType.SHOP.ordinal()] = 8;
                }
                catch(NoSuchFieldError nosuchfielderror7) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$runHistory$RunPathElement$PathNodeType[PathNodeType.UNKNOWN_MONSTER.ordinal()] = 9;
                }
                catch(NoSuchFieldError nosuchfielderror8) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$runHistory$RunPathElement$PathNodeType[PathNodeType.UNKNOWN_SHOP.ordinal()] = 10;
                }
                catch(NoSuchFieldError nosuchfielderror9) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$runHistory$RunPathElement$PathNodeType[PathNodeType.UNKNOWN_TREASURE.ordinal()] = 11;
                }
                catch(NoSuchFieldError nosuchfielderror10) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.screens.runHistory.RunPathElement.PathNodeType[nodeType.ordinal()])
        {
        case 1: // '\001'
            return ImageMaster.RUN_HISTORY_MAP_ICON_MONSTER;

        case 2: // '\002'
            return ImageMaster.RUN_HISTORY_MAP_ICON_ELITE;

        case 3: // '\003'
            return ImageMaster.RUN_HISTORY_MAP_ICON_EVENT;

        case 4: // '\004'
            return ImageMaster.RUN_HISTORY_MAP_ICON_BOSS;

        case 5: // '\005'
            return ImageMaster.RUN_HISTORY_MAP_ICON_CHEST;

        case 6: // '\006'
            return ImageMaster.RUN_HISTORY_MAP_ICON_BOSS_CHEST;

        case 7: // '\007'
            return ImageMaster.RUN_HISTORY_MAP_ICON_REST;

        case 8: // '\b'
            return ImageMaster.RUN_HISTORY_MAP_ICON_SHOP;

        case 9: // '\t'
            return ImageMaster.RUN_HISTORY_MAP_ICON_UNKNOWN_MONSTER;

        case 10: // '\n'
            return ImageMaster.RUN_HISTORY_MAP_ICON_UNKNOWN_SHOP;

        case 11: // '\013'
            return ImageMaster.RUN_HISTORY_MAP_ICON_UNKNOWN_CHEST;
        }
        return ImageMaster.RUN_HISTORY_MAP_ICON_EVENT;
    }

    private String stringForType()
    {
        return stringForType(nodeType);
    }

    private String stringForType(PathNodeType type)
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.screens.runHistory.RunPathElement.PathNodeType[type.ordinal()])
        {
        case 1: // '\001'
            return TEXT_MONSTER;

        case 2: // '\002'
            return TEXT_ELITE;

        case 3: // '\003'
            return TEXT_EVENT;

        case 4: // '\004'
            return TEXT_BOSS;

        case 5: // '\005'
            return TEXT_TREASURE;

        case 6: // '\006'
            return TEXT_BOSS_TREASURE;

        case 7: // '\007'
            return TEXT_CAMPFIRE;

        case 8: // '\b'
            return TEXT_SHOP;

        case 9: // '\t'
            return TEXT_UNKNOWN_MONSTER;

        case 10: // '\n'
            return TEXT_UNKN0WN_SHOP;

        case 11: // '\013'
            return TEXT_UNKNOWN_TREASURE;
        }
        return TEXT_ERROR;
    }

    private static final boolean SHOW_ROOM_TYPE = true;
    private static final boolean SHOW_HP = true;
    private static final boolean SHOW_GOLD = true;
    private static final boolean SHOW_FIGHT_DETAILS = true;
    private static final boolean SHOW_EVENT_DETAILS = true;
    private static final boolean SHOW_EVENT_PLAYER_CHOICE = true;
    private static final boolean SHOW_CARD_PICK_DETAILS = true;
    private static final boolean SHOW_CARD_SKIP_INFO = true;
    private static final boolean SHOW_RELIC_OBTAIN_DETAILS = true;
    private static final boolean SHOW_POTION_OBTAIN_DETAILS = true;
    private static final boolean SHOW_CAMPFIRE_CHOICE_DETAILS = true;
    private static final boolean SHOW_PURCHASE_DETAILS = true;
    private static final float ICON_SIZE;
    private static final float ICON_HOVER_SCALE = 2F;
    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private static final String NEW_LINE = " NL ";
    private static final String TAB = " TAB ";
    private static final String TEXT_ERROR;
    private static final String TEXT_MONSTER;
    private static final String TEXT_ELITE;
    private static final String TEXT_EVENT;
    private static final String TEXT_BOSS;
    private static final String TEXT_TREASURE;
    private static final String TEXT_BOSS_TREASURE;
    private static final String TEXT_CAMPFIRE;
    private static final String TEXT_SHOP;
    private static final String TEXT_UNKNOWN_MONSTER;
    private static final String TEXT_UNKN0WN_SHOP;
    private static final String TEXT_UNKNOWN_TREASURE;
    private static final String TEXT_FLOOR_FORMAT;
    private static final String TEXT_SIMPLE_FLOOR_FORMAT;
    private static final String TEXT_DAMAGE_FORMAT;
    private static final String TEXT_TURNS_FORMAT;
    private static final String TEXT_COMBAT_HP_FORMAT;
    private static final String TEXT_GOLD_FORMAT;
    private static final String TEXT_OBTAIN_HEADER;
    private static final String TEXT_SKIP_HEADER;
    private static final String TEXT_MISSING_INFO;
    private static final String TEXT_SINGING_BOWL_CHOICE;
    private static final String TEXT_OBTAIN_TYPE_CARD;
    private static final String TEXT_OBTAIN_TYPE_RELIC;
    private static final String TEXT_OBTAIN_TYPE_POTION;
    private static final String TEXT_OBTAIN_TYPE_SPECIAL;
    private static final String TEXT_REST_OPTION;
    private static final String TEXT_SMITH_OPTION;
    private static final String TEXT_TOKE_OPTION;
    private static final String TEXT_DIG_OPTION;
    private static final String TEXT_LIFT_OPTION;
    private static final String TEXT_RECALL_OPTION;
    private static final String TEXT_PURCHASED;
    private static final String TEXT_SPENT;
    private static final String TEXT_TOOK;
    private static final String TEXT_LOST;
    private static final String TEXT_GENERIC_MAX_HP_FORMAT;
    private static final String TEXT_HEALED;
    private static final String TEXT_GAINED;
    private static final String TEXT_IGNORED;
    private static final String TEXT_GENERIC_HP_FORMAT;
    private static final String TEXT_EVENT_DAMAGE;
    private static final String TEXT_UPGRADED;
    private static final String TEXT_TRANSFORMED;
    private static final String TEXT_LOST_RELIC;
    private static final String TEXT_REMOVE_OPTION;
    public Hitbox hb;
    public PathNodeType nodeType;
    private int floor;
    public int col;
    public int row;
    private Integer currentHP;
    private Integer maxHP;
    private Integer gold;
    private BattleStats battleStats;
    private EventStats eventStats;
    private CardChoiceStats cardChoiceStats;
    private List relicsObtained;
    private List potionsObtained;
    private CampfireChoice campfireChoice;
    private List shopPurchases;
    private List shopPurges;
    private String cachedTooltip;

    static 
    {
        ICON_SIZE = 48F * Settings.scale;
        uiStrings = CardCrawlGame.languagePack.getUIString("RunHistoryPathNodes");
        TEXT = uiStrings.TEXT;
        TEXT_ERROR = TEXT[0];
        TEXT_MONSTER = TEXT[1];
        TEXT_ELITE = TEXT[2];
        TEXT_EVENT = TEXT[3];
        TEXT_BOSS = TEXT[4];
        TEXT_TREASURE = TEXT[5];
        TEXT_BOSS_TREASURE = TEXT[6];
        TEXT_CAMPFIRE = TEXT[7];
        TEXT_SHOP = TEXT[8];
        TEXT_UNKNOWN_MONSTER = TEXT[9];
        TEXT_UNKN0WN_SHOP = TEXT[10];
        TEXT_UNKNOWN_TREASURE = TEXT[11];
        TEXT_FLOOR_FORMAT = TEXT[12];
        TEXT_SIMPLE_FLOOR_FORMAT = TEXT[13];
        TEXT_DAMAGE_FORMAT = TEXT[14];
        TEXT_TURNS_FORMAT = TEXT[15];
        TEXT_COMBAT_HP_FORMAT = TEXT[16];
        TEXT_GOLD_FORMAT = TEXT[17];
        TEXT_OBTAIN_HEADER = TEXT[18];
        TEXT_SKIP_HEADER = TEXT[19];
        TEXT_MISSING_INFO = TEXT[20];
        TEXT_SINGING_BOWL_CHOICE = TEXT[21];
        TEXT_OBTAIN_TYPE_CARD = TEXT[22];
        TEXT_OBTAIN_TYPE_RELIC = TEXT[23];
        TEXT_OBTAIN_TYPE_POTION = TEXT[24];
        TEXT_OBTAIN_TYPE_SPECIAL = TEXT[25];
        TEXT_REST_OPTION = TEXT[26];
        TEXT_SMITH_OPTION = TEXT[27];
        TEXT_TOKE_OPTION = TEXT[28];
        TEXT_DIG_OPTION = TEXT[29];
        TEXT_LIFT_OPTION = TEXT[30];
        TEXT_RECALL_OPTION = TEXT[46];
        TEXT_PURCHASED = TEXT[31];
        TEXT_SPENT = TEXT[32];
        TEXT_TOOK = TEXT[33];
        TEXT_LOST = TEXT[34];
        TEXT_GENERIC_MAX_HP_FORMAT = TEXT[35];
        TEXT_HEALED = TEXT[36];
        TEXT_GAINED = TEXT[37];
        TEXT_IGNORED = TEXT[38];
        TEXT_GENERIC_HP_FORMAT = TEXT[39];
        TEXT_EVENT_DAMAGE = TEXT[42];
        TEXT_UPGRADED = TEXT[43];
        TEXT_TRANSFORMED = TEXT[44];
        TEXT_LOST_RELIC = TEXT[45];
        TEXT_REMOVE_OPTION = TEXT_TOKE_OPTION;
    }
}
