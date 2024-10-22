// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SadisticPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower, GainStrengthPower, ArtifactPower

public class SadisticPower extends AbstractPower
{

    public SadisticPower(AbstractCreature owner, int amount)
    {
        name = NAME;
        ID = "Sadistic";
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        loadRegion("sadistic");
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[1]).toString();
    }

    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source)
    {
        if(power.type == AbstractPower.PowerType.DEBUFF && !power.ID.equals("Shackled") && source == owner && target != owner && !target.hasPower("Artifact"))
        {
            flash();
            addToBot(new DamageAction(target, new DamageInfo(owner, amount, com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE));
        }
    }

    public static final String POWER_ID = "Sadistic";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Sadistic");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
