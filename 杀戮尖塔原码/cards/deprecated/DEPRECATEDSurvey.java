// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DEPRECATEDSurvey.java

package com.megacrit.cardcrawl.cards.deprecated;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

public class DEPRECATEDSurvey extends AbstractCard
{

    public DEPRECATEDSurvey()
    {
        super("Survey", cardStrings.NAME, null, 1, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.PURPLE, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.COMMON, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.SELF);
        baseBlock = 8;
        baseMagicNumber = 2;
        magicNumber = baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new GainBlockAction(p, p, block));
        addToBot(new ApplyPowerAction(p, p, new VigorPower(p, magicNumber), magicNumber));
    }

    public void upgrade()
    {
        if(!upgraded)
        {
            upgradeName();
            upgradeBlock(3);
            upgradeMagicNumber(1);
        }
    }

    public AbstractCard makeCopy()
    {
        return new DEPRECATEDSurvey();
    }

    public static final String ID = "Survey";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Survey");
    }
}
