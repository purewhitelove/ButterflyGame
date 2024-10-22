// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ClearCardQueueAction.java

package com.megacrit.cardcrawl.actions;

import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.actions:
//            AbstractGameAction, GameActionManager

public class ClearCardQueueAction extends AbstractGameAction
{

    public ClearCardQueueAction()
    {
    }

    public void update()
    {
        Iterator iterator = AbstractDungeon.actionManager.cardQueue.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            CardQueueItem c = (CardQueueItem)iterator.next();
            if(AbstractDungeon.player.limbo.contains(c.card))
            {
                AbstractDungeon.effectList.add(new ExhaustCardEffect(c.card));
                AbstractDungeon.player.limbo.group.remove(c.card);
            }
        } while(true);
        AbstractDungeon.actionManager.cardQueue.clear();
        isDone = true;
    }
}
