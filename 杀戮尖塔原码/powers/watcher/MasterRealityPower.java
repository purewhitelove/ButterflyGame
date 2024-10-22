// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MasterRealityPower.java

package com.megacrit.cardcrawl.powers.watcher;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class MasterRealityPower extends AbstractPower
{

    public MasterRealityPower(AbstractCreature owner)
    {
        name = powerStrings.NAME;
        ID = "MasterRealityPower";
        this.owner = owner;
        updateDescription();
        loadRegion("master_reality");
        type = com.megacrit.cardcrawl.powers.AbstractPower.PowerType.BUFF;
    }

    public void updateDescription()
    {
        description = powerStrings.DESCRIPTIONS[0];
    }

    public static final String POWER_ID = "MasterRealityPower";
    private static final PowerStrings powerStrings;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("MasterRealityPower");
    }
}
