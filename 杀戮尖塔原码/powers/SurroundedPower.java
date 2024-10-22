// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SurroundedPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class SurroundedPower extends AbstractPower
{

    public SurroundedPower(AbstractCreature owner)
    {
        name = powerStrings.NAME;
        ID = "Surrounded";
        this.owner = owner;
        amount = -1;
        updateDescription();
        loadRegion("surrounded");
    }

    public void updateDescription()
    {
        description = powerStrings.DESCRIPTIONS[0];
    }

    public static final String POWER_ID = "Surrounded";
    private static final PowerStrings powerStrings;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Surrounded");
    }
}
