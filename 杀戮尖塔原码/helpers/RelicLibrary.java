// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RelicLibrary.java

package com.megacrit.cardcrawl.helpers;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.metrics.BotDataUploader;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.helpers:
//            ImageMaster

public class RelicLibrary
{

    public RelicLibrary()
    {
    }

    public static void initialize()
    {
        long startTime = System.currentTimeMillis();
        add(new Abacus());
        add(new Akabeko());
        add(new Anchor());
        add(new AncientTeaSet());
        add(new ArtOfWar());
        add(new Astrolabe());
        add(new BagOfMarbles());
        add(new BagOfPreparation());
        add(new BirdFacedUrn());
        add(new BlackStar());
        add(new BloodVial());
        add(new BloodyIdol());
        add(new BlueCandle());
        add(new Boot());
        add(new BottledFlame());
        add(new BottledLightning());
        add(new BottledTornado());
        add(new BronzeScales());
        add(new BustedCrown());
        add(new Calipers());
        add(new CallingBell());
        add(new CaptainsWheel());
        add(new Cauldron());
        add(new CentennialPuzzle());
        add(new CeramicFish());
        add(new ChemicalX());
        add(new ClockworkSouvenir());
        add(new CoffeeDripper());
        add(new Courier());
        add(new CultistMask());
        add(new CursedKey());
        add(new DarkstonePeriapt());
        add(new DeadBranch());
        add(new DollysMirror());
        add(new DreamCatcher());
        add(new DuVuDoll());
        add(new Ectoplasm());
        add(new EmptyCage());
        add(new Enchiridion());
        add(new EternalFeather());
        add(new FaceOfCleric());
        add(new FossilizedHelix());
        add(new FrozenEgg2());
        add(new FrozenEye());
        add(new FusionHammer());
        add(new GamblingChip());
        add(new Ginger());
        add(new Girya());
        add(new GoldenIdol());
        add(new GremlinHorn());
        add(new GremlinMask());
        add(new HandDrill());
        add(new HappyFlower());
        add(new HornCleat());
        add(new IceCream());
        add(new IncenseBurner());
        add(new InkBottle());
        add(new JuzuBracelet());
        add(new Kunai());
        add(new Lantern());
        add(new LetterOpener());
        add(new LizardTail());
        add(new Mango());
        add(new MarkOfTheBloom());
        add(new Matryoshka());
        add(new MawBank());
        add(new MealTicket());
        add(new MeatOnTheBone());
        add(new MedicalKit());
        add(new MembershipCard());
        add(new MercuryHourglass());
        add(new MoltenEgg2());
        add(new MummifiedHand());
        add(new MutagenicStrength());
        add(new Necronomicon());
        add(new NeowsLament());
        add(new NilrysCodex());
        add(new NlothsGift());
        add(new NlothsMask());
        add(new Nunchaku());
        add(new OddlySmoothStone());
        add(new OddMushroom());
        add(new OldCoin());
        add(new Omamori());
        add(new OrangePellets());
        add(new Orichalcum());
        add(new OrnamentalFan());
        add(new Orrery());
        add(new PandorasBox());
        add(new Pantograph());
        add(new PeacePipe());
        add(new Pear());
        add(new PenNib());
        add(new PhilosopherStone());
        add(new Pocketwatch());
        add(new PotionBelt());
        add(new PrayerWheel());
        add(new PreservedInsect());
        add(new PrismaticShard());
        add(new QuestionCard());
        add(new RedMask());
        add(new RegalPillow());
        add(new RunicDome());
        add(new RunicPyramid());
        add(new SacredBark());
        add(new Shovel());
        add(new Shuriken());
        add(new SingingBowl());
        add(new SlaversCollar());
        add(new Sling());
        add(new SmilingMask());
        add(new SneckoEye());
        add(new Sozu());
        add(new SpiritPoop());
        add(new SsserpentHead());
        add(new StoneCalendar());
        add(new StrangeSpoon());
        add(new Strawberry());
        add(new StrikeDummy());
        add(new Sundial());
        add(new ThreadAndNeedle());
        add(new TinyChest());
        add(new TinyHouse());
        add(new Toolbox());
        add(new Torii());
        add(new ToxicEgg2());
        add(new ToyOrnithopter());
        add(new TungstenRod());
        add(new Turnip());
        add(new UnceasingTop());
        add(new Vajra());
        add(new VelvetChoker());
        add(new Waffle());
        add(new WarPaint());
        add(new WarpedTongs());
        add(new Whetstone());
        add(new WhiteBeast());
        add(new WingBoots());
        addGreen(new HoveringKite());
        addGreen(new NinjaScroll());
        addGreen(new PaperCrane());
        addGreen(new RingOfTheSerpent());
        addGreen(new SnakeRing());
        addGreen(new SneckoSkull());
        addGreen(new TheSpecimen());
        addGreen(new Tingsha());
        addGreen(new ToughBandages());
        addGreen(new TwistedFunnel());
        addGreen(new WristBlade());
        addRed(new BlackBlood());
        addRed(new Brimstone());
        addRed(new BurningBlood());
        addRed(new ChampionsBelt());
        addRed(new CharonsAshes());
        addRed(new MagicFlower());
        addRed(new MarkOfPain());
        addRed(new PaperFrog());
        addRed(new RedSkull());
        addRed(new RunicCube());
        addRed(new SelfFormingClay());
        addBlue(new CrackedCore());
        addBlue(new DataDisk());
        addBlue(new EmotionChip());
        addBlue(new FrozenCore());
        addBlue(new GoldPlatedCables());
        addBlue(new Inserter());
        addBlue(new NuclearBattery());
        addBlue(new RunicCapacitor());
        addBlue(new SymbioticVirus());
        addPurple(new CloakClasp());
        addPurple(new Damaru());
        addPurple(new GoldenEye());
        addPurple(new HolyWater());
        addPurple(new Melange());
        addPurple(new PureWater());
        addPurple(new VioletLotus());
        addPurple(new TeardropLocket());
        addPurple(new Duality());
        if(!Settings.isBeta);
        logger.info((new StringBuilder()).append("Relic load time: ").append(System.currentTimeMillis() - startTime).append("ms").toString());
        sortLists();
    }

    public static void resetForReload()
    {
        totalRelicCount = 0;
        seenRelics = 0;
        sharedRelics.clear();
        redRelics.clear();
        greenRelics.clear();
        blueRelics.clear();
        purpleRelics.clear();
        starterList.clear();
        commonList.clear();
        uncommonList.clear();
        rareList.clear();
        bossList.clear();
        specialList.clear();
        shopList.clear();
        redList.clear();
        greenList.clear();
        blueList.clear();
        whiteList.clear();
    }

    private static void sortLists()
    {
        Collections.sort(starterList);
        Collections.sort(commonList);
        Collections.sort(uncommonList);
        Collections.sort(rareList);
        Collections.sort(bossList);
        Collections.sort(specialList);
        Collections.sort(shopList);
        if(Settings.isDev)
        {
            logger.info(starterList);
            logger.info(commonList);
            logger.info(uncommonList);
            logger.info(rareList);
            logger.info(bossList);
        }
    }

    private static void printRelicsMissingLargeArt()
    {
        int common = 0;
        int uncommon = 0;
        int rare = 0;
        int boss = 0;
        int shop = 0;
        int other = 0;
        logger.info("[ART] START DISPLAYING RELICS WITH MISSING HIGH RES ART");
        Iterator iterator = sharedRelics.entrySet().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            java.util.Map.Entry r = (java.util.Map.Entry)iterator.next();
            AbstractRelic relic = (AbstractRelic)r.getValue();
            if(ImageMaster.loadImage((new StringBuilder()).append("images/largeRelics/").append(relic.imgUrl).toString()) == null)
                logger.info(relic.name);
        } while(true);
    }

    private static void printRelicCount()
    {
        int common = 0;
        int uncommon = 0;
        int rare = 0;
        int boss = 0;
        int shop = 0;
        int other = 0;
        Iterator iterator = sharedRelics.entrySet().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            java.util.Map.Entry r = (java.util.Map.Entry)iterator.next();
            static class _cls1
            {

                static final int $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier[];
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
                    $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier = new int[com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.values().length];
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier[com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.COMMON.ordinal()] = 1;
                    }
                    catch(NoSuchFieldError nosuchfielderror4) { }
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier[com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.UNCOMMON.ordinal()] = 2;
                    }
                    catch(NoSuchFieldError nosuchfielderror5) { }
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier[com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.RARE.ordinal()] = 3;
                    }
                    catch(NoSuchFieldError nosuchfielderror6) { }
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier[com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.BOSS.ordinal()] = 4;
                    }
                    catch(NoSuchFieldError nosuchfielderror7) { }
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier[com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.SHOP.ordinal()] = 5;
                    }
                    catch(NoSuchFieldError nosuchfielderror8) { }
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier[com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.STARTER.ordinal()] = 6;
                    }
                    catch(NoSuchFieldError nosuchfielderror9) { }
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier[com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.SPECIAL.ordinal()] = 7;
                    }
                    catch(NoSuchFieldError nosuchfielderror10) { }
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier[com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.DEPRECATED.ordinal()] = 8;
                    }
                    catch(NoSuchFieldError nosuchfielderror11) { }
                }
            }

            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier[((AbstractRelic)r.getValue()).tier.ordinal()])
            {
            case 1: // '\001'
                common++;
                break;

            case 2: // '\002'
                uncommon++;
                break;

            case 3: // '\003'
                rare++;
                break;

            case 4: // '\004'
                boss++;
                break;

            case 5: // '\005'
                shop++;
                break;

            default:
                other++;
                break;
            }
        } while(true);
        if(Settings.isDev)
        {
            logger.info("RELIC COUNTS");
            logger.info((new StringBuilder()).append("Common: ").append(common).toString());
            logger.info((new StringBuilder()).append("Uncommon: ").append(uncommon).toString());
            logger.info((new StringBuilder()).append("Rare: ").append(rare).toString());
            logger.info((new StringBuilder()).append("Boss: ").append(boss).toString());
            logger.info((new StringBuilder()).append("Shop: ").append(shop).toString());
            logger.info((new StringBuilder()).append("Other: ").append(other).toString());
            logger.info((new StringBuilder()).append("Red: ").append(redRelics.size()).toString());
            logger.info((new StringBuilder()).append("Green: ").append(greenRelics.size()).toString());
            logger.info((new StringBuilder()).append("Blue: ").append(blueRelics.size()).toString());
            logger.info((new StringBuilder()).append("Purple: ").append(purpleRelics.size()).toString());
        }
    }

    public static void add(AbstractRelic relic)
    {
        if(UnlockTracker.isRelicSeen(relic.relicId))
            seenRelics++;
        relic.isSeen = UnlockTracker.isRelicSeen(relic.relicId);
        sharedRelics.put(relic.relicId, relic);
        addToTierList(relic);
        totalRelicCount++;
    }

    public static void addRed(AbstractRelic relic)
    {
        if(UnlockTracker.isRelicSeen(relic.relicId))
            seenRelics++;
        relic.isSeen = UnlockTracker.isRelicSeen(relic.relicId);
        redRelics.put(relic.relicId, relic);
        addToTierList(relic);
        redList.add(relic);
        totalRelicCount++;
    }

    public static void addGreen(AbstractRelic relic)
    {
        if(UnlockTracker.isRelicSeen(relic.relicId))
            seenRelics++;
        relic.isSeen = UnlockTracker.isRelicSeen(relic.relicId);
        greenRelics.put(relic.relicId, relic);
        addToTierList(relic);
        greenList.add(relic);
        totalRelicCount++;
    }

    public static void addBlue(AbstractRelic relic)
    {
        if(UnlockTracker.isRelicSeen(relic.relicId))
            seenRelics++;
        relic.isSeen = UnlockTracker.isRelicSeen(relic.relicId);
        blueRelics.put(relic.relicId, relic);
        addToTierList(relic);
        blueList.add(relic);
        totalRelicCount++;
    }

    public static void addPurple(AbstractRelic relic)
    {
        if(UnlockTracker.isRelicSeen(relic.relicId))
            seenRelics++;
        relic.isSeen = UnlockTracker.isRelicSeen(relic.relicId);
        purpleRelics.put(relic.relicId, relic);
        addToTierList(relic);
        whiteList.add(relic);
        totalRelicCount++;
    }

    public static void addToTierList(AbstractRelic relic)
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier[relic.tier.ordinal()])
        {
        case 6: // '\006'
            starterList.add(relic);
            break;

        case 1: // '\001'
            commonList.add(relic);
            break;

        case 2: // '\002'
            uncommonList.add(relic);
            break;

        case 3: // '\003'
            rareList.add(relic);
            break;

        case 5: // '\005'
            shopList.add(relic);
            break;

        case 7: // '\007'
            specialList.add(relic);
            break;

        case 4: // '\004'
            bossList.add(relic);
            break;

        case 8: // '\b'
            logger.info((new StringBuilder()).append(relic.relicId).append(" is deprecated.").toString());
            break;

        default:
            logger.info((new StringBuilder()).append(relic.relicId).append(" is undefined tier.").toString());
            break;
        }
    }

    public static AbstractRelic getRelic(String key)
    {
        if(sharedRelics.containsKey(key))
            return (AbstractRelic)sharedRelics.get(key);
        if(redRelics.containsKey(key))
            return (AbstractRelic)redRelics.get(key);
        if(greenRelics.containsKey(key))
            return (AbstractRelic)greenRelics.get(key);
        if(blueRelics.containsKey(key))
            return (AbstractRelic)blueRelics.get(key);
        if(purpleRelics.containsKey(key))
            return (AbstractRelic)purpleRelics.get(key);
        else
            return new Circlet();
    }

    public static boolean isARelic(String key)
    {
        return sharedRelics.containsKey(key) || redRelics.containsKey(key) || greenRelics.containsKey(key) || blueRelics.containsKey(key) || purpleRelics.containsKey(key);
    }

    public static void populateRelicPool(ArrayList pool, com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier tier, com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass c)
    {
        Iterator iterator = sharedRelics.entrySet().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            java.util.Map.Entry r = (java.util.Map.Entry)iterator.next();
            if(((AbstractRelic)r.getValue()).tier == tier && (!UnlockTracker.isRelicLocked((String)r.getKey()) || Settings.treatEverythingAsUnlocked()))
                pool.add(r.getKey());
        } while(true);
label0:
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass[c.ordinal()])
        {
        default:
            break;

        case 1: // '\001'
            Iterator iterator1 = redRelics.entrySet().iterator();
            do
            {
                if(!iterator1.hasNext())
                    break;
                java.util.Map.Entry r = (java.util.Map.Entry)iterator1.next();
                if(((AbstractRelic)r.getValue()).tier == tier && (!UnlockTracker.isRelicLocked((String)r.getKey()) || Settings.treatEverythingAsUnlocked()))
                    pool.add(r.getKey());
            } while(true);
            break;

        case 2: // '\002'
            Iterator iterator2 = greenRelics.entrySet().iterator();
            do
            {
                java.util.Map.Entry r;
                do
                {
                    if(!iterator2.hasNext())
                        break label0;
                    r = (java.util.Map.Entry)iterator2.next();
                } while(((AbstractRelic)r.getValue()).tier != tier || UnlockTracker.isRelicLocked((String)r.getKey()) && !Settings.treatEverythingAsUnlocked());
                pool.add(r.getKey());
            } while(true);

        case 3: // '\003'
            Iterator iterator3 = blueRelics.entrySet().iterator();
            do
            {
                java.util.Map.Entry r;
                do
                {
                    if(!iterator3.hasNext())
                        break label0;
                    r = (java.util.Map.Entry)iterator3.next();
                } while(((AbstractRelic)r.getValue()).tier != tier || UnlockTracker.isRelicLocked((String)r.getKey()) && !Settings.treatEverythingAsUnlocked());
                pool.add(r.getKey());
            } while(true);

        case 4: // '\004'
            Iterator iterator4 = purpleRelics.entrySet().iterator();
            do
            {
                java.util.Map.Entry r;
                do
                {
                    if(!iterator4.hasNext())
                        break label0;
                    r = (java.util.Map.Entry)iterator4.next();
                } while(((AbstractRelic)r.getValue()).tier != tier || UnlockTracker.isRelicLocked((String)r.getKey()) && !Settings.treatEverythingAsUnlocked());
                pool.add(r.getKey());
            } while(true);
        }
    }

    public static void addSharedRelics(ArrayList relicPool)
    {
        if(Settings.isDev)
            logger.info((new StringBuilder()).append("[RELIC] Adding ").append(sharedRelics.size()).append(" shared relics...").toString());
        java.util.Map.Entry r;
        for(Iterator iterator = sharedRelics.entrySet().iterator(); iterator.hasNext(); relicPool.add(r.getValue()))
            r = (java.util.Map.Entry)iterator.next();

    }

    public static void addClassSpecificRelics(ArrayList relicPool)
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass[AbstractDungeon.player.chosenClass.ordinal()])
        {
        default:
            break;

        case 1: // '\001'
            if(Settings.isDev)
                logger.info((new StringBuilder()).append("[RELIC] Adding ").append(redRelics.size()).append(" red relics...").toString());
            java.util.Map.Entry r;
            for(Iterator iterator = redRelics.entrySet().iterator(); iterator.hasNext(); relicPool.add(r.getValue()))
                r = (java.util.Map.Entry)iterator.next();

            break;

        case 2: // '\002'
            if(Settings.isDev)
                logger.info((new StringBuilder()).append("[RELIC] Adding ").append(greenRelics.size()).append(" green relics...").toString());
            java.util.Map.Entry r;
            for(Iterator iterator1 = greenRelics.entrySet().iterator(); iterator1.hasNext(); relicPool.add(r.getValue()))
                r = (java.util.Map.Entry)iterator1.next();

            break;

        case 3: // '\003'
            if(Settings.isDev)
                logger.info((new StringBuilder()).append("[RELIC] Adding ").append(blueRelics.size()).append(" blue relics...").toString());
            java.util.Map.Entry r;
            for(Iterator iterator2 = blueRelics.entrySet().iterator(); iterator2.hasNext(); relicPool.add(r.getValue()))
                r = (java.util.Map.Entry)iterator2.next();

            break;

        case 4: // '\004'
            if(Settings.isDev)
                logger.info((new StringBuilder()).append("[RELIC] Adding ").append(purpleRelics.size()).append(" purple relics...").toString());
            java.util.Map.Entry r;
            for(Iterator iterator3 = purpleRelics.entrySet().iterator(); iterator3.hasNext(); relicPool.add(r.getValue()))
                r = (java.util.Map.Entry)iterator3.next();

            break;
        }
    }

    public static void uploadRelicData()
    {
        ArrayList data = new ArrayList();
        java.util.Map.Entry r;
        for(Iterator iterator = sharedRelics.entrySet().iterator(); iterator.hasNext(); data.add(((AbstractRelic)r.getValue()).gameDataUploadData("All")))
            r = (java.util.Map.Entry)iterator.next();

        java.util.Map.Entry r;
        for(Iterator iterator1 = redRelics.entrySet().iterator(); iterator1.hasNext(); data.add(((AbstractRelic)r.getValue()).gameDataUploadData("Red")))
            r = (java.util.Map.Entry)iterator1.next();

        java.util.Map.Entry r;
        for(Iterator iterator2 = greenRelics.entrySet().iterator(); iterator2.hasNext(); data.add(((AbstractRelic)r.getValue()).gameDataUploadData("Green")))
            r = (java.util.Map.Entry)iterator2.next();

        java.util.Map.Entry r;
        for(Iterator iterator3 = blueRelics.entrySet().iterator(); iterator3.hasNext(); data.add(((AbstractRelic)r.getValue()).gameDataUploadData("Blue")))
            r = (java.util.Map.Entry)iterator3.next();

        java.util.Map.Entry r;
        for(Iterator iterator4 = purpleRelics.entrySet().iterator(); iterator4.hasNext(); data.add(((AbstractRelic)r.getValue()).gameDataUploadData("Purple")))
            r = (java.util.Map.Entry)iterator4.next();

        BotDataUploader.uploadDataAsync(com.megacrit.cardcrawl.metrics.BotDataUploader.GameDataType.RELIC_DATA, AbstractRelic.gameDataUploadHeader(), data);
    }

    public static ArrayList sortByName(ArrayList group, boolean ascending)
    {
        ArrayList tmp = new ArrayList();
        int addIndex;
        AbstractRelic r;
label0:
        for(Iterator iterator = group.iterator(); iterator.hasNext(); tmp.add(addIndex, r))
        {
            r = (AbstractRelic)iterator.next();
            addIndex = 0;
            Iterator iterator1 = tmp.iterator();
            do
            {
                if(!iterator1.hasNext())
                    continue label0;
                AbstractRelic r2 = (AbstractRelic)iterator1.next();
                if(ascending ? r.name.compareTo(r2.name) > 0 : r.name.compareTo(r2.name) < 0)
                    continue label0;
                addIndex++;
            } while(true);
        }

        return tmp;
    }

    public static ArrayList sortByStatus(ArrayList group, boolean ascending)
    {
        ArrayList tmp = new ArrayList();
        int addIndex;
        AbstractRelic r;
        for(Iterator iterator = group.iterator(); iterator.hasNext(); tmp.add(addIndex, r))
        {
            r = (AbstractRelic)iterator.next();
            addIndex = 0;
            for(Iterator iterator1 = tmp.iterator(); iterator1.hasNext(); addIndex++)
            {
                AbstractRelic r2 = (AbstractRelic)iterator1.next();
                String a;
                String b;
                if(!ascending)
                {
                    if(UnlockTracker.isRelicLocked(r.relicId))
                        a = "LOCKED";
                    else
                    if(UnlockTracker.isRelicSeen(r.relicId))
                        a = "UNSEEN";
                    else
                        a = "SEEN";
                    if(UnlockTracker.isRelicLocked(r2.relicId))
                        b = "LOCKED";
                    else
                    if(UnlockTracker.isRelicSeen(r2.relicId))
                        b = "UNSEEN";
                    else
                        b = "SEEN";
                    if(a.compareTo(b) > 0)
                        break;
                    continue;
                }
                if(UnlockTracker.isRelicLocked(r.relicId))
                    a = "LOCKED";
                else
                if(UnlockTracker.isRelicSeen(r.relicId))
                    a = "UNSEEN";
                else
                    a = "SEEN";
                if(UnlockTracker.isRelicLocked(r2.relicId))
                    b = "LOCKED";
                else
                if(UnlockTracker.isRelicSeen(r2.relicId))
                    b = "UNSEEN";
                else
                    b = "SEEN";
                if(a.compareTo(b) < 0)
                    break;
            }

        }

        return tmp;
    }

    public static void unlockAndSeeAllRelics()
    {
        String s;
        for(Iterator iterator = UnlockTracker.lockedRelics.iterator(); iterator.hasNext(); UnlockTracker.hardUnlockOverride(s))
            s = (String)iterator.next();

        java.util.Map.Entry r;
        for(Iterator iterator1 = sharedRelics.entrySet().iterator(); iterator1.hasNext(); UnlockTracker.markRelicAsSeen((String)r.getKey()))
            r = (java.util.Map.Entry)iterator1.next();

        java.util.Map.Entry r;
        for(Iterator iterator2 = redRelics.entrySet().iterator(); iterator2.hasNext(); UnlockTracker.markRelicAsSeen((String)r.getKey()))
            r = (java.util.Map.Entry)iterator2.next();

        java.util.Map.Entry r;
        for(Iterator iterator3 = greenRelics.entrySet().iterator(); iterator3.hasNext(); UnlockTracker.markRelicAsSeen((String)r.getKey()))
            r = (java.util.Map.Entry)iterator3.next();

        java.util.Map.Entry r;
        for(Iterator iterator4 = blueRelics.entrySet().iterator(); iterator4.hasNext(); UnlockTracker.markRelicAsSeen((String)r.getKey()))
            r = (java.util.Map.Entry)iterator4.next();

        java.util.Map.Entry r;
        for(Iterator iterator5 = purpleRelics.entrySet().iterator(); iterator5.hasNext(); UnlockTracker.markRelicAsSeen((String)r.getKey()))
            r = (java.util.Map.Entry)iterator5.next();

    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/helpers/RelicLibrary.getName());
    public static int totalRelicCount = 0;
    public static int seenRelics = 0;
    private static HashMap sharedRelics = new HashMap();
    private static HashMap redRelics = new HashMap();
    private static HashMap greenRelics = new HashMap();
    private static HashMap blueRelics = new HashMap();
    private static HashMap purpleRelics = new HashMap();
    public static ArrayList starterList = new ArrayList();
    public static ArrayList commonList = new ArrayList();
    public static ArrayList uncommonList = new ArrayList();
    public static ArrayList rareList = new ArrayList();
    public static ArrayList bossList = new ArrayList();
    public static ArrayList specialList = new ArrayList();
    public static ArrayList shopList = new ArrayList();
    public static ArrayList redList = new ArrayList();
    public static ArrayList greenList = new ArrayList();
    public static ArrayList blueList = new ArrayList();
    public static ArrayList whiteList = new ArrayList();

}
