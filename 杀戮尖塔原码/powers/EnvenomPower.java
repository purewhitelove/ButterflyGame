// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EnvenomPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower, PoisonPower

public class EnvenomPower extends AbstractPower
{

    public EnvenomPower(AbstractCreature owner, int newAmount)
    {
        name = NAME;
        ID = "Envenom";
        this.owner = owner;
        amount = newAmount;
        updateDescription();
        loadRegion("envenom");
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[1]).toString();
    }

    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target)
    {
        if(damageAmount > 0 && target != owner && info.type == com.megacrit.cardcrawl.cards.DamageInfo.DamageType.NORMAL)
        {
            flash();
            addToTop(new ApplyPowerAction(target, owner, new PoisonPower(target, owner, amount), amount, true));
        }
    }

    public static final String POWER_ID = "Envenom";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Envenom");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
