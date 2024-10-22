// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SkipEnemiesTurnAction.java

package com.megacrit.cardcrawl.actions.watcher;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class SkipEnemiesTurnAction extends AbstractGameAction
{

    public SkipEnemiesTurnAction()
    {
    }

    public void update()
    {
        AbstractDungeon.getCurrRoom().skipMonsterTurn = true;
        isDone = true;
    }
}
