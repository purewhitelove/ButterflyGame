// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ReducePowerAction.java

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

// Referenced classes of package com.megacrit.cardcrawl.actions.common:
//            RemoveSpecificPowerAction

public class ReducePowerAction extends AbstractGameAction
{

    public ReducePowerAction(AbstractCreature target, AbstractCreature source, String power, int amount)
    {
        setValues(target, source, amount);
        if(Settings.FAST_MODE)
            duration = startDuration = Settings.ACTION_DUR_XFAST;
        else
            duration = startDuration = Settings.ACTION_DUR_FAST;
        powerID = power;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.REDUCE_POWER;
    }

    public ReducePowerAction(AbstractCreature target, AbstractCreature source, AbstractPower powerInstance, int amount)
    {
        setValues(target, source, amount);
        if(Settings.FAST_MODE)
            duration = startDuration = Settings.ACTION_DUR_XFAST;
        else
            duration = startDuration = Settings.ACTION_DUR_FAST;
        this.powerInstance = powerInstance;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.REDUCE_POWER;
    }

    public void update()
    {
        if(duration == startDuration)
        {
            AbstractPower reduceMe = null;
            if(powerID != null)
                reduceMe = target.getPower(powerID);
            else
            if(powerInstance != null)
                reduceMe = powerInstance;
            if(reduceMe != null)
                if(amount < reduceMe.amount)
                {
                    reduceMe.reducePower(amount);
                    reduceMe.updateDescription();
                    AbstractDungeon.onModifyPower();
                } else
                {
                    addToTop(new RemoveSpecificPowerAction(target, source, reduceMe));
                }
        }
        tickDuration();
    }

    private String powerID;
    private AbstractPower powerInstance;
}
