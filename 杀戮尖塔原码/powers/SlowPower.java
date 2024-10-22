// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SlowPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class SlowPower extends AbstractPower
{

    public SlowPower(AbstractCreature owner, int amount)
    {
        name = NAME;
        ID = "Slow";
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        loadRegion("slow");
        type = AbstractPower.PowerType.DEBUFF;
    }

    public void atEndOfRound()
    {
        amount = 0;
        updateDescription();
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(FontHelper.colorString(owner.name, "y")).append(DESCRIPTIONS[1]).toString();
        if(amount == 0) goto _L2; else goto _L1
_L1:
        new StringBuilder();
        this;
        JVM INSTR dup_x1 ;
        description;
        append();
        DESCRIPTIONS[2];
        append();
        amount * 10;
        append();
        DESCRIPTIONS[3];
        append();
        toString();
        description;
_L2:
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action)
    {
        addToBot(new ApplyPowerAction(owner, owner, new SlowPower(owner, 1), 1));
    }

    public float atDamageReceive(float damage, com.megacrit.cardcrawl.cards.DamageInfo.DamageType type)
    {
        if(type == com.megacrit.cardcrawl.cards.DamageInfo.DamageType.NORMAL)
            return damage * (1.0F + (float)amount * 0.1F);
        else
            return damage;
    }

    public static final String POWER_ID = "Slow";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Slow");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
