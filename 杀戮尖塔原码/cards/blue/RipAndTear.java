// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RipAndTear.java

package com.megacrit.cardcrawl.cards.blue;

import com.megacrit.cardcrawl.actions.defect.NewRipAndTearAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class RipAndTear extends AbstractCard
{

    public RipAndTear()
    {
        super("Rip and Tear", cardStrings.NAME, "blue/attack/rip_and_tear", 1, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.BLUE, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.ALL_ENEMY);
        baseDamage = 7;
        baseMagicNumber = 2;
        magicNumber = baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        for(int i = 0; i < magicNumber; i++)
            addToBot(new NewRipAndTearAction(this));

    }

    public void upgrade()
    {
        if(!upgraded)
        {
            upgradeName();
            upgradeDamage(2);
        }
    }

    public AbstractCard makeCopy()
    {
        return new RipAndTear();
    }

    public static final String ID = "Rip and Tear";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Rip and Tear");
    }
}
