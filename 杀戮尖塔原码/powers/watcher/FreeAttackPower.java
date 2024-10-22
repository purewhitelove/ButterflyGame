// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FreeAttackPower.java

package com.megacrit.cardcrawl.powers.watcher;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class FreeAttackPower extends AbstractPower
{

    public FreeAttackPower(AbstractCreature owner, int amount)
    {
        name = powerStrings.NAME;
        ID = "FreeAttackPower";
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        loadRegion("swivel");
    }

    public void updateDescription()
    {
        if(amount == 1)
            description = powerStrings.DESCRIPTIONS[0];
        else
            description = (new StringBuilder()).append(powerStrings.DESCRIPTIONS[1]).append(amount).append(powerStrings.DESCRIPTIONS[2]).toString();
    }

    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if(card.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK && !card.purgeOnUse && amount > 0)
        {
            flash();
            amount--;
            if(amount == 0)
                addToTop(new RemoveSpecificPowerAction(owner, owner, "FreeAttackPower"));
        }
    }

    public static final String POWER_ID = "FreeAttackPower";
    private static final PowerStrings powerStrings;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("FreeAttackPower");
    }
}
