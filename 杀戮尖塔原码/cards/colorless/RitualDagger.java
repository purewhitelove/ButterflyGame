// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RitualDagger.java

package com.megacrit.cardcrawl.cards.colorless;

import com.megacrit.cardcrawl.actions.unique.RitualDaggerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class RitualDagger extends AbstractCard
{

    public RitualDagger()
    {
        super("RitualDagger", cardStrings.NAME, "colorless/attack/ritual_dagger", 1, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.COLORLESS, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.SPECIAL, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.ENEMY);
        misc = 15;
        baseMagicNumber = 3;
        magicNumber = baseMagicNumber;
        baseDamage = misc;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new RitualDaggerAction(m, new DamageInfo(p, damage, damageTypeForTurn), magicNumber, uuid));
    }

    public void applyPowers()
    {
        baseBlock = misc;
        super.applyPowers();
        initializeDescription();
    }

    public void upgrade()
    {
        if(!upgraded)
        {
            upgradeMagicNumber(2);
            upgradeName();
        }
    }

    public AbstractCard makeCopy()
    {
        return new RitualDagger();
    }

    public static final String ID = "RitualDagger";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("RitualDagger");
    }
}
