// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   InvinciblePower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class InvinciblePower extends AbstractPower
{

    public InvinciblePower(AbstractCreature owner, int amount)
    {
        name = NAME;
        ID = "Invincible";
        this.owner = owner;
        this.amount = amount;
        maxAmt = amount;
        updateDescription();
        loadRegion("heartDef");
        priority = 99;
    }

    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount)
    {
        if(damageAmount > amount)
            damageAmount = amount;
        amount -= damageAmount;
        if(amount < 0)
            amount = 0;
        updateDescription();
        return damageAmount;
    }

    public void atStartOfTurn()
    {
        amount = maxAmt;
        updateDescription();
    }

    public void updateDescription()
    {
        if(amount <= 0)
            description = DESCRIPTIONS[2];
        else
            description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[1]).toString();
    }

    public static final String POWER_ID = "Invincible";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    private int maxAmt;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Invincible");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
