// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WaitAction.java

package com.megacrit.cardcrawl.actions.utility;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;

public class WaitAction extends AbstractGameAction
{

    public WaitAction(float setDur)
    {
        setValues(null, null, 0);
        if(Settings.FAST_MODE && setDur > 0.1F)
            duration = 0.1F;
        else
            duration = setDur;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.WAIT;
    }

    public void update()
    {
        tickDuration();
    }
}
