// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StormOfSteel.java

package com.megacrit.cardcrawl.cards.green;

import com.megacrit.cardcrawl.actions.unique.BladeFuryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class StormOfSteel extends AbstractCard
{

    public StormOfSteel()
    {
        super("Storm of Steel", cardStrings.NAME, "green/skill/storm_of_steel", 1, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.GREEN, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.RARE, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.NONE);
        cardsToPreview = new Shiv();
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new BladeFuryAction(upgraded));
    }

    public AbstractCard makeCopy()
    {
        return new StormOfSteel();
    }

    public void upgrade()
    {
        if(!upgraded)
        {
            upgradeName();
            cardsToPreview.upgrade();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    public static final String ID = "Storm of Steel";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Storm of Steel");
    }
}
