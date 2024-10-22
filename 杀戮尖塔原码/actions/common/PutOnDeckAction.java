// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PutOnDeckAction.java

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.select.HandCardSelectScreen;
import java.util.ArrayList;
import java.util.Iterator;

public class PutOnDeckAction extends AbstractGameAction
{

    public PutOnDeckAction(AbstractCreature target, AbstractCreature source, int amount, boolean isRandom)
    {
        this.target = target;
        p = (AbstractPlayer)target;
        setValues(target, source, amount);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.isRandom = isRandom;
    }

    public void update()
    {
        if(duration == 0.5F)
        {
            if(p.hand.size() < amount)
                amount = p.hand.size();
            if(isRandom)
            {
                for(int i = 0; i < amount; i++)
                    p.hand.moveToDeck(p.hand.getRandomCard(AbstractDungeon.cardRandomRng), false);

            } else
            {
                if(p.hand.group.size() > amount)
                {
                    numPlaced = amount;
                    AbstractDungeon.handCardSelectScreen.open(TEXT[0], amount, false);
                    tickDuration();
                    return;
                }
                for(int i = 0; i < p.hand.size(); i++)
                    p.hand.moveToDeck(p.hand.getRandomCard(AbstractDungeon.cardRandomRng), isRandom);

            }
        }
        if(!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            AbstractCard c;
            for(Iterator iterator = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator(); iterator.hasNext(); p.hand.moveToDeck(c, false))
                c = (AbstractCard)iterator.next();

            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        tickDuration();
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private AbstractPlayer p;
    private boolean isRandom;
    public static int numPlaced;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("PutOnDeckAction");
        TEXT = uiStrings.TEXT;
    }
}
