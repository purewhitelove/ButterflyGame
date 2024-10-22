// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DrawPileViewScreen.java

package com.megacrit.cardcrawl.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.FrozenEye;
import com.megacrit.cardcrawl.screens.mainMenu.ScrollBar;
import com.megacrit.cardcrawl.screens.mainMenu.ScrollBarListener;
import com.megacrit.cardcrawl.ui.buttons.CancelButton;
import com.megacrit.cardcrawl.ui.panels.*;
import java.util.ArrayList;
import java.util.Iterator;

public class DrawPileViewScreen
    implements ScrollBarListener
{

    public DrawPileViewScreen()
    {
        drawPileCopy = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);
        isHovered = false;
        grabbedScreen = false;
        scrollLowerBound = -Settings.DEFAULT_SCROLL_LIMIT;
        scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT;
        grabStartY = scrollLowerBound;
        currentDiffY = scrollLowerBound;
        hoveredCard = null;
        prevDeckSize = 0;
        controllerCard = null;
        drawStartX = Settings.WIDTH;
        drawStartX -= 5F * AbstractCard.IMG_WIDTH * 0.75F;
        drawStartX -= 4F * Settings.CARD_VIEW_PAD_X;
        drawStartX /= 2.0F;
        drawStartX += (AbstractCard.IMG_WIDTH * 0.75F) / 2.0F;
        padX = AbstractCard.IMG_WIDTH * 0.75F + Settings.CARD_VIEW_PAD_X;
        padY = AbstractCard.IMG_HEIGHT * 0.75F + Settings.CARD_VIEW_PAD_Y;
        scrollBar = new ScrollBar(this);
        scrollBar.move(0.0F, -30F * Settings.scale);
    }

    public void update()
    {
        boolean isDraggingScrollBar = false;
        if(shouldShowScrollBar())
            isDraggingScrollBar = scrollBar.update();
        if(!isDraggingScrollBar)
            updateScrolling();
        updateControllerInput();
        if(Settings.isControllerMode && controllerCard != null && !CardCrawlGame.isPopupOpen && !CInputHelper.isTopPanelActive())
            if((float)Gdx.input.getY() > (float)Settings.HEIGHT * 0.7F)
                currentDiffY += Settings.SCROLL_SPEED;
            else
            if((float)Gdx.input.getY() < (float)Settings.HEIGHT * 0.3F)
                currentDiffY -= Settings.SCROLL_SPEED;
        updatePositions();
        if(Settings.isControllerMode && controllerCard != null && !CInputHelper.isTopPanelActive())
            CInputHelper.setCursor(controllerCard.hb);
    }

    private void updateControllerInput()
    {
        if(!Settings.isControllerMode || CInputHelper.isTopPanelActive())
            return;
        boolean anyHovered = false;
        int index = 0;
        Iterator iterator = drawPileCopy.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.hb.hovered)
            {
                anyHovered = true;
                break;
            }
            index++;
        } while(true);
        if(!anyHovered)
        {
            Gdx.input.setCursorPosition((int)((AbstractCard)drawPileCopy.group.get(0)).hb.cX, Settings.HEIGHT - (int)((AbstractCard)drawPileCopy.group.get(0)).hb.cY);
            controllerCard = (AbstractCard)drawPileCopy.group.get(0);
        } else
        if((CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed()) && drawPileCopy.size() > 5)
        {
            if((index -= 5) < 0)
            {
                int wrap = drawPileCopy.size() / 5;
                index += wrap * 5;
                if(index + 5 < drawPileCopy.size())
                    index += 5;
            }
            Gdx.input.setCursorPosition((int)((AbstractCard)drawPileCopy.group.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractCard)drawPileCopy.group.get(index)).hb.cY);
            controllerCard = (AbstractCard)drawPileCopy.group.get(index);
        } else
        if((CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed()) && drawPileCopy.size() > 5)
        {
            if(index < drawPileCopy.size() - 5)
                index += 5;
            else
                index %= 5;
            Gdx.input.setCursorPosition((int)((AbstractCard)drawPileCopy.group.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractCard)drawPileCopy.group.get(index)).hb.cY);
            controllerCard = (AbstractCard)drawPileCopy.group.get(index);
        } else
        if(CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
        {
            if(index % 5 > 0)
                index--;
            else
            if((index += 4) > drawPileCopy.size() - 1)
                index = drawPileCopy.size() - 1;
            Gdx.input.setCursorPosition((int)((AbstractCard)drawPileCopy.group.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractCard)drawPileCopy.group.get(index)).hb.cY);
            controllerCard = (AbstractCard)drawPileCopy.group.get(index);
        } else
        if(CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed())
        {
            if(index % 5 < 4)
            {
                if(++index > drawPileCopy.size() - 1)
                    index -= drawPileCopy.size() % 5;
            } else
            if((index -= 4) < 0)
                index = 0;
            Gdx.input.setCursorPosition((int)((AbstractCard)drawPileCopy.group.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractCard)drawPileCopy.group.get(index)).hb.cY);
            controllerCard = (AbstractCard)drawPileCopy.group.get(index);
        }
    }

    private void updateScrolling()
    {
        int y = InputHelper.mY;
        if(!grabbedScreen)
        {
            if(InputHelper.scrolledDown)
                currentDiffY += Settings.SCROLL_SPEED;
            else
            if(InputHelper.scrolledUp)
                currentDiffY -= Settings.SCROLL_SPEED;
            if(InputHelper.justClickedLeft)
            {
                grabbedScreen = true;
                grabStartY = (float)y - currentDiffY;
            }
        } else
        if(InputHelper.isMouseDown)
            currentDiffY = (float)y - grabStartY;
        else
            grabbedScreen = false;
        if(prevDeckSize != drawPileCopy.size())
            calculateScrollBounds();
        resetScrolling();
        updateBarPosition();
    }

    private void calculateScrollBounds()
    {
        if(drawPileCopy.size() > 10)
        {
            int scrollTmp = drawPileCopy.size() / 5 - 2;
            if(drawPileCopy.size() % 5 != 0)
                scrollTmp++;
            scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT + (float)scrollTmp * padY;
        } else
        {
            scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT;
        }
        prevDeckSize = drawPileCopy.size();
    }

    private void resetScrolling()
    {
        if(currentDiffY < scrollLowerBound)
            currentDiffY = MathHelper.scrollSnapLerpSpeed(currentDiffY, scrollLowerBound);
        else
        if(currentDiffY > scrollUpperBound)
            currentDiffY = MathHelper.scrollSnapLerpSpeed(currentDiffY, scrollUpperBound);
    }

    private void updatePositions()
    {
        hoveredCard = null;
        int lineNum = 0;
        ArrayList cards = drawPileCopy.group;
        for(int i = 0; i < cards.size(); i++)
        {
            int mod = i % 5;
            if(mod == 0 && i != 0)
                lineNum++;
            ((AbstractCard)cards.get(i)).target_x = drawStartX + (float)mod * padX;
            ((AbstractCard)cards.get(i)).target_y = (drawStartY + currentDiffY) - (float)lineNum * padY;
            ((AbstractCard)cards.get(i)).update();
            if(!AbstractDungeon.topPanel.potionUi.isHidden)
                continue;
            ((AbstractCard)cards.get(i)).updateHoverLogic();
            if(((AbstractCard)cards.get(i)).hb.hovered)
                hoveredCard = (AbstractCard)cards.get(i);
        }

    }

    public void reopen()
    {
        if(Settings.isControllerMode)
        {
            Gdx.input.setCursorPosition(10, Settings.HEIGHT / 2);
            controllerCard = null;
        }
        AbstractDungeon.overlayMenu.cancelButton.show(TEXT[2]);
    }

    public void open()
    {
        if(Settings.isControllerMode)
        {
            Gdx.input.setCursorPosition(10, Settings.HEIGHT / 2);
            controllerCard = null;
        }
        CardCrawlGame.sound.play("DECK_OPEN");
        AbstractDungeon.overlayMenu.showBlackScreen();
        AbstractDungeon.overlayMenu.cancelButton.show(TEXT[2]);
        currentDiffY = scrollLowerBound;
        grabStartY = scrollLowerBound;
        grabbedScreen = false;
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.GAME_DECK_VIEW;
        drawPileCopy.clear();
        AbstractCard c;
        for(Iterator iterator = AbstractDungeon.player.drawPile.group.iterator(); iterator.hasNext(); drawPileCopy.addToBottom(c))
        {
            c = (AbstractCard)iterator.next();
            c.setAngle(0.0F, true);
            c.targetDrawScale = 0.75F;
            c.targetDrawScale = 0.75F;
            c.drawScale = 0.75F;
            c.lighten(true);
        }

        if(!AbstractDungeon.player.hasRelic("Frozen Eye"))
        {
            drawPileCopy.sortAlphabetically(true);
            drawPileCopy.sortByRarityPlusStatusCardType(true);
        }
        hideCards();
        if(drawPileCopy.group.size() <= 5)
            drawStartY = (float)Settings.HEIGHT * 0.5F;
        else
            drawStartY = (float)Settings.HEIGHT * 0.66F;
        calculateScrollBounds();
    }

    private void hideCards()
    {
        int lineNum = 0;
        ArrayList cards = drawPileCopy.group;
        for(int i = 0; i < cards.size(); i++)
        {
            int mod = i % 5;
            if(mod == 0 && i != 0)
                lineNum++;
            ((AbstractCard)cards.get(i)).current_x = drawStartX + (float)mod * padX;
            ((AbstractCard)cards.get(i)).current_y = (drawStartY + currentDiffY) - (float)lineNum * padY - MathUtils.random(100F * Settings.scale, 200F * Settings.scale);
            ((AbstractCard)cards.get(i)).targetDrawScale = 0.75F;
            ((AbstractCard)cards.get(i)).drawScale = 0.75F;
        }

    }

    public void render(SpriteBatch sb)
    {
        if(shouldShowScrollBar())
            scrollBar.render(sb);
        if(hoveredCard == null)
        {
            drawPileCopy.render(sb);
        } else
        {
            drawPileCopy.renderExceptOneCard(sb, hoveredCard);
            hoveredCard.renderHoverShadow(sb);
            hoveredCard.render(sb);
            hoveredCard.renderCardTip(sb);
        }
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.DRAW_PILE_BANNER, 0.0F, 0.0F, 630F * Settings.scale, 128F * Settings.scale);
        FontHelper.renderFontLeftTopAligned(sb, FontHelper.panelNameFont, TEXT[3], 166F * Settings.scale, 82F * Settings.scale, Settings.LIGHT_YELLOW_COLOR);
        if(!AbstractDungeon.player.hasRelic("Frozen Eye"))
            FontHelper.renderDeckViewTip(sb, BODY_INFO, 48F * Settings.scale, Settings.GOLD_COLOR);
        FontHelper.renderDeckViewTip(sb, HEADER_INFO, 96F * Settings.scale, Settings.CREAM_COLOR);
        AbstractDungeon.overlayMenu.combatDeckPanel.render(sb);
    }

    public void scrolledUsingBar(float newPercent)
    {
        currentDiffY = MathHelper.valueFromPercentBetween(scrollLowerBound, scrollUpperBound, newPercent);
        updateBarPosition();
    }

    private void updateBarPosition()
    {
        float percent = MathHelper.percentFromValueBetween(scrollLowerBound, scrollUpperBound, currentDiffY);
        scrollBar.parentScrolledToPercent(percent);
    }

    private boolean shouldShowScrollBar()
    {
        return scrollUpperBound > SCROLL_BAR_THRESHOLD;
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private CardGroup drawPileCopy;
    public boolean isHovered;
    private static final int CARDS_PER_LINE = 5;
    private static final float SCROLL_BAR_THRESHOLD;
    private boolean grabbedScreen;
    private static float drawStartX;
    private static float drawStartY;
    private static float padX;
    private static float padY;
    private float scrollLowerBound;
    private float scrollUpperBound;
    private float grabStartY;
    private float currentDiffY;
    private static final String HEADER_INFO;
    private static final String BODY_INFO;
    private AbstractCard hoveredCard;
    private int prevDeckSize;
    private ScrollBar scrollBar;
    private AbstractCard controllerCard;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("DrawPileViewScreen");
        TEXT = uiStrings.TEXT;
        SCROLL_BAR_THRESHOLD = 500F * Settings.scale;
        HEADER_INFO = TEXT[0];
        BODY_INFO = TEXT[1];
    }
}
