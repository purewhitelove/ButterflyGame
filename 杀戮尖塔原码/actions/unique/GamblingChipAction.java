// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GamblingChipAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
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

public class GamblingChipAction extends AbstractGameAction
{

    public GamblingChipAction(AbstractCreature source)
    {
        setValues(AbstractDungeon.player, source, -1);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION;
        notchip = false;
    }

    public GamblingChipAction(AbstractCreature source, boolean notChip)
    {
        setValues(AbstractDungeon.player, source, -1);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION;
        notchip = notChip;
    }

    public void update()
    {
        if(duration == 0.5F)
        {
            if(notchip)
                AbstractDungeon.handCardSelectScreen.open(TEXT[1], 99, true, true);
            else
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 99, true, true);
            addToBot(new WaitAction(0.25F));
            tickDuration();
            return;
        }
        if(!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            if(!AbstractDungeon.handCardSelectScreen.selectedCards.group.isEmpty())
            {
                addToTop(new DrawCardAction(p, AbstractDungeon.handCardSelectScreen.selectedCards.group.size()));
                AbstractCard c;
                for(Iterator iterator = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator(); iterator.hasNext(); c.triggerOnManualDiscard())
                {
                    c = (AbstractCard)iterator.next();
                    AbstractDungeon.player.hand.moveToDiscardPile(c);
                    GameActionManager.incrementDiscard(false);
                }

            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        tickDuration();
    }

    private AbstractPlayer p;
    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private boolean notchip;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("GamblingChipAction");
        TEXT = uiStrings.TEXT;
    }
}
