// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CannotChangeStancePower.java

package com.megacrit.cardcrawl.powers.watcher;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class CannotChangeStancePower extends AbstractPower
{

    public CannotChangeStancePower(AbstractCreature owner)
    {
        name = powerStrings.NAME;
        ID = "CannotChangeStancePower";
        this.owner = owner;
        updateDescription();
        loadRegion("no_stance");
        type = com.megacrit.cardcrawl.powers.AbstractPower.PowerType.DEBUFF;
    }

    public void atEndOfTurn(boolean isPlayer)
    {
        if(isPlayer)
            addToBot(new RemoveSpecificPowerAction(owner, owner, "CannotChangeStancePower"));
    }

    public void updateDescription()
    {
        description = powerStrings.DESCRIPTIONS[0];
    }

    public static final String POWER_ID = "CannotChangeStancePower";
    private static final PowerStrings powerStrings;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("CannotChangeStancePower");
    }
}
