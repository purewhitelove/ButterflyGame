// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DEPRECATEDCleanseEvil.java

package com.megacrit.cardcrawl.cards.deprecated;

import com.megacrit.cardcrawl.actions.watcher.DivinePunishmentAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Smite;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DEPRECATEDCleanseEvil extends AbstractCard
{

    public DEPRECATEDCleanseEvil()
    {
        super("CleanseEvil", cardStrings.NAME, "purple/skill/cleanse_evil", -1, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.PURPLE, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.SELF);
        cardsToPreview = new Smite();
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractCard c = new Smite();
        if(upgraded)
            c.upgrade();
        addToBot(new DivinePunishmentAction(c, freeToPlayOnce, energyOnUse));
    }

    public void upgrade()
    {
        if(!upgraded)
        {
            upgradeName();
            cardsToPreview.upgrade();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    public AbstractCard makeCopy()
    {
        return new DEPRECATEDCleanseEvil();
    }

    public static final String ID = "CleanseEvil";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("CleanseEvil");
    }
}
