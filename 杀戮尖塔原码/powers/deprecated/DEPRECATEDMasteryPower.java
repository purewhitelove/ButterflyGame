// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DEPRECATEDMasteryPower.java

package com.megacrit.cardcrawl.powers.deprecated;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.NeutralStance;

public class DEPRECATEDMasteryPower extends AbstractPower
{

    public DEPRECATEDMasteryPower(AbstractCreature owner, int amount)
    {
        name = NAME;
        ID = "Mastery";
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        loadRegion("corruption");
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[1]).toString();
    }

    public void onChangeStance(AbstractStance oldStance, AbstractStance newStance)
    {
        if(oldStance.ID.equals(newStance.ID) && !newStance.ID.equals("Neutral"))
        {
            flash();
            addToBot(new GainEnergyAction(amount));
        }
    }

    public static final String POWER_ID = "Mastery";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Mastery");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
