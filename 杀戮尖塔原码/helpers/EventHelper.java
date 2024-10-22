// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EventHelper.java

package com.megacrit.cardcrawl.helpers;

import com.megacrit.cardcrawl.blights.MimicInfestation;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.daily.mods.DeadlyEvents;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.beyond.*;
import com.megacrit.cardcrawl.events.city.*;
import com.megacrit.cardcrawl.events.exordium.*;
import com.megacrit.cardcrawl.events.shrines.*;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.helpers:
//            ModHelper

public class EventHelper
{
    public static final class RoomResult extends Enum
    {

        public static RoomResult[] values()
        {
            return (RoomResult[])$VALUES.clone();
        }

        public static RoomResult valueOf(String name)
        {
            return (RoomResult)Enum.valueOf(com/megacrit/cardcrawl/helpers/EventHelper$RoomResult, name);
        }

        public static final RoomResult EVENT;
        public static final RoomResult ELITE;
        public static final RoomResult TREASURE;
        public static final RoomResult SHOP;
        public static final RoomResult MONSTER;
        private static final RoomResult $VALUES[];

        static 
        {
            EVENT = new RoomResult("EVENT", 0);
            ELITE = new RoomResult("ELITE", 1);
            TREASURE = new RoomResult("TREASURE", 2);
            SHOP = new RoomResult("SHOP", 3);
            MONSTER = new RoomResult("MONSTER", 4);
            $VALUES = (new RoomResult[] {
                EVENT, ELITE, TREASURE, SHOP, MONSTER
            });
        }

        private RoomResult(String s, int i)
        {
            super(s, i);
        }
    }


    public EventHelper()
    {
    }

    public static RoomResult roll()
    {
        return roll(AbstractDungeon.eventRng);
    }

    public static RoomResult roll(Random eventRng)
    {
        saveFilePreviousChances = getChances();
        float roll = eventRng.random();
        logger.info((new StringBuilder()).append("Rolling for room type... EVENT_RNG_COUNTER: ").append(AbstractDungeon.eventRng.counter).toString());
        boolean forceChest = false;
        if(AbstractDungeon.player.hasRelic("Tiny Chest"))
        {
            AbstractRelic r = AbstractDungeon.player.getRelic("Tiny Chest");
            r.counter++;
            if(r.counter == 4)
            {
                r.counter = 0;
                r.flash();
                forceChest = true;
            }
        }
        logger.info((new StringBuilder()).append("ROLL: ").append(roll).toString());
        logger.info((new StringBuilder()).append("ELIT: ").append(ELITE_CHANCE).toString());
        logger.info((new StringBuilder()).append("MNST: ").append(MONSTER_CHANCE).toString());
        logger.info((new StringBuilder()).append("SHOP: ").append(SHOP_CHANCE).toString());
        logger.info((new StringBuilder()).append("TRSR: ").append(TREASURE_CHANCE).toString());
        int eliteSize = 0;
        if(ModHelper.isModEnabled("DeadlyEvents"))
            eliteSize = (int)(ELITE_CHANCE * 100F);
        if(AbstractDungeon.floorNum < 6)
            eliteSize = 0;
        int monsterSize = (int)(MONSTER_CHANCE * 100F);
        int shopSize = (int)(SHOP_CHANCE * 100F);
        if(AbstractDungeon.getCurrRoom() instanceof ShopRoom)
            shopSize = 0;
        int treasureSize = (int)(TREASURE_CHANCE * 100F);
        int fillIndex = 0;
        RoomResult possibleResults[] = new RoomResult[100];
        Arrays.fill(possibleResults, RoomResult.EVENT);
        if(ModHelper.isModEnabled("DeadlyEvents"))
        {
            Arrays.fill(possibleResults, Math.min(99, fillIndex), Math.min(100, fillIndex + eliteSize), RoomResult.ELITE);
            fillIndex += eliteSize;
            Arrays.fill(possibleResults, Math.min(99, fillIndex), Math.min(100, fillIndex + eliteSize), RoomResult.ELITE);
            fillIndex += eliteSize;
        }
        Arrays.fill(possibleResults, Math.min(99, fillIndex), Math.min(100, fillIndex + monsterSize), RoomResult.MONSTER);
        fillIndex += monsterSize;
        Arrays.fill(possibleResults, Math.min(99, fillIndex), Math.min(100, fillIndex + shopSize), RoomResult.SHOP);
        fillIndex += shopSize;
        Arrays.fill(possibleResults, Math.min(99, fillIndex), Math.min(100, fillIndex + treasureSize), RoomResult.TREASURE);
        RoomResult choice = possibleResults[(int)(roll * 100F)];
        if(forceChest)
            choice = RoomResult.TREASURE;
        if(choice == RoomResult.ELITE)
        {
            ELITE_CHANCE = 0.0F;
            if(ModHelper.isModEnabled("DeadlyEvents"))
                ELITE_CHANCE = 0.1F;
        } else
        {
            ELITE_CHANCE += 0.1F;
        }
        if(choice == RoomResult.MONSTER)
        {
            if(AbstractDungeon.player.hasRelic("Juzu Bracelet"))
            {
                AbstractDungeon.player.getRelic("Juzu Bracelet").flash();
                choice = RoomResult.EVENT;
            }
            MONSTER_CHANCE = 0.1F;
        } else
        {
            MONSTER_CHANCE += 0.1F;
        }
        if(choice == RoomResult.SHOP)
            SHOP_CHANCE = 0.03F;
        else
            SHOP_CHANCE += 0.03F;
        if(Settings.isEndless && AbstractDungeon.player.hasBlight("MimicInfestation"))
        {
            if(choice == RoomResult.TREASURE)
            {
                if(AbstractDungeon.player.hasRelic("Juzu Bracelet"))
                {
                    AbstractDungeon.player.getRelic("Juzu Bracelet").flash();
                    choice = RoomResult.EVENT;
                } else
                {
                    choice = RoomResult.ELITE;
                }
                TREASURE_CHANCE = 0.02F;
                if(ModHelper.isModEnabled("DeadlyEvents"))
                    TREASURE_CHANCE += 0.02F;
            }
        } else
        if(choice == RoomResult.TREASURE)
        {
            TREASURE_CHANCE = 0.02F;
        } else
        {
            TREASURE_CHANCE += 0.02F;
            if(ModHelper.isModEnabled("DeadlyEvents"))
                TREASURE_CHANCE += 0.02F;
        }
        return choice;
    }

    public static void resetProbabilities()
    {
        saveFilePreviousChances = null;
        ELITE_CHANCE = 0.0F;
        MONSTER_CHANCE = 0.1F;
        SHOP_CHANCE = 0.03F;
        TREASURE_CHANCE = 0.02F;
    }

    public static void setChances(ArrayList chances)
    {
        ELITE_CHANCE = ((Float)chances.get(0)).floatValue();
        MONSTER_CHANCE = ((Float)chances.get(1)).floatValue();
        SHOP_CHANCE = ((Float)chances.get(2)).floatValue();
        TREASURE_CHANCE = ((Float)chances.get(3)).floatValue();
    }

    public static ArrayList getChances()
    {
        ArrayList chances = new ArrayList();
        chances.add(Float.valueOf(ELITE_CHANCE));
        chances.add(Float.valueOf(MONSTER_CHANCE));
        chances.add(Float.valueOf(SHOP_CHANCE));
        chances.add(Float.valueOf(TREASURE_CHANCE));
        return chances;
    }

    public static ArrayList getChancesPreRoll()
    {
        if(saveFilePreviousChances != null)
            return saveFilePreviousChances;
        else
            return getChances();
    }

    public static String getMostRecentEventID()
    {
        return saveFileLastEventChoice;
    }

    public static AbstractEvent getEvent(String key)
    {
        if(!Settings.isDev);
        saveFileLastEventChoice = key;
        String s = key;
        byte byte0 = -1;
        switch(s.hashCode())
        {
        case -833416624: 
            if(s.equals("Accursed Blacksmith"))
                byte0 = 0;
            break;

        case 2055237045: 
            if(s.equals("Bonfire Elementals"))
                byte0 = 1;
            break;

        case 1917982011: 
            if(s.equals("Fountain of Cleansing"))
                byte0 = 2;
            break;

        case 1088076555: 
            if(s.equals("Designer"))
                byte0 = 3;
            break;

        case 591082013: 
            if(s.equals("Duplicator"))
                byte0 = 4;
            break;

        case 76141: 
            if(s.equals("Lab"))
                byte0 = 5;
            break;

        case 1689970904: 
            if(s.equals("Match and Keep!"))
                byte0 = 6;
            break;

        case 195217658: 
            if(s.equals("Golden Shrine"))
                byte0 = 7;
            break;

        case 1813457356: 
            if(s.equals("Purifier"))
                byte0 = 8;
            break;

        case 1104324102: 
            if(s.equals("Transmorgrifier"))
                byte0 = 9;
            break;

        case -1971074156: 
            if(s.equals("Wheel of Change"))
                byte0 = 10;
            break;

        case 1694921927: 
            if(s.equals("Upgrade Shrine"))
                byte0 = 11;
            break;

        case -1699225493: 
            if(s.equals("FaceTrader"))
                byte0 = 12;
            break;

        case -258741642: 
            if(s.equals("NoteForYourself"))
                byte0 = 13;
            break;

        case 933905419: 
            if(s.equals("WeMeetAgain"))
                byte0 = 14;
            break;

        case 1167999560: 
            if(s.equals("The Woman in Blue"))
                byte0 = 15;
            break;

        case 740516280: 
            if(s.equals("Big Fish"))
                byte0 = 16;
            break;

        case 1962578239: 
            if(s.equals("The Cleric"))
                byte0 = 17;
            break;

        case 1824060574: 
            if(s.equals("Dead Adventurer"))
                byte0 = 18;
            break;

        case -191855422: 
            if(s.equals("Golden Wing"))
                byte0 = 19;
            break;

        case -192277265: 
            if(s.equals("Golden Idol"))
                byte0 = 20;
            break;

        case 1208556260: 
            if(s.equals("World of Goop"))
                byte0 = 21;
            break;

        case 815072724: 
            if(s.equals("Forgotten Altar"))
                byte0 = 22;
            break;

        case -1731176390: 
            if(s.equals("Scrap Ooze"))
                byte0 = 23;
            break;

        case 114917293: 
            if(s.equals("Liars Game"))
                byte0 = 24;
            break;

        case -980968223: 
            if(s.equals("Living Wall"))
                byte0 = 25;
            break;

        case -1914822917: 
            if(s.equals("Mushrooms"))
                byte0 = 26;
            break;

        case -2022548400: 
            if(s.equals("N'loth"))
                byte0 = 27;
            break;

        case 1953642014: 
            if(s.equals("Shining Light"))
                byte0 = 28;
            break;

        case -1321370043: 
            if(s.equals("Vampires"))
                byte0 = 29;
            break;

        case 2132136932: 
            if(s.equals("Ghosts"))
                byte0 = 30;
            break;

        case 1956330105: 
            if(s.equals("Addict"))
                byte0 = 31;
            break;

        case -1511782511: 
            if(s.equals("Back to Basics"))
                byte0 = 32;
            break;

        case 1985970164: 
            if(s.equals("Beggar"))
                byte0 = 33;
            break;

        case -646914399: 
            if(s.equals("Cursed Tome"))
                byte0 = 34;
            break;

        case -85879143: 
            if(s.equals("Drug Dealer"))
                byte0 = 35;
            break;

        case 1121505076: 
            if(s.equals("Knowing Skull"))
                byte0 = 36;
            break;

        case -1766312002: 
            if(s.equals("Masked Bandits"))
                byte0 = 37;
            break;

        case 2424440: 
            if(s.equals("Nest"))
                byte0 = 38;
            break;

        case 19316684: 
            if(s.equals("The Library"))
                byte0 = 39;
            break;

        case -1757506145: 
            if(s.equals("The Mausoleum"))
                byte0 = 40;
            break;

        case -207216254: 
            if(s.equals("The Joust"))
                byte0 = 41;
            break;

        case -308228690: 
            if(s.equals("Colosseum"))
                byte0 = 42;
            break;

        case -1178669457: 
            if(s.equals("Mysterious Sphere"))
                byte0 = 43;
            break;

        case 1051874140: 
            if(s.equals("SecretPortal"))
                byte0 = 44;
            break;

        case -1998240723: 
            if(s.equals("Tomb of Lord Red Mask"))
                byte0 = 45;
            break;

        case 580837991: 
            if(s.equals("Falling"))
                byte0 = 46;
            break;

        case -1819469228: 
            if(s.equals("Winding Halls"))
                byte0 = 47;
            break;

        case 236474855: 
            if(s.equals("The Moai Head"))
                byte0 = 48;
            break;

        case 998956998: 
            if(s.equals("SensoryStone"))
                byte0 = 49;
            break;

        case -1655196495: 
            if(s.equals("MindBloom"))
                byte0 = 50;
            break;
        }
        switch(byte0)
        {
        case 0: // '\0'
            return new AccursedBlacksmith();

        case 1: // '\001'
            return new Bonfire();

        case 2: // '\002'
            return new FountainOfCurseRemoval();

        case 3: // '\003'
            return new Designer();

        case 4: // '\004'
            return new Duplicator();

        case 5: // '\005'
            return new Lab();

        case 6: // '\006'
            return new GremlinMatchGame();

        case 7: // '\007'
            return new GoldShrine();

        case 8: // '\b'
            return new PurificationShrine();

        case 9: // '\t'
            return new Transmogrifier();

        case 10: // '\n'
            return new GremlinWheelGame();

        case 11: // '\013'
            return new UpgradeShrine();

        case 12: // '\f'
            return new FaceTrader();

        case 13: // '\r'
            return new NoteForYourself();

        case 14: // '\016'
            return new WeMeetAgain();

        case 15: // '\017'
            return new WomanInBlue();

        case 16: // '\020'
            return new BigFish();

        case 17: // '\021'
            return new Cleric();

        case 18: // '\022'
            return new DeadAdventurer();

        case 19: // '\023'
            return new GoldenWing();

        case 20: // '\024'
            return new GoldenIdolEvent();

        case 21: // '\025'
            return new GoopPuddle();

        case 22: // '\026'
            return new ForgottenAltar();

        case 23: // '\027'
            return new ScrapOoze();

        case 24: // '\030'
            return new Sssserpent();

        case 25: // '\031'
            return new LivingWall();

        case 26: // '\032'
            return new Mushrooms();

        case 27: // '\033'
            return new Nloth();

        case 28: // '\034'
            return new ShiningLight();

        case 29: // '\035'
            return new Vampires();

        case 30: // '\036'
            return new Ghosts();

        case 31: // '\037'
            return new Addict();

        case 32: // ' '
            return new BackToBasics();

        case 33: // '!'
            return new Beggar();

        case 34: // '"'
            return new CursedTome();

        case 35: // '#'
            return new DrugDealer();

        case 36: // '$'
            return new KnowingSkull();

        case 37: // '%'
            return new MaskedBandits();

        case 38: // '&'
            return new Nest();

        case 39: // '\''
            return new TheLibrary();

        case 40: // '('
            return new TheMausoleum();

        case 41: // ')'
            return new TheJoust();

        case 42: // '*'
            return new Colosseum();

        case 43: // '+'
            return new MysteriousSphere();

        case 44: // ','
            return new SecretPortal();

        case 45: // '-'
            return new TombRedMask();

        case 46: // '.'
            return new Falling();

        case 47: // '/'
            return new WindingHalls();

        case 48: // '0'
            return new MoaiHead();

        case 49: // '1'
            return new SensoryStone();

        case 50: // '2'
            return new MindBloom();
        }
        logger.info((new StringBuilder()).append("---------------------------\nERROR: Unspecified key: ").append(key).append(" in EventHelper.\n---------------------------").toString());
        return null;
    }

    public static String getEventName(String key)
    {
        String s = key;
        byte byte0 = -1;
        switch(s.hashCode())
        {
        case -833416624: 
            if(s.equals("Accursed Blacksmith"))
                byte0 = 0;
            break;

        case 2055237045: 
            if(s.equals("Bonfire Elementals"))
                byte0 = 1;
            break;

        case 1917982011: 
            if(s.equals("Fountain of Cleansing"))
                byte0 = 2;
            break;

        case 1088076555: 
            if(s.equals("Designer"))
                byte0 = 3;
            break;

        case 591082013: 
            if(s.equals("Duplicator"))
                byte0 = 4;
            break;

        case 76141: 
            if(s.equals("Lab"))
                byte0 = 5;
            break;

        case 1689970904: 
            if(s.equals("Match and Keep!"))
                byte0 = 6;
            break;

        case 195217658: 
            if(s.equals("Golden Shrine"))
                byte0 = 7;
            break;

        case 1813457356: 
            if(s.equals("Purifier"))
                byte0 = 8;
            break;

        case 1104324102: 
            if(s.equals("Transmorgrifier"))
                byte0 = 9;
            break;

        case -1971074156: 
            if(s.equals("Wheel of Change"))
                byte0 = 10;
            break;

        case 1694921927: 
            if(s.equals("Upgrade Shrine"))
                byte0 = 11;
            break;

        case -1699225493: 
            if(s.equals("FaceTrader"))
                byte0 = 12;
            break;

        case -258741642: 
            if(s.equals("NoteForYourself"))
                byte0 = 13;
            break;

        case 933905419: 
            if(s.equals("WeMeetAgain"))
                byte0 = 14;
            break;

        case 1167999560: 
            if(s.equals("The Woman in Blue"))
                byte0 = 15;
            break;

        case 740516280: 
            if(s.equals("Big Fish"))
                byte0 = 16;
            break;

        case 1962578239: 
            if(s.equals("The Cleric"))
                byte0 = 17;
            break;

        case 1824060574: 
            if(s.equals("Dead Adventurer"))
                byte0 = 18;
            break;

        case -191855422: 
            if(s.equals("Golden Wing"))
                byte0 = 19;
            break;

        case -192277265: 
            if(s.equals("Golden Idol"))
                byte0 = 20;
            break;

        case 1208556260: 
            if(s.equals("World of Goop"))
                byte0 = 21;
            break;

        case 815072724: 
            if(s.equals("Forgotten Altar"))
                byte0 = 22;
            break;

        case -1731176390: 
            if(s.equals("Scrap Ooze"))
                byte0 = 23;
            break;

        case 114917293: 
            if(s.equals("Liars Game"))
                byte0 = 24;
            break;

        case -980968223: 
            if(s.equals("Living Wall"))
                byte0 = 25;
            break;

        case -1914822917: 
            if(s.equals("Mushrooms"))
                byte0 = 26;
            break;

        case -2022548400: 
            if(s.equals("N'loth"))
                byte0 = 27;
            break;

        case 1953642014: 
            if(s.equals("Shining Light"))
                byte0 = 28;
            break;

        case -1321370043: 
            if(s.equals("Vampires"))
                byte0 = 29;
            break;

        case 2132136932: 
            if(s.equals("Ghosts"))
                byte0 = 30;
            break;

        case 1956330105: 
            if(s.equals("Addict"))
                byte0 = 31;
            break;

        case -1511782511: 
            if(s.equals("Back to Basics"))
                byte0 = 32;
            break;

        case 1985970164: 
            if(s.equals("Beggar"))
                byte0 = 33;
            break;

        case -646914399: 
            if(s.equals("Cursed Tome"))
                byte0 = 34;
            break;

        case -85879143: 
            if(s.equals("Drug Dealer"))
                byte0 = 35;
            break;

        case 1121505076: 
            if(s.equals("Knowing Skull"))
                byte0 = 36;
            break;

        case -1766312002: 
            if(s.equals("Masked Bandits"))
                byte0 = 37;
            break;

        case 2424440: 
            if(s.equals("Nest"))
                byte0 = 38;
            break;

        case 19316684: 
            if(s.equals("The Library"))
                byte0 = 39;
            break;

        case -1757506145: 
            if(s.equals("The Mausoleum"))
                byte0 = 40;
            break;

        case -207216254: 
            if(s.equals("The Joust"))
                byte0 = 41;
            break;

        case -308228690: 
            if(s.equals("Colosseum"))
                byte0 = 42;
            break;

        case -1178669457: 
            if(s.equals("Mysterious Sphere"))
                byte0 = 43;
            break;

        case 1051874140: 
            if(s.equals("SecretPortal"))
                byte0 = 44;
            break;

        case -1998240723: 
            if(s.equals("Tomb of Lord Red Mask"))
                byte0 = 45;
            break;

        case 580837991: 
            if(s.equals("Falling"))
                byte0 = 46;
            break;

        case -1819469228: 
            if(s.equals("Winding Halls"))
                byte0 = 47;
            break;

        case 236474855: 
            if(s.equals("The Moai Head"))
                byte0 = 48;
            break;

        case 998956998: 
            if(s.equals("SensoryStone"))
                byte0 = 49;
            break;

        case -1655196495: 
            if(s.equals("MindBloom"))
                byte0 = 50;
            break;
        }
        switch(byte0)
        {
        case 0: // '\0'
            return AccursedBlacksmith.NAME;

        case 1: // '\001'
            return Bonfire.NAME;

        case 2: // '\002'
            return FountainOfCurseRemoval.NAME;

        case 3: // '\003'
            return Designer.NAME;

        case 4: // '\004'
            return Duplicator.NAME;

        case 5: // '\005'
            return Lab.NAME;

        case 6: // '\006'
            return GremlinMatchGame.NAME;

        case 7: // '\007'
            return GoldShrine.NAME;

        case 8: // '\b'
            return PurificationShrine.NAME;

        case 9: // '\t'
            return Transmogrifier.NAME;

        case 10: // '\n'
            return GremlinWheelGame.NAME;

        case 11: // '\013'
            return UpgradeShrine.NAME;

        case 12: // '\f'
            return FaceTrader.NAME;

        case 13: // '\r'
            return NoteForYourself.NAME;

        case 14: // '\016'
            return WeMeetAgain.NAME;

        case 15: // '\017'
            return WomanInBlue.NAME;

        case 16: // '\020'
            return BigFish.NAME;

        case 17: // '\021'
            return Cleric.NAME;

        case 18: // '\022'
            return DeadAdventurer.NAME;

        case 19: // '\023'
            return GoldenWing.NAME;

        case 20: // '\024'
            return GoldenIdolEvent.NAME;

        case 21: // '\025'
            return GoopPuddle.NAME;

        case 22: // '\026'
            return ForgottenAltar.NAME;

        case 23: // '\027'
            return ScrapOoze.NAME;

        case 24: // '\030'
            return Sssserpent.NAME;

        case 25: // '\031'
            return LivingWall.NAME;

        case 26: // '\032'
            return Mushrooms.NAME;

        case 27: // '\033'
            return Nloth.NAME;

        case 28: // '\034'
            return ShiningLight.NAME;

        case 29: // '\035'
            return Vampires.NAME;

        case 30: // '\036'
            return Ghosts.NAME;

        case 31: // '\037'
            return Addict.NAME;

        case 32: // ' '
            return BackToBasics.NAME;

        case 33: // '!'
            return Beggar.NAME;

        case 34: // '"'
            return CursedTome.NAME;

        case 35: // '#'
            return DrugDealer.NAME;

        case 36: // '$'
            return KnowingSkull.NAME;

        case 37: // '%'
            return MaskedBandits.NAME;

        case 38: // '&'
            return Nest.NAME;

        case 39: // '\''
            return TheLibrary.NAME;

        case 40: // '('
            return TheMausoleum.NAME;

        case 41: // ')'
            return TheJoust.NAME;

        case 42: // '*'
            return Colosseum.NAME;

        case 43: // '+'
            return MysteriousSphere.NAME;

        case 44: // ','
            return SecretPortal.NAME;

        case 45: // '-'
            return TombRedMask.NAME;

        case 46: // '.'
            return Falling.NAME;

        case 47: // '/'
            return WindingHalls.NAME;

        case 48: // '0'
            return MoaiHead.NAME;

        case 49: // '1'
            return SensoryStone.NAME;

        case 50: // '2'
            return MindBloom.NAME;
        }
        return "";
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/helpers/EventHelper.getName());
    private static final float BASE_ELITE_CHANCE = 0.1F;
    private static final float BASE_MONSTER_CHANCE = 0.1F;
    private static final float BASE_SHOP_CHANCE = 0.03F;
    private static final float BASE_TREASURE_CHANCE = 0.02F;
    private static final float RAMP_ELITE_CHANCE = 0.1F;
    private static final float RAMP_MONSTER_CHANCE = 0.1F;
    private static final float RAMP_SHOP_CHANCE = 0.03F;
    private static final float RAMP_TREASURE_CHANCE = 0.02F;
    private static final float RESET_ELITE_CHANCE = 0F;
    private static final float RESET_MONSTER_CHANCE = 0.1F;
    private static final float RESET_SHOP_CHANCE = 0.03F;
    private static final float RESET_TREASURE_CHANCE = 0.02F;
    private static float ELITE_CHANCE = 0.1F;
    private static float MONSTER_CHANCE = 0.1F;
    private static float SHOP_CHANCE = 0.03F;
    public static float TREASURE_CHANCE = 0.02F;
    private static ArrayList saveFilePreviousChances;
    private static String saveFileLastEventChoice;

}
