// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JudgementAction.java

package com.megacrit.cardcrawl.actions.watcher;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.InstantKillAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class JudgementAction extends AbstractGameAction
{

    public JudgementAction(AbstractCreature target, int cutoff)
    {
        duration = Settings.ACTION_DUR_FAST;
        source = null;
        this.target = target;
        this.cutoff = cutoff;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_FAST && target.currentHealth <= cutoff && (target instanceof AbstractMonster))
            addToTop(new InstantKillAction(target));
        isDone = true;
    }

    private int cutoff;
}
