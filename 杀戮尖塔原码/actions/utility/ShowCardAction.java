// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ShowCardAction.java

package com.megacrit.cardcrawl.actions.utility;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ShowCardAction extends AbstractGameAction
{

    public ShowCardAction(AbstractCard card)
    {
        this.card = null;
        setValues(AbstractDungeon.player, null, 1);
        this.card = card;
        duration = 0.2F;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.SPECIAL;
    }

    public void update()
    {
        if(duration == 0.2F)
        {
            if(AbstractDungeon.player.limbo.contains(card))
                AbstractDungeon.player.limbo.removeCard(card);
            AbstractDungeon.player.cardInUse = null;
        }
        tickDuration();
    }

    private AbstractCard card;
    private static final float PURGE_DURATION = 0.2F;
}
