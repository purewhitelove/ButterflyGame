// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MasterDeckViewScreen.java

package com.megacrit.cardcrawl.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.screens.mainMenu.ScrollBar;
import com.megacrit.cardcrawl.screens.mainMenu.ScrollBarListener;
import com.megacrit.cardcrawl.screens.mainMenu.SortHeaderButton;
import com.megacrit.cardcrawl.ui.buttons.*;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import java.util.*;

// Referenced classes of package com.megacrit.cardcrawl.screens:
//            MasterDeckSortHeader, SingleCardViewPopup

public class MasterDeckViewScreen
    implements ScrollBarListener
{

    public MasterDeckViewScreen()
    {
        grabbedScreen = false;
        grabStartY = 0.0F;
        currentDiffY = 0.0F;
        scrollLowerBound = -Settings.DEFAULT_SCROLL_LIMIT;
        scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT;
        hoveredCard = null;
        clickStartedCard = null;
        prevDeckSize = 0;
        controllerCard = null;
        headerIndex = -1;
        sortOrder = null;
        tmpSortedDeck = null;
        tmpHeaderPosition = (-1.0F / 0.0F);
        headerScrollLockRemainingFrames = 0;
        justSorted = false;
        drawStartX = Settings.WIDTH;
        drawStartX -= 5F * AbstractCard.IMG_WIDTH * 0.75F;
        drawStartX -= 4F * Settings.CARD_VIEW_PAD_X;
        drawStartX /= 2.0F;
        drawStartX += (AbstractCard.IMG_WIDTH * 0.75F) / 2.0F;
        padX = AbstractCard.IMG_WIDTH * 0.75F + Settings.CARD_VIEW_PAD_X;
        padY = AbstractCard.IMG_HEIGHT * 0.75F + Settings.CARD_VIEW_PAD_Y;
        scrollBar = new ScrollBar(this);
        scrollBar.move(0.0F, -30F * Settings.scale);
        sortHeader = new MasterDeckSortHeader(this);
    }

    public void update()
    {
        updateControllerInput();
        if(Settings.isControllerMode && controllerCard != null && !CardCrawlGame.isPopupOpen && !AbstractDungeon.topPanel.selectPotionMode)
            if((float)Gdx.input.getY() > (float)Settings.HEIGHT * 0.7F)
                currentDiffY += Settings.SCROLL_SPEED;
            else
            if((float)Gdx.input.getY() < (float)Settings.HEIGHT * 0.3F)
                currentDiffY -= Settings.SCROLL_SPEED;
        boolean isDraggingScrollBar = false;
        if(shouldShowScrollBar())
            isDraggingScrollBar = scrollBar.update();
        if(!isDraggingScrollBar)
            updateScrolling();
        updatePositions();
        sortHeader.update();
        updateClicking();
        if(Settings.isControllerMode && controllerCard != null)
            Gdx.input.setCursorPosition((int)controllerCard.hb.cX, (int)((float)Settings.HEIGHT - controllerCard.hb.cY));
    }

    private void updateControllerInput()
    {
        if(!Settings.isControllerMode || AbstractDungeon.topPanel.selectPotionMode)
            return;
        CardGroup deck = AbstractDungeon.player.masterDeck;
        boolean anyHovered = false;
        int index = 0;
        if(tmpSortedDeck == null)
            tmpSortedDeck = deck.group;
        Iterator iterator = tmpSortedDeck.iterator();
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
        anyHovered = anyHovered || headerIndex >= 0;
        if(!anyHovered)
        {
            if(tmpSortedDeck.size() > 0)
            {
                Gdx.input.setCursorPosition((int)((AbstractCard)tmpSortedDeck.get(0)).hb.cX, (int)((AbstractCard)tmpSortedDeck.get(0)).hb.cY);
                controllerCard = (AbstractCard)tmpSortedDeck.get(0);
            }
        } else
        if(headerIndex >= 0)
        {
            if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
            {
                sortHeader.selectionIndex = headerIndex = -1;
                controllerCard = (AbstractCard)tmpSortedDeck.get(0);
                Gdx.input.setCursorPosition((int)((AbstractCard)tmpSortedDeck.get(0)).hb.cX, Settings.HEIGHT - (int)((AbstractCard)tmpSortedDeck.get(0)).hb.cY);
            } else
            if(CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
            {
                if(headerIndex > 0)
                    selectSortButton(--headerIndex);
            } else
            if((CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed()) && headerIndex < sortHeader.buttons.length - 1)
                selectSortButton(++headerIndex);
        } else
        if((CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed()) && deck.size() > 5)
        {
            if((index -= 5) < 0)
            {
                selectSortButton(headerIndex = 0);
                return;
            }
            Gdx.input.setCursorPosition((int)((AbstractCard)tmpSortedDeck.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractCard)tmpSortedDeck.get(index)).hb.cY);
            controllerCard = (AbstractCard)tmpSortedDeck.get(index);
        } else
        if((CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed()) && deck.size() > 5)
        {
            if(index < deck.size() - 5)
                index += 5;
            else
                index %= 5;
            Gdx.input.setCursorPosition((int)((AbstractCard)tmpSortedDeck.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractCard)tmpSortedDeck.get(index)).hb.cY);
            controllerCard = (AbstractCard)tmpSortedDeck.get(index);
        } else
        if(CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
        {
            if(index % 5 > 0)
                index--;
            else
            if((index += 4) > deck.size() - 1)
                index = deck.size() - 1;
            Gdx.input.setCursorPosition((int)((AbstractCard)tmpSortedDeck.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractCard)tmpSortedDeck.get(index)).hb.cY);
            controllerCard = (AbstractCard)tmpSortedDeck.get(index);
        } else
        if(CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed())
        {
            if(index % 5 < 4)
            {
                if(++index > deck.size() - 1)
                    index -= deck.size() % 5;
            } else
            if((index -= 4) < 0)
                index = 0;
            Gdx.input.setCursorPosition((int)((AbstractCard)tmpSortedDeck.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractCard)tmpSortedDeck.get(index)).hb.cY);
            controllerCard = (AbstractCard)tmpSortedDeck.get(index);
        }
    }

    public void open()
    {
        if(Settings.isControllerMode)
        {
            Gdx.input.setCursorPosition(10, Settings.HEIGHT / 2);
            controllerCard = null;
        }
        AbstractDungeon.player.releaseCard();
        CardCrawlGame.sound.play("DECK_OPEN");
        currentDiffY = scrollLowerBound;
        grabStartY = scrollLowerBound;
        grabbedScreen = false;
        hideCards();
        AbstractDungeon.dynamicBanner.hide();
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.MASTER_DECK_VIEW;
        AbstractDungeon.overlayMenu.proceedButton.hide();
        AbstractDungeon.overlayMenu.hideCombatPanels();
        AbstractDungeon.overlayMenu.showBlackScreen();
        AbstractDungeon.overlayMenu.cancelButton.show(TEXT[1]);
        calculateScrollBounds();
    }

    private void updatePositions()
    {
        hoveredCard = null;
        int lineNum = 0;
        ArrayList cards = AbstractDungeon.player.masterDeck.group;
        if(sortOrder != null)
        {
            cards = new ArrayList(cards);
            Collections.sort(cards, sortOrder);
            tmpSortedDeck = cards;
        } else
        {
            tmpSortedDeck = null;
        }
        AbstractCard c;
        if(justSorted && headerScrollLockRemainingFrames <= 0)
        {
            c = highestYPosition(cards);
            if(c != null)
                tmpHeaderPosition = c.current_y;
        }
        for(int i = 0; i < cards.size(); i++)
        {
            int mod = i % 5;
            if(mod == 0 && i != 0)
                lineNum++;
            ((AbstractCard)cards.get(i)).target_x = drawStartX + (float)mod * padX;
            ((AbstractCard)cards.get(i)).target_y = (drawStartY + currentDiffY) - (float)lineNum * padY;
            ((AbstractCard)cards.get(i)).update();
            ((AbstractCard)cards.get(i)).updateHoverLogic();
            if(((AbstractCard)cards.get(i)).hb.hovered)
                hoveredCard = (AbstractCard)cards.get(i);
        }

        i = highestYPosition(cards);
        if(justSorted && i != null)
        {
            int lerps = 0;
            float lerpY = ((AbstractCard) (i)).current_y;
            for(float lerpTarget = ((AbstractCard) (i)).target_y; lerpY != lerpTarget;)
            {
                lerpY = MathHelper.cardLerpSnap(lerpY, lerpTarget);
                lerps++;
            }

            headerScrollLockRemainingFrames = lerps;
        }
        headerScrollLockRemainingFrames -= Settings.FAST_MODE ? 2 : 1;
        if(cards.size() > 0 && sortHeader != null && i != null)
        {
            sortHeader.updateScrollPosition(headerScrollLockRemainingFrames > 0 ? tmpHeaderPosition : ((AbstractCard) (i)).current_y);
            justSorted = false;
        }
    }

    private AbstractCard highestYPosition(List cards)
    {
        if(cards == null)
            return null;
        float highestY = 0.0F;
        AbstractCard retVal = null;
        Iterator iterator = cards.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard card = (AbstractCard)iterator.next();
            if(card.current_y > highestY)
            {
                highestY = card.current_y;
                retVal = card;
            }
        } while(true);
        return retVal;
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
        if(prevDeckSize != AbstractDungeon.player.masterDeck.size())
            calculateScrollBounds();
        resetScrolling();
        updateBarPosition();
    }

    private void updateClicking()
    {
        if(hoveredCard != null)
        {
            CardCrawlGame.cursor.changeType(com.megacrit.cardcrawl.core.GameCursor.CursorType.INSPECT);
            if(InputHelper.justClickedLeft)
                clickStartedCard = hoveredCard;
            if((InputHelper.justReleasedClickLeft && hoveredCard == clickStartedCard || CInputActionSet.select.isJustPressed()) && headerIndex < 0)
            {
                InputHelper.justReleasedClickLeft = false;
                CardCrawlGame.cardPopup.open(hoveredCard, AbstractDungeon.player.masterDeck);
                clickStartedCard = null;
            }
        } else
        {
            clickStartedCard = null;
        }
    }

    private void calculateScrollBounds()
    {
        if(AbstractDungeon.player.masterDeck.size() > 10)
        {
            int scrollTmp = AbstractDungeon.player.masterDeck.size() / 5 - 2;
            if(AbstractDungeon.player.masterDeck.size() % 5 != 0)
                scrollTmp++;
            scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT + (float)scrollTmp * padY;
        } else
        {
            scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT;
        }
        prevDeckSize = AbstractDungeon.player.masterDeck.size();
    }

    private void resetScrolling()
    {
        if(currentDiffY < scrollLowerBound)
            currentDiffY = MathHelper.scrollSnapLerpSpeed(currentDiffY, scrollLowerBound);
        else
        if(currentDiffY > scrollUpperBound)
            currentDiffY = MathHelper.scrollSnapLerpSpeed(currentDiffY, scrollUpperBound);
    }

    private void hideCards()
    {
        int lineNum = 0;
        ArrayList cards = AbstractDungeon.player.masterDeck.group;
        for(int i = 0; i < cards.size(); i++)
        {
            int mod = i % 5;
            if(mod == 0 && i != 0)
                lineNum++;
            ((AbstractCard)cards.get(i)).current_x = drawStartX + (float)mod * padX;
            ((AbstractCard)cards.get(i)).current_y = (drawStartY + currentDiffY) - (float)lineNum * padY - MathUtils.random(100F * Settings.scale, 200F * Settings.scale);
            ((AbstractCard)cards.get(i)).targetDrawScale = 0.75F;
            ((AbstractCard)cards.get(i)).drawScale = 0.75F;
            ((AbstractCard)cards.get(i)).setAngle(0.0F, true);
        }

    }

    public void render(SpriteBatch sb)
    {
        if(shouldShowScrollBar())
            scrollBar.render(sb);
        if(hoveredCard == null)
        {
            AbstractDungeon.player.masterDeck.renderMasterDeck(sb);
        } else
        {
            AbstractDungeon.player.masterDeck.renderMasterDeckExceptOneCard(sb, hoveredCard);
            hoveredCard.renderHoverShadow(sb);
            hoveredCard.render(sb);
            if(hoveredCard.inBottleFlame)
            {
                AbstractRelic tmp = RelicLibrary.getRelic("Bottled Flame");
                float prevX = tmp.currentX;
                float prevY = tmp.currentY;
                tmp.currentX = hoveredCard.current_x + 130F * Settings.scale;
                tmp.currentY = hoveredCard.current_y + 182F * Settings.scale;
                tmp.scale = hoveredCard.drawScale * Settings.scale * 1.5F;
                tmp.render(sb);
                tmp.currentX = prevX;
                tmp.currentY = prevY;
                tmp = null;
            } else
            if(hoveredCard.inBottleLightning)
            {
                AbstractRelic tmp = RelicLibrary.getRelic("Bottled Lightning");
                float prevX = tmp.currentX;
                float prevY = tmp.currentY;
                tmp.currentX = hoveredCard.current_x + 130F * Settings.scale;
                tmp.currentY = hoveredCard.current_y + 182F * Settings.scale;
                tmp.scale = hoveredCard.drawScale * Settings.scale * 1.5F;
                tmp.render(sb);
                tmp.currentX = prevX;
                tmp.currentY = prevY;
                tmp = null;
            } else
            if(hoveredCard.inBottleTornado)
            {
                AbstractRelic tmp = RelicLibrary.getRelic("Bottled Tornado");
                float prevX = tmp.currentX;
                float prevY = tmp.currentY;
                tmp.currentX = hoveredCard.current_x + 130F * Settings.scale;
                tmp.currentY = hoveredCard.current_y + 182F * Settings.scale;
                tmp.scale = hoveredCard.drawScale * Settings.scale * 1.5F;
                tmp.render(sb);
                tmp.currentX = prevX;
                tmp.currentY = prevY;
                tmp = null;
            }
        }
        AbstractDungeon.player.masterDeck.renderTip(sb);
        FontHelper.renderDeckViewTip(sb, HEADER_INFO, 96F * Settings.scale, Settings.CREAM_COLOR);
        sortHeader.render(sb);
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

    public void setSortOrder(Comparator sortOrder)
    {
        if(this.sortOrder != sortOrder)
            justSorted = true;
        this.sortOrder = sortOrder;
    }

    private void selectSortButton(int index)
    {
        Hitbox hb = sortHeader.buttons[headerIndex].hb;
        Gdx.input.setCursorPosition((int)hb.cX, Settings.HEIGHT - (int)hb.cY);
        controllerCard = null;
        sortHeader.selectionIndex = headerIndex;
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private static float drawStartX;
    private static float drawStartY;
    private static float padX;
    private static float padY;
    private static final float SCROLL_BAR_THRESHOLD;
    private static final int CARDS_PER_LINE = 5;
    private boolean grabbedScreen;
    private float grabStartY;
    private float currentDiffY;
    private float scrollLowerBound;
    private float scrollUpperBound;
    private static final String HEADER_INFO;
    private AbstractCard hoveredCard;
    private AbstractCard clickStartedCard;
    private int prevDeckSize;
    private ScrollBar scrollBar;
    private AbstractCard controllerCard;
    private MasterDeckSortHeader sortHeader;
    private int headerIndex;
    private Comparator sortOrder;
    private ArrayList tmpSortedDeck;
    private float tmpHeaderPosition;
    private int headerScrollLockRemainingFrames;
    private boolean justSorted;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("MasterDeckViewScreen");
        TEXT = uiStrings.TEXT;
        drawStartY = (float)Settings.HEIGHT * 0.66F;
        SCROLL_BAR_THRESHOLD = 500F * Settings.scale;
        HEADER_INFO = TEXT[0];
    }
}
