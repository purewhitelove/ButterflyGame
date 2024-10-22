// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CreativeAI.java

package com.megacrit.cardcrawl.cards.blue;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.CreativeAIPower;

public class CreativeAI extends AbstractCard
{

    public CreativeAI()
    {
        super("Creative AI", cardStrings.NAME, "blue/power/creative_ai", 3, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.POWER, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.BLUE, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.RARE, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.SELF);
        baseMagicNumber = 1;
        magicNumber = baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new ApplyPowerAction(p, p, new CreativeAIPower(p, magicNumber), magicNumber));
    }

    public AbstractCard makeCopy()
    {
        return new CreativeAI();
    }

    public void upgrade()
    {
        if(!upgraded)
        {
            upgradeName();
            upgradeBaseCost(2);
        }
    }

    public static final String ID = "Creative AI";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Creative AI");
    }
}
