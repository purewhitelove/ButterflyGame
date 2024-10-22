// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RemoveDebuffsAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import java.util.ArrayList;
import java.util.Iterator;

public class RemoveDebuffsAction extends AbstractGameAction
{

    public RemoveDebuffsAction(AbstractCreature c)
    {
        this.c = c;
        duration = 0.5F;
    }

    public void update()
    {
        Iterator iterator = c.powers.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractPower p = (AbstractPower)iterator.next();
            if(p.type == com.megacrit.cardcrawl.powers.AbstractPower.PowerType.DEBUFF)
                addToTop(new RemoveSpecificPowerAction(c, c, p.ID));
        } while(true);
        isDone = true;
    }

    private AbstractCreature c;
}
