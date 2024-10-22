// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MeditateAction.java

package com.megacrit.cardcrawl.actions.watcher;

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

public class MeditateAction extends AbstractGameAction
{

    public MeditateAction(int numberOfCards)
    {
        optional = false;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION;
        duration = startDuration = Settings.ACTION_DUR_FAST;
        player = AbstractDungeon.player;
        this.numberOfCards = numberOfCards;
    }

    public void update()
    {
        if(duration == startDuration)
        {
            if(player.discardPile.isEmpty() || numberOfCards <= 0)
            {
                isDone = true;
                return;
            }
            if(player.discardPile.size() <= numberOfCards && !optional)
            {
                ArrayList cardsToMove = new ArrayList();
                for(Iterator iterator2 = player.discardPile.group.iterator(); iterator2.hasNext();)
                {
                    AbstractCard c = (AbstractCard)iterator2.next();
                    cardsToMove.add(c);
                    c.retain = true;
                }

                AbstractCard c;
                for(Iterator iterator3 = cardsToMove.iterator(); iterator3.hasNext(); c.lighten(false))
                {
                    c = (AbstractCard)iterator3.next();
                    if(player.hand.size() < 10)
                    {
                        player.hand.addToHand(c);
                        player.discardPile.removeCard(c);
                    }
                }

                isDone = true;
                return;
            }
            if(numberOfCards == 1)
            {
                if(optional)
                    AbstractDungeon.gridSelectScreen.open(player.discardPile, numberOfCards, true, TEXT[0]);
                else
                    AbstractDungeon.gridSelectScreen.open(player.discardPile, numberOfCards, TEXT[0], false);
            } else
            if(optional)
                AbstractDungeon.gridSelectScreen.open(player.discardPile, numberOfCards, true, (new StringBuilder()).append(TEXT[1]).append(numberOfCards).append(TEXT[2]).toString());
            else
                AbstractDungeon.gridSelectScreen.open(player.discardPile, numberOfCards, (new StringBuilder()).append(TEXT[1]).append(numberOfCards).append(TEXT[2]).toString(), false);
            tickDuration();
            return;
        }
        if(!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
        {
            AbstractCard c;
            for(Iterator iterator = AbstractDungeon.gridSelectScreen.selectedCards.iterator(); iterator.hasNext(); c.unhover())
            {
                c = (AbstractCard)iterator.next();
                if(player.hand.size() < 10)
                {
                    player.hand.addToHand(c);
                    c.retain = true;
                    player.discardPile.removeCard(c);
                }
                c.lighten(false);
            }

            for(Iterator iterator1 = player.discardPile.group.iterator(); iterator1.hasNext();)
            {
                AbstractCard c = (AbstractCard)iterator1.next();
                c.unhover();
                c.target_x = CardGroup.DISCARD_PILE_X;
                c.target_y = 0.0F;
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
