// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GenericStrengthUpPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower, StrengthPower

public class GenericStrengthUpPower extends AbstractPower
{

    public GenericStrengthUpPower(AbstractCreature owner, String newName, int strAmt)
    {
        name = newName;
        ID = "Generic Strength Up Power";
        this.owner = owner;
        amount = strAmt;
        updateDescription();
        loadRegion("stasis");
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[1]).toString();
    }

    public void atEndOfRound()
    {
        flash();
        addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, amount), amount));
    }

    public static final String POWER_ID = "Generic Strength Up Power";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Generic Strength Up Power");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
