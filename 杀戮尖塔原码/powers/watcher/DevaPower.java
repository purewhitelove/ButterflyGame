// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DevaPower.java

package com.megacrit.cardcrawl.powers.watcher;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DevaPower extends AbstractPower
{

    public DevaPower(AbstractCreature owner)
    {
        energyGainAmount = 1;
        name = NAME;
        ID = "DevaForm";
        this.owner = owner;
        amount = 1;
        energyGainAmount = 1;
        updateDescription();
        loadRegion("deva2");
    }

    public void stackPower(int stackAmount)
    {
        super.stackPower(stackAmount);
        energyGainAmount++;
    }

    public void updateDescription()
    {
        if(energyGainAmount == 1)
            description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(DESCRIPTIONS[3]).append(amount).append(DESCRIPTIONS[4]).toString();
        else
            description = (new StringBuilder()).append(DESCRIPTIONS[1]).append(energyGainAmount).append(DESCRIPTIONS[2]).append(DESCRIPTIONS[3]).append(amount).append(DESCRIPTIONS[4]).toString();
    }

    public void onEnergyRecharge()
    {
        flash();
        AbstractDungeon.player.gainEnergy(energyGainAmount);
        energyGainAmount += amount;
        updateDescription();
    }

    public static final String POWER_ID = "DevaForm";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    private int energyGainAmount;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("DevaForm");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
