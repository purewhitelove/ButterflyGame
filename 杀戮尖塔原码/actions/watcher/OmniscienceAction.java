// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OmniscienceAction.java

package com.megacrit.cardcrawl.actions.watcher;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import java.util.ArrayList;
import java.util.Iterator;

public class OmniscienceAction extends AbstractGameAction
{

    public OmniscienceAction(int numberOfCards)
    {
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION;
        duration = startDuration = Settings.ACTION_DUR_FAST;
        player = AbstractDungeon.player;
        playAmt = numberOfCards;
    }

    public void update()
    {
        if(duration == startDuration)
        {
            if(player.drawPile.isEmpty())
            {
                isDone = true;
                return;
            }
            CardGroup temp = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);
            AbstractCard c;
            for(Iterator iterator1 = player.drawPile.group.iterator(); iterator1.hasNext(); temp.addToTop(c))
                c = (AbstractCard)iterator1.next();

            temp.sortAlphabetically(true);
            temp.sortByRarityPlusStatusCardType(false);
            AbstractDungeon.gridSelectScreen.open(temp, 1, TEXT[0], false);
            tickDuration();
            return;
        }
        if(!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
        {
            for(Iterator iterator = AbstractDungeon.gridSelectScreen.selectedCards.iterator(); iterator.hasNext();)
            {
                AbstractCard c = (AbstractCard)iterator.next();
                c.exhaust = true;
                AbstractDungeon.player.drawPile.group.remove(c);
                AbstractDungeon.getCurrRoom().souls.remove(c);
                addToBot(new NewQueueCardAction(c, true, false, true));
                int i = 0;
                while(i < playAmt - 1) 
                {
                    AbstractCard tmp = c.makeStatEquivalentCopy();
                    tmp.purgeOnUse = true;
                    addToBot(new NewQueueCardAction(tmp, true, false, true));
                    i++;
                }
            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            AbstractDungeon.player.hand.refreshHandLayout();
        }
        tickDuration();
    }

    public static final String TEXT[];
    private AbstractPlayer player;
    private int playAmt;

    static 
    {
        TEXT = CardCrawlGame.languagePack.getUIString("WishAction").TEXT;
    }
}
