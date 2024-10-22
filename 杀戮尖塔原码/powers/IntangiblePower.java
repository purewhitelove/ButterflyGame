// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IntangiblePower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class IntangiblePower extends AbstractPower
{

    public IntangiblePower(AbstractCreature owner, int turns)
    {
        name = NAME;
        ID = "Intangible";
        this.owner = owner;
        amount = turns;
        updateDescription();
        loadRegion("intangible");
        priority = 75;
        justApplied = true;
    }

    public void playApplyPowerSfx()
    {
        CardCrawlGame.sound.play("POWER_INTANGIBLE", 0.05F);
    }

    public float atDamageFinalReceive(float damage, com.megacrit.cardcrawl.cards.DamageInfo.DamageType type)
    {
        if(damage > 1.0F)
            damage = 1.0F;
        return damage;
    }

    public void updateDescription()
    {
        description = DESCRIPTIONS[0];
    }

    public void atEndOfTurn(boolean isPlayer)
    {
        if(justApplied)
        {
            justApplied = false;
            return;
        }
        flash();
        if(amount == 0)
            addToBot(new RemoveSpecificPowerAction(owner, owner, "Intangible"));
        else
            addToBot(new ReducePowerAction(owner, owner, "Intangible", 1));
    }

    public static final String POWER_ID = "Intangible";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    private boolean justApplied;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Intangible");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
