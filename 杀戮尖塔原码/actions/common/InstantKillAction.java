// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   InstantKillAction.java

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class InstantKillAction extends AbstractGameAction
{

    public InstantKillAction(AbstractCreature target)
    {
        source = null;
        this.target = target;
    }

    public void update()
    {
        target.currentHealth = 0;
        target.healthBarUpdatedEvent();
        target.damage(new DamageInfo(null, 0, com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS));
        isDone = true;
    }
}
