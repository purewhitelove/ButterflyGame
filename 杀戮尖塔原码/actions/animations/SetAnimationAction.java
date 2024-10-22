// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SetAnimationAction.java

package com.megacrit.cardcrawl.actions.animations;

import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;

public class SetAnimationAction extends AbstractGameAction
{

    public SetAnimationAction(AbstractCreature owner, String animationName)
    {
        called = false;
        setValues(null, owner, 0);
        duration = Settings.ACTION_DUR_FAST;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.WAIT;
        animation = animationName;
    }

    public void update()
    {
        if(!called)
        {
            source.state.setAnimation(0, animation, false);
            called = true;
            source.state.addAnimation(0, "idle", true, 0.0F);
        }
        tickDuration();
    }

    private boolean called;
    private String animation;
}
