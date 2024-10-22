// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Fission.java

package com.megacrit.cardcrawl.cards.blue;

import com.megacrit.cardcrawl.actions.defect.FissionAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Fission extends AbstractCard
{

    public Fission()
    {
        super("Fission", cardStrings.NAME, "blue/skill/fission", 0, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.BLUE, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.RARE, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.NONE);
        magicNumber = 1;
        baseMagicNumber = 1;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new FissionAction(upgraded));
    }

    public void upgrade()
    {
        if(!upgraded)
        {
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    public AbstractCard makeCopy()
    {
        return new Fission();
    }

    public static final String ID = "Fission";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Fission");
    }
}
