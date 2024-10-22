// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DiscardPileToTopOfDeckAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import java.util.ArrayList;
import java.util.Iterator;

public class DiscardPileToTopOfDeckAction extends AbstractGameAction
{

    public DiscardPileToTopOfDeckAction(AbstractCreature source)
    {
        p = AbstractDungeon.player;
        setValues(null, source, amount);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_FASTER;
    }

    public void update()
    {
        if(AbstractDungeon.getCurrRoom().isBattleEnding())
        {
            isDone = true;
            return;
        }
        if(duration == Settings.ACTION_DUR_FASTER)
        {
            if(p.discardPile.isEmpty())
            {
                isDone = true;
                return;
            }
            if(p.discardPile.size() == 1)
            {
                AbstractCard tmp = p.discardPile.getTopCard();
                p.discardPile.removeCard(tmp);
                p.discardPile.moveToDeck(tmp, false);
            }
            if(p.discardPile.group.size() > amount)
            {
                AbstractDungeon.gridSelectScreen.open(p.discardPile, 1, TEXT[0], false, false, false, false);
                tickDuration();
                return;
            }
        }
        if(!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
        {
            AbstractCard c;
            for(Iterator iterator = AbstractDungeon.gridSelectScreen.selectedCards.iterator(); iterator.hasNext(); p.hand.moveToDeck(c, false))
            {
                c = (AbstractCard)iterator.next();
                p.discardPile.removeCard(c);
            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            AbstractDungeon.player.hand.refreshHandLayout();
        }
        tickDuration();
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private AbstractPlayer p;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("DiscardPileToTopOfDeckAction");
        TEXT = uiStrings.TEXT;
    }
}
