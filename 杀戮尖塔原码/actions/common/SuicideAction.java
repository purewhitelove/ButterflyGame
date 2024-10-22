// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SuicideAction.java

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SuicideAction extends AbstractGameAction
{

    public SuicideAction(AbstractMonster target)
    {
        this(target, true);
    }

    public SuicideAction(AbstractMonster target, boolean triggerRelics)
    {
        duration = 0.0F;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DAMAGE;
        m = target;
        relicTrigger = triggerRelics;
    }

    public void update()
    {
        if(duration == 0.0F)
        {
            m.gold = 0;
            m.currentHealth = 0;
            m.die(relicTrigger);
            m.healthBarUpdatedEvent();
        }
        tickDuration();
    }

    private AbstractMonster m;
    private boolean relicTrigger;
}
