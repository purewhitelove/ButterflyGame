// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GainEnergyAction.java

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;
import java.util.Iterator;

public class GainEnergyAction extends AbstractGameAction
{

    public GainEnergyAction(int amount)
    {
        setValues(AbstractDungeon.player, AbstractDungeon.player, 0);
        duration = Settings.ACTION_DUR_FAST;
        energyGain = amount;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_FAST)
        {
            AbstractDungeon.player.gainEnergy(energyGain);
            AbstractDungeon.actionManager.updateEnergyGain(energyGain);
            AbstractCard c;
            for(Iterator iterator = AbstractDungeon.player.hand.group.iterator(); iterator.hasNext(); c.triggerOnGainEnergy(energyGain, true))
                c = (AbstractCard)iterator.next();

        }
        tickDuration();
    }

    private int energyGain;
}
