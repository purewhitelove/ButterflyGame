// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EndTurnAction.java

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.EnemyTurnEffect;
import java.util.ArrayList;

public class EndTurnAction extends AbstractGameAction
{

    public EndTurnAction()
    {
    }

    public void update()
    {
        AbstractDungeon.actionManager.endTurn();
        if(!AbstractDungeon.getCurrRoom().skipMonsterTurn)
            AbstractDungeon.topLevelEffects.add(new EnemyTurnEffect());
        isDone = true;
    }
}
