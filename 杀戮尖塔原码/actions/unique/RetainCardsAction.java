// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RetainCardsAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
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

public class RetainCardsAction extends AbstractGameAction
{

    public RetainCardsAction(AbstractCreature source, int amount)
    {
        setValues(AbstractDungeon.player, source, amount);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION;
    }

    public void update()
    {
        if(duration == 0.5F)
        {
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], amount, false, true, false, false, true);
            addToBot(new WaitAction(0.25F));
            tickDuration();
            return;
        }
        if(!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            AbstractCard c;
            for(Iterator iterator = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator(); iterator.hasNext(); AbstractDungeon.player.hand.addToTop(c))
            {
                c = (AbstractCard)iterator.next();
                if(!c.isEthereal)
                    c.retain = true;
            }

            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        tickDuration();
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("RetainCardsAction");
        TEXT = uiStrings.TEXT;
    }
}
