// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DEPRECATEDCondensePower.java

package com.megacrit.cardcrawl.powers.deprecated;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DEPRECATEDCondensePower extends AbstractPower
{

    public DEPRECATEDCondensePower(AbstractCreature owner, int bufferAmt)
    {
        name = NAME;
        ID = "DEPRECATEDCondense";
        this.owner = owner;
        amount = bufferAmt;
        updateDescription();
        loadRegion("buffer");
    }

    public int onLoseHp(int damageAmount)
    {
        if(damageAmount > amount)
        {
            flash();
            return amount;
        } else
        {
            return damageAmount;
        }
    }

    public void stackPower(int i)
    {
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[1]).toString();
    }

    public static final String POWER_ID = "DEPRECATEDCondense";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("DEPRECATEDCondense");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
