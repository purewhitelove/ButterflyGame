// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MainMenuPanelButton.java

package com.megacrit.cardcrawl.screens.mainMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.credits.CreditsScreen;
import com.megacrit.cardcrawl.daily.DailyScreen;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.DoorUnlockScreen;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import com.megacrit.cardcrawl.screens.compendium.*;
import com.megacrit.cardcrawl.screens.custom.CustomModeScreen;
import com.megacrit.cardcrawl.screens.leaderboards.LeaderboardScreen;
import com.megacrit.cardcrawl.screens.options.InputSettingsScreen;
import com.megacrit.cardcrawl.screens.runHistory.RunHistoryScreen;
import com.megacrit.cardcrawl.screens.stats.StatsScreen;
import com.megacrit.cardcrawl.ui.buttons.CancelButton;

// Referenced classes of package com.megacrit.cardcrawl.screens.mainMenu:
//            MenuPanelScreen, MainMenuScreen, MenuCancelButton

public class MainMenuPanelButton
{
    public static final class PanelColor extends Enum
    {

        public static PanelColor[] values()
        {
            return (PanelColor[])$VALUES.clone();
        }

        public static PanelColor valueOf(String name)
        {
            return (PanelColor)Enum.valueOf(com/megacrit/cardcrawl/screens/mainMenu/MainMenuPanelButton$PanelColor, name);
        }

        public static final PanelColor RED;
        public static final PanelColor BLUE;
        public static final PanelColor BEIGE;
        public static final PanelColor GRAY;
        private static final PanelColor $VALUES[];

        static 
        {
            RED = new PanelColor("RED", 0);
            BLUE = new PanelColor("BLUE", 1);
            BEIGE = new PanelColor("BEIGE", 2);
            GRAY = new PanelColor("GRAY", 3);
            $VALUES = (new PanelColor[] {
                RED, BLUE, BEIGE, GRAY
            });
        }

        private PanelColor(String s, int i)
        {
            super(s, i);
        }
    }

    public static final class PanelClickResult extends Enum
    {

        public static PanelClickResult[] values()
        {
            return (PanelClickResult[])$VALUES.clone();
        }

        public static PanelClickResult valueOf(String name)
        {
            return (PanelClickResult)Enum.valueOf(com/megacrit/cardcrawl/screens/mainMenu/MainMenuPanelButton$PanelClickResult, name);
        }

        public static final PanelClickResult PLAY_NORMAL;
        public static final PanelClickResult PLAY_DAILY;
        public static final PanelClickResult PLAY_CUSTOM;
        public static final PanelClickResult INFO_CARD;
        public static final PanelClickResult INFO_RELIC;
        public static final PanelClickResult INFO_POTION;
        public static final PanelClickResult STAT_CHAR;
        public static final PanelClickResult STAT_LEADERBOARDS;
        public static final PanelClickResult STAT_HISTORY;
        public static final PanelClickResult SETTINGS_GAME;
        public static final PanelClickResult SETTINGS_INPUT;
        public static final PanelClickResult SETTINGS_CREDITS;
        private static final PanelClickResult $VALUES[];

        static 
        {
            PLAY_NORMAL = new PanelClickResult("PLAY_NORMAL", 0);
            PLAY_DAILY = new PanelClickResult("PLAY_DAILY", 1);
            PLAY_CUSTOM = new PanelClickResult("PLAY_CUSTOM", 2);
            INFO_CARD = new PanelClickResult("INFO_CARD", 3);
            INFO_RELIC = new PanelClickResult("INFO_RELIC", 4);
            INFO_POTION = new PanelClickResult("INFO_POTION", 5);
            STAT_CHAR = new PanelClickResult("STAT_CHAR", 6);
            STAT_LEADERBOARDS = new PanelClickResult("STAT_LEADERBOARDS", 7);
            STAT_HISTORY = new PanelClickResult("STAT_HISTORY", 8);
            SETTINGS_GAME = new PanelClickResult("SETTINGS_GAME", 9);
            SETTINGS_INPUT = new PanelClickResult("SETTINGS_INPUT", 10);
            SETTINGS_CREDITS = new PanelClickResult("SETTINGS_CREDITS", 11);
            $VALUES = (new PanelClickResult[] {
                PLAY_NORMAL, PLAY_DAILY, PLAY_CUSTOM, INFO_CARD, INFO_RELIC, INFO_POTION, STAT_CHAR, STAT_LEADERBOARDS, STAT_HISTORY, SETTINGS_GAME, 
                SETTINGS_INPUT, SETTINGS_CREDITS
            });
        }

        private PanelClickResult(String s, int i)
        {
            super(s, i);
        }
    }


    public MainMenuPanelButton(PanelClickResult setResult, PanelColor setColor, float x, float y)
    {
        hb = new Hitbox(400F * Settings.scale, 700F * Settings.scale);
        gColor = Settings.GOLD_COLOR.cpy();
        cColor = Settings.CREAM_COLOR.cpy();
        wColor = Color.WHITE.cpy();
        grColor = Color.GRAY.cpy();
        portraitImg = null;
        panelImg = ImageMaster.MENU_PANEL_BG_BLUE;
        header = null;
        description = null;
        uiScale = 1.0F;
        result = setResult;
        pColor = setColor;
        hb.move(x, y);
        setLabel();
        animTime = MathUtils.random(0.2F, 0.35F);
        animTimer = animTime;
    }

    private void setLabel()
    {
        panelImg = ImageMaster.MENU_PANEL_BG_BEIGE;
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MainMenuPanelButton$PanelClickResult[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MainMenuPanelButton$PanelClickResult = new int[PanelClickResult.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MainMenuPanelButton$PanelClickResult[PanelClickResult.PLAY_CUSTOM.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MainMenuPanelButton$PanelClickResult[PanelClickResult.PLAY_DAILY.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MainMenuPanelButton$PanelClickResult[PanelClickResult.PLAY_NORMAL.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MainMenuPanelButton$PanelClickResult[PanelClickResult.INFO_CARD.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MainMenuPanelButton$PanelClickResult[PanelClickResult.INFO_RELIC.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MainMenuPanelButton$PanelClickResult[PanelClickResult.INFO_POTION.ordinal()] = 6;
                }
                catch(NoSuchFieldError nosuchfielderror5) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MainMenuPanelButton$PanelClickResult[PanelClickResult.STAT_CHAR.ordinal()] = 7;
                }
                catch(NoSuchFieldError nosuchfielderror6) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MainMenuPanelButton$PanelClickResult[PanelClickResult.STAT_HISTORY.ordinal()] = 8;
                }
                catch(NoSuchFieldError nosuchfielderror7) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MainMenuPanelButton$PanelClickResult[PanelClickResult.STAT_LEADERBOARDS.ordinal()] = 9;
                }
                catch(NoSuchFieldError nosuchfielderror8) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MainMenuPanelButton$PanelClickResult[PanelClickResult.SETTINGS_CREDITS.ordinal()] = 10;
                }
                catch(NoSuchFieldError nosuchfielderror9) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MainMenuPanelButton$PanelClickResult[PanelClickResult.SETTINGS_GAME.ordinal()] = 11;
                }
                catch(NoSuchFieldError nosuchfielderror10) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MainMenuPanelButton$PanelClickResult[PanelClickResult.SETTINGS_INPUT.ordinal()] = 12;
                }
                catch(NoSuchFieldError nosuchfielderror11) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.screens.mainMenu.MainMenuPanelButton.PanelClickResult[result.ordinal()])
        {
        default:
            break;

        case 1: // '\001'
            header = MenuPanelScreen.TEXT[39];
            if(pColor == PanelColor.GRAY)
            {
                description = MenuPanelScreen.TEXT[37];
                panelImg = ImageMaster.MENU_PANEL_BG_GRAY;
            } else
            {
                description = MenuPanelScreen.TEXT[40];
                panelImg = ImageMaster.MENU_PANEL_BG_RED;
            }
            portraitImg = ImageMaster.P_LOOP;
            break;

        case 2: // '\002'
            header = MenuPanelScreen.TEXT[3];
            description = MenuPanelScreen.TEXT[5];
            portraitImg = ImageMaster.P_DAILY;
            if(pColor == PanelColor.GRAY)
                panelImg = ImageMaster.MENU_PANEL_BG_GRAY;
            else
                panelImg = ImageMaster.MENU_PANEL_BG_BLUE;
            break;

        case 3: // '\003'
            header = MenuPanelScreen.TEXT[0];
            description = MenuPanelScreen.TEXT[2];
            portraitImg = ImageMaster.P_STANDARD;
            break;

        case 4: // '\004'
            header = MenuPanelScreen.TEXT[9];
            description = MenuPanelScreen.TEXT[11];
            portraitImg = ImageMaster.P_INFO_CARD;
            break;

        case 5: // '\005'
            header = MenuPanelScreen.TEXT[12];
            description = MenuPanelScreen.TEXT[14];
            portraitImg = ImageMaster.P_INFO_RELIC;
            panelImg = ImageMaster.MENU_PANEL_BG_BLUE;
            break;

        case 6: // '\006'
            header = MenuPanelScreen.TEXT[43];
            description = MenuPanelScreen.TEXT[44];
            portraitImg = ImageMaster.P_INFO_POTION;
            panelImg = ImageMaster.MENU_PANEL_BG_RED;
            break;

        case 7: // '\007'
            header = MenuPanelScreen.TEXT[18];
            description = MenuPanelScreen.TEXT[20];
            portraitImg = ImageMaster.P_STAT_CHAR;
            break;

        case 8: // '\b'
            header = MenuPanelScreen.TEXT[24];
            description = MenuPanelScreen.TEXT[26];
            portraitImg = ImageMaster.P_STAT_HISTORY;
            panelImg = ImageMaster.MENU_PANEL_BG_RED;
            break;

        case 9: // '\t'
            header = MenuPanelScreen.TEXT[21];
            description = MenuPanelScreen.TEXT[23];
            portraitImg = ImageMaster.P_STAT_LEADERBOARD;
            panelImg = ImageMaster.MENU_PANEL_BG_BLUE;
            break;

        case 10: // '\n'
            header = MenuPanelScreen.TEXT[33];
            description = MenuPanelScreen.TEXT[35];
            portraitImg = ImageMaster.P_SETTING_CREDITS;
            panelImg = ImageMaster.MENU_PANEL_BG_RED;
            break;

        case 11: // '\013'
            header = MenuPanelScreen.TEXT[27];
            if(!Settings.isConsoleBuild)
                description = MenuPanelScreen.TEXT[29];
            else
                description = MenuPanelScreen.TEXT[42];
            portraitImg = ImageMaster.P_SETTING_GAME;
            break;

        case 12: // '\f'
            header = MenuPanelScreen.TEXT[30];
            if(!Settings.isConsoleBuild)
                description = MenuPanelScreen.TEXT[32];
            else
                description = MenuPanelScreen.TEXT[41];
            portraitImg = ImageMaster.P_SETTING_INPUT;
            panelImg = ImageMaster.MENU_PANEL_BG_BLUE;
            break;
        }
    }

    public void update()
    {
        if(pColor != PanelColor.GRAY)
            hb.update();
        if(hb.justHovered)
            CardCrawlGame.sound.playV("UI_HOVER", 0.5F);
        if(hb.hovered)
        {
            uiScale = MathHelper.fadeLerpSnap(uiScale, 1.025F);
            if(InputHelper.justClickedLeft)
                hb.clickStarted = true;
        } else
        {
            uiScale = MathHelper.cardScaleLerpSnap(uiScale, 1.0F);
        }
        if(hb.hovered && CInputActionSet.select.isJustPressed())
            hb.clicked = true;
        if(hb.clicked)
        {
            hb.clicked = false;
            CardCrawlGame.sound.play("DECK_OPEN");
            CardCrawlGame.mainMenuScreen.panelScreen.hide();
            buttonEffect();
        }
        animatePanelIn();
    }

    private void animatePanelIn()
    {
        animTimer -= Gdx.graphics.getDeltaTime();
        if(animTimer < 0.0F)
            animTimer = 0.0F;
        yMod = Interpolation.swingIn.apply(0.0F, START_Y, animTimer / animTime);
        wColor.a = 1.0F - animTimer / animTime;
        cColor.a = wColor.a;
        gColor.a = wColor.a;
        grColor.a = wColor.a;
    }

    private void buttonEffect()
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.screens.mainMenu.MainMenuPanelButton.PanelClickResult[result.ordinal()])
        {
        case 4: // '\004'
            CardCrawlGame.mainMenuScreen.cardLibraryScreen.open();
            break;

        case 5: // '\005'
            CardCrawlGame.mainMenuScreen.relicScreen.open();
            break;

        case 6: // '\006'
            CardCrawlGame.mainMenuScreen.potionScreen.open();
            break;

        case 1: // '\001'
            CardCrawlGame.mainMenuScreen.customModeScreen.open();
            break;

        case 2: // '\002'
            CardCrawlGame.mainMenuScreen.dailyScreen.open();
            break;

        case 3: // '\003'
            CardCrawlGame.mainMenuScreen.charSelectScreen.open(false);
            break;

        case 10: // '\n'
            DoorUnlockScreen.show = false;
            CardCrawlGame.mainMenuScreen.creditsScreen.open(false);
            break;

        case 11: // '\013'
            CardCrawlGame.sound.play("END_TURN");
            CardCrawlGame.mainMenuScreen.isSettingsUp = true;
            InputHelper.pressedEscape = false;
            CardCrawlGame.mainMenuScreen.statsScreen.hide();
            CardCrawlGame.mainMenuScreen.cancelButton.hide();
            CardCrawlGame.cancelButton.show(MainMenuScreen.TEXT[2]);
            CardCrawlGame.mainMenuScreen.screen = MainMenuScreen.CurScreen.SETTINGS;
            break;

        case 12: // '\f'
            CardCrawlGame.mainMenuScreen.inputSettingsScreen.open();
            break;

        case 7: // '\007'
            CardCrawlGame.mainMenuScreen.statsScreen.open();
            break;

        case 8: // '\b'
            CardCrawlGame.mainMenuScreen.runHistoryScreen.open();
            break;

        case 9: // '\t'
            CardCrawlGame.mainMenuScreen.leaderboardsScreen.open();
            break;
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(wColor);
        sb.draw(panelImg, hb.cX - 256F, (hb.cY + yMod) - 400F, 256F, 400F, 512F, 800F, uiScale * Settings.scale, uiScale * Settings.scale, 0.0F, 0, 0, 512, 800, false, false);
        if(hb.hovered)
        {
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, (uiScale - 1.0F) * 16F));
            sb.setBlendFunction(770, 1);
            sb.draw(ImageMaster.MENU_PANEL_BG_BLUE, hb.cX - 256F, (hb.cY + yMod) - 400F, 256F, 400F, 512F, 800F, uiScale * Settings.scale, uiScale * Settings.scale, 0.0F, 0, 0, 512, 800, false, false);
            sb.setBlendFunction(770, 771);
        }
        if(pColor == PanelColor.GRAY)
            sb.setColor(grColor);
        else
            sb.setColor(wColor);
        sb.draw(portraitImg, hb.cX - 158.5F, ((hb.cY + yMod) - 103F) + 140F * Settings.scale, 158.5F, 103F, 317F, 206F, Settings.scale, Settings.scale, 0.0F, 0, 0, 317, 206, false, false);
        if(pColor == PanelColor.GRAY)
        {
            sb.setColor(wColor);
            sb.draw(ImageMaster.P_LOCK, hb.cX - 158.5F, ((hb.cY + yMod) - 103F) + 140F * Settings.scale, 158.5F, 103F, 317F, 206F, Settings.scale, Settings.scale, 0.0F, 0, 0, 317, 206, false, false);
        }
        sb.draw(ImageMaster.MENU_PANEL_FRAME, hb.cX - 256F, (hb.cY + yMod) - 400F, 256F, 400F, 512F, 800F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 800, false, false);
        if(FontHelper.getWidth(FontHelper.damageNumberFont, header, 0.8F) > 310F * Settings.scale)
            FontHelper.renderFontCenteredHeight(sb, FontHelper.damageNumberFont, header, hb.cX - 138F * Settings.scale, hb.cY + yMod + 294F * Settings.scale, 280F * Settings.scale, gColor, 0.7F);
        else
            FontHelper.renderFontCenteredHeight(sb, FontHelper.damageNumberFont, header, hb.cX - 153F * Settings.scale, hb.cY + yMod + 294F * Settings.scale, 310F * Settings.scale, gColor, 0.8F);
        FontHelper.renderFontCenteredHeight(sb, FontHelper.charDescFont, description, hb.cX - 153F * Settings.scale, (hb.cY + yMod) - 130F * Settings.scale, 330F * Settings.scale, cColor);
        hb.render(sb);
    }

    public Hitbox hb;
    private PanelClickResult result;
    public PanelColor pColor;
    private Color gColor;
    private Color cColor;
    private Color wColor;
    private Color grColor;
    private Texture portraitImg;
    private Texture panelImg;
    private static final int W = 512;
    private static final int H = 800;
    private static final int P_W = 317;
    private static final int P_H = 206;
    private String header;
    private String description;
    private float yMod;
    private float animTimer;
    private float animTime;
    private static final float START_Y;
    private float uiScale;

    static 
    {
        START_Y = -100F * Settings.scale;
    }
}
