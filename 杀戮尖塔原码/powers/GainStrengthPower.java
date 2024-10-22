// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GainStrengthPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower, StrengthPower

public class GainStrengthPower extends AbstractPower
{

    public GainStrengthPower(AbstractCreature owner, int newAmount)
    {
        name = NAME;
        ID = "Shackled";
        this.owner = owner;
        amount = newAmount;
        type = AbstractPower.PowerType.DEBUFF;
        updateDescription();
        loadRegion("shackle");
        if(amount >= 999)
            amount = 999;
        if(amount <= -999)
            amount = -999;
    }

    public void playApplyPowerSfx()
    {
        CardCrawlGame.sound.play("POWER_SHACKLE", 0.05F);
    }

    public void stackPower(int stackAmount)
    {
        fontScale = 8F;
        amount += stackAmount;
        if(amount == 0)
            addToTop(new RemoveSpecificPowerAction(owner, owner, "Shackled"));
        if(amount >= 999)
            amount = 999;
        if(amount <= -999)
            amount = -999;
    }

    public void reducePower(int reduceAmount)
    {
        fontScale = 8F;
        amount -= reduceAmount;
        if(amount == 0)
            addToTop(new RemoveSpecificPowerAction(owner, owner, NAME));
        if(amount >= 999)
            amount = 999;
        if(amount <= -999)
            amount = -999;
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[1]).toString();
    }

    public void atEndOfTurn(boolean isPlayer)
    {
        flash();
        addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, amount), amount));
        addToBot(new RemoveSpecificPowerAction(owner, owner, "Shackled"));
    }

    public static final String POWER_ID = "Shackled";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Shackled");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
