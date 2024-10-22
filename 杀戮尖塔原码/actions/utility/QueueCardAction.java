// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   QueueCardAction.java

package com.megacrit.cardcrawl.actions.utility;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @deprecated Class QueueCardAction is deprecated
 */

public class QueueCardAction extends AbstractGameAction
{

    public QueueCardAction()
    {
        duration = Settings.ACTION_DUR_FAST;
    }

    public QueueCardAction(AbstractCard card, AbstractCreature target)
    {
        duration = Settings.ACTION_DUR_FAST;
        this.card = card;
        this.target = target;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_FAST)
        {
            if(card == null)
                AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem());
            else
            if(!queueContains(card))
                AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(card, (AbstractMonster)target));
            isDone = true;
        }
    }

    private boolean queueContains(AbstractCard card)
    {
        for(Iterator iterator = AbstractDungeon.actionManager.cardQueue.iterator(); iterator.hasNext();)
        {
            CardQueueItem i = (CardQueueItem)iterator.next();
            if(i.card == card)
                return true;
        }

        return false;
    }

    private AbstractCard card;
}
