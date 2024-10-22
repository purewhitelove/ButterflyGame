// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PenNibPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class PenNibPower extends AbstractPower
{

    public PenNibPower(AbstractCreature owner, int amount)
    {
        name = NAME;
        ID = "Pen Nib";
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        loadRegion("penNib");
        type = AbstractPower.PowerType.BUFF;
        isTurnBased = true;
        priority = 6;
    }

    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if(card.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK)
            addToBot(new RemoveSpecificPowerAction(owner, owner, "Pen Nib"));
    }

    public void updateDescription()
    {
        description = DESCRIPTIONS[0];
    }

    public float atDamageGive(float damage, com.megacrit.cardcrawl.cards.DamageInfo.DamageType type)
    {
        if(type == com.megacrit.cardcrawl.cards.DamageInfo.DamageType.NORMAL)
            return damage * 2.0F;
        else
            return damage;
    }

    public static final String POWER_ID = "Pen Nib";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Pen Nib");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
