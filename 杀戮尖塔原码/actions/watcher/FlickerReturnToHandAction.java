// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FlickerReturnToHandAction.java

package com.megacrit.cardcrawl.actions.watcher;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class FlickerReturnToHandAction extends AbstractGameAction
{

    public FlickerReturnToHandAction(AbstractCard card)
    {
        this.card = card;
        duration = Settings.ACTION_DUR_FASTER;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_FASTER && AbstractDungeon.player.discardPile.contains(card) && AbstractDungeon.player.hand.size() < 10)
            card.returnToHand = true;
        tickDuration();
    }

    private AbstractCard card;
}
