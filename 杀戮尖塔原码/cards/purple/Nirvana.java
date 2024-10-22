// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Nirvana.java

package com.megacrit.cardcrawl.cards.purple;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.NirvanaPower;

public class Nirvana extends AbstractCard
{

    public Nirvana()
    {
        super("Nirvana", cardStrings.NAME, "purple/power/nirvana", 1, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.POWER, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.PURPLE, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.SELF);
        baseMagicNumber = 3;
        magicNumber = baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new ApplyPowerAction(p, p, new NirvanaPower(p, magicNumber), magicNumber));
    }

    public AbstractCard makeCopy()
    {
        return new Nirvana();
    }

    public void upgrade()
    {
        if(!upgraded)
        {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

    public static final String ID = "Nirvana";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Nirvana");
    }
}
