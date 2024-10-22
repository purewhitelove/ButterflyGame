// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LoseDexterityPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower, DexterityPower

public class LoseDexterityPower extends AbstractPower
{

    public LoseDexterityPower(AbstractCreature owner, int newAmount)
    {
        name = powerStrings.NAME;
        ID = "DexLoss";
        this.owner = owner;
        amount = newAmount;
        type = AbstractPower.PowerType.DEBUFF;
        updateDescription();
        loadRegion("flex");
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(powerStrings.DESCRIPTIONS[0]).append(amount).append(powerStrings.DESCRIPTIONS[1]).toString();
    }

    public void atEndOfTurn(boolean isPlayer)
    {
        flash();
        addToBot(new ApplyPowerAction(owner, owner, new DexterityPower(owner, -amount), -amount));
        addToBot(new RemoveSpecificPowerAction(owner, owner, "DexLoss"));
    }

    public static final String POWER_ID = "DexLoss";
    private static final PowerStrings powerStrings;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("DexLoss");
    }
}
