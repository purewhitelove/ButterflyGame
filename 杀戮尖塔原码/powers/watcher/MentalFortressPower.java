// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MentalFortressPower.java

package com.megacrit.cardcrawl.powers.watcher;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.AbstractStance;

public class MentalFortressPower extends AbstractPower
{

    public MentalFortressPower(AbstractCreature owner, int amount)
    {
        name = powerStrings.NAME;
        ID = "Controlled";
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        loadRegion("mental_fortress");
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(powerStrings.DESCRIPTIONS[0]).append(amount).append(powerStrings.DESCRIPTIONS[1]).toString();
    }

    public void onChangeStance(AbstractStance oldStance, AbstractStance newStance)
    {
        if(!oldStance.ID.equals(newStance.ID))
        {
            flash();
            addToBot(new GainBlockAction(owner, owner, amount));
        }
    }

    public static final String POWER_ID = "Controlled";
    private static final PowerStrings powerStrings;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Controlled");
    }
}
