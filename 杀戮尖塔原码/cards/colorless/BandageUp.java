// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BandageUp.java

package com.megacrit.cardcrawl.cards.colorless;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.ArrayList;

public class BandageUp extends AbstractCard
{

    public BandageUp()
    {
        super("Bandage Up", cardStrings.NAME, "colorless/skill/bandage_up", 0, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.COLORLESS, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.SELF);
        baseMagicNumber = 4;
        magicNumber = baseMagicNumber;
        exhaust = true;
        tags.add(com.megacrit.cardcrawl.cards.AbstractCard.CardTags.HEALING);
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new HealAction(p, p, magicNumber));
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
        return new BandageUp();
    }

    public static final String ID = "Bandage Up";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Bandage Up");
    }
}
