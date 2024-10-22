// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DEPRECATEDFlowPower.java

package com.megacrit.cardcrawl.powers.deprecated;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.*;

public class DEPRECATEDFlowPower extends AbstractPower
{

    public DEPRECATEDFlowPower(AbstractCreature owner, int amount)
    {
        name = powerStrings.NAME;
        ID = "FlowPower";
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        loadRegion("afterImage");
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(powerStrings.DESCRIPTIONS[0]).append(amount).append(powerStrings.DESCRIPTIONS[1]).append(amount).append(powerStrings.DESCRIPTIONS[2]).toString();
    }

    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if(card.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL)
        {
            flash();
            addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, amount), amount));
            addToBot(new ApplyPowerAction(owner, owner, new LoseStrengthPower(owner, amount), amount));
        } else
        if(card.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK)
        {
            flash();
            addToBot(new ApplyPowerAction(owner, owner, new DexterityPower(owner, amount), amount));
            addToBot(new ApplyPowerAction(owner, owner, new LoseDexterityPower(owner, amount), amount));
        }
    }

    public static final String POWER_ID = "FlowPower";
    private static final PowerStrings powerStrings;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("FlowPower");
    }
}
