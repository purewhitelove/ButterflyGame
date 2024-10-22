// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DemonFormPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower, StrengthPower

public class DemonFormPower extends AbstractPower
{

    public DemonFormPower(AbstractCreature owner, int strengthAmount)
    {
        name = powerStrings.NAME;
        ID = "Demon Form";
        this.owner = owner;
        amount = strengthAmount;
        updateDescription();
        loadRegion("demonForm");
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(powerStrings.DESCRIPTIONS[0]).append(amount).append(powerStrings.DESCRIPTIONS[1]).toString();
    }

    public void atStartOfTurnPostDraw()
    {
        flash();
        addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, amount), amount));
    }

    public static final String POWER_ID = "Demon Form";
    private static final PowerStrings powerStrings;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Demon Form");
    }
}
