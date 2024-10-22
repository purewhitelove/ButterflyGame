// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractDungeon.java

package com.megacrit.cardcrawl.dungeons;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.blights.MimicInfestation;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.cards.colorless.SwiftStrike;
import com.megacrit.cardcrawl.cards.curses.AscendersBane;
import com.megacrit.cardcrawl.cards.curses.CurseOfTheBell;
import com.megacrit.cardcrawl.cards.curses.Necronomicurse;
import com.megacrit.cardcrawl.cards.curses.Pride;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.ExceptionHandler;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.credits.CreditsScreen;
import com.megacrit.cardcrawl.daily.mods.BigGameHunter;
import com.megacrit.cardcrawl.daily.mods.Binary;
import com.megacrit.cardcrawl.daily.mods.CertainFuture;
import com.megacrit.cardcrawl.daily.mods.ColorlessCards;
import com.megacrit.cardcrawl.daily.mods.DeadlyEvents;
import com.megacrit.cardcrawl.daily.mods.Diverse;
import com.megacrit.cardcrawl.daily.mods.Draft;
import com.megacrit.cardcrawl.daily.mods.Flight;
import com.megacrit.cardcrawl.daily.mods.Hoarder;
import com.megacrit.cardcrawl.daily.mods.Insanity;
import com.megacrit.cardcrawl.daily.mods.SealedDeck;
import com.megacrit.cardcrawl.daily.mods.Shiny;
import com.megacrit.cardcrawl.daily.mods.Terminal;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.beyond.SecretPortal;
import com.megacrit.cardcrawl.events.city.Beggar;
import com.megacrit.cardcrawl.events.city.KnowingSkull;
import com.megacrit.cardcrawl.events.city.TheJoust;
import com.megacrit.cardcrawl.events.exordium.Cleric;
import com.megacrit.cardcrawl.events.shrines.AccursedBlacksmith;
import com.megacrit.cardcrawl.events.shrines.Bonfire;
import com.megacrit.cardcrawl.events.shrines.Designer;
import com.megacrit.cardcrawl.events.shrines.Duplicator;
import com.megacrit.cardcrawl.events.shrines.FaceTrader;
import com.megacrit.cardcrawl.events.shrines.FountainOfCurseRemoval;
import com.megacrit.cardcrawl.events.shrines.Lab;
import com.megacrit.cardcrawl.events.shrines.Nloth;
import com.megacrit.cardcrawl.events.shrines.NoteForYourself;
import com.megacrit.cardcrawl.events.shrines.WeMeetAgain;
import com.megacrit.cardcrawl.events.shrines.WomanInBlue;
import com.megacrit.cardcrawl.helpers.BlightHelper;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.EventHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.helpers.Prefs;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.helpers.SaveHelper;
import com.megacrit.cardcrawl.helpers.TipTracker;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.integrations.PublisherIntegration;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.map.DungeonMap;
import com.megacrit.cardcrawl.map.Legend;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapGenerator;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.map.RoomTypeAssigner;
import com.megacrit.cardcrawl.metrics.MetricData;
import com.megacrit.cardcrawl.metrics.Metrics;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.MonsterInfo;
import com.megacrit.cardcrawl.neow.NeowRoom;
import com.megacrit.cardcrawl.neow.NeowUnlockScreen;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.FruitJuice;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BottledFlame;
import com.megacrit.cardcrawl.relics.BottledLightning;
import com.megacrit.cardcrawl.relics.BottledTornado;
import com.megacrit.cardcrawl.relics.Circlet;
import com.megacrit.cardcrawl.relics.Girya;
import com.megacrit.cardcrawl.relics.GoldenIdol;
import com.megacrit.cardcrawl.relics.JuzuBracelet;
import com.megacrit.cardcrawl.relics.PandorasBox;
import com.megacrit.cardcrawl.relics.PeacePipe;
import com.megacrit.cardcrawl.relics.PrismaticShard;
import com.megacrit.cardcrawl.relics.RedCirclet;
import com.megacrit.cardcrawl.relics.Shovel;
import com.megacrit.cardcrawl.relics.SmilingMask;
import com.megacrit.cardcrawl.relics.Whetstone;
import com.megacrit.cardcrawl.relics.WingBoots;
import com.megacrit.cardcrawl.rewards.chests.AbstractChest;
import com.megacrit.cardcrawl.rewards.chests.LargeChest;
import com.megacrit.cardcrawl.rewards.chests.MediumChest;
import com.megacrit.cardcrawl.rewards.chests.SmallChest;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.EmptyRoom;
import com.megacrit.cardcrawl.rooms.EventRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import com.megacrit.cardcrawl.rooms.TreasureRoom;
import com.megacrit.cardcrawl.rooms.TreasureRoomBoss;
import com.megacrit.cardcrawl.rooms.VictoryRoom;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import com.megacrit.cardcrawl.scenes.AbstractScene;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import com.megacrit.cardcrawl.screens.DeathScreen;
import com.megacrit.cardcrawl.screens.DiscardPileViewScreen;
import com.megacrit.cardcrawl.screens.DoorUnlockScreen;
import com.megacrit.cardcrawl.screens.DrawPileViewScreen;
import com.megacrit.cardcrawl.screens.DungeonMapScreen;
import com.megacrit.cardcrawl.screens.ExhaustPileViewScreen;
import com.megacrit.cardcrawl.screens.MasterDeckViewScreen;
import com.megacrit.cardcrawl.screens.VictoryScreen;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import com.megacrit.cardcrawl.screens.options.ConfirmPopup;
import com.megacrit.cardcrawl.screens.options.InputSettingsScreen;
import com.megacrit.cardcrawl.screens.options.SettingsScreen;
import com.megacrit.cardcrawl.screens.select.BossRelicSelectScreen;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.screens.select.HandCardSelectScreen;
import com.megacrit.cardcrawl.screens.stats.AchievementGrid;
import com.megacrit.cardcrawl.screens.stats.CharStat;
import com.megacrit.cardcrawl.screens.stats.StatsScreen;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.NeutralStance;
import com.megacrit.cardcrawl.ui.FtueTip;
import com.megacrit.cardcrawl.ui.buttons.CancelButton;
import com.megacrit.cardcrawl.ui.buttons.DynamicBanner;
import com.megacrit.cardcrawl.ui.buttons.PeekButton;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import com.megacrit.cardcrawl.unlock.UnlockCharacterScreen;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.unlock.misc.WatcherUnlock;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.ObtainKeyEffect;
import com.megacrit.cardcrawl.vfx.PlayerTurnEffect;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.dungeons:
//            Exordium, TheCity, TheBeyond

public abstract class AbstractDungeon
{
    public static final class RenderScene extends Enum
    {

        public static RenderScene[] values()
        {
            return (RenderScene[])$VALUES.clone();
        }

        public static RenderScene valueOf(String name)
        {
            return (RenderScene)Enum.valueOf(com/megacrit/cardcrawl/dungeons/AbstractDungeon$RenderScene, name);
        }

        public static final RenderScene NORMAL;
        public static final RenderScene EVENT;
        public static final RenderScene CAMPFIRE;
        private static final RenderScene $VALUES[];

        static 
        {
            NORMAL = new RenderScene("NORMAL", 0);
            EVENT = new RenderScene("EVENT", 1);
            CAMPFIRE = new RenderScene("CAMPFIRE", 2);
            $VALUES = (new RenderScene[] {
                NORMAL, EVENT, CAMPFIRE
            });
        }

        private RenderScene(String s, int i)
        {
            super(s, i);
        }
    }

    public static final class CurrentScreen extends Enum
    {

        public static CurrentScreen[] values()
        {
            return (CurrentScreen[])$VALUES.clone();
        }

        public static CurrentScreen valueOf(String name)
        {
            return (CurrentScreen)Enum.valueOf(com/megacrit/cardcrawl/dungeons/AbstractDungeon$CurrentScreen, name);
        }

        public static final CurrentScreen NONE;
        public static final CurrentScreen MASTER_DECK_VIEW;
        public static final CurrentScreen SETTINGS;
        public static final CurrentScreen INPUT_SETTINGS;
        public static final CurrentScreen GRID;
        public static final CurrentScreen MAP;
        public static final CurrentScreen FTUE;
        public static final CurrentScreen CHOOSE_ONE;
        public static final CurrentScreen HAND_SELECT;
        public static final CurrentScreen SHOP;
        public static final CurrentScreen COMBAT_REWARD;
        public static final CurrentScreen DISCARD_VIEW;
        public static final CurrentScreen EXHAUST_VIEW;
        public static final CurrentScreen GAME_DECK_VIEW;
        public static final CurrentScreen BOSS_REWARD;
        public static final CurrentScreen DEATH;
        public static final CurrentScreen CARD_REWARD;
        public static final CurrentScreen TRANSFORM;
        public static final CurrentScreen VICTORY;
        public static final CurrentScreen UNLOCK;
        public static final CurrentScreen DOOR_UNLOCK;
        public static final CurrentScreen CREDITS;
        public static final CurrentScreen NO_INTERACT;
        public static final CurrentScreen NEOW_UNLOCK;
        private static final CurrentScreen $VALUES[];

        static 
        {
            NONE = new CurrentScreen("NONE", 0);
            MASTER_DECK_VIEW = new CurrentScreen("MASTER_DECK_VIEW", 1);
            SETTINGS = new CurrentScreen("SETTINGS", 2);
            INPUT_SETTINGS = new CurrentScreen("INPUT_SETTINGS", 3);
            GRID = new CurrentScreen("GRID", 4);
            MAP = new CurrentScreen("MAP", 5);
            FTUE = new CurrentScreen("FTUE", 6);
            CHOOSE_ONE = new CurrentScreen("CHOOSE_ONE", 7);
            HAND_SELECT = new CurrentScreen("HAND_SELECT", 8);
            SHOP = new CurrentScreen("SHOP", 9);
            COMBAT_REWARD = new CurrentScreen("COMBAT_REWARD", 10);
            DISCARD_VIEW = new CurrentScreen("DISCARD_VIEW", 11);
            EXHAUST_VIEW = new CurrentScreen("EXHAUST_VIEW", 12);
            GAME_DECK_VIEW = new CurrentScreen("GAME_DECK_VIEW", 13);
            BOSS_REWARD = new CurrentScreen("BOSS_REWARD", 14);
            DEATH = new CurrentScreen("DEATH", 15);
            CARD_REWARD = new CurrentScreen("CARD_REWARD", 16);
            TRANSFORM = new CurrentScreen("TRANSFORM", 17);
            VICTORY = new CurrentScreen("VICTORY", 18);
            UNLOCK = new CurrentScreen("UNLOCK", 19);
            DOOR_UNLOCK = new CurrentScreen("DOOR_UNLOCK", 20);
            CREDITS = new CurrentScreen("CREDITS", 21);
            NO_INTERACT = new CurrentScreen("NO_INTERACT", 22);
            NEOW_UNLOCK = new CurrentScreen("NEOW_UNLOCK", 23);
            $VALUES = (new CurrentScreen[] {
                NONE, MASTER_DECK_VIEW, SETTINGS, INPUT_SETTINGS, GRID, MAP, FTUE, CHOOSE_ONE, HAND_SELECT, SHOP, 
                COMBAT_REWARD, DISCARD_VIEW, EXHAUST_VIEW, GAME_DECK_VIEW, BOSS_REWARD, DEATH, CARD_REWARD, TRANSFORM, VICTORY, UNLOCK, 
                DOOR_UNLOCK, CREDITS, NO_INTERACT, NEOW_UNLOCK
            });
        }

        private CurrentScreen(String s, int i)
        {
            super(s, i);
        }
    }


    public AbstractDungeon(String name, String levelId, AbstractPlayer p, ArrayList newSpecialOneTimeEventList)
    {
        ascensionCheck = UnlockTracker.isAscensionUnlocked(p);
        CardCrawlGame.dungeon = this;
        long startTime = System.currentTimeMillis();
        name = name;
        id = levelId;
        player = p;
        topPanel.setPlayerName();
        actionManager = new GameActionManager();
        overlayMenu = new OverlayMenu(p);
        dynamicBanner = new DynamicBanner();
        unlocks.clear();
        specialOneTimeEventList = newSpecialOneTimeEventList;
        isFadingIn = false;
        isFadingOut = false;
        waitingOnFadeOut = false;
        fadeTimer = 1.0F;
        isDungeonBeaten = false;
        isScreenUp = false;
        dungeonTransitionSetup();
        generateMonsters();
        initializeBoss();
        setBoss((String)bossList.get(0));
        initializeEventList();
        initializeEventImg();
        initializeShrineList();
        initializeCardPools();
        if(floorNum == 0)
            p.initializeStarterDeck();
        initializePotions();
        BlightHelper.initialize();
        if(id.equals("Exordium"))
        {
            screen = CurrentScreen.NONE;
            isScreenUp = false;
        } else
        {
            screen = CurrentScreen.MAP;
            isScreenUp = true;
        }
        logger.info((new StringBuilder()).append("Content generation time: ").append(System.currentTimeMillis() - startTime).append("ms").toString());
    }

    public AbstractDungeon(String name, AbstractPlayer p, SaveFile saveFile)
    {
        ascensionCheck = UnlockTracker.isAscensionUnlocked(p);
        id = saveFile.level_name;
        CardCrawlGame.dungeon = this;
        long startTime = System.currentTimeMillis();
        name = name;
        player = p;
        topPanel.setPlayerName();
        actionManager = new GameActionManager();
        overlayMenu = new OverlayMenu(p);
        dynamicBanner = new DynamicBanner();
        isFadingIn = false;
        isFadingOut = false;
        waitingOnFadeOut = false;
        fadeTimer = 1.0F;
        isDungeonBeaten = false;
        isScreenUp = false;
        firstRoomChosen = true;
        unlocks.clear();
        try
        {
            loadSave(saveFile);
        }
        catch(Exception e)
        {
            logger.info("Exception occurred while loading save!");
            logger.info("Deleting save due to crash!");
            SaveAndContinue.deleteSave(player);
            ExceptionHandler.handleException(e, LOGGER);
            Gdx.app.exit();
        }
        initializeEventImg();
        initializeShrineList();
        initializeCardPools();
        initializePotions();
        BlightHelper.initialize();
        screen = CurrentScreen.NONE;
        isScreenUp = false;
        logger.info((new StringBuilder()).append("Dungeon load time: ").append(System.currentTimeMillis() - startTime).append("ms").toString());
    }

    private void setBoss(String key)
    {
        bossKey = key;
        if(DungeonMap.boss != null && DungeonMap.bossOutline != null)
        {
            DungeonMap.boss.dispose();
            DungeonMap.bossOutline.dispose();
        }
        if(key.equals("The Guardian"))
        {
            DungeonMap.boss = ImageMaster.loadImage("images/ui/map/boss/guardian.png");
            DungeonMap.bossOutline = ImageMaster.loadImage("images/ui/map/bossOutline/guardian.png");
        } else
        if(key.equals("Hexaghost"))
        {
            DungeonMap.boss = ImageMaster.loadImage("images/ui/map/boss/hexaghost.png");
            DungeonMap.bossOutline = ImageMaster.loadImage("images/ui/map/bossOutline/hexaghost.png");
        } else
        if(key.equals("Slime Boss"))
        {
            DungeonMap.boss = ImageMaster.loadImage("images/ui/map/boss/slime.png");
            DungeonMap.bossOutline = ImageMaster.loadImage("images/ui/map/bossOutline/slime.png");
        } else
        if(key.equals("Collector"))
        {
            DungeonMap.boss = ImageMaster.loadImage("images/ui/map/boss/collector.png");
            DungeonMap.bossOutline = ImageMaster.loadImage("images/ui/map/bossOutline/collector.png");
        } else
        if(key.equals("Automaton"))
        {
            DungeonMap.boss = ImageMaster.loadImage("images/ui/map/boss/automaton.png");
            DungeonMap.bossOutline = ImageMaster.loadImage("images/ui/map/bossOutline/automaton.png");
        } else
        if(key.equals("Champ"))
        {
            DungeonMap.boss = ImageMaster.loadImage("images/ui/map/boss/champ.png");
            DungeonMap.bossOutline = ImageMaster.loadImage("images/ui/map/bossOutline/champ.png");
        } else
        if(key.equals("Awakened One"))
        {
            DungeonMap.boss = ImageMaster.loadImage("images/ui/map/boss/awakened.png");
            DungeonMap.bossOutline = ImageMaster.loadImage("images/ui/map/bossOutline/awakened.png");
        } else
        if(key.equals("Time Eater"))
        {
            DungeonMap.boss = ImageMaster.loadImage("images/ui/map/boss/timeeater.png");
            DungeonMap.bossOutline = ImageMaster.loadImage("images/ui/map/bossOutline/timeeater.png");
        } else
        if(key.equals("Donu and Deca"))
        {
            DungeonMap.boss = ImageMaster.loadImage("images/ui/map/boss/donu.png");
            DungeonMap.bossOutline = ImageMaster.loadImage("images/ui/map/bossOutline/donu.png");
        } else
        if(key.equals("The Heart"))
        {
            DungeonMap.boss = ImageMaster.loadImage("images/ui/map/boss/heart.png");
            DungeonMap.bossOutline = ImageMaster.loadImage("images/ui/map/bossOutline/heart.png");
        } else
        {
            logger.info((new StringBuilder()).append("WARNING: UNKNOWN BOSS ICON: ").append(key).toString());
            DungeonMap.boss = null;
        }
        logger.info((new StringBuilder()).append("[BOSS] ").append(key).toString());
    }

    protected abstract void initializeLevelSpecificChances();

    public static boolean isPlayerInDungeon()
    {
        return CardCrawlGame.dungeon != null;
    }

    public static void generateSeeds()
    {
        logger.info((new StringBuilder()).append("Generating seeds: ").append(Settings.seed).toString());
        monsterRng = new Random(Settings.seed);
        eventRng = new Random(Settings.seed);
        merchantRng = new Random(Settings.seed);
        cardRng = new Random(Settings.seed);
        treasureRng = new Random(Settings.seed);
        relicRng = new Random(Settings.seed);
        monsterHpRng = new Random(Settings.seed);
        potionRng = new Random(Settings.seed);
        aiRng = new Random(Settings.seed);
        shuffleRng = new Random(Settings.seed);
        cardRandomRng = new Random(Settings.seed);
        miscRng = new Random(Settings.seed);
    }

    public static void loadSeeds(SaveFile save)
    {
        if(save.is_daily || save.is_trial)
        {
            Settings.isDailyRun = save.is_daily;
            Settings.isTrial = save.is_trial;
            Settings.specialSeed = Long.valueOf(save.special_seed);
            if(save.is_daily)
                ModHelper.setTodaysMods(save.special_seed, player.chosenClass);
            else
                ModHelper.setTodaysMods(save.seed, player.chosenClass);
        }
        monsterRng = new Random(Settings.seed, save.monster_seed_count);
        eventRng = new Random(Settings.seed, save.event_seed_count);
        merchantRng = new Random(Settings.seed, save.merchant_seed_count);
        cardRng = new Random(Settings.seed, save.card_seed_count);
        cardBlizzRandomizer = save.card_random_seed_randomizer;
        treasureRng = new Random(Settings.seed, save.treasure_seed_count);
        relicRng = new Random(Settings.seed, save.relic_seed_count);
        potionRng = new Random(Settings.seed, save.potion_seed_count);
        logger.info((new StringBuilder()).append("Loading seeds: ").append(Settings.seed).toString());
        logger.info((new StringBuilder()).append("Monster seed:  ").append(monsterRng.counter).toString());
        logger.info((new StringBuilder()).append("Event seed:    ").append(eventRng.counter).toString());
        logger.info((new StringBuilder()).append("Merchant seed: ").append(merchantRng.counter).toString());
        logger.info((new StringBuilder()).append("Card seed:     ").append(cardRng.counter).toString());
        logger.info((new StringBuilder()).append("Treasure seed: ").append(treasureRng.counter).toString());
        logger.info((new StringBuilder()).append("Relic seed:    ").append(relicRng.counter).toString());
        logger.info((new StringBuilder()).append("Potion seed:   ").append(potionRng.counter).toString());
    }

    public void populatePathTaken(SaveFile saveFile)
    {
        MapRoomNode node = null;
        if(saveFile.current_room.equals(com/megacrit/cardcrawl/rooms/MonsterRoomBoss.getName()))
        {
            node = new MapRoomNode(-1, 15);
            node.room = new MonsterRoomBoss();
            nextRoom = node;
        } else
        if(saveFile.current_room.equals(com/megacrit/cardcrawl/rooms/TreasureRoomBoss.getName()))
        {
            node = new MapRoomNode(-1, 15);
            node.room = new TreasureRoomBoss();
            nextRoom = node;
        } else
        if(saveFile.room_y == 15 && saveFile.room_x == -1)
        {
            node = new MapRoomNode(-1, 15);
            node.room = new VictoryRoom(com.megacrit.cardcrawl.rooms.VictoryRoom.EventType.HEART);
            nextRoom = node;
        } else
        if(saveFile.current_room.equals(com/megacrit/cardcrawl/neow/NeowRoom.getName()))
            nextRoom = null;
        else
            nextRoom = (MapRoomNode)((ArrayList)map.get(saveFile.room_y)).get(saveFile.room_x);
        for(int i = 0; i < pathX.size(); i++)
        {
            if(((Integer)pathY.get(i)).intValue() == 14)
            {
                MapRoomNode node2 = (MapRoomNode)((ArrayList)map.get(((Integer)pathY.get(i)).intValue())).get(((Integer)pathX.get(i)).intValue());
                Iterator iterator = node2.getEdges().iterator();
                do
                {
                    if(!iterator.hasNext())
                        break;
                    MapEdge e = (MapEdge)iterator.next();
                    if(e != null)
                        e.markAsTaken();
                } while(true);
            }
            if(((Integer)pathY.get(i)).intValue() >= 15)
                continue;
            ((MapRoomNode)((ArrayList)map.get(((Integer)pathY.get(i)).intValue())).get(((Integer)pathX.get(i)).intValue())).taken = true;
            if(node != null)
            {
                MapEdge connectedEdge = node.getEdgeConnectedTo((MapRoomNode)((ArrayList)map.get(((Integer)pathY.get(i)).intValue())).get(((Integer)pathX.get(i)).intValue()));
                if(connectedEdge != null)
                    connectedEdge.markAsTaken();
            }
            node = (MapRoomNode)((ArrayList)map.get(((Integer)pathY.get(i)).intValue())).get(((Integer)pathX.get(i)).intValue());
        }

        if(isLoadingIntoNeow(saveFile))
        {
            logger.info("Loading into Neow");
            currMapNode = new MapRoomNode(0, -1);
            currMapNode.room = new EmptyRoom();
            nextRoom = null;
        } else
        {
            logger.info((new StringBuilder()).append("Loading into: ").append(saveFile.room_x).append(",").append(saveFile.room_y).toString());
            currMapNode = new MapRoomNode(0, -1);
            currMapNode.room = new EmptyRoom();
        }
        nextRoomTransition(saveFile);
        if(isLoadingIntoNeow(saveFile))
            if(saveFile.chose_neow_reward)
                currMapNode.room = new NeowRoom(true);
            else
                currMapNode.room = new NeowRoom(false);
        if((currMapNode.room instanceof VictoryRoom) && (!Settings.isFinalActAvailable || !Settings.hasRubyKey || !Settings.hasEmeraldKey || !Settings.hasSapphireKey))
            CardCrawlGame.stopClock = true;
    }

    protected boolean isLoadingIntoNeow(SaveFile saveFile)
    {
        return floorNum == 0 || saveFile.current_room.equals(com/megacrit/cardcrawl/neow/NeowRoom.getName());
    }

    public static AbstractChest getRandomChest()
    {
        int roll = treasureRng.random(0, 99);
        if(roll < smallChestChance)
            return new SmallChest();
        if(roll < mediumChestChance + smallChestChance)
            return new MediumChest();
        else
            return new LargeChest();
    }

    protected static void generateMap()
    {
        long startTime = System.currentTimeMillis();
        int mapHeight = 15;
        int mapWidth = 7;
        int mapPathDensity = 6;
        ArrayList roomList = new ArrayList();
        map = MapGenerator.generateDungeon(mapHeight, mapWidth, mapPathDensity, mapRng);
        int count = 0;
        for(Iterator iterator = map.iterator(); iterator.hasNext();)
        {
            ArrayList a = (ArrayList)iterator.next();
            Iterator iterator1 = a.iterator();
            while(iterator1.hasNext()) 
            {
                MapRoomNode n = (MapRoomNode)iterator1.next();
                if(n.hasEdges() && n.y != map.size() - 2)
                    count++;
            }
        }

        generateRoomTypes(roomList, count);
        RoomTypeAssigner.assignRowAsRoomType((ArrayList)map.get(map.size() - 1), com/megacrit/cardcrawl/rooms/RestRoom);
        RoomTypeAssigner.assignRowAsRoomType((ArrayList)map.get(0), com/megacrit/cardcrawl/rooms/MonsterRoom);
        if(Settings.isEndless && player.hasBlight("MimicInfestation"))
            RoomTypeAssigner.assignRowAsRoomType((ArrayList)map.get(8), com/megacrit/cardcrawl/rooms/MonsterRoomElite);
        else
            RoomTypeAssigner.assignRowAsRoomType((ArrayList)map.get(8), com/megacrit/cardcrawl/rooms/TreasureRoom);
        map = RoomTypeAssigner.distributeRoomsAcrossMap(mapRng, map, roomList);
        logger.info("Generated the following dungeon map:");
        logger.info(MapGenerator.toString(map, Boolean.valueOf(true)));
        logger.info((new StringBuilder()).append("Game Seed: ").append(Settings.seed).toString());
        logger.info((new StringBuilder()).append("Map generation time: ").append(System.currentTimeMillis() - startTime).append("ms").toString());
        firstRoomChosen = false;
        fadeIn();
        setEmeraldElite();
    }

    protected static void setEmeraldElite()
    {
        if(Settings.isFinalActAvailable && !Settings.hasEmeraldKey)
        {
            ArrayList eliteNodes = new ArrayList();
            for(int i = 0; i < map.size(); i++)
            {
                for(int j = 0; j < ((ArrayList)map.get(i)).size(); j++)
                    if(((MapRoomNode)((ArrayList)map.get(i)).get(j)).room instanceof MonsterRoomElite)
                        eliteNodes.add(((ArrayList)map.get(i)).get(j));

            }

            MapRoomNode chosenNode = (MapRoomNode)eliteNodes.get(mapRng.random(0, eliteNodes.size() - 1));
            chosenNode.hasEmeraldKey = true;
            logger.info((new StringBuilder()).append("[INFO] Elite nodes identified: ").append(eliteNodes.size()).toString());
            logger.info((new StringBuilder()).append("[INFO] Emerald Key  placed in: [").append(chosenNode.x).append(",").append(chosenNode.y).append("]").toString());
        }
    }

    private static void generateRoomTypes(ArrayList roomList, int availableRoomCount)
    {
        logger.info((new StringBuilder()).append("Generating Room Types! There are ").append(availableRoomCount).append(" rooms:").toString());
        int shopCount = Math.round((float)availableRoomCount * shopRoomChance);
        logger.info((new StringBuilder()).append(" SHOP (").append(toPercentage(shopRoomChance)).append("): ").append(shopCount).toString());
        int restCount = Math.round((float)availableRoomCount * restRoomChance);
        logger.info((new StringBuilder()).append(" REST (").append(toPercentage(restRoomChance)).append("): ").append(restCount).toString());
        int treasureCount = Math.round((float)availableRoomCount * treasureRoomChance);
        logger.info((new StringBuilder()).append(" TRSRE (").append(toPercentage(treasureRoomChance)).append("): ").append(treasureCount).toString());
        int eliteCount;
        if(ModHelper.isModEnabled("Elite Swarm"))
        {
            eliteCount = Math.round((float)availableRoomCount * (eliteRoomChance * 2.5F));
            logger.info((new StringBuilder()).append(" ELITE (").append(toPercentage(eliteRoomChance)).append("): ").append(eliteCount).toString());
        } else
        if(ascensionLevel >= 1)
        {
            eliteCount = Math.round((float)availableRoomCount * eliteRoomChance * 1.6F);
            logger.info((new StringBuilder()).append(" ELITE (").append(toPercentage(eliteRoomChance)).append("): ").append(eliteCount).toString());
        } else
        {
            eliteCount = Math.round((float)availableRoomCount * eliteRoomChance);
            logger.info((new StringBuilder()).append(" ELITE (").append(toPercentage(eliteRoomChance)).append("): ").append(eliteCount).toString());
        }
        int eventCount = Math.round((float)availableRoomCount * eventRoomChance);
        logger.info((new StringBuilder()).append(" EVNT (").append(toPercentage(eventRoomChance)).append("): ").append(eventCount).toString());
        int monsterCount = availableRoomCount - shopCount - restCount - treasureCount - eliteCount - eventCount;
        logger.info((new StringBuilder()).append(" MSTR (").append(toPercentage(1.0F - shopRoomChance - restRoomChance - treasureRoomChance - eliteRoomChance - eventRoomChance)).append("): ").append(monsterCount).toString());
        for(int i = 0; i < shopCount; i++)
            roomList.add(new ShopRoom());

        for(int i = 0; i < restCount; i++)
            roomList.add(new RestRoom());

        for(int i = 0; i < eliteCount; i++)
            roomList.add(new MonsterRoomElite());

        for(int i = 0; i < eventCount; i++)
            roomList.add(new EventRoom());

    }

    private static String toPercentage(float n)
    {
        return (new StringBuilder()).append(String.format("%.0f", new Object[] {
            Float.valueOf(n * 100F)
        })).append("%").toString();
    }

    private static void firstRoomLogic()
    {
        initializeFirstRoom();
        leftRoomAvailable = currMapNode.leftNodeAvailable();
        centerRoomAvailable = currMapNode.centerNodeAvailable();
        rightRoomAvailable = currMapNode.rightNodeAvailable();
    }

    private boolean passesDonutCheck(ArrayList map)
    {
        logger.info("CASEY'S DONUT CHECK: ");
        int width = ((ArrayList)map.get(0)).size();
        int height = map.size();
        logger.info((new StringBuilder()).append(" HEIGHT: ").append(height).toString());
        logger.info((new StringBuilder()).append(" WIDTH:  ").append(width).toString());
        int nodeCount = 0;
        boolean roomHasNode[] = new boolean[width];
        for(int i = 0; i < width; i++)
            roomHasNode[i] = false;

        ArrayList secondToLastRow = (ArrayList)map.get(map.size() - 2);
        for(Iterator iterator = secondToLastRow.iterator(); iterator.hasNext();)
        {
            MapRoomNode n = (MapRoomNode)iterator.next();
            Iterator iterator2 = n.getEdges().iterator();
            while(iterator2.hasNext()) 
            {
                MapEdge e = (MapEdge)iterator2.next();
                roomHasNode[e.dstX] = true;
            }
        }

        for(int i = 0; i < width - 1; i++)
            if(roomHasNode[i])
                nodeCount++;

        if(nodeCount == 1)
        {
            logger.info((new StringBuilder()).append(" [SUCCESS] ").append(nodeCount).append(" NODE IN LAST ROW").toString());
        } else
        {
            logger.info((new StringBuilder()).append(" [FAIL] ").append(nodeCount).append(" NODES IN LAST ROW").toString());
            return false;
        }
        int roomCount = 0;
        for(Iterator iterator1 = map.iterator(); iterator1.hasNext();)
        {
            ArrayList rows = (ArrayList)iterator1.next();
            Iterator iterator3 = rows.iterator();
            while(iterator3.hasNext()) 
            {
                MapRoomNode n = (MapRoomNode)iterator3.next();
                if(n.room != null)
                    roomCount++;
            }
        }

        logger.info((new StringBuilder()).append(" ROOM COUNT: ").append(roomCount).toString());
        return true;
    }

    public static AbstractRoom getCurrRoom()
    {
        return currMapNode.getRoom();
    }

    public static MapRoomNode getCurrMapNode()
    {
        return currMapNode;
    }

    public static void setCurrMapNode(MapRoomNode currMapNode)
    {
        SoulGroup group = currMapNode.room.souls;
        if(currMapNode != null && getCurrRoom() != null)
            getCurrRoom().dispose();
        currMapNode = currMapNode;
        if(currMapNode.room == null)
        {
            logger.warn("This player loaded into a room that no longer exists (due to a new map gen?)");
            int i = 0;
            do
            {
                if(i >= 5)
                    break;
                if(((MapRoomNode)((ArrayList)map.get(currMapNode.y)).get(i)).room != null)
                {
                    currMapNode = (MapRoomNode)((ArrayList)map.get(currMapNode.y)).get(i);
                    currMapNode.room = ((MapRoomNode)((ArrayList)map.get(currMapNode.y)).get(i)).room;
                    nextRoom.room = ((MapRoomNode)((ArrayList)map.get(currMapNode.y)).get(i)).room;
                    break;
                }
                i++;
            } while(true);
        } else
        {
            currMapNode.room.souls = group;
        }
    }

    public ArrayList getMap()
    {
        return map;
    }

    public static AbstractRelic returnRandomRelic(com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier tier)
    {
        logger.info((new StringBuilder()).append("Returning ").append(tier.name()).append(" relic").toString());
        return RelicLibrary.getRelic(returnRandomRelicKey(tier)).makeCopy();
    }

    public static AbstractRelic returnRandomScreenlessRelic(com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier tier)
    {
        logger.info((new StringBuilder()).append("Returning ").append(tier.name()).append(" relic").toString());
        AbstractRelic tmpRelic;
        for(tmpRelic = RelicLibrary.getRelic(returnRandomRelicKey(tier)).makeCopy(); Objects.equals(tmpRelic.relicId, "Bottled Flame") || Objects.equals(tmpRelic.relicId, "Bottled Lightning") || Objects.equals(tmpRelic.relicId, "Bottled Tornado") || Objects.equals(tmpRelic.relicId, "Whetstone"); tmpRelic = RelicLibrary.getRelic(returnRandomRelicKey(tier)).makeCopy());
        return tmpRelic;
    }

    public static AbstractRelic returnRandomNonCampfireRelic(com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier tier)
    {
        logger.info((new StringBuilder()).append("Returning ").append(tier.name()).append(" relic").toString());
        AbstractRelic tmpRelic;
        for(tmpRelic = RelicLibrary.getRelic(returnRandomRelicKey(tier)).makeCopy(); Objects.equals(tmpRelic.relicId, "Peace Pipe") || Objects.equals(tmpRelic.relicId, "Shovel") || Objects.equals(tmpRelic.relicId, "Girya"); tmpRelic = RelicLibrary.getRelic(returnRandomRelicKey(tier)).makeCopy());
        return tmpRelic;
    }

    public static AbstractRelic returnRandomRelicEnd(com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier tier)
    {
        logger.info((new StringBuilder()).append("Returning ").append(tier.name()).append(" relic").toString());
        return RelicLibrary.getRelic(returnEndRandomRelicKey(tier)).makeCopy();
    }

    public static String returnEndRandomRelicKey(com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier tier)
    {
        String retVal = null;
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier[];
            static final int $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardColor[];
            static final int $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[];
            static final int $SwitchMap$com$megacrit$cardcrawl$helpers$EventHelper$RoomResult[];
            static final int $SwitchMap$com$megacrit$cardcrawl$dungeons$AbstractDungeon$CurrentScreen[];
            static final int $SwitchMap$com$megacrit$cardcrawl$dungeons$AbstractDungeon$RenderScene[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$dungeons$AbstractDungeon$RenderScene = new int[RenderScene.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$dungeons$AbstractDungeon$RenderScene[RenderScene.NORMAL.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$dungeons$AbstractDungeon$RenderScene[RenderScene.CAMPFIRE.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$dungeons$AbstractDungeon$RenderScene[RenderScene.EVENT.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                $SwitchMap$com$megacrit$cardcrawl$dungeons$AbstractDungeon$CurrentScreen = new int[CurrentScreen.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$dungeons$AbstractDungeon$CurrentScreen[CurrentScreen.NO_INTERACT.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$dungeons$AbstractDungeon$CurrentScreen[CurrentScreen.NONE.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$dungeons$AbstractDungeon$CurrentScreen[CurrentScreen.FTUE.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror5) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$dungeons$AbstractDungeon$CurrentScreen[CurrentScreen.MASTER_DECK_VIEW.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror6) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$dungeons$AbstractDungeon$CurrentScreen[CurrentScreen.GAME_DECK_VIEW.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror7) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$dungeons$AbstractDungeon$CurrentScreen[CurrentScreen.DISCARD_VIEW.ordinal()] = 6;
                }
                catch(NoSuchFieldError nosuchfielderror8) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$dungeons$AbstractDungeon$CurrentScreen[CurrentScreen.EXHAUST_VIEW.ordinal()] = 7;
                }
                catch(NoSuchFieldError nosuchfielderror9) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$dungeons$AbstractDungeon$CurrentScreen[CurrentScreen.SETTINGS.ordinal()] = 8;
                }
                catch(NoSuchFieldError nosuchfielderror10) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$dungeons$AbstractDungeon$CurrentScreen[CurrentScreen.INPUT_SETTINGS.ordinal()] = 9;
                }
                catch(NoSuchFieldError nosuchfielderror11) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$dungeons$AbstractDungeon$CurrentScreen[CurrentScreen.MAP.ordinal()] = 10;
                }
                catch(NoSuchFieldError nosuchfielderror12) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$dungeons$AbstractDungeon$CurrentScreen[CurrentScreen.GRID.ordinal()] = 11;
                }
                catch(NoSuchFieldError nosuchfielderror13) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$dungeons$AbstractDungeon$CurrentScreen[CurrentScreen.CARD_REWARD.ordinal()] = 12;
                }
                catch(NoSuchFieldError nosuchfielderror14) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$dungeons$AbstractDungeon$CurrentScreen[CurrentScreen.COMBAT_REWARD.ordinal()] = 13;
                }
                catch(NoSuchFieldError nosuchfielderror15) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$dungeons$AbstractDungeon$CurrentScreen[CurrentScreen.BOSS_REWARD.ordinal()] = 14;
                }
                catch(NoSuchFieldError nosuchfielderror16) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$dungeons$AbstractDungeon$CurrentScreen[CurrentScreen.HAND_SELECT.ordinal()] = 15;
                }
                catch(NoSuchFieldError nosuchfielderror17) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$dungeons$AbstractDungeon$CurrentScreen[CurrentScreen.SHOP.ordinal()] = 16;
                }
                catch(NoSuchFieldError nosuchfielderror18) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$dungeons$AbstractDungeon$CurrentScreen[CurrentScreen.DEATH.ordinal()] = 17;
                }
                catch(NoSuchFieldError nosuchfielderror19) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$dungeons$AbstractDungeon$CurrentScreen[CurrentScreen.VICTORY.ordinal()] = 18;
                }
                catch(NoSuchFieldError nosuchfielderror20) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$dungeons$AbstractDungeon$CurrentScreen[CurrentScreen.UNLOCK.ordinal()] = 19;
                }
                catch(NoSuchFieldError nosuchfielderror21) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$dungeons$AbstractDungeon$CurrentScreen[CurrentScreen.NEOW_UNLOCK.ordinal()] = 20;
                }
                catch(NoSuchFieldError nosuchfielderror22) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$dungeons$AbstractDungeon$CurrentScreen[CurrentScreen.CREDITS.ordinal()] = 21;
                }
                catch(NoSuchFieldError nosuchfielderror23) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$dungeons$AbstractDungeon$CurrentScreen[CurrentScreen.DOOR_UNLOCK.ordinal()] = 22;
                }
                catch(NoSuchFieldError nosuchfielderror24) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$dungeons$AbstractDungeon$CurrentScreen[CurrentScreen.TRANSFORM.ordinal()] = 23;
                }
                catch(NoSuchFieldError nosuchfielderror25) { }
                $SwitchMap$com$megacrit$cardcrawl$helpers$EventHelper$RoomResult = new int[com.megacrit.cardcrawl.helpers.EventHelper.RoomResult.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$helpers$EventHelper$RoomResult[com.megacrit.cardcrawl.helpers.EventHelper.RoomResult.ELITE.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror26) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$helpers$EventHelper$RoomResult[com.megacrit.cardcrawl.helpers.EventHelper.RoomResult.MONSTER.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror27) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$helpers$EventHelper$RoomResult[com.megacrit.cardcrawl.helpers.EventHelper.RoomResult.SHOP.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror28) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$helpers$EventHelper$RoomResult[com.megacrit.cardcrawl.helpers.EventHelper.RoomResult.TREASURE.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror29) { }
                $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity = new int[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.BASIC.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror30) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.COMMON.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror31) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror32) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.RARE.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror33) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.CURSE.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror34) { }
                $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardColor = new int[com.megacrit.cardcrawl.cards.AbstractCard.CardColor.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardColor[com.megacrit.cardcrawl.cards.AbstractCard.CardColor.COLORLESS.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror35) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardColor[com.megacrit.cardcrawl.cards.AbstractCard.CardColor.CURSE.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror36) { }
                $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier = new int[com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier[com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.COMMON.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror37) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier[com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.UNCOMMON.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror38) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier[com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.RARE.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror39) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier[com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.SHOP.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror40) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier[com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.BOSS.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror41) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier[tier.ordinal()])
        {
        case 1: // '\001'
            if(commonRelicPool.isEmpty())
                retVal = returnRandomRelicKey(com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.UNCOMMON);
            else
                retVal = (String)commonRelicPool.remove(commonRelicPool.size() - 1);
            break;

        case 2: // '\002'
            if(uncommonRelicPool.isEmpty())
                retVal = returnRandomRelicKey(com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.RARE);
            else
                retVal = (String)uncommonRelicPool.remove(uncommonRelicPool.size() - 1);
            break;

        case 3: // '\003'
            if(rareRelicPool.isEmpty())
                retVal = "Circlet";
            else
                retVal = (String)rareRelicPool.remove(rareRelicPool.size() - 1);
            break;

        case 4: // '\004'
            if(shopRelicPool.isEmpty())
                retVal = returnRandomRelicKey(com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.UNCOMMON);
            else
                retVal = (String)shopRelicPool.remove(shopRelicPool.size() - 1);
            break;

        case 5: // '\005'
            if(bossRelicPool.isEmpty())
                retVal = "Red Circlet";
            else
                retVal = (String)bossRelicPool.remove(0);
            break;

        default:
            logger.info((new StringBuilder()).append("Incorrect relic tier: ").append(tier.name()).append(" was called in returnEndRandomRelicKey()").toString());
            break;
        }
        if(!RelicLibrary.getRelic(retVal).canSpawn())
            return returnEndRandomRelicKey(tier);
        else
            return retVal;
    }

    public static String returnRandomRelicKey(com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier tier)
    {
        String retVal = null;
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier[tier.ordinal()])
        {
        case 1: // '\001'
            if(commonRelicPool.isEmpty())
                retVal = returnRandomRelicKey(com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.UNCOMMON);
            else
                retVal = (String)commonRelicPool.remove(0);
            break;

        case 2: // '\002'
            if(uncommonRelicPool.isEmpty())
                retVal = returnRandomRelicKey(com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.RARE);
            else
                retVal = (String)uncommonRelicPool.remove(0);
            break;

        case 3: // '\003'
            if(rareRelicPool.isEmpty())
                retVal = "Circlet";
            else
                retVal = (String)rareRelicPool.remove(0);
            break;

        case 4: // '\004'
            if(shopRelicPool.isEmpty())
                retVal = returnRandomRelicKey(com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.UNCOMMON);
            else
                retVal = (String)shopRelicPool.remove(0);
            break;

        case 5: // '\005'
            if(bossRelicPool.isEmpty())
                retVal = "Red Circlet";
            else
                retVal = (String)bossRelicPool.remove(0);
            break;

        default:
            logger.info((new StringBuilder()).append("Incorrect relic tier: ").append(tier.name()).append(" was called in returnRandomRelicKey()").toString());
            break;
        }
        if(!RelicLibrary.getRelic(retVal).canSpawn())
            return returnEndRandomRelicKey(tier);
        else
            return retVal;
    }

    public static com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier returnRandomRelicTier()
    {
        int roll = relicRng.random(0, 99);
        if(roll < commonRelicChance)
            return com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.COMMON;
        if(roll < commonRelicChance + uncommonRelicChance)
            return com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.UNCOMMON;
        else
            return com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.RARE;
    }

    public static AbstractPotion returnTotallyRandomPotion()
    {
        return PotionHelper.getRandomPotion();
    }

    public static AbstractPotion returnRandomPotion()
    {
        return returnRandomPotion(false);
    }

    public static AbstractPotion returnRandomPotion(boolean limited)
    {
        int roll = potionRng.random(0, 99);
        if(roll < PotionHelper.POTION_COMMON_CHANCE)
            return returnRandomPotion(com.megacrit.cardcrawl.potions.AbstractPotion.PotionRarity.COMMON, limited);
        if(roll < PotionHelper.POTION_UNCOMMON_CHANCE + PotionHelper.POTION_COMMON_CHANCE)
            return returnRandomPotion(com.megacrit.cardcrawl.potions.AbstractPotion.PotionRarity.UNCOMMON, limited);
        else
            return returnRandomPotion(com.megacrit.cardcrawl.potions.AbstractPotion.PotionRarity.RARE, limited);
    }

    public static AbstractPotion returnRandomPotion(com.megacrit.cardcrawl.potions.AbstractPotion.PotionRarity rarity, boolean limited)
    {
        AbstractPotion temp = PotionHelper.getRandomPotion();
        boolean spamCheck = limited;
        do
        {
            if(temp.rarity == rarity && !spamCheck)
                break;
            spamCheck = limited;
            temp = PotionHelper.getRandomPotion();
            if(temp.ID != "Fruit Juice")
                spamCheck = false;
        } while(true);
        return temp;
    }

    public static void transformCard(AbstractCard c)
    {
        transformCard(c, false);
    }

    public static void transformCard(AbstractCard c, boolean autoUpgrade)
    {
        transformCard(c, autoUpgrade, new Random());
    }

    public static void transformCard(AbstractCard c, boolean autoUpgrade, Random rng)
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardColor[c.color.ordinal()])
        {
        case 1: // '\001'
            transformedCard = returnTrulyRandomColorlessCardFromAvailable(c, rng).makeCopy();
            break;

        case 2: // '\002'
            transformedCard = CardLibrary.getCurse(c, rng).makeCopy();
            break;

        default:
            transformedCard = returnTrulyRandomCardFromAvailable(c, rng).makeCopy();
            break;
        }
        UnlockTracker.markCardAsSeen(transformedCard.cardID);
        if(autoUpgrade && transformedCard.canUpgrade())
            transformedCard.upgrade();
    }

    public static void srcTransformCard(AbstractCard c)
    {
label0:
        {
            logger.info("Transform using SRC pool...");
            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardRarity[c.rarity.ordinal()])
            {
            default:
                break;

            case 1: // '\001'
                transformedCard = srcCommonCardPool.getRandomCard(false).makeCopy();
                break label0;

            case 2: // '\002'
                srcCommonCardPool.removeCard(c.cardID);
                transformedCard = srcCommonCardPool.getRandomCard(false).makeCopy();
                srcCommonCardPool.addToTop(c.makeCopy());
                break label0;

            case 3: // '\003'
                srcUncommonCardPool.removeCard(c.cardID);
                transformedCard = srcUncommonCardPool.getRandomCard(false).makeCopy();
                srcUncommonCardPool.addToTop(c.makeCopy());
                break label0;

            case 4: // '\004'
                srcRareCardPool.removeCard(c.cardID);
                if(srcRareCardPool.isEmpty())
                    transformedCard = srcUncommonCardPool.getRandomCard(false).makeCopy();
                else
                    transformedCard = srcRareCardPool.getRandomCard(false).makeCopy();
                srcRareCardPool.addToTop(c.makeCopy());
                break label0;

            case 5: // '\005'
                if(!srcRareCardPool.isEmpty())
                    transformedCard = srcRareCardPool.getRandomCard(false).makeCopy();
                else
                    transformedCard = srcUncommonCardPool.getRandomCard(false).makeCopy();
                break;
            }
            logger.info((new StringBuilder()).append("Transform called on a strange card type: ").append(c.type.name()).toString());
            transformedCard = srcCommonCardPool.getRandomCard(false).makeCopy();
        }
    }

    public static CardGroup getEachRare()
    {
        CardGroup everyRareCard = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);
        AbstractCard c;
        for(Iterator iterator = rareCardPool.group.iterator(); iterator.hasNext(); everyRareCard.addToBottom(c.makeCopy()))
            c = (AbstractCard)iterator.next();

        return everyRareCard;
    }

    public static AbstractCard returnRandomCard()
    {
        ArrayList list = new ArrayList();
        com.megacrit.cardcrawl.cards.AbstractCard.CardRarity rarity = rollRarity();
        if(rarity.equals(com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.COMMON))
            list.addAll(srcCommonCardPool.group);
        else
        if(rarity.equals(com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON))
            list.addAll(srcUncommonCardPool.group);
        else
            list.addAll(srcRareCardPool.group);
        return (AbstractCard)list.get(cardRandomRng.random(list.size() - 1));
    }

    public static AbstractCard returnTrulyRandomCard()
    {
        ArrayList list = new ArrayList();
        list.addAll(srcCommonCardPool.group);
        list.addAll(srcUncommonCardPool.group);
        list.addAll(srcRareCardPool.group);
        return (AbstractCard)list.get(cardRandomRng.random(list.size() - 1));
    }

    public static AbstractCard returnTrulyRandomCardInCombat()
    {
        ArrayList list = new ArrayList();
        Iterator iterator = srcCommonCardPool.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(!c.hasTag(com.megacrit.cardcrawl.cards.AbstractCard.CardTags.HEALING))
            {
                list.add(c);
                UnlockTracker.markCardAsSeen(c.cardID);
            }
        } while(true);
        iterator = srcUncommonCardPool.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(!c.hasTag(com.megacrit.cardcrawl.cards.AbstractCard.CardTags.HEALING))
            {
                list.add(c);
                UnlockTracker.markCardAsSeen(c.cardID);
            }
        } while(true);
        iterator = srcRareCardPool.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(!c.hasTag(com.megacrit.cardcrawl.cards.AbstractCard.CardTags.HEALING))
            {
                list.add(c);
                UnlockTracker.markCardAsSeen(c.cardID);
            }
        } while(true);
        return (AbstractCard)list.get(cardRandomRng.random(list.size() - 1));
    }

    public static AbstractCard returnTrulyRandomCardInCombat(com.megacrit.cardcrawl.cards.AbstractCard.CardType type)
    {
        ArrayList list = new ArrayList();
        Iterator iterator = srcCommonCardPool.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.type == type && !c.hasTag(com.megacrit.cardcrawl.cards.AbstractCard.CardTags.HEALING))
                list.add(c);
        } while(true);
        iterator = srcUncommonCardPool.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.type == type && !c.hasTag(com.megacrit.cardcrawl.cards.AbstractCard.CardTags.HEALING))
                list.add(c);
        } while(true);
        iterator = srcRareCardPool.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.type == type && !c.hasTag(com.megacrit.cardcrawl.cards.AbstractCard.CardTags.HEALING))
                list.add(c);
        } while(true);
        return (AbstractCard)list.get(cardRandomRng.random(list.size() - 1));
    }

    public static AbstractCard returnTrulyRandomColorlessCardInCombat()
    {
        return returnTrulyRandomColorlessCardInCombat(cardRandomRng);
    }

    public static AbstractCard returnTrulyRandomColorlessCardInCombat(String prohibitedID)
    {
        return returnTrulyRandomColorlessCardFromAvailable(prohibitedID, cardRandomRng);
    }

    public static AbstractCard returnTrulyRandomColorlessCardInCombat(Random rng)
    {
        ArrayList list = new ArrayList();
        Iterator iterator = srcColorlessCardPool.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(!c.hasTag(com.megacrit.cardcrawl.cards.AbstractCard.CardTags.HEALING))
                list.add(c);
        } while(true);
        return (AbstractCard)list.get(rng.random(list.size() - 1));
    }

    public static AbstractCard returnTrulyRandomColorlessCardFromAvailable(String prohibited, Random rng)
    {
        ArrayList list = new ArrayList();
        Iterator iterator = srcColorlessCardPool.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.cardID != prohibited)
                list.add(c);
        } while(true);
        return (AbstractCard)list.get(rng.random(list.size() - 1));
    }

    public static AbstractCard returnTrulyRandomColorlessCardFromAvailable(AbstractCard prohibited, Random rng)
    {
        ArrayList list = new ArrayList();
        Iterator iterator = srcColorlessCardPool.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(!Objects.equals(c.cardID, prohibited.cardID))
                list.add(c);
        } while(true);
        return (AbstractCard)list.get(rng.random(list.size() - 1));
    }

    public static AbstractCard returnTrulyRandomCardFromAvailable(AbstractCard prohibited, Random rng)
    {
        ArrayList list = new ArrayList();
label0:
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardColor[prohibited.color.ordinal()])
        {
        case 1: // '\001'
            Iterator iterator = colorlessCardPool.group.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractCard c = (AbstractCard)iterator.next();
                if(!Objects.equals(c.cardID, prohibited.cardID))
                    list.add(c);
            } while(true);
            break;

        case 2: // '\002'
            return CardLibrary.getCurse();

        default:
            Iterator iterator1 = commonCardPool.group.iterator();
            do
            {
                if(!iterator1.hasNext())
                    break;
                AbstractCard c = (AbstractCard)iterator1.next();
                if(!Objects.equals(c.cardID, prohibited.cardID))
                    list.add(c);
            } while(true);
            iterator1 = srcUncommonCardPool.group.iterator();
            do
            {
                if(!iterator1.hasNext())
                    break;
                AbstractCard c = (AbstractCard)iterator1.next();
                if(!Objects.equals(c.cardID, prohibited.cardID))
                    list.add(c);
            } while(true);
            iterator1 = srcRareCardPool.group.iterator();
            do
            {
                AbstractCard c;
                do
                {
                    if(!iterator1.hasNext())
                        break label0;
                    c = (AbstractCard)iterator1.next();
                } while(Objects.equals(c.cardID, prohibited.cardID));
                list.add(c);
            } while(true);
        }
        return ((AbstractCard)list.get(rng.random(list.size() - 1))).makeCopy();
    }

    public static AbstractCard returnTrulyRandomCardFromAvailable(AbstractCard prohibited)
    {
        return returnTrulyRandomCardFromAvailable(prohibited, new Random());
    }

    public static AbstractCard getTransformedCard()
    {
        AbstractCard retVal = transformedCard;
        transformedCard = null;
        return retVal;
    }

    public void populateFirstStrongEnemy(ArrayList monsters, ArrayList exclusions)
    {
        String m;
        do
            m = MonsterInfo.roll(monsters, monsterRng.random());
        while(exclusions.contains(m));
        monsterList.add(m);
    }

    public void populateMonsterList(ArrayList monsters, int numMonsters, boolean elites)
    {
        if(elites)
        {
            for(int i = 0; i < numMonsters; i++)
                if(eliteMonsterList.isEmpty())
                {
                    eliteMonsterList.add(MonsterInfo.roll(monsters, monsterRng.random()));
                } else
                {
                    String toAdd = MonsterInfo.roll(monsters, monsterRng.random());
                    if(!toAdd.equals(eliteMonsterList.get(eliteMonsterList.size() - 1)))
                        eliteMonsterList.add(toAdd);
                    else
                        i--;
                }

        } else
        {
            for(int i = 0; i < numMonsters; i++)
            {
                if(monsterList.isEmpty())
                {
                    monsterList.add(MonsterInfo.roll(monsters, monsterRng.random()));
                    continue;
                }
                String toAdd = MonsterInfo.roll(monsters, monsterRng.random());
                if(!toAdd.equals(monsterList.get(monsterList.size() - 1)))
                {
                    if(monsterList.size() > 1 && toAdd.equals(monsterList.get(monsterList.size() - 2)))
                        i--;
                    else
                        monsterList.add(toAdd);
                } else
                {
                    i--;
                }
            }

        }
    }

    protected abstract ArrayList generateExclusions();

    public static AbstractCard returnColorlessCard(com.megacrit.cardcrawl.cards.AbstractCard.CardRarity rarity)
    {
label0:
        {
            Collections.shuffle(colorlessCardPool.group, new java.util.Random(shuffleRng.randomLong()));
            AbstractCard c;
            for(Iterator iterator = colorlessCardPool.group.iterator(); iterator.hasNext();)
            {
                c = (AbstractCard)iterator.next();
                if(c.rarity == rarity)
                    return c.makeCopy();
            }

            if(rarity != com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.RARE)
                break label0;
            Iterator iterator1 = colorlessCardPool.group.iterator();
            do
            {
                if(!iterator1.hasNext())
                    break label0;
                c = (AbstractCard)iterator1.next();
            } while(c.rarity != com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON);
            return c.makeCopy();
        }
        return new SwiftStrike();
    }

    public static AbstractCard returnColorlessCard()
    {
        Collections.shuffle(colorlessCardPool.group);
        Iterator iterator = colorlessCardPool.group.iterator();
        if(iterator.hasNext())
        {
            AbstractCard c = (AbstractCard)iterator.next();
            return c.makeCopy();
        } else
        {
            return new SwiftStrike();
        }
    }

    public static AbstractCard returnRandomCurse()
    {
        AbstractCard c = CardLibrary.getCurse().makeCopy();
        UnlockTracker.markCardAsSeen(c.cardID);
        return c;
    }

    public void initializePotions()
    {
        PotionHelper.initialize(player.chosenClass);
    }

    public void initializeCardPools()
    {
        logger.info("INIT CARD POOL");
        long startTime = System.currentTimeMillis();
        commonCardPool.clear();
        uncommonCardPool.clear();
        rareCardPool.clear();
        colorlessCardPool.clear();
        curseCardPool.clear();
        ArrayList tmpPool = new ArrayList();
        if(ModHelper.isModEnabled("Colorless Cards"))
            CardLibrary.addColorlessCards(tmpPool);
        if(ModHelper.isModEnabled("Diverse"))
        {
            CardLibrary.addRedCards(tmpPool);
            CardLibrary.addGreenCards(tmpPool);
            CardLibrary.addBlueCards(tmpPool);
            if(!UnlockTracker.isCharacterLocked("Watcher"))
                CardLibrary.addPurpleCards(tmpPool);
        } else
        {
            player.getCardPool(tmpPool);
        }
        addColorlessCards();
        addCurseCards();
        Iterator iterator = tmpPool.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardRarity[c.rarity.ordinal()])
            {
            case 2: // '\002'
                commonCardPool.addToTop(c);
                break;

            case 3: // '\003'
                uncommonCardPool.addToTop(c);
                break;

            case 4: // '\004'
                rareCardPool.addToTop(c);
                break;

            case 5: // '\005'
                curseCardPool.addToTop(c);
                break;

            default:
                logger.info((new StringBuilder()).append("Unspecified rarity: ").append(c.rarity.name()).append(" when creating pools! AbstractDungeon: Line 827").toString());
                break;
            }
        } while(true);
        srcColorlessCardPool = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.CARD_POOL);
        srcCurseCardPool = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.CARD_POOL);
        srcRareCardPool = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.CARD_POOL);
        srcUncommonCardPool = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.CARD_POOL);
        srcCommonCardPool = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.CARD_POOL);
        AbstractCard c;
        for(Iterator iterator1 = colorlessCardPool.group.iterator(); iterator1.hasNext(); srcColorlessCardPool.addToBottom(c))
            c = (AbstractCard)iterator1.next();

        AbstractCard c;
        for(Iterator iterator2 = curseCardPool.group.iterator(); iterator2.hasNext(); srcCurseCardPool.addToBottom(c))
            c = (AbstractCard)iterator2.next();

        AbstractCard c;
        for(Iterator iterator3 = rareCardPool.group.iterator(); iterator3.hasNext(); srcRareCardPool.addToBottom(c))
            c = (AbstractCard)iterator3.next();

        AbstractCard c;
        for(Iterator iterator4 = uncommonCardPool.group.iterator(); iterator4.hasNext(); srcUncommonCardPool.addToBottom(c))
            c = (AbstractCard)iterator4.next();

        AbstractCard c;
        for(Iterator iterator5 = commonCardPool.group.iterator(); iterator5.hasNext(); srcCommonCardPool.addToBottom(c))
            c = (AbstractCard)iterator5.next();

        logger.info((new StringBuilder()).append("Cardpool load time: ").append(System.currentTimeMillis() - startTime).append("ms").toString());
    }

    private void addColorlessCards()
    {
        Iterator iterator = CardLibrary.cards.entrySet().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            java.util.Map.Entry c = (java.util.Map.Entry)iterator.next();
            AbstractCard card = (AbstractCard)c.getValue();
            if(card.color == com.megacrit.cardcrawl.cards.AbstractCard.CardColor.COLORLESS && card.rarity != com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.BASIC && card.rarity != com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.SPECIAL && card.type != com.megacrit.cardcrawl.cards.AbstractCard.CardType.STATUS)
                colorlessCardPool.addToTop(card);
        } while(true);
        logger.info((new StringBuilder()).append("COLORLESS CARDS: ").append(colorlessCardPool.size()).toString());
    }

    private void addCurseCards()
    {
        Iterator iterator = CardLibrary.cards.entrySet().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            java.util.Map.Entry c = (java.util.Map.Entry)iterator.next();
            AbstractCard card = (AbstractCard)c.getValue();
            if(card.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.CURSE && !Objects.equals(card.cardID, "Necronomicurse") && !Objects.equals(card.cardID, "AscendersBane") && !Objects.equals(card.cardID, "CurseOfTheBell") && !Objects.equals(card.cardID, "Pride"))
                curseCardPool.addToTop(card);
        } while(true);
        logger.info((new StringBuilder()).append("CURSE CARDS: ").append(curseCardPool.size()).toString());
    }

    protected void initializeRelicList()
    {
        commonRelicPool.clear();
        uncommonRelicPool.clear();
        rareRelicPool.clear();
        shopRelicPool.clear();
        bossRelicPool.clear();
        RelicLibrary.populateRelicPool(commonRelicPool, com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.COMMON, player.chosenClass);
        RelicLibrary.populateRelicPool(uncommonRelicPool, com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.UNCOMMON, player.chosenClass);
        RelicLibrary.populateRelicPool(rareRelicPool, com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.RARE, player.chosenClass);
        RelicLibrary.populateRelicPool(shopRelicPool, com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.SHOP, player.chosenClass);
        RelicLibrary.populateRelicPool(bossRelicPool, com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.BOSS, player.chosenClass);
        if(floorNum >= 1)
        {
            AbstractRelic r;
            for(Iterator iterator = player.relics.iterator(); iterator.hasNext(); relicsToRemoveOnStart.add(r.relicId))
                r = (AbstractRelic)iterator.next();

        }
        Collections.shuffle(commonRelicPool, new java.util.Random(relicRng.randomLong()));
        Collections.shuffle(uncommonRelicPool, new java.util.Random(relicRng.randomLong()));
        Collections.shuffle(rareRelicPool, new java.util.Random(relicRng.randomLong()));
        Collections.shuffle(shopRelicPool, new java.util.Random(relicRng.randomLong()));
        Collections.shuffle(bossRelicPool, new java.util.Random(relicRng.randomLong()));
        if(ModHelper.isModEnabled("Flight") || ModHelper.isModEnabled("Uncertain Future"))
            relicsToRemoveOnStart.add("WingedGreaves");
        if(ModHelper.isModEnabled("Diverse"))
            relicsToRemoveOnStart.add("PrismaticShard");
        if(ModHelper.isModEnabled("DeadlyEvents"))
            relicsToRemoveOnStart.add("Juzu Bracelet");
        if(ModHelper.isModEnabled("Hoarder"))
            relicsToRemoveOnStart.add("Smiling Mask");
        if(ModHelper.isModEnabled("Draft") || ModHelper.isModEnabled("SealedDeck") || ModHelper.isModEnabled("Shiny") || ModHelper.isModEnabled("Insanity"))
            relicsToRemoveOnStart.add("Pandora's Box");
        Iterator iterator1 = relicsToRemoveOnStart.iterator();
label0:
        do
        {
            if(!iterator1.hasNext())
                break;
            String remove = (String)iterator1.next();
            Iterator s = commonRelicPool.iterator();
            String derp;
            do
            {
                if(!s.hasNext())
                    break;
                derp = (String)s.next();
                if(!derp.equals(remove))
                    continue;
                s.remove();
                logger.info((new StringBuilder()).append(derp).append(" removed.").toString());
                break;
            } while(true);
            s = uncommonRelicPool.iterator();
            do
            {
                if(!s.hasNext())
                    break;
                derp = (String)s.next();
                if(!derp.equals(remove))
                    continue;
                s.remove();
                logger.info((new StringBuilder()).append(derp).append(" removed.").toString());
                break;
            } while(true);
            s = rareRelicPool.iterator();
            do
            {
                if(!s.hasNext())
                    break;
                derp = (String)s.next();
                if(!derp.equals(remove))
                    continue;
                s.remove();
                logger.info((new StringBuilder()).append(derp).append(" removed.").toString());
                break;
            } while(true);
            s = bossRelicPool.iterator();
            do
            {
                if(!s.hasNext())
                    break;
                derp = (String)s.next();
                if(!derp.equals(remove))
                    continue;
                s.remove();
                logger.info((new StringBuilder()).append(derp).append(" removed.").toString());
                break;
            } while(true);
            s = shopRelicPool.iterator();
            do
            {
                if(!s.hasNext())
                    continue label0;
                derp = (String)s.next();
            } while(!derp.equals(remove));
            s.remove();
            logger.info((new StringBuilder()).append(derp).append(" removed.").toString());
        } while(true);
        if(Settings.isDebug)
        {
            logger.info("Relic (Common):");
            String s;
            for(Iterator iterator2 = commonRelicPool.iterator(); iterator2.hasNext(); logger.info((new StringBuilder()).append(" ").append(s).toString()))
                s = (String)iterator2.next();

            logger.info("Relic (Uncommon):");
            String s;
            for(Iterator iterator3 = uncommonRelicPool.iterator(); iterator3.hasNext(); logger.info((new StringBuilder()).append(" ").append(s).toString()))
                s = (String)iterator3.next();

            logger.info("Relic (Rare):");
            String s;
            for(Iterator iterator4 = rareRelicPool.iterator(); iterator4.hasNext(); logger.info((new StringBuilder()).append(" ").append(s).toString()))
                s = (String)iterator4.next();

            logger.info("Relic (Shop):");
            String s;
            for(Iterator iterator5 = shopRelicPool.iterator(); iterator5.hasNext(); logger.info((new StringBuilder()).append(" ").append(s).toString()))
                s = (String)iterator5.next();

            logger.info("Relic (Boss):");
            String s;
            for(Iterator iterator6 = bossRelicPool.iterator(); iterator6.hasNext(); logger.info((new StringBuilder()).append(" ").append(s).toString()))
                s = (String)iterator6.next();

        }
    }

    protected abstract void generateMonsters();

    protected abstract void generateWeakEnemies(int i);

    protected abstract void generateStrongEnemies(int i);

    protected abstract void generateElites(int i);

    protected abstract void initializeBoss();

    protected abstract void initializeEventList();

    protected abstract void initializeEventImg();

    protected abstract void initializeShrineList();

    public void initializeSpecialOneTimeEventList()
    {
        specialOneTimeEventList.clear();
        specialOneTimeEventList.add("Accursed Blacksmith");
        specialOneTimeEventList.add("Bonfire Elementals");
        specialOneTimeEventList.add("Designer");
        specialOneTimeEventList.add("Duplicator");
        specialOneTimeEventList.add("FaceTrader");
        specialOneTimeEventList.add("Fountain of Cleansing");
        specialOneTimeEventList.add("Knowing Skull");
        specialOneTimeEventList.add("Lab");
        specialOneTimeEventList.add("N'loth");
        if(isNoteForYourselfAvailable())
            specialOneTimeEventList.add("NoteForYourself");
        specialOneTimeEventList.add("SecretPortal");
        specialOneTimeEventList.add("The Joust");
        specialOneTimeEventList.add("WeMeetAgain");
        specialOneTimeEventList.add("The Woman in Blue");
    }

    private boolean isNoteForYourselfAvailable()
    {
        if(Settings.isDailyRun)
        {
            logger.info("Note For Yourself is disabled due to Daily Run");
            return false;
        }
        if(ascensionLevel >= 15)
        {
            logger.info("Note For Yourself is disabled beyond Ascension 15+");
            return false;
        }
        if(ascensionLevel == 0)
        {
            logger.info("Note For Yourself is enabled due to No Ascension");
            return true;
        }
        if(ascensionLevel < player.getPrefs().getInteger("ASCENSION_LEVEL"))
        {
            logger.info("Note For Yourself is enabled as it's less than Highest Unlocked Ascension");
            return true;
        } else
        {
            logger.info("Note For Yourself is disabled as requirements aren't met");
            return false;
        }
    }

    public static ArrayList getColorlessRewardCards()
    {
        ArrayList retVal = new ArrayList();
        int numCards = 3;
        for(Iterator iterator = player.relics.iterator(); iterator.hasNext();)
        {
            AbstractRelic r = (AbstractRelic)iterator.next();
            numCards = r.changeNumberOfCardsInReward(numCards);
        }

        if(ModHelper.isModEnabled("Binary"))
            numCards--;
        for(int i = 0; i < numCards; i++)
        {
            com.megacrit.cardcrawl.cards.AbstractCard.CardRarity rarity = rollRareOrUncommon(colorlessRareChance);
            AbstractCard card = null;
            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardRarity[rarity.ordinal()])
            {
            case 4: // '\004'
                card = getColorlessCardFromPool(rarity);
                cardBlizzRandomizer = cardBlizzStartOffset;
                break;

            case 3: // '\003'
                card = getColorlessCardFromPool(rarity);
                break;

            default:
                logger.info("WTF?");
                break;
            }
            for(; retVal.contains(card); card = getColorlessCardFromPool(rarity))
                if(card != null)
                    logger.info((new StringBuilder()).append("DUPE: ").append(card.originalName).toString());

            if(card != null)
                retVal.add(card);
        }

        ArrayList retVal2 = new ArrayList();
        AbstractCard c;
        for(Iterator iterator1 = retVal.iterator(); iterator1.hasNext(); retVal2.add(c.makeCopy()))
            c = (AbstractCard)iterator1.next();

        return retVal2;
    }

    public static ArrayList getRewardCards()
    {
        ArrayList retVal = new ArrayList();
        int numCards = 3;
        for(Iterator iterator = player.relics.iterator(); iterator.hasNext();)
        {
            AbstractRelic r = (AbstractRelic)iterator.next();
            numCards = r.changeNumberOfCardsInReward(numCards);
        }

        if(ModHelper.isModEnabled("Binary"))
            numCards--;
        for(int i = 0; i < numCards; i++)
        {
            com.megacrit.cardcrawl.cards.AbstractCard.CardRarity rarity = rollRarity();
            AbstractCard card = null;
            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardRarity[rarity.ordinal()])
            {
            case 3: // '\003'
                break;

            case 4: // '\004'
                cardBlizzRandomizer = cardBlizzStartOffset;
                break;

            case 2: // '\002'
                cardBlizzRandomizer -= cardBlizzGrowth;
                if(cardBlizzRandomizer <= cardBlizzMaxOffset)
                    cardBlizzRandomizer = cardBlizzMaxOffset;
                break;

            default:
                logger.info("WTF?");
                break;
            }
            boolean containsDupe = true;
label0:
            do
            {
                if(!containsDupe)
                    break;
                containsDupe = false;
                if(player.hasRelic("PrismaticShard"))
                    card = CardLibrary.getAnyColorCard(rarity);
                else
                    card = getCard(rarity);
                Iterator iterator4 = retVal.iterator();
                AbstractCard c;
                do
                {
                    if(!iterator4.hasNext())
                        continue label0;
                    c = (AbstractCard)iterator4.next();
                } while(!c.cardID.equals(card.cardID));
                containsDupe = true;
            } while(true);
            if(card != null)
                retVal.add(card);
        }

        ArrayList retVal2 = new ArrayList();
        AbstractCard c;
        for(Iterator iterator1 = retVal.iterator(); iterator1.hasNext(); retVal2.add(c.makeCopy()))
            c = (AbstractCard)iterator1.next();

        for(Iterator iterator2 = retVal2.iterator(); iterator2.hasNext();)
        {
            AbstractCard c = (AbstractCard)iterator2.next();
            if(c.rarity != com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.RARE && cardRng.randomBoolean(cardUpgradedChance) && c.canUpgrade())
            {
                c.upgrade();
            } else
            {
                Iterator iterator3 = player.relics.iterator();
                while(iterator3.hasNext()) 
                {
                    AbstractRelic r = (AbstractRelic)iterator3.next();
                    r.onPreviewObtainCard(c);
                }
            }
        }

        return retVal2;
    }

    public static AbstractCard getCard(com.megacrit.cardcrawl.cards.AbstractCard.CardRarity rarity)
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardRarity[rarity.ordinal()])
        {
        case 4: // '\004'
            return rareCardPool.getRandomCard(true);

        case 3: // '\003'
            return uncommonCardPool.getRandomCard(true);

        case 2: // '\002'
            return commonCardPool.getRandomCard(true);

        case 5: // '\005'
            return curseCardPool.getRandomCard(true);
        }
        logger.info("No rarity on getCard in Abstract Dungeon");
        return null;
    }

    public static AbstractCard getCard(com.megacrit.cardcrawl.cards.AbstractCard.CardRarity rarity, Random rng)
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardRarity[rarity.ordinal()])
        {
        case 4: // '\004'
            return rareCardPool.getRandomCard(rng);

        case 3: // '\003'
            return uncommonCardPool.getRandomCard(rng);

        case 2: // '\002'
            return commonCardPool.getRandomCard(rng);

        case 5: // '\005'
            return curseCardPool.getRandomCard(rng);
        }
        logger.info("No rarity on getCard in Abstract Dungeon");
        return null;
    }

    public static AbstractCard getCardWithoutRng(com.megacrit.cardcrawl.cards.AbstractCard.CardRarity rarity)
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardRarity[rarity.ordinal()])
        {
        case 4: // '\004'
            return rareCardPool.getRandomCard(false);

        case 3: // '\003'
            return uncommonCardPool.getRandomCard(false);

        case 2: // '\002'
            return commonCardPool.getRandomCard(false);

        case 5: // '\005'
            return returnRandomCurse();
        }
        logger.info("Check getCardWithoutRng");
        return null;
    }

    public static AbstractCard getCardFromPool(com.megacrit.cardcrawl.cards.AbstractCard.CardRarity rarity, com.megacrit.cardcrawl.cards.AbstractCard.CardType type, boolean useRng)
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardRarity[rarity.ordinal()])
        {
        case 4: // '\004'
        {
            AbstractCard retVal = rareCardPool.getRandomCard(type, useRng);
            if(retVal != null)
                return retVal;
            logger.info((new StringBuilder()).append("ERROR: Could not find Rare card of type: ").append(type.name()).toString());
            // fall through
        }

        case 3: // '\003'
        {
            AbstractCard retVal = uncommonCardPool.getRandomCard(type, useRng);
            if(retVal != null)
                return retVal;
            if(type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.POWER)
                return getCardFromPool(com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.RARE, type, useRng);
            logger.info((new StringBuilder()).append("ERROR: Could not find Uncommon card of type: ").append(type.name()).toString());
            // fall through
        }

        case 2: // '\002'
        {
            AbstractCard retVal = commonCardPool.getRandomCard(type, useRng);
            if(retVal != null)
                return retVal;
            if(type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.POWER)
                return getCardFromPool(com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON, type, useRng);
            logger.info((new StringBuilder()).append("ERROR: Could not find Common card of type: ").append(type.name()).toString());
            // fall through
        }

        case 5: // '\005'
        {
            AbstractCard retVal = curseCardPool.getRandomCard(type, useRng);
            if(retVal != null)
                return retVal;
            logger.info((new StringBuilder()).append("ERROR: Could not find Curse card of type: ").append(type.name()).toString());
            // fall through
        }

        default:
        {
            logger.info("ERROR: Default in getCardFromPool");
            return null;
        }
        }
    }

    public static AbstractCard getColorlessCardFromPool(com.megacrit.cardcrawl.cards.AbstractCard.CardRarity rarity)
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardRarity[rarity.ordinal()])
        {
        case 4: // '\004'
        {
            AbstractCard retVal = colorlessCardPool.getRandomCard(true, rarity);
            if(retVal != null)
                return retVal;
            // fall through
        }

        case 3: // '\003'
        {
            AbstractCard retVal = colorlessCardPool.getRandomCard(true, rarity);
            if(retVal != null)
                return retVal;
            // fall through
        }

        default:
        {
            logger.info("ERROR: getColorlessCardFromPool");
            return null;
        }
        }
    }

    public static com.megacrit.cardcrawl.cards.AbstractCard.CardRarity rollRarity(Random rng)
    {
        int roll = cardRng.random(99);
        roll += cardBlizzRandomizer;
        if(currMapNode == null)
            return getCardRarityFallback(roll);
        else
            return getCurrRoom().getCardRarity(roll);
    }

    private static com.megacrit.cardcrawl.cards.AbstractCard.CardRarity getCardRarityFallback(int roll)
    {
        int rareRate = 3;
        if(roll < rareRate)
            return com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.RARE;
        if(roll < 40)
            return com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON;
        else
            return com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.COMMON;
    }

    public static com.megacrit.cardcrawl.cards.AbstractCard.CardRarity rollRarity()
    {
        return rollRarity(cardRng);
    }

    public static com.megacrit.cardcrawl.cards.AbstractCard.CardRarity rollRareOrUncommon(float rareChance)
    {
        if(cardRng.randomBoolean(rareChance))
            return com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.RARE;
        else
            return com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON;
    }

    public static AbstractMonster getRandomMonster()
    {
        return currMapNode.room.monsters.getRandomMonster(null, true, cardRandomRng);
    }

    public static AbstractMonster getRandomMonster(AbstractMonster except)
    {
        return currMapNode.room.monsters.getRandomMonster(except, true, cardRandomRng);
    }

    public static void nextRoomTransitionStart()
    {
        fadeOut();
        waitingOnFadeOut = true;
        overlayMenu.proceedButton.hide();
        if(ModHelper.isModEnabled("Terminal"))
            player.decreaseMaxHealth(1);
    }

    public static void initializeFirstRoom()
    {
        fadeIn();
        floorNum++;
        if(currMapNode.room instanceof MonsterRoom)
        {
            if(!CardCrawlGame.loadingSave)
                if(SaveHelper.shouldSave())
                {
                    SaveHelper.saveIfAppropriate(com.megacrit.cardcrawl.saveAndContinue.SaveFile.SaveType.ENTER_ROOM);
                } else
                {
                    Metrics metrics = new Metrics();
                    metrics.setValues(false, false, null, com.megacrit.cardcrawl.metrics.Metrics.MetricRequestType.NONE);
                    metrics.gatherAllDataAndSave(false, false, null);
                }
            floorNum--;
        }
        scene.nextRoom(currMapNode.room);
    }

    public static void resetPlayer()
    {
        player.orbs.clear();
        player.animX = 0.0F;
        player.animY = 0.0F;
        player.hideHealthBar();
        player.hand.clear();
        player.powers.clear();
        player.drawPile.clear();
        player.discardPile.clear();
        player.exhaustPile.clear();
        player.limbo.clear();
        player.loseBlock(true);
        player.damagedThisCombat = 0;
        if(!player.stance.ID.equals("Neutral"))
        {
            player.stance = new NeutralStance();
            player.onStanceChange("Neutral");
        }
        GameActionManager.turn = 1;
    }

    public void nextRoomTransition()
    {
        nextRoomTransition(null);
    }

    public void nextRoomTransition(SaveFile saveFile)
    {
        overlayMenu.proceedButton.setLabel(TEXT[0]);
        combatRewardScreen.clear();
        if(nextRoom != null && nextRoom.room != null)
            nextRoom.room.rewards.clear();
        if(getCurrRoom() instanceof MonsterRoomElite)
        {
            if(!eliteMonsterList.isEmpty())
            {
                logger.info((new StringBuilder()).append("Removing elite: ").append((String)eliteMonsterList.get(0)).append(" from monster list.").toString());
                eliteMonsterList.remove(0);
            } else
            {
                generateElites(10);
            }
        } else
        if(getCurrRoom() instanceof MonsterRoom)
        {
            if(!monsterList.isEmpty())
            {
                logger.info((new StringBuilder()).append("Removing monster: ").append((String)monsterList.get(0)).append(" from monster list.").toString());
                monsterList.remove(0);
            } else
            {
                generateStrongEnemies(12);
            }
        } else
        if((getCurrRoom() instanceof EventRoom) && (getCurrRoom().event instanceof NoteForYourself))
        {
            AbstractCard tmpCard = ((NoteForYourself)getCurrRoom().event).saveCard;
            if(tmpCard != null)
            {
                CardCrawlGame.playerPref.putString("NOTE_CARD", tmpCard.cardID);
                CardCrawlGame.playerPref.putInteger("NOTE_UPGRADE", tmpCard.timesUpgraded);
                CardCrawlGame.playerPref.flush();
            }
        }
        if(RestRoom.lastFireSoundId != 0L)
            CardCrawlGame.sound.fadeOut("REST_FIRE_WET", RestRoom.lastFireSoundId);
        if(!player.stance.ID.equals("Neutral") && player.stance != null)
            player.stance.stopIdleSfx();
        gridSelectScreen.upgradePreviewCard = null;
        previousScreen = null;
        dynamicBanner.hide();
        dungeonMapScreen.closeInstantly();
        closeCurrentScreen();
        topPanel.unhoverHitboxes();
        fadeIn();
        player.resetControllerValues();
        effectList.clear();
        Iterator i = topLevelEffects.iterator();
        do
        {
            if(!i.hasNext())
                break;
            AbstractGameEffect e = (AbstractGameEffect)i.next();
            if(!(e instanceof ObtainKeyEffect))
                i.remove();
        } while(true);
        topLevelEffectsQueue.clear();
        effectsQueue.clear();
        dungeonMapScreen.dismissable = true;
        dungeonMapScreen.map.legend.isLegendHighlighted = false;
        resetPlayer();
        if(!CardCrawlGame.loadingSave)
        {
            incrementFloorBasedMetrics();
            floorNum++;
            if(!((Boolean)TipTracker.tips.get("INTENT_TIP")).booleanValue() && floorNum == 6)
                TipTracker.neverShowAgain("INTENT_TIP");
            StatsScreen.incrementFloorClimbed();
            SaveHelper.saveIfAppropriate(com.megacrit.cardcrawl.saveAndContinue.SaveFile.SaveType.ENTER_ROOM);
        }
        monsterHpRng = new Random(Long.valueOf(Settings.seed.longValue() + (long)floorNum));
        aiRng = new Random(Long.valueOf(Settings.seed.longValue() + (long)floorNum));
        shuffleRng = new Random(Long.valueOf(Settings.seed.longValue() + (long)floorNum));
        cardRandomRng = new Random(Long.valueOf(Settings.seed.longValue() + (long)floorNum));
        miscRng = new Random(Long.valueOf(Settings.seed.longValue() + (long)floorNum));
        boolean isLoadingPostCombatSave = CardCrawlGame.loadingSave && saveFile != null && saveFile.post_combat;
        boolean isLoadingCompletedEvent = false;
        if(nextRoom != null && !isLoadingPostCombatSave)
        {
            AbstractRelic r;
            for(Iterator iterator = player.relics.iterator(); iterator.hasNext(); r.onEnterRoom(nextRoom.room))
                r = (AbstractRelic)iterator.next();

        }
        if(!actionManager.actions.isEmpty())
        {
            logger.info("[WARNING] Line:1904: Action Manager was NOT clear! Clearing");
            actionManager.clear();
        }
        if(nextRoom != null)
        {
            String roomMetricKey = nextRoom.room.getMapSymbol();
            if(nextRoom.room instanceof EventRoom)
            {
                Random eventRngDuplicate = new Random(Settings.seed, eventRng.counter);
                com.megacrit.cardcrawl.helpers.EventHelper.RoomResult roomResult = EventHelper.roll(eventRngDuplicate);
                isLoadingCompletedEvent = isLoadingPostCombatSave && roomResult == com.megacrit.cardcrawl.helpers.EventHelper.RoomResult.EVENT;
                if(!isLoadingCompletedEvent)
                {
                    eventRng = eventRngDuplicate;
                    nextRoom.room = generateRoom(roomResult);
                }
                roomMetricKey = nextRoom.room.getMapSymbol();
                if((nextRoom.room instanceof MonsterRoom) || (nextRoom.room instanceof MonsterRoomElite))
                    nextRoom.room.combatEvent = true;
                nextRoom.room.setMapSymbol("?");
                nextRoom.room.setMapImg(ImageMaster.MAP_NODE_EVENT, ImageMaster.MAP_NODE_EVENT_OUTLINE);
            }
            if(!isLoadingPostCombatSave)
                CardCrawlGame.metricData.path_per_floor.add(roomMetricKey);
            setCurrMapNode(nextRoom);
        }
        if(getCurrRoom() != null && !isLoadingPostCombatSave)
        {
            AbstractRelic r;
            for(Iterator iterator1 = player.relics.iterator(); iterator1.hasNext(); r.justEnteredRoom(getCurrRoom()))
                r = (AbstractRelic)iterator1.next();

        }
        if(isLoadingCompletedEvent)
        {
            getCurrRoom().phase = com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMPLETE;
            String eventKey = (String)((HashMap)saveFile.metric_event_choices.get(saveFile.metric_event_choices.size() - 1)).get("event_name");
            ((EventRoom)getCurrRoom()).event = EventHelper.getEvent(eventKey);
        } else
        {
            if(isAscensionMode)
                CardCrawlGame.publisherIntegration.setRichPresenceDisplayPlaying(floorNum, ascensionLevel, player.getLocalizedCharacterName());
            else
                CardCrawlGame.publisherIntegration.setRichPresenceDisplayPlaying(floorNum, player.getLocalizedCharacterName());
            getCurrRoom().onPlayerEntry();
        }
        if((getCurrRoom() instanceof MonsterRoom) && lastCombatMetricKey.equals("Shield and Spear"))
        {
            player.movePosition((float)Settings.WIDTH / 2.0F, floorY);
        } else
        {
            player.movePosition((float)Settings.WIDTH * 0.25F, floorY);
            player.flipHorizontal = false;
        }
        if((currMapNode.room instanceof MonsterRoom) && !isLoadingPostCombatSave)
            player.preBattlePrep();
        scene.nextRoom(currMapNode.room);
        if(currMapNode.room instanceof RestRoom)
            rs = RenderScene.CAMPFIRE;
        else
        if(currMapNode.room.event instanceof AbstractImageEvent)
            rs = RenderScene.EVENT;
        else
            rs = RenderScene.NORMAL;
    }

    private void incrementFloorBasedMetrics()
    {
        if(floorNum != 0)
        {
            CardCrawlGame.metricData.current_hp_per_floor.add(Integer.valueOf(player.currentHealth));
            CardCrawlGame.metricData.max_hp_per_floor.add(Integer.valueOf(player.maxHealth));
            CardCrawlGame.metricData.gold_per_floor.add(Integer.valueOf(player.gold));
        }
    }

    private AbstractRoom generateRoom(com.megacrit.cardcrawl.helpers.EventHelper.RoomResult roomType)
    {
        logger.info((new StringBuilder()).append("GENERATING ROOM: ").append(roomType.name()).toString());
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.helpers.EventHelper.RoomResult[roomType.ordinal()])
        {
        case 1: // '\001'
            return new MonsterRoomElite();

        case 2: // '\002'
            return new MonsterRoom();

        case 3: // '\003'
            return new ShopRoom();

        case 4: // '\004'
            return new TreasureRoom();
        }
        return new EventRoom();
    }

    public static MonsterGroup getMonsters()
    {
        return getCurrRoom().monsters;
    }

    public MonsterGroup getMonsterForRoomCreation()
    {
        if(monsterList.isEmpty())
            generateStrongEnemies(12);
        logger.info((new StringBuilder()).append("MONSTER: ").append((String)monsterList.get(0)).toString());
        lastCombatMetricKey = (String)monsterList.get(0);
        return MonsterHelper.getEncounter((String)monsterList.get(0));
    }

    public MonsterGroup getEliteMonsterForRoomCreation()
    {
        if(eliteMonsterList.isEmpty())
            generateElites(10);
        logger.info((new StringBuilder()).append("ELITE: ").append((String)eliteMonsterList.get(0)).toString());
        lastCombatMetricKey = (String)eliteMonsterList.get(0);
        return MonsterHelper.getEncounter((String)eliteMonsterList.get(0));
    }

    public static AbstractEvent generateEvent(Random rng)
    {
        if(rng.random(1.0F) < shrineChance)
        {
            if(!shrineList.isEmpty() || !specialOneTimeEventList.isEmpty())
                return getShrine(rng);
            if(!eventList.isEmpty())
            {
                return getEvent(rng);
            } else
            {
                logger.info("No events or shrines left");
                return null;
            }
        }
        AbstractEvent retVal = getEvent(rng);
        if(retVal == null)
            return getShrine(rng);
        else
            return retVal;
    }

    public static AbstractEvent getShrine(Random rng)
    {
        ArrayList tmp = new ArrayList();
        tmp.addAll(shrineList);
        String tmpKey = specialOneTimeEventList.iterator();
        do
        {
            if(!tmpKey.hasNext())
                break;
            String e = (String)tmpKey.next();
            String s = e;
            byte byte0 = -1;
            switch(s.hashCode())
            {
            case 1917982011: 
                if(s.equals("Fountain of Cleansing"))
                    byte0 = 0;
                break;

            case 1088076555: 
                if(s.equals("Designer"))
                    byte0 = 1;
                break;

            case 591082013: 
                if(s.equals("Duplicator"))
                    byte0 = 2;
                break;

            case -1699225493: 
                if(s.equals("FaceTrader"))
                    byte0 = 3;
                break;

            case 1121505076: 
                if(s.equals("Knowing Skull"))
                    byte0 = 4;
                break;

            case -2022548400: 
                if(s.equals("N'loth"))
                    byte0 = 5;
                break;

            case -207216254: 
                if(s.equals("The Joust"))
                    byte0 = 6;
                break;

            case 1167999560: 
                if(s.equals("The Woman in Blue"))
                    byte0 = 7;
                break;

            case 1051874140: 
                if(s.equals("SecretPortal"))
                    byte0 = 8;
                break;
            }
            switch(byte0)
            {
            case 0: // '\0'
                if(player.isCursed())
                    tmp.add(e);
                break;

            case 1: // '\001'
                if((id.equals("TheCity") || id.equals("TheBeyond")) && player.gold >= 75)
                    tmp.add(e);
                break;

            case 2: // '\002'
                if(id.equals("TheCity") || id.equals("TheBeyond"))
                    tmp.add(e);
                break;

            case 3: // '\003'
                if(id.equals("TheCity") || id.equals("Exordium"))
                    tmp.add(e);
                break;

            case 4: // '\004'
                if(id.equals("TheCity") && player.currentHealth > 12)
                    tmp.add(e);
                break;

            case 5: // '\005'
                if((id.equals("TheCity") || id.equals("TheCity")) && player.relics.size() >= 2)
                    tmp.add(e);
                break;

            case 6: // '\006'
                if(id.equals("TheCity") && player.gold >= 50)
                    tmp.add(e);
                break;

            case 7: // '\007'
                if(player.gold >= 50)
                    tmp.add(e);
                break;

            case 8: // '\b'
                if(CardCrawlGame.playtime >= 800F && id.equals("TheBeyond"))
                    tmp.add(e);
                break;

            default:
                tmp.add(e);
                break;
            }
        } while(true);
        tmpKey = (String)tmp.get(rng.random(tmp.size() - 1));
        shrineList.remove(tmpKey);
        specialOneTimeEventList.remove(tmpKey);
        logger.info((new StringBuilder()).append("Removed event: ").append(tmpKey).append(" from pool.").toString());
        return EventHelper.getEvent(tmpKey);
    }

    public static AbstractEvent getEvent(Random rng)
    {
        ArrayList tmp = new ArrayList();
        Iterator iterator = eventList.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            String e = (String)iterator.next();
            String s = e;
            byte byte0 = -1;
            switch(s.hashCode())
            {
            case 1824060574: 
                if(s.equals("Dead Adventurer"))
                    byte0 = 0;
                break;

            case -1914822917: 
                if(s.equals("Mushrooms"))
                    byte0 = 1;
                break;

            case 236474855: 
                if(s.equals("The Moai Head"))
                    byte0 = 2;
                break;

            case 1962578239: 
                if(s.equals("The Cleric"))
                    byte0 = 3;
                break;

            case 1985970164: 
                if(s.equals("Beggar"))
                    byte0 = 4;
                break;

            case -308228690: 
                if(s.equals("Colosseum"))
                    byte0 = 5;
                break;
            }
            switch(byte0)
            {
            case 0: // '\0'
                if(floorNum > 6)
                    tmp.add(e);
                break;

            case 1: // '\001'
                if(floorNum > 6)
                    tmp.add(e);
                break;

            case 2: // '\002'
                if(player.hasRelic("Golden Idol") || (float)player.currentHealth / (float)player.maxHealth <= 0.5F)
                    tmp.add(e);
                break;

            case 3: // '\003'
                if(player.gold >= 35)
                    tmp.add(e);
                break;

            case 4: // '\004'
                if(player.gold >= 75)
                    tmp.add(e);
                break;

            case 5: // '\005'
                if(currMapNode != null && currMapNode.y > map.size() / 2)
                    tmp.add(e);
                break;

            default:
                tmp.add(e);
                break;
            }
        } while(true);
        if(tmp.isEmpty())
        {
            return getShrine(rng);
        } else
        {
            String tmpKey = (String)tmp.get(rng.random(tmp.size() - 1));
            eventList.remove(tmpKey);
            logger.info((new StringBuilder()).append("Removed event: ").append(tmpKey).append(" from pool.").toString());
            return EventHelper.getEvent(tmpKey);
        }
    }

    public MonsterGroup getBoss()
    {
        lastCombatMetricKey = bossKey;
        dungeonMapScreen.map.atBoss = true;
        return MonsterHelper.getEncounter(bossKey);
    }

    public void update()
    {
        if(!CardCrawlGame.stopClock)
            CardCrawlGame.playtime += Gdx.graphics.getDeltaTime();
        if(CardCrawlGame.screenTimer > 0.0F)
        {
            InputHelper.justClickedLeft = false;
            CInputActionSet.select.unpress();
        }
        topPanel.update();
        dynamicBanner.update();
        updateFading();
        currMapNode.room.updateObjects();
        if(isScreenUp)
        {
            topGradientColor.a = MathHelper.fadeLerpSnap(topGradientColor.a, 0.25F);
            botGradientColor.a = MathHelper.fadeLerpSnap(botGradientColor.a, 0.25F);
        } else
        {
            topGradientColor.a = MathHelper.fadeLerpSnap(topGradientColor.a, 0.1F);
            botGradientColor.a = MathHelper.fadeLerpSnap(botGradientColor.a, 0.1F);
        }
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen[screen.ordinal()])
        {
        case 1: // '\001'
        case 2: // '\002'
            dungeonMapScreen.update();
            currMapNode.room.update();
            scene.update();
            currMapNode.room.eventControllerInput();
            break;

        case 3: // '\003'
            ftue.update();
            InputHelper.justClickedRight = false;
            InputHelper.justClickedLeft = false;
            currMapNode.room.update();
            break;

        case 4: // '\004'
            deckViewScreen.update();
            break;

        case 5: // '\005'
            gameDeckViewScreen.update();
            break;

        case 6: // '\006'
            discardPileViewScreen.update();
            break;

        case 7: // '\007'
            exhaustPileViewScreen.update();
            break;

        case 8: // '\b'
            settingsScreen.update();
            break;

        case 9: // '\t'
            inputSettingsScreen.update();
            break;

        case 10: // '\n'
            dungeonMapScreen.update();
            break;

        case 11: // '\013'
            gridSelectScreen.update();
            if(PeekButton.isPeeking)
                currMapNode.room.update();
            break;

        case 12: // '\f'
            cardRewardScreen.update();
            if(PeekButton.isPeeking)
                currMapNode.room.update();
            break;

        case 13: // '\r'
            combatRewardScreen.update();
            break;

        case 14: // '\016'
            bossRelicScreen.update();
            currMapNode.room.update();
            break;

        case 15: // '\017'
            handCardSelectScreen.update();
            currMapNode.room.update();
            break;

        case 16: // '\020'
            shopScreen.update();
            break;

        case 17: // '\021'
            deathScreen.update();
            break;

        case 18: // '\022'
            victoryScreen.update();
            break;

        case 19: // '\023'
            unlockScreen.update();
            break;

        case 20: // '\024'
            gUnlockScreen.update();
            break;

        case 21: // '\025'
            creditsScreen.update();
            break;

        case 22: // '\026'
            CardCrawlGame.mainMenuScreen.doorUnlockScreen.update();
            break;

        default:
            logger.info((new StringBuilder()).append("ERROR: UNKNOWN SCREEN TO UPDATE: ").append(screen.name()).toString());
            break;
        }
        turnPhaseEffectActive = false;
        Iterator i = topLevelEffects.iterator();
        do
        {
            if(!i.hasNext())
                break;
            AbstractGameEffect e = (AbstractGameEffect)i.next();
            e.update();
            if(e.isDone)
                i.remove();
        } while(true);
        i = effectList.iterator();
        do
        {
            if(!i.hasNext())
                break;
            AbstractGameEffect e = (AbstractGameEffect)i.next();
            e.update();
            if(e instanceof PlayerTurnEffect)
                turnPhaseEffectActive = true;
            if(e.isDone)
                i.remove();
        } while(true);
        for(i = effectsQueue.iterator(); i.hasNext(); i.remove())
        {
            AbstractGameEffect e = (AbstractGameEffect)i.next();
            effectList.add(e);
        }

        for(i = topLevelEffectsQueue.iterator(); i.hasNext(); i.remove())
        {
            AbstractGameEffect e = (AbstractGameEffect)i.next();
            topLevelEffects.add(e);
        }

        overlayMenu.update();
    }

    public void render(SpriteBatch sb)
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.dungeons.AbstractDungeon.RenderScene[rs.ordinal()])
        {
        case 1: // '\001'
            scene.renderCombatRoomBg(sb);
            break;

        case 2: // '\002'
            scene.renderCampfireRoom(sb);
            renderLetterboxGradient(sb);
            break;

        case 3: // '\003'
            scene.renderEventRoom(sb);
            break;
        }
        AbstractRoom room = effectList.iterator();
        do
        {
            if(!room.hasNext())
                break;
            AbstractGameEffect e = (AbstractGameEffect)room.next();
            if(e.renderBehind)
                e.render(sb);
        } while(true);
        currMapNode.room.render(sb);
        if(rs == RenderScene.NORMAL)
            scene.renderCombatRoomFg(sb);
        if(rs != RenderScene.CAMPFIRE)
            renderLetterboxGradient(sb);
        room = getCurrRoom();
        if((room instanceof EventRoom) || (room instanceof NeowRoom) || (room instanceof VictoryRoom))
            room.renderEventTexts(sb);
        Iterator iterator = effectList.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractGameEffect e = (AbstractGameEffect)iterator.next();
            if(!e.renderBehind)
                e.render(sb);
        } while(true);
        overlayMenu.render(sb);
        overlayMenu.renderBlackScreen(sb);
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen[screen.ordinal()])
        {
        case 2: // '\002'
            dungeonMapScreen.render(sb);
            break;

        case 4: // '\004'
            deckViewScreen.render(sb);
            break;

        case 6: // '\006'
            discardPileViewScreen.render(sb);
            break;

        case 5: // '\005'
            gameDeckViewScreen.render(sb);
            break;

        case 7: // '\007'
            exhaustPileViewScreen.render(sb);
            break;

        case 8: // '\b'
            settingsScreen.render(sb);
            break;

        case 9: // '\t'
            inputSettingsScreen.render(sb);
            break;

        case 10: // '\n'
            dungeonMapScreen.render(sb);
            break;

        case 11: // '\013'
            gridSelectScreen.render(sb);
            break;

        case 12: // '\f'
            cardRewardScreen.render(sb);
            break;

        case 13: // '\r'
            combatRewardScreen.render(sb);
            break;

        case 14: // '\016'
            bossRelicScreen.render(sb);
            break;

        case 15: // '\017'
            handCardSelectScreen.render(sb);
            break;

        case 16: // '\020'
            shopScreen.render(sb);
            break;

        case 17: // '\021'
            deathScreen.render(sb);
            break;

        case 18: // '\022'
            victoryScreen.render(sb);
            break;

        case 19: // '\023'
            unlockScreen.render(sb);
            break;

        case 22: // '\026'
            CardCrawlGame.mainMenuScreen.doorUnlockScreen.render(sb);
            break;

        case 20: // '\024'
            gUnlockScreen.render(sb);
            break;

        case 21: // '\025'
            creditsScreen.render(sb);
            break;

        default:
            logger.info((new StringBuilder()).append("ERROR: UNKNOWN SCREEN TO RENDER: ").append(screen.name()).toString());
            break;

        case 1: // '\001'
        case 3: // '\003'
            break;
        }
        if(screen != CurrentScreen.UNLOCK)
        {
            sb.setColor(topGradientColor);
            if(!Settings.hideTopBar)
                sb.draw(ImageMaster.SCROLL_GRADIENT, 0.0F, (float)Settings.HEIGHT - 128F * Settings.scale, Settings.WIDTH, 64F * Settings.scale);
            sb.setColor(botGradientColor);
            if(!Settings.hideTopBar)
                sb.draw(ImageMaster.SCROLL_GRADIENT, 0.0F, 64F * Settings.scale, Settings.WIDTH, -64F * Settings.scale);
        }
        if(screen == CurrentScreen.FTUE)
            ftue.render(sb);
        overlayMenu.cancelButton.render(sb);
        dynamicBanner.render(sb);
        if(screen != CurrentScreen.UNLOCK)
            topPanel.render(sb);
        currMapNode.room.renderAboveTopPanel(sb);
        iterator = topLevelEffects.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractGameEffect e = (AbstractGameEffect)iterator.next();
            if(!e.renderBehind)
                e.render(sb);
        } while(true);
        sb.setColor(fadeColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
    }

    private void renderLetterboxGradient(SpriteBatch spritebatch)
    {
    }

    public void updateFading()
    {
        if(isFadingIn)
        {
            fadeTimer -= Gdx.graphics.getDeltaTime();
            fadeColor.a = Interpolation.fade.apply(0.0F, 1.0F, fadeTimer / 0.8F);
            if(fadeTimer < 0.0F)
            {
                isFadingIn = false;
                fadeColor.a = 0.0F;
                fadeTimer = 0.0F;
            }
        } else
        if(isFadingOut)
        {
            fadeTimer -= Gdx.graphics.getDeltaTime();
            fadeColor.a = Interpolation.fade.apply(1.0F, 0.0F, fadeTimer / 0.8F);
            if(fadeTimer < 0.0F)
            {
                fadeTimer = 0.0F;
                isFadingOut = false;
                fadeColor.a = 1.0F;
                if(!isDungeonBeaten)
                    nextRoomTransition();
            }
        }
    }

    public static void closeCurrentScreen()
    {
        PeekButton.isPeeking = false;
        if(previousScreen == screen)
            previousScreen = null;
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen[screen.ordinal()])
        {
        case 4: // '\004'
            overlayMenu.cancelButton.hide();
            genericScreenOverlayReset();
            AbstractCard c;
            for(Iterator iterator = player.masterDeck.group.iterator(); iterator.hasNext(); c.untip())
            {
                c = (AbstractCard)iterator.next();
                c.unhover();
            }

            break;

        case 6: // '\006'
            overlayMenu.cancelButton.hide();
            genericScreenOverlayReset();
            AbstractCard c;
            for(Iterator iterator1 = player.discardPile.group.iterator(); iterator1.hasNext(); c.unhover())
            {
                c = (AbstractCard)iterator1.next();
                c.drawScale = 0.12F;
                c.targetDrawScale = 0.12F;
                c.teleportToDiscardPile();
                c.darken(true);
            }

            break;

        case 3: // '\003'
            genericScreenOverlayReset();
            break;

        case 5: // '\005'
            overlayMenu.cancelButton.hide();
            genericScreenOverlayReset();
            break;

        case 7: // '\007'
            overlayMenu.cancelButton.hide();
            genericScreenOverlayReset();
            break;

        case 8: // '\b'
            overlayMenu.cancelButton.hide();
            genericScreenOverlayReset();
            settingsScreen.abandonPopup.hide();
            settingsScreen.exitPopup.hide();
            break;

        case 9: // '\t'
            overlayMenu.cancelButton.hide();
            genericScreenOverlayReset();
            settingsScreen.abandonPopup.hide();
            settingsScreen.exitPopup.hide();
            break;

        case 20: // '\024'
            genericScreenOverlayReset();
            CardCrawlGame.sound.stop("UNLOCK_SCREEN", gUnlockScreen.id);
            break;

        case 11: // '\013'
            genericScreenOverlayReset();
            if(!combatRewardScreen.rewards.isEmpty())
                previousScreen = CurrentScreen.COMBAT_REWARD;
            break;

        case 12: // '\f'
            overlayMenu.cancelButton.hide();
            dynamicBanner.hide();
            genericScreenOverlayReset();
            if(!screenSwap)
                cardRewardScreen.onClose();
            break;

        case 13: // '\r'
            dynamicBanner.hide();
            genericScreenOverlayReset();
            break;

        case 14: // '\016'
            genericScreenOverlayReset();
            dynamicBanner.hide();
            break;

        case 15: // '\017'
            genericScreenOverlayReset();
            overlayMenu.showCombatPanels();
            break;

        case 10: // '\n'
            genericScreenOverlayReset();
            dungeonMapScreen.close();
            if(!firstRoomChosen && nextRoom != null && !dungeonMapScreen.dismissable)
            {
                firstRoomChosen = true;
                firstRoomLogic();
            }
            break;

        case 16: // '\020'
            CardCrawlGame.sound.play("SHOP_CLOSE");
            genericScreenOverlayReset();
            overlayMenu.cancelButton.hide();
            break;

        case 23: // '\027'
            CardCrawlGame.sound.play("ATTACK_MAGIC_SLOW_1");
            genericScreenOverlayReset();
            overlayMenu.cancelButton.hide();
            break;

        case 17: // '\021'
        case 18: // '\022'
        case 19: // '\023'
        case 21: // '\025'
        case 22: // '\026'
        default:
            logger.info((new StringBuilder()).append("UNSPECIFIED CASE: ").append(screen.name()).toString());
            break;
        }
        if(previousScreen == null)
            screen = CurrentScreen.NONE;
        else
        if(screenSwap)
        {
            screenSwap = false;
        } else
        {
            screen = previousScreen;
            previousScreen = null;
            if(getCurrRoom().rewardTime)
                previousScreen = CurrentScreen.COMBAT_REWARD;
            isScreenUp = true;
            openPreviousScreen(screen);
        }
    }

    private static void openPreviousScreen(CurrentScreen s)
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen[s.ordinal()])
        {
        case 8: // '\b'
        case 9: // '\t'
        case 19: // '\023'
        default:
            break;

        case 17: // '\021'
            deathScreen.reopen();
            break;

        case 18: // '\022'
            victoryScreen.reopen();
            break;

        case 4: // '\004'
            deckViewScreen.open();
            break;

        case 12: // '\f'
            cardRewardScreen.reopen();
            if(cardRewardScreen.rItem != null)
                previousScreen = CurrentScreen.COMBAT_REWARD;
            break;

        case 6: // '\006'
            discardPileViewScreen.reopen();
            break;

        case 7: // '\007'
            exhaustPileViewScreen.reopen();
            break;

        case 5: // '\005'
            gameDeckViewScreen.reopen();
            break;

        case 15: // '\017'
            overlayMenu.hideBlackScreen();
            handCardSelectScreen.reopen();
            break;

        case 13: // '\r'
            combatRewardScreen.reopen();
            break;

        case 14: // '\016'
            bossRelicScreen.reopen();
            break;

        case 16: // '\020'
            shopScreen.open();
            break;

        case 11: // '\013'
            overlayMenu.hideBlackScreen();
            if(gridSelectScreen.isJustForConfirming)
                dynamicBanner.appear();
            gridSelectScreen.reopen();
            break;

        case 20: // '\024'
            gUnlockScreen.reOpen();
            break;

        case 10: // '\n'
            if(dungeonMapScreen.dismissable)
                overlayMenu.cancelButton.show(DungeonMapScreen.TEXT[1]);
            else
                overlayMenu.cancelButton.hide();
            break;
        }
    }

    private static void genericScreenOverlayReset()
    {
        if(previousScreen == null)
            if(player.isDead)
            {
                previousScreen = CurrentScreen.DEATH;
            } else
            {
                isScreenUp = false;
                overlayMenu.hideBlackScreen();
            }
        if(getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT && !player.isDead)
            overlayMenu.showCombatPanels();
    }

    public static void fadeIn()
    {
        if(fadeColor.a != 1.0F)
            logger.info("WARNING: Attempting to fade in even though screen is not black");
        isFadingIn = true;
        if(Settings.FAST_MODE)
            fadeTimer = 0.001F;
        else
            fadeTimer = 0.8F;
    }

    public static void fadeOut()
    {
        if(fadeTimer == 0.0F)
        {
            if(fadeColor.a != 0.0F)
                logger.info("WARNING: Attempting to fade out even though screen is not transparent");
            isFadingOut = true;
            if(Settings.FAST_MODE)
                fadeTimer = 0.001F;
            else
                fadeTimer = 0.8F;
        }
    }

    public static void dungeonTransitionSetup()
    {
        actNum++;
        if(cardRng.counter > 0 && cardRng.counter < 250)
            cardRng.setCounter(250);
        else
        if(cardRng.counter > 250 && cardRng.counter < 500)
            cardRng.setCounter(500);
        else
        if(cardRng.counter > 500 && cardRng.counter < 750)
            cardRng.setCounter(750);
        logger.info((new StringBuilder()).append("CardRng Counter: ").append(cardRng.counter).toString());
        topPanel.unhoverHitboxes();
        pathX.clear();
        pathY.clear();
        EventHelper.resetProbabilities();
        eventList.clear();
        shrineList.clear();
        monsterList.clear();
        eliteMonsterList.clear();
        bossList.clear();
        AbstractRoom.blizzardPotionMod = 0;
        if(ascensionLevel >= 5)
            player.heal(MathUtils.round((float)(player.maxHealth - player.currentHealth) * 0.75F), false);
        else
            player.heal(player.maxHealth, false);
        if(floorNum > 1)
            topPanel.panelHealEffect();
        if(floorNum <= 1 && (CardCrawlGame.dungeon instanceof Exordium))
        {
            if(ascensionLevel >= 14)
                player.decreaseMaxHealth(player.getAscensionMaxHPLoss());
            if(ascensionLevel >= 6)
                player.currentHealth = MathUtils.round((float)player.maxHealth * 0.9F);
            if(ascensionLevel >= 10)
            {
                player.masterDeck.addToTop(new AscendersBane());
                UnlockTracker.markCardAsSeen("AscendersBane");
            }
            CardCrawlGame.playtime = 0.0F;
        }
        dungeonMapScreen.map.atBoss = false;
    }

    public static void reset()
    {
        logger.info("Resetting variables...");
        CardCrawlGame.resetScoreVars();
        ModHelper.setModsFalse();
        floorNum = 0;
        actNum = 0;
        if(currMapNode != null && getCurrRoom() != null)
        {
            getCurrRoom().dispose();
            if(getCurrRoom().monsters != null)
            {
                AbstractMonster m;
                for(Iterator iterator = getCurrRoom().monsters.monsters.iterator(); iterator.hasNext(); m.dispose())
                    m = (AbstractMonster)iterator.next();

            }
        }
        currMapNode = null;
        shrineList.clear();
        relicsToRemoveOnStart.clear();
        previousScreen = null;
        actionManager.clear();
        actionManager.clearNextRoomCombatActions();
        combatRewardScreen.clear();
        cardRewardScreen.reset();
        if(dungeonMapScreen != null)
            dungeonMapScreen.closeInstantly();
        effectList.clear();
        effectsQueue.clear();
        topLevelEffectsQueue.clear();
        topLevelEffects.clear();
        cardBlizzRandomizer = cardBlizzStartOffset;
        if(player != null)
            player.relics.clear();
        rs = RenderScene.NORMAL;
        blightPool.clear();
    }

    protected void removeRelicFromPool(ArrayList pool, String name)
    {
        Iterator i = pool.iterator();
        do
        {
            if(!i.hasNext())
                break;
            String s = (String)i.next();
            if(s.equals(name))
            {
                i.remove();
                logger.info((new StringBuilder()).append("Relic").append(s).append(" removed from relic pool.").toString());
            }
        } while(true);
    }

    public static void onModifyPower()
    {
        if(player != null)
        {
            player.hand.applyPowers();
            if(player.hasPower("Focus"))
            {
                AbstractOrb o;
                for(Iterator iterator = player.orbs.iterator(); iterator.hasNext(); o.updateDescription())
                    o = (AbstractOrb)iterator.next();

            }
        }
        if(getCurrRoom().monsters != null)
        {
            AbstractMonster m;
            for(Iterator iterator1 = getCurrRoom().monsters.monsters.iterator(); iterator1.hasNext(); m.applyPowers())
                m = (AbstractMonster)iterator1.next();

        }
    }

    public void checkForPactAchievement()
    {
        if(player != null && player.exhaustPile.size() >= 20)
            UnlockTracker.unlockAchievement("THE_PACT");
    }

    public void loadSave(SaveFile saveFile)
    {
        floorNum = saveFile.floor_num;
        actNum = saveFile.act_num;
        Settings.seed = Long.valueOf(saveFile.seed);
        loadSeeds(saveFile);
        monsterList = saveFile.monster_list;
        eliteMonsterList = saveFile.elite_monster_list;
        bossList = saveFile.boss_list;
        setBoss(saveFile.boss);
        commonRelicPool = saveFile.common_relics;
        uncommonRelicPool = saveFile.uncommon_relics;
        rareRelicPool = saveFile.rare_relics;
        shopRelicPool = saveFile.shop_relics;
        bossRelicPool = saveFile.boss_relics;
        pathX = saveFile.path_x;
        pathY = saveFile.path_y;
        bossCount = saveFile.spirit_count;
        eventList = saveFile.event_list;
        specialOneTimeEventList = saveFile.one_time_event_list;
        EventHelper.setChances(saveFile.event_chances);
        AbstractRoom.blizzardPotionMod = saveFile.potion_chance;
        ShopScreen.purgeCost = saveFile.purgeCost;
        CardHelper.obtainedCards = saveFile.obtained_cards;
        if(saveFile.daily_mods != null)
            ModHelper.setMods(saveFile.daily_mods);
    }

    public static AbstractBlight getBlight(String targetID)
    {
        for(Iterator iterator = blightPool.iterator(); iterator.hasNext();)
        {
            AbstractBlight b = (AbstractBlight)iterator.next();
            if(b.blightID.equals(targetID))
                return b;
        }

        return null;
    }

    protected static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/dungeons/AbstractDungeon.getName());
    private static final UIStrings uiStrings;
    public static final String TEXT[];
    public static String name;
    public static String levelNum;
    public static String id;
    public static int floorNum = 0;
    public static int actNum = 0;
    public static AbstractPlayer player;
    public static ArrayList unlocks = new ArrayList();
    protected static float shrineChance = 0.25F;
    protected static float cardUpgradedChance;
    public static AbstractCard transformedCard;
    public static boolean loading_post_combat = false;
    public static boolean is_victory = false;
    public static Texture eventBackgroundImg;
    public static Random monsterRng;
    public static Random mapRng;
    public static Random eventRng;
    public static Random merchantRng;
    public static Random cardRng;
    public static Random treasureRng;
    public static Random relicRng;
    public static Random potionRng;
    public static Random monsterHpRng;
    public static Random aiRng;
    public static Random shuffleRng;
    public static Random cardRandomRng;
    public static Random miscRng;
    public static CardGroup srcColorlessCardPool;
    public static CardGroup srcCurseCardPool;
    public static CardGroup srcCommonCardPool;
    public static CardGroup srcUncommonCardPool;
    public static CardGroup srcRareCardPool;
    public static CardGroup colorlessCardPool;
    public static CardGroup curseCardPool;
    public static CardGroup commonCardPool;
    public static CardGroup uncommonCardPool;
    public static CardGroup rareCardPool;
    public static ArrayList commonRelicPool = new ArrayList();
    public static ArrayList uncommonRelicPool = new ArrayList();
    public static ArrayList rareRelicPool = new ArrayList();
    public static ArrayList shopRelicPool = new ArrayList();
    public static ArrayList bossRelicPool = new ArrayList();
    public static String lastCombatMetricKey = null;
    public static ArrayList monsterList = new ArrayList();
    public static ArrayList eliteMonsterList = new ArrayList();
    public static ArrayList bossList = new ArrayList();
    public static String bossKey;
    public static ArrayList eventList = new ArrayList();
    public static ArrayList shrineList = new ArrayList();
    public static ArrayList specialOneTimeEventList = new ArrayList();
    public static GameActionManager actionManager = new GameActionManager();
    public static ArrayList topLevelEffects = new ArrayList();
    public static ArrayList topLevelEffectsQueue = new ArrayList();
    public static ArrayList effectList = new ArrayList();
    public static ArrayList effectsQueue = new ArrayList();
    public static boolean turnPhaseEffectActive = false;
    public static float colorlessRareChance;
    protected static float shopRoomChance;
    protected static float restRoomChance;
    protected static float eventRoomChance;
    protected static float eliteRoomChance;
    protected static float treasureRoomChance;
    protected static int smallChestChance;
    protected static int mediumChestChance;
    protected static int largeChestChance;
    protected static int commonRelicChance;
    protected static int uncommonRelicChance;
    protected static int rareRelicChance;
    public static AbstractScene scene;
    public static MapRoomNode currMapNode;
    public static ArrayList map;
    public static boolean leftRoomAvailable;
    public static boolean centerRoomAvailable;
    public static boolean rightRoomAvailable;
    public static boolean firstRoomChosen = false;
    public static final int MAP_HEIGHT = 15;
    public static final int MAP_WIDTH = 7;
    public static final int MAP_DENSITY = 6;
    public static final int FINAL_ACT_MAP_HEIGHT = 3;
    public static RenderScene rs;
    public static ArrayList pathX = new ArrayList();
    public static ArrayList pathY = new ArrayList();
    public static Color topGradientColor = new Color(1.0F, 1.0F, 1.0F, 0.25F);
    public static Color botGradientColor = new Color(1.0F, 1.0F, 1.0F, 0.25F);
    public static float floorY;
    public static TopPanel topPanel = new TopPanel();
    public static CardRewardScreen cardRewardScreen = new CardRewardScreen();
    public static CombatRewardScreen combatRewardScreen = new CombatRewardScreen();
    public static BossRelicSelectScreen bossRelicScreen = new BossRelicSelectScreen();
    public static MasterDeckViewScreen deckViewScreen = new MasterDeckViewScreen();
    public static DiscardPileViewScreen discardPileViewScreen = new DiscardPileViewScreen();
    public static DrawPileViewScreen gameDeckViewScreen = new DrawPileViewScreen();
    public static ExhaustPileViewScreen exhaustPileViewScreen = new ExhaustPileViewScreen();
    public static SettingsScreen settingsScreen = new SettingsScreen();
    public static InputSettingsScreen inputSettingsScreen = new InputSettingsScreen();
    public static DungeonMapScreen dungeonMapScreen = new DungeonMapScreen();
    public static GridCardSelectScreen gridSelectScreen = new GridCardSelectScreen();
    public static HandCardSelectScreen handCardSelectScreen = new HandCardSelectScreen();
    public static ShopScreen shopScreen = new ShopScreen();
    public static CreditsScreen creditsScreen = null;
    public static FtueTip ftue = null;
    public static DeathScreen deathScreen;
    public static VictoryScreen victoryScreen;
    public static UnlockCharacterScreen unlockScreen = new UnlockCharacterScreen();
    public static NeowUnlockScreen gUnlockScreen = new NeowUnlockScreen();
    public static boolean isScreenUp = false;
    public static OverlayMenu overlayMenu;
    public static CurrentScreen screen;
    public static CurrentScreen previousScreen;
    public static DynamicBanner dynamicBanner;
    public static boolean screenSwap = false;
    public static boolean isDungeonBeaten;
    public static int cardBlizzStartOffset;
    public static int cardBlizzRandomizer;
    public static int cardBlizzGrowth = 1;
    public static int cardBlizzMaxOffset = -40;
    public static boolean isFadingIn;
    public static boolean isFadingOut;
    public static boolean waitingOnFadeOut;
    protected static float fadeTimer;
    public static Color fadeColor;
    public static Color sourceFadeColor;
    public static MapRoomNode nextRoom;
    public static float sceneOffsetY = 0.0F;
    public static ArrayList relicsToRemoveOnStart = new ArrayList();
    public static int bossCount = 0;
    public static final float SCENE_OFFSET_TIME = 1.3F;
    public static boolean isAscensionMode = false;
    public static int ascensionLevel = 0;
    public static ArrayList blightPool = new ArrayList();
    public static boolean ascensionCheck;
    private static final Logger LOGGER = LogManager.getLogger(com/megacrit/cardcrawl/dungeons/AbstractDungeon.getName());

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("AbstractDungeon");
        TEXT = uiStrings.TEXT;
        srcColorlessCardPool = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.CARD_POOL);
        srcCurseCardPool = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.CARD_POOL);
        srcCommonCardPool = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.CARD_POOL);
        srcUncommonCardPool = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.CARD_POOL);
        srcRareCardPool = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.CARD_POOL);
        colorlessCardPool = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.CARD_POOL);
        curseCardPool = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.CARD_POOL);
        commonCardPool = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.CARD_POOL);
        uncommonCardPool = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.CARD_POOL);
        rareCardPool = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.CARD_POOL);
        rs = RenderScene.NORMAL;
        floorY = 340F * Settings.yScale;
        cardBlizzStartOffset = 5;
        cardBlizzRandomizer = cardBlizzStartOffset;
    }
}
