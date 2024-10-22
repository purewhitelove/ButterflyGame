// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BlurPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class BlurPower extends AbstractPower
{

    public BlurPower(AbstractCreature owner, int amount)
    {
        name = NAME;
        ID = "Blur";
        this.owner = owner;
        this.amount = amount;
        description = DESCRIPTIONS[0];
        loadRegion("blur");
        isTurnBased = true;
    }

    public void updateDescription()
    {
        if(amount == 1)
            description = DESCRIPTIONS[0];
        else
            description = (new StringBuilder()).append(DESCRIPTIONS[1]).append(amount).append(DESCRIPTIONS[2]).toString();
    }

    public void atEndOfRound()
    {
        if(amount == 0)
            addToBot(new RemoveSpecificPowerAction(owner, owner, "Blur"));
        else
            addToBot(new ReducePowerAction(owner, owner, "Blur", 1));
    }

    public static final String POWER_ID = "Blur";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Blur");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
