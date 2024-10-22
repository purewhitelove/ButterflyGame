// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Chrysalis.java

package com.megacrit.cardcrawl.cards.colorless;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Chrysalis extends AbstractCard
{

    public Chrysalis()
    {
        super("Chrysalis", cardStrings.NAME, "colorless/skill/chrysalis", 2, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.COLORLESS, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.RARE, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.NONE);
        baseMagicNumber = 3;
        magicNumber = baseMagicNumber;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        for(int i = 0; i < magicNumber; i++)
        {
            AbstractCard card = AbstractDungeon.returnTrulyRandomCardInCombat(com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL).makeCopy();
            if(card.cost > 0)
            {
                card.cost = 0;
                card.costForTurn = 0;
                card.isCostModified = true;
            }
            addToBot(new MakeTempCardInDrawPileAction(card, 1, true, true));
        }

    }

    public void upgrade()
    {
        if(!upgraded)
        {
            upgradeName();
            upgradeMagicNumber(2);
        }
    }

    public AbstractCard makeCopy()
    {
        return new Chrysalis();
    }

    public static final String ID = "Chrysalis";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Chrysalis");
    }
}
