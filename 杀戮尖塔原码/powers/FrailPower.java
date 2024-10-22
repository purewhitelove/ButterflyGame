// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FrailPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class FrailPower extends AbstractPower
{

    public FrailPower(AbstractCreature owner, int amount, boolean isSourceMonster)
    {
        justApplied = false;
        name = NAME;
        ID = "Frail";
        this.owner = owner;
        this.amount = amount;
        priority = 10;
        updateDescription();
        loadRegion("frail");
        if(isSourceMonster)
            justApplied = true;
        type = AbstractPower.PowerType.DEBUFF;
        isTurnBased = true;
    }

    public void atEndOfRound()
    {
        if(justApplied)
        {
            justApplied = false;
            return;
        }
        if(amount == 0)
            addToBot(new RemoveSpecificPowerAction(owner, owner, "Frail"));
        else
            addToBot(new ReducePowerAction(owner, owner, "Frail", 1));
    }

    public void updateDescription()
    {
        if(amount == 1)
            description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[1]).toString();
        else
            description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[2]).toString();
    }

    public float modifyBlock(float blockAmount)
    {
        return blockAmount * 0.75F;
    }

    public static final String POWER_ID = "Frail";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    private boolean justApplied;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Frail");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
