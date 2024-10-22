// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HandCardSelectScreen.java

package com.megacrit.cardcrawl.screens.select;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.*;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.buttons.CardSelectConfirmButton;
import com.megacrit.cardcrawl.ui.buttons.PeekButton;
import com.megacrit.cardcrawl.ui.panels.*;
import java.util.ArrayList;
import java.util.Iterator;

public class HandCardSelectScreen
{

    public HandCardSelectScreen()
    {
        selectedCards = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);
        upgradePreviewCard = null;
        wereCardsRetrieved = false;
        canPickZero = false;
        upTo = false;
        message = "";
        button = new CardSelectConfirmButton();
        peekButton = new PeekButton();
        anyNumber = false;
        forTransform = false;
        numSelected = 0;
        waitThenClose = false;
        waitToCloseTimer = 0.0F;
        arrowScale1 = 0.75F;
        arrowScale2 = 0.75F;
        arrowScale3 = 0.75F;
        arrowTimer = 0.0F;
    }

    public void update()
    {
        updateControllerInput();
        peekButton.update();
        if(!PeekButton.isPeeking)
        {
            updateHand();
            updateSelectedCards();
            if(waitThenClose)
            {
                waitToCloseTimer -= Gdx.graphics.getDeltaTime();
                if(waitToCloseTimer < 0.0F)
                {
                    waitThenClose = false;
                    AbstractDungeon.closeCurrentScreen();
                    if(forTransform && selectedCards.size() == 1)
                    {
                        if(AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
                            AbstractDungeon.srcTransformCard(selectedCards.getBottomCard());
                        else
                            AbstractDungeon.transformCard(selectedCards.getBottomCard());
                        selectedCards.group.clear();
                    }
                }
            }
            if(Settings.FAST_HAND_CONF && numCardsToSelect == 1 && selectedCards.size() == 1 && !canPickZero && !waitThenClose)
            {
                InputHelper.justClickedLeft = false;
                waitToCloseTimer = 0.25F;
                waitThenClose = true;
                return;
            }
            button.update();
            if(button.hb.clicked || CInputActionSet.proceed.isJustPressed() || InputActionSet.confirm.isJustPressed())
            {
                CInputActionSet.proceed.unpress();
                button.hb.clicked = false;
                if(canPickZero && selectedCards.size() == 0)
                {
                    InputHelper.justClickedLeft = false;
                    AbstractDungeon.closeCurrentScreen();
                    return;
                }
                if(anyNumber || upTo)
                {
                    InputHelper.justClickedLeft = false;
                    AbstractDungeon.closeCurrentScreen();
                    return;
                }
                if(selectedCards.size() == numCardsToSelect)
                {
                    InputHelper.justClickedLeft = false;
                    AbstractDungeon.closeCurrentScreen();
                    if(forTransform && selectedCards.size() == 1)
                    {
                        if(AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
                            AbstractDungeon.srcTransformCard(selectedCards.getBottomCard());
                        else
                            AbstractDungeon.transformCard(selectedCards.getBottomCard());
                        selectedCards.group.clear();
                    }
                    return;
                }
            }
        }
    }

    private void updateControllerInput()
    {
        if(!Settings.isControllerMode)
            return;
        boolean inHand = true;
        boolean anyHovered = false;
        int index = 0;
        int y = 0;
        Iterator iterator = selectedCards.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.hb.hovered)
            {
                anyHovered = true;
                inHand = false;
                break;
            }
            index++;
        } while(true);
        if(inHand)
        {
            index = 0;
            Iterator iterator1 = hand.group.iterator();
            do
            {
                if(!iterator1.hasNext())
                    break;
                AbstractCard c = (AbstractCard)iterator1.next();
                if(c == hoveredCard)
                {
                    anyHovered = true;
                    break;
                }
                index++;
            } while(true);
        }
        if(!anyHovered)
        {
            if(!hand.group.isEmpty())
            {
                setHoveredCard((AbstractCard)hand.group.get(0));
                Gdx.input.setCursorPosition((int)hoveredCard.hb.cX, Settings.HEIGHT - (int)hoveredCard.hb.cY);
            } else
            if(!selectedCards.isEmpty())
                Gdx.input.setCursorPosition((int)((AbstractCard)selectedCards.group.get(0)).hb.cX, Settings.HEIGHT - (int)((AbstractCard)selectedCards.group.get(0)).hb.cY);
        } else
        if(!inHand)
        {
            if((CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed()) && !hand.group.isEmpty())
            {
                index = 0;
                if(((AbstractCard)hand.group.get(index)).hb.cY < 0.0F)
                    y = 1;
                else
                    y = (int)((AbstractCard)hand.group.get(index)).hb.cY;
                Gdx.input.setCursorPosition((int)((AbstractCard)hand.group.get(index)).hb.cX, Settings.HEIGHT - y);
                setHoveredCard((AbstractCard)hand.group.get(index));
            } else
            if(CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed())
            {
                if(++index > selectedCards.size() - 1)
                    index = 0;
                Gdx.input.setCursorPosition((int)((AbstractCard)selectedCards.group.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractCard)selectedCards.group.get(index)).hb.cY);
                unhoverCard(hoveredCard);
            } else
            if(CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
            {
                if(--index < 0)
                    index = selectedCards.size() - 1;
                Gdx.input.setCursorPosition((int)((AbstractCard)selectedCards.group.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractCard)selectedCards.group.get(index)).hb.cY);
                unhoverCard(hoveredCard);
            } else
            if(CInputActionSet.select.isJustPressed())
            {
                CInputActionSet.select.unpress();
                if(((AbstractCard)selectedCards.group.get(index)).hb.hovered)
                {
                    AbstractCard tmp = (AbstractCard)selectedCards.group.get(index);
                    AbstractDungeon.player.hand.addToTop(tmp);
                    selectedCards.group.remove(tmp);
                    refreshSelectedCards();
                    updateMessage();
                    hand.refreshHandLayout();
                }
            }
        } else
        if((CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed()) && !selectedCards.isEmpty())
        {
            index = 0;
            if(((AbstractCard)selectedCards.group.get(index)).hb.cY < 0.0F)
                y = 1;
            else
                y = (int)((AbstractCard)selectedCards.group.get(index)).hb.cY;
            unhoverCard(hoveredCard);
            Gdx.input.setCursorPosition((int)((AbstractCard)selectedCards.group.get(index)).hb.cX, Settings.HEIGHT - y);
        } else
        if(hand.size() > 1 && (CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed()))
        {
            if(--index < 0)
                index = hand.size() - 1;
            unhoverCard(hoveredCard);
            setHoveredCard((AbstractCard)hand.group.get(index));
            Gdx.input.setCursorPosition((int)hoveredCard.hb.cX, Settings.HEIGHT - (int)hoveredCard.hb.cY);
        } else
        if(hand.size() > 1 && (CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed()))
        {
            if(++index > hand.size() - 1)
                index = 0;
            unhoverCard(hoveredCard);
            setHoveredCard((AbstractCard)hand.group.get(index));
            Gdx.input.setCursorPosition((int)hoveredCard.hb.cX, Settings.HEIGHT - (int)hoveredCard.hb.cY);
        } else
        if(hand.size() == 1 && hoveredCard == null)
            setHoveredCard((AbstractCard)hand.group.get(index));
    }

    private void unhoverCard(AbstractCard card)
    {
        if(card == null)
        {
            return;
        } else
        {
            card.targetDrawScale = 0.7F;
            card.hoverTimer = 0.25F;
            card.unhover();
            card = null;
            hand.refreshHandLayout();
            return;
        }
    }

    private void setHoveredCard(AbstractCard card)
    {
        hoveredCard = card;
        hoveredCard.current_y = HOVER_CARD_Y_POSITION;
        hoveredCard.target_y = HOVER_CARD_Y_POSITION;
        hoveredCard.drawScale = 1.0F;
        hoveredCard.targetDrawScale = 1.0F;
        hoveredCard.setAngle(0.0F, true);
        hand.hoverCardPush(hoveredCard);
    }

    private void updateHand()
    {
        if(!Settings.isControllerMode)
        {
            hoverCheck();
            unhoverCheck();
        }
        startDraggingCardCheck();
        hotkeyCheck();
    }

    private void refreshSelectedCards()
    {
        for(Iterator iterator = selectedCards.group.iterator(); iterator.hasNext();)
        {
            AbstractCard c = (AbstractCard)iterator.next();
            c.target_y = (float)Settings.HEIGHT / 2.0F + 160F * Settings.scale;
        }

        switch(selectedCards.size())
        {
        case 1: // '\001'
            if(forUpgrade)
                ((AbstractCard)selectedCards.group.get(0)).target_x = (float)Settings.WIDTH * 0.37F;
            else
                ((AbstractCard)selectedCards.group.get(0)).target_x = (float)Settings.WIDTH / 2.0F;
            break;

        case 2: // '\002'
            ((AbstractCard)selectedCards.group.get(0)).target_x = (float)Settings.WIDTH / 2.0F - 120F * Settings.scale;
            ((AbstractCard)selectedCards.group.get(1)).target_x = (float)Settings.WIDTH / 2.0F + 120F * Settings.scale;
            break;

        case 3: // '\003'
            ((AbstractCard)selectedCards.group.get(0)).target_x = (float)Settings.WIDTH / 2.0F - 240F * Settings.scale;
            ((AbstractCard)selectedCards.group.get(1)).target_x = (float)Settings.WIDTH / 2.0F;
            ((AbstractCard)selectedCards.group.get(2)).target_x = (float)Settings.WIDTH / 2.0F + 240F * Settings.scale;
            break;

        case 4: // '\004'
            ((AbstractCard)selectedCards.group.get(0)).target_x = (float)Settings.WIDTH / 2.0F - 360F * Settings.scale;
            ((AbstractCard)selectedCards.group.get(1)).target_x = (float)Settings.WIDTH / 2.0F - 120F * Settings.scale;
            ((AbstractCard)selectedCards.group.get(2)).target_x = (float)Settings.WIDTH / 2.0F + 120F * Settings.scale;
            ((AbstractCard)selectedCards.group.get(3)).target_x = (float)Settings.WIDTH / 2.0F + 360F * Settings.scale;
            break;

        case 5: // '\005'
            ((AbstractCard)selectedCards.group.get(0)).target_x = (float)Settings.WIDTH / 2.0F - 360F * Settings.scale;
            ((AbstractCard)selectedCards.group.get(1)).target_x = (float)Settings.WIDTH / 2.0F - 180F * Settings.scale;
            ((AbstractCard)selectedCards.group.get(2)).target_x = (float)Settings.WIDTH / 2.0F;
            ((AbstractCard)selectedCards.group.get(3)).target_x = (float)Settings.WIDTH / 2.0F + 180F * Settings.scale;
            ((AbstractCard)selectedCards.group.get(4)).target_x = (float)Settings.WIDTH / 2.0F + 360F * Settings.scale;
            break;

        case 6: // '\006'
            ((AbstractCard)selectedCards.group.get(0)).target_x = (float)Settings.WIDTH / 2.0F - 450F * Settings.scale;
            ((AbstractCard)selectedCards.group.get(1)).target_x = (float)Settings.WIDTH / 2.0F - 270F * Settings.scale;
            ((AbstractCard)selectedCards.group.get(2)).target_x = (float)Settings.WIDTH / 2.0F - 90F * Settings.scale;
            ((AbstractCard)selectedCards.group.get(3)).target_x = (float)Settings.WIDTH / 2.0F + 90F * Settings.scale;
            ((AbstractCard)selectedCards.group.get(4)).target_x = (float)Settings.WIDTH / 2.0F + 270F * Settings.scale;
            ((AbstractCard)selectedCards.group.get(5)).target_x = (float)Settings.WIDTH / 2.0F + 450F * Settings.scale;
            break;

        case 7: // '\007'
            ((AbstractCard)selectedCards.group.get(0)).target_x = (float)Settings.WIDTH / 2.0F - 540F * Settings.scale;
            ((AbstractCard)selectedCards.group.get(1)).target_x = (float)Settings.WIDTH / 2.0F - 360F * Settings.scale;
            ((AbstractCard)selectedCards.group.get(2)).target_x = (float)Settings.WIDTH / 2.0F - 180F * Settings.scale;
            ((AbstractCard)selectedCards.group.get(3)).target_x = (float)Settings.WIDTH / 2.0F;
            ((AbstractCard)selectedCards.group.get(4)).target_x = (float)Settings.WIDTH / 2.0F + 180F * Settings.scale;
            ((AbstractCard)selectedCards.group.get(5)).target_x = (float)Settings.WIDTH / 2.0F + 360F * Settings.scale;
            ((AbstractCard)selectedCards.group.get(6)).target_x = (float)Settings.WIDTH / 2.0F + 540F * Settings.scale;
            break;

        case 8: // '\b'
            ((AbstractCard)selectedCards.group.get(0)).target_x = (float)Settings.WIDTH / 2.0F - 630F * Settings.scale;
            ((AbstractCard)selectedCards.group.get(1)).target_x = (float)Settings.WIDTH / 2.0F - 450F * Settings.scale;
            ((AbstractCard)selectedCards.group.get(2)).target_x = (float)Settings.WIDTH / 2.0F - 270F * Settings.scale;
            ((AbstractCard)selectedCards.group.get(3)).target_x = (float)Settings.WIDTH / 2.0F - 90F * Settings.scale;
            ((AbstractCard)selectedCards.group.get(4)).target_x = (float)Settings.WIDTH / 2.0F + 90F * Settings.scale;
            ((AbstractCard)selectedCards.group.get(5)).target_x = (float)Settings.WIDTH / 2.0F + 270F * Settings.scale;
            ((AbstractCard)selectedCards.group.get(6)).target_x = (float)Settings.WIDTH / 2.0F + 450F * Settings.scale;
            ((AbstractCard)selectedCards.group.get(7)).target_x = (float)Settings.WIDTH / 2.0F + 630F * Settings.scale;
            break;

        case 9: // '\t'
            ((AbstractCard)selectedCards.group.get(0)).target_x = (float)Settings.WIDTH / 2.0F - 720F * Settings.scale;
            ((AbstractCard)selectedCards.group.get(1)).target_x = (float)Settings.WIDTH / 2.0F - 540F * Settings.scale;
            ((AbstractCard)selectedCards.group.get(2)).target_x = (float)Settings.WIDTH / 2.0F - 360F * Settings.scale;
            ((AbstractCard)selectedCards.group.get(3)).target_x = (float)Settings.WIDTH / 2.0F - 180F * Settings.scale;
            ((AbstractCard)selectedCards.group.get(4)).target_x = (float)Settings.WIDTH / 2.0F;
            ((AbstractCard)selectedCards.group.get(5)).target_x = (float)Settings.WIDTH / 2.0F + 180F * Settings.scale;
            ((AbstractCard)selectedCards.group.get(6)).target_x = (float)Settings.WIDTH / 2.0F + 360F * Settings.scale;
            ((AbstractCard)selectedCards.group.get(7)).target_x = (float)Settings.WIDTH / 2.0F + 540F * Settings.scale;
            ((AbstractCard)selectedCards.group.get(8)).target_x = (float)Settings.WIDTH / 2.0F + 720F * Settings.scale;
            break;

        case 10: // '\n'
            ((AbstractCard)selectedCards.group.get(0)).target_x = (float)Settings.WIDTH / 2.0F - 810F * Settings.scale;
            ((AbstractCard)selectedCards.group.get(1)).target_x = (float)Settings.WIDTH / 2.0F - 630F * Settings.scale;
            ((AbstractCard)selectedCards.group.get(2)).target_x = (float)Settings.WIDTH / 2.0F - 450F * Settings.scale;
            ((AbstractCard)selectedCards.group.get(3)).target_x = (float)Settings.WIDTH / 2.0F - 270F * Settings.scale;
            ((AbstractCard)selectedCards.group.get(4)).target_x = (float)Settings.WIDTH / 2.0F - 90F * Settings.scale;
            ((AbstractCard)selectedCards.group.get(5)).target_x = (float)Settings.WIDTH / 2.0F + 90F * Settings.scale;
            ((AbstractCard)selectedCards.group.get(6)).target_x = (float)Settings.WIDTH / 2.0F + 270F * Settings.scale;
            ((AbstractCard)selectedCards.group.get(7)).target_x = (float)Settings.WIDTH / 2.0F + 450F * Settings.scale;
            ((AbstractCard)selectedCards.group.get(8)).target_x = (float)Settings.WIDTH / 2.0F + 630F * Settings.scale;
            ((AbstractCard)selectedCards.group.get(9)).target_x = (float)Settings.WIDTH / 2.0F + 810F * Settings.scale;
            break;
        }
        if(upTo)
            button.enable();
        else
        if(selectedCards.size() == numCardsToSelect)
            button.enable();
        else
        if(selectedCards.size() > 1 && anyNumber && !canPickZero)
            button.enable();
        else
        if(selectedCards.size() != numCardsToSelect && !anyNumber)
            button.disable();
        else
        if(anyNumber && canPickZero)
            button.enable();
    }

    private void hoverCheck()
    {
        if(hoveredCard == null)
        {
            hoveredCard = hand.getHoveredCard();
            if(hoveredCard != null)
            {
                hoveredCard.current_y = HOVER_CARD_Y_POSITION;
                hoveredCard.target_y = HOVER_CARD_Y_POSITION;
                hoveredCard.drawScale = 1.0F;
                hoveredCard.targetDrawScale = 1.0F;
                hoveredCard.setAngle(0.0F, true);
                hand.hoverCardPush(hoveredCard);
            }
        }
    }

    private void unhoverCheck()
    {
        if(hoveredCard != null && !hoveredCard.isHoveredInHand(1.0F))
        {
            hoveredCard.targetDrawScale = 0.7F;
            hoveredCard.hoverTimer = 0.25F;
            hoveredCard.unhover();
            hoveredCard = null;
            hand.refreshHandLayout();
        }
    }

    private void startDraggingCardCheck()
    {
        if(hoveredCard != null && (InputHelper.justClickedLeft || CInputActionSet.select.isJustPressed()))
        {
            CInputActionSet.select.unpress();
            if(!Settings.FAST_HAND_CONF || numCardsToSelect != 1 || selectedCards.size() != 1)
                selectHoveredCard();
        }
    }

    private void selectHoveredCard()
    {
        if(numCardsToSelect > selectedCards.group.size())
        {
            InputHelper.justClickedLeft = false;
            CardCrawlGame.sound.play("CARD_OBTAIN");
            hand.removeCard(hoveredCard);
            hand.refreshHandLayout();
            hoveredCard.setAngle(0.0F, false);
            selectedCards.addToTop(hoveredCard);
            refreshSelectedCards();
            hoveredCard = null;
            updateMessage();
        } else
        if(numCardsToSelect == 1 && selectedCards.group.size() == 1)
        {
            InputHelper.justClickedLeft = false;
            CardCrawlGame.sound.play("CARD_OBTAIN");
            hand.removeCard(hoveredCard);
            hoveredCard.setAngle(0.0F, false);
            selectedCards.addToBottom(hoveredCard);
            refreshSelectedCards();
            hoveredCard = null;
            AbstractDungeon.player.hand.addToTop(selectedCards.getTopCard());
            selectedCards.removeTopCard();
            refreshSelectedCards();
            hand.refreshHandLayout();
            if(forUpgrade && selectedCards.size() == 1)
            {
                upgradePreviewCard = ((AbstractCard)selectedCards.group.get(0)).makeStatEquivalentCopy();
                upgradePreviewCard.upgrade();
                upgradePreviewCard.displayUpgrades();
                upgradePreviewCard.drawScale = 0.75F;
            }
        }
        InputHelper.moveCursorToNeutralPosition();
    }

    private void hotkeyCheck()
    {
        AbstractCard hotkeyCard = InputHelper.getCardSelectedByHotkey(hand);
        if(hotkeyCard != null)
        {
            hoveredCard = hotkeyCard;
            hoveredCard.setAngle(0.0F, false);
            selectHoveredCard();
        }
    }

    private void updateSelectedCards()
    {
        selectedCards.update();
        Iterator i = selectedCards.group.iterator();
        do
        {
            if(!i.hasNext())
                break;
            AbstractCard e = (AbstractCard)i.next();
            e.current_x = MathHelper.cardLerpSnap(e.current_x, e.target_x);
            e.current_y = MathHelper.cardLerpSnap(e.current_y, e.target_y);
            e.hb.update();
            if(selectedCards.group.size() >= 5)
            {
                e.targetDrawScale = 0.5F;
                if(Math.abs(e.current_x - e.target_x) < MIN_HOVER_DIST && e.hb.hovered)
                    e.targetDrawScale = 0.66F;
            } else
            {
                e.targetDrawScale = 0.66F;
                if(forUpgrade)
                    e.targetDrawScale = 0.75F;
                if(Math.abs(e.current_x - e.target_x) < MIN_HOVER_DIST && e.hb.hovered)
                    if(forUpgrade)
                        e.targetDrawScale = 0.85F;
                    else
                        e.targetDrawScale = 0.75F;
            }
            if(waitThenClose || Math.abs(e.current_x - e.target_x) >= MIN_HOVER_DIST || !e.hb.hovered || !InputHelper.justClickedLeft && !CInputActionSet.select.isJustPressed())
                continue;
            InputHelper.justClickedLeft = false;
            AbstractDungeon.player.hand.addToTop(e);
            i.remove();
            refreshSelectedCards();
            updateMessage();
            if(Settings.isControllerMode)
                hand.refreshHandLayout();
            break;
        } while(true);
        if(selectedCards.isEmpty() && !canPickZero)
            button.disable();
    }

    private void updateMessage()
    {
        if(selectedCards.group.size() == 0)
        {
            upgradePreviewCard = null;
            if(selectedCards.group.size() == numCardsToSelect)
            {
                if(numCardsToSelect == 1)
                    message = (new StringBuilder()).append(TEXT[0]).append(selectionReason).toString();
                else
                    message = (new StringBuilder()).append(TEXT[1]).append(selectionReason).toString();
            } else
            if(numCardsToSelect != 1)
            {
                if(!anyNumber)
                    message = (new StringBuilder()).append(TEXT[2]).append(numCardsToSelect).append(TEXT[3]).append(selectionReason).toString();
                else
                    message = (new StringBuilder()).append(TEXT[4]).append(selectionReason).toString();
            } else
            {
                message = (new StringBuilder()).append(TEXT[5]).append(selectionReason).toString();
            }
        } else
        if(selectedCards.group.size() != 0)
        {
            int numLeft = numCardsToSelect - selectedCards.group.size();
            if(selectedCards.group.size() == numCardsToSelect)
            {
                if(numCardsToSelect == 1)
                    message = (new StringBuilder()).append(TEXT[0]).append(selectionReason).toString();
                else
                    message = (new StringBuilder()).append(TEXT[1]).append(selectionReason).toString();
                if(forUpgrade && selectedCards.size() == 1)
                {
                    if(upgradePreviewCard == null)
                        upgradePreviewCard = ((AbstractCard)selectedCards.group.get(0)).makeStatEquivalentCopy();
                    upgradePreviewCard.upgrade();
                    upgradePreviewCard.displayUpgrades();
                    upgradePreviewCard.drawScale = 0.75F;
                    upgradePreviewCard.targetDrawScale = 0.75F;
                } else
                {
                    upgradePreviewCard = null;
                }
            } else
            if(numLeft != 1)
            {
                upgradePreviewCard = null;
                if(!anyNumber)
                    message = (new StringBuilder()).append(TEXT[2]).append(numLeft).append(TEXT[3]).append(selectionReason).toString();
                else
                    message = (new StringBuilder()).append(TEXT[4]).append(selectionReason).toString();
            } else
            {
                upgradePreviewCard = null;
                message = (new StringBuilder()).append(TEXT[5]).append(selectionReason).toString();
            }
        }
    }

    public void reopen()
    {
        AbstractDungeon.overlayMenu.showBlackScreen(0.75F);
    }

    public void open(String msg, int amount, boolean anyNumber, boolean canPickZero, boolean forTransform, boolean forUpgrade, boolean upTo)
    {
        prep();
        numCardsToSelect = amount;
        this.canPickZero = canPickZero;
        this.anyNumber = anyNumber;
        selectionReason = msg;
        this.upTo = upTo;
        if(canPickZero)
        {
            button.isDisabled = true;
            button.enable();
        } else
        {
            button.isDisabled = false;
            button.disable();
        }
        this.forTransform = forTransform;
        this.forUpgrade = forUpgrade;
        if(!forUpgrade)
            upgradePreviewCard = null;
        button.hideInstantly();
        button.show();
        peekButton.hideInstantly();
        peekButton.show();
        updateMessage();
    }

    public void open(String msg, int amount, boolean anyNumber, boolean canPickZero, boolean forTransform, boolean forUpgrade)
    {
        open(msg, amount, anyNumber, canPickZero, forTransform, forUpgrade, false);
    }

    public void open(String msg, int amount, boolean anyNumber, boolean canPickZero, boolean forTransform)
    {
        open(msg, amount, anyNumber, canPickZero, forTransform, false);
    }

    public void open(String msg, int amount, boolean anyNumber, boolean canPickZero)
    {
        prep();
        numCardsToSelect = amount;
        this.canPickZero = canPickZero;
        this.anyNumber = anyNumber;
        selectionReason = msg;
        if(canPickZero)
        {
            button.isDisabled = true;
            button.enable();
        } else
        {
            button.isDisabled = false;
            button.disable();
        }
        button.hideInstantly();
        button.show();
        if(AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
        {
            peekButton.hideInstantly();
            peekButton.show();
        }
        updateMessage();
    }

    public void open(String msg, int amount, boolean anyNumber)
    {
        open(msg, amount, anyNumber, false);
    }

    public void render(SpriteBatch sb)
    {
        AbstractDungeon.player.hand.render(sb);
        AbstractDungeon.overlayMenu.energyPanel.render(sb);
        if(!PeekButton.isPeeking)
        {
            FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, message, Settings.WIDTH / 2, (float)Settings.HEIGHT - 180F * Settings.scale, Settings.CREAM_COLOR);
            if(!Settings.FAST_HAND_CONF || numCardsToSelect != 1 || canPickZero)
                button.render(sb);
            selectedCards.render(sb);
            if(forUpgrade && upgradePreviewCard != null)
            {
                renderArrows(sb);
                upgradePreviewCard.current_x = (float)Settings.WIDTH * 0.63F;
                upgradePreviewCard.current_y = (float)Settings.HEIGHT / 2.0F + 160F * Settings.scale;
                upgradePreviewCard.target_x = (float)Settings.WIDTH * 0.63F;
                upgradePreviewCard.target_y = (float)Settings.HEIGHT / 2.0F + 160F * Settings.scale;
                upgradePreviewCard.displayUpgrades();
                boolean t1 = upgradePreviewCard.isDamageModified;
                boolean t2 = upgradePreviewCard.isBlockModified;
                boolean t3 = upgradePreviewCard.isMagicNumberModified;
                boolean t4 = upgradePreviewCard.isCostModified;
                upgradePreviewCard.applyPowers();
                if(!upgradePreviewCard.isDamageModified && t1)
                    upgradePreviewCard.isDamageModified = true;
                if(!upgradePreviewCard.isBlockModified && t2)
                    upgradePreviewCard.isBlockModified = true;
                if(!upgradePreviewCard.isMagicNumberModified && t3)
                    upgradePreviewCard.isMagicNumberModified = true;
                if(!upgradePreviewCard.isCostModified && t4)
                    upgradePreviewCard.isCostModified = true;
                upgradePreviewCard.render(sb);
                upgradePreviewCard.updateHoverLogic();
                upgradePreviewCard.renderCardTip(sb);
            }
        }
        peekButton.render(sb);
        AbstractDungeon.overlayMenu.combatDeckPanel.render(sb);
        AbstractDungeon.overlayMenu.discardPilePanel.render(sb);
        AbstractDungeon.overlayMenu.exhaustPanel.render(sb);
    }

    private void renderArrows(SpriteBatch sb)
    {
        float x = (float)Settings.WIDTH / 2.0F - 96F * Settings.scale - 10F * Settings.scale;
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.UPGRADE_ARROW, x, (float)Settings.HEIGHT / 2.0F + 120F * Settings.scale, 32F, 32F, 64F, 64F, arrowScale1 * Settings.scale, arrowScale1 * Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        x += 64F * Settings.scale;
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.UPGRADE_ARROW, x, (float)Settings.HEIGHT / 2.0F + 120F * Settings.scale, 32F, 32F, 64F, 64F, arrowScale2 * Settings.scale, arrowScale2 * Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        x += 64F * Settings.scale;
        sb.draw(ImageMaster.UPGRADE_ARROW, x, (float)Settings.HEIGHT / 2.0F + 120F * Settings.scale, 32F, 32F, 64F, 64F, arrowScale3 * Settings.scale, arrowScale3 * Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        arrowTimer += Gdx.graphics.getDeltaTime() * 2.0F;
        arrowScale1 = 0.8F + (MathUtils.cos(arrowTimer) + 1.0F) / 8F;
        arrowScale2 = 0.8F + (MathUtils.cos(arrowTimer - 0.8F) + 1.0F) / 8F;
        arrowScale3 = 0.8F + (MathUtils.cos(arrowTimer - 1.6F) + 1.0F) / 8F;
    }

    private void prep()
    {
        AbstractDungeon.player.resetControllerValues();
        AbstractCard c;
        for(Iterator iterator = AbstractDungeon.player.hand.group.iterator(); iterator.hasNext(); c.unhover())
            c = (AbstractCard)iterator.next();

        upTo = false;
        forTransform = false;
        forUpgrade = false;
        canPickZero = false;
        AbstractDungeon.topPanel.unhoverHitboxes();
        AbstractDungeon.actionManager.cleanCardQueue();
        hand = AbstractDungeon.player.hand;
        AbstractDungeon.player.releaseCard();
        AbstractDungeon.getMonsters().hoveredMonster = null;
        waitThenClose = false;
        waitToCloseTimer = 0.0F;
        selectedCards.clear();
        hoveredCard = null;
        wereCardsRetrieved = false;
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.HAND_SELECT;
        AbstractDungeon.player.hand.stopGlowing();
        AbstractDungeon.player.hand.refreshHandLayout();
        AbstractDungeon.overlayMenu.showBlackScreen(0.75F);
        numSelected = 0;
        if(Settings.isControllerMode)
            Gdx.input.setCursorPosition((int)((AbstractCard)hand.group.get(0)).hb.cX, (int)((AbstractCard)hand.group.get(0)).hb.cY);
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    public int numCardsToSelect;
    public CardGroup selectedCards;
    public AbstractCard hoveredCard;
    public AbstractCard upgradePreviewCard;
    public String selectionReason;
    public boolean wereCardsRetrieved;
    public boolean canPickZero;
    public boolean upTo;
    private String message;
    public CardSelectConfirmButton button;
    private PeekButton peekButton;
    private boolean anyNumber;
    private boolean forTransform;
    private boolean forUpgrade;
    public int numSelected;
    public static final float MIN_HOVER_DIST;
    private boolean waitThenClose;
    private float waitToCloseTimer;
    private CardGroup hand;
    public static final float HOVER_CARD_Y_POSITION;
    private static final int ARROW_W = 64;
    private float arrowScale1;
    private float arrowScale2;
    private float arrowScale3;
    private float arrowTimer;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("HandCardSelectScreen");
        TEXT = uiStrings.TEXT;
        MIN_HOVER_DIST = 64F * Settings.scale;
        HOVER_CARD_Y_POSITION = 210F * Settings.scale;
    }
}
