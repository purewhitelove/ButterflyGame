// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FastShakeAction.java

package com.megacrit.cardcrawl.actions.animations;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class FastShakeAction extends AbstractGameAction
{

    public FastShakeAction(AbstractCreature owner, float shakeDur, float actionDur)
    {
        called = false;
        setValues(null, owner, 0);
        duration = actionDur;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.WAIT;
        this.shakeDur = shakeDur;
    }

    public void update()
    {
        if(!called)
        {
            source.useShakeAnimation(shakeDur);
            called = true;
        }
        tickDuration();
    }

    private boolean called;
    private float shakeDur;
}
