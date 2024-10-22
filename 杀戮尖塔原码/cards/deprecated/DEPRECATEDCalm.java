// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DEPRECATEDCalm.java

package com.megacrit.cardcrawl.cards.deprecated;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DEPRECATEDCalm extends AbstractCard
{

    public DEPRECATEDCalm()
    {
        super("Calm", cardStrings.NAME, null, 1, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.PURPLE, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.BASIC, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.SELF);
    }

    public void use(AbstractPlayer abstractplayer, AbstractMonster abstractmonster)
    {
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
        return new DEPRECATEDCalm();
    }

    public static final String ID = "Calm";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Calm");
    }
}
