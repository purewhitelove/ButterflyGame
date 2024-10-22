// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ForceField.java

package com.megacrit.cardcrawl.cards.blue;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.ArrayList;
import java.util.Iterator;

public class ForceField extends AbstractCard
{

    public ForceField()
    {
        super("Force Field", cardStrings.NAME, "blue/skill/forcefield", 4, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.BLUE, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.SELF);
        baseBlock = 12;
        if(CardCrawlGame.dungeon != null && AbstractDungeon.currMapNode != null)
            configureCostsOnNewCard();
    }

    public void configureCostsOnNewCard()
    {
        Iterator iterator = AbstractDungeon.actionManager.cardsPlayedThisCombat.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.POWER)
                updateCost(-1);
        } while(true);
    }

    public void triggerOnCardPlayed(AbstractCard c)
    {
        if(c.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.POWER)
            updateCost(-1);
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
            upgradeBlock(4);
        }
    }

    public AbstractCard makeCopy()
    {
        return new ForceField();
    }

    public static final String ID = "Force Field";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Force Field");
    }
}
