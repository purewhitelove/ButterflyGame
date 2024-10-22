// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DEPRECATEDPerfectedForm.java

package com.megacrit.cardcrawl.cards.deprecated;

import com.megacrit.cardcrawl.actions.watcher.PerfectedFormAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DEPRECATEDPerfectedForm extends AbstractCard
{

    public DEPRECATEDPerfectedForm()
    {
        super("PerfectedForm", cardStrings.NAME, null, 0, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.PURPLE, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.RARE, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.SELF);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new PerfectedFormAction());
    }

    public void upgrade()
    {
        if(!upgraded)
        {
            upgradeName();
            initializeDescription();
            exhaust = false;
        }
    }

    public AbstractCard makeCopy()
    {
        return new DEPRECATEDPerfectedForm();
    }

    public static final String ID = "PerfectedForm";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("PerfectedForm");
    }
}
