// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EnergizedPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class EnergizedPower extends AbstractPower
{

    public EnergizedPower(AbstractCreature owner, int energyAmt)
    {
        name = NAME;
        ID = "Energized";
        this.owner = owner;
        amount = energyAmt;
        if(amount >= 999)
            amount = 999;
        updateDescription();
        loadRegion("energized_green");
    }

    public void stackPower(int stackAmount)
    {
        super.stackPower(stackAmount);
        if(amount >= 999)
            amount = 999;
    }

    public void updateDescription()
    {
        if(amount == 1)
            description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[1]).toString();
        else
            description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[2]).toString();
    }

    public void onEnergyRecharge()
    {
        flash();
        AbstractDungeon.player.gainEnergy(amount);
        addToBot(new RemoveSpecificPowerAction(owner, owner, "Energized"));
    }

    public static final String POWER_ID = "Energized";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Energized");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
