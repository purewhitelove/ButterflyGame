// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DEPRECATEDBlessed.java

package com.megacrit.cardcrawl.cards.deprecated;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DEPRECATEDBlessed extends AbstractCard
{

    public DEPRECATEDBlessed()
    {
        super("Blessed", cardStrings.NAME, null, 0, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.PURPLE, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.SELF);
        exhaust = true;
        baseMagicNumber = 2;
        magicNumber = baseMagicNumber;
        cardsToPreview = new Miracle();
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractCard miracle = CardLibrary.getCard("Miracle").makeCopy();
        if(upgraded)
            miracle.upgrade();
        addToBot(new MakeTempCardInDrawPileAction(miracle, magicNumber, true, true, false));
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

    public AbstractCard makeCopy()
    {
        return new DEPRECATEDBlessed();
    }

    public static final String ID = "Blessed";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Blessed");
    }
}
