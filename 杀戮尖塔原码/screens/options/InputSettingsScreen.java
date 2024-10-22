// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   InputSettingsScreen.java

package com.megacrit.cardcrawl.screens.options;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.*;
import com.megacrit.cardcrawl.helpers.input.*;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.mainMenu.*;
import com.megacrit.cardcrawl.screens.stats.StatsScreen;
import com.megacrit.cardcrawl.ui.buttons.CancelButton;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.screens.options:
//            GiantToggleButton, RemapInputElementHeader, RemapInputElement, RemapInputElementListener, 
//            SettingsScreen

public class InputSettingsScreen
    implements RemapInputElementListener, HitboxListener, ScrollBarListener
{
    private static final class HighlightType extends Enum
    {

        public static HighlightType[] values()
        {
            return (HighlightType[])$VALUES.clone();
        }

        public static HighlightType valueOf(String name)
        {
            return (HighlightType)Enum.valueOf(com/megacrit/cardcrawl/screens/options/InputSettingsScreen$HighlightType, name);
        }

        public static final HighlightType INPUT;
        public static final HighlightType RESET;
        public static final HighlightType CONTROLLER_ON_TOGGLE;
        public static final HighlightType TOUCHSCREEN_ON_TOGGLE;
        private static final HighlightType $VALUES[];

        static 
        {
            INPUT = new HighlightType("INPUT", 0);
            RESET = new HighlightType("RESET", 1);
            CONTROLLER_ON_TOGGLE = new HighlightType("CONTROLLER_ON_TOGGLE", 2);
            TOUCHSCREEN_ON_TOGGLE = new HighlightType("TOUCHSCREEN_ON_TOGGLE", 3);
            $VALUES = (new HighlightType[] {
                INPUT, RESET, CONTROLLER_ON_TOGGLE, TOUCHSCREEN_ON_TOGGLE
            });
        }

        private HighlightType(String s, int i)
        {
            super(s, i);
        }
    }


    public InputSettingsScreen()
    {
        button = new MenuCancelButton();
        scrollBar = new ScrollBar(this, SCROLLBAR_X, SCROLLBAR_Y, SCROLLBAR_HEIGHT);
        resetButtonHb = new Hitbox(300F * Settings.scale, 72F * Settings.scale);
        elements = new ArrayList();
        gameSettingsHb = new Hitbox(GAME_SETTINGS_BUTTON_WIDTH, Settings.scale * 72F);
        touchscreenModeButton = null;
        screenX = HIDE_X;
        targetX = HIDE_X;
        targetScrollOffset = 0.0F;
        visibleScrollOffset = 0.0F;
        screenUp = false;
        elementHb = null;
        if(Settings.isConsoleBuild)
        {
            controllerEnabledToggleButton = new GiantToggleButton(GiantToggleButton.ToggleType.CONTROLLER_ENABLED, (float)Settings.WIDTH * 0.6F, RESET_BUTTON_CY, TEXT[48]);
            resetButtonHb.move((float)Settings.WIDTH * 0.35F, RESET_BUTTON_CY);
        } else
        {
            controllerEnabledToggleButton = new GiantToggleButton(GiantToggleButton.ToggleType.CONTROLLER_ENABLED, (float)Settings.WIDTH * 0.42F, RESET_BUTTON_CY, TEXT[48]);
            touchscreenModeButton = new GiantToggleButton(GiantToggleButton.ToggleType.TOUCHSCREEN_ENABLED, (float)Settings.WIDTH * 0.65F, RESET_BUTTON_CY, TEXT[49]);
            resetButtonHb.move((float)Settings.WIDTH * 0.25F, RESET_BUTTON_CY);
        }
    }

    public void open()
    {
        open(true);
    }

    public void open(boolean animated)
    {
        targetX = 0.0F;
        targetScrollOffset = 0.0F;
        visibleScrollOffset = 0.0F;
        if(animated)
            button.show(RETURN_BUTTON_TEXT);
        else
            button.showInstantly(RETURN_BUTTON_TEXT);
        screenUp = true;
        CardCrawlGame.mainMenuScreen.screen = com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen.CurScreen.INPUT_SETTINGS;
        refreshData();
        gameSettingsHb.move((float)Settings.WIDTH / 2.0F - 438F * Settings.scale, Settings.OPTION_Y + 382F * Settings.scale);
        scrollBar.isBackgroundVisible = false;
        scrollBar.setCenter(SCROLLBAR_X, SCROLLBAR_Y);
        if(CardCrawlGame.isInARun())
            AbstractDungeon.screen = com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.INPUT_SETTINGS;
    }

    private void refreshData()
    {
        elementHb = null;
        elements.clear();
        elements.add(new RemapInputElementHeader(TEXT[3], TEXT[4], TEXT[5]));
        if(Settings.isControllerMode)
        {
            elements.add(new RemapInputElement(this, TEXT[28], InputActionSet.confirm, CInputActionSet.select));
            elements.add(new RemapInputElement(this, TEXT[29], InputActionSet.cancel, CInputActionSet.cancel));
            elements.add(new RemapInputElement(this, TEXT[30], null, CInputActionSet.topPanel));
            elements.add(new RemapInputElement(this, TEXT[31], InputActionSet.endTurn, CInputActionSet.proceed));
            elements.add(new RemapInputElement(this, TEXT[32], InputActionSet.masterDeck, CInputActionSet.pageLeftViewDeck));
            elements.add(new RemapInputElement(this, TEXT[33], InputActionSet.exhaustPile, CInputActionSet.pageRightViewExhaust));
            elements.add(new RemapInputElement(this, TEXT[34], InputActionSet.map, CInputActionSet.map));
            elements.add(new RemapInputElement(this, TEXT[35], null, CInputActionSet.settings));
            elements.add(new RemapInputElement(this, TEXT[36], InputActionSet.drawPile, CInputActionSet.drawPile));
            elements.add(new RemapInputElement(this, TEXT[37], InputActionSet.discardPile, CInputActionSet.discardPile));
            elements.add(new RemapInputElement(this, TEXT[38], InputActionSet.up, CInputActionSet.up));
            elements.add(new RemapInputElement(this, TEXT[39], InputActionSet.down, CInputActionSet.down));
            elements.add(new RemapInputElement(this, TEXT[40], InputActionSet.left, CInputActionSet.left));
            elements.add(new RemapInputElement(this, TEXT[41], InputActionSet.right, CInputActionSet.right));
            elements.add(new RemapInputElement(this, TEXT[42], null, CInputActionSet.altUp));
            elements.add(new RemapInputElement(this, TEXT[43], null, CInputActionSet.altDown));
            elements.add(new RemapInputElement(this, TEXT[44], null, CInputActionSet.altLeft));
            elements.add(new RemapInputElement(this, TEXT[45], null, CInputActionSet.altRight));
        } else
        {
            elements.add(new RemapInputElement(this, TEXT[7], InputActionSet.confirm, CInputActionSet.select));
            elements.add(new RemapInputElement(this, TEXT[8], InputActionSet.cancel, CInputActionSet.cancel));
            elements.add(new RemapInputElement(this, TEXT[9], InputActionSet.map, CInputActionSet.map));
            elements.add(new RemapInputElement(this, TEXT[10], InputActionSet.masterDeck, CInputActionSet.pageLeftViewDeck));
            elements.add(new RemapInputElement(this, TEXT[11], InputActionSet.drawPile, CInputActionSet.drawPile));
            elements.add(new RemapInputElement(this, TEXT[12], InputActionSet.discardPile, CInputActionSet.discardPile));
            elements.add(new RemapInputElement(this, TEXT[13], InputActionSet.exhaustPile, CInputActionSet.pageRightViewExhaust));
            elements.add(new RemapInputElement(this, TEXT[14], InputActionSet.endTurn, CInputActionSet.proceed));
            elements.add(new RemapInputElement(this, TEXT[50], InputActionSet.peek, CInputActionSet.drawPile));
            elements.add(new RemapInputElement(this, TEXT[38], InputActionSet.up, CInputActionSet.up));
            elements.add(new RemapInputElement(this, TEXT[39], InputActionSet.down, CInputActionSet.down));
            elements.add(new RemapInputElement(this, TEXT[15], InputActionSet.left, CInputActionSet.left));
            elements.add(new RemapInputElement(this, TEXT[16], InputActionSet.right, CInputActionSet.right));
            elements.add(new RemapInputElement(this, TEXT[17], InputActionSet.selectCard_1));
            elements.add(new RemapInputElement(this, TEXT[18], InputActionSet.selectCard_2));
            elements.add(new RemapInputElement(this, TEXT[19], InputActionSet.selectCard_3));
            elements.add(new RemapInputElement(this, TEXT[20], InputActionSet.selectCard_4));
            elements.add(new RemapInputElement(this, TEXT[21], InputActionSet.selectCard_5));
            elements.add(new RemapInputElement(this, TEXT[22], InputActionSet.selectCard_6));
            elements.add(new RemapInputElement(this, TEXT[23], InputActionSet.selectCard_7));
            elements.add(new RemapInputElement(this, TEXT[24], InputActionSet.selectCard_8));
            elements.add(new RemapInputElement(this, TEXT[25], InputActionSet.selectCard_9));
            elements.add(new RemapInputElement(this, TEXT[26], InputActionSet.selectCard_10));
            elements.add(new RemapInputElement(this, TEXT[27], InputActionSet.releaseCard));
        }
        maxScrollAmount = (ROW_Y_DIFF * (float)elements.size() + 2.0F * ROW_TABLE_VERTICAL_EXTRA_PADDING) - SCROLL_CONTAINER_VISIBLE_HEIGHT;
    }

    public void update()
    {
        updateControllerInput();
        button.update();
        controllerEnabledToggleButton.update();
        if(!Settings.isConsoleBuild)
            touchscreenModeButton.update();
        updateScrolling();
        if(button.hb.clicked || InputHelper.pressedEscape || CInputActionSet.cancel.isJustPressed())
        {
            if(CardCrawlGame.isInARun())
            {
                AbstractDungeon.closeCurrentScreen();
            } else
            {
                InputHelper.pressedEscape = false;
                CardCrawlGame.mainMenuScreen.screen = com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen.CurScreen.MAIN_MENU;
            }
            hide();
        }
        updateKeyPositions();
        RemapInputElement element;
        for(Iterator iterator = elements.iterator(); iterator.hasNext(); element.update())
            element = (RemapInputElement)iterator.next();

        if(Settings.isControllerMode && CInputActionSet.pageLeftViewDeck.isJustPressed())
            clicked(gameSettingsHb);
        resetButtonHb.encapsulatedUpdate(this);
        gameSettingsHb.encapsulatedUpdate(this);
        screenX = MathHelper.uiLerpSnap(screenX, targetX);
        if(elementHb != null && Settings.isControllerMode)
            CInputHelper.setCursor(elementHb);
        scrollBar.update();
    }

    private void updateControllerInput()
    {
        if(!Settings.isControllerMode)
            return;
        HighlightType type = HighlightType.INPUT;
        boolean anyHovered = false;
        int index = 0;
        Iterator iterator = elements.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            RemapInputElement e = (RemapInputElement)iterator.next();
            e.hb.update();
            if(e.hb.hovered)
            {
                anyHovered = true;
                break;
            }
            index++;
        } while(true);
        if(resetButtonHb.hovered)
        {
            type = HighlightType.RESET;
            anyHovered = true;
        } else
        if(controllerEnabledToggleButton.hb.hovered)
        {
            type = HighlightType.CONTROLLER_ON_TOGGLE;
            anyHovered = true;
        } else
        if(touchscreenModeButton.hb.hovered)
        {
            type = HighlightType.TOUCHSCREEN_ON_TOGGLE;
            anyHovered = true;
        }
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$screens$options$InputSettingsScreen$HighlightType[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$screens$options$InputSettingsScreen$HighlightType = new int[HighlightType.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$options$InputSettingsScreen$HighlightType[HighlightType.INPUT.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$options$InputSettingsScreen$HighlightType[HighlightType.RESET.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$options$InputSettingsScreen$HighlightType[HighlightType.CONTROLLER_ON_TOGGLE.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
            }
        }

        if(!anyHovered)
        {
            index = 1;
            CInputHelper.setCursor(((RemapInputElement)elements.get(1)).hb);
        } else
        {
            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.screens.options.InputSettingsScreen.HighlightType[type.ordinal()])
            {
            default:
                break;

            case 1: // '\001'
                if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
                {
                    if(--index < 1)
                        index = 1;
                    CInputHelper.setCursor(((RemapInputElement)elements.get(index)).hb);
                    elementHb = ((RemapInputElement)elements.get(index)).hb;
                    break;
                }
                if(!CInputActionSet.down.isJustPressed() && !CInputActionSet.altDown.isJustPressed())
                    break;
                if(++index > elements.size() - 1)
                {
                    CInputHelper.setCursor(resetButtonHb);
                    elementHb = resetButtonHb;
                } else
                {
                    CInputHelper.setCursor(((RemapInputElement)elements.get(index)).hb);
                    elementHb = ((RemapInputElement)elements.get(index)).hb;
                }
                break;

            case 2: // '\002'
                if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
                {
                    CInputHelper.setCursor(((RemapInputElement)elements.get(elements.size() - 1)).hb);
                    elementHb = ((RemapInputElement)elements.get(elements.size() - 1)).hb;
                    break;
                }
                if(CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed())
                {
                    CInputHelper.setCursor(controllerEnabledToggleButton.hb);
                    elementHb = controllerEnabledToggleButton.hb;
                    break;
                }
                if(CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed())
                {
                    CInputHelper.setCursor(touchscreenModeButton.hb);
                    elementHb = touchscreenModeButton.hb;
                }
                break;

            case 3: // '\003'
                if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
                {
                    CInputHelper.setCursor(((RemapInputElement)elements.get(elements.size() - 1)).hb);
                    elementHb = ((RemapInputElement)elements.get(elements.size() - 1)).hb;
                    break;
                }
                if(CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
                {
                    CInputHelper.setCursor(resetButtonHb);
                    elementHb = resetButtonHb;
                }
                break;
            }
        }
        updateControllerScrolling();
    }

    private void updateControllerScrolling()
    {
        if((float)Gdx.input.getY() > (float)Settings.HEIGHT * 0.65F)
        {
            targetScrollOffset += Settings.SCROLL_SPEED / 3F;
            if(targetScrollOffset > maxScrollAmount)
                targetScrollOffset = maxScrollAmount;
        } else
        if((float)Gdx.input.getY() < (float)Settings.HEIGHT * 0.35F)
        {
            targetScrollOffset -= Settings.SCROLL_SPEED / 3F;
            if(targetScrollOffset < 0.0F)
                targetScrollOffset = 0.0F;
        }
    }

    private void updateScrolling()
    {
        float targetScrollOffset = this.targetScrollOffset;
        if(InputHelper.scrolledDown)
            targetScrollOffset += Settings.SCROLL_SPEED * 3F;
        else
        if(InputHelper.scrolledUp)
            targetScrollOffset -= Settings.SCROLL_SPEED * 3F;
        if(targetScrollOffset != this.targetScrollOffset)
        {
            this.targetScrollOffset = MathHelper.scrollSnapLerpSpeed(this.targetScrollOffset, targetScrollOffset);
            this.targetScrollOffset = MathUtils.clamp(this.targetScrollOffset, 0.0F, maxScrollAmount);
        }
        updateBarPosition();
    }

    public void hide()
    {
        CardCrawlGame.sound.play("DECK_CLOSE", 0.1F);
        targetX = HIDE_X;
        button.hide();
        screenUp = false;
        InputActionSet.save();
        CInputActionSet.save();
        CardCrawlGame.mainMenuScreen.panelScreen.refresh();
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(Color.WHITE);
        renderFullscreenBackground(sb, ImageMaster.INPUT_SETTINGS_BG);
        RemapInputElement element;
        for(Iterator iterator = elements.iterator(); iterator.hasNext(); element.render(sb))
            element = (RemapInputElement)iterator.next();

        renderResetDefaultButton(sb);
        renderFullscreenBackground(sb, ImageMaster.INPUT_SETTINGS_EDGES);
        scrollBar.render(sb);
        renderHeader(sb);
        if(Settings.isControllerMode)
            sb.draw(CInputActionSet.pageLeftViewDeck.getKeyImg(), gameSettingsHb.cX - 32F - FontHelper.getSmartWidth(FontHelper.panelNameFont, GAME_SETTINNGS_TAB_HEADER, 99999F, 2.0F) / 2.0F - 42F * Settings.scale, (Settings.OPTION_Y - 32F) + 379F * Settings.scale, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        controllerEnabledToggleButton.render(sb);
        if(!Settings.isConsoleBuild)
            touchscreenModeButton.render(sb);
        button.render(sb);
    }

    private void updateKeyPositions()
    {
        float x = ROW_X_POSITION;
        float y = SCROLL_CONTAINER_TOP - ROW_TABLE_VERTICAL_EXTRA_PADDING - ROW_Y_DIFF / 2.0F;
        if(Settings.isSixteenByTen)
            y -= Settings.scale * 4F;
        visibleScrollOffset = MathHelper.scrollSnapLerpSpeed(visibleScrollOffset, targetScrollOffset);
        y += visibleScrollOffset;
        float maxVisibleY = SCROLL_CONTAINER_TOP - Settings.scale * 10F;
        float minVisibleY = SCROLL_CONTAINER_BOTTOM - (RemapInputElement.ROW_HEIGHT - Settings.scale * 10F);
        for(Iterator iterator = elements.iterator(); iterator.hasNext();)
        {
            RemapInputElement element = (RemapInputElement)iterator.next();
            element.move(x, y);
            boolean isInView = minVisibleY < element.hb.y && element.hb.y < maxVisibleY;
            element.isHidden = !isInView;
            y -= ROW_Y_DIFF;
        }

    }

    private void renderHeader(SpriteBatch sb)
    {
        Color textColor = gameSettingsHb.hovered ? Settings.GOLD_COLOR : Color.LIGHT_GRAY;
        FontHelper.renderFontCentered(sb, FontHelper.panelNameFont, GAME_SETTINNGS_TAB_HEADER, gameSettingsHb.cX, gameSettingsHb.cY, textColor);
        gameSettingsHb.render(sb);
        FontHelper.renderFontCentered(sb, FontHelper.panelNameFont, TAB_HEADER, gameSettingsHb.cX + 396F * Settings.scale, gameSettingsHb.cY, Settings.GOLD_COLOR);
    }

    private void renderFullscreenBackground(SpriteBatch sb, Texture image)
    {
        sb.draw(image, (float)Settings.WIDTH / 2.0F - 960F, Settings.OPTION_Y - 540F, 960F, 540F, 1920F, 1080F, Settings.scale, Settings.scale, 0.0F, 0, 0, 1920, 1080, false, false);
    }

    private void renderResetDefaultButton(SpriteBatch sb)
    {
        Color color = resetButtonHb.hovered ? Settings.GREEN_TEXT_COLOR : Settings.CREAM_COLOR;
        FontHelper.renderFontCentered(sb, FontHelper.panelEndTurnFont, TEXT[6], resetButtonHb.cX, resetButtonHb.cY, color);
        resetButtonHb.render(sb);
    }

    public void didStartRemapping(RemapInputElement element)
    {
        for(Iterator iterator = elements.iterator(); iterator.hasNext();)
        {
            RemapInputElement e = (RemapInputElement)iterator.next();
            e.hasInputFocus = e == element;
        }

    }

    public boolean willRemap(RemapInputElement element, int oldKey, int newKey)
    {
        if(oldKey == newKey)
            return true;
        Iterator iterator = elements.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            RemapInputElement e = (RemapInputElement)iterator.next();
            if(e.action != null && e.action.getKey() == newKey)
                e.action.remap(oldKey);
        } while(true);
        return true;
    }

    public boolean willRemapController(RemapInputElement element, int oldCode, int newCode)
    {
        if(oldCode == newCode)
            return true;
        Iterator iterator = elements.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            RemapInputElement e = (RemapInputElement)iterator.next();
            if(e.cAction != null && e.cAction.getKey() == newCode)
                e.cAction.remap(oldCode);
        } while(true);
        return true;
    }

    public void hoverStarted(Hitbox hitbox)
    {
        CardCrawlGame.sound.play("UI_HOVER");
    }

    public void startClicking(Hitbox hitbox)
    {
        CardCrawlGame.sound.play("UI_CLICK_1");
    }

    public void clicked(Hitbox hb)
    {
        if(hb == resetButtonHb)
        {
            CardCrawlGame.sound.play("END_TURN");
            InputActionSet.resetToDefaults();
            CInputActionSet.resetToDefaults();
            refreshData();
            updateKeyPositions();
        } else
        if(hb == gameSettingsHb)
            if(CardCrawlGame.isInARun())
            {
                AbstractDungeon.settingsScreen.open(false);
            } else
            {
                CardCrawlGame.sound.play("END_TURN");
                CardCrawlGame.mainMenuScreen.isSettingsUp = true;
                InputHelper.pressedEscape = false;
                CardCrawlGame.mainMenuScreen.statsScreen.hide();
                CardCrawlGame.mainMenuScreen.cancelButton.hide();
                CardCrawlGame.cancelButton.showInstantly(MainMenuScreen.TEXT[2]);
                CardCrawlGame.mainMenuScreen.screen = com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen.CurScreen.SETTINGS;
            }
    }

    public void scrolledUsingBar(float newPercent)
    {
        targetScrollOffset = MathHelper.valueFromPercentBetween(0.0F, maxScrollAmount, newPercent);
        scrollBar.parentScrolledToPercent(newPercent);
    }

    private void updateBarPosition()
    {
        float percent = MathHelper.percentFromValueBetween(0.0F, maxScrollAmount, targetScrollOffset);
        scrollBar.parentScrolledToPercent(percent);
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private static final String TAB_HEADER;
    private static final String GAME_SETTINNGS_TAB_HEADER;
    private static final String RETURN_BUTTON_TEXT;
    private static final boolean ALLOW_OVERSCROLL = false;
    private static final int BG_RAW_WIDTH = 1920;
    private static final int BG_RAW_HEIGHT = 1080;
    private static final float SHOW_X = 0F;
    private static final float HIDE_X;
    private static final float GAME_SETTINGS_BUTTON_WIDTH;
    private static final float ROW_X_POSITION;
    private static final float ROW_TABLE_VERTICAL_EXTRA_PADDING;
    private static final float ROW_PADDING;
    private static final float ROW_Y_DIFF;
    private static final float SCROLL_CONTAINER_VISIBLE_HEIGHT;
    private static final float SCROLL_CONTAINER_BOTTOM;
    private static final float SCROLL_CONTAINER_TOP;
    private static final float SCROLLBAR_X;
    private static final float SCROLLBAR_Y;
    private static final float SCROLLBAR_HEIGHT;
    private static final float RESET_BUTTON_CY;
    public MenuCancelButton button;
    private ScrollBar scrollBar;
    private Hitbox resetButtonHb;
    private ArrayList elements;
    private Hitbox gameSettingsHb;
    private GiantToggleButton controllerEnabledToggleButton;
    private GiantToggleButton touchscreenModeButton;
    private float screenX;
    private float targetX;
    private float maxScrollAmount;
    private float targetScrollOffset;
    private float visibleScrollOffset;
    public boolean screenUp;
    private Hitbox elementHb;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("InputSettingsScreen");
        TEXT = uiStrings.TEXT;
        TAB_HEADER = TEXT[0];
        GAME_SETTINNGS_TAB_HEADER = TEXT[1];
        RETURN_BUTTON_TEXT = TEXT[2];
        HIDE_X = -1100F * Settings.scale;
        GAME_SETTINGS_BUTTON_WIDTH = 360F * Settings.scale;
        ROW_X_POSITION = (float)Settings.WIDTH / 2.0F - 37F * Settings.scale;
        ROW_TABLE_VERTICAL_EXTRA_PADDING = 10F * Settings.scale;
        ROW_PADDING = 0.0F * Settings.scale;
        ROW_Y_DIFF = RemapInputElement.ROW_HEIGHT + ROW_PADDING;
        SCROLL_CONTAINER_VISIBLE_HEIGHT = 651F * Settings.scale;
        SCROLL_CONTAINER_BOTTOM = Settings.OPTION_Y - 360F * Settings.scale;
        SCROLL_CONTAINER_TOP = SCROLL_CONTAINER_BOTTOM + SCROLL_CONTAINER_VISIBLE_HEIGHT;
        SCROLLBAR_X = (float)Settings.WIDTH / 2.0F + 576F * Settings.scale;
        SCROLLBAR_Y = (float)(Settings.HEIGHT / 2) - 61F * Settings.scale;
        SCROLLBAR_HEIGHT = 584F * Settings.scale;
        RESET_BUTTON_CY = (Settings.isSixteenByTen ? 100F : 70F) * Settings.scale;
    }
}
