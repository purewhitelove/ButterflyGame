// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EnergyDownPower.java

package com.megacrit.cardcrawl.powers.watcher;

import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class EnergyDownPower extends AbstractPower
{

    public EnergyDownPower(AbstractCreature owner, int amount, boolean isFasting)
    {
        name = powerStrings.NAME;
        ID = "EnergyDownPower";
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        if(isFasting)
            loadRegion("fasting");
        else
            loadRegion("energized_blue");
        type = com.megacrit.cardcrawl.powers.AbstractPower.PowerType.DEBUFF;
    }

    public EnergyDownPower(AbstractCreature owner, int amount)
    {
        this(owner, amount, false);
    }

    public void updateDescription()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(powerStrings.DESCRIPTIONS[0]);
        for(int i = 0; i < amount; i++)
            sb.append("[E] ");

        if(powerStrings.DESCRIPTIONS[1].isEmpty())
            sb.append(LocalizedStrings.PERIOD);
        else
            sb.append(powerStrings.DESCRIPTIONS[1]);
        description = sb.toString();
    }

    public void atStartOfTurn()
    {
        addToBot(new LoseEnergyAction(amount));
        flash();
    }

    public static final String POWER_ID = "EnergyDownPower";
    private static final PowerStrings powerStrings;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("EnergyDownPower");
    }
}
