// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NotStanceCheckAction.java

package com.megacrit.cardcrawl.actions.watcher;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.AbstractStance;

public class NotStanceCheckAction extends AbstractGameAction
{

    public NotStanceCheckAction(String stanceToCheck, AbstractGameAction actionToCheck)
    {
        this.stanceToCheck = null;
        actionToBuffer = actionToCheck;
        this.stanceToCheck = stanceToCheck;
    }

    public void update()
    {
        if(!AbstractDungeon.player.stance.ID.equals(stanceToCheck))
            addToBot(actionToBuffer);
        isDone = true;
    }

    private AbstractGameAction actionToBuffer;
    private String stanceToCheck;
}
