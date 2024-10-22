// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Tranquility.java

package com.megacrit.cardcrawl.cards.purple;

import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.CalmStance;

public class Tranquility extends AbstractCard
{

    public Tranquility()
    {
        super("ClearTheMind", cardStrings.NAME, "purple/skill/tranquility", 1, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.PURPLE, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.COMMON, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.SELF);
        exhaust = true;
        selfRetain = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new ChangeStanceAction("Calm"));
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
        return new Tranquility();
    }

    public static final String ID = "ClearTheMind";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("ClearTheMind");
    }
}
