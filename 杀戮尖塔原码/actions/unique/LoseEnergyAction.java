// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LoseEnergyAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class LoseEnergyAction extends AbstractGameAction
{

    public LoseEnergyAction(int amount)
    {
        setValues(AbstractDungeon.player, AbstractDungeon.player, 0);
        energyLoss = amount;
        duration = Settings.ACTION_DUR_FAST;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_FAST)
            AbstractDungeon.player.loseEnergy(energyLoss);
        tickDuration();
    }

    private int energyLoss;
}
