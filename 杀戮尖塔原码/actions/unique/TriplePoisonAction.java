// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TriplePoisonAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class TriplePoisonAction extends AbstractGameAction
{

    public TriplePoisonAction(AbstractCreature target, AbstractCreature source)
    {
        this.target = target;
        this.source = source;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DEBUFF;
        attackEffect = com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE;
    }

    public void update()
    {
        if(target.hasPower("Poison"))
            addToTop(new ApplyPowerAction(target, source, new PoisonPower(target, source, target.getPower("Poison").amount * 2), target.getPower("Poison").amount * 2));
        isDone = true;
    }
}
