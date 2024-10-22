// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CompileDriverAction.java

package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import java.util.ArrayList;
import java.util.Iterator;

public class CompileDriverAction extends AbstractGameAction
{

    public CompileDriverAction(AbstractPlayer source, int amount)
    {
        setValues(target, source, amount);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.WAIT;
    }

    public void update()
    {
        ArrayList orbList = new ArrayList();
        Iterator iterator = AbstractDungeon.player.orbs.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractOrb o = (AbstractOrb)iterator.next();
            if(o.ID != null && !o.ID.equals("Empty") && !orbList.contains(o.ID))
                orbList.add(o.ID);
        } while(true);
        int toDraw = orbList.size() * amount;
        if(toDraw > 0)
            addToTop(new DrawCardAction(source, toDraw));
        isDone = true;
    }
}
