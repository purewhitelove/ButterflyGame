// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PainfulStabsPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class PainfulStabsPower extends AbstractPower
{

    public PainfulStabsPower(AbstractCreature owner)
    {
        name = NAME;
        ID = "Painful Stabs";
        this.owner = owner;
        amount = -1;
        updateDescription();
        loadRegion("painfulStabs");
    }

    public void updateDescription()
    {
        description = DESCRIPTIONS[0];
    }

    public void onInflictDamage(DamageInfo info, int damageAmount, AbstractCreature target)
    {
        if(damageAmount > 0 && info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS)
            addToBot(new MakeTempCardInDiscardAction(new Wound(), 1));
    }

    public static final String POWER_ID = "Painful Stabs";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Painful Stabs");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
