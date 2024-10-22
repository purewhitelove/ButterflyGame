// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MonsterStartTurnAction.java

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class MonsterStartTurnAction extends AbstractGameAction
{

    public MonsterStartTurnAction()
    {
        duration = DURATION;
    }

    public void update()
    {
        if(duration == DURATION)
        {
            isDone = true;
            AbstractDungeon.getCurrRoom().monsters.applyPreTurnLogic();
        }
        tickDuration();
    }

    private static final float DURATION;

    static 
    {
        DURATION = Settings.ACTION_DUR_FAST;
    }
}
