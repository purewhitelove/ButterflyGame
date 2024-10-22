// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DiscardPileToHandAction.java

package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import java.util.ArrayList;
import java.util.Iterator;

public class DiscardPileToHandAction extends AbstractGameAction
{

    public DiscardPileToHandAction(int amount)
    {
        p = AbstractDungeon.player;
        setValues(p, AbstractDungeon.player, amount);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION;
    }

    public void update()
    {
        if(p.hand.size() >= 10)
        {
            isDone = true;
            return;
        }
        if(p.discardPile.size() == 1)
        {
            AbstractCard card = (AbstractCard)p.discardPile.group.get(0);
            if(p.hand.size() < 10)
            {
                p.hand.addToHand(card);
                p.discardPile.removeCard(card);
            }
            card.lighten(false);
            p.hand.refreshHandLayout();
            isDone = true;
            return;
        }
        if(duration == 0.5F)
        {
            AbstractDungeon.gridSelectScreen.open(p.discardPile, amount, TEXT[0], false);
            tickDuration();
            return;
        }
        if(AbstractDungeon.gridSelectScreen.selectedCards.size() != 0)
        {
            AbstractCard c;
            for(Iterator iterator = AbstractDungeon.gridSelectScreen.selectedCards.iterator(); iterator.hasNext(); c.unhover())
            {
                c = (AbstractCard)iterator.next();
                if(p.hand.size() < 10)
                {
                    p.hand.addToHand(c);
                    p.discardPile.removeCard(c);
                }
                c.lighten(false);
            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            p.hand.refreshHandLayout();
            for(Iterator iterator1 = p.discardPile.group.iterator(); iterator1.hasNext();)
            {
                AbstractCard c = (AbstractCard)iterator1.next();
                c.unhover();
                c.target_x = CardGroup.DISCARD_PILE_X;
                c.target_y = 0.0F;
            }

            isDone = true;
        }
        tickDuration();
    }

    public static final String TEXT[];
    private AbstractPlayer p;

    static 
    {
        TEXT = CardCrawlGame.languagePack.getUIString("DiscardPileToHandAction").TEXT;
    }
}
