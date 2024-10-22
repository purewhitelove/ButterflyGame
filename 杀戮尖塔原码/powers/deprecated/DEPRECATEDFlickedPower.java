// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DEPRECATEDFlickedPower.java

package com.megacrit.cardcrawl.powers.deprecated;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DEPRECATEDFlickedPower extends AbstractPower
{

    public DEPRECATEDFlickedPower(AbstractCreature owner, int amt)
    {
        name = powerStrings.NAME;
        ID = "FlickPower";
        this.owner = owner;
        amount = amt;
        updateDescription();
        loadRegion("talk_to_hand");
        type = com.megacrit.cardcrawl.powers.AbstractPower.PowerType.DEBUFF;
    }

    public void stackPower(int stackAmount)
    {
        amount += stackAmount;
        if(amount >= 3)
        {
            addToBot(new DamageAction(owner, new DamageInfo(null, 50, com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY, true));
            addToBot(new RemoveSpecificPowerAction(owner, null, "FlickPower"));
        } else
        {
            fontScale = 8F;
            updateDescription();
        }
    }

    public void updateDescription()
    {
        if(amount == 1)
            description = (new StringBuilder()).append(powerStrings.DESCRIPTIONS[0]).append(powerStrings.DESCRIPTIONS[1]).append(50).append(powerStrings.DESCRIPTIONS[3]).toString();
        else
            description = (new StringBuilder()).append(powerStrings.DESCRIPTIONS[0]).append(powerStrings.DESCRIPTIONS[2]).append(50).append(powerStrings.DESCRIPTIONS[3]).toString();
    }

    public static final String POWER_ID = "FlickPower";
    private static final PowerStrings powerStrings;
    private static final int FLICK_DMG = 50;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("FlickPower");
    }
}
