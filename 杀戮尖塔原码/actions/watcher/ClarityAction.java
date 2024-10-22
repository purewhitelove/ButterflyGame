// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ClarityAction.java

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

public class ClarityAction extends AbstractGameAction
{

    public ClarityAction(int numCards)
    {
        amount = numCards;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION;
        startingDuration = Settings.ACTION_DUR_FAST;
        duration = startingDuration;
    }

    public void update()
    {
        if(duration == startingDuration)
        {
            if(AbstractDungeon.player.drawPile.isEmpty())
            {
                isDone = true;
                return;
            }
            CardGroup tmpGroup = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);
            for(int i = 0; i < Math.min(amount, AbstractDungeon.player.drawPile.size()); i++)
                tmpGroup.addToTop((AbstractCard)AbstractDungeon.player.drawPile.group.get(AbstractDungeon.player.drawPile.size() - i - 1));

            AbstractDungeon.gridSelectScreen.open(tmpGroup, amount, false, TEXT[0]);
        } else
        if(!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
        {
            AbstractCard c;
            for(Iterator iterator = AbstractDungeon.gridSelectScreen.selectedCards.iterator(); iterator.hasNext(); AbstractDungeon.player.drawPile.moveToHand(c, AbstractDungeon.player.drawPile))
                c = (AbstractCard)iterator.next();

            Iterator iterator1 = AbstractDungeon.gridSelectScreen.targetGroup.group.iterator();
            do
            {
                if(!iterator1.hasNext())
                    break;
                AbstractCard c = (AbstractCard)iterator1.next();
                if(!AbstractDungeon.gridSelectScreen.selectedCards.contains(c))
                    AbstractDungeon.player.drawPile.moveToExhaustPile(c);
            } while(true);
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
        tickDuration();
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private float startingDuration;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("ClarityAction");
        TEXT = uiStrings.TEXT;
    }
}
