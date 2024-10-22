// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EstablishmentPower.java

package com.megacrit.cardcrawl.powers.watcher;

import com.megacrit.cardcrawl.actions.unique.EstablishmentPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class EstablishmentPower extends AbstractPower
{

    public EstablishmentPower(AbstractCreature owner, int strengthAmount)
    {
        name = powerStrings.NAME;
        ID = "EstablishmentPower";
        this.owner = owner;
        amount = strengthAmount;
        updateDescription();
        loadRegion("establishment");
        priority = 25;
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(powerStrings.DESCRIPTIONS[0]).append(amount).append(powerStrings.DESCRIPTIONS[1]).toString();
    }

    public void atEndOfTurn(boolean isPlayer)
    {
        flash();
        addToBot(new EstablishmentPowerAction(amount));
    }

    public static final String POWER_ID = "EstablishmentPower";
    private static final PowerStrings powerStrings;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("EstablishmentPower");
    }
}
