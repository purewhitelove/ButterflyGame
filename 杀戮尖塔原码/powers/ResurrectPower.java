// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ResurrectPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class ResurrectPower extends AbstractPower
{

    public ResurrectPower(AbstractCreature owner)
    {
        name = NAME;
        ID = "Life Link";
        this.owner = owner;
        updateDescription();
        loadRegion("regrow");
        type = AbstractPower.PowerType.BUFF;
    }

    public void updateDescription()
    {
        description = DESCRIPTIONS[0];
    }

    public static final String POWER_ID = "Life Link";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Life Link");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
