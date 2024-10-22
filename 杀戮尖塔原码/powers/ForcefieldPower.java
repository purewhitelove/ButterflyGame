// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ForcefieldPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class ForcefieldPower extends AbstractPower
{

    public ForcefieldPower(AbstractCreature owner)
    {
        name = NAME;
        ID = "Nullify Attack";
        this.owner = owner;
        amount = -1;
        updateDescription();
        loadRegion("forcefield");
    }

    public void updateDescription()
    {
        description = DESCRIPTIONS[0];
    }

    public float atDamageFinalReceive(float damage, com.megacrit.cardcrawl.cards.DamageInfo.DamageType type)
    {
        if(damage > 0.0F && type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS && type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS)
            return 0.0F;
        else
            return damage;
    }

    public static final String POWER_ID = "Nullify Attack";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Nullify Attack");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
