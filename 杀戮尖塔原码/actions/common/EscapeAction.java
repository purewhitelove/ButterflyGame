// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EscapeAction.java

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EscapeAction extends AbstractGameAction
{

    public EscapeAction(AbstractMonster source)
    {
        setValues(source, source);
        duration = 0.5F;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.TEXT;
    }

    public void update()
    {
        if(duration == 0.5F)
        {
            AbstractMonster m = (AbstractMonster)source;
            m.escape();
        }
        tickDuration();
    }
}
