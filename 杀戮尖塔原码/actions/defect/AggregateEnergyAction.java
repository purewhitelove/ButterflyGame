// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AggregateEnergyAction.java

package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AggregateEnergyAction extends AbstractGameAction
{

    public AggregateEnergyAction(int divideAmountNum)
    {
        duration = Settings.ACTION_DUR_FAST;
        divideAmount = divideAmountNum;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_FAST)
            AbstractDungeon.player.gainEnergy(AbstractDungeon.player.drawPile.size() / divideAmount);
        tickDuration();
    }

    private int divideAmount;
}
