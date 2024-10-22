// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HeelHookAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.WeakPower;

public class HeelHookAction extends AbstractGameAction
{

    public HeelHookAction(AbstractCreature target, DamageInfo info)
    {
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.BLOCK;
        this.target = target;
        this.info = info;
    }

    public void update()
    {
        if(target != null && target.hasPower("Weakened"))
        {
            addToTop(new DrawCardAction(AbstractDungeon.player, 1));
            addToTop(new GainEnergyAction(1));
        }
        addToTop(new DamageAction(target, info, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        isDone = true;
    }

    private DamageInfo info;
}
