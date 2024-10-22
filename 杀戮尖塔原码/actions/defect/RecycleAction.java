// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RecycleAction.java

package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.select.HandCardSelectScreen;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import java.util.ArrayList;
import java.util.Iterator;

public class RecycleAction extends AbstractGameAction
{

    public RecycleAction()
    {
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION;
        p = AbstractDungeon.player;
        duration = Settings.ACTION_DUR_FAST;
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
            if(p.hand.size() == 1)
            {
                if(p.hand.getBottomCard().costForTurn == -1)
                    addToTop(new GainEnergyAction(EnergyPanel.getCurrentEnergy()));
                else
                if(p.hand.getBottomCard().costForTurn > 0)
                    addToTop(new GainEnergyAction(p.hand.getBottomCard().costForTurn));
                p.hand.moveToExhaustPile(p.hand.getBottomCard());
                tickDuration();
                return;
            } else
            {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false);
                tickDuration();
                return;
            }
        }
        if(!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            AbstractCard c;
            for(Iterator iterator = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator(); iterator.hasNext(); p.hand.moveToExhaustPile(c))
            {
                c = (AbstractCard)iterator.next();
                if(c.costForTurn == -1)
                {
                    addToTop(new GainEnergyAction(EnergyPanel.getCurrentEnergy()));
                    continue;
                }
                if(c.costForTurn > 0)
                    addToTop(new GainEnergyAction(c.costForTurn));
            }

            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
        }
        tickDuration();
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private AbstractPlayer p;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("RecycleAction");
        TEXT = uiStrings.TEXT;
    }
}
