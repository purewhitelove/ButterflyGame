// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BackAttackPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class BackAttackPower extends AbstractPower
{

    public BackAttackPower(AbstractCreature owner)
    {
        name = NAME;
        type = AbstractPower.PowerType.BUFF;
        ID = "BackAttack";
        this.owner = owner;
        amount = -1;
        updateDescription();
        if(owner.hb.cX < (float)Settings.WIDTH / 2.0F)
            loadRegion("backAttack");
        else
            loadRegion("backAttack2");
    }

    public void updateDescription()
    {
        description = DESCRIPTIONS[0];
    }

    public static final String POWER_ID = "BackAttack";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("BackAttack");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
