// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RandomizeHandCostAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.random.Random;
import java.util.ArrayList;
import java.util.Iterator;

public class RandomizeHandCostAction extends AbstractGameAction
{

    public RandomizeHandCostAction()
    {
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION;
        p = AbstractDungeon.player;
        duration = Settings.ACTION_DUR_FAST;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_FAST)
        {
            Iterator iterator = p.hand.group.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractCard card = (AbstractCard)iterator.next();
                if(card.cost >= 0)
                {
                    int newCost = AbstractDungeon.cardRandomRng.random(3);
                    if(card.cost != newCost)
                    {
                        card.cost = newCost;
                        card.costForTurn = card.cost;
                        card.isCostModified = true;
                    }
                }
            } while(true);
            isDone = true;
            return;
        } else
        {
            tickDuration();
            return;
        }
    }

    private AbstractPlayer p;
}
