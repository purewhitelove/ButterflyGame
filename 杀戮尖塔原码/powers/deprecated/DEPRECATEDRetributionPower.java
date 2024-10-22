// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DEPRECATEDRetributionPower.java

package com.megacrit.cardcrawl.powers.deprecated;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

public class DEPRECATEDRetributionPower extends AbstractPower
{

    public DEPRECATEDRetributionPower(AbstractCreature owner, int vigorAmt)
    {
        name = powerStrings.NAME;
        ID = "Retribution";
        this.owner = owner;
        amount = vigorAmt;
        updateDescription();
        loadRegion("anger");
    }

    public int onAttacked(DamageInfo info, int damageAmount)
    {
        if(damageAmount > 0)
        {
            flash();
            addToTop(new ApplyPowerAction(owner, owner, new VigorPower(owner, amount), amount));
        }
        return damageAmount;
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(powerStrings.DESCRIPTIONS[0]).append(amount).append(powerStrings.DESCRIPTIONS[1]).toString();
    }

    public static final String POWER_ID = "Retribution";
    private static final PowerStrings powerStrings;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Retribution");
    }
}
