// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PhantasmalPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower, DoubleDamagePower

public class PhantasmalPower extends AbstractPower
{

    public PhantasmalPower(AbstractCreature owner, int amount)
    {
        name = NAME;
        ID = "Phantasmal";
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        loadRegion("phantasmal");
    }

    public void updateDescription()
    {
        if(amount == 1)
            description = DESCRIPTIONS[0];
        else
            description = (new StringBuilder()).append(DESCRIPTIONS[1]).append(amount).append(DESCRIPTIONS[2]).toString();
    }

    public void atStartOfTurn()
    {
        flash();
        addToBot(new ApplyPowerAction(owner, owner, new DoubleDamagePower(owner, 1, false), amount));
        addToBot(new ReducePowerAction(owner, owner, "Phantasmal", 1));
    }

    public static final String POWER_ID = "Phantasmal";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Phantasmal");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
