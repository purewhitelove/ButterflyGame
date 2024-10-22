// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PressEndTurnButtonAction.java

package com.megacrit.cardcrawl.actions.watcher;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class PressEndTurnButtonAction extends AbstractGameAction
{

    public PressEndTurnButtonAction()
    {
    }

    public void update()
    {
        AbstractDungeon.actionManager.callEndTurnEarlySequence();
        isDone = true;
    }
}
