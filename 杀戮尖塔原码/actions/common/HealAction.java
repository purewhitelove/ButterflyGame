// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HealAction.java

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;

public class HealAction extends AbstractGameAction
{

    public HealAction(AbstractCreature target, AbstractCreature source, int amount)
    {
        setValues(target, source, amount);
        startDuration = duration;
        if(Settings.FAST_MODE)
            duration = startDuration = Settings.ACTION_DUR_FAST;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.HEAL;
    }

    public HealAction(AbstractCreature target, AbstractCreature source, int amount, float duration)
    {
        this(target, source, amount);
        this.duration = startDuration = duration;
    }

    public void update()
    {
        if(duration == startDuration)
            target.heal(amount);
        tickDuration();
    }
}
