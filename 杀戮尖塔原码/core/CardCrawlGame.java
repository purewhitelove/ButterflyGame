// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CardCrawlGame.java

package com.megacrit.cardcrawl.core;

import com.badlogic.gdx.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.codedisaster.steamworks.*;
import com.esotericsoftware.spine.SkeletonRendererDebug;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.CharacterManager;
import com.megacrit.cardcrawl.credits.CreditsScreen;
import com.megacrit.cardcrawl.daily.TimeHelper;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.controller.CInputHelper;
import com.megacrit.cardcrawl.helpers.input.*;
import com.megacrit.cardcrawl.helpers.steamInput.SteamInputHelper;
import com.megacrit.cardcrawl.integrations.*;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.metrics.BotDataUploader;
import com.megacrit.cardcrawl.metrics.MetricData;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rewards.RewardSave;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import com.megacrit.cardcrawl.scenes.AbstractScene;
import com.megacrit.cardcrawl.scenes.TitleBackground;
import com.megacrit.cardcrawl.screens.*;
import com.megacrit.cardcrawl.screens.custom.CustomModeScreen;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import com.megacrit.cardcrawl.screens.splash.SplashScreen;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.trials.AbstractTrial;
import com.megacrit.cardcrawl.ui.buttons.CancelButton;
import com.megacrit.cardcrawl.ui.panels.SeedPanel;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import de.robojumper.ststwitch.TwitchConfig;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.core:
//            BuildSettings, GameCursor, EnergyManager, Settings, 
//            AbstractCreature, ExceptionHandler, SystemStats, OverlayMenu

public class CardCrawlGame
    implements ApplicationListener
{
    public static final class GameMode extends Enum
    {

        public static GameMode[] values()
        {
            return (GameMode[])$VALUES.clone();
        }

        public static GameMode valueOf(String name)
        {
            return (GameMode)Enum.valueOf(com/megacrit/cardcrawl/core/CardCrawlGame$GameMode, name);
        }

        public static final GameMode CHAR_SELECT;
        public static final GameMode GAMEPLAY;
        public static final GameMode DUNGEON_TRANSITION;
        public static final GameMode SPLASH;
        private static final GameMode $VALUES[];

        static 
        {
            CHAR_SELECT = new GameMode("CHAR_SELECT", 0);
            GAMEPLAY = new GameMode("GAMEPLAY", 1);
            DUNGEON_TRANSITION = new GameMode("DUNGEON_TRANSITION", 2);
            SPLASH = new GameMode("SPLASH", 3);
            $VALUES = (new GameMode[] {
                CHAR_SELECT, GAMEPLAY, DUNGEON_TRANSITION, SPLASH
            });
        }

        private GameMode(String s, int i)
        {
            super(s, i);
        }
    }


    public CardCrawlGame(String prefDir)
    {
        fpsLogger = new FPSLogger();
        prevDebugKeyDown = false;
        steamInputHelper = null;
        displayCursor = true;
        clUtilsCallback = new SteamUtilsCallback() {

            public void onSteamShutdown()
            {
                CardCrawlGame.logger.error("Steam client requested to shut down!");
            }

            final CardCrawlGame this$0;

            
            {
                this.this$0 = CardCrawlGame.this;
                super();
            }
        }
;
        preferenceDir = prefDir;
    }

    public void create()
    {
        if(Settings.isAlpha)
        {
            TRUE_VERSION_NUM = (new StringBuilder()).append(TRUE_VERSION_NUM).append(" ALPHA").toString();
            VERSION_NUM = (new StringBuilder()).append(VERSION_NUM).append(" ALPHA").toString();
        } else
        if(Settings.isBeta)
            VERSION_NUM = (new StringBuilder()).append(VERSION_NUM).append(" BETA").toString();
        try
        {
            TwitchConfig.createConfig();
            BuildSettings buildSettings = new BuildSettings(Gdx.files.internal("build.properties").reader());
            logger.info((new StringBuilder()).append("DistributorPlatform=").append(buildSettings.getDistributor()).toString());
            logger.info((new StringBuilder()).append("isModded=").append(Settings.isModded).toString());
            logger.info((new StringBuilder()).append("isBeta=").append(Settings.isBeta).toString());
            publisherIntegration = DistributorFactory.getEnabledDistributor(buildSettings.getDistributor());
            saveMigration();
            saveSlotPref = SaveHelper.getPrefs("STSSaveSlots");
            saveSlot = saveSlotPref.getInteger("DEFAULT_SLOT", 0);
            playerPref = SaveHelper.getPrefs("STSPlayer");
            playerName = saveSlotPref.getString(SaveHelper.slotName("PROFILE_NAME", saveSlot), "");
            if(playerName.equals(""))
                playerName = playerPref.getString("name", "");
            alias = playerPref.getString("alias", "");
            if(alias.equals(""))
            {
                alias = generateRandomAlias();
                playerPref.putString("alias", alias);
                playerPref.flush();
            }
            Settings.initialize(false);
            camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            if(Settings.VERT_LETTERBOX_AMT != 0 || Settings.HORIZ_LETTERBOX_AMT != 0)
            {
                camera.position.set((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, 0.0F);
                camera.update();
                viewport = new FitViewport(Settings.WIDTH, Settings.M_H - Settings.HEIGHT / 2, camera);
            } else
            {
                camera.position.set(camera.viewportWidth / 2.0F, camera.viewportHeight / 2.0F, 0.0F);
                camera.update();
                viewport = new FitViewport(Settings.WIDTH, Settings.HEIGHT, camera);
                viewport.apply();
            }
            languagePack = new LocalizedStrings();
            cardPopup = new SingleCardViewPopup();
            relicPopup = new SingleRelicViewPopup();
            if(Settings.IS_FULLSCREEN)
                resize(Settings.M_W, Settings.M_H);
            Gdx.graphics.setCursor(Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("images/blank.png")), 0, 0));
            sb = new SpriteBatch();
            psb = new PolygonSpriteBatch();
            music = new MusicMaster();
            sound = new SoundMaster();
            AbstractCreature.initialize();
            AbstractCard.initialize();
            GameDictionary.initialize();
            ImageMaster.initialize();
            AbstractPower.initialize();
            FontHelper.initialize();
            AbstractCard.initializeDynamicFrameWidths();
            UnlockTracker.initialize();
            CardLibrary.initialize();
            RelicLibrary.initialize();
            InputHelper.initialize();
            TipTracker.initialize();
            ModHelper.initialize();
            ShaderHelper.initializeShaders();
            UnlockTracker.retroactiveUnlock();
            CInputHelper.loadSettings();
            clientUtils = new SteamUtils(clUtilsCallback);
            steamInputHelper = new SteamInputHelper();
            steelSeries = new SteelSeries();
            cursor = new GameCursor();
            metricData = new MetricData();
            cancelButton = new CancelButton();
            tips = new GameTips();
            characterManager = new CharacterManager();
            splashScreen = new SplashScreen();
            mode = GameMode.SPLASH;
        }
        catch(Exception e)
        {
            logger.info("Exception occurred in CardCrawlGame create method!");
            ExceptionHandler.handleException(e, logger);
            Gdx.app.exit();
        }
    }

    public static void reloadPrefs()
    {
        playerPref = SaveHelper.getPrefs("STSPlayer");
        alias = playerPref.getString("alias", "");
        if(alias.equals(""))
        {
            alias = generateRandomAlias();
            playerPref.putString("alias", alias);
        }
        music.fadeOutBGM();
        mainMenuScreen.fadeOutMusic();
        InputActionSet.prefs = SaveHelper.getPrefs("STSInputSettings");
        InputActionSet.load();
        CInputActionSet.prefs = SaveHelper.getPrefs("STSInputSettings_Controller");
        CInputActionSet.load();
        if(SteamInputHelper.numControllers == 1)
            SteamInputHelper.initActions(SteamInputHelper.controllerHandles[0]);
        characterManager = new CharacterManager();
        Settings.initialize(true);
        UnlockTracker.initialize();
        CardLibrary.resetForReload();
        CardLibrary.initialize();
        RelicLibrary.resetForReload();
        RelicLibrary.initialize();
        TipTracker.initialize();
        logger.info((new StringBuilder()).append("TEXTURE COUNT: ").append(Texture.getNumManagedTextures()).toString());
        screenColor.a = 0.0F;
        screenTime = 0.01F;
        screenTimer = 0.01F;
        fadeIn = false;
        startOver = true;
    }

    public void saveMigration()
    {
        if(!SaveHelper.saveExists())
        {
            Preferences p = Gdx.app.getPreferences("STSPlayer");
            if(p.getString("name", "").equals("") && Gdx.app.getPreferences("STSDataVagabond").getLong("PLAYTIME") == 0L)
            {
                logger.info("New player, no migration.");
                return;
            }
            logger.info("Migrating Save...");
            migrateHelper("STSPlayer");
            migrateHelper("STSUnlocks");
            migrateHelper("STSUnlockProgress");
            migrateHelper("STSTips");
            migrateHelper("STSSound");
            migrateHelper("STSSeenRelics");
            migrateHelper("STSSeenCards");
            migrateHelper("STSSeenBosses");
            migrateHelper("STSGameplaySettings");
            migrateHelper("STSDataVagabond");
            migrateHelper("STSDataTheSilent");
            migrateHelper("STSAchievements");
            if(MathUtils.randomBoolean(0.5F))
                logger.warn("Save Migration");
        } else
        {
            logger.info("No migration");
        }
    }

    public void migrateHelper(String file)
    {
        Preferences p = Gdx.app.getPreferences(file);
        Prefs p2 = SaveHelper.getPrefs(file);
        Map map = p.get();
        java.util.Map.Entry c;
        for(Iterator iterator = map.entrySet().iterator(); iterator.hasNext(); p2.putString((String)c.getKey(), p.getString((String)c.getKey())))
            c = (java.util.Map.Entry)iterator.next();

        p2.flush();
    }

    public void render()
    {
        TimeHelper.update();
        if(Gdx.graphics.getRawDeltaTime() > 0.1F)
            return;
        try
        {
            if(!SteamInputHelper.alive)
                CInputHelper.initializeIfAble();
            update();
            sb.setProjectionMatrix(camera.combined);
            psb.setProjectionMatrix(camera.combined);
            Gdx.gl.glClear(16384);
            sb.begin();
            sb.setColor(Color.WHITE);
            static class _cls2
            {

                static final int $SwitchMap$com$megacrit$cardcrawl$core$CardCrawlGame$GameMode[];
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
                    $SwitchMap$com$megacrit$cardcrawl$core$CardCrawlGame$GameMode = new int[GameMode.values().length];
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$core$CardCrawlGame$GameMode[GameMode.SPLASH.ordinal()] = 1;
                    }
                    catch(NoSuchFieldError nosuchfielderror3) { }
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$core$CardCrawlGame$GameMode[GameMode.CHAR_SELECT.ordinal()] = 2;
                    }
                    catch(NoSuchFieldError nosuchfielderror4) { }
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$core$CardCrawlGame$GameMode[GameMode.GAMEPLAY.ordinal()] = 3;
                    }
                    catch(NoSuchFieldError nosuchfielderror5) { }
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$core$CardCrawlGame$GameMode[GameMode.DUNGEON_TRANSITION.ordinal()] = 4;
                    }
                    catch(NoSuchFieldError nosuchfielderror6) { }
                }
            }

            switch(_cls2..SwitchMap.com.megacrit.cardcrawl.core.CardCrawlGame.GameMode[mode.ordinal()])
            {
            case 1: // '\001'
                splashScreen.render(sb);
                break;

            case 4: // '\004'
                break;

            case 2: // '\002'
                mainMenuScreen.render(sb);
                break;

            case 3: // '\003'
                if(dungeonTransitionScreen != null)
                {
                    dungeonTransitionScreen.render(sb);
                    break;
                }
                if(dungeon != null)
                    dungeon.render(sb);
                break;

            default:
                logger.info((new StringBuilder()).append("Unknown Game Mode: ").append(mode.name()).toString());
                break;
            }
            DrawMaster.draw(sb);
            if(cardPopup.isOpen)
                cardPopup.render(sb);
            if(relicPopup.isOpen)
                relicPopup.render(sb);
            TipHelper.render(sb);
            if(mode != GameMode.SPLASH)
            {
                renderBlackFadeScreen(sb);
                if(displayCursor)
                {
                    if(isPopupOpen)
                    {
                        InputHelper.mX = popupMX;
                        InputHelper.mY = popupMY;
                    }
                    cursor.render(sb);
                }
            }
            if(Settings.HORIZ_LETTERBOX_AMT != 0)
            {
                sb.setColor(Color.BLACK);
                sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, -Settings.HORIZ_LETTERBOX_AMT);
                sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, Settings.HEIGHT, Settings.WIDTH, Settings.HORIZ_LETTERBOX_AMT);
            } else
            if(Settings.VERT_LETTERBOX_AMT != 0)
            {
                sb.setColor(Color.BLACK);
                sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, -Settings.VERT_LETTERBOX_AMT, Settings.HEIGHT);
                sb.draw(ImageMaster.WHITE_SQUARE_IMG, Settings.WIDTH, 0.0F, Settings.VERT_LETTERBOX_AMT, Settings.HEIGHT);
            }
            sb.end();
        }
        catch(Exception e)
        {
            logger.info("Exception occurred in CardCrawlGame render method!");
            ExceptionHandler.handleException(e, logger);
            Gdx.app.exit();
        }
        return;
    }

    private void renderBlackFadeScreen(SpriteBatch sb)
    {
        sb.setColor(screenColor);
        if(screenColor.a < 0.55F && !mainMenuScreen.bg.activated)
            mainMenuScreen.bg.activated = true;
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
    }

    public void updateFade()
    {
        if(screenTimer != 0.0F)
        {
            screenTimer -= Gdx.graphics.getDeltaTime();
            if(screenTimer < 0.0F)
                screenTimer = 0.0F;
            if(fadeIn)
            {
                screenColor.a = Interpolation.fade.apply(1.0F, 0.0F, 1.0F - screenTimer / screenTime);
            } else
            {
                screenColor.a = Interpolation.fade.apply(0.0F, 1.0F, 1.0F - screenTimer / screenTime);
                if(startOver && screenTimer == 0.0F)
                {
                    if(AbstractDungeon.scene != null)
                        AbstractDungeon.scene.fadeOutAmbiance();
                    long startTime = System.currentTimeMillis();
                    AbstractDungeon.screen = com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.NONE;
                    AbstractDungeon.reset();
                    FontHelper.cardTitleFont.getData().setScale(1.0F);
                    AbstractRelic.relicPage = 0;
                    SeedPanel.textField = "";
                    ModHelper.setModsFalse();
                    SeedHelper.cachedSeed = null;
                    Settings.seed = null;
                    Settings.seedSet = false;
                    Settings.specialSeed = null;
                    Settings.isTrial = false;
                    Settings.isDailyRun = false;
                    Settings.isEndless = false;
                    Settings.isFinalActAvailable = false;
                    Settings.hasRubyKey = false;
                    Settings.hasEmeraldKey = false;
                    Settings.hasSapphireKey = false;
                    CustomModeScreen.finalActAvailable = false;
                    trial = null;
                    logger.info((new StringBuilder()).append("Dungeon Reset: ").append(System.currentTimeMillis() - startTime).append("ms").toString());
                    startTime = System.currentTimeMillis();
                    ShopScreen.resetPurgeCost();
                    tips.initialize();
                    metricData.clearData();
                    logger.info((new StringBuilder()).append("Shop Screen Rest, Tips Initialize, Metric Data Clear: ").append(System.currentTimeMillis() - startTime).append("ms").toString());
                    startTime = System.currentTimeMillis();
                    UnlockTracker.refresh();
                    logger.info((new StringBuilder()).append("Unlock Tracker Refresh:  ").append(System.currentTimeMillis() - startTime).append("ms").toString());
                    startTime = System.currentTimeMillis();
                    mainMenuScreen = new MainMenuScreen();
                    mainMenuScreen.bg.slideDownInstantly();
                    saveSlotPref.putFloat(SaveHelper.slotName("COMPLETION", saveSlot), UnlockTracker.getCompletionPercentage());
                    saveSlotPref.putLong(SaveHelper.slotName("PLAYTIME", saveSlot), UnlockTracker.getTotalPlaytime());
                    saveSlotPref.flush();
                    logger.info((new StringBuilder()).append("New Main Menu Screen: ").append(System.currentTimeMillis() - startTime).append("ms").toString());
                    startTime = System.currentTimeMillis();
                    CardHelper.clear();
                    mode = GameMode.CHAR_SELECT;
                    nextDungeon = "Exordium";
                    dungeonTransitionScreen = new DungeonTransitionScreen("Exordium");
                    TipTracker.refresh();
                    logger.info((new StringBuilder()).append("[GC] BEFORE: ").append(String.valueOf(SystemStats.getUsedMemory())).toString());
                    System.gc();
                    logger.info((new StringBuilder()).append("[GC] AFTER: ").append(String.valueOf(SystemStats.getUsedMemory())).toString());
                    logger.info((new StringBuilder()).append("New Transition Screen, Tip Tracker Refresh: ").append(System.currentTimeMillis() - startTime).append("ms").toString());
                    startTime = System.currentTimeMillis();
                    fadeIn(2.0F);
                    if(queueCredits)
                    {
                        queueCredits = false;
                        mainMenuScreen.creditsScreen.open(playCreditsBgm);
                        mainMenuScreen.hideMenuButtons();
                    }
                }
            }
        }
    }

    public static void fadeIn(float duration)
    {
        screenColor.a = 1.0F;
        screenTime = duration;
        screenTimer = duration;
        fadeIn = true;
    }

    public static void fadeToBlack(float duration)
    {
        screenColor.a = 0.0F;
        screenTime = duration;
        screenTimer = duration;
        fadeIn = false;
    }

    public static void startOver()
    {
        startOver = true;
        fadeToBlack(2.0F);
    }

    public static void startOverButShowCredits()
    {
        startOver = true;
        queueCredits = true;
        doorUnlockScreenCheck();
        fadeToBlack(2.0F);
    }

    private static void doorUnlockScreenCheck()
    {
        DoorUnlockScreen.show = false;
        if(!Settings.isStandardRun())
        {
            logger.info("[INFO] Non-Standard Run, no check for door.");
            return;
        }
        switch(_cls2..SwitchMap.com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass[AbstractDungeon.player.chosenClass.ordinal()])
        {
        case 1: // '\001'
            if(!playerPref.getBoolean((new StringBuilder()).append(AbstractDungeon.player.chosenClass.name()).append("_WIN").toString(), false))
            {
                logger.info("[INFO] Ironclad Victory: Show Door.");
                playerPref.putBoolean((new StringBuilder()).append(AbstractDungeon.player.chosenClass.name()).append("_WIN").toString(), true);
                DoorUnlockScreen.show = true;
            } else
            {
                logger.info("[INFO] Ironclad Already Won: No Door.");
            }
            break;

        case 2: // '\002'
            if(!playerPref.getBoolean((new StringBuilder()).append(AbstractDungeon.player.chosenClass.name()).append("_WIN").toString(), false))
            {
                logger.info("[INFO] Silent Victory: Show Door.");
                playerPref.putBoolean((new StringBuilder()).append(AbstractDungeon.player.chosenClass.name()).append("_WIN").toString(), true);
                DoorUnlockScreen.show = true;
            } else
            {
                logger.info("[INFO] Silent Already Won: No Door.");
            }
            break;

        case 3: // '\003'
            if(!playerPref.getBoolean((new StringBuilder()).append(AbstractDungeon.player.chosenClass.name()).append("_WIN").toString(), false))
            {
                logger.info("[INFO] Defect Victory: Show Door.");
                playerPref.putBoolean((new StringBuilder()).append(AbstractDungeon.player.chosenClass.name()).append("_WIN").toString(), true);
                DoorUnlockScreen.show = true;
            } else
            {
                logger.info("[INFO] Defect Already Won: No Door.");
            }
            break;
        }
        if(DoorUnlockScreen.show)
            playerPref.flush();
    }

    public static void resetScoreVars()
    {
        monstersSlain = 0;
        elites1Slain = 0;
        elites2Slain = 0;
        elites3Slain = 0;
        if(dungeon != null)
            AbstractDungeon.bossCount = 0;
        champion = 0;
        perfect = 0;
        overkill = false;
        combo = false;
        goldGained = 0;
        cardsPurged = 0;
        potionsBought = 0;
        mysteryMachine = 0;
        playtime = 0.0F;
        stopClock = false;
    }

    public void update()
    {
        cursor.update();
        screenShake.update(viewport);
        if(mode != GameMode.SPLASH)
            updateFade();
        music.update();
        sound.update();
        if(steelSeries.isEnabled.booleanValue())
            steelSeries.update();
        if(Settings.isDebug)
            if(DevInputActionSet.toggleCursor.isJustPressed())
                displayCursor = !displayCursor;
            else
            if(DevInputActionSet.toggleVersion.isJustPressed())
                displayVersion = !displayVersion;
        if(SteamInputHelper.numControllers == 1)
            SteamInputHelper.updateFirst();
        else
        if(SteamInputHelper.numControllers == 999 && CInputHelper.controllers == null)
            CInputHelper.initializeIfAble();
        InputHelper.updateFirst();
        if(cardPopup.isOpen)
            cardPopup.update();
        if(relicPopup.isOpen)
            relicPopup.update();
        if(isPopupOpen)
        {
            popupMX = InputHelper.mX;
            popupMY = InputHelper.mY;
            InputHelper.mX = -9999;
            InputHelper.mY = -9999;
        }
        switch(_cls2..SwitchMap.com.megacrit.cardcrawl.core.CardCrawlGame.GameMode[mode.ordinal()])
        {
        case 1: // '\001'
            splashScreen.update();
            if(splashScreen.isDone)
            {
                mode = GameMode.CHAR_SELECT;
                splashScreen = null;
                mainMenuScreen = new MainMenuScreen();
            }
            break;

        case 4: // '\004'
            break;

        case 2: // '\002'
            mainMenuScreen.update();
            if(!mainMenuScreen.fadedOut)
                break;
            AbstractDungeon.pathX = new ArrayList();
            AbstractDungeon.pathY = new ArrayList();
            if(trial == null && Settings.specialSeed != null)
                trial = TrialHelper.getTrialForSeed(SeedHelper.getString(Settings.specialSeed.longValue()));
            if(loadingSave)
            {
                ModHelper.setModsFalse();
                AbstractDungeon.player = createCharacter(chosenCharacter);
                loadPlayerSave(AbstractDungeon.player);
            } else
            {
                Settings.setFinalActAvailability();
                logger.info((new StringBuilder()).append("FINAL ACT AVAILABLE: ").append(Settings.isFinalActAvailable).toString());
                if(trial == null)
                {
                    if(Settings.isDailyRun)
                    {
                        AbstractDungeon.ascensionLevel = 0;
                        AbstractDungeon.isAscensionMode = false;
                    }
                    AbstractDungeon.player = createCharacter(chosenCharacter);
                    AbstractRelic r;
                    for(Iterator iterator = AbstractDungeon.player.relics.iterator(); iterator.hasNext(); r.onEquip())
                    {
                        r = (AbstractRelic)iterator.next();
                        r.updateDescription(AbstractDungeon.player.chosenClass);
                    }

                    Iterator iterator1 = AbstractDungeon.player.masterDeck.group.iterator();
                    do
                    {
                        if(!iterator1.hasNext())
                            break;
                        AbstractCard c = (AbstractCard)iterator1.next();
                        if(c.rarity != com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.BASIC)
                            CardHelper.obtain(c.cardID, c.rarity, c.color);
                    } while(true);
                } else
                {
                    Settings.isTrial = true;
                    Settings.isDailyRun = false;
                    setupTrialMods(trial, chosenCharacter);
                    setupTrialPlayer(trial);
                }
            }
            mode = GameMode.GAMEPLAY;
            nextDungeon = "Exordium";
            dungeonTransitionScreen = new DungeonTransitionScreen("Exordium");
            if(loadingSave)
            {
                dungeonTransitionScreen.isComplete = true;
            } else
            {
                monstersSlain = 0;
                elites1Slain = 0;
                elites2Slain = 0;
                elites3Slain = 0;
            }
            break;

        case 3: // '\003'
            if(dungeonTransitionScreen != null)
            {
                dungeonTransitionScreen.update();
                if(dungeonTransitionScreen.isComplete)
                {
                    dungeonTransitionScreen = null;
                    if(loadingSave)
                    {
                        getDungeon(saveFile.level_name, AbstractDungeon.player, saveFile);
                        loadPostCombat(saveFile);
                        if(!saveFile.post_combat)
                            loadingSave = false;
                    } else
                    {
                        getDungeon(nextDungeon, AbstractDungeon.player);
                        if(!nextDungeon.equals("Exordium") || Settings.isShowBuild || !((Boolean)TipTracker.tips.get("NEOW_SKIP")).booleanValue())
                        {
                            AbstractDungeon.dungeonMapScreen.open(true);
                            TipTracker.neverShowAgain("NEOW_SKIP");
                        }
                    }
                }
            } else
            if(dungeon != null)
                dungeon.update();
            else
                logger.info("Eh-?");
            if(dungeon != null && AbstractDungeon.isDungeonBeaten && AbstractDungeon.fadeColor.a == 1.0F)
            {
                dungeon = null;
                AbstractDungeon.scene.fadeOutAmbiance();
                dungeonTransitionScreen = new DungeonTransitionScreen(nextDungeon);
            }
            break;

        default:
            logger.info((new StringBuilder()).append("Unknown Game Mode: ").append(mode.name()).toString());
            break;
        }
        updateDebugSwitch();
        InputHelper.updateLast();
        if(CInputHelper.controller != null)
            CInputHelper.updateLast();
        if(Settings.isInfo)
            fpsLogger.log();
    }

    private void setupTrialMods(AbstractTrial trial, com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass chosenClass)
    {
        if(trial.useRandomDailyMods())
        {
            long sourceTime = System.nanoTime();
            Random rng = new Random(Long.valueOf(sourceTime));
            Settings.seed = Long.valueOf(SeedHelper.generateUnoffensiveSeed(rng));
            ModHelper.setTodaysMods(Settings.seed.longValue(), chosenClass);
        } else
        if(trial.dailyModIDs() != null)
        {
            ModHelper.setMods(trial.dailyModIDs());
            ModHelper.clearNulls();
        }
    }

    private void setupTrialPlayer(AbstractTrial trial)
    {
        AbstractDungeon.player = trial.setupPlayer(createCharacter(chosenCharacter));
        if(!trial.keepStarterRelic())
            AbstractDungeon.player.relics.clear();
        AbstractRelic relic;
        for(Iterator iterator = trial.extraStartingRelicIDs().iterator(); iterator.hasNext(); AbstractDungeon.relicsToRemoveOnStart.add(relic.relicId))
        {
            String relicID = (String)iterator.next();
            relic = RelicLibrary.getRelic(relicID);
            relic.instantObtain(AbstractDungeon.player, AbstractDungeon.player.relics.size(), false);
        }

        AbstractRelic r;
        for(Iterator iterator1 = AbstractDungeon.player.relics.iterator(); iterator1.hasNext(); r.onEquip())
        {
            r = (AbstractRelic)iterator1.next();
            r.updateDescription(AbstractDungeon.player.chosenClass);
        }

        if(!trial.keepsStarterCards())
            AbstractDungeon.player.masterDeck.clear();
        String cardID;
        for(Iterator iterator2 = trial.extraStartingCardIDs().iterator(); iterator2.hasNext(); AbstractDungeon.player.masterDeck.addToTop(CardLibrary.getCard(cardID).makeCopy()))
            cardID = (String)iterator2.next();

    }

    private void loadPostCombat(SaveFile saveFile)
    {
        if(saveFile.post_combat)
        {
            AbstractDungeon.getCurrRoom().isBattleOver = true;
            AbstractDungeon.overlayMenu.hideCombatPanels();
            AbstractDungeon.loading_post_combat = true;
            AbstractDungeon.getCurrRoom().smoked = saveFile.smoked;
            AbstractDungeon.getCurrRoom().mugged = saveFile.mugged;
            if(AbstractDungeon.getCurrRoom().event != null)
                AbstractDungeon.getCurrRoom().event.postCombatLoad();
            if(AbstractDungeon.getCurrRoom().monsters != null)
            {
                AbstractDungeon.getCurrRoom().monsters.monsters.clear();
                AbstractDungeon.actionManager.actions.clear();
            }
            if(!saveFile.smoked)
            {
                Iterator iterator = saveFile.combat_rewards.iterator();
                do
                {
                    if(!iterator.hasNext())
                        break;
                    RewardSave i = (RewardSave)iterator.next();
                    String s = i.type;
                    byte byte0 = -1;
                    switch(s.hashCode())
                    {
                    case 2061072: 
                        if(s.equals("CARD"))
                            byte0 = 0;
                        break;

                    case 2193504: 
                        if(s.equals("GOLD"))
                            byte0 = 1;
                        break;

                    case 77859667: 
                        if(s.equals("RELIC"))
                            byte0 = 2;
                        break;

                    case -1929101933: 
                        if(s.equals("POTION"))
                            byte0 = 3;
                        break;

                    case -759508872: 
                        if(s.equals("STOLEN_GOLD"))
                            byte0 = 4;
                        break;

                    case -706635454: 
                        if(s.equals("SAPPHIRE_KEY"))
                            byte0 = 5;
                        break;

                    case -866293372: 
                        if(s.equals("EMERALD_KEY"))
                            byte0 = 6;
                        break;
                    }
                    switch(byte0)
                    {
                    case 1: // '\001'
                        AbstractDungeon.getCurrRoom().addGoldToRewards(i.amount);
                        break;

                    case 2: // '\002'
                        AbstractDungeon.getCurrRoom().addRelicToRewards(RelicLibrary.getRelic(i.id).makeCopy());
                        break;

                    case 3: // '\003'
                        AbstractDungeon.getCurrRoom().addPotionToRewards(PotionHelper.getPotion(i.id));
                        break;

                    case 4: // '\004'
                        AbstractDungeon.getCurrRoom().addStolenGoldToRewards(i.amount);
                        break;

                    case 5: // '\005'
                        AbstractDungeon.getCurrRoom().addSapphireKey((RewardItem)AbstractDungeon.getCurrRoom().rewards.get(AbstractDungeon.getCurrRoom().rewards.size() - 1));
                        break;

                    case 6: // '\006'
                        AbstractDungeon.getCurrRoom().rewards.add(new RewardItem((RewardItem)AbstractDungeon.getCurrRoom().rewards.get(AbstractDungeon.getCurrRoom().rewards.size() - 1), com.megacrit.cardcrawl.rewards.RewardItem.RewardType.EMERALD_KEY));
                        break;

                    default:
                        logger.info((new StringBuilder()).append("Loading unknown type: ").append(i.type).toString());
                        break;

                    case 0: // '\0'
                        break;
                    }
                } while(true);
            }
            if(AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss)
            {
                AbstractDungeon.scene.fadeInAmbiance();
                music.silenceTempBgmInstantly();
                music.silenceBGMInstantly();
                AbstractMonster.playBossStinger();
            } else
            if(AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite)
            {
                AbstractDungeon.scene.fadeInAmbiance();
                music.fadeOutTempBGM();
            }
            saveFile.post_combat = false;
        }
    }

    private void loadPlayerSave(AbstractPlayer p)
    {
        saveFile = SaveAndContinue.loadSaveFile(p.chosenClass);
        AbstractDungeon.loading_post_combat = false;
        Settings.seed = Long.valueOf(saveFile.seed);
        Settings.isFinalActAvailable = saveFile.is_final_act_on;
        Settings.hasRubyKey = saveFile.has_ruby_key;
        Settings.hasEmeraldKey = saveFile.has_emerald_key;
        Settings.hasSapphireKey = saveFile.has_sapphire_key;
        Settings.isDailyRun = saveFile.is_daily;
        if(Settings.isDailyRun)
            Settings.dailyDate = saveFile.daily_date;
        Settings.specialSeed = Long.valueOf(saveFile.special_seed);
        Settings.seedSet = saveFile.seed_set;
        Settings.isTrial = saveFile.is_trial;
        if(Settings.isTrial)
        {
            ModHelper.setTodaysMods(Settings.seed.longValue(), AbstractDungeon.player.chosenClass);
            AbstractPlayer.customMods = saveFile.custom_mods;
        } else
        if(Settings.isDailyRun)
            ModHelper.setTodaysMods(Settings.specialSeed.longValue(), AbstractDungeon.player.chosenClass);
        AbstractPlayer.customMods = saveFile.custom_mods;
        if(AbstractPlayer.customMods == null)
            AbstractPlayer.customMods = new ArrayList();
        p.currentHealth = saveFile.current_health;
        p.maxHealth = saveFile.max_health;
        p.gold = saveFile.gold;
        p.displayGold = p.gold;
        p.masterHandSize = saveFile.hand_size;
        p.potionSlots = saveFile.potion_slots;
        if(p.potionSlots == 0)
            p.potionSlots = 3;
        p.potions.clear();
        for(int i = 0; i < p.potionSlots; i++)
            p.potions.add(new PotionSlot(i));

        p.masterMaxOrbs = saveFile.max_orbs;
        p.energy = new EnergyManager(saveFile.red + saveFile.green + saveFile.blue);
        monstersSlain = saveFile.monsters_killed;
        elites1Slain = saveFile.elites1_killed;
        elites2Slain = saveFile.elites2_killed;
        elites3Slain = saveFile.elites3_killed;
        goldGained = saveFile.gold_gained;
        champion = saveFile.champions;
        perfect = saveFile.perfect;
        combo = saveFile.combo;
        overkill = saveFile.overkill;
        mysteryMachine = saveFile.mystery_machine;
        playtime = saveFile.play_time;
        AbstractDungeon.ascensionLevel = saveFile.ascension_level;
        AbstractDungeon.isAscensionMode = saveFile.is_ascension_mode;
        p.masterDeck.clear();
        CardSave s;
        for(Iterator iterator = saveFile.cards.iterator(); iterator.hasNext(); p.masterDeck.addToTop(CardLibrary.getCopy(s.id, s.upgrades, s.misc)))
        {
            s = (CardSave)iterator.next();
            logger.info((new StringBuilder()).append(s.id).append(", ").append(s.upgrades).toString());
        }

        Settings.isEndless = saveFile.is_endless_mode;
        int index = 0;
        p.blights.clear();
        if(saveFile.blights != null)
        {
            for(Iterator iterator1 = saveFile.blights.iterator(); iterator1.hasNext(); index++)
            {
                String b = (String)iterator1.next();
                AbstractBlight blight = BlightHelper.getBlight(b);
                if(blight == null)
                    continue;
                int incrementAmount = ((Integer)saveFile.endless_increments.get(index)).intValue();
                for(int i = 0; i < incrementAmount; i++)
                    blight.incrementUp();

                blight.setIncrement(incrementAmount);
                blight.instantObtain(AbstractDungeon.player, index, false);
            }

            if(saveFile.blight_counters != null)
            {
                index = 0;
                for(Iterator iterator2 = saveFile.blight_counters.iterator(); iterator2.hasNext();)
                {
                    Integer i = (Integer)iterator2.next();
                    ((AbstractBlight)p.blights.get(index)).setCounter(i.intValue());
                    ((AbstractBlight)p.blights.get(index)).updateDescription(p.chosenClass);
                    index++;
                }

            }
        }
        p.relics.clear();
        index = 0;
        for(Iterator iterator3 = saveFile.relics.iterator(); iterator3.hasNext();)
        {
            String s = (String)iterator3.next();
            AbstractRelic r = RelicLibrary.getRelic(s).makeCopy();
            r.instantObtain(p, index, false);
            if(index < saveFile.relic_counters.size())
                r.setCounter(((Integer)saveFile.relic_counters.get(index)).intValue());
            r.updateDescription(p.chosenClass);
            index++;
        }

        index = 0;
        for(Iterator iterator4 = saveFile.potions.iterator(); iterator4.hasNext();)
        {
            String s = (String)iterator4.next();
            AbstractPotion potion = PotionHelper.getPotion(s);
            if(potion != null)
                AbstractDungeon.player.obtainPotion(index, potion);
            index++;
        }

        AbstractCard tmpCard = null;
        if(saveFile.bottled_flame != null)
        {
            Iterator iterator5 = AbstractDungeon.player.masterDeck.group.iterator();
            AbstractCard i;
label0:
            do
            {
                do
                {
                    if(!iterator5.hasNext())
                        break label0;
                    i = (AbstractCard)iterator5.next();
                } while(!i.cardID.equals(saveFile.bottled_flame));
                tmpCard = i;
            } while(i.timesUpgraded != saveFile.bottled_flame_upgrade || i.misc != saveFile.bottled_flame_misc);
            if(tmpCard != null)
            {
                tmpCard.inBottleFlame = true;
                ((BottledFlame)AbstractDungeon.player.getRelic("Bottled Flame")).card = tmpCard;
                ((BottledFlame)AbstractDungeon.player.getRelic("Bottled Flame")).setDescriptionAfterLoading();
            }
        }
        tmpCard = null;
        if(saveFile.bottled_lightning != null)
        {
            Iterator iterator6 = AbstractDungeon.player.masterDeck.group.iterator();
            AbstractCard i;
label1:
            do
            {
                do
                {
                    if(!iterator6.hasNext())
                        break label1;
                    i = (AbstractCard)iterator6.next();
                } while(!i.cardID.equals(saveFile.bottled_lightning));
                tmpCard = i;
            } while(i.timesUpgraded != saveFile.bottled_lightning_upgrade || i.misc != saveFile.bottled_lightning_misc);
            if(tmpCard != null)
            {
                tmpCard.inBottleLightning = true;
                ((BottledLightning)AbstractDungeon.player.getRelic("Bottled Lightning")).card = tmpCard;
                ((BottledLightning)AbstractDungeon.player.getRelic("Bottled Lightning")).setDescriptionAfterLoading();
            }
        }
        tmpCard = null;
        if(saveFile.bottled_tornado != null)
        {
            Iterator iterator7 = AbstractDungeon.player.masterDeck.group.iterator();
            AbstractCard i;
label2:
            do
            {
                do
                {
                    if(!iterator7.hasNext())
                        break label2;
                    i = (AbstractCard)iterator7.next();
                } while(!i.cardID.equals(saveFile.bottled_tornado));
                tmpCard = i;
            } while(i.timesUpgraded != saveFile.bottled_tornado_upgrade || i.misc != saveFile.bottled_tornado_misc);
            if(tmpCard != null)
            {
                tmpCard.inBottleTornado = true;
                ((BottledTornado)AbstractDungeon.player.getRelic("Bottled Tornado")).card = tmpCard;
                ((BottledTornado)AbstractDungeon.player.getRelic("Bottled Tornado")).setDescriptionAfterLoading();
            }
        }
        if(saveFile.daily_mods != null && saveFile.daily_mods.size() > 0)
            ModHelper.setMods(saveFile.daily_mods);
        metricData.clearData();
        metricData.campfire_rested = saveFile.metric_campfire_rested;
        metricData.campfire_upgraded = saveFile.metric_campfire_upgraded;
        metricData.purchased_purges = saveFile.metric_purchased_purges;
        metricData.potions_floor_spawned = saveFile.metric_potions_floor_spawned;
        metricData.current_hp_per_floor = saveFile.metric_current_hp_per_floor;
        metricData.max_hp_per_floor = saveFile.metric_max_hp_per_floor;
        metricData.gold_per_floor = saveFile.metric_gold_per_floor;
        metricData.path_per_floor = saveFile.metric_path_per_floor;
        metricData.path_taken = saveFile.metric_path_taken;
        metricData.items_purchased = saveFile.metric_items_purchased;
        metricData.items_purged = saveFile.metric_items_purged;
        metricData.card_choices = saveFile.metric_card_choices;
        metricData.event_choices = saveFile.metric_event_choices;
        metricData.damage_taken = saveFile.metric_damage_taken;
        metricData.boss_relics = saveFile.metric_boss_relics;
        if(saveFile.metric_potions_obtained != null)
            metricData.potions_obtained = saveFile.metric_potions_obtained;
        if(saveFile.metric_relics_obtained != null)
            metricData.relics_obtained = saveFile.metric_relics_obtained;
        if(saveFile.metric_campfire_choices != null)
            metricData.campfire_choices = saveFile.metric_campfire_choices;
        if(saveFile.metric_item_purchase_floors != null)
            metricData.item_purchase_floors = saveFile.metric_item_purchase_floors;
        if(saveFile.metric_items_purged_floors != null)
            metricData.items_purged_floors = saveFile.metric_items_purged_floors;
        if(saveFile.neow_bonus != null)
            metricData.neowBonus = saveFile.neow_bonus;
        if(saveFile.neow_cost != null)
            metricData.neowCost = saveFile.neow_cost;
    }

    private static AbstractPlayer createCharacter(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass selection)
    {
        AbstractPlayer p = characterManager.recreateCharacter(selection);
        AbstractCard c;
        for(Iterator iterator = p.masterDeck.group.iterator(); iterator.hasNext(); UnlockTracker.markCardAsSeen(c.cardID))
            c = (AbstractCard)iterator.next();

        return p;
    }

    private void updateDebugSwitch()
    {
        if(!Settings.isDev)
            return;
        if(DevInputActionSet.toggleDebug.isJustPressed())
        {
            Settings.isDebug = !Settings.isDebug;
            return;
        }
        if(DevInputActionSet.toggleInfo.isJustPressed())
        {
            Settings.isInfo = !Settings.isInfo;
            return;
        }
        if(Settings.isDebug && DevInputActionSet.uploadData.isJustPressed())
        {
            RelicLibrary.uploadRelicData();
            CardLibrary.uploadCardData();
            MonsterHelper.uploadEnemyData();
            PotionHelper.uploadPotionData();
            ModHelper.uploadModData();
            BlightHelper.uploadBlightData();
            BotDataUploader.uploadKeywordData();
            return;
        }
        if(!Settings.isDebug)
            return;
        if(DevInputActionSet.hideTopBar.isJustPressed())
        {
            Settings.hideTopBar = !Settings.hideTopBar;
            return;
        }
        if(DevInputActionSet.hidePopUps.isJustPressed())
        {
            Settings.hidePopupDetails = !Settings.hidePopupDetails;
            return;
        }
        if(DevInputActionSet.hideRelics.isJustPressed())
        {
            Settings.hideRelics = !Settings.hideRelics;
            return;
        }
        if(DevInputActionSet.hideCombatLowUI.isJustPressed())
        {
            Settings.hideLowerElements = !Settings.hideLowerElements;
            return;
        }
        if(DevInputActionSet.hideCards.isJustPressed())
        {
            Settings.hideCards = !Settings.hideCards;
            return;
        }
        if(DevInputActionSet.hideEndTurnButton.isJustPressed())
        {
            Settings.hideEndTurn = !Settings.hideEndTurn;
            if(AbstractDungeon.getMonsters() == null)
                return;
            AbstractMonster m;
            for(Iterator iterator = AbstractDungeon.getMonsters().monsters.iterator(); iterator.hasNext(); m.damage(new DamageInfo(AbstractDungeon.player, m.currentHealth, com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS)))
                m = (AbstractMonster)iterator.next();

            return;
        }
        if(DevInputActionSet.hideCombatInfo.isJustPressed())
            Settings.hideCombatElements = !Settings.hideCombatElements;
    }

    public void resize(int i, int j)
    {
    }

    public AbstractDungeon getDungeon(String key, AbstractPlayer p)
    {
        String s = key;
        byte byte0 = -1;
        switch(s.hashCode())
        {
        case -1887678253: 
            if(s.equals("Exordium"))
                byte0 = 0;
            break;

        case 313705820: 
            if(s.equals("TheCity"))
                byte0 = 1;
            break;

        case 791401920: 
            if(s.equals("TheBeyond"))
                byte0 = 2;
            break;

        case 884969688: 
            if(s.equals("TheEnding"))
                byte0 = 3;
            break;
        }
        switch(byte0)
        {
        case 0: // '\0'
            ArrayList emptyList = new ArrayList();
            return new Exordium(p, emptyList);

        case 1: // '\001'
            return new TheCity(p, AbstractDungeon.specialOneTimeEventList);

        case 2: // '\002'
            return new TheBeyond(p, AbstractDungeon.specialOneTimeEventList);

        case 3: // '\003'
            return new TheEnding(p, AbstractDungeon.specialOneTimeEventList);
        }
        return null;
    }

    public AbstractDungeon getDungeon(String key, AbstractPlayer p, SaveFile saveFile)
    {
        String s = key;
        byte byte0 = -1;
        switch(s.hashCode())
        {
        case -1887678253: 
            if(s.equals("Exordium"))
                byte0 = 0;
            break;

        case 313705820: 
            if(s.equals("TheCity"))
                byte0 = 1;
            break;

        case 791401920: 
            if(s.equals("TheBeyond"))
                byte0 = 2;
            break;

        case 884969688: 
            if(s.equals("TheEnding"))
                byte0 = 3;
            break;
        }
        switch(byte0)
        {
        case 0: // '\0'
            return new Exordium(p, saveFile);

        case 1: // '\001'
            return new TheCity(p, saveFile);

        case 2: // '\002'
            return new TheBeyond(p, saveFile);

        case 3: // '\003'
            return new TheEnding(p, saveFile);
        }
        return null;
    }

    public void pause()
    {
        logger.info("PAUSE()");
        Settings.isControllerMode = false;
        if(MUTE_IF_BG && mainMenuScreen != null)
        {
            Settings.isBackgrounded = true;
            if(mode == GameMode.CHAR_SELECT)
                mainMenuScreen.muteAmbienceVolume();
            else
            if(AbstractDungeon.scene != null)
                AbstractDungeon.scene.muteAmbienceVolume();
        }
    }

    public void resume()
    {
        logger.info("RESUME()");
        if(MUTE_IF_BG && mainMenuScreen != null)
        {
            Settings.isBackgrounded = false;
            if(mode == GameMode.CHAR_SELECT)
                mainMenuScreen.updateAmbienceVolume();
            else
            if(AbstractDungeon.scene != null)
                AbstractDungeon.scene.updateAmbienceVolume();
        }
    }

    public void dispose()
    {
        logger.info("Game shutting down...");
        logger.info("Flushing saves to disk...");
        AsyncSaver.shutdownSaveThread();
        if(SteamInputHelper.alive)
        {
            logger.info("Shutting down controller handler...");
            SteamInputHelper.alive = false;
            SteamInputHelper.controller.shutdown();
            if(clientUtils != null)
                clientUtils.dispose();
        }
        if(sInputDetectThread != null)
        {
            logger.info("Steam input detection was running! Shutting down...");
            sInputDetectThread.interrupt();
        }
        logger.info("Shutting down publisher integrations...");
        publisherIntegration.dispose();
        logger.info("Flushing logs to disk. Clean shutdown successful.");
        LogManager.shutdown();
    }

    public static String generateRandomAlias()
    {
        String alphabet = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder retVal = new StringBuilder();
        for(int i = 0; i < 16; i++)
            retVal.append(alphabet.charAt(MathUtils.random(0, alphabet.length() - 1)));

        return retVal.toString();
    }

    public static boolean isInARun()
    {
        return mode == GameMode.GAMEPLAY && AbstractDungeon.player != null && !AbstractDungeon.player.isDead;
    }

    public static Texture getSaveSlotImg()
    {
        switch(saveSlot)
        {
        case 0: // '\0'
            return ImageMaster.PROFILE_A;

        case 1: // '\001'
            return ImageMaster.PROFILE_B;

        case 2: // '\002'
            return ImageMaster.PROFILE_C;
        }
        return ImageMaster.PROFILE_A;
    }

    public static String VERSION_NUM = "[V2.3.4] (12-18-2022)";
    public static String TRUE_VERSION_NUM = "2022-12-18";
    private OrthographicCamera camera;
    public static FitViewport viewport;
    public static PolygonSpriteBatch psb;
    private SpriteBatch sb;
    public static GameCursor cursor;
    public static boolean isPopupOpen = false;
    public static int popupMX;
    public static int popupMY;
    public static ScreenShake screenShake = new ScreenShake();
    public static AbstractDungeon dungeon;
    public static MainMenuScreen mainMenuScreen;
    public static SplashScreen splashScreen;
    public static DungeonTransitionScreen dungeonTransitionScreen;
    public static CancelButton cancelButton;
    public static MusicMaster music;
    public static SoundMaster sound;
    public static GameTips tips;
    public static SingleCardViewPopup cardPopup;
    public static SingleRelicViewPopup relicPopup;
    private FPSLogger fpsLogger;
    public boolean prevDebugKeyDown;
    public static String nextDungeon;
    public static GameMode mode;
    public static boolean startOver = false;
    private static boolean queueCredits = false;
    public static boolean playCreditsBgm = false;
    public static boolean MUTE_IF_BG = false;
    public static com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass chosenCharacter = null;
    public static boolean loadingSave;
    public static SaveFile saveFile = null;
    public static Prefs saveSlotPref;
    public static Prefs playerPref;
    public static int saveSlot = 0;
    public static String playerName;
    public static String alias;
    public static CharacterManager characterManager;
    public static int monstersSlain = 0;
    public static int elites1Slain = 0;
    public static int elites2Slain = 0;
    public static int elites3Slain = 0;
    public static int elitesModdedSlain = 0;
    public static int champion = 0;
    public static int perfect = 0;
    public static boolean overkill = false;
    public static boolean combo = false;
    public static boolean cheater = false;
    public static int goldGained = 0;
    public static int cardsPurged = 0;
    public static int potionsBought = 0;
    public static int mysteryMachine = 0;
    public static float playtime = 0.0F;
    public static boolean stopClock = false;
    public static SkeletonRendererDebug debugRenderer;
    public static AbstractTrial trial = null;
    public static Scanner sControllerScanner;
    SteamInputHelper steamInputHelper;
    public static SteamUtils clientUtils;
    public static Thread sInputDetectThread = null;
    private static Color screenColor;
    public static float screenTimer = 2.0F;
    public static float screenTime = 2.0F;
    private static boolean fadeIn = true;
    public static MetricData metricData;
    public static PublisherIntegration publisherIntegration;
    public static SteelSeries steelSeries;
    public static LocalizedStrings languagePack;
    private boolean displayCursor;
    public static boolean displayVersion = true;
    public static String preferenceDir = null;
    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/core/CardCrawlGame.getName());
    private SteamUtilsCallback clUtilsCallback;

    static 
    {
        mode = GameMode.CHAR_SELECT;
        screenColor = Color.BLACK.cpy();
    }

}
