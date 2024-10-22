// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TransmuteAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;
import java.util.Iterator;

public class TransmuteAction extends AbstractGameAction
{

    public TransmuteAction()
    {
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.WAIT;
        p = AbstractDungeon.player;
        duration = Settings.ACTION_DUR_FAST;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_FAST)
        {
            AbstractDungeon.actionManager.cleanCardQueue();
            if(p.hand.group.isEmpty())
            {
                isDone = true;
                return;
            }
            CardGroup tmp = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);
            tmp.group.addAll(p.hand.group);
            p.hand.clear();
            AbstractCard transformedCard;
            for(Iterator iterator = tmp.group.iterator(); iterator.hasNext(); p.hand.addToTop(transformedCard))
            {
                AbstractCard c = (AbstractCard)iterator.next();
                AbstractDungeon.transformCard(c);
                transformedCard = AbstractDungeon.getTransformedCard();
            }

            tickDuration();
            return;
        } else
        {
            p.hand.refreshHandLayout();
            isDone = true;
            return;
        }
    }

    private AbstractPlayer p;
}
