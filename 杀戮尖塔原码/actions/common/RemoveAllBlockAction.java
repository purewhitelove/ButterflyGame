// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RemoveAllBlockAction.java

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class RemoveAllBlockAction extends AbstractGameAction
{

    public RemoveAllBlockAction(AbstractCreature target, AbstractCreature source)
    {
        setValues(target, source, amount);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.BLOCK;
        duration = 0.25F;
    }

    public void update()
    {
        if(!target.isDying && !target.isDead && duration == 0.25F && target.currentBlock > 0)
            target.loseBlock();
        tickDuration();
    }

    private static final float DUR = 0.25F;
}
