// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CalculatedGambleAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class CalculatedGambleAction extends AbstractGameAction
{

    public CalculatedGambleAction(boolean upgraded)
    {
        target = AbstractDungeon.player;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.WAIT;
        startingDuration = Settings.ACTION_DUR_FAST;
        duration = Settings.ACTION_DUR_FAST;
        isUpgraded = upgraded;
    }

    public void update()
    {
        if(duration == startingDuration)
        {
            int count = AbstractDungeon.player.hand.size();
            if(isUpgraded)
            {
                addToTop(new DrawCardAction(target, count + 1));
                addToTop(new DiscardAction(target, target, count, true));
            } else
            if(count != 0)
            {
                addToTop(new DrawCardAction(target, count));
                addToTop(new DiscardAction(target, target, count, true));
            }
            isDone = true;
        }
    }

    private float startingDuration;
    private boolean isUpgraded;
}
