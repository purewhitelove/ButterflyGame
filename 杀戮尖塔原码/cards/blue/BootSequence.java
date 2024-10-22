// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BootSequence.java

package com.megacrit.cardcrawl.cards.blue;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BootSequence extends AbstractCard
{

    public BootSequence()
    {
        super("BootSequence", cardStrings.NAME, "blue/skill/boot_sequence", 0, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.BLUE, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.SELF);
        isInnate = true;
        exhaust = true;
        baseBlock = 10;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new GainBlockAction(p, p, block));
    }

    public void upgrade()
    {
        if(!upgraded)
        {
            upgradeName();
            upgradeBlock(3);
        }
    }

    public AbstractCard makeCopy()
    {
        return new BootSequence();
    }

    public static final String ID = "BootSequence";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("BootSequence");
    }
}
