// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Settings.java

package com.megacrit.cardcrawl.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.Prefs;
import com.megacrit.cardcrawl.helpers.SaveHelper;
import com.megacrit.cardcrawl.screens.custom.CustomModeScreen;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.core:
//            DisplayConfig, CardCrawlGame

public class Settings
{
    public static final class GameLanguage extends Enum
    {

        public static GameLanguage[] values()
        {
            return (GameLanguage[])$VALUES.clone();
        }

        public static GameLanguage valueOf(String name)
        {
            return (GameLanguage)Enum.valueOf(com/megacrit/cardcrawl/core/Settings$GameLanguage, name);
        }

        public static final GameLanguage ENG;
        public static final GameLanguage DUT;
        public static final GameLanguage EPO;
        public static final GameLanguage PTB;
        public static final GameLanguage ZHS;
        public static final GameLanguage ZHT;
        public static final GameLanguage FIN;
        public static final GameLanguage FRA;
        public static final GameLanguage DEU;
        public static final GameLanguage GRE;
        public static final GameLanguage IND;
        public static final GameLanguage ITA;
        public static final GameLanguage JPN;
        public static final GameLanguage KOR;
        public static final GameLanguage NOR;
        public static final GameLanguage POL;
        public static final GameLanguage RUS;
        public static final GameLanguage SPA;
        public static final GameLanguage SRP;
        public static final GameLanguage SRB;
        public static final GameLanguage THA;
        public static final GameLanguage TUR;
        public static final GameLanguage UKR;
        public static final GameLanguage VIE;
        public static final GameLanguage WWW;
        private static final GameLanguage $VALUES[];

        static 
        {
            ENG = new GameLanguage("ENG", 0);
            DUT = new GameLanguage("DUT", 1);
            EPO = new GameLanguage("EPO", 2);
            PTB = new GameLanguage("PTB", 3);
            ZHS = new GameLanguage("ZHS", 4);
            ZHT = new GameLanguage("ZHT", 5);
            FIN = new GameLanguage("FIN", 6);
            FRA = new GameLanguage("FRA", 7);
            DEU = new GameLanguage("DEU", 8);
            GRE = new GameLanguage("GRE", 9);
            IND = new GameLanguage("IND", 10);
            ITA = new GameLanguage("ITA", 11);
            JPN = new GameLanguage("JPN", 12);
            KOR = new GameLanguage("KOR", 13);
            NOR = new GameLanguage("NOR", 14);
            POL = new GameLanguage("POL", 15);
            RUS = new GameLanguage("RUS", 16);
            SPA = new GameLanguage("SPA", 17);
            SRP = new GameLanguage("SRP", 18);
            SRB = new GameLanguage("SRB", 19);
            THA = new GameLanguage("THA", 20);
            TUR = new GameLanguage("TUR", 21);
            UKR = new GameLanguage("UKR", 22);
            VIE = new GameLanguage("VIE", 23);
            WWW = new GameLanguage("WWW", 24);
            $VALUES = (new GameLanguage[] {
                ENG, DUT, EPO, PTB, ZHS, ZHT, FIN, FRA, DEU, GRE, 
                IND, ITA, JPN, KOR, NOR, POL, RUS, SPA, SRP, SRB, 
                THA, TUR, UKR, VIE, WWW
            });
        }

        private GameLanguage(String s, int i)
        {
            super(s, i);
        }
    }


    public Settings()
    {
    }

    public static void initialize(boolean reloaded)
    {
        if(!reloaded)
            initializeDisplay();
        initializeSoundPref();
        initializeGamePref(reloaded);
    }

    private static void initializeDisplay()
    {
        logger.info("Initializing display settings...");
        DisplayConfig displayConf = DisplayConfig.readConfig();
        M_W = Gdx.graphics.getWidth();
        M_H = Gdx.graphics.getHeight();
        WIDTH = displayConf.getWidth();
        HEIGHT = displayConf.getHeight();
        MAX_FPS = displayConf.getMaxFPS();
        SAVED_WIDTH = WIDTH;
        SAVED_HEIGHT = HEIGHT;
        IS_FULLSCREEN = displayConf.getIsFullscreen();
        IS_W_FULLSCREEN = displayConf.getWFS();
        IS_V_SYNC = displayConf.getIsVsync();
        float aspectRatio = (float)WIDTH / (float)HEIGHT;
        boolean isUltrawide = false;
        isLetterbox = aspectRatio > 2.34F || aspectRatio < 1.3332F;
        if(aspectRatio > 1.32F && aspectRatio < 1.34F)
            isFourByThree = true;
        else
        if(aspectRatio > 1.59F && aspectRatio < 1.61F)
            isSixteenByTen = true;
        else
        if(aspectRatio >= 1.78F && aspectRatio > 1.78F)
            isUltrawide = true;
        if(isLetterbox)
        {
            if(aspectRatio < 1.333F)
            {
                HEIGHT = MathUtils.round((float)WIDTH * 0.75F);
                HORIZ_LETTERBOX_AMT = (M_H - HEIGHT) / 2;
                HORIZ_LETTERBOX_AMT += 2;
                scale = (float)WIDTH / 1920F;
                xScale = scale;
                renderScale = scale;
                yScale = (float)HEIGHT / 1080F;
                isFourByThree = true;
            } else
            if(aspectRatio > 2.34F)
            {
                WIDTH = MathUtils.round((float)HEIGHT * 2.3333F);
                VERT_LETTERBOX_AMT = (M_W - WIDTH) / 2;
                VERT_LETTERBOX_AMT++;
                scale = (float)(int)((float)HEIGHT * 1.77778F) / 1920F;
                xScale = (float)WIDTH / 1920F;
                renderScale = xScale;
                yScale = scale;
                setXOffset();
            }
        } else
        if(isFourByThree)
        {
            scale = (float)WIDTH / 1920F;
            xScale = scale;
            yScale = (float)HEIGHT / 1080F;
            renderScale = yScale;
        } else
        if(isUltrawide)
        {
            scale = (float)(int)((float)HEIGHT * 1.777778F) / 1920F;
            xScale = (float)WIDTH / 1920F;
            renderScale = xScale;
            yScale = scale;
            setXOffset();
            isLetterbox = true;
        } else
        {
            scale = (float)WIDTH / 1920F;
            xScale = scale;
            yScale = scale;
            renderScale = scale;
        }
        SCROLL_SPEED = 75F * scale;
        MAP_SCROLL_SPEED = 75F * scale;
        DEFAULT_SCROLL_LIMIT = 50F * yScale;
        MAP_DST_Y = 150F * scale;
        CLICK_DIST_THRESHOLD = 30F * scale;
        CARD_DROP_END_Y = (float)HEIGHT * 0.81F;
        POTION_W = isMobile ? 64F * scale : 56F * scale;
        POTION_Y = isMobile ? (float)HEIGHT - 42F * scale : (float)HEIGHT - 30F * scale;
        OPTION_Y = (float)HEIGHT / 2.0F - 32F * yScale;
        EVENT_Y = (float)HEIGHT / 2.0F - 128F * scale;
        CARD_VIEW_PAD_X = 40F * scale;
        CARD_VIEW_PAD_Y = 40F * scale;
        HOVER_BUTTON_RISE_AMOUNT = 8F * scale;
        CARD_SNAP_THRESHOLD = 1.0F * scale;
        UI_SNAP_THRESHOLD = 1.0F * scale;
        FOUR_BY_THREE_OFFSET_Y = 140F * yScale;
    }

    private static void setXOffset()
    {
        if(scale == 1.0F)
        {
            LETTERBOX_OFFSET_Y = 0.0F;
            return;
        }
        float offsetScale = xScale - 1.0F;
        if(offsetScale < 0.0F)
        {
            LETTERBOX_OFFSET_Y = 0.0F;
            return;
        } else
        {
            LETTERBOX_OFFSET_Y = (float)(WIDTH - 1920) * offsetScale;
            return;
        }
    }

    private static void initializeSoundPref()
    {
        logger.info("Initializing sound settings...");
        soundPref = SaveHelper.getPrefs("STSSound");
        try
        {
            soundPref.getBoolean("Ambience On");
            soundPref.getBoolean("Mute in Bg");
        }
        catch(Exception e)
        {
            soundPref.putBoolean("Ambience On", soundPref.getBoolean("Ambience On", true));
            soundPref.putBoolean("Mute in Bg", soundPref.getBoolean("Mute in Bg", true));
            soundPref.flush();
        }
        AMBIANCE_ON = soundPref.getBoolean("Ambience On", true);
        CardCrawlGame.MUTE_IF_BG = soundPref.getBoolean("Mute in Bg", true);
    }

    private static void initializeGamePref(boolean reloaded)
    {
        logger.info("Initializing game settings...");
        gamePref = SaveHelper.getPrefs("STSGameplaySettings");
        dailyPref = SaveHelper.getPrefs("STSDaily");
        try
        {
            gamePref.getBoolean("Summed Damage");
            gamePref.getBoolean("Blocked Damage");
            gamePref.getBoolean("Hand Confirmation");
            gamePref.getBoolean("Upload Data");
            gamePref.getBoolean("Particle Effects");
            gamePref.getBoolean("Fast Mode");
            gamePref.getBoolean("Show Card keys");
            gamePref.getBoolean("Bigger Text");
            gamePref.getBoolean("Long-press Enabled");
            gamePref.getBoolean("Screen Shake");
            gamePref.getBoolean("Playtester Art");
            gamePref.getBoolean("Controller Enabled");
            gamePref.getBoolean("Touchscreen Enabled");
        }
        catch(Exception e)
        {
            gamePref.putBoolean("Summed Damage", gamePref.getBoolean("Summed Damage", false));
            gamePref.putBoolean("Blocked Damage", gamePref.getBoolean("Blocked Damage", false));
            gamePref.putBoolean("Hand Confirmation", gamePref.getBoolean("Hand Confirmation", false));
            gamePref.putBoolean("Upload Data", gamePref.getBoolean("Upload Data", true));
            gamePref.putBoolean("Particle Effects", gamePref.getBoolean("Particle Effects", false));
            gamePref.putBoolean("Fast Mode", gamePref.getBoolean("Fast Mode", false));
            gamePref.putBoolean("Show Card keys", gamePref.getBoolean("Show Card keys", false));
            gamePref.putBoolean("Bigger Text", gamePref.getBoolean("Bigger Text", false));
            gamePref.putBoolean("Long-press Enabled", gamePref.getBoolean("Long-press Enabled", false));
            gamePref.putBoolean("Screen Shake", gamePref.getBoolean("Screen Shake", true));
            gamePref.putBoolean("Playtester Art", gamePref.getBoolean("Playtester Art", false));
            gamePref.putBoolean("Controller Enabled", gamePref.getBoolean("Controller Enabled", true));
            gamePref.putBoolean("Touchscreen Enabled", gamePref.getBoolean("Touchscreen Enabled", false));
            if(!reloaded)
                setLanguage(gamePref.getString("LANGUAGE", GameLanguage.ENG.name()), true);
            gamePref.flush();
        }
        SHOW_DMG_SUM = gamePref.getBoolean("Summed Damage", false);
        SHOW_DMG_BLOCK = gamePref.getBoolean("Blocked Damage", false);
        FAST_HAND_CONF = gamePref.getBoolean("Hand Confirmation", false);
        UPLOAD_DATA = gamePref.getBoolean("Upload Data", true);
        DISABLE_EFFECTS = gamePref.getBoolean("Particle Effects", false);
        FAST_MODE = gamePref.getBoolean("Fast Mode", false);
        SHOW_CARD_HOTKEYS = gamePref.getBoolean("Show Card keys", false);
        BIG_TEXT_MODE = gamePref.getBoolean("Bigger Text", false);
        USE_LONG_PRESS = gamePref.getBoolean("Long-press Enabled", false);
        SCREEN_SHAKE = gamePref.getBoolean("Screen Shake", true);
        PLAYTESTER_ART_MODE = gamePref.getBoolean("Playtester Art", false);
        CONTROLLER_ENABLED = gamePref.getBoolean("Controller Enabled", true);
        TOUCHSCREEN_ENABLED = gamePref.getBoolean("Touchscreen Enabled", false);
        if(TOUCHSCREEN_ENABLED || isConsoleBuild)
            isTouchScreen = true;
        if(!reloaded)
            setLanguage(gamePref.getString("LANGUAGE", GameLanguage.ENG.name()), true);
    }

    public static void setLanguage(GameLanguage key, boolean initial)
    {
        language = key;
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage = new int[GameLanguage.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[GameLanguage.ZHS.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[GameLanguage.ZHT.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[GameLanguage.JPN.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[GameLanguage.ENG.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[GameLanguage.DUT.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[GameLanguage.EPO.ordinal()] = 6;
                }
                catch(NoSuchFieldError nosuchfielderror5) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[GameLanguage.PTB.ordinal()] = 7;
                }
                catch(NoSuchFieldError nosuchfielderror6) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[GameLanguage.FIN.ordinal()] = 8;
                }
                catch(NoSuchFieldError nosuchfielderror7) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[GameLanguage.FRA.ordinal()] = 9;
                }
                catch(NoSuchFieldError nosuchfielderror8) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[GameLanguage.DEU.ordinal()] = 10;
                }
                catch(NoSuchFieldError nosuchfielderror9) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[GameLanguage.GRE.ordinal()] = 11;
                }
                catch(NoSuchFieldError nosuchfielderror10) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[GameLanguage.IND.ordinal()] = 12;
                }
                catch(NoSuchFieldError nosuchfielderror11) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[GameLanguage.ITA.ordinal()] = 13;
                }
                catch(NoSuchFieldError nosuchfielderror12) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[GameLanguage.KOR.ordinal()] = 14;
                }
                catch(NoSuchFieldError nosuchfielderror13) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[GameLanguage.NOR.ordinal()] = 15;
                }
                catch(NoSuchFieldError nosuchfielderror14) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[GameLanguage.POL.ordinal()] = 16;
                }
                catch(NoSuchFieldError nosuchfielderror15) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[GameLanguage.RUS.ordinal()] = 17;
                }
                catch(NoSuchFieldError nosuchfielderror16) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[GameLanguage.SPA.ordinal()] = 18;
                }
                catch(NoSuchFieldError nosuchfielderror17) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[GameLanguage.SRP.ordinal()] = 19;
                }
                catch(NoSuchFieldError nosuchfielderror18) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[GameLanguage.SRB.ordinal()] = 20;
                }
                catch(NoSuchFieldError nosuchfielderror19) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[GameLanguage.THA.ordinal()] = 21;
                }
                catch(NoSuchFieldError nosuchfielderror20) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[GameLanguage.UKR.ordinal()] = 22;
                }
                catch(NoSuchFieldError nosuchfielderror21) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[GameLanguage.TUR.ordinal()] = 23;
                }
                catch(NoSuchFieldError nosuchfielderror22) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[GameLanguage.VIE.ordinal()] = 24;
                }
                catch(NoSuchFieldError nosuchfielderror23) { }
            }
        }

        if(initial)
            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.core.Settings.GameLanguage[language.ordinal()])
            {
            case 1: // '\001'
            case 2: // '\002'
                manualAndAutoLineBreak = true;
                lineBreakViaCharacter = true;
                usesOrdinal = false;
                removeAtoZSort = true;
                break;

            case 3: // '\003'
                lineBreakViaCharacter = true;
                usesOrdinal = false;
                if(isConsoleBuild)
                {
                    manualLineBreak = true;
                    leftAlignCards = true;
                } else
                {
                    manualAndAutoLineBreak = true;
                    manualLineBreak = false;
                    leftAlignCards = false;
                }
                removeAtoZSort = true;
                break;

            case 4: // '\004'
                lineBreakViaCharacter = false;
                usesOrdinal = true;
                break;

            case 5: // '\005'
            case 6: // '\006'
            case 7: // '\007'
            case 8: // '\b'
            case 9: // '\t'
            case 10: // '\n'
            case 11: // '\013'
            case 12: // '\f'
            case 13: // '\r'
            case 14: // '\016'
            case 15: // '\017'
            case 16: // '\020'
            case 17: // '\021'
            case 18: // '\022'
            case 19: // '\023'
            case 20: // '\024'
            case 21: // '\025'
            case 22: // '\026'
            case 23: // '\027'
            case 24: // '\030'
                lineBreakViaCharacter = false;
                usesOrdinal = false;
                break;

            default:
                logger.info((new StringBuilder()).append("[ERROR] Unspecified language: ").append(key.toString()).toString());
                lineBreakViaCharacter = false;
                usesOrdinal = true;
                break;
            }
        gamePref.putString("LANGUAGE", key.name());
    }

    public static void setLanguage(String langStr, boolean initial)
    {
        try
        {
            GameLanguage langKey = GameLanguage.valueOf(langStr);
            setLanguage(langKey, initial);
        }
        catch(IllegalArgumentException ex)
        {
            setLanguageLegacy(langStr, initial);
        }
    }

    public static void setLanguageLegacy(String key, boolean initial)
    {
        String s = key;
        byte byte0 = -1;
        switch(s.hashCode())
        {
        case 60895824: 
            if(s.equals("English"))
                byte0 = 0;
            break;

        case 1048717547: 
            if(s.equals("Brazilian Portuguese"))
                byte0 = 1;
            break;

        case 137306876: 
            if(s.equals("Chinese (Simplified)"))
                byte0 = 2;
            break;

        case -293414505: 
            if(s.equals("Chinese (Traditional)"))
                byte0 = 3;
            break;

        case 811777979: 
            if(s.equals("Finnish"))
                byte0 = 4;
            break;

        case 2112439738: 
            if(s.equals("French"))
                byte0 = 5;
            break;

        case 2129449382: 
            if(s.equals("German"))
                byte0 = 6;
            break;

        case 69066464: 
            if(s.equals("Greek"))
                byte0 = 7;
            break;

        case -517823520: 
            if(s.equals("Italian"))
                byte0 = 8;
            break;

        case -1550031926: 
            if(s.equals("Indonesian"))
                byte0 = 9;
            break;

        case -688086063: 
            if(s.equals("Japanese"))
                byte0 = 10;
            break;

        case -2041773788: 
            if(s.equals("Korean"))
                byte0 = 11;
            break;

        case -1764554162: 
            if(s.equals("Norwegian"))
                byte0 = 12;
            break;

        case -1898802383: 
            if(s.equals("Polish"))
                byte0 = 13;
            break;

        case -1074763917: 
            if(s.equals("Russian"))
                byte0 = 14;
            break;

        case -347177772: 
            if(s.equals("Spanish"))
                byte0 = 15;
            break;

        case 203842592: 
            if(s.equals("Serbian-Cyrillic"))
                byte0 = 16;
            break;

        case 1744709323: 
            if(s.equals("Serbian-Latin"))
                byte0 = 17;
            break;

        case 2605500: 
            if(s.equals("Thai"))
                byte0 = 18;
            break;

        case 699082148: 
            if(s.equals("Turkish"))
                byte0 = 19;
            break;

        case -539078964: 
            if(s.equals("Ukrainian"))
                byte0 = 20;
            break;

        case -1775884449: 
            if(s.equals("Vietnamese"))
                byte0 = 21;
            break;
        }
        switch(byte0)
        {
        case 0: // '\0'
            language = GameLanguage.ENG;
            if(initial)
            {
                lineBreakViaCharacter = false;
                usesOrdinal = true;
            }
            break;

        case 1: // '\001'
            language = GameLanguage.PTB;
            if(initial)
            {
                lineBreakViaCharacter = false;
                usesOrdinal = false;
            }
            break;

        case 2: // '\002'
            language = GameLanguage.ZHS;
            if(initial)
            {
                lineBreakViaCharacter = true;
                usesOrdinal = false;
            }
            break;

        case 3: // '\003'
            language = GameLanguage.ZHT;
            if(initial)
            {
                lineBreakViaCharacter = true;
                usesOrdinal = false;
            }
            break;

        case 4: // '\004'
            language = GameLanguage.FIN;
            if(initial)
            {
                lineBreakViaCharacter = false;
                usesOrdinal = false;
            }
            break;

        case 5: // '\005'
            language = GameLanguage.FRA;
            if(initial)
            {
                lineBreakViaCharacter = false;
                usesOrdinal = false;
            }
            break;

        case 6: // '\006'
            language = GameLanguage.DEU;
            if(initial)
            {
                lineBreakViaCharacter = false;
                usesOrdinal = false;
            }
            break;

        case 7: // '\007'
            language = GameLanguage.GRE;
            if(initial)
            {
                lineBreakViaCharacter = false;
                usesOrdinal = false;
            }
            break;

        case 8: // '\b'
            language = GameLanguage.ITA;
            if(initial)
            {
                lineBreakViaCharacter = false;
                usesOrdinal = false;
            }
            break;

        case 9: // '\t'
            language = GameLanguage.IND;
            if(initial)
            {
                lineBreakViaCharacter = false;
                usesOrdinal = false;
            }
            break;

        case 10: // '\n'
            language = GameLanguage.JPN;
            if(initial)
            {
                lineBreakViaCharacter = true;
                usesOrdinal = false;
            }
            break;

        case 11: // '\013'
            language = GameLanguage.KOR;
            if(initial)
            {
                lineBreakViaCharacter = false;
                usesOrdinal = false;
            }
            break;

        case 12: // '\f'
            language = GameLanguage.NOR;
            if(initial)
            {
                lineBreakViaCharacter = false;
                usesOrdinal = false;
            }
            break;

        case 13: // '\r'
            language = GameLanguage.POL;
            if(initial)
            {
                lineBreakViaCharacter = false;
                usesOrdinal = false;
            }
            break;

        case 14: // '\016'
            language = GameLanguage.RUS;
            if(initial)
            {
                lineBreakViaCharacter = false;
                usesOrdinal = false;
            }
            break;

        case 15: // '\017'
            language = GameLanguage.SPA;
            if(initial)
            {
                lineBreakViaCharacter = false;
                usesOrdinal = false;
            }
            break;

        case 16: // '\020'
            language = GameLanguage.SRP;
            if(initial)
            {
                lineBreakViaCharacter = false;
                usesOrdinal = false;
            }
            break;

        case 17: // '\021'
            language = GameLanguage.SRB;
            if(initial)
            {
                lineBreakViaCharacter = false;
                usesOrdinal = false;
            }
            break;

        case 18: // '\022'
            language = GameLanguage.THA;
            if(initial)
            {
                lineBreakViaCharacter = false;
                usesOrdinal = false;
            }
            break;

        case 19: // '\023'
            language = GameLanguage.TUR;
            if(initial)
            {
                lineBreakViaCharacter = false;
                usesOrdinal = false;
            }
            break;

        case 20: // '\024'
            language = GameLanguage.UKR;
            if(initial)
            {
                lineBreakViaCharacter = false;
                usesOrdinal = false;
            }
            break;

        case 21: // '\025'
            language = GameLanguage.VIE;
            if(initial)
            {
                lineBreakViaCharacter = false;
                usesOrdinal = false;
            }
            // fall through

        default:
            language = GameLanguage.ENG;
            if(initial)
            {
                lineBreakViaCharacter = false;
                usesOrdinal = true;
            }
            break;
        }
        gamePref.putString("LANGUAGE", key);
    }

    public static boolean isStandardRun()
    {
        return !isDailyRun && !isTrial && !seedSet;
    }

    public static boolean treatEverythingAsUnlocked()
    {
        return isDailyRun || isTrial;
    }

    public static void setFinalActAvailability()
    {
        isFinalActAvailable = CardCrawlGame.playerPref.getBoolean((new StringBuilder()).append(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.IRONCLAD.name()).append("_WIN").toString(), false) && CardCrawlGame.playerPref.getBoolean((new StringBuilder()).append(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.THE_SILENT.name()).append("_WIN").toString(), false) && CardCrawlGame.playerPref.getBoolean((new StringBuilder()).append(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.DEFECT.name()).append("_WIN").toString(), false) && !isDailyRun && !isTrial || CustomModeScreen.finalActAvailable;
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/core/Settings.getName());
    public static boolean isDev = false;
    public static boolean isBeta = false;
    public static boolean isAlpha = false;
    public static boolean isModded = false;
    public static boolean isControllerMode = false;
    public static boolean isMobile = false;
    public static boolean testFonts = false;
    public static boolean isDebug = false;
    public static boolean isInfo = false;
    public static boolean isTestingNeow = false;
    public static boolean usesTrophies = false;
    public static boolean isConsoleBuild = false;
    public static boolean usesProfileSaves = false;
    public static boolean isTouchScreen = false;
    public static boolean isDemo = false;
    public static boolean isShowBuild = false;
    public static boolean isPublisherBuild = false;
    public static GameLanguage language;
    public static boolean lineBreakViaCharacter = false;
    public static boolean usesOrdinal = true;
    public static boolean leftAlignCards = false;
    public static boolean manualLineBreak = false;
    public static boolean removeAtoZSort = false;
    public static boolean manualAndAutoLineBreak = false;
    public static Prefs soundPref;
    public static Prefs dailyPref;
    public static Prefs gamePref;
    public static boolean isDailyRun;
    public static boolean hasDoneDailyToday;
    public static long dailyDate = 0L;
    public static long totalPlayTime;
    public static boolean isFinalActAvailable;
    public static boolean hasRubyKey;
    public static boolean hasEmeraldKey;
    public static boolean hasSapphireKey;
    public static boolean isEndless;
    public static boolean isTrial;
    public static Long specialSeed;
    public static String trialName;
    public static boolean IS_FULLSCREEN;
    public static boolean IS_W_FULLSCREEN;
    public static boolean IS_V_SYNC;
    public static int MAX_FPS;
    public static int M_W;
    public static int M_H;
    public static int SAVED_WIDTH;
    public static int SAVED_HEIGHT;
    public static int WIDTH;
    public static int HEIGHT;
    public static boolean isSixteenByTen = false;
    public static boolean isFourByThree = false;
    public static boolean isTwoSixteen = false;
    public static boolean isLetterbox = false;
    public static int HORIZ_LETTERBOX_AMT = 0;
    public static int VERT_LETTERBOX_AMT = 0;
    public static ArrayList displayOptions = null;
    public static int displayIndex = 0;
    public static float scale;
    public static float renderScale;
    public static float xScale;
    public static float yScale;
    public static float FOUR_BY_THREE_OFFSET_Y;
    public static float LETTERBOX_OFFSET_Y;
    public static Long seed;
    public static boolean seedSet = false;
    public static long seedSourceTimestamp;
    public static boolean isBackgrounded = false;
    public static float bgVolume = 0.0F;
    public static final String MASTER_VOLUME_PREF = "Master Volume";
    public static final String MUSIC_VOLUME_PREF = "Music Volume";
    public static final String SOUND_VOLUME_PREF = "Sound Volume";
    public static final String AMBIENCE_ON_PREF = "Ambience On";
    public static final String MUTE_IF_BG_PREF = "Mute in Bg";
    public static final float DEFAULT_MASTER_VOLUME = 0.5F;
    public static final float DEFAULT_MUSIC_VOLUME = 0.5F;
    public static final float DEFAULT_SOUND_VOLUME = 0.5F;
    public static float MASTER_VOLUME;
    public static float MUSIC_VOLUME;
    public static float SOUND_VOLUME;
    public static boolean AMBIANCE_ON;
    public static final String SCREEN_SHAKE_PREF = "Screen Shake";
    public static final String SUM_DMG_PREF = "Summed Damage";
    public static final String BLOCKED_DMG_PREF = "Blocked Damage";
    public static final String HAND_CONF_PREF = "Hand Confirmation";
    public static final String EFFECTS_PREF = "Particle Effects";
    public static final String FAST_MODE_PREF = "Fast Mode";
    public static final String UPLOAD_PREF = "Upload Data";
    public static final String PLAYTESTER_ART = "Playtester Art";
    public static final String SHOW_CARD_HOTKEYS_PREF = "Show Card keys";
    public static final String BIG_TEXT_PREF = "Bigger Text";
    public static final String LONG_PRESS_PREF = "Long-press Enabled";
    public static final String CONTROLLER_ENABLED_PREF = "Controller Enabled";
    public static final String TOUCHSCREEN_ENABLED_PREF = "Touchscreen Enabled";
    public static final String LAST_DAILY = "LAST_DAILY";
    public static boolean SHOW_DMG_SUM;
    public static boolean SHOW_DMG_BLOCK;
    public static boolean FAST_HAND_CONF;
    public static boolean FAST_MODE;
    public static boolean CONTROLLER_ENABLED;
    public static boolean TOUCHSCREEN_ENABLED;
    public static boolean DISABLE_EFFECTS;
    public static boolean UPLOAD_DATA;
    public static boolean SCREEN_SHAKE;
    public static boolean PLAYTESTER_ART_MODE;
    public static boolean SHOW_CARD_HOTKEYS;
    public static boolean USE_LONG_PRESS = false;
    public static boolean BIG_TEXT_MODE = false;
    public static final Color CREAM_COLOR = new Color(0xfff6e2ff);
    public static final Color LIGHT_YELLOW_COLOR = new Color(0xffeda7ff);
    public static final Color RED_TEXT_COLOR = new Color(0xff6563ff);
    public static final Color GREEN_TEXT_COLOR = new Color(0x7fff00ff);
    public static final Color BLUE_TEXT_COLOR = new Color(0x87ceebff);
    public static final Color GOLD_COLOR = new Color(0xefc851ff);
    public static final Color PURPLE_COLOR = new Color(0xee82eeff);
    public static final Color TOP_PANEL_SHADOW_COLOR = new Color(64);
    public static final Color HALF_TRANSPARENT_WHITE_COLOR = new Color(1.0F, 1.0F, 1.0F, 0.5F);
    public static final Color QUARTER_TRANSPARENT_WHITE_COLOR = new Color(1.0F, 1.0F, 1.0F, 0.25F);
    public static final Color TWO_THIRDS_TRANSPARENT_BLACK_COLOR = new Color(0.0F, 0.0F, 0.0F, 0.66F);
    public static final Color HALF_TRANSPARENT_BLACK_COLOR = new Color(0.0F, 0.0F, 0.0F, 0.5F);
    public static final Color QUARTER_TRANSPARENT_BLACK_COLOR = new Color(0.0F, 0.0F, 0.0F, 0.25F);
    public static final Color RED_RELIC_COLOR = new Color(0xff6563bf);
    public static final Color GREEN_RELIC_COLOR = new Color(0x7fff00bf);
    public static final Color BLUE_RELIC_COLOR = new Color(0x87ceebbf);
    public static final Color PURPLE_RELIC_COLOR = new Color(0xc83cffbf);
    public static final float POST_ATTACK_WAIT_DUR = 0.1F;
    public static final float WAIT_BEFORE_BATTLE_TIME = 1F;
    public static float ACTION_DUR_XFAST = 0.1F;
    public static float ACTION_DUR_FASTER = 0.2F;
    public static float ACTION_DUR_FAST = 0.25F;
    public static float ACTION_DUR_MED = 0.5F;
    public static float ACTION_DUR_LONG = 1.0F;
    public static float ACTION_DUR_XLONG = 1.5F;
    public static float CARD_DROP_END_Y;
    public static float SCROLL_SPEED;
    public static float MAP_SCROLL_SPEED;
    public static final float SCROLL_LERP_SPEED = 12F;
    public static final float SCROLL_SNAP_BACK_SPEED = 10F;
    public static float DEFAULT_SCROLL_LIMIT;
    public static float MAP_DST_Y;
    public static final float CLICK_SPEED_THRESHOLD = 0.4F;
    public static float CLICK_DIST_THRESHOLD;
    public static float POTION_W;
    public static float POTION_Y;
    public static final Color DISCARD_COLOR = Color.valueOf("8a769bff");
    public static final Color DISCARD_GLOW_COLOR = Color.valueOf("553a66ff");
    public static final Color SHADOW_COLOR = new Color(0.0F, 0.0F, 0.0F, 0.5F);
    public static final float CARD_SOUL_SCALE = 0.12F;
    public static final float CARD_LERP_SPEED = 6F;
    public static float CARD_SNAP_THRESHOLD;
    public static float UI_SNAP_THRESHOLD;
    public static final float CARD_SCALE_LERP_SPEED = 7.5F;
    public static final float CARD_SCALE_SNAP_THRESHOLD = 0.003F;
    public static final float UI_LERP_SPEED = 9F;
    public static final float ORB_LERP_SPEED = 6F;
    public static final float MOUSE_LERP_SPEED = 20F;
    public static final float POP_LERP_SPEED = 8F;
    public static final float FADE_LERP_SPEED = 12F;
    public static final float SLOW_COLOR_LERP_SPEED = 3F;
    public static final float FADE_SNAP_THRESHOLD = 0.01F;
    public static final float ROTATE_LERP_SPEED = 12F;
    public static final float SCALE_SNAP_THRESHOLD = 0.003F;
    public static float HOVER_BUTTON_RISE_AMOUNT;
    public static final float CARD_VIEW_SCALE = 0.75F;
    public static float CARD_VIEW_PAD_X;
    public static float CARD_VIEW_PAD_Y;
    public static float OPTION_Y;
    public static float EVENT_Y;
    public static final int MAX_ASCENSION_LEVEL = 20;
    public static final float POST_COMBAT_WAIT_TIME = 0.25F;
    public static final int MAX_HAND_SIZE = 10;
    public static final int NUM_POTIONS = 3;
    public static final int NORMAL_POTION_DROP_RATE = 40;
    public static final int ELITE_POTION_DROP_RATE = 40;
    public static final int BOSS_GOLD_AMT = 100;
    public static final int BOSS_GOLD_JITTER = 5;
    public static final int ACHIEVEMENT_COUNT = 46;
    public static final int NORMAL_RARE_DROP_RATE = 3;
    public static final int NORMAL_UNCOMMON_DROP_RATE = 40;
    public static final int ELITE_RARE_DROP_RATE = 10;
    public static final int ELITE_UNCOMMON_DROP_RATE = 50;
    public static final int UNLOCK_PER_CHAR_COUNT = 5;
    public static boolean hideTopBar = false;
    public static boolean hidePopupDetails = false;
    public static boolean hideRelics = false;
    public static boolean hideLowerElements = false;
    public static boolean hideCards = false;
    public static boolean hideEndTurn = false;
    public static boolean hideCombatElements = false;
    public static final String SENDTODEVS = "sendToDevs";

}
