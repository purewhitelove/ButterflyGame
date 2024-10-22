// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CorpseExplosionAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class CorpseExplosionAction extends AbstractGameAction
{

    public CorpseExplosionAction(AbstractCreature target, AbstractCreature source)
    {
        this.target = target;
        this.source = source;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.WAIT;
        duration = Settings.ACTION_DUR_FAST;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_FAST)
            if(target.hasPower("Poison"))
                addToTop(new RemoveSpecificPowerAction(target, source, "Poison"));
            else
                isDone = true;
        tickDuration();
    }
}
