// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MenuButton.java

package com.megacrit.cardcrawl.screens.mainMenu;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.CharacterManager;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.integrations.SteelSeries;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.scenes.TitleBackground;
import com.megacrit.cardcrawl.screens.options.ConfirmPopup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.screens.mainMenu:
//            MainMenuScreen, MenuPanelScreen, PatchNotesScreen

public class MenuButton
{
    public static final class ClickResult extends Enum
    {

        public static ClickResult[] values()
        {
            return (ClickResult[])$VALUES.clone();
        }

        public static ClickResult valueOf(String name)
        {
            return (ClickResult)Enum.valueOf(com/megacrit/cardcrawl/screens/mainMenu/MenuButton$ClickResult, name);
        }

        public static final ClickResult PLAY;
        public static final ClickResult RESUME_GAME;
        public static final ClickResult ABANDON_RUN;
        public static final ClickResult INFO;
        public static final ClickResult STAT;
        public static final ClickResult SETTINGS;
        public static final ClickResult PATCH_NOTES;
        public static final ClickResult QUIT;
        private static final ClickResult $VALUES[];

        static 
        {
            PLAY = new ClickResult("PLAY", 0);
            RESUME_GAME = new ClickResult("RESUME_GAME", 1);
            ABANDON_RUN = new ClickResult("ABANDON_RUN", 2);
            INFO = new ClickResult("INFO", 3);
            STAT = new ClickResult("STAT", 4);
            SETTINGS = new ClickResult("SETTINGS", 5);
            PATCH_NOTES = new ClickResult("PATCH_NOTES", 6);
            QUIT = new ClickResult("QUIT", 7);
            $VALUES = (new ClickResult[] {
                PLAY, RESUME_GAME, ABANDON_RUN, INFO, STAT, SETTINGS, PATCH_NOTES, QUIT
            });
        }

        private ClickResult(String s, int i)
        {
            super(s, i);
        }
    }


    public MenuButton(ClickResult r, int index)
    {
        tint = Color.WHITE.cpy();
        highlightColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        hidden = false;
        x = 0.0F;
        targetX = 0.0F;
        confirmation = false;
        if(highlightImg == null)
            highlightImg = ImageMaster.loadImage("images/ui/mainMenu/menu_option_highlight.png");
        result = r;
        this.index = index;
        setLabel();
        if(Settings.isTouchScreen || Settings.isMobile)
        {
            hb = new Hitbox(FontHelper.getSmartWidth(FontHelper.losePowerFont, label, 9999F, 1.0F) * 1.25F + 100F * Settings.scale, SPACE_Y * 2.0F);
            hb.move(hb.width / 2.0F + 75F * Settings.scale, START_Y + (float)index * (SPACE_Y * 2.0F));
        } else
        {
            hb = new Hitbox(FontHelper.getSmartWidth(FontHelper.buttonLabelFont, label, 9999F, 1.0F) + 100F * Settings.scale, SPACE_Y);
            hb.move(hb.width / 2.0F + 75F * Settings.scale, START_Y + (float)index * SPACE_Y);
        }
    }

    private void setLabel()
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MenuButton$ClickResult[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MenuButton$ClickResult = new int[ClickResult.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MenuButton$ClickResult[ClickResult.PLAY.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MenuButton$ClickResult[ClickResult.RESUME_GAME.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MenuButton$ClickResult[ClickResult.ABANDON_RUN.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MenuButton$ClickResult[ClickResult.INFO.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MenuButton$ClickResult[ClickResult.STAT.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MenuButton$ClickResult[ClickResult.SETTINGS.ordinal()] = 6;
                }
                catch(NoSuchFieldError nosuchfielderror5) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MenuButton$ClickResult[ClickResult.QUIT.ordinal()] = 7;
                }
                catch(NoSuchFieldError nosuchfielderror6) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$MenuButton$ClickResult[ClickResult.PATCH_NOTES.ordinal()] = 8;
                }
                catch(NoSuchFieldError nosuchfielderror7) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.screens.mainMenu.MenuButton.ClickResult[result.ordinal()])
        {
        case 1: // '\001'
            label = TEXT[1];
            break;

        case 2: // '\002'
            label = TEXT[4];
            break;

        case 3: // '\003'
            label = TEXT[10];
            break;

        case 4: // '\004'
            label = TEXT[14];
            break;

        case 5: // '\005'
            label = TEXT[6];
            break;

        case 6: // '\006'
            label = TEXT[12];
            break;

        case 7: // '\007'
            label = TEXT[8];
            break;

        case 8: // '\b'
            label = TEXT[9];
            break;

        default:
            label = "ERROR";
            break;
        }
    }

    public void update()
    {
        if(CardCrawlGame.mainMenuScreen.screen == MainMenuScreen.CurScreen.MAIN_MENU && CardCrawlGame.mainMenuScreen.bg.slider < 0.5F)
            hb.update();
        x = MathHelper.uiLerpSnap(x, targetX);
        if(hb.justHovered && !hidden)
            CardCrawlGame.sound.playV("UI_HOVER", 0.75F);
        if(hb.hovered)
        {
            highlightColor.a = 0.9F;
            targetX = 25F * Settings.scale;
            if(InputHelper.justClickedLeft)
            {
                CardCrawlGame.sound.playA("UI_CLICK_1", -0.1F);
                hb.clickStarted = true;
            }
            tint = Color.WHITE.cpy();
        } else
        if(CardCrawlGame.mainMenuScreen.screen == MainMenuScreen.CurScreen.MAIN_MENU)
        {
            highlightColor.a = MathHelper.fadeLerpSnap(highlightColor.a, 0.0F);
            targetX = 0.0F;
            hidden = false;
            tint.r = MathHelper.fadeLerpSnap(tint.r, 0.3F);
            tint.g = tint.r;
            tint.b = tint.r;
        }
        if(hb.hovered && CInputActionSet.select.isJustPressed())
        {
            CInputActionSet.select.unpress();
            hb.clicked = true;
            CardCrawlGame.sound.playA("UI_CLICK_1", -0.1F);
        }
        if(hb.clicked)
        {
            hb.clicked = false;
            buttonEffect();
            CardCrawlGame.mainMenuScreen.hideMenuButtons();
        }
    }

    public void hide()
    {
        hb.hovered = false;
        targetX = -1000F * Settings.scale + 30F * Settings.scale * (float)index;
        hidden = true;
    }

    public void buttonEffect()
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.screens.mainMenu.MenuButton.ClickResult[result.ordinal()])
        {
        case 1: // '\001'
            CardCrawlGame.mainMenuScreen.panelScreen.open(MenuPanelScreen.PanelScreen.PLAY);
            break;

        case 2: // '\002'
            CardCrawlGame.mainMenuScreen.screen = MainMenuScreen.CurScreen.NONE;
            CardCrawlGame.mainMenuScreen.hideMenuButtons();
            CardCrawlGame.mainMenuScreen.darken();
            resumeGame();
            break;

        case 3: // '\003'
            CardCrawlGame.mainMenuScreen.screen = MainMenuScreen.CurScreen.ABANDON_CONFIRM;
            CardCrawlGame.mainMenuScreen.abandonPopup.show();
            break;

        case 4: // '\004'
            CardCrawlGame.mainMenuScreen.panelScreen.open(MenuPanelScreen.PanelScreen.COMPENDIUM);
            break;

        case 5: // '\005'
            CardCrawlGame.mainMenuScreen.panelScreen.open(MenuPanelScreen.PanelScreen.STATS);
            break;

        case 6: // '\006'
            CardCrawlGame.mainMenuScreen.panelScreen.open(MenuPanelScreen.PanelScreen.SETTINGS);
            break;

        case 8: // '\b'
            CardCrawlGame.mainMenuScreen.patchNotesScreen.open();
            break;

        case 7: // '\007'
            logger.info("Quit Game button clicked!");
            Gdx.app.exit();
            break;
        }
    }

    private void resumeGame()
    {
        CardCrawlGame.loadingSave = true;
        CardCrawlGame.chosenCharacter = CardCrawlGame.characterManager.loadChosenCharacter().chosenClass;
        CardCrawlGame.mainMenuScreen.isFadingOut = true;
        CardCrawlGame.mainMenuScreen.fadeOutMusic();
        Settings.isDailyRun = false;
        Settings.isTrial = false;
        ModHelper.setModsFalse();
        if(CardCrawlGame.steelSeries.isEnabled.booleanValue())
            CardCrawlGame.steelSeries.event_character_chosen(CardCrawlGame.chosenCharacter);
    }

    public void render(SpriteBatch sb)
    {
        float lerper = Interpolation.circleIn.apply(CardCrawlGame.mainMenuScreen.bg.slider);
        float sliderX = -1000F * Settings.scale * lerper;
        sliderX -= (float)index * 250F * Settings.scale * lerper;
        if(result == ClickResult.ABANDON_RUN)
            if(confirmation)
                label = TEXT[11];
            else
                label = TEXT[10];
        sb.setBlendFunction(770, 1);
        sb.setColor(highlightColor);
        if(Settings.isTouchScreen || Settings.isMobile)
            sb.draw(highlightImg, ((x + FONT_X + sliderX) - 179F) + 120F * Settings.scale, hb.cY - 56F, 179F, 52F, 358F, 104F, Settings.scale, Settings.scale * 1.2F, 0.0F, 0, 0, 358, 104, false, false);
        else
            sb.draw(highlightImg, ((x + FONT_X + sliderX) - 179F) + 120F * Settings.scale, hb.cY - 52F, 179F, 52F, 358F, 104F, Settings.scale, Settings.scale * 0.8F, 0.0F, 0, 0, 358, 104, false, false);
        sb.setBlendFunction(770, 771);
        if(Settings.isTouchScreen || Settings.isMobile)
            FontHelper.renderSmartText(sb, FontHelper.losePowerFont, label, x + FONT_X + sliderX, hb.cY + FONT_OFFSET_Y, 9999F, 1.0F, Settings.CREAM_COLOR, 1.25F);
        else
            FontHelper.renderSmartText(sb, FontHelper.buttonLabelFont, label, x + FONT_X + sliderX, hb.cY + FONT_OFFSET_Y, 9999F, 1.0F, Settings.CREAM_COLOR);
        hb.render(sb);
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/screens/mainMenu/MenuButton.getName());
    private static final UIStrings uiStrings;
    public static final String TEXT[];
    public ClickResult result;
    private String label;
    public Hitbox hb;
    private Color tint;
    private Color highlightColor;
    private int index;
    private boolean hidden;
    private float x;
    private float targetX;
    public static final float FONT_X;
    public static final float START_Y;
    public static final float SPACE_Y;
    public static final float FONT_OFFSET_Y;
    private boolean confirmation;
    private static Texture highlightImg = null;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("MenuButton");
        TEXT = uiStrings.TEXT;
        FONT_X = 120F * Settings.scale;
        START_Y = 120F * Settings.scale;
        SPACE_Y = 50F * Settings.scale;
        FONT_OFFSET_Y = 10F * Settings.scale;
    }
}
