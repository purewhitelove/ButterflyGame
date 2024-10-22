// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AnimateSlowAttackAction.java

package com.megacrit.cardcrawl.actions.animations;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;

public class AnimateSlowAttackAction extends AbstractGameAction
{

    public AnimateSlowAttackAction(AbstractCreature owner)
    {
        called = false;
        setValues(null, owner, 0);
        startDuration = 0.5F;
        duration = startDuration;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.WAIT;
    }

    public void update()
    {
        if(!called)
        {
            if(Settings.FAST_MODE)
            {
                source.useFastAttackAnimation();
                duration = Settings.ACTION_DUR_FAST;
            } else
            {
                source.useSlowAttackAnimation();
            }
            called = true;
        }
        tickDuration();
    }

    private boolean called;
}
