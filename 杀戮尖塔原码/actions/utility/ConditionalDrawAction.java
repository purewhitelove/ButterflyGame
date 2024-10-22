// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ConditionalDrawAction.java

package com.megacrit.cardcrawl.actions.utility;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;
import java.util.Iterator;

public class ConditionalDrawAction extends AbstractGameAction
{

    public ConditionalDrawAction(int newAmount, com.megacrit.cardcrawl.cards.AbstractCard.CardType restrictedType)
    {
        duration = Settings.ACTION_DUR_FAST;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.WAIT;
        source = AbstractDungeon.player;
        target = AbstractDungeon.player;
        amount = newAmount;
        this.restrictedType = restrictedType;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_FAST)
        {
            if(checkCondition())
                addToTop(new DrawCardAction(source, amount));
            isDone = true;
        }
    }

    private boolean checkCondition()
    {
        for(Iterator iterator = AbstractDungeon.player.hand.group.iterator(); iterator.hasNext();)
        {
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.type == restrictedType)
                return false;
        }

        return true;
    }

    private com.megacrit.cardcrawl.cards.AbstractCard.CardType restrictedType;
}
