// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PotionHelper.java

package com.megacrit.cardcrawl.helpers;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.metrics.BotDataUploader;
import com.megacrit.cardcrawl.potions.*;
import com.megacrit.cardcrawl.random.Random;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PotionHelper
{

    public PotionHelper()
    {
    }

    public static void initialize(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass chosenClass)
    {
        potions.clear();
        potions = getPotions(chosenClass, false);
    }

    public static ArrayList getPotionsByRarity(com.megacrit.cardcrawl.potions.AbstractPotion.PotionRarity rarity)
    {
        ArrayList retVal = new ArrayList();
        Iterator iterator = getPotions(null, true).iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            String s = (String)iterator.next();
            AbstractPotion p = getPotion(s);
            if(p.rarity == rarity)
                retVal.add(p);
        } while(true);
        return retVal;
    }

    public static ArrayList getPotions(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass c, boolean getAll)
    {
        ArrayList retVal = new ArrayList();
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$characters$AbstractPlayer$PlayerClass[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$characters$AbstractPlayer$PlayerClass = new int[com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$characters$AbstractPlayer$PlayerClass[com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.IRONCLAD.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$characters$AbstractPlayer$PlayerClass[com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.THE_SILENT.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$characters$AbstractPlayer$PlayerClass[com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.DEFECT.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$characters$AbstractPlayer$PlayerClass[com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.WATCHER.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
            }
        }

        if(!getAll)
        {
            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass[c.ordinal()])
            {
            case 1: // '\001'
                retVal.add("BloodPotion");
                retVal.add("ElixirPotion");
                retVal.add("HeartOfIron");
                break;

            case 2: // '\002'
                retVal.add("Poison Potion");
                retVal.add("CunningPotion");
                retVal.add("GhostInAJar");
                break;

            case 3: // '\003'
                retVal.add("FocusPotion");
                retVal.add("PotionOfCapacity");
                retVal.add("EssenceOfDarkness");
                break;

            case 4: // '\004'
                retVal.add("BottledMiracle");
                retVal.add("StancePotion");
                retVal.add("Ambrosia");
                break;
            }
        } else
        {
            retVal.add("BloodPotion");
            retVal.add("ElixirPotion");
            retVal.add("HeartOfIron");
            retVal.add("Poison Potion");
            retVal.add("CunningPotion");
            retVal.add("GhostInAJar");
            retVal.add("FocusPotion");
            retVal.add("PotionOfCapacity");
            retVal.add("EssenceOfDarkness");
            retVal.add("BottledMiracle");
            retVal.add("StancePotion");
            retVal.add("Ambrosia");
        }
        retVal.add("Block Potion");
        retVal.add("Dexterity Potion");
        retVal.add("Energy Potion");
        retVal.add("Explosive Potion");
        retVal.add("Fire Potion");
        retVal.add("Strength Potion");
        retVal.add("Swift Potion");
        retVal.add("Weak Potion");
        retVal.add("FearPotion");
        retVal.add("AttackPotion");
        retVal.add("SkillPotion");
        retVal.add("PowerPotion");
        retVal.add("ColorlessPotion");
        retVal.add("SteroidPotion");
        retVal.add("SpeedPotion");
        retVal.add("BlessingOfTheForge");
        retVal.add("Regen Potion");
        retVal.add("Ancient Potion");
        retVal.add("LiquidBronze");
        retVal.add("GamblersBrew");
        retVal.add("EssenceOfSteel");
        retVal.add("DuplicationPotion");
        retVal.add("DistilledChaos");
        retVal.add("LiquidMemories");
        retVal.add("CultistPotion");
        retVal.add("Fruit Juice");
        retVal.add("SneckoOil");
        retVal.add("FairyPotion");
        retVal.add("SmokeBomb");
        retVal.add("EntropicBrew");
        return retVal;
    }

    public static AbstractPotion getRandomPotion(Random rng)
    {
        String randomKey = (String)potions.get(rng.random(potions.size() - 1));
        return getPotion(randomKey);
    }

    public static AbstractPotion getRandomPotion()
    {
        String randomKey = (String)potions.get(AbstractDungeon.potionRng.random(potions.size() - 1));
        return getPotion(randomKey);
    }

    public static boolean isAPotion(String key)
    {
        return getPotions(null, true).contains(key);
    }

    public static AbstractPotion getPotion(String name)
    {
        if(name == null || name.equals(""))
            return null;
        String s = name;
        byte byte0 = -1;
        switch(s.hashCode())
        {
        case -1813432936: 
            if(s.equals("Ambrosia"))
                byte0 = 0;
            break;

        case 1592755027: 
            if(s.equals("BottledMiracle"))
                byte0 = 1;
            break;

        case -1573559404: 
            if(s.equals("EssenceOfDarkness"))
                byte0 = 2;
            break;

        case -1521326906: 
            if(s.equals("Block Potion"))
                byte0 = 3;
            break;

        case 1321250223: 
            if(s.equals("Dexterity Potion"))
                byte0 = 4;
            break;

        case 187249195: 
            if(s.equals("Energy Potion"))
                byte0 = 5;
            break;

        case 1163620974: 
            if(s.equals("Explosive Potion"))
                byte0 = 6;
            break;

        case 378930877: 
            if(s.equals("Fire Potion"))
                byte0 = 7;
            break;

        case -1946326446: 
            if(s.equals("Strength Potion"))
                byte0 = 8;
            break;

        case 938559616: 
            if(s.equals("Swift Potion"))
                byte0 = 9;
            break;

        case -976296533: 
            if(s.equals("Poison Potion"))
                byte0 = 10;
            break;

        case -1084502213: 
            if(s.equals("Weak Potion"))
                byte0 = 11;
            break;

        case -993832189: 
            if(s.equals("FearPotion"))
                byte0 = 12;
            break;

        case -471865564: 
            if(s.equals("SkillPotion"))
                byte0 = 13;
            break;

        case 2018692824: 
            if(s.equals("PowerPotion"))
                byte0 = 14;
            break;

        case 571908635: 
            if(s.equals("AttackPotion"))
                byte0 = 15;
            break;

        case 1092732943: 
            if(s.equals("ColorlessPotion"))
                byte0 = 16;
            break;

        case 1974850767: 
            if(s.equals("SteroidPotion"))
                byte0 = 17;
            break;

        case -639415142: 
            if(s.equals("SpeedPotion"))
                byte0 = 18;
            break;

        case 1272738036: 
            if(s.equals("BlessingOfTheForge"))
                byte0 = 19;
            break;

        case 1711181252: 
            if(s.equals("PotionOfCapacity"))
                byte0 = 20;
            break;

        case 748260995: 
            if(s.equals("CunningPotion"))
                byte0 = 21;
            break;

        case -563244898: 
            if(s.equals("DistilledChaos"))
                byte0 = 22;
            break;

        case 891586619: 
            if(s.equals("Ancient Potion"))
                byte0 = 23;
            break;

        case -871582954: 
            if(s.equals("Regen Potion"))
                byte0 = 24;
            break;

        case -131581938: 
            if(s.equals("GhostInAJar"))
                byte0 = 25;
            break;

        case -989581205: 
            if(s.equals("FocusPotion"))
                byte0 = 26;
            break;

        case -1365175786: 
            if(s.equals("LiquidBronze"))
                byte0 = 27;
            break;

        case 1312927451: 
            if(s.equals("LiquidMemories"))
                byte0 = 28;
            break;

        case -852144469: 
            if(s.equals("GamblersBrew"))
                byte0 = 29;
            break;

        case -2006332828: 
            if(s.equals("EssenceOfSteel"))
                byte0 = 30;
            break;

        case -16400371: 
            if(s.equals("BloodPotion"))
                byte0 = 31;
            break;

        case -717939453: 
            if(s.equals("StancePotion"))
                byte0 = 32;
            break;

        case 741355073: 
            if(s.equals("DuplicationPotion"))
                byte0 = 33;
            break;

        case 1313368658: 
            if(s.equals("ElixirPotion"))
                byte0 = 34;
            break;

        case 1939845731: 
            if(s.equals("CultistPotion"))
                byte0 = 35;
            break;

        case 286712340: 
            if(s.equals("Fruit Juice"))
                byte0 = 36;
            break;

        case 1120790805: 
            if(s.equals("SneckoOil"))
                byte0 = 37;
            break;

        case -396593240: 
            if(s.equals("FairyPotion"))
                byte0 = 38;
            break;

        case -114734959: 
            if(s.equals("SmokeBomb"))
                byte0 = 39;
            break;

        case -1392305052: 
            if(s.equals("EntropicBrew"))
                byte0 = 40;
            break;

        case 912386821: 
            if(s.equals("HeartOfIron"))
                byte0 = 41;
            break;

        case -350179221: 
            if(s.equals("Potion Slot"))
                byte0 = 42;
            break;
        }
        switch(byte0)
        {
        case 0: // '\0'
            return new Ambrosia();

        case 1: // '\001'
            return new BottledMiracle();

        case 2: // '\002'
            return new EssenceOfDarkness();

        case 3: // '\003'
            return new BlockPotion();

        case 4: // '\004'
            return new DexterityPotion();

        case 5: // '\005'
            return new EnergyPotion();

        case 6: // '\006'
            return new ExplosivePotion();

        case 7: // '\007'
            return new FirePotion();

        case 8: // '\b'
            return new StrengthPotion();

        case 9: // '\t'
            return new SwiftPotion();

        case 10: // '\n'
            return new PoisonPotion();

        case 11: // '\013'
            return new WeakenPotion();

        case 12: // '\f'
            return new FearPotion();

        case 13: // '\r'
            return new SkillPotion();

        case 14: // '\016'
            return new PowerPotion();

        case 15: // '\017'
            return new AttackPotion();

        case 16: // '\020'
            return new ColorlessPotion();

        case 17: // '\021'
            return new SteroidPotion();

        case 18: // '\022'
            return new SpeedPotion();

        case 19: // '\023'
            return new BlessingOfTheForge();

        case 20: // '\024'
            return new PotionOfCapacity();

        case 21: // '\025'
            return new CunningPotion();

        case 22: // '\026'
            return new DistilledChaosPotion();

        case 23: // '\027'
            return new AncientPotion();

        case 24: // '\030'
            return new RegenPotion();

        case 25: // '\031'
            return new GhostInAJar();

        case 26: // '\032'
            return new FocusPotion();

        case 27: // '\033'
            return new LiquidBronze();

        case 28: // '\034'
            return new LiquidMemories();

        case 29: // '\035'
            return new GamblersBrew();

        case 30: // '\036'
            return new EssenceOfSteel();

        case 31: // '\037'
            return new BloodPotion();

        case 32: // ' '
            return new StancePotion();

        case 33: // '!'
            return new DuplicationPotion();

        case 34: // '"'
            return new Elixir();

        case 35: // '#'
            return new CultistPotion();

        case 36: // '$'
            return new FruitJuice();

        case 37: // '%'
            return new SneckoOil();

        case 38: // '&'
            return new FairyPotion();

        case 39: // '\''
            return new SmokeBomb();

        case 40: // '('
            return new EntropicBrew();

        case 41: // ')'
            return new HeartOfIron();

        case 42: // '*'
            return null;
        }
        logger.info((new StringBuilder()).append("MISSING KEY: POTIONHELPER 37: ").append(name).toString());
        return new FirePotion();
    }

    public static void uploadPotionData()
    {
        initialize(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.IRONCLAD);
        HashSet ironcladPotions = new HashSet(potions);
        HashSet sharedPotions = new HashSet(potions);
        initialize(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.THE_SILENT);
        HashSet silentPotions = new HashSet(potions);
        sharedPotions.retainAll(potions);
        initialize(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.DEFECT);
        HashSet defectPotions = new HashSet(potions);
        sharedPotions.retainAll(potions);
        initialize(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.WATCHER);
        HashSet watcherPotions = new HashSet(potions);
        sharedPotions.retainAll(potions);
        ironcladPotions.removeAll(sharedPotions);
        silentPotions.removeAll(sharedPotions);
        defectPotions.removeAll(sharedPotions);
        watcherPotions.removeAll(sharedPotions);
        potions.clear();
        ArrayList data = new ArrayList();
        String id;
        for(Iterator iterator = ironcladPotions.iterator(); iterator.hasNext(); data.add(getPotion(id).getUploadData("RED")))
            id = (String)iterator.next();

        String id;
        for(Iterator iterator1 = silentPotions.iterator(); iterator1.hasNext(); data.add(getPotion(id).getUploadData("GREEN")))
            id = (String)iterator1.next();

        String id;
        for(Iterator iterator2 = defectPotions.iterator(); iterator2.hasNext(); data.add(getPotion(id).getUploadData("BLUE")))
            id = (String)iterator2.next();

        String id;
        for(Iterator iterator3 = watcherPotions.iterator(); iterator3.hasNext(); data.add(getPotion(id).getUploadData("PURPLE")))
            id = (String)iterator3.next();

        String id;
        for(Iterator iterator4 = sharedPotions.iterator(); iterator4.hasNext(); data.add(getPotion(id).getUploadData("ALL")))
            id = (String)iterator4.next();

        BotDataUploader.uploadDataAsync(com.megacrit.cardcrawl.metrics.BotDataUploader.GameDataType.POTION_DATA, AbstractPotion.gameDataUploadHeader(), data);
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/helpers/PotionHelper.getName());
    public static ArrayList potions = new ArrayList();
    public static int POTION_COMMON_CHANCE = 65;
    public static int POTION_UNCOMMON_CHANCE = 25;

}
