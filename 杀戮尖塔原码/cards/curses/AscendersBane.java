// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AscendersBane.java

package com.megacrit.cardcrawl.cards.curses;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AscendersBane extends AbstractCard
{

    public AscendersBane()
    {
        super("AscendersBane", cardStrings.NAME, "curse/ascenders_bane", -2, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.CURSE, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.CURSE, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.SPECIAL, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.NONE);
        isEthereal = true;
    }

    public void use(AbstractPlayer abstractplayer, AbstractMonster abstractmonster)
    {
    }

    public void upgrade()
    {
    }

    public AbstractCard makeCopy()
    {
        return new AscendersBane();
    }

    public static final String ID = "AscendersBane";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("AscendersBane");
    }
}
