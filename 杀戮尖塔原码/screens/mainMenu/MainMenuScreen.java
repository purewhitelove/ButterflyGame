// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MainMenuScreen.java

package com.megacrit.cardcrawl.screens.mainMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.characters.CharacterManager;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.credits.CreditsScreen;
import com.megacrit.cardcrawl.cutscenes.NeowNarrationScreen;
import com.megacrit.cardcrawl.daily.DailyScreen;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.*;
import com.megacrit.cardcrawl.helpers.input.*;
import com.megacrit.cardcrawl.integrations.PublisherIntegration;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.scenes.TitleBackground;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.screens.DoorUnlockScreen;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import com.megacrit.cardcrawl.screens.compendium.*;
import com.megacrit.cardcrawl.screens.custom.CustomModeScreen;
import com.megacrit.cardcrawl.screens.leaderboards.LeaderboardScreen;
import com.megacrit.cardcrawl.screens.options.*;
import com.megacrit.cardcrawl.screens.runHistory.RunHistoryScreen;
import com.megacrit.cardcrawl.screens.stats.StatsScreen;
import com.megacrit.cardcrawl.ui.buttons.CancelButton;
import com.megacrit.cardcrawl.ui.buttons.ConfirmButton;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.screens.mainMenu:
//            SaveSlotScreen, MenuPanelScreen, PatchNotesScreen, SyncMessage, 
//            MenuCancelButton, MenuButton, MainMenuPanelButton, EarlyAccessPopup

public class MainMenuScreen
{
    public static final class CurScreen extends Enum
    {

        public static CurScreen[] values()
        {
            return (CurScreen[])$VALUES.clone();
        }

        public static CurScreen valueOf(String name)
        {
            return (CurScreen)Enum.valueOf(com/megacrit/cardcrawl/screens/mainMenu/MainMenuScreen$CurScreen, name);
        }

        public static final CurScreen CHAR_SELECT;
        public static final CurScreen RELIC_VIEW;
        public static final CurScreen POTION_VIEW;
        public static final CurScreen BANNER_DECK_VIEW;
        public static final CurScreen DAILY;
        public static final CurScreen TRIALS;
        public static final CurScreen SETTINGS;
        public static final CurScreen MAIN_MENU;
        public static final CurScreen SAVE_SLOT;
        public static final CurScreen STATS;
        public static final CurScreen RUN_HISTORY;
        public static final CurScreen CARD_LIBRARY;
        public static final CurScreen CREDITS;
        public static final CurScreen PATCH_NOTES;
        public static final CurScreen NONE;
        public static final CurScreen LEADERBOARD;
        public static final CurScreen ABANDON_CONFIRM;
        public static final CurScreen PANEL_MENU;
        public static final CurScreen INPUT_SETTINGS;
        public static final CurScreen CUSTOM;
        public static final CurScreen NEOW_SCREEN;
        public static final CurScreen DOOR_UNLOCK;
        private static final CurScreen $VALUES[];

        static 
        {
            CHAR_SELECT = new CurScreen("CHAR_SELECT", 0);
            RELIC_VIEW = new CurScreen("RELIC_VIEW", 1);
            POTION_VIEW = new CurScreen("POTION_VIEW", 2);
            BANNER_DECK_VIEW = new CurScreen("BANNER_DECK_VIEW", 3);
            DAILY = new CurScreen("DAILY", 4);
            TRIALS = new CurScreen("TRIALS", 5);
            SETTINGS = new CurScreen("SETTINGS", 6);
            MAIN_MENU = new CurScreen("MAIN_MENU", 7);
            SAVE_SLOT = new CurScreen("SAVE_SLOT", 8);
            STATS = new CurScreen("STATS", 9);
            RUN_HISTORY = new CurScreen("RUN_HISTORY", 10);
            CARD_LIBRARY = new CurScreen("CARD_LIBRARY", 11);
            CREDITS = new CurScreen("CREDITS", 12);
            PATCH_NOTES = new CurScreen("PATCH_NOTES", 13);
            NONE = new CurScreen("NONE", 14);
            LEADERBOARD = new CurScreen("LEADERBOARD", 15);
            ABANDON_CONFIRM = new CurScreen("ABANDON_CONFIRM", 16);
            PANEL_MENU = new CurScreen("PANEL_MENU", 17);
            INPUT_SETTINGS = new CurScreen("INPUT_SETTINGS", 18);
            CUSTOM = new CurScreen("CUSTOM", 19);
            NEOW_SCREEN = new CurScreen("NEOW_SCREEN", 20);
            DOOR_UNLOCK = new CurScreen("DOOR_UNLOCK", 21);
            $VALUES = (new CurScreen[] {
                CHAR_SELECT, RELIC_VIEW, POTION_VIEW, BANNER_DECK_VIEW, DAILY, TRIALS, SETTINGS, MAIN_MENU, SAVE_SLOT, STATS, 
                RUN_HISTORY, CARD_LIBRARY, CREDITS, PATCH_NOTES, NONE, LEADERBOARD, ABANDON_CONFIRM, PANEL_MENU, INPUT_SETTINGS, CUSTOM, 
                NEOW_SCREEN, DOOR_UNLOCK
            });
        }

        private CurScreen(String s, int i)
        {
            super(s, i);
        }
    }


    public MainMenuScreen()
    {
        this(true);
    }

    public MainMenuScreen(boolean playBgm)
    {
        nameEditHb = Settings.isMobile ? new Hitbox(550F * Settings.scale, 180F * Settings.scale) : new Hitbox(400F * Settings.scale, 100F * Settings.scale);
        darken = false;
        superDarken = false;
        saveSlotImg = null;
        screenColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
        overlayColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
        fadedOut = false;
        isFadingOut = false;
        windId = 0L;
        charInfo = null;
        bg = new TitleBackground();
        eaPopup = null;
        screen = CurScreen.MAIN_MENU;
        saveSlotScreen = new SaveSlotScreen();
        panelScreen = new MenuPanelScreen();
        statsScreen = new StatsScreen();
        dailyScreen = new DailyScreen();
        cardLibraryScreen = new CardLibraryScreen();
        leaderboardsScreen = new LeaderboardScreen();
        relicScreen = new RelicViewScreen();
        potionScreen = new PotionViewScreen();
        creditsScreen = new CreditsScreen();
        doorUnlockScreen = new DoorUnlockScreen();
        neowNarrateScreen = new NeowNarrationScreen();
        patchNotesScreen = new PatchNotesScreen();
        charSelectScreen = new CharacterSelectScreen();
        customModeScreen = new CustomModeScreen();
        abandonPopup = new ConfirmPopup(com.megacrit.cardcrawl.screens.options.ConfirmPopup.ConfirmType.ABANDON_MAIN_MENU);
        inputSettingsScreen = new InputSettingsScreen();
        optionPanel = new OptionsPanel();
        syncMessage = new SyncMessage();
        isSettingsUp = false;
        confirmButton = new ConfirmButton(TEXT[1]);
        cancelButton = new MenuCancelButton();
        deckHb = new Hitbox(-1000F, 0.0F, 400F * Settings.scale, 80F * Settings.scale);
        buttons = new ArrayList();
        abandonedRun = false;
        CardCrawlGame.publisherIntegration.setRichPresenceDisplayInMenu();
        AbstractDungeon.player = null;
        if(Settings.isDemo && Settings.isShowBuild)
            TipTracker.reset();
        if(playBgm)
        {
            CardCrawlGame.music.changeBGM("MENU");
            if(Settings.AMBIANCE_ON)
                windId = CardCrawlGame.sound.playAndLoop("WIND");
            else
                windId = CardCrawlGame.sound.playAndLoop("WIND", 0.0F);
        }
        UnlockTracker.refresh();
        cardLibraryScreen.initialize();
        charSelectScreen.initialize();
        confirmButton.hide();
        updateAmbienceVolume();
        nameEditHb.move(200F * Settings.scale, (float)Settings.HEIGHT - 50F * Settings.scale);
        setMainMenuButtons();
        runHistoryScreen = new RunHistoryScreen();
    }

    private void setMainMenuButtons()
    {
        buttons.clear();
        int index = 0;
        if(!Settings.isMobile && !Settings.isConsoleBuild)
        {
            buttons.add(new MenuButton(MenuButton.ClickResult.QUIT, index++));
            buttons.add(new MenuButton(MenuButton.ClickResult.PATCH_NOTES, index++));
        }
        buttons.add(new MenuButton(MenuButton.ClickResult.SETTINGS, index++));
        if(!Settings.isShowBuild && statsScreen.statScreenUnlocked())
        {
            buttons.add(new MenuButton(MenuButton.ClickResult.STAT, index++));
            buttons.add(new MenuButton(MenuButton.ClickResult.INFO, index++));
        }
        if(CardCrawlGame.characterManager.anySaveFileExists())
        {
            buttons.add(new MenuButton(MenuButton.ClickResult.ABANDON_RUN, index++));
            buttons.add(new MenuButton(MenuButton.ClickResult.RESUME_GAME, index++));
        } else
        {
            buttons.add(new MenuButton(MenuButton.ClickResult.PLAY, index++));
        }
    }

    public void update()
    {
        if(isFadingOut)
        {
            InputHelper.justClickedLeft = false;
            InputHelper.justReleasedClickLeft = false;
            InputHelper.justClickedRight = false;
            InputHelper.justReleasedClickRight = false;
        }
        abandonPopup.update();
        if(abandonedRun)
        {
            abandonedRun = false;
            buttons.remove(buttons.size() - 1);
            buttons.remove(buttons.size() - 1);
            buttons.add(new MenuButton(MenuButton.ClickResult.PLAY, buttons.size()));
        }
        if(Settings.isInfo && DevInputActionSet.deleteSteamCloud.isJustPressed())
            CardCrawlGame.publisherIntegration.deleteAllCloudFiles();
        syncMessage.update();
        cancelButton.update();
        updateSettings();
        if(screen != CurScreen.SAVE_SLOT)
        {
            MenuButton b;
            for(Iterator iterator = buttons.iterator(); iterator.hasNext(); b.update())
                b = (MenuButton)iterator.next();

        }
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MainMenuScreen$CurScreen[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MainMenuScreen$CurScreen = new int[CurScreen.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MainMenuScreen$CurScreen[CurScreen.CHAR_SELECT.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MainMenuScreen$CurScreen[CurScreen.CARD_LIBRARY.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MainMenuScreen$CurScreen[CurScreen.CUSTOM.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MainMenuScreen$CurScreen[CurScreen.PANEL_MENU.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MainMenuScreen$CurScreen[CurScreen.DAILY.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MainMenuScreen$CurScreen[CurScreen.BANNER_DECK_VIEW.ordinal()] = 6;
                }
                catch(NoSuchFieldError nosuchfielderror5) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MainMenuScreen$CurScreen[CurScreen.MAIN_MENU.ordinal()] = 7;
                }
                catch(NoSuchFieldError nosuchfielderror6) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MainMenuScreen$CurScreen[CurScreen.LEADERBOARD.ordinal()] = 8;
                }
                catch(NoSuchFieldError nosuchfielderror7) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MainMenuScreen$CurScreen[CurScreen.RELIC_VIEW.ordinal()] = 9;
                }
                catch(NoSuchFieldError nosuchfielderror8) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MainMenuScreen$CurScreen[CurScreen.POTION_VIEW.ordinal()] = 10;
                }
                catch(NoSuchFieldError nosuchfielderror9) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MainMenuScreen$CurScreen[CurScreen.SAVE_SLOT.ordinal()] = 11;
                }
                catch(NoSuchFieldError nosuchfielderror10) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MainMenuScreen$CurScreen[CurScreen.SETTINGS.ordinal()] = 12;
                }
                catch(NoSuchFieldError nosuchfielderror11) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MainMenuScreen$CurScreen[CurScreen.TRIALS.ordinal()] = 13;
                }
                catch(NoSuchFieldError nosuchfielderror12) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MainMenuScreen$CurScreen[CurScreen.STATS.ordinal()] = 14;
                }
                catch(NoSuchFieldError nosuchfielderror13) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MainMenuScreen$CurScreen[CurScreen.CREDITS.ordinal()] = 15;
                }
                catch(NoSuchFieldError nosuchfielderror14) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MainMenuScreen$CurScreen[CurScreen.DOOR_UNLOCK.ordinal()] = 16;
                }
                catch(NoSuchFieldError nosuchfielderror15) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MainMenuScreen$CurScreen[CurScreen.NEOW_SCREEN.ordinal()] = 17;
                }
                catch(NoSuchFieldError nosuchfielderror16) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MainMenuScreen$CurScreen[CurScreen.PATCH_NOTES.ordinal()] = 18;
                }
                catch(NoSuchFieldError nosuchfielderror17) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MainMenuScreen$CurScreen[CurScreen.RUN_HISTORY.ordinal()] = 19;
                }
                catch(NoSuchFieldError nosuchfielderror18) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MainMenuScreen$CurScreen[CurScreen.INPUT_SETTINGS.ordinal()] = 20;
                }
                catch(NoSuchFieldError nosuchfielderror19) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen.CurScreen[screen.ordinal()])
        {
        case 1: // '\001'
            updateCharSelectController();
            charSelectScreen.update();
            break;

        case 2: // '\002'
            cardLibraryScreen.update();
            break;

        case 3: // '\003'
            customModeScreen.update();
            break;

        case 4: // '\004'
            updateMenuPanelController();
            panelScreen.update();
            break;

        case 5: // '\005'
            dailyScreen.update();
            break;

        case 7: // '\007'
            updateMenuButtonController();
            break;

        case 8: // '\b'
            leaderboardsScreen.update();
            break;

        case 9: // '\t'
            relicScreen.update();
            break;

        case 10: // '\n'
            potionScreen.update();
            break;

        case 14: // '\016'
            statsScreen.update();
            break;

        case 15: // '\017'
            creditsScreen.update();
            break;

        case 16: // '\020'
            doorUnlockScreen.update();
            break;

        case 17: // '\021'
            neowNarrateScreen.update();
            break;

        case 18: // '\022'
            patchNotesScreen.update();
            break;

        case 19: // '\023'
            runHistoryScreen.update();
            break;

        case 20: // '\024'
            inputSettingsScreen.update();
            break;
        }
        saveSlotScreen.update();
        bg.update();
        if(superDarken)
            screenColor.a = MathHelper.popLerpSnap(screenColor.a, 1.0F);
        else
        if(darken)
            screenColor.a = MathHelper.popLerpSnap(screenColor.a, 0.8F);
        else
            screenColor.a = MathHelper.popLerpSnap(screenColor.a, 0.0F);
        if(!statsScreen.screenUp)
            updateRenameArea();
        if(charInfo != null && charInfo.resumeGame)
        {
            deckHb.update();
            if(deckHb.justHovered)
                CardCrawlGame.sound.play("UI_HOVER");
        }
        if(!isFadingOut)
            handleInput();
        fadeOut();
    }

    private void updateMenuButtonController()
    {
        if(!Settings.isControllerMode || EarlyAccessPopup.isUp)
            return;
        boolean anyHovered = false;
        int index = 0;
        Iterator iterator = buttons.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            MenuButton b = (MenuButton)iterator.next();
            if(b.hb.hovered)
            {
                anyHovered = true;
                break;
            }
            index++;
        } while(true);
        if(anyHovered)
        {
            if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
            {
                if(--index < 0)
                    index = buttons.size() - 1;
                CInputHelper.setCursor(((MenuButton)buttons.get(index)).hb);
            } else
            if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
            {
                if(++index > buttons.size() - 1)
                    index = 0;
                CInputHelper.setCursor(((MenuButton)buttons.get(index)).hb);
            }
        } else
        {
            index = buttons.size() - 1;
            CInputHelper.setCursor(((MenuButton)buttons.get(index)).hb);
        }
    }

    private void updateCharSelectController()
    {
        if(!Settings.isControllerMode || isFadingOut)
            return;
        boolean anyHovered = false;
        int index = 0;
        Iterator iterator = charSelectScreen.options.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            CharacterOption b = (CharacterOption)iterator.next();
            if(b.hb.hovered)
            {
                anyHovered = true;
                break;
            }
            index++;
        } while(true);
        if(!anyHovered)
        {
            index = 0;
            CInputHelper.setCursor(((CharacterOption)charSelectScreen.options.get(index)).hb);
            ((CharacterOption)charSelectScreen.options.get(index)).hb.clicked = true;
        } else
        {
            if(CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
            {
                if(--index < 0)
                    index = charSelectScreen.options.size() - 1;
                CInputHelper.setCursor(((CharacterOption)charSelectScreen.options.get(index)).hb);
                ((CharacterOption)charSelectScreen.options.get(index)).hb.clicked = true;
            } else
            if(CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed())
            {
                if(++index > charSelectScreen.options.size() - 1)
                    index = 0;
                CInputHelper.setCursor(((CharacterOption)charSelectScreen.options.get(index)).hb);
                ((CharacterOption)charSelectScreen.options.get(index)).hb.clicked = true;
            }
            if(((CharacterOption)charSelectScreen.options.get(index)).locked)
                charSelectScreen.confirmButton.hide();
            else
                charSelectScreen.confirmButton.show();
        }
    }

    private void updateMenuPanelController()
    {
        if(!Settings.isControllerMode)
            return;
        boolean anyHovered = false;
        int index = 0;
        Iterator iterator = panelScreen.panels.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            MainMenuPanelButton b = (MainMenuPanelButton)iterator.next();
            if(b.hb.hovered)
            {
                anyHovered = true;
                break;
            }
            index++;
        } while(true);
        if(anyHovered)
        {
            if(CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
            {
                if(--index < 0)
                    index = panelScreen.panels.size() - 1;
                if(((MainMenuPanelButton)panelScreen.panels.get(index)).pColor == MainMenuPanelButton.PanelColor.GRAY)
                    index--;
                CInputHelper.setCursor(((MainMenuPanelButton)panelScreen.panels.get(index)).hb);
            } else
            if(CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed())
            {
                if(++index > panelScreen.panels.size() - 1)
                    index = 0;
                if(((MainMenuPanelButton)panelScreen.panels.get(index)).pColor == MainMenuPanelButton.PanelColor.GRAY)
                    index = 0;
                CInputHelper.setCursor(((MainMenuPanelButton)panelScreen.panels.get(index)).hb);
            }
        } else
        {
            index = 0;
            CInputHelper.setCursor(((MainMenuPanelButton)panelScreen.panels.get(index)).hb);
        }
    }

    private void updateSettings()
    {
        if(saveSlotScreen.shown)
            return;
        if(!EarlyAccessPopup.isUp && InputHelper.pressedEscape && screen == CurScreen.MAIN_MENU && !isFadingOut)
            if(!isSettingsUp)
            {
                GameCursor.hidden = false;
                CardCrawlGame.sound.play("END_TURN");
                isSettingsUp = true;
                darken();
                InputHelper.pressedEscape = false;
                statsScreen.hide();
                dailyScreen.hide();
                cancelButton.hide();
                CardCrawlGame.cancelButton.show(TEXT[2]);
                screen = CurScreen.SETTINGS;
                panelScreen.panels.clear();
                hideMenuButtons();
            } else
            if(!EarlyAccessPopup.isUp)
            {
                isSettingsUp = false;
                CardCrawlGame.cancelButton.hide();
                screen = CurScreen.MAIN_MENU;
                if(screen == CurScreen.MAIN_MENU)
                    cancelButton.hide();
            }
        if(isSettingsUp)
            optionPanel.update();
        CardCrawlGame.cancelButton.update();
    }

    private void updateRenameArea()
    {
        if(screen == CurScreen.MAIN_MENU)
            nameEditHb.update();
        if(screen == CurScreen.MAIN_MENU && (nameEditHb.hovered && InputHelper.justClickedLeft || CInputActionSet.map.isJustPressed()))
        {
            InputHelper.justClickedLeft = false;
            nameEditHb.hovered = false;
            saveSlotScreen.open(CardCrawlGame.playerName);
            screen = CurScreen.SAVE_SLOT;
        }
        if(bg.slider <= 0.1F && CardCrawlGame.saveSlotPref.getInteger("DEFAULT_SLOT", -1) == -1 && screen == CurScreen.MAIN_MENU && !setDefaultSlot())
        {
            logger.info("No saves detected, opening Save Slot screen automatically.");
            CardCrawlGame.playerPref.putBoolean("ftuePopupShown", true);
            saveSlotScreen.open(CardCrawlGame.playerName);
            screen = CurScreen.SAVE_SLOT;
        }
    }

    private boolean setDefaultSlot()
    {
        if(!CardCrawlGame.playerPref.getString("name", "").equals(""))
        {
            logger.info("Migration to Save Slot schema detected, setting DEFAULT_SLOT to 0.");
            CardCrawlGame.saveSlot = 0;
            CardCrawlGame.saveSlotPref.putInteger("DEFAULT_SLOT", 0);
            CardCrawlGame.saveSlotPref.flush();
            return true;
        } else
        {
            return false;
        }
    }

    private void handleInput()
    {
        confirmButton.update();
    }

    public void fadeOutMusic()
    {
        CardCrawlGame.music.fadeOutBGM();
        if(Settings.AMBIANCE_ON)
            CardCrawlGame.sound.fadeOut("WIND", windId);
    }

    public void render(SpriteBatch sb)
    {
        bg.render(sb);
        cancelButton.render(sb);
        renderNameEdit(sb);
        MenuButton b;
        for(Iterator iterator = buttons.iterator(); iterator.hasNext(); b.render(sb))
            b = (MenuButton)iterator.next();

        abandonPopup.render(sb);
        sb.setColor(screenColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        if(isFadingOut)
            confirmButton.update();
        if(screen == CurScreen.CHAR_SELECT)
            charSelectScreen.render(sb);
        sb.setColor(overlayColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        renderSettings(sb);
        confirmButton.render(sb);
        if(CardCrawlGame.displayVersion)
            FontHelper.renderSmartText(sb, FontHelper.cardDescFont_N, VERSION_INFO, 20F * Settings.scale - 700F * bg.slider, 30F * Settings.scale, 10000F, 32F * Settings.scale, new Color(1.0F, 1.0F, 1.0F, 0.3F));
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen.CurScreen[screen.ordinal()])
        {
        case 2: // '\002'
            cardLibraryScreen.render(sb);
            break;

        case 3: // '\003'
            customModeScreen.render(sb);
            break;

        case 4: // '\004'
            panelScreen.render(sb);
            break;

        case 5: // '\005'
            dailyScreen.render(sb);
            sb.setColor(overlayColor);
            sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
            break;

        case 9: // '\t'
            relicScreen.render(sb);
            break;

        case 10: // '\n'
            potionScreen.render(sb);
            break;

        case 8: // '\b'
            leaderboardsScreen.render(sb);
            break;

        case 14: // '\016'
            statsScreen.render(sb);
            break;

        case 19: // '\023'
            runHistoryScreen.render(sb);
            break;

        case 20: // '\024'
            inputSettingsScreen.render(sb);
            break;

        case 15: // '\017'
            creditsScreen.render(sb);
            break;

        case 16: // '\020'
            doorUnlockScreen.render(sb);
            break;

        case 17: // '\021'
            neowNarrateScreen.render(sb);
            break;

        case 18: // '\022'
            patchNotesScreen.render(sb);
            break;
        }
        saveSlotScreen.render(sb);
        syncMessage.render(sb);
        if(eaPopup != null)
            eaPopup.render(sb);
    }

    private void renderSettings(SpriteBatch sb)
    {
        if(isSettingsUp && screen == CurScreen.SETTINGS)
            optionPanel.render(sb);
        CardCrawlGame.cancelButton.render(sb);
    }

    private void renderNameEdit(SpriteBatch sb)
    {
        if(Settings.isMobile)
        {
            if(!nameEditHb.hovered)
                FontHelper.renderSmartText(sb, FontHelper.cardEnergyFont_L, CardCrawlGame.playerName, 140F * Settings.scale - 500F * bg.slider, (float)Settings.HEIGHT - 30F * Settings.scale, 1000F, 30F * Settings.scale, Color.GOLD, 0.9F);
            else
                FontHelper.renderSmartText(sb, FontHelper.cardEnergyFont_L, CardCrawlGame.playerName, 140F * Settings.scale - 500F * bg.slider, (float)Settings.HEIGHT - 30F * Settings.scale, 1000F, 30F * Settings.scale, Settings.GREEN_TEXT_COLOR, 0.9F);
        } else
        if(!nameEditHb.hovered)
            FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, CardCrawlGame.playerName, 100F * Settings.scale - 500F * bg.slider, (float)Settings.HEIGHT - 24F * Settings.scale, 1000F, 30F * Settings.scale, Color.GOLD, 1.0F);
        else
            FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, CardCrawlGame.playerName, 100F * Settings.scale - 500F * bg.slider, (float)Settings.HEIGHT - 24F * Settings.scale, 1000F, 30F * Settings.scale, Settings.GREEN_TEXT_COLOR, 1.0F);
        if(Settings.isTouchScreen || Settings.isMobile)
        {
            if(!Settings.isMobile)
                FontHelper.renderSmartText(sb, FontHelper.cardDescFont_N, TEXT[5], 100F * Settings.scale - 500F * bg.slider, (float)Settings.HEIGHT - 60F * Settings.scale, 1000F, 30F * Settings.scale, Color.SKY, 1.0F);
            else
                FontHelper.renderSmartText(sb, FontHelper.largeDialogOptionFont, TEXT[5], 140F * Settings.scale - 500F * bg.slider, (float)Settings.HEIGHT - 80F * Settings.scale, 1000F, 30F * Settings.scale, Color.SKY);
        } else
        if(!Settings.isControllerMode)
        {
            FontHelper.renderSmartText(sb, FontHelper.cardDescFont_N, TEXT[3], 100F * Settings.scale - 500F * bg.slider, (float)Settings.HEIGHT - 60F * Settings.scale, 1000F, 30F * Settings.scale, Color.SKY, 1.0F);
        } else
        {
            sb.draw(CInputActionSet.map.getKeyImg(), (-32F + 120F * Settings.scale) - 500F * bg.slider, (-32F + (float)Settings.HEIGHT) - 78F * Settings.scale, 32F, 32F, 64F, 64F, Settings.scale * 0.8F, Settings.scale * 0.8F, 0.0F, 0, 0, 64, 64, false, false);
            FontHelper.renderSmartText(sb, FontHelper.cardDescFont_N, TEXT[4], 150F * Settings.scale - 500F * bg.slider, (float)Settings.HEIGHT - 70F * Settings.scale, 1000F, 30F * Settings.scale, Color.SKY);
        }
        if(Settings.isMobile)
            sb.draw(CardCrawlGame.getSaveSlotImg(), 70F * Settings.scale - 50F - 500F * bg.slider, (float)Settings.HEIGHT - 70F * Settings.scale - 50F, 50F, 50F, 100F, 100F, Settings.scale, Settings.scale, 0.0F, 0, 0, 100, 100, false, false);
        else
            sb.draw(CardCrawlGame.getSaveSlotImg(), 50F * Settings.scale - 50F - 500F * bg.slider, (float)Settings.HEIGHT - 50F * Settings.scale - 50F, 50F, 50F, 100F, 100F, Settings.scale * 0.75F, Settings.scale * 0.75F, 0.0F, 0, 0, 100, 100, false, false);
        nameEditHb.render(sb);
    }

    private void fadeOut()
    {
        if(isFadingOut && !fadedOut)
        {
            overlayColor.a += Gdx.graphics.getDeltaTime();
            if(overlayColor.a > 1.0F)
            {
                overlayColor.a = 1.0F;
                fadedOut = true;
                FontHelper.ClearLeaderboardFontTextures();
            }
        } else
        if(overlayColor.a != 0.0F)
        {
            overlayColor.a -= Gdx.graphics.getDeltaTime() * 2.0F;
            if(overlayColor.a < 0.0F)
                overlayColor.a = 0.0F;
        }
    }

    public void updateAmbienceVolume()
    {
        if(Settings.AMBIANCE_ON)
            CardCrawlGame.sound.adjustVolume("WIND", windId);
        else
            CardCrawlGame.sound.adjustVolume("WIND", windId, 0.0F);
    }

    public void muteAmbienceVolume()
    {
        if(Settings.AMBIANCE_ON)
            CardCrawlGame.sound.adjustVolume("WIND", windId, 0.0F);
    }

    public void unmuteAmbienceVolume()
    {
        CardCrawlGame.sound.adjustVolume("WIND", windId);
    }

    public void darken()
    {
        darken = true;
    }

    public void lighten()
    {
        darken = false;
    }

    public void hideMenuButtons()
    {
        MenuButton b;
        for(Iterator iterator = buttons.iterator(); iterator.hasNext(); b.hide())
            b = (MenuButton)iterator.next();

    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/screens/mainMenu/MainMenuScreen.getName());
    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private static final String VERSION_INFO;
    private Hitbox nameEditHb;
    public String newName;
    public boolean darken;
    public boolean superDarken;
    public Texture saveSlotImg;
    public Color screenColor;
    public static final float OVERLAY_ALPHA = 0.8F;
    private Color overlayColor;
    public boolean fadedOut;
    public boolean isFadingOut;
    public long windId;
    private CharSelectInfo charInfo;
    public TitleBackground bg;
    private EarlyAccessPopup eaPopup;
    public CurScreen screen;
    public SaveSlotScreen saveSlotScreen;
    public MenuPanelScreen panelScreen;
    public StatsScreen statsScreen;
    public DailyScreen dailyScreen;
    public CardLibraryScreen cardLibraryScreen;
    public LeaderboardScreen leaderboardsScreen;
    public RelicViewScreen relicScreen;
    public PotionViewScreen potionScreen;
    public CreditsScreen creditsScreen;
    public DoorUnlockScreen doorUnlockScreen;
    public NeowNarrationScreen neowNarrateScreen;
    public PatchNotesScreen patchNotesScreen;
    public RunHistoryScreen runHistoryScreen;
    public CharacterSelectScreen charSelectScreen;
    public CustomModeScreen customModeScreen;
    public ConfirmPopup abandonPopup;
    public InputSettingsScreen inputSettingsScreen;
    public OptionsPanel optionPanel;
    public SyncMessage syncMessage;
    public boolean isSettingsUp;
    public ConfirmButton confirmButton;
    public MenuCancelButton cancelButton;
    private Hitbox deckHb;
    public ArrayList buttons;
    public boolean abandonedRun;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("MainMenuScreen");
        TEXT = uiStrings.TEXT;
        VERSION_INFO = CardCrawlGame.VERSION_NUM;
    }
}
