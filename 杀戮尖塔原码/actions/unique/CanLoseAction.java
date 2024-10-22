// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CanLoseAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class CanLoseAction extends AbstractGameAction
{

    public CanLoseAction()
    {
    }

    public void update()
    {
        AbstractDungeon.getCurrRoom().cannotLose = false;
        isDone = true;
    }
}
