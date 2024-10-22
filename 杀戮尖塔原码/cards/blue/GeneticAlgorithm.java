// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GeneticAlgorithm.java

package com.megacrit.cardcrawl.cards.blue;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMiscAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class GeneticAlgorithm extends AbstractCard
{

    public GeneticAlgorithm()
    {
        super("Genetic Algorithm", cardStrings.NAME, "blue/skill/genetic_algorithm", 1, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.BLUE, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.SELF);
        misc = 1;
        baseMagicNumber = 2;
        magicNumber = baseMagicNumber;
        baseBlock = misc;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new IncreaseMiscAction(uuid, misc, magicNumber));
        addToBot(new GainBlockAction(p, p, block));
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
            upgradeMagicNumber(1);
            upgradeName();
        }
    }

    public AbstractCard makeCopy()
    {
        return new GeneticAlgorithm();
    }

    public static final String ID = "Genetic Algorithm";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Genetic Algorithm");
    }
}
