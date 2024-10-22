// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   InspirationAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class InspirationAction extends AbstractGameAction
{

    public InspirationAction(int drawAmt)
    {
        source = AbstractDungeon.player;
        duration = Settings.ACTION_DUR_FAST;
        amount = drawAmt;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_FAST && amount - AbstractDungeon.player.hand.size() > 0)
            addToTop(new DrawCardAction(source, amount - AbstractDungeon.player.hand.size()));
        tickDuration();
    }
}
