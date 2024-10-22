// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChangeStateAction.java

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ChangeStateAction extends AbstractGameAction
{

    public ChangeStateAction(AbstractMonster monster, String stateName)
    {
        called = false;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.SPECIAL;
        m = monster;
        this.stateName = stateName;
    }

    public void update()
    {
        if(!called)
        {
            m.changeState(stateName);
            called = true;
            isDone = true;
        }
    }

    private boolean called;
    private AbstractMonster m;
    private String stateName;
}
