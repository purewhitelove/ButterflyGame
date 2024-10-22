// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DEPRECATEDAlwaysMadPower.java

package com.megacrit.cardcrawl.powers.deprecated;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DEPRECATEDAlwaysMadPower extends AbstractPower
{

    public DEPRECATEDAlwaysMadPower(AbstractCreature owner)
    {
        name = powerStrings.NAME;
        ID = "AlwaysMad";
        this.owner = owner;
        updateDescription();
        loadRegion("anger");
    }

    public void updateDescription()
    {
        description = DESCRIPTIONS[0];
    }

    public static final String POWER_ID = "AlwaysMad";
    private static final PowerStrings powerStrings;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("AlwaysMad");
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
