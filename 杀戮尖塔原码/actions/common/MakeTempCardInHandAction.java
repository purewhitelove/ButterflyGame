// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MakeTempCardInHandAction.java

package com.megacrit.cardcrawl.actions.common;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.MasterRealityPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import java.util.ArrayList;

public class MakeTempCardInHandAction extends AbstractGameAction
{

    public MakeTempCardInHandAction(AbstractCard card, boolean isOtherCardInCenter)
    {
        this.isOtherCardInCenter = true;
        sameUUID = false;
        UnlockTracker.markCardAsSeen(card.cardID);
        amount = 1;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION;
        c = card;
        if(c.type != com.megacrit.cardcrawl.cards.AbstractCard.CardType.CURSE && c.type != com.megacrit.cardcrawl.cards.AbstractCard.CardType.STATUS && AbstractDungeon.player.hasPower("MasterRealityPower"))
            c.upgrade();
        this.isOtherCardInCenter = isOtherCardInCenter;
    }

    public MakeTempCardInHandAction(AbstractCard card)
    {
        this(card, 1);
    }

    public MakeTempCardInHandAction(AbstractCard card, int amount)
    {
        isOtherCardInCenter = true;
        sameUUID = false;
        UnlockTracker.markCardAsSeen(card.cardID);
        this.amount = amount;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION;
        c = card;
        if(c.type != com.megacrit.cardcrawl.cards.AbstractCard.CardType.CURSE && c.type != com.megacrit.cardcrawl.cards.AbstractCard.CardType.STATUS && AbstractDungeon.player.hasPower("MasterRealityPower"))
            c.upgrade();
    }

    public MakeTempCardInHandAction(AbstractCard card, int amount, boolean isOtherCardInCenter)
    {
        this(card, amount);
        this.isOtherCardInCenter = isOtherCardInCenter;
    }

    public MakeTempCardInHandAction(AbstractCard card, boolean isOtherCardInCenter, boolean sameUUID)
    {
        this(card, 1);
        this.isOtherCardInCenter = isOtherCardInCenter;
        this.sameUUID = sameUUID;
    }

    public void update()
    {
        if(amount == 0)
        {
            isDone = true;
            return;
        }
        int discardAmount = 0;
        int handAmount = amount;
        if(amount + AbstractDungeon.player.hand.size() > 10)
        {
            AbstractDungeon.player.createHandIsFullDialog();
            discardAmount = (amount + AbstractDungeon.player.hand.size()) - 10;
            handAmount -= discardAmount;
        }
        addToHand(handAmount);
        addToDiscard(discardAmount);
        if(amount > 0)
            addToTop(new WaitAction(0.8F));
        isDone = true;
    }

    private void addToHand(int handAmt)
    {
        switch(amount)
        {
        case 0: // '\0'
            break;

        case 1: // '\001'
            if(handAmt != 1)
                break;
            if(isOtherCardInCenter)
                AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(makeNewCard(), (float)Settings.WIDTH / 2.0F - (PADDING + AbstractCard.IMG_WIDTH), (float)Settings.HEIGHT / 2.0F));
            else
                AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(makeNewCard()));
            break;

        case 2: // '\002'
            if(handAmt == 1)
            {
                AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(makeNewCard(), (float)Settings.WIDTH / 2.0F - (PADDING + AbstractCard.IMG_WIDTH * 0.5F), (float)Settings.HEIGHT / 2.0F));
                break;
            }
            if(handAmt == 2)
            {
                AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(makeNewCard(), (float)Settings.WIDTH / 2.0F + (PADDING + AbstractCard.IMG_WIDTH), (float)Settings.HEIGHT / 2.0F));
                AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(makeNewCard(), (float)Settings.WIDTH / 2.0F - (PADDING + AbstractCard.IMG_WIDTH), (float)Settings.HEIGHT / 2.0F));
            }
            break;

        case 3: // '\003'
            if(handAmt == 1)
            {
                AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(makeNewCard(), (float)Settings.WIDTH / 2.0F - (PADDING + AbstractCard.IMG_WIDTH), (float)Settings.HEIGHT / 2.0F));
                break;
            }
            if(handAmt == 2)
            {
                AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(makeNewCard(), (float)Settings.WIDTH / 2.0F + (PADDING + AbstractCard.IMG_WIDTH), (float)Settings.HEIGHT / 2.0F));
                AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(makeNewCard(), (float)Settings.WIDTH / 2.0F - (PADDING + AbstractCard.IMG_WIDTH), (float)Settings.HEIGHT / 2.0F));
                break;
            }
            if(handAmt != 3)
                break;
            for(int i = 0; i < amount; i++)
                AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(makeNewCard()));

            break;

        default:
            for(int i = 0; i < handAmt; i++)
                AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(makeNewCard(), MathUtils.random((float)Settings.WIDTH * 0.2F, (float)Settings.WIDTH * 0.8F), MathUtils.random((float)Settings.HEIGHT * 0.3F, (float)Settings.HEIGHT * 0.7F)));

            break;
        }
    }

    private void addToDiscard(int discardAmt)
    {
        switch(amount)
        {
        case 0: // '\0'
            break;

        case 1: // '\001'
            if(discardAmt == 1)
                AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(makeNewCard(), (float)Settings.WIDTH / 2.0F + (PADDING + AbstractCard.IMG_WIDTH), (float)Settings.HEIGHT / 2.0F));
            break;

        case 2: // '\002'
            if(discardAmt == 1)
            {
                AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(makeNewCard(), (float)Settings.WIDTH * 0.5F - (PADDING + AbstractCard.IMG_WIDTH * 0.5F), (float)Settings.HEIGHT * 0.5F));
                break;
            }
            if(discardAmt == 2)
            {
                AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(makeNewCard(), (float)Settings.WIDTH * 0.5F - (PADDING + AbstractCard.IMG_WIDTH * 0.5F), (float)Settings.HEIGHT * 0.5F));
                AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(makeNewCard(), (float)Settings.WIDTH * 0.5F + (PADDING + AbstractCard.IMG_WIDTH * 0.5F), (float)Settings.HEIGHT * 0.5F));
            }
            break;

        case 3: // '\003'
            if(discardAmt == 1)
            {
                AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(makeNewCard(), (float)Settings.WIDTH * 0.5F + (PADDING + AbstractCard.IMG_WIDTH), (float)Settings.HEIGHT * 0.5F));
                break;
            }
            if(discardAmt == 2)
            {
                AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(makeNewCard(), (float)Settings.WIDTH * 0.5F, (float)Settings.HEIGHT * 0.5F));
                AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(makeNewCard(), (float)Settings.WIDTH * 0.5F + (PADDING + AbstractCard.IMG_WIDTH), (float)Settings.HEIGHT * 0.5F));
                break;
            }
            if(discardAmt == 3)
            {
                AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(makeNewCard(), (float)Settings.WIDTH * 0.5F, (float)Settings.HEIGHT * 0.5F));
                AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(makeNewCard(), (float)Settings.WIDTH * 0.5F - (PADDING + AbstractCard.IMG_WIDTH), (float)Settings.HEIGHT * 0.5F));
                AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(makeNewCard(), (float)Settings.WIDTH * 0.5F + (PADDING + AbstractCard.IMG_WIDTH), (float)Settings.HEIGHT * 0.5F));
            }
            break;

        default:
            for(int i = 0; i < discardAmt; i++)
                AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(makeNewCard(), MathUtils.random((float)Settings.WIDTH * 0.2F, (float)Settings.WIDTH * 0.8F), MathUtils.random((float)Settings.HEIGHT * 0.3F, (float)Settings.HEIGHT * 0.7F)));

            break;
        }
    }

    private AbstractCard makeNewCard()
    {
        if(sameUUID)
            return c.makeSameInstanceOf();
        else
            return c.makeStatEquivalentCopy();
    }

    private AbstractCard c;
    private static final float PADDING;
    private boolean isOtherCardInCenter;
    private boolean sameUUID;

    static 
    {
        PADDING = 25F * Settings.scale;
    }
}
