// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GrowthPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower, StrengthPower

public class GrowthPower extends AbstractPower
{

    public GrowthPower(AbstractCreature owner, int strAmt)
    {
        skipFirst = true;
        name = NAME;
        ID = "GrowthPower";
        this.owner = owner;
        amount = strAmt;
        updateDescription();
        loadRegion("ritual");
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[1]).toString();
    }

    public void atEndOfRound()
    {
        if(!skipFirst)
        {
            flash();
            addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, amount), amount));
        } else
        {
            skipFirst = false;
        }
    }

    public static final String POWER_ID = "GrowthPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    private boolean skipFirst;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("GrowthPower");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
