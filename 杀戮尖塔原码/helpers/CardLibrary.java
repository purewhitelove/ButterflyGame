// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CardLibrary.java

package com.megacrit.cardcrawl.helpers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.blue.*;
import com.megacrit.cardcrawl.cards.colorless.*;
import com.megacrit.cardcrawl.cards.curses.*;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.cards.optionCards.*;
import com.megacrit.cardcrawl.cards.purple.*;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.cards.status.*;
import com.megacrit.cardcrawl.cards.tempCards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.metrics.BotDataUploader;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import java.io.PrintStream;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.helpers:
//            ImageMaster

public class CardLibrary
{
    public static final class LibraryType extends Enum
    {

        public static LibraryType[] values()
        {
            return (LibraryType[])$VALUES.clone();
        }

        public static LibraryType valueOf(String name)
        {
            return (LibraryType)Enum.valueOf(com/megacrit/cardcrawl/helpers/CardLibrary$LibraryType, name);
        }

        public static final LibraryType RED;
        public static final LibraryType GREEN;
        public static final LibraryType BLUE;
        public static final LibraryType PURPLE;
        public static final LibraryType CURSE;
        public static final LibraryType COLORLESS;
        private static final LibraryType $VALUES[];

        static 
        {
            RED = new LibraryType("RED", 0);
            GREEN = new LibraryType("GREEN", 1);
            BLUE = new LibraryType("BLUE", 2);
            PURPLE = new LibraryType("PURPLE", 3);
            CURSE = new LibraryType("CURSE", 4);
            COLORLESS = new LibraryType("COLORLESS", 5);
            $VALUES = (new LibraryType[] {
                RED, GREEN, BLUE, PURPLE, CURSE, COLORLESS
            });
        }

        private LibraryType(String s, int i)
        {
            super(s, i);
        }
    }


    public CardLibrary()
    {
    }

    public static void initialize()
    {
        long startTime = System.currentTimeMillis();
        addRedCards();
        addGreenCards();
        addBlueCards();
        addPurpleCards();
        addColorlessCards();
        addCurseCards();
        if(!Settings.isDev);
        logger.info((new StringBuilder()).append("Card load time: ").append(System.currentTimeMillis() - startTime).append("ms with ").append(cards.size()).append(" cards").toString());
        if(Settings.isDev)
        {
            logger.info((new StringBuilder()).append("[INFO] Red Cards: \t").append(redCards).toString());
            logger.info((new StringBuilder()).append("[INFO] Green Cards: \t").append(greenCards).toString());
            logger.info((new StringBuilder()).append("[INFO] Blue Cards: \t").append(blueCards).toString());
            logger.info((new StringBuilder()).append("[INFO] Purple Cards: \t").append(purpleCards).toString());
            logger.info((new StringBuilder()).append("[INFO] Colorless Cards: \t").append(colorlessCards).toString());
            logger.info((new StringBuilder()).append("[INFO] Curse Cards: \t").append(curseCards).toString());
            logger.info((new StringBuilder()).append("[INFO] Total Cards: \t").append(redCards + greenCards + blueCards + purpleCards + colorlessCards + curseCards).toString());
        }
    }

    public static void resetForReload()
    {
        cards = new HashMap();
        curses = new HashMap();
        totalCardCount = 0;
        redCards = 0;
        greenCards = 0;
        blueCards = 0;
        purpleCards = 0;
        colorlessCards = 0;
        curseCards = 0;
        seenRedCards = 0;
        seenGreenCards = 0;
        seenBlueCards = 0;
        seenPurpleCards = 0;
        seenColorlessCards = 0;
        seenCurseCards = 0;
    }

    private static void addRedCards()
    {
        add(new Anger());
        add(new Armaments());
        add(new Barricade());
        add(new Bash());
        add(new BattleTrance());
        add(new Berserk());
        add(new BloodForBlood());
        add(new Bloodletting());
        add(new Bludgeon());
        add(new BodySlam());
        add(new Brutality());
        add(new BurningPact());
        add(new Carnage());
        add(new Clash());
        add(new Cleave());
        add(new Clothesline());
        add(new Combust());
        add(new Corruption());
        add(new DarkEmbrace());
        add(new Defend_Red());
        add(new DemonForm());
        add(new Disarm());
        add(new DoubleTap());
        add(new Dropkick());
        add(new DualWield());
        add(new Entrench());
        add(new Evolve());
        add(new Exhume());
        add(new Feed());
        add(new FeelNoPain());
        add(new FiendFire());
        add(new FireBreathing());
        add(new FlameBarrier());
        add(new Flex());
        add(new GhostlyArmor());
        add(new Havoc());
        add(new Headbutt());
        add(new HeavyBlade());
        add(new Hemokinesis());
        add(new Immolate());
        add(new Impervious());
        add(new InfernalBlade());
        add(new Inflame());
        add(new Intimidate());
        add(new IronWave());
        add(new Juggernaut());
        add(new LimitBreak());
        add(new Metallicize());
        add(new Offering());
        add(new PerfectedStrike());
        add(new PommelStrike());
        add(new PowerThrough());
        add(new Pummel());
        add(new Rage());
        add(new Rampage());
        add(new Reaper());
        add(new RecklessCharge());
        add(new Rupture());
        add(new SearingBlow());
        add(new SecondWind());
        add(new SeeingRed());
        add(new Sentinel());
        add(new SeverSoul());
        add(new Shockwave());
        add(new ShrugItOff());
        add(new SpotWeakness());
        add(new Strike_Red());
        add(new SwordBoomerang());
        add(new ThunderClap());
        add(new TrueGrit());
        add(new TwinStrike());
        add(new Uppercut());
        add(new Warcry());
        add(new Whirlwind());
        add(new WildStrike());
    }

    private static void addGreenCards()
    {
        add(new Accuracy());
        add(new Acrobatics());
        add(new Adrenaline());
        add(new AfterImage());
        add(new Alchemize());
        add(new AllOutAttack());
        add(new AThousandCuts());
        add(new Backflip());
        add(new Backstab());
        add(new Bane());
        add(new BladeDance());
        add(new Blur());
        add(new BouncingFlask());
        add(new BulletTime());
        add(new Burst());
        add(new CalculatedGamble());
        add(new Caltrops());
        add(new Catalyst());
        add(new Choke());
        add(new CloakAndDagger());
        add(new Concentrate());
        add(new CorpseExplosion());
        add(new CripplingPoison());
        add(new DaggerSpray());
        add(new DaggerThrow());
        add(new Dash());
        add(new DeadlyPoison());
        add(new Defend_Green());
        add(new Deflect());
        add(new DieDieDie());
        add(new Distraction());
        add(new DodgeAndRoll());
        add(new Doppelganger());
        add(new EndlessAgony());
        add(new Envenom());
        add(new EscapePlan());
        add(new Eviscerate());
        add(new Expertise());
        add(new Finisher());
        add(new Flechettes());
        add(new FlyingKnee());
        add(new Footwork());
        add(new GlassKnife());
        add(new GrandFinale());
        add(new HeelHook());
        add(new InfiniteBlades());
        add(new LegSweep());
        add(new Malaise());
        add(new MasterfulStab());
        add(new Neutralize());
        add(new Nightmare());
        add(new NoxiousFumes());
        add(new Outmaneuver());
        add(new PhantasmalKiller());
        add(new PiercingWail());
        add(new PoisonedStab());
        add(new Predator());
        add(new Prepared());
        add(new QuickSlash());
        add(new Reflex());
        add(new RiddleWithHoles());
        add(new Setup());
        add(new Skewer());
        add(new Slice());
        add(new StormOfSteel());
        add(new Strike_Green());
        add(new SuckerPunch());
        add(new Survivor());
        add(new Tactician());
        add(new Terror());
        add(new ToolsOfTheTrade());
        add(new SneakyStrike());
        add(new Unload());
        add(new WellLaidPlans());
        add(new WraithForm());
    }

    private static void addBlueCards()
    {
        add(new Aggregate());
        add(new AllForOne());
        add(new Amplify());
        add(new AutoShields());
        add(new BallLightning());
        add(new Barrage());
        add(new BeamCell());
        add(new BiasedCognition());
        add(new Blizzard());
        add(new BootSequence());
        add(new Buffer());
        add(new Capacitor());
        add(new Chaos());
        add(new Chill());
        add(new Claw());
        add(new ColdSnap());
        add(new CompileDriver());
        add(new ConserveBattery());
        add(new Consume());
        add(new Coolheaded());
        add(new CoreSurge());
        add(new CreativeAI());
        add(new Darkness());
        add(new Defend_Blue());
        add(new Defragment());
        add(new DoomAndGloom());
        add(new DoubleEnergy());
        add(new Dualcast());
        add(new EchoForm());
        add(new Electrodynamics());
        add(new Fission());
        add(new ForceField());
        add(new FTL());
        add(new Fusion());
        add(new GeneticAlgorithm());
        add(new Glacier());
        add(new GoForTheEyes());
        add(new Heatsinks());
        add(new HelloWorld());
        add(new Hologram());
        add(new Hyperbeam());
        add(new Leap());
        add(new LockOn());
        add(new Loop());
        add(new MachineLearning());
        add(new Melter());
        add(new MeteorStrike());
        add(new MultiCast());
        add(new Overclock());
        add(new Rainbow());
        add(new Reboot());
        add(new Rebound());
        add(new Recursion());
        add(new Recycle());
        add(new ReinforcedBody());
        add(new Reprogram());
        add(new RipAndTear());
        add(new Scrape());
        add(new Seek());
        add(new SelfRepair());
        add(new Skim());
        add(new Stack());
        add(new StaticDischarge());
        add(new SteamBarrier());
        add(new Storm());
        add(new Streamline());
        add(new Strike_Blue());
        add(new Sunder());
        add(new SweepingBeam());
        add(new Tempest());
        add(new ThunderStrike());
        add(new Turbo());
        add(new Equilibrium());
        add(new WhiteNoise());
        add(new Zap());
    }

    private static void addPurpleCards()
    {
        add(new Alpha());
        add(new BattleHymn());
        add(new Blasphemy());
        add(new BowlingBash());
        add(new Brilliance());
        add(new CarveReality());
        add(new Collect());
        add(new Conclude());
        add(new ConjureBlade());
        add(new Consecrate());
        add(new Crescendo());
        add(new CrushJoints());
        add(new CutThroughFate());
        add(new DeceiveReality());
        add(new Defend_Watcher());
        add(new DeusExMachina());
        add(new DevaForm());
        add(new Devotion());
        add(new EmptyBody());
        add(new EmptyFist());
        add(new EmptyMind());
        add(new Eruption());
        add(new Establishment());
        add(new Evaluate());
        add(new Fasting());
        add(new FearNoEvil());
        add(new FlurryOfBlows());
        add(new FlyingSleeves());
        add(new FollowUp());
        add(new ForeignInfluence());
        add(new Foresight());
        add(new Halt());
        add(new Indignation());
        add(new InnerPeace());
        add(new Judgement());
        add(new JustLucky());
        add(new LessonLearned());
        add(new LikeWater());
        add(new MasterReality());
        add(new Meditate());
        add(new MentalFortress());
        add(new Nirvana());
        add(new Omniscience());
        add(new Perseverance());
        add(new Pray());
        add(new PressurePoints());
        add(new Prostrate());
        add(new Protect());
        add(new Ragnarok());
        add(new ReachHeaven());
        add(new Rushdown());
        add(new Sanctity());
        add(new SandsOfTime());
        add(new SashWhip());
        add(new Scrawl());
        add(new SignatureMove());
        add(new SimmeringFury());
        add(new SpiritShield());
        add(new Strike_Purple());
        add(new Study());
        add(new Swivel());
        add(new TalkToTheHand());
        add(new Tantrum());
        add(new ThirdEye());
        add(new Tranquility());
        add(new Vault());
        add(new Vigilance());
        add(new Wallop());
        add(new WaveOfTheHand());
        add(new Weave());
        add(new WheelKick());
        add(new WindmillStrike());
        add(new Wish());
        add(new Worship());
        add(new WreathOfFlame());
    }

    private static void printMissingPortraitInfo()
    {
        Iterator iterator = cards.entrySet().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            java.util.Map.Entry c = (java.util.Map.Entry)iterator.next();
            AbstractCard card = (AbstractCard)c.getValue();
            if(card.jokePortrait == null)
                System.out.println((new StringBuilder()).append(card.name).append(";").append(card.color.name()).append(";").append(card.type.name()).toString());
        } while(true);
        iterator = cards.entrySet().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            java.util.Map.Entry c = (java.util.Map.Entry)iterator.next();
            AbstractCard card = (AbstractCard)c.getValue();
            if(ImageMaster.loadImage((new StringBuilder()).append("images/1024PortraitsBeta/").append(card.assetUrl).append(".png").toString()) == null)
                System.out.println((new StringBuilder()).append("[INFO] ").append(card.name).append(" missing LARGE beta portrait.").toString());
        } while(true);
    }

    private static void printBlueCards(com.megacrit.cardcrawl.cards.AbstractCard.CardColor color)
    {
        Iterator iterator = cards.entrySet().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            java.util.Map.Entry c = (java.util.Map.Entry)iterator.next();
            if(((AbstractCard)c.getValue()).color == color)
            {
                AbstractCard card = (AbstractCard)c.getValue();
                System.out.println((new StringBuilder()).append(card.originalName).append("; ").append(card.type.toString()).append("; ").append(card.rarity.toString()).append("; ").append(card.cost).append("; ").append(card.rawDescription).toString());
            }
        } while(true);
    }

    private static void addColorlessCards()
    {
        add(new Apotheosis());
        add(new BandageUp());
        add(new Blind());
        add(new Chrysalis());
        add(new DarkShackles());
        add(new DeepBreath());
        add(new Discovery());
        add(new DramaticEntrance());
        add(new Enlightenment());
        add(new Finesse());
        add(new FlashOfSteel());
        add(new Forethought());
        add(new GoodInstincts());
        add(new HandOfGreed());
        add(new Impatience());
        add(new JackOfAllTrades());
        add(new Madness());
        add(new Magnetism());
        add(new MasterOfStrategy());
        add(new Mayhem());
        add(new Metamorphosis());
        add(new MindBlast());
        add(new Panacea());
        add(new Panache());
        add(new PanicButton());
        add(new Purity());
        add(new SadisticNature());
        add(new SecretTechnique());
        add(new SecretWeapon());
        add(new SwiftStrike());
        add(new TheBomb());
        add(new ThinkingAhead());
        add(new Transmutation());
        add(new Trip());
        add(new Violence());
        add(new Burn());
        add(new Dazed());
        add(new Slimed());
        add(new VoidCard());
        add(new Wound());
        add(new Apparition());
        add(new Beta());
        add(new Bite());
        add(new JAX());
        add(new Insight());
        add(new Miracle());
        add(new Omega());
        add(new RitualDagger());
        add(new Safety());
        add(new Shiv());
        add(new Smite());
        add(new ThroughViolence());
        add(new BecomeAlmighty());
        add(new FameAndFortune());
        add(new LiveForever());
        add(new Expunger());
    }

    private static void addCurseCards()
    {
        add(new AscendersBane());
        add(new CurseOfTheBell());
        add(new Clumsy());
        add(new Decay());
        add(new Doubt());
        add(new Injury());
        add(new Necronomicurse());
        add(new Normality());
        add(new Pain());
        add(new Parasite());
        add(new Pride());
        add(new Regret());
        add(new Shame());
        add(new Writhe());
    }

    private static void removeNonFinalizedCards()
    {
        ArrayList toRemove = new ArrayList();
        Iterator iterator = cards.entrySet().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            java.util.Map.Entry c = (java.util.Map.Entry)iterator.next();
            if(((AbstractCard)c.getValue()).assetUrl == null)
                toRemove.add(c.getKey());
        } while(true);
        String s;
        for(iterator = toRemove.iterator(); iterator.hasNext(); cards.remove(s))
        {
            s = (String)iterator.next();
            logger.info((new StringBuilder()).append("Removing Card ").append(s).append(" for trailer build.").toString());
        }

        toRemove.clear();
        iterator = curses.entrySet().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            java.util.Map.Entry c = (java.util.Map.Entry)iterator.next();
            if(((AbstractCard)c.getValue()).assetUrl == null)
                toRemove.add(c.getKey());
        } while(true);
        String s;
        for(Iterator iterator1 = toRemove.iterator(); iterator1.hasNext(); curses.remove(s))
        {
            s = (String)iterator1.next();
            logger.info((new StringBuilder()).append("Removing Curse ").append(s).append(" for trailer build.").toString());
        }

    }

    public static void unlockAndSeeAllCards()
    {
        String s;
        for(Iterator iterator = UnlockTracker.lockedCards.iterator(); iterator.hasNext(); UnlockTracker.hardUnlockOverride(s))
            s = (String)iterator.next();

        Iterator iterator1 = cards.entrySet().iterator();
        do
        {
            if(!iterator1.hasNext())
                break;
            java.util.Map.Entry c = (java.util.Map.Entry)iterator1.next();
            if(((AbstractCard)c.getValue()).rarity != com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.BASIC && !UnlockTracker.isCardSeen((String)c.getKey()))
                UnlockTracker.markCardAsSeen((String)c.getKey());
        } while(true);
        iterator1 = curses.entrySet().iterator();
        do
        {
            if(!iterator1.hasNext())
                break;
            java.util.Map.Entry c = (java.util.Map.Entry)iterator1.next();
            if(!UnlockTracker.isCardSeen((String)c.getKey()))
                UnlockTracker.markCardAsSeen((String)c.getKey());
        } while(true);
    }

    public static void add(AbstractCard card)
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardColor[];
            static final int $SwitchMap$com$megacrit$cardcrawl$helpers$CardLibrary$LibraryType[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$helpers$CardLibrary$LibraryType = new int[LibraryType.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$helpers$CardLibrary$LibraryType[LibraryType.COLORLESS.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$helpers$CardLibrary$LibraryType[LibraryType.CURSE.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$helpers$CardLibrary$LibraryType[LibraryType.RED.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$helpers$CardLibrary$LibraryType[LibraryType.GREEN.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$helpers$CardLibrary$LibraryType[LibraryType.BLUE.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$helpers$CardLibrary$LibraryType[LibraryType.PURPLE.ordinal()] = 6;
                }
                catch(NoSuchFieldError nosuchfielderror5) { }
                $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardColor = new int[com.megacrit.cardcrawl.cards.AbstractCard.CardColor.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardColor[com.megacrit.cardcrawl.cards.AbstractCard.CardColor.RED.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror6) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardColor[com.megacrit.cardcrawl.cards.AbstractCard.CardColor.GREEN.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror7) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardColor[com.megacrit.cardcrawl.cards.AbstractCard.CardColor.PURPLE.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror8) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardColor[com.megacrit.cardcrawl.cards.AbstractCard.CardColor.BLUE.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror9) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardColor[com.megacrit.cardcrawl.cards.AbstractCard.CardColor.COLORLESS.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror10) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardColor[com.megacrit.cardcrawl.cards.AbstractCard.CardColor.CURSE.ordinal()] = 6;
                }
                catch(NoSuchFieldError nosuchfielderror11) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardColor[card.color.ordinal()])
        {
        case 1: // '\001'
            redCards++;
            if(UnlockTracker.isCardSeen(card.cardID))
                seenRedCards++;
            break;

        case 2: // '\002'
            greenCards++;
            if(UnlockTracker.isCardSeen(card.cardID))
                seenGreenCards++;
            break;

        case 3: // '\003'
            purpleCards++;
            if(UnlockTracker.isCardSeen(card.cardID))
                seenPurpleCards++;
            break;

        case 4: // '\004'
            blueCards++;
            if(UnlockTracker.isCardSeen(card.cardID))
                seenBlueCards++;
            break;

        case 5: // '\005'
            colorlessCards++;
            if(UnlockTracker.isCardSeen(card.cardID))
                seenColorlessCards++;
            break;

        case 6: // '\006'
            curseCards++;
            if(UnlockTracker.isCardSeen(card.cardID))
                seenCurseCards++;
            curses.put(card.cardID, card);
            break;
        }
        if(!UnlockTracker.isCardSeen(card.cardID))
            card.isSeen = false;
        cards.put(card.cardID, card);
        totalCardCount++;
    }

    public static AbstractCard getCopy(String key, int upgradeTime, int misc)
    {
        AbstractCard source = getCard(key);
        AbstractCard retVal = null;
        if(source == null)
            retVal = getCard("Madness").makeCopy();
        else
            retVal = getCard(key).makeCopy();
        for(int i = 0; i < upgradeTime; i++)
            retVal.upgrade();

        retVal.misc = misc;
        if(misc != 0)
        {
            if(retVal.cardID.equals("Genetic Algorithm"))
            {
                retVal.block = misc;
                retVal.baseBlock = misc;
                retVal.initializeDescription();
            }
            if(retVal.cardID.equals("RitualDagger"))
            {
                retVal.damage = misc;
                retVal.baseDamage = misc;
                retVal.initializeDescription();
            }
        }
        return retVal;
    }

    public static AbstractCard getCopy(String key)
    {
        return getCard(key).makeCopy();
    }

    public static AbstractCard getCard(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass plyrClass, String key)
    {
        return (AbstractCard)cards.get(key);
    }

    public static AbstractCard getCard(String key)
    {
        return (AbstractCard)cards.get(key);
    }

    public static String getCardNameFromMetricID(String metricID)
    {
        String components[];
        AbstractCard card;
        components = metricID.split("\\+");
        String baseId = components[0];
        card = (AbstractCard)cards.getOrDefault(baseId, null);
        if(card == null)
            return metricID;
        try
        {
            if(components.length > 1)
            {
                card = card.makeCopy();
                int upgrades = Integer.parseInt(components[1]);
                for(int i = 0; i < upgrades; i++)
                    card.upgrade();

            }
        }
        catch(Object obj) { }
        return card.name;
    }

    public static boolean isACard(String metricID)
    {
        String components[] = metricID.split("\\+");
        String baseId = components[0];
        AbstractCard card = (AbstractCard)cards.getOrDefault(baseId, null);
        return card != null;
    }

    public static AbstractCard getCurse()
    {
        ArrayList tmp = new ArrayList();
        Iterator iterator = curses.entrySet().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            java.util.Map.Entry c = (java.util.Map.Entry)iterator.next();
            if(!((AbstractCard)c.getValue()).cardID.equals("AscendersBane") && !((AbstractCard)c.getValue()).cardID.equals("Necronomicurse") && !((AbstractCard)c.getValue()).cardID.equals("CurseOfTheBell") && !((AbstractCard)c.getValue()).cardID.equals("Pride"))
                tmp.add(c.getKey());
        } while(true);
        return (AbstractCard)cards.get(tmp.get(AbstractDungeon.cardRng.random(0, tmp.size() - 1)));
    }

    public static AbstractCard getCurse(AbstractCard prohibitedCard, Random rng)
    {
        ArrayList tmp = new ArrayList();
        Iterator iterator = curses.entrySet().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            java.util.Map.Entry c = (java.util.Map.Entry)iterator.next();
            if(!Objects.equals(((AbstractCard)c.getValue()).cardID, prohibitedCard.cardID) && !Objects.equals(((AbstractCard)c.getValue()).cardID, "Necronomicurse") && !Objects.equals(((AbstractCard)c.getValue()).cardID, "AscendersBane") && !Objects.equals(((AbstractCard)c.getValue()).cardID, "CurseOfTheBell") && !Objects.equals(((AbstractCard)c.getValue()).cardID, "Pride"))
                tmp.add(c.getKey());
        } while(true);
        return (AbstractCard)cards.get(tmp.get(rng.random(0, tmp.size() - 1)));
    }

    public static AbstractCard getCurse(AbstractCard prohibitedCard)
    {
        return getCurse(prohibitedCard, new Random());
    }

    public static void uploadCardData()
    {
        ArrayList data = new ArrayList();
        Iterator iterator = cards.entrySet().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            java.util.Map.Entry c = (java.util.Map.Entry)iterator.next();
            data.add(((AbstractCard)c.getValue()).gameDataUploadData());
            AbstractCard c2 = ((AbstractCard)c.getValue()).makeCopy();
            if(c2.canUpgrade())
            {
                c2.upgrade();
                data.add(c2.gameDataUploadData());
            }
        } while(true);
        BotDataUploader.uploadDataAsync(com.megacrit.cardcrawl.metrics.BotDataUploader.GameDataType.CARD_DATA, AbstractCard.gameDataUploadHeader(), data);
    }

    public static ArrayList getAllCards()
    {
        ArrayList retVal = new ArrayList();
        java.util.Map.Entry c;
        for(Iterator iterator = cards.entrySet().iterator(); iterator.hasNext(); retVal.add(c.getValue()))
            c = (java.util.Map.Entry)iterator.next();

        return retVal;
    }

    public static AbstractCard getAnyColorCard(com.megacrit.cardcrawl.cards.AbstractCard.CardType type, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity rarity)
    {
        CardGroup anyCard = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);
        Iterator iterator = cards.entrySet().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            java.util.Map.Entry c = (java.util.Map.Entry)iterator.next();
            if(((AbstractCard)c.getValue()).rarity == rarity && !((AbstractCard)c.getValue()).hasTag(com.megacrit.cardcrawl.cards.AbstractCard.CardTags.HEALING) && ((AbstractCard)c.getValue()).type != com.megacrit.cardcrawl.cards.AbstractCard.CardType.CURSE && ((AbstractCard)c.getValue()).type != com.megacrit.cardcrawl.cards.AbstractCard.CardType.STATUS && ((AbstractCard)c.getValue()).type == type && (!UnlockTracker.isCardLocked((String)c.getKey()) || Settings.treatEverythingAsUnlocked()))
                anyCard.addToBottom((AbstractCard)c.getValue());
        } while(true);
        anyCard.shuffle(AbstractDungeon.cardRandomRng);
        return anyCard.getRandomCard(true, rarity);
    }

    public static AbstractCard getAnyColorCard(com.megacrit.cardcrawl.cards.AbstractCard.CardRarity rarity)
    {
        CardGroup anyCard = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);
        Iterator iterator = cards.entrySet().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            java.util.Map.Entry c = (java.util.Map.Entry)iterator.next();
            if(((AbstractCard)c.getValue()).rarity == rarity && ((AbstractCard)c.getValue()).type != com.megacrit.cardcrawl.cards.AbstractCard.CardType.CURSE && ((AbstractCard)c.getValue()).type != com.megacrit.cardcrawl.cards.AbstractCard.CardType.STATUS && (!UnlockTracker.isCardLocked((String)c.getKey()) || Settings.treatEverythingAsUnlocked()))
                anyCard.addToBottom((AbstractCard)c.getValue());
        } while(true);
        anyCard.shuffle(AbstractDungeon.cardRng);
        return anyCard.getRandomCard(true, rarity).makeCopy();
    }

    public static CardGroup getEachRare(AbstractPlayer p)
    {
        CardGroup everyRareCard = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);
        Iterator iterator = cards.entrySet().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            java.util.Map.Entry c = (java.util.Map.Entry)iterator.next();
            if(((AbstractCard)c.getValue()).color == p.getCardColor() && ((AbstractCard)c.getValue()).rarity == com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.RARE)
                everyRareCard.addToBottom(((AbstractCard)c.getValue()).makeCopy());
        } while(true);
        return everyRareCard;
    }

    public static ArrayList getCardList(LibraryType type)
    {
        ArrayList retVal = new ArrayList();
label0:
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.helpers.CardLibrary.LibraryType[type.ordinal()])
        {
        default:
            break;

        case 1: // '\001'
            Iterator iterator = cards.entrySet().iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                java.util.Map.Entry c = (java.util.Map.Entry)iterator.next();
                if(((AbstractCard)c.getValue()).color == com.megacrit.cardcrawl.cards.AbstractCard.CardColor.COLORLESS)
                    retVal.add(c.getValue());
            } while(true);
            break;

        case 2: // '\002'
            Iterator iterator1 = cards.entrySet().iterator();
            do
            {
                java.util.Map.Entry c;
                do
                {
                    if(!iterator1.hasNext())
                        break label0;
                    c = (java.util.Map.Entry)iterator1.next();
                } while(((AbstractCard)c.getValue()).color != com.megacrit.cardcrawl.cards.AbstractCard.CardColor.CURSE);
                retVal.add(c.getValue());
            } while(true);

        case 3: // '\003'
            Iterator iterator2 = cards.entrySet().iterator();
            do
            {
                java.util.Map.Entry c;
                do
                {
                    if(!iterator2.hasNext())
                        break label0;
                    c = (java.util.Map.Entry)iterator2.next();
                } while(((AbstractCard)c.getValue()).color != com.megacrit.cardcrawl.cards.AbstractCard.CardColor.RED);
                retVal.add(c.getValue());
            } while(true);

        case 4: // '\004'
            Iterator iterator3 = cards.entrySet().iterator();
            do
            {
                java.util.Map.Entry c;
                do
                {
                    if(!iterator3.hasNext())
                        break label0;
                    c = (java.util.Map.Entry)iterator3.next();
                } while(((AbstractCard)c.getValue()).color != com.megacrit.cardcrawl.cards.AbstractCard.CardColor.GREEN);
                retVal.add(c.getValue());
            } while(true);

        case 5: // '\005'
            Iterator iterator4 = cards.entrySet().iterator();
            do
            {
                java.util.Map.Entry c;
                do
                {
                    if(!iterator4.hasNext())
                        break label0;
                    c = (java.util.Map.Entry)iterator4.next();
                } while(((AbstractCard)c.getValue()).color != com.megacrit.cardcrawl.cards.AbstractCard.CardColor.BLUE);
                retVal.add(c.getValue());
            } while(true);

        case 6: // '\006'
            Iterator iterator5 = cards.entrySet().iterator();
            do
            {
                java.util.Map.Entry c;
                do
                {
                    if(!iterator5.hasNext())
                        break label0;
                    c = (java.util.Map.Entry)iterator5.next();
                } while(((AbstractCard)c.getValue()).color != com.megacrit.cardcrawl.cards.AbstractCard.CardColor.PURPLE);
                retVal.add(c.getValue());
            } while(true);
        }
        return retVal;
    }

    public static void addCardsIntoPool(ArrayList tmpPool, com.megacrit.cardcrawl.cards.AbstractCard.CardColor color)
    {
        logger.info((new StringBuilder()).append("[INFO] Adding ").append(color).append(" cards into card pool.").toString());
        AbstractCard card = null;
        Iterator iterator = cards.entrySet().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            java.util.Map.Entry c = (java.util.Map.Entry)iterator.next();
            card = (AbstractCard)c.getValue();
            if(card.color == color && card.rarity != com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.BASIC && card.type != com.megacrit.cardcrawl.cards.AbstractCard.CardType.STATUS && (!UnlockTracker.isCardLocked((String)c.getKey()) || Settings.treatEverythingAsUnlocked()))
                tmpPool.add(card);
        } while(true);
    }

    public static void addRedCards(ArrayList tmpPool)
    {
        logger.info("[INFO] Adding red cards into card pool.");
        AbstractCard card = null;
        Iterator iterator = cards.entrySet().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            java.util.Map.Entry c = (java.util.Map.Entry)iterator.next();
            card = (AbstractCard)c.getValue();
            if(card.color == com.megacrit.cardcrawl.cards.AbstractCard.CardColor.RED && card.rarity != com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.BASIC && (!UnlockTracker.isCardLocked((String)c.getKey()) || Settings.treatEverythingAsUnlocked()))
                tmpPool.add(card);
        } while(true);
    }

    public static void addGreenCards(ArrayList tmpPool)
    {
        logger.info("[INFO] Adding green cards into card pool.");
        AbstractCard card = null;
        Iterator iterator = cards.entrySet().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            java.util.Map.Entry c = (java.util.Map.Entry)iterator.next();
            card = (AbstractCard)c.getValue();
            if(card.color == com.megacrit.cardcrawl.cards.AbstractCard.CardColor.GREEN && card.rarity != com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.BASIC && (!UnlockTracker.isCardLocked((String)c.getKey()) || Settings.treatEverythingAsUnlocked()))
                tmpPool.add(card);
        } while(true);
    }

    public static void addBlueCards(ArrayList tmpPool)
    {
        logger.info("[INFO] Adding blue cards into card pool.");
        AbstractCard card = null;
        Iterator iterator = cards.entrySet().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            java.util.Map.Entry c = (java.util.Map.Entry)iterator.next();
            card = (AbstractCard)c.getValue();
            if(card.color == com.megacrit.cardcrawl.cards.AbstractCard.CardColor.BLUE && card.rarity != com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.BASIC && (!UnlockTracker.isCardLocked((String)c.getKey()) || Settings.treatEverythingAsUnlocked()))
                tmpPool.add(card);
        } while(true);
    }

    public static void addPurpleCards(ArrayList tmpPool)
    {
        logger.info("[INFO] Adding purple cards into card pool.");
        AbstractCard card = null;
        Iterator iterator = cards.entrySet().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            java.util.Map.Entry c = (java.util.Map.Entry)iterator.next();
            card = (AbstractCard)c.getValue();
            if(card.color == com.megacrit.cardcrawl.cards.AbstractCard.CardColor.PURPLE && card.rarity != com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.BASIC && (!UnlockTracker.isCardLocked((String)c.getKey()) || Settings.treatEverythingAsUnlocked()))
                tmpPool.add(card);
        } while(true);
    }

    public static void addColorlessCards(ArrayList tmpPool)
    {
        logger.info("[INFO] Adding colorless cards into card pool.");
        AbstractCard card = null;
        Iterator iterator = cards.entrySet().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            java.util.Map.Entry c = (java.util.Map.Entry)iterator.next();
            card = (AbstractCard)c.getValue();
            if(card.color == com.megacrit.cardcrawl.cards.AbstractCard.CardColor.COLORLESS && card.type != com.megacrit.cardcrawl.cards.AbstractCard.CardType.STATUS && (!UnlockTracker.isCardLocked((String)c.getKey()) || Settings.treatEverythingAsUnlocked()))
                tmpPool.add(card);
        } while(true);
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/helpers/CardLibrary.getName());
    public static int totalCardCount = 0;
    public static HashMap cards = new HashMap();
    private static HashMap curses = new HashMap();
    public static int redCards = 0;
    public static int greenCards = 0;
    public static int blueCards = 0;
    public static int purpleCards = 0;
    public static int colorlessCards = 0;
    public static int curseCards = 0;
    public static int seenRedCards = 0;
    public static int seenGreenCards = 0;
    public static int seenBlueCards = 0;
    public static int seenPurpleCards = 0;
    public static int seenColorlessCards = 0;
    public static int seenCurseCards = 0;

}
