// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ForTheEyesAction.java

package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

public class ForTheEyesAction extends AbstractGameAction
{

    public ForTheEyesAction(int weakAmt, AbstractMonster m)
    {
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.WAIT;
        amount = weakAmt;
        this.m = m;
    }

    public void update()
    {
        if(m != null && m.getIntentBaseDmg() >= 0)
            addToTop(new ApplyPowerAction(m, AbstractDungeon.player, new WeakPower(m, amount, false), amount));
        isDone = true;
    }

    private AbstractMonster m;
}
