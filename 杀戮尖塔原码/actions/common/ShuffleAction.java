// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ShuffleAction.java

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import java.util.ArrayList;
import java.util.Iterator;

public class ShuffleAction extends AbstractGameAction
{

    public ShuffleAction(CardGroup theGroup)
    {
        this(theGroup, false);
    }

    public ShuffleAction(CardGroup theGroup, boolean trigger)
    {
        setValues(null, null, 0);
        duration = 0.0F;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.SHUFFLE;
        group = theGroup;
        triggerRelics = trigger;
    }

    public void update()
    {
        if(triggerRelics)
        {
            AbstractRelic r;
            for(Iterator iterator = AbstractDungeon.player.relics.iterator(); iterator.hasNext(); r.onShuffle())
                r = (AbstractRelic)iterator.next();

        }
        group.shuffle();
        isDone = true;
    }

    private CardGroup group;
    private boolean triggerRelics;
}
