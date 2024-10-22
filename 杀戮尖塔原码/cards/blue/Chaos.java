// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Chaos.java

package com.megacrit.cardcrawl.cards.blue;

import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class Chaos extends AbstractCard
{

    public Chaos()
    {
        super("Chaos", cardStrings.NAME, "blue/skill/chaos", 1, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.BLUE, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.SELF);
        showEvokeValue = true;
        showEvokeOrbCount = 1;
        baseMagicNumber = 1;
        magicNumber = baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if(upgraded)
            addToBot(new ChannelAction(AbstractOrb.getRandomOrb(true)));
        addToBot(new ChannelAction(AbstractOrb.getRandomOrb(true)));
    }

    public void upgrade()
    {
        if(!upgraded)
        {
            upgradeName();
            upgradeMagicNumber(1);
            showEvokeOrbCount = 2;
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    public AbstractCard makeCopy()
    {
        return new Chaos();
    }

    public static final String ID = "Chaos";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Chaos");
    }
}
