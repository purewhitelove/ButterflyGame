// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LockOnPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class LockOnPower extends AbstractPower
{

    public LockOnPower(AbstractCreature owner, int amount)
    {
        name = NAME;
        ID = "Lockon";
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        loadRegion("lockon");
        type = AbstractPower.PowerType.DEBUFF;
        isTurnBased = true;
    }

    public void atEndOfRound()
    {
        if(amount == 0)
            addToBot(new RemoveSpecificPowerAction(owner, owner, "Lockon"));
        else
            addToBot(new ReducePowerAction(owner, owner, "Lockon", 1));
    }

    public void updateDescription()
    {
        if(owner != null)
            if(amount == 1)
                description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(50).append(DESCRIPTIONS[1]).append(amount).append(DESCRIPTIONS[2]).toString();
            else
                description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(50).append(DESCRIPTIONS[1]).append(amount).append(DESCRIPTIONS[3]).toString();
    }

    public static final String POWER_ID = "Lockon";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final float MULTIPLIER = 1.5F;
    private static final int MULTI_STR = 50;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Lockon");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
