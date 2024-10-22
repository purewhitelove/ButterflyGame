// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Normality.java

package com.megacrit.cardcrawl.cards.curses;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.ArrayList;

public class Normality extends AbstractCard
{

    public Normality()
    {
        super("Normality", cardStrings.NAME, "curse/normality", -2, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.CURSE, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.CURSE, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.CURSE, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.NONE);
    }

    public boolean canPlay(AbstractCard card)
    {
        if(AbstractDungeon.actionManager.cardsPlayedThisTurn.size() >= 3)
        {
            card.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            return false;
        } else
        {
            return true;
        }
    }

    public void use(AbstractPlayer abstractplayer, AbstractMonster abstractmonster)
    {
    }

    public void applyPowers()
    {
        super.applyPowers();
        if(AbstractDungeon.actionManager.cardsPlayedThisTurn.size() == 0)
            rawDescription = (new StringBuilder()).append(cardStrings.EXTENDED_DESCRIPTION[1]).append(3).append(cardStrings.EXTENDED_DESCRIPTION[2]).toString();
        else
        if(AbstractDungeon.actionManager.cardsPlayedThisTurn.size() == 1)
            rawDescription = (new StringBuilder()).append(cardStrings.EXTENDED_DESCRIPTION[1]).append(3).append(cardStrings.EXTENDED_DESCRIPTION[3]).append(AbstractDungeon.actionManager.cardsPlayedThisTurn.size()).append(cardStrings.EXTENDED_DESCRIPTION[4]).toString();
        else
            rawDescription = (new StringBuilder()).append(cardStrings.EXTENDED_DESCRIPTION[1]).append(3).append(cardStrings.EXTENDED_DESCRIPTION[3]).append(AbstractDungeon.actionManager.cardsPlayedThisTurn.size()).append(cardStrings.EXTENDED_DESCRIPTION[5]).toString();
        initializeDescription();
    }

    public void upgrade()
    {
    }

    public AbstractCard makeCopy()
    {
        return new Normality();
    }

    public static final String ID = "Normality";
    private static final CardStrings cardStrings;
    private static final int PLAY_LIMIT = 3;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Normality");
    }
}
