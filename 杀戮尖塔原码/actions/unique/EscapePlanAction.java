// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EscapePlanAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;
import java.util.Iterator;

public class EscapePlanAction extends AbstractGameAction
{

    public EscapePlanAction(int blockGain)
    {
        duration = 0.0F;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.WAIT;
        this.blockGain = blockGain;
    }

    public void update()
    {
        Iterator iterator = DrawCardAction.drawnCards.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.type != com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL)
                continue;
            AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, blockGain));
            break;
        } while(true);
        isDone = true;
    }

    private int blockGain;
}
