// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EnlightenmentAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;
import java.util.Iterator;

public class EnlightenmentAction extends AbstractGameAction
{

    public EnlightenmentAction(boolean forRestOfCombat)
    {
        forCombat = false;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION;
        p = AbstractDungeon.player;
        duration = Settings.ACTION_DUR_FAST;
        forCombat = forRestOfCombat;
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
                AbstractCard c = (AbstractCard)iterator.next();
                if(c.costForTurn > 1)
                {
                    c.costForTurn = 1;
                    c.isCostModifiedForTurn = true;
                }
                if(forCombat && c.cost > 1)
                {
                    c.cost = 1;
                    c.isCostModified = true;
                }
            } while(true);
        }
        tickDuration();
    }

    private AbstractPlayer p;
    private boolean forCombat;
}
