// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RestoreRetainedCardsAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;
import java.util.Iterator;

public class RestoreRetainedCardsAction extends AbstractGameAction
{

    public RestoreRetainedCardsAction(CardGroup group)
    {
        setValues(AbstractDungeon.player, source, -1);
        this.group = group;
    }

    public void update()
    {
        isDone = true;
        Iterator c = group.group.iterator();
        do
        {
            if(!c.hasNext())
                break;
            AbstractCard e = (AbstractCard)c.next();
            if(e.retain || e.selfRetain)
            {
                e.onRetained();
                AbstractDungeon.player.hand.addToTop(e);
                e.retain = false;
                c.remove();
            }
        } while(true);
        AbstractDungeon.player.hand.refreshHandLayout();
    }

    private CardGroup group;
}
