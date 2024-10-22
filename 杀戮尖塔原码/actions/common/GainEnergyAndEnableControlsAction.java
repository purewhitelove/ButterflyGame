// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GainEnergyAndEnableControlsAction.java

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import java.util.ArrayList;
import java.util.Iterator;

public class GainEnergyAndEnableControlsAction extends AbstractGameAction
{

    public GainEnergyAndEnableControlsAction(int amount)
    {
        setValues(AbstractDungeon.player, AbstractDungeon.player, 0);
        energyGain = amount;
    }

    public void update()
    {
        if(duration == 0.5F)
        {
            AbstractDungeon.player.gainEnergy(energyGain);
            AbstractDungeon.actionManager.updateEnergyGain(energyGain);
            AbstractCard c;
            for(Iterator iterator = AbstractDungeon.player.hand.group.iterator(); iterator.hasNext(); c.triggerOnGainEnergy(energyGain, false))
                c = (AbstractCard)iterator.next();

            AbstractRelic r;
            for(Iterator iterator1 = AbstractDungeon.player.relics.iterator(); iterator1.hasNext(); r.onEnergyRecharge())
                r = (AbstractRelic)iterator1.next();

            AbstractPower p;
            for(Iterator iterator2 = AbstractDungeon.player.powers.iterator(); iterator2.hasNext(); p.onEnergyRecharge())
                p = (AbstractPower)iterator2.next();

            AbstractDungeon.actionManager.turnHasEnded = false;
        }
        tickDuration();
    }

    private int energyGain;
}
