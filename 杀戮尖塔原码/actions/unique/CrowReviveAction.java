// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CrowReviveAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.TintEffect;
import java.util.ArrayList;

public class CrowReviveAction extends AbstractGameAction
{

    public CrowReviveAction(AbstractMonster target, AbstractCreature source)
    {
        setValues(target, source, 0);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.SPECIAL;
    }

    public void update()
    {
        if(duration == 0.5F && (target instanceof AbstractMonster))
        {
            target.isDying = false;
            target.heal(target.maxHealth);
            target.healthBarRevivedEvent();
            ((AbstractMonster)target).deathTimer = 0.0F;
            ((AbstractMonster)target).tint = new TintEffect();
            ((AbstractMonster)target).tintFadeOutCalled = false;
            ((AbstractMonster)target).isDead = false;
            target.powers.clear();
        }
        tickDuration();
    }
}
