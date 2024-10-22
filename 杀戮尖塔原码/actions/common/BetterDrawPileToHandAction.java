// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BetterDrawPileToHandAction.java

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import java.util.ArrayList;
import java.util.Iterator;

public class BetterDrawPileToHandAction extends AbstractGameAction
{

    public BetterDrawPileToHandAction(int numberOfCards, boolean optional)
    {
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION;
        duration = startDuration = Settings.ACTION_DUR_FAST;
        player = AbstractDungeon.player;
        this.numberOfCards = numberOfCards;
        this.optional = optional;
    }

    public BetterDrawPileToHandAction(int numberOfCards)
    {
        this(numberOfCards, false);
    }

    public void update()
    {
        if(duration == startDuration)
        {
            if(player.drawPile.isEmpty() || numberOfCards <= 0)
            {
                isDone = true;
                return;
            }
            if(player.drawPile.size() <= numberOfCards && !optional)
            {
                ArrayList cardsToMove = new ArrayList();
                AbstractCard c;
                for(Iterator iterator1 = player.drawPile.group.iterator(); iterator1.hasNext(); cardsToMove.add(c))
                    c = (AbstractCard)iterator1.next();

                for(Iterator iterator2 = cardsToMove.iterator(); iterator2.hasNext();)
                {
                    AbstractCard c = (AbstractCard)iterator2.next();
                    if(player.hand.size() == 10)
                    {
                        player.drawPile.moveToDiscardPile(c);
                        player.createHandIsFullDialog();
                    } else
                    {
                        player.drawPile.moveToHand(c, player.drawPile);
                    }
                }

                isDone = true;
                return;
            }
            CardGroup temp = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);
            AbstractCard c;
            for(Iterator iterator3 = player.drawPile.group.iterator(); iterator3.hasNext(); temp.addToTop(c))
                c = (AbstractCard)iterator3.next();

            temp.sortAlphabetically(true);
            temp.sortByRarityPlusStatusCardType(false);
            if(numberOfCards == 1)
            {
                if(optional)
                    AbstractDungeon.gridSelectScreen.open(temp, numberOfCards, true, TEXT[0]);
                else
                    AbstractDungeon.gridSelectScreen.open(temp, numberOfCards, TEXT[0], false);
            } else
            if(optional)
                AbstractDungeon.gridSelectScreen.open(temp, numberOfCards, true, (new StringBuilder()).append(TEXT[1]).append(numberOfCards).append(TEXT[2]).toString());
            else
                AbstractDungeon.gridSelectScreen.open(temp, numberOfCards, (new StringBuilder()).append(TEXT[1]).append(numberOfCards).append(TEXT[2]).toString(), false);
            tickDuration();
            return;
        }
        if(!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
        {
            for(Iterator iterator = AbstractDungeon.gridSelectScreen.selectedCards.iterator(); iterator.hasNext();)
            {
                AbstractCard c = (AbstractCard)iterator.next();
                if(player.hand.size() == 10)
                {
                    player.drawPile.moveToDiscardPile(c);
                    player.createHandIsFullDialog();
                } else
                {
                    player.drawPile.moveToHand(c, player.drawPile);
                }
            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            AbstractDungeon.player.hand.refreshHandLayout();
        }
        tickDuration();
    }

    public static final String TEXT[];
    private AbstractPlayer player;
    private int numberOfCards;
    private boolean optional;

    static 
    {
        TEXT = CardCrawlGame.languagePack.getUIString("BetterToHandAction").TEXT;
    }
}
