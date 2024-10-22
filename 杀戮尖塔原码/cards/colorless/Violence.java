// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Violence.java

package com.megacrit.cardcrawl.cards.colorless;

import com.megacrit.cardcrawl.actions.utility.DrawPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Violence extends AbstractCard
{

    public Violence()
    {
        super("Violence", cardStrings.NAME, "colorless/skill/violence", 0, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.COLORLESS, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.RARE, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.NONE);
        baseMagicNumber = 3;
        magicNumber = baseMagicNumber;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new DrawPileToHandAction(magicNumber, com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK));
    }

    public void upgrade()
    {
        if(!upgraded)
        {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

    public AbstractCard makeCopy()
    {
        return new Violence();
    }

    public static final String ID = "Violence";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Violence");
    }
}
