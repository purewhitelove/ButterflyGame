// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Transmutation.java

package com.megacrit.cardcrawl.cards.colorless;

import com.megacrit.cardcrawl.actions.unique.TransmutationAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class Transmutation extends AbstractCard
{

    public Transmutation()
    {
        super("Transmutation", cardStrings.NAME, "colorless/skill/transmutation", -1, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.COLORLESS, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.RARE, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.SELF);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if(energyOnUse < EnergyPanel.totalCount)
            energyOnUse = EnergyPanel.totalCount;
        addToBot(new TransmutationAction(p, upgraded, freeToPlayOnce, energyOnUse));
    }

    public AbstractCard makeCopy()
    {
        return new Transmutation();
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

    public static final String ID = "Transmutation";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Transmutation");
    }
}
