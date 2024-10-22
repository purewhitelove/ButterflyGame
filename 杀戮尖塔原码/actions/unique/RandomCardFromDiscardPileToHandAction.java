// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RandomCardFromDiscardPileToHandAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class RandomCardFromDiscardPileToHandAction extends AbstractGameAction
{

    public RandomCardFromDiscardPileToHandAction()
    {
        p = AbstractDungeon.player;
        setValues(p, AbstractDungeon.player, amount);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION;
    }

    public void update()
    {
        if(p.discardPile.size() > 0)
        {
            AbstractCard card = p.discardPile.getRandomCard(AbstractDungeon.cardRandomRng);
            p.hand.addToHand(card);
            card.lighten(false);
            p.discardPile.removeCard(card);
            p.hand.refreshHandLayout();
        }
        tickDuration();
        isDone = true;
    }

    private AbstractPlayer p;
}
