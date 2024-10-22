// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EnergyManager.java

package com.megacrit.cardcrawl.core;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ConservePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.IceCream;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class EnergyManager
{

    public EnergyManager(int e)
    {
        energyMaster = e;
    }

    public void prep()
    {
        energy = energyMaster;
        EnergyPanel.totalCount = 0;
    }

    public void recharge()
    {
        if(AbstractDungeon.player.hasRelic("Ice Cream"))
        {
            if(EnergyPanel.totalCount > 0)
            {
                AbstractDungeon.player.getRelic("Ice Cream").flash();
                AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, AbstractDungeon.player.getRelic("Ice Cream")));
            }
            EnergyPanel.addEnergy(energy);
        } else
        if(AbstractDungeon.player.hasPower("Conserve"))
        {
            if(EnergyPanel.totalCount > 0)
                AbstractDungeon.actionManager.addToTop(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, "Conserve", 1));
            EnergyPanel.addEnergy(energy);
        } else
        {
            EnergyPanel.setEnergy(energy);
        }
        AbstractDungeon.actionManager.updateEnergyGain(energy);
    }

    public void use(int e)
    {
        EnergyPanel.useEnergy(e);
    }

    public int energy;
    public int energyMaster;
}
