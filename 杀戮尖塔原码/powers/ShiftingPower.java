// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ShiftingPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower, StrengthPower, ArtifactPower, GainStrengthPower

public class ShiftingPower extends AbstractPower
{

    public ShiftingPower(AbstractCreature owner)
    {
        name = NAME;
        ID = "Shifting";
        this.owner = owner;
        updateDescription();
        isPostActionPower = true;
        loadRegion("shift");
    }

    public int onAttacked(DamageInfo info, int damageAmount)
    {
        if(damageAmount > 0)
        {
            addToTop(new ApplyPowerAction(owner, owner, new StrengthPower(owner, -damageAmount), -damageAmount));
            if(!owner.hasPower("Artifact"))
                addToTop(new ApplyPowerAction(owner, owner, new GainStrengthPower(owner, damageAmount), damageAmount));
            flash();
        }
        return damageAmount;
    }

    public void updateDescription()
    {
        description = DESCRIPTIONS[1];
    }

    public static final String POWER_ID = "Shifting";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Shifting");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
