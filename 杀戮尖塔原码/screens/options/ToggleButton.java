// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ToggleButton.java

package com.megacrit.cardcrawl.screens.options;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.scenes.AbstractScene;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import com.megacrit.cardcrawl.vfx.RestartForChangesEffect;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.screens.options:
//            OptionsPanel, SettingsScreen, DropdownMenu

public class ToggleButton
{
    public static final class ToggleBtnType extends Enum
    {

        public static ToggleBtnType[] values()
        {
            return (ToggleBtnType[])$VALUES.clone();
        }

        public static ToggleBtnType valueOf(String name)
        {
            return (ToggleBtnType)Enum.valueOf(com/megacrit/cardcrawl/screens/options/ToggleButton$ToggleBtnType, name);
        }

        public static final ToggleBtnType FULL_SCREEN;
        public static final ToggleBtnType W_FULL_SCREEN;
        public static final ToggleBtnType SCREEN_SHAKE;
        public static final ToggleBtnType AMBIENCE_ON;
        public static final ToggleBtnType MUTE_IF_BG;
        public static final ToggleBtnType SUM_DMG;
        public static final ToggleBtnType BIG_TEXT;
        public static final ToggleBtnType BLOCK_DMG;
        public static final ToggleBtnType HAND_CONF;
        public static final ToggleBtnType FAST_MODE;
        public static final ToggleBtnType UPLOAD_DATA;
        public static final ToggleBtnType LONG_PRESS;
        public static final ToggleBtnType V_SYNC;
        public static final ToggleBtnType PLAYTESTER_ART;
        public static final ToggleBtnType SHOW_CARD_HOTKEYS;
        public static final ToggleBtnType EFFECTS;
        private static final ToggleBtnType $VALUES[];

        static 
        {
            FULL_SCREEN = new ToggleBtnType("FULL_SCREEN", 0);
            W_FULL_SCREEN = new ToggleBtnType("W_FULL_SCREEN", 1);
            SCREEN_SHAKE = new ToggleBtnType("SCREEN_SHAKE", 2);
            AMBIENCE_ON = new ToggleBtnType("AMBIENCE_ON", 3);
            MUTE_IF_BG = new ToggleBtnType("MUTE_IF_BG", 4);
            SUM_DMG = new ToggleBtnType("SUM_DMG", 5);
            BIG_TEXT = new ToggleBtnType("BIG_TEXT", 6);
            BLOCK_DMG = new ToggleBtnType("BLOCK_DMG", 7);
            HAND_CONF = new ToggleBtnType("HAND_CONF", 8);
            FAST_MODE = new ToggleBtnType("FAST_MODE", 9);
            UPLOAD_DATA = new ToggleBtnType("UPLOAD_DATA", 10);
            LONG_PRESS = new ToggleBtnType("LONG_PRESS", 11);
            V_SYNC = new ToggleBtnType("V_SYNC", 12);
            PLAYTESTER_ART = new ToggleBtnType("PLAYTESTER_ART", 13);
            SHOW_CARD_HOTKEYS = new ToggleBtnType("SHOW_CARD_HOTKEYS", 14);
            EFFECTS = new ToggleBtnType("EFFECTS", 15);
            $VALUES = (new ToggleBtnType[] {
                FULL_SCREEN, W_FULL_SCREEN, SCREEN_SHAKE, AMBIENCE_ON, MUTE_IF_BG, SUM_DMG, BIG_TEXT, BLOCK_DMG, HAND_CONF, FAST_MODE, 
                UPLOAD_DATA, LONG_PRESS, V_SYNC, PLAYTESTER_ART, SHOW_CARD_HOTKEYS, EFFECTS
            });
        }

        private ToggleBtnType(String s, int i)
        {
            super(s, i);
        }
    }


    public ToggleButton(float x, float y, float middleY, ToggleBtnType type)
    {
        enabled = true;
        this.x = x;
        this.y = middleY + y * Settings.scale;
        this.type = type;
        hb = new Hitbox(200F * Settings.scale, 32F * Settings.scale);
        hb.move(x + 74F * Settings.scale, this.y);
        enabled = getPref();
    }

    public ToggleButton(float x, float y, float middleY, ToggleBtnType type, boolean isLarge)
    {
        enabled = true;
        this.x = x;
        this.y = middleY + y * Settings.scale;
        this.type = type;
        if(isLarge)
        {
            hb = new Hitbox(480F * Settings.scale, 32F * Settings.scale);
            hb.move(x + 214F * Settings.scale, this.y);
        } else
        {
            hb = new Hitbox(240F * Settings.scale, 32F * Settings.scale);
            hb.move(x + 74F * Settings.scale, this.y);
        }
        enabled = getPref();
    }

    private boolean getPref()
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$screens$options$ToggleButton$ToggleBtnType[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$screens$options$ToggleButton$ToggleBtnType = new int[ToggleBtnType.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$options$ToggleButton$ToggleBtnType[ToggleBtnType.AMBIENCE_ON.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$options$ToggleButton$ToggleBtnType[ToggleBtnType.MUTE_IF_BG.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$options$ToggleButton$ToggleBtnType[ToggleBtnType.BIG_TEXT.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$options$ToggleButton$ToggleBtnType[ToggleBtnType.BLOCK_DMG.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$options$ToggleButton$ToggleBtnType[ToggleBtnType.EFFECTS.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$options$ToggleButton$ToggleBtnType[ToggleBtnType.FAST_MODE.ordinal()] = 6;
                }
                catch(NoSuchFieldError nosuchfielderror5) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$options$ToggleButton$ToggleBtnType[ToggleBtnType.SHOW_CARD_HOTKEYS.ordinal()] = 7;
                }
                catch(NoSuchFieldError nosuchfielderror6) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$options$ToggleButton$ToggleBtnType[ToggleBtnType.FULL_SCREEN.ordinal()] = 8;
                }
                catch(NoSuchFieldError nosuchfielderror7) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$options$ToggleButton$ToggleBtnType[ToggleBtnType.W_FULL_SCREEN.ordinal()] = 9;
                }
                catch(NoSuchFieldError nosuchfielderror8) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$options$ToggleButton$ToggleBtnType[ToggleBtnType.V_SYNC.ordinal()] = 10;
                }
                catch(NoSuchFieldError nosuchfielderror9) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$options$ToggleButton$ToggleBtnType[ToggleBtnType.HAND_CONF.ordinal()] = 11;
                }
                catch(NoSuchFieldError nosuchfielderror10) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$options$ToggleButton$ToggleBtnType[ToggleBtnType.SCREEN_SHAKE.ordinal()] = 12;
                }
                catch(NoSuchFieldError nosuchfielderror11) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$options$ToggleButton$ToggleBtnType[ToggleBtnType.SUM_DMG.ordinal()] = 13;
                }
                catch(NoSuchFieldError nosuchfielderror12) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$options$ToggleButton$ToggleBtnType[ToggleBtnType.UPLOAD_DATA.ordinal()] = 14;
                }
                catch(NoSuchFieldError nosuchfielderror13) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$options$ToggleButton$ToggleBtnType[ToggleBtnType.LONG_PRESS.ordinal()] = 15;
                }
                catch(NoSuchFieldError nosuchfielderror14) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$options$ToggleButton$ToggleBtnType[ToggleBtnType.PLAYTESTER_ART.ordinal()] = 16;
                }
                catch(NoSuchFieldError nosuchfielderror15) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.screens.options.ToggleButton.ToggleBtnType[type.ordinal()])
        {
        case 1: // '\001'
            return Settings.soundPref.getBoolean("Ambience On", true);

        case 2: // '\002'
            return Settings.soundPref.getBoolean("Mute in Bg", true);

        case 3: // '\003'
            return Settings.gamePref.getBoolean("Bigger Text", false);

        case 4: // '\004'
            return Settings.gamePref.getBoolean("Blocked Damage", false);

        case 5: // '\005'
            return Settings.gamePref.getBoolean("Particle Effects", false);

        case 6: // '\006'
            return Settings.gamePref.getBoolean("Fast Mode", false);

        case 7: // '\007'
            return Settings.gamePref.getBoolean("Show Card keys", false);

        case 8: // '\b'
            return Settings.IS_FULLSCREEN;

        case 9: // '\t'
            return Settings.IS_W_FULLSCREEN;

        case 10: // '\n'
            return Settings.IS_V_SYNC;

        case 11: // '\013'
            return Settings.gamePref.getBoolean("Hand Confirmation", false);

        case 12: // '\f'
            return Settings.gamePref.getBoolean("Screen Shake", true);

        case 13: // '\r'
            return Settings.gamePref.getBoolean("Summed Damage", false);

        case 14: // '\016'
            return Settings.gamePref.getBoolean("Upload Data", true);

        case 15: // '\017'
            return Settings.gamePref.getBoolean("Long-press Enabled", Settings.isConsoleBuild);

        case 16: // '\020'
            return Settings.gamePref.getBoolean("Playtester Art", false);
        }
        return true;
    }

    public void update()
    {
        hb.update();
        if(hb.hovered && (InputHelper.justClickedLeft || CInputActionSet.select.isJustPressed()))
        {
            InputHelper.justClickedLeft = false;
            toggle();
        }
    }

    public void toggle()
    {
        enabled = !enabled;
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.screens.options.ToggleButton.ToggleBtnType[type.ordinal()])
        {
        default:
            break;

        case 1: // '\001'
            Settings.soundPref.putBoolean("Ambience On", enabled);
            Settings.soundPref.flush();
            Settings.AMBIANCE_ON = enabled;
            if(CardCrawlGame.mode == com.megacrit.cardcrawl.core.CardCrawlGame.GameMode.CHAR_SELECT)
                CardCrawlGame.mainMenuScreen.updateAmbienceVolume();
            else
                AbstractDungeon.scene.updateAmbienceVolume();
            logger.info((new StringBuilder()).append("Ambience On: ").append(enabled).toString());
            break;

        case 2: // '\002'
            Settings.soundPref.putBoolean("Mute in Bg", enabled);
            Settings.soundPref.flush();
            CardCrawlGame.MUTE_IF_BG = enabled;
            logger.info((new StringBuilder()).append("Mute while in Background: ").append(enabled).toString());
            break;

        case 3: // '\003'
            Settings.gamePref.putBoolean("Bigger Text", enabled);
            Settings.gamePref.flush();
            Settings.BIG_TEXT_MODE = enabled;
            CardCrawlGame.mainMenuScreen.optionPanel.displayRestartRequiredText();
            logger.info((new StringBuilder()).append("Bigger Text: ").append(enabled).toString());
            break;

        case 4: // '\004'
            Settings.gamePref.putBoolean("Blocked Damage", enabled);
            Settings.gamePref.flush();
            Settings.SHOW_DMG_BLOCK = enabled;
            logger.info((new StringBuilder()).append("Show Blocked Damage: ").append(enabled).toString());
            break;

        case 5: // '\005'
            Settings.gamePref.putBoolean("Particle Effects", enabled);
            Settings.gamePref.flush();
            Settings.DISABLE_EFFECTS = enabled;
            logger.info((new StringBuilder()).append("Particle FX Disabled: ").append(enabled).toString());
            break;

        case 6: // '\006'
            Settings.gamePref.putBoolean("Fast Mode", enabled);
            Settings.gamePref.flush();
            Settings.FAST_MODE = enabled;
            logger.info((new StringBuilder()).append("Fast Mode: ").append(enabled).toString());
            break;

        case 7: // '\007'
            Settings.gamePref.putBoolean("Show Card keys", enabled);
            Settings.gamePref.flush();
            Settings.SHOW_CARD_HOTKEYS = enabled;
            logger.info((new StringBuilder()).append("Show Card Hotkeys: ").append(enabled).toString());
            break;

        case 8: // '\b'
            Settings.IS_FULLSCREEN = !Settings.IS_FULLSCREEN;
            if(Settings.IS_FULLSCREEN)
            {
                if(CardCrawlGame.mode == com.megacrit.cardcrawl.core.CardCrawlGame.GameMode.CHAR_SELECT)
                    CardCrawlGame.mainMenuScreen.optionPanel.setFullscreen(false);
                else
                    AbstractDungeon.settingsScreen.panel.setFullscreen(false);
                if(CardCrawlGame.mode == com.megacrit.cardcrawl.core.CardCrawlGame.GameMode.CHAR_SELECT)
                {
                    if(CardCrawlGame.mainMenuScreen.optionPanel.wfsToggle.enabled)
                        CardCrawlGame.mainMenuScreen.optionPanel.wfsToggle.enabled = false;
                } else
                if(AbstractDungeon.settingsScreen.panel.wfsToggle.enabled)
                    AbstractDungeon.settingsScreen.panel.wfsToggle.enabled = false;
                updateResolutionDropdown(0);
            } else
            {
                updateResolutionDropdown(2);
            }
            Settings.IS_W_FULLSCREEN = false;
            DisplayConfig.writeDisplayConfigFile(Gdx.graphics.getDisplayMode().width, Gdx.graphics.getDisplayMode().height, Settings.MAX_FPS, Settings.IS_FULLSCREEN, Settings.IS_W_FULLSCREEN, Settings.IS_V_SYNC);
            logger.info((new StringBuilder()).append("Fullscreen: ").append(Settings.IS_FULLSCREEN).toString());
            break;

        case 9: // '\t'
            Settings.IS_W_FULLSCREEN = !Settings.IS_W_FULLSCREEN;
            if(Settings.IS_W_FULLSCREEN)
            {
                if(CardCrawlGame.mode == com.megacrit.cardcrawl.core.CardCrawlGame.GameMode.CHAR_SELECT)
                    CardCrawlGame.mainMenuScreen.optionPanel.setFullscreen(true);
                else
                    AbstractDungeon.settingsScreen.panel.setFullscreen(true);
                if(CardCrawlGame.mode == com.megacrit.cardcrawl.core.CardCrawlGame.GameMode.CHAR_SELECT)
                {
                    if(CardCrawlGame.mainMenuScreen.optionPanel.fsToggle.enabled)
                        CardCrawlGame.mainMenuScreen.optionPanel.fsToggle.enabled = false;
                } else
                if(AbstractDungeon.settingsScreen.panel.fsToggle.enabled)
                    AbstractDungeon.settingsScreen.panel.fsToggle.enabled = false;
                updateResolutionDropdown(1);
            } else
            {
                updateResolutionDropdown(2);
            }
            Settings.IS_FULLSCREEN = false;
            DisplayConfig.writeDisplayConfigFile(Gdx.graphics.getDisplayMode().width, Gdx.graphics.getDisplayMode().height, Settings.MAX_FPS, Settings.IS_FULLSCREEN, Settings.IS_W_FULLSCREEN, Settings.IS_V_SYNC);
            logger.info((new StringBuilder()).append("Borderless Fullscreen: ").append(Settings.IS_W_FULLSCREEN).toString());
            break;

        case 10: // '\n'
            Settings.IS_V_SYNC = !Settings.IS_V_SYNC;
            if(CardCrawlGame.mode == com.megacrit.cardcrawl.core.CardCrawlGame.GameMode.CHAR_SELECT)
            {
                CardCrawlGame.mainMenuScreen.optionPanel.vSyncToggle.enabled = Settings.IS_V_SYNC;
                CardCrawlGame.mainMenuScreen.optionPanel.effects.clear();
                CardCrawlGame.mainMenuScreen.optionPanel.effects.add(new RestartForChangesEffect());
            } else
            {
                AbstractDungeon.settingsScreen.panel.vSyncToggle.enabled = Settings.IS_V_SYNC;
                AbstractDungeon.settingsScreen.panel.effects.clear();
                AbstractDungeon.settingsScreen.panel.effects.add(new RestartForChangesEffect());
            }
            DisplayConfig.writeDisplayConfigFile(Settings.SAVED_WIDTH, Settings.SAVED_HEIGHT, Settings.MAX_FPS, Settings.IS_FULLSCREEN, Settings.IS_W_FULLSCREEN, Settings.IS_V_SYNC);
            logger.info((new StringBuilder()).append("V Sync: ").append(Settings.IS_V_SYNC).toString());
            break;

        case 11: // '\013'
            Settings.gamePref.putBoolean("Hand Confirmation", enabled);
            Settings.gamePref.flush();
            Settings.FAST_HAND_CONF = enabled;
            logger.info((new StringBuilder()).append("Hand Confirmation: ").append(enabled).toString());
            break;

        case 12: // '\f'
            Settings.gamePref.putBoolean("Screen Shake", enabled);
            Settings.gamePref.flush();
            Settings.SCREEN_SHAKE = enabled;
            logger.info((new StringBuilder()).append("Screen Shake: ").append(enabled).toString());
            break;

        case 13: // '\r'
            Settings.gamePref.putBoolean("Summed Damage", enabled);
            Settings.gamePref.flush();
            Settings.SHOW_DMG_SUM = enabled;
            logger.info((new StringBuilder()).append("Display Summed Damage: ").append(enabled).toString());
            break;

        case 14: // '\016'
            Settings.gamePref.putBoolean("Upload Data", enabled);
            Settings.gamePref.flush();
            Settings.UPLOAD_DATA = enabled;
            logger.info((new StringBuilder()).append("Upload Data: ").append(enabled).toString());
            break;

        case 16: // '\020'
            Settings.gamePref.putBoolean("Playtester Art", enabled);
            Settings.gamePref.flush();
            Settings.PLAYTESTER_ART_MODE = enabled;
            logger.info((new StringBuilder()).append("Playtester Art: ").append(enabled).toString());
            break;

        case 15: // '\017'
            Settings.gamePref.putBoolean("Long-press Enabled", enabled);
            Settings.gamePref.flush();
            Settings.USE_LONG_PRESS = enabled;
            logger.info((new StringBuilder()).append("Long-press: ").append(enabled).toString());
            break;
        }
    }

    private void updateResolutionDropdown(int mode)
    {
        CardCrawlGame.mainMenuScreen.optionPanel.resoDropdown.updateResolutionLabels(mode);
        AbstractDungeon.settingsScreen.panel.resoDropdown.updateResolutionLabels(mode);
    }

    public void render(SpriteBatch sb)
    {
        if(enabled)
            sb.setColor(Color.LIGHT_GRAY);
        else
            sb.setColor(Color.WHITE);
        float scale = Settings.scale;
        if(hb.hovered)
        {
            sb.setColor(Color.SKY);
            scale = Settings.scale * 1.25F;
        }
        sb.draw(ImageMaster.OPTION_TOGGLE, x - 16F, y - 16F, 16F, 16F, 32F, 32F, scale, scale, 0.0F, 0, 0, 32, 32, false, false);
        if(enabled)
        {
            sb.setColor(Color.WHITE);
            sb.draw(ImageMaster.OPTION_TOGGLE_ON, x - 16F, y - 16F, 16F, 16F, 32F, 32F, scale, scale, 0.0F, 0, 0, 32, 32, false, false);
        }
        hb.render(sb);
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/screens/options/ToggleButton.getName());
    private static final int W = 32;
    private float x;
    private float y;
    public Hitbox hb;
    public boolean enabled;
    private ToggleBtnType type;

}
