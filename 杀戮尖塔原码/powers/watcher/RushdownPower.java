// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RushdownPower.java

package com.megacrit.cardcrawl.powers.watcher;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.WrathStance;

public class RushdownPower extends AbstractPower
{

    public RushdownPower(AbstractCreature owner, int amount)
    {
        name = powerStrings.NAME;
        ID = "Adaptation";
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        loadRegion("rushdown");
    }

    public void updateDescription()
    {
        if(amount > 1)
            description = (new StringBuilder()).append(powerStrings.DESCRIPTIONS[0]).append(amount).append(powerStrings.DESCRIPTIONS[2]).toString();
        else
            description = (new StringBuilder()).append(powerStrings.DESCRIPTIONS[0]).append(amount).append(powerStrings.DESCRIPTIONS[1]).toString();
    }

    public void onChangeStance(AbstractStance oldStance, AbstractStance newStance)
    {
        if(!oldStance.ID.equals(newStance.ID) && newStance.ID.equals("Wrath"))
        {
            flash();
            addToBot(new DrawCardAction(owner, amount));
        }
    }

    public static final String POWER_ID = "Adaptation";
    private static final PowerStrings powerStrings;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Adaptation");
    }
}
