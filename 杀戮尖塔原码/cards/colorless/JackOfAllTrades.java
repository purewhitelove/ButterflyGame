// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JackOfAllTrades.java

package com.megacrit.cardcrawl.cards.colorless;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class JackOfAllTrades extends AbstractCard
{

    public JackOfAllTrades()
    {
        super("Jack Of All Trades", cardStrings.NAME, "colorless/skill/jack_of_all_trades", 0, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.COLORLESS, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.NONE);
        exhaust = true;
        baseMagicNumber = 1;
        magicNumber = baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractCard c = AbstractDungeon.returnTrulyRandomColorlessCardInCombat(AbstractDungeon.cardRandomRng).makeCopy();
        addToBot(new MakeTempCardInHandAction(c, 1));
        if(upgraded)
        {
            c = AbstractDungeon.returnTrulyRandomColorlessCardInCombat(AbstractDungeon.cardRandomRng).makeCopy();
            addToBot(new MakeTempCardInHandAction(c, 1));
        }
    }

    public void upgrade()
    {
        if(!upgraded)
        {
            upgradeName();
            upgradeMagicNumber(1);
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    public AbstractCard makeCopy()
    {
        return new JackOfAllTrades();
    }

    public static final String ID = "Jack Of All Trades";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Jack Of All Trades");
    }
}
