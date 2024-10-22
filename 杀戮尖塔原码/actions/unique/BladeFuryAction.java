// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BladeFuryAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BladeFuryAction extends AbstractGameAction
{

    public BladeFuryAction(boolean upgraded)
    {
        upgrade = upgraded;
    }

    public void update()
    {
        int theSize = AbstractDungeon.player.hand.size();
        if(upgrade)
        {
            AbstractCard s = (new Shiv()).makeCopy();
            s.upgrade();
            addToTop(new MakeTempCardInHandAction(s, theSize));
        } else
        {
            addToTop(new MakeTempCardInHandAction(new Shiv(), theSize));
        }
        addToTop(new DiscardAction(AbstractDungeon.player, AbstractDungeon.player, theSize, false));
        isDone = true;
    }

    private boolean upgrade;
}
