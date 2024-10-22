// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BendAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.select.HandCardSelectScreen;
import java.util.ArrayList;
import java.util.Iterator;

public class BendAction extends AbstractGameAction
{

    public BendAction()
    {
        cannotChoose = new ArrayList();
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION;
        p = AbstractDungeon.player;
        duration = Settings.ACTION_DUR_FAST;
    }

    public void update()
    {
label0:
        {
label1:
            {
                if(duration != Settings.ACTION_DUR_FAST)
                    break label0;
                Iterator iterator = p.hand.group.iterator();
                AbstractCard c;
                do
                {
                    if(!iterator.hasNext())
                        break;
                    c = (AbstractCard)iterator.next();
                    if(c.type != com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK || c.cost <= 0)
                        cannotChoose.add(c);
                } while(true);
                if(cannotChoose.size() == p.hand.group.size())
                {
                    isDone = true;
                    return;
                }
                if(p.hand.group.size() - cannotChoose.size() != 1)
                    break label1;
                iterator = p.hand.group.iterator();
                do
                {
                    if(!iterator.hasNext())
                        break label1;
                    c = (AbstractCard)iterator.next();
                } while(c.type != com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK);
                c.modifyCostForCombat(-1);
                isDone = true;
                return;
            }
            p.hand.group.removeAll(cannotChoose);
            if(p.hand.group.size() > 1)
            {
                AbstractDungeon.handCardSelectScreen.open("Upgrade.", 1, false);
                tickDuration();
                return;
            }
            if(p.hand.group.size() == 1)
            {
                p.hand.getTopCard().modifyCostForCombat(-1);
                returnCards();
                isDone = true;
            }
        }
        if(!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            AbstractCard c;
            for(Iterator iterator1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator(); iterator1.hasNext(); p.hand.addToTop(c))
            {
                c = (AbstractCard)iterator1.next();
                c.modifyCostForCombat(-1);
            }

            returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            isDone = true;
        }
        tickDuration();
    }

    private void returnCards()
    {
        AbstractCard c;
        for(Iterator iterator = cannotChoose.iterator(); iterator.hasNext(); p.hand.addToTop(c))
            c = (AbstractCard)iterator.next();

        p.hand.refreshHandLayout();
    }

    private AbstractPlayer p;
    private ArrayList cannotChoose;
}
