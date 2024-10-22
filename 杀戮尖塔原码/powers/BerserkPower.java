// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BerserkPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class BerserkPower extends AbstractPower
{

    public BerserkPower(AbstractCreature owner, int amount)
    {
        name = powerStrings.NAME;
        ID = "Berserk";
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        loadRegion("berserk");
    }

    public void updateDescription()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(powerStrings.DESCRIPTIONS[0]);
        for(int i = 0; i < amount; i++)
            sb.append("[R] ");

        sb.append(LocalizedStrings.PERIOD);
        description = sb.toString();
    }

    public void atStartOfTurn()
    {
        addToBot(new GainEnergyAction(amount));
        flash();
    }

    public static final String POWER_ID = "Berserk";
    private static final PowerStrings powerStrings;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Berserk");
    }
}
