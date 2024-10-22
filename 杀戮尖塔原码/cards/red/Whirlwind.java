// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Whirlwind.java

package com.megacrit.cardcrawl.cards.red;

import com.megacrit.cardcrawl.actions.unique.WhirlwindAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Whirlwind extends AbstractCard
{

    public Whirlwind()
    {
        super("Whirlwind", cardStrings.NAME, "red/attack/whirlwind", -1, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.RED, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.ALL_ENEMY);
        baseDamage = 5;
        isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new WhirlwindAction(p, multiDamage, damageTypeForTurn, freeToPlayOnce, energyOnUse));
    }

    public void upgrade()
    {
        if(!upgraded)
        {
            upgradeName();
            upgradeDamage(3);
        }
    }

    public AbstractCard makeCopy()
    {
        return new Whirlwind();
    }

    public static final String ID = "Whirlwind";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Whirlwind");
    }
}
