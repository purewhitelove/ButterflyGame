// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WrathNextTurnPower.java

package com.megacrit.cardcrawl.powers.watcher;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.WrathStance;

public class WrathNextTurnPower extends AbstractPower
{

    public WrathNextTurnPower(AbstractCreature owner)
    {
        name = powerStrings.NAME;
        ID = "WrathNextTurnPower";
        this.owner = owner;
        updateDescription();
        loadRegion("anger");
    }

    public void updateDescription()
    {
        description = powerStrings.DESCRIPTIONS[0];
    }

    public void atStartOfTurn()
    {
        addToBot(new ChangeStanceAction("Wrath"));
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    public static final String POWER_ID = "WrathNextTurnPower";
    private static final PowerStrings powerStrings;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("WrathNextTurnPower");
    }
}
