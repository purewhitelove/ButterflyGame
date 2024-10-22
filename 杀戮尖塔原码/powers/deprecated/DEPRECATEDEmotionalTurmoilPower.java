// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DEPRECATEDEmotionalTurmoilPower.java

package com.megacrit.cardcrawl.powers.deprecated;

import com.megacrit.cardcrawl.actions.deprecated.DEPRECATEDRandomStanceAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DEPRECATEDEmotionalTurmoilPower extends AbstractPower
{

    public DEPRECATEDEmotionalTurmoilPower(AbstractCreature owner)
    {
        name = powerStrings.NAME;
        ID = "EmotionalTurmoilPower";
        this.owner = owner;
        updateDescription();
        loadRegion("draw");
        type = com.megacrit.cardcrawl.powers.AbstractPower.PowerType.BUFF;
        isTurnBased = false;
    }

    public void atStartOfTurnPostDraw()
    {
        addToBot(new DEPRECATEDRandomStanceAction());
    }

    public void updateDescription()
    {
        description = powerStrings.DESCRIPTIONS[0];
    }

    public static final String POWER_ID = "EmotionalTurmoilPower";
    private static final PowerStrings powerStrings;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("EmotionalTurmoilPower");
    }
}
