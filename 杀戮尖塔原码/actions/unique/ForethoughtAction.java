// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ForethoughtAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.select.HandCardSelectScreen;
import java.util.ArrayList;
import java.util.Iterator;

public class ForethoughtAction extends AbstractGameAction
{

    public ForethoughtAction(boolean upgraded)
    {
        p = AbstractDungeon.player;
        duration = Settings.ACTION_DUR_FAST;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION;
        chooseAny = upgraded;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_FAST)
        {
            if(p.hand.isEmpty())
            {
                isDone = true;
                return;
            }
            if(p.hand.size() == 1 && !chooseAny)
            {
                AbstractCard c = p.hand.getTopCard();
                if(c.cost > 0)
                    c.freeToPlayOnce = true;
                p.hand.moveToBottomOfDeck(c);
                AbstractDungeon.player.hand.refreshHandLayout();
                isDone = true;
                return;
            }
            if(!chooseAny)
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false);
            else
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 99, true, true);
            tickDuration();
            return;
        }
        if(!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            AbstractCard c;
            for(Iterator iterator = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator(); iterator.hasNext(); p.hand.moveToBottomOfDeck(c))
            {
                c = (AbstractCard)iterator.next();
                if(c.cost > 0)
                    c.freeToPlayOnce = true;
            }

            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        tickDuration();
    }

    private AbstractPlayer p;
    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private boolean chooseAny;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("ForethoughtAction");
        TEXT = uiStrings.TEXT;
    }
}
