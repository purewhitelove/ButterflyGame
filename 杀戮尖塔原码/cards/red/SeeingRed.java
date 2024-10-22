// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SeeingRed.java

package com.megacrit.cardcrawl.cards.red;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SeeingRed extends AbstractCard
{

    public SeeingRed()
    {
        super("Seeing Red", cardStrings.NAME, "red/skill/seeing_red", 1, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.RED, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.NONE);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new GainEnergyAction(2));
    }

    public void upgrade()
    {
        if(!upgraded)
        {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

    public AbstractCard makeCopy()
    {
        return new SeeingRed();
    }

    public static final String ID = "Seeing Red";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Seeing Red");
    }
}
