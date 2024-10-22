// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BarricadePower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class BarricadePower extends AbstractPower
{

    public BarricadePower(AbstractCreature owner)
    {
        name = NAME;
        ID = "Barricade";
        this.owner = owner;
        amount = -1;
        updateDescription();
        loadRegion("barricade");
    }

    public void updateDescription()
    {
        if(owner.isPlayer)
            description = DESCRIPTIONS[0];
        else
            description = DESCRIPTIONS[1];
    }

    public static final String POWER_ID = "Barricade";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Barricade");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
