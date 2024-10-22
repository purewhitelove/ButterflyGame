// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AllCostToHandAction.java

package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;
import java.util.Iterator;

public class AllCostToHandAction extends AbstractGameAction
{

    public AllCostToHandAction(int costToTarget)
    {
        p = AbstractDungeon.player;
        setValues(p, AbstractDungeon.player, amount);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION;
        costTarget = costToTarget;
    }

    public void update()
    {
        if(p.discardPile.size() > 0)
        {
            Iterator iterator = p.discardPile.group.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractCard card = (AbstractCard)iterator.next();
                if(card.cost == costTarget || card.freeToPlayOnce)
                    addToBot(new DiscardToHandAction(card));
            } while(true);
        }
        tickDuration();
        isDone = true;
    }

    private AbstractPlayer p;
    private int costTarget;
}
