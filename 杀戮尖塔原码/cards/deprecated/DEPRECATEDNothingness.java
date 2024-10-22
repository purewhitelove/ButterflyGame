// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DEPRECATEDNothingness.java

package com.megacrit.cardcrawl.cards.deprecated;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.ArrayList;
import java.util.Iterator;

public class DEPRECATEDNothingness extends AbstractCard
{

    public DEPRECATEDNothingness()
    {
        super("Nothingness", cardStrings.NAME, "colorless/skill/purity", 1, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.PURPLE, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.SELF);
    }

    public static int countCards()
    {
        int count = 0;
        Iterator iterator = AbstractDungeon.player.hand.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(isEmpty(c))
                count++;
        } while(true);
        iterator = AbstractDungeon.player.drawPile.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(isEmpty(c))
                count++;
        } while(true);
        iterator = AbstractDungeon.player.discardPile.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(isEmpty(c))
                count++;
        } while(true);
        return count;
    }

    public static boolean isEmpty(AbstractCard c)
    {
        return c.hasTag(com.megacrit.cardcrawl.cards.AbstractCard.CardTags.EMPTY);
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if(upgraded)
            addToBot(new ScryAction(countCards()));
        addToBot(new DrawCardAction(p, countCards()));
    }

    public AbstractCard makeCopy()
    {
        return new DEPRECATEDNothingness();
    }

    public void upgrade()
    {
        if(!upgraded)
        {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    public static final String ID = "Nothingness";
    private static final CardStrings cardStrings;
    public static final String UPGRADE_DESCRIPTION;
    private static final int COST = 1;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Nothingness");
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }
}
