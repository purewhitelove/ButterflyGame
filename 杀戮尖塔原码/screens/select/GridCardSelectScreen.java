// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GridCardSelectScreen.java

package com.megacrit.cardcrawl.screens.select;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.mainMenu.ScrollBar;
import com.megacrit.cardcrawl.screens.mainMenu.ScrollBarListener;
import com.megacrit.cardcrawl.ui.buttons.*;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import com.megacrit.cardcrawl.vfx.FastCardObtainEffect;
import java.util.ArrayList;
import java.util.Iterator;

public class GridCardSelectScreen
    implements ScrollBarListener
{

    public GridCardSelectScreen()
    {
        grabStartY = 0.0F;
        currentDiffY = 0.0F;
        selectedCards = new ArrayList();
        hoveredCard = null;
        upgradePreviewCard = null;
        numCards = 0;
        cardSelectAmount = 0;
        scrollLowerBound = -Settings.DEFAULT_SCROLL_LIMIT;
        scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT;
        grabbedScreen = false;
        canCancel = true;
        forUpgrade = false;
        forTransform = false;
        forPurge = false;
        confirmScreenUp = false;
        isJustForConfirming = false;
        confirmButton = new GridSelectConfirmButton(TEXT[0]);
        peekButton = new PeekButton();
        tipMsg = "";
        lastTip = "";
        ritualAnimTimer = 0.0F;
        prevDeckSize = 0;
        cancelWasOn = false;
        anyNumber = false;
        forClarity = false;
        controllerCard = null;
        arrowScale1 = 1.0F;
        arrowScale2 = 1.0F;
        arrowScale3 = 1.0F;
        arrowTimer = 0.0F;
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
        updateControllerInput();
        updatePeekButton();
        if(PeekButton.isPeeking)
            return;
        if(Settings.isControllerMode && controllerCard != null && !CardCrawlGame.isPopupOpen && upgradePreviewCard == null)
            if((float)Gdx.input.getY() > (float)Settings.HEIGHT * 0.75F)
                currentDiffY += Settings.SCROLL_SPEED;
            else
            if((float)Gdx.input.getY() < (float)Settings.HEIGHT * 0.25F)
                currentDiffY -= Settings.SCROLL_SPEED;
        boolean isDraggingScrollBar = false;
        if(shouldShowScrollBar())
            isDraggingScrollBar = scrollBar.update();
        if(!isDraggingScrollBar)
            updateScrolling();
        if(forClarity)
            if(selectedCards.size() > 0)
                confirmButton.isDisabled = false;
            else
                confirmButton.isDisabled = true;
        confirmButton.update();
        if(isJustForConfirming)
        {
            updateCardPositionsAndHoverLogic();
            if(confirmButton.hb.clicked || CInputActionSet.topPanel.isJustPressed())
            {
                CInputActionSet.select.unpress();
                confirmButton.hb.clicked = false;
                AbstractDungeon.overlayMenu.cancelButton.hide();
                AbstractDungeon.dynamicBanner.hide();
                confirmScreenUp = false;
                AbstractCard c;
                for(Iterator iterator = targetGroup.group.iterator(); iterator.hasNext(); AbstractDungeon.topLevelEffects.add(new FastCardObtainEffect(c, c.current_x, c.current_y)))
                    c = (AbstractCard)iterator.next();

                AbstractDungeon.closeCurrentScreen();
            }
            return;
        }
        if((anyNumber || forClarity) && confirmButton.hb.clicked)
        {
            confirmButton.hb.clicked = false;
            AbstractDungeon.closeCurrentScreen();
            return;
        }
        if(!confirmScreenUp)
        {
            updateCardPositionsAndHoverLogic();
            if(hoveredCard != null && InputHelper.justClickedLeft)
                hoveredCard.hb.clickStarted = true;
            if(hoveredCard != null && (hoveredCard.hb.clicked || CInputActionSet.select.isJustPressed()))
            {
                hoveredCard.hb.clicked = false;
                if(!selectedCards.contains(hoveredCard))
                {
                    if(forClarity && selectedCards.size() > 0)
                    {
                        ((AbstractCard)selectedCards.get(0)).stopGlowing();
                        selectedCards.clear();
                        cardSelectAmount--;
                    }
                    selectedCards.add(hoveredCard);
                    hoveredCard.beginGlowing();
                    hoveredCard.targetDrawScale = 0.75F;
                    hoveredCard.drawScale = 0.875F;
                    cardSelectAmount++;
                    CardCrawlGame.sound.play("CARD_SELECT");
                    if(numCards == cardSelectAmount)
                    {
                        if(forUpgrade)
                        {
                            hoveredCard.untip();
                            confirmScreenUp = true;
                            upgradePreviewCard = hoveredCard.makeStatEquivalentCopy();
                            upgradePreviewCard.upgrade();
                            upgradePreviewCard.displayUpgrades();
                            upgradePreviewCard.drawScale = 0.875F;
                            hoveredCard.stopGlowing();
                            selectedCards.clear();
                            AbstractDungeon.overlayMenu.cancelButton.show(TEXT[1]);
                            confirmButton.show();
                            confirmButton.isDisabled = false;
                            lastTip = tipMsg;
                            tipMsg = TEXT[2];
                            return;
                        }
                        if(forTransform)
                        {
                            hoveredCard.untip();
                            confirmScreenUp = true;
                            upgradePreviewCard = hoveredCard.makeStatEquivalentCopy();
                            upgradePreviewCard.drawScale = 0.875F;
                            hoveredCard.stopGlowing();
                            selectedCards.clear();
                            AbstractDungeon.overlayMenu.cancelButton.show(TEXT[1]);
                            confirmButton.show();
                            confirmButton.isDisabled = false;
                            lastTip = tipMsg;
                            tipMsg = TEXT[2];
                            return;
                        }
                        if(forPurge)
                        {
                            if(numCards == 1)
                            {
                                hoveredCard.untip();
                                hoveredCard.stopGlowing();
                                confirmScreenUp = true;
                                hoveredCard.current_x = (float)Settings.WIDTH / 2.0F;
                                hoveredCard.target_x = (float)Settings.WIDTH / 2.0F;
                                hoveredCard.current_y = (float)Settings.HEIGHT / 2.0F;
                                hoveredCard.target_y = (float)Settings.HEIGHT / 2.0F;
                                hoveredCard.update();
                                hoveredCard.targetDrawScale = 1.0F;
                                hoveredCard.drawScale = 1.0F;
                                selectedCards.clear();
                                confirmButton.show();
                                confirmButton.isDisabled = false;
                                lastTip = tipMsg;
                                tipMsg = TEXT[2];
                                AbstractDungeon.overlayMenu.cancelButton.show(TEXT[1]);
                            } else
                            {
                                AbstractDungeon.closeCurrentScreen();
                            }
                            AbstractCard c;
                            for(Iterator iterator1 = selectedCards.iterator(); iterator1.hasNext(); c.stopGlowing())
                                c = (AbstractCard)iterator1.next();

                            return;
                        }
                        if(!anyNumber)
                        {
                            AbstractDungeon.closeCurrentScreen();
                            if(AbstractDungeon.screen != com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.SHOP)
                                AbstractDungeon.overlayMenu.cancelButton.hide();
                            else
                                AbstractDungeon.overlayMenu.cancelButton.show(TEXT[3]);
                            AbstractCard c;
                            for(Iterator iterator2 = selectedCards.iterator(); iterator2.hasNext(); c.stopGlowing())
                                c = (AbstractCard)iterator2.next();

                            if(targetGroup.type == com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.DISCARD_PILE)
                            {
                                AbstractCard c;
                                for(Iterator iterator3 = targetGroup.group.iterator(); iterator3.hasNext(); c.lighten(true))
                                {
                                    c = (AbstractCard)iterator3.next();
                                    c.drawScale = 0.12F;
                                    c.targetDrawScale = 0.12F;
                                    c.teleportToDiscardPile();
                                }

                            }
                            return;
                        }
                        if(cardSelectAmount < targetGroup.size() && anyNumber)
                        {
                            AbstractDungeon.closeCurrentScreen();
                            if(AbstractDungeon.screen != com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.SHOP)
                                AbstractDungeon.overlayMenu.cancelButton.hide();
                            else
                                AbstractDungeon.overlayMenu.cancelButton.show(TEXT[3]);
                            AbstractCard c;
                            for(Iterator iterator4 = selectedCards.iterator(); iterator4.hasNext(); c.stopGlowing())
                                c = (AbstractCard)iterator4.next();

                            if(targetGroup.type == com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.DISCARD_PILE)
                            {
                                AbstractCard c;
                                for(Iterator iterator5 = targetGroup.group.iterator(); iterator5.hasNext(); c.lighten(true))
                                {
                                    c = (AbstractCard)iterator5.next();
                                    c.drawScale = 0.12F;
                                    c.targetDrawScale = 0.12F;
                                    c.teleportToDiscardPile();
                                }

                            }
                        }
                    }
                } else
                if(selectedCards.contains(hoveredCard))
                {
                    hoveredCard.stopGlowing();
                    selectedCards.remove(hoveredCard);
                    cardSelectAmount--;
                }
                return;
            }
        } else
        {
            if(forTransform)
            {
                ritualAnimTimer -= Gdx.graphics.getDeltaTime();
                if(ritualAnimTimer < 0.0F)
                {
                    upgradePreviewCard = AbstractDungeon.returnTrulyRandomCardFromAvailable(upgradePreviewCard).makeCopy();
                    ritualAnimTimer = 0.1F;
                }
            }
            if(forUpgrade)
                upgradePreviewCard.update();
            if(!forPurge)
            {
                upgradePreviewCard.drawScale = 1.0F;
                hoveredCard.update();
                hoveredCard.drawScale = 1.0F;
            }
            if(confirmButton.hb.clicked || CInputActionSet.topPanel.isJustPressed())
            {
                CInputActionSet.select.unpress();
                confirmButton.hb.clicked = false;
                AbstractDungeon.overlayMenu.cancelButton.hide();
                confirmScreenUp = false;
                selectedCards.add(hoveredCard);
                AbstractDungeon.closeCurrentScreen();
            }
        }
        if(Settings.isControllerMode)
            if(upgradePreviewCard != null)
                CInputHelper.setCursor(upgradePreviewCard.hb);
            else
            if(controllerCard != null)
                CInputHelper.setCursor(controllerCard.hb);
    }

    private void updatePeekButton()
    {
        peekButton.update();
    }

    private void updateControllerInput()
    {
        if(!Settings.isControllerMode || upgradePreviewCard != null)
            return;
        boolean anyHovered = false;
        int index = 0;
        Iterator iterator = targetGroup.group.iterator();
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
        if(!anyHovered && controllerCard == null)
        {
            CInputHelper.setCursor(((AbstractCard)targetGroup.group.get(0)).hb);
            controllerCard = (AbstractCard)targetGroup.group.get(0);
        } else
        if((CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed()) && targetGroup.size() > 5)
        {
            if(index < 5)
            {
                index = (targetGroup.size() + 2) - (4 - index);
                if(index > targetGroup.size() - 1)
                    index -= 5;
                if(index > targetGroup.size() - 1 || index < 0)
                    index = 0;
            } else
            {
                index -= 5;
            }
            CInputHelper.setCursor(((AbstractCard)targetGroup.group.get(index)).hb);
            controllerCard = (AbstractCard)targetGroup.group.get(index);
        } else
        if((CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed()) && targetGroup.size() > 5)
        {
            if(index < targetGroup.size() - 5)
                index += 5;
            else
                index %= 5;
            CInputHelper.setCursor(((AbstractCard)targetGroup.group.get(index)).hb);
            controllerCard = (AbstractCard)targetGroup.group.get(index);
        } else
        if(CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
        {
            if(index % 5 > 0)
                index--;
            else
            if((index += 4) > targetGroup.size() - 1)
                index = targetGroup.size() - 1;
            CInputHelper.setCursor(((AbstractCard)targetGroup.group.get(index)).hb);
            controllerCard = (AbstractCard)targetGroup.group.get(index);
        } else
        if(CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed())
        {
            if(index % 5 < 4)
            {
                if(++index > targetGroup.size() - 1)
                    index -= targetGroup.size() % 5;
            } else
            if((index -= 4) < 0)
                index = 0;
            if(index > targetGroup.group.size() - 1)
                index = 0;
            CInputHelper.setCursor(((AbstractCard)targetGroup.group.get(index)).hb);
            controllerCard = (AbstractCard)targetGroup.group.get(index);
        }
    }

    private void updateCardPositionsAndHoverLogic()
    {
        if(isJustForConfirming && targetGroup.size() <= 4)
        {
            switch(targetGroup.size())
            {
            case 1: // '\001'
                targetGroup.getBottomCard().current_x = (float)Settings.WIDTH / 2.0F;
                targetGroup.getBottomCard().target_x = (float)Settings.WIDTH / 2.0F;
                break;

            case 2: // '\002'
                ((AbstractCard)targetGroup.group.get(0)).current_x = (float)Settings.WIDTH / 2.0F - padX / 2.0F;
                ((AbstractCard)targetGroup.group.get(0)).target_x = (float)Settings.WIDTH / 2.0F - padX / 2.0F;
                ((AbstractCard)targetGroup.group.get(1)).current_x = (float)Settings.WIDTH / 2.0F + padX / 2.0F;
                ((AbstractCard)targetGroup.group.get(1)).target_x = (float)Settings.WIDTH / 2.0F + padX / 2.0F;
                break;

            case 3: // '\003'
                ((AbstractCard)targetGroup.group.get(0)).current_x = drawStartX + padX;
                ((AbstractCard)targetGroup.group.get(1)).current_x = drawStartX + padX * 2.0F;
                ((AbstractCard)targetGroup.group.get(2)).current_x = drawStartX + padX * 3F;
                ((AbstractCard)targetGroup.group.get(0)).target_x = drawStartX + padX;
                ((AbstractCard)targetGroup.group.get(1)).target_x = drawStartX + padX * 2.0F;
                ((AbstractCard)targetGroup.group.get(2)).target_x = drawStartX + padX * 3F;
                break;

            case 4: // '\004'
                ((AbstractCard)targetGroup.group.get(0)).current_x = (float)Settings.WIDTH / 2.0F - padX / 2.0F - padX;
                ((AbstractCard)targetGroup.group.get(0)).target_x = (float)Settings.WIDTH / 2.0F - padX / 2.0F - padX;
                ((AbstractCard)targetGroup.group.get(1)).current_x = (float)Settings.WIDTH / 2.0F - padX / 2.0F;
                ((AbstractCard)targetGroup.group.get(1)).target_x = (float)Settings.WIDTH / 2.0F - padX / 2.0F;
                ((AbstractCard)targetGroup.group.get(2)).current_x = (float)Settings.WIDTH / 2.0F + padX / 2.0F;
                ((AbstractCard)targetGroup.group.get(2)).target_x = (float)Settings.WIDTH / 2.0F + padX / 2.0F;
                ((AbstractCard)targetGroup.group.get(3)).current_x = (float)Settings.WIDTH / 2.0F + padX / 2.0F + padX;
                ((AbstractCard)targetGroup.group.get(3)).target_x = (float)Settings.WIDTH / 2.0F + padX / 2.0F + padX;
                break;
            }
            ArrayList c2 = targetGroup.group;
label0:
            for(int i = 0; i < c2.size(); i++)
            {
                ((AbstractCard)c2.get(i)).target_y = drawStartY + currentDiffY;
                ((AbstractCard)c2.get(i)).fadingOut = false;
                ((AbstractCard)c2.get(i)).update();
                ((AbstractCard)c2.get(i)).updateHoverLogic();
                hoveredCard = null;
                Iterator iterator = c2.iterator();
                do
                {
                    if(!iterator.hasNext())
                        continue label0;
                    AbstractCard c = (AbstractCard)iterator.next();
                    if(c.hb.hovered)
                        hoveredCard = c;
                } while(true);
            }

            return;
        }
        int lineNum = 0;
        ArrayList cards = targetGroup.group;
label1:
        for(int i = 0; i < cards.size(); i++)
        {
            int mod = i % 5;
            if(mod == 0 && i != 0)
                lineNum++;
            ((AbstractCard)cards.get(i)).target_x = drawStartX + (float)mod * padX;
            ((AbstractCard)cards.get(i)).target_y = (drawStartY + currentDiffY) - (float)lineNum * padY;
            ((AbstractCard)cards.get(i)).fadingOut = false;
            ((AbstractCard)cards.get(i)).update();
            ((AbstractCard)cards.get(i)).updateHoverLogic();
            hoveredCard = null;
            Iterator iterator1 = cards.iterator();
            do
            {
                if(!iterator1.hasNext())
                    continue label1;
                AbstractCard c = (AbstractCard)iterator1.next();
                if(c.hb.hovered)
                    hoveredCard = c;
            } while(true);
        }

    }

    public void open(CardGroup group, int numCards, boolean anyNumber, String msg)
    {
        open(group, numCards, msg, false, false, true, false);
        this.anyNumber = anyNumber;
        forClarity = !anyNumber;
        confirmButton.hideInstantly();
        confirmButton.show();
        confirmButton.updateText(TEXT[0]);
        confirmButton.isDisabled = !anyNumber;
    }

    public void open(CardGroup group, int numCards, String tipMsg, boolean forUpgrade, boolean forTransform, boolean canCancel, boolean forPurge)
    {
        targetGroup = group;
        callOnOpen();
        this.forUpgrade = forUpgrade;
        this.forTransform = forTransform;
        this.canCancel = canCancel;
        this.forPurge = forPurge;
        this.tipMsg = tipMsg;
        this.numCards = numCards;
        if((forUpgrade || forTransform || forPurge || AbstractDungeon.previousScreen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.SHOP) && canCancel)
            AbstractDungeon.overlayMenu.cancelButton.show(TEXT[1]);
        if(!canCancel)
            AbstractDungeon.overlayMenu.cancelButton.hide();
        if(AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
        {
            peekButton.hideInstantly();
            peekButton.show();
        }
        calculateScrollBounds();
    }

    public void open(CardGroup group, int numCards, String tipMsg, boolean forUpgrade, boolean forRitual)
    {
        open(group, numCards, tipMsg, forUpgrade, forRitual, true, false);
    }

    public void open(CardGroup group, int numCards, String tipMsg, boolean forUpgrade)
    {
        open(group, numCards, tipMsg, forUpgrade, false);
    }

    public void openConfirmationGrid(CardGroup group, String tipMsg)
    {
        targetGroup = group;
        callOnOpen();
        isJustForConfirming = true;
        this.tipMsg = tipMsg;
        AbstractDungeon.overlayMenu.cancelButton.hideInstantly();
        confirmButton.show();
        confirmButton.updateText(TEXT[0]);
        confirmButton.isDisabled = false;
        canCancel = false;
        if(group.size() <= 5)
            AbstractDungeon.dynamicBanner.appear(tipMsg);
    }

    private void callOnOpen()
    {
        if(Settings.isControllerMode)
        {
            Gdx.input.setCursorPosition(10, Settings.HEIGHT / 2);
            controllerCard = null;
        }
        anyNumber = false;
        forClarity = false;
        canCancel = false;
        forUpgrade = false;
        forTransform = false;
        forPurge = false;
        confirmScreenUp = false;
        isJustForConfirming = false;
        AbstractDungeon.overlayMenu.proceedButton.hide();
        controllerCard = null;
        hoveredCard = null;
        selectedCards.clear();
        AbstractDungeon.topPanel.unhoverHitboxes();
        cardSelectAmount = 0;
        currentDiffY = 0.0F;
        grabStartY = 0.0F;
        grabbedScreen = false;
        hideCards();
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.GRID;
        AbstractDungeon.overlayMenu.showBlackScreen(0.75F);
        confirmButton.hideInstantly();
        peekButton.hideInstantly();
        if(targetGroup.group.size() <= 5)
            drawStartY = (float)Settings.HEIGHT * 0.5F;
        else
            drawStartY = (float)Settings.HEIGHT * 0.66F;
    }

    public void reopen()
    {
        AbstractDungeon.overlayMenu.showBlackScreen(0.75F);
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.GRID;
        AbstractDungeon.topPanel.unhoverHitboxes();
        if(cancelWasOn && !isJustForConfirming && canCancel)
            AbstractDungeon.overlayMenu.cancelButton.show(cancelText);
        AbstractCard c;
        for(Iterator iterator = targetGroup.group.iterator(); iterator.hasNext(); c.lighten(false))
        {
            c = (AbstractCard)iterator.next();
            c.targetDrawScale = 0.75F;
            c.drawScale = 0.75F;
        }

        scrollBar.reset();
    }

    public void hide()
    {
        if(!AbstractDungeon.overlayMenu.cancelButton.isHidden)
        {
            cancelWasOn = true;
            cancelText = AbstractDungeon.overlayMenu.cancelButton.buttonText;
        }
    }

    private void updateScrolling()
    {
        if(PeekButton.isPeeking)
            return;
        if(isJustForConfirming && targetGroup.size() <= 5)
        {
            currentDiffY = -64F * Settings.scale;
            return;
        }
        int y = InputHelper.mY;
        boolean isDraggingScrollBar = scrollBar.update();
        if(!isDraggingScrollBar)
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
        if(prevDeckSize != targetGroup.size())
            calculateScrollBounds();
        resetScrolling();
        updateBarPosition();
    }

    private void calculateScrollBounds()
    {
        int scrollTmp = 0;
        if(targetGroup.size() > 10)
        {
            scrollTmp = targetGroup.size() / 5 - 2;
            if(targetGroup.size() % 5 != 0)
                scrollTmp++;
            scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT + (float)scrollTmp * padY;
        } else
        {
            scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT;
        }
        prevDeckSize = targetGroup.size();
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
        ArrayList cards = targetGroup.group;
        for(int i = 0; i < cards.size(); i++)
        {
            ((AbstractCard)cards.get(i)).setAngle(0.0F, true);
            int mod = i % 5;
            if(mod == 0 && i != 0)
                lineNum++;
            ((AbstractCard)cards.get(i)).lighten(true);
            ((AbstractCard)cards.get(i)).current_x = drawStartX + (float)mod * padX;
            ((AbstractCard)cards.get(i)).current_y = (drawStartY + currentDiffY) - (float)lineNum * padY - MathUtils.random(100F * Settings.scale, 200F * Settings.scale);
            ((AbstractCard)cards.get(i)).targetDrawScale = 0.75F;
            ((AbstractCard)cards.get(i)).drawScale = 0.75F;
        }

    }

    public void cancelUpgrade()
    {
        cardSelectAmount = 0;
        confirmScreenUp = false;
        confirmButton.hide();
        confirmButton.isDisabled = true;
        hoveredCard = null;
        upgradePreviewCard = null;
        if(Settings.isControllerMode && controllerCard != null)
        {
            hoveredCard = controllerCard;
            CInputHelper.setCursor(hoveredCard.hb);
        }
        if((forUpgrade || forTransform || forPurge || AbstractDungeon.previousScreen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.SHOP) && canCancel)
            AbstractDungeon.overlayMenu.cancelButton.show(TEXT[1]);
        int lineNum = 0;
        ArrayList cards = targetGroup.group;
        for(int i = 0; i < cards.size(); i++)
        {
            int mod = i % 5;
            if(mod == 0 && i != 0)
                lineNum++;
            ((AbstractCard)cards.get(i)).current_x = drawStartX + (float)mod * padX;
            ((AbstractCard)cards.get(i)).current_y = (drawStartY + currentDiffY) - (float)lineNum * padY;
        }

        tipMsg = lastTip;
    }

    public void render(SpriteBatch sb)
    {
        if(shouldShowScrollBar())
            scrollBar.render(sb);
        if(!PeekButton.isPeeking)
            if(hoveredCard != null)
            {
                if(AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
                    targetGroup.renderExceptOneCard(sb, hoveredCard);
                else
                    targetGroup.renderExceptOneCardShowBottled(sb, hoveredCard);
                hoveredCard.renderHoverShadow(sb);
                hoveredCard.render(sb);
                if(AbstractDungeon.getCurrRoom().phase != com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
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
                hoveredCard.renderCardTip(sb);
            } else
            if(AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
                targetGroup.render(sb);
            else
                targetGroup.renderShowBottled(sb);
        if(confirmScreenUp)
        {
            sb.setColor(new Color(0.0F, 0.0F, 0.0F, 0.8F));
            sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, (float)Settings.HEIGHT - 64F * Settings.scale);
            if(forTransform || forUpgrade)
            {
                renderArrows(sb);
                hoveredCard.current_x = (float)Settings.WIDTH * 0.36F;
                hoveredCard.current_y = (float)Settings.HEIGHT / 2.0F;
                hoveredCard.target_x = (float)Settings.WIDTH * 0.36F;
                hoveredCard.target_y = (float)Settings.HEIGHT / 2.0F;
                hoveredCard.render(sb);
                hoveredCard.updateHoverLogic();
                hoveredCard.renderCardTip(sb);
                upgradePreviewCard.current_x = (float)Settings.WIDTH * 0.63F;
                upgradePreviewCard.current_y = (float)Settings.HEIGHT / 2.0F;
                upgradePreviewCard.target_x = (float)Settings.WIDTH * 0.63F;
                upgradePreviewCard.target_y = (float)Settings.HEIGHT / 2.0F;
                upgradePreviewCard.render(sb);
                upgradePreviewCard.updateHoverLogic();
                upgradePreviewCard.renderCardTip(sb);
            } else
            {
                hoveredCard.current_x = (float)Settings.WIDTH / 2.0F;
                hoveredCard.current_y = (float)Settings.HEIGHT / 2.0F;
                hoveredCard.render(sb);
                hoveredCard.updateHoverLogic();
            }
        }
        if(!PeekButton.isPeeking && (forUpgrade || forTransform || forPurge || isJustForConfirming || anyNumber || forClarity))
            confirmButton.render(sb);
        peekButton.render(sb);
        if((!isJustForConfirming || targetGroup.size() > 5) && !PeekButton.isPeeking)
            FontHelper.renderDeckViewTip(sb, tipMsg, 96F * Settings.scale, Settings.CREAM_COLOR);
    }

    private void renderArrows(SpriteBatch sb)
    {
        float x = (float)Settings.WIDTH / 2.0F - 73F * Settings.scale - 32F;
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.UPGRADE_ARROW, x, (float)Settings.HEIGHT / 2.0F - 32F, 32F, 32F, 64F, 64F, arrowScale1 * Settings.scale, arrowScale1 * Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        x += 64F * Settings.scale;
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.UPGRADE_ARROW, x, (float)Settings.HEIGHT / 2.0F - 32F, 32F, 32F, 64F, 64F, arrowScale2 * Settings.scale, arrowScale2 * Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        x += 64F * Settings.scale;
        sb.draw(ImageMaster.UPGRADE_ARROW, x, (float)Settings.HEIGHT / 2.0F - 32F, 32F, 32F, 64F, 64F, arrowScale3 * Settings.scale, arrowScale3 * Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        arrowTimer += Gdx.graphics.getDeltaTime() * 2.0F;
        arrowScale1 = 0.8F + (MathUtils.cos(arrowTimer) + 1.0F) / 8F;
        arrowScale2 = 0.8F + (MathUtils.cos(arrowTimer - 0.8F) + 1.0F) / 8F;
        arrowScale3 = 0.8F + (MathUtils.cos(arrowTimer - 1.6F) + 1.0F) / 8F;
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
        return !confirmScreenUp && scrollUpperBound > SCROLL_BAR_THRESHOLD && !PeekButton.isPeeking;
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private static float drawStartX;
    private static float drawStartY;
    private static float padX;
    private static float padY;
    private static final int CARDS_PER_LINE = 5;
    private static final float SCROLL_BAR_THRESHOLD;
    private float grabStartY;
    private float currentDiffY;
    public ArrayList selectedCards;
    public CardGroup targetGroup;
    private AbstractCard hoveredCard;
    public AbstractCard upgradePreviewCard;
    private int numCards;
    private int cardSelectAmount;
    private float scrollLowerBound;
    private float scrollUpperBound;
    private boolean grabbedScreen;
    private boolean canCancel;
    public boolean forUpgrade;
    public boolean forTransform;
    public boolean forPurge;
    public boolean confirmScreenUp;
    public boolean isJustForConfirming;
    public GridSelectConfirmButton confirmButton;
    public PeekButton peekButton;
    private String tipMsg;
    private String lastTip;
    private float ritualAnimTimer;
    private static final float RITUAL_ANIM_INTERVAL = 0.1F;
    private int prevDeckSize;
    public boolean cancelWasOn;
    public boolean anyNumber;
    public boolean forClarity;
    public String cancelText;
    private ScrollBar scrollBar;
    private AbstractCard controllerCard;
    private static final int ARROW_W = 64;
    private float arrowScale1;
    private float arrowScale2;
    private float arrowScale3;
    private float arrowTimer;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("GridCardSelectScreen");
        TEXT = uiStrings.TEXT;
        SCROLL_BAR_THRESHOLD = 500F * Settings.scale;
    }
}
