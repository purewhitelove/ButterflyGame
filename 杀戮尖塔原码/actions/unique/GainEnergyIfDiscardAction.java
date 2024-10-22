// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GainEnergyIfDiscardAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;
import java.util.Iterator;

public class GainEnergyIfDiscardAction extends AbstractGameAction
{

    public GainEnergyIfDiscardAction(int amount)
    {
        setValues(AbstractDungeon.player, AbstractDungeon.player, 0);
        duration = Settings.ACTION_DUR_FAST;
        energyGain = amount;
    }

    public void update()
    {
        if(GameActionManager.totalDiscardedThisTurn > 0)
        {
            AbstractDungeon.player.gainEnergy(energyGain);
            AbstractDungeon.actionManager.updateEnergyGain(energyGain);
            AbstractCard c;
            for(Iterator iterator = AbstractDungeon.player.hand.group.iterator(); iterator.hasNext(); c.triggerOnGainEnergy(energyGain, true))
                c = (AbstractCard)iterator.next();

        }
        isDone = true;
    }

    private int energyGain;
}
