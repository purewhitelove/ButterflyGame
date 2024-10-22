// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RollMoveAction.java

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class RollMoveAction extends AbstractGameAction
{

    public RollMoveAction(AbstractMonster monster)
    {
        this.monster = monster;
    }

    public void update()
    {
        monster.rollMove();
        isDone = true;
    }

    private AbstractMonster monster;
}
