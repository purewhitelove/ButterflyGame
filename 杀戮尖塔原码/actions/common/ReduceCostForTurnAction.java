// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ReduceCostForTurnAction.java

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

public class ReduceCostForTurnAction extends AbstractGameAction
{

    public ReduceCostForTurnAction(AbstractCard card, int amt)
    {
        targetCard = card;
        amount = amt;
        startDuration = Settings.ACTION_DUR_FASTER;
        duration = startDuration;
    }

    public void update()
    {
        if(duration == startDuration && targetCard.costForTurn > 0)
            targetCard.setCostForTurn(targetCard.costForTurn - amount);
        tickDuration();
        if(Settings.FAST_MODE)
            isDone = true;
    }

    private AbstractCard targetCard;
}
