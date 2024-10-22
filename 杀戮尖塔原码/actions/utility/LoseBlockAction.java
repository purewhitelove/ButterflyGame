// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LoseBlockAction.java

package com.megacrit.cardcrawl.actions.utility;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class LoseBlockAction extends AbstractGameAction
{

    public LoseBlockAction(AbstractCreature target, AbstractCreature source, int amount)
    {
        setValues(target, source, amount);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.BLOCK;
    }

    public void update()
    {
        if(duration == 0.5F)
        {
            if(target.currentBlock == 0)
            {
                isDone = true;
                return;
            }
            target.loseBlock(amount);
        }
        tickDuration();
    }
}
