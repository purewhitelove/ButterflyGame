// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MadnessAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;
import java.util.Iterator;

public class MadnessAction extends AbstractGameAction
{

    public MadnessAction()
    {
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION;
        p = AbstractDungeon.player;
        duration = Settings.ACTION_DUR_FAST;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_FAST)
        {
            boolean betterPossible = false;
            boolean possible = false;
            Iterator iterator = p.hand.group.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractCard c = (AbstractCard)iterator.next();
                if(c.costForTurn > 0)
                    betterPossible = true;
                else
                if(c.cost > 0)
                    possible = true;
            } while(true);
            if(betterPossible || possible)
                findAndModifyCard(betterPossible);
        }
        tickDuration();
    }

    private void findAndModifyCard(boolean better)
    {
        AbstractCard c = p.hand.getRandomCard(AbstractDungeon.cardRandomRng);
        if(better)
        {
            if(c.costForTurn > 0)
            {
                c.cost = 0;
                c.costForTurn = 0;
                c.isCostModified = true;
                c.superFlash(Color.GOLD.cpy());
            } else
            {
                findAndModifyCard(better);
            }
        } else
        if(c.cost > 0)
        {
            c.cost = 0;
            c.costForTurn = 0;
            c.isCostModified = true;
            c.superFlash(Color.GOLD.cpy());
        } else
        {
            findAndModifyCard(better);
        }
    }

    private AbstractPlayer p;
}
