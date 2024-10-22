// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PerfectedFormAction.java

package com.megacrit.cardcrawl.actions.watcher;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.DivinityStance;

public class PerfectedFormAction extends AbstractGameAction
{

    public PerfectedFormAction()
    {
    }

    public void update()
    {
        isDone = true;
        boolean hadCalm = false;
        boolean hadCourage = false;
        boolean hadWrath = false;
        if(AbstractDungeon.player.stance.ID.equals("Divinity"));
    }
}
