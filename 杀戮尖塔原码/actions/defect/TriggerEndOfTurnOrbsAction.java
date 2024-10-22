// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TriggerEndOfTurnOrbsAction.java

package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.relics.GoldPlatedCables;
import java.util.ArrayList;
import java.util.Iterator;

public class TriggerEndOfTurnOrbsAction extends AbstractGameAction
{

    public TriggerEndOfTurnOrbsAction()
    {
    }

    public void update()
    {
        if(!AbstractDungeon.player.orbs.isEmpty())
        {
            AbstractOrb o;
            for(Iterator iterator = AbstractDungeon.player.orbs.iterator(); iterator.hasNext(); o.onEndOfTurn())
                o = (AbstractOrb)iterator.next();

            if(AbstractDungeon.player.hasRelic("Cables") && !(AbstractDungeon.player.orbs.get(0) instanceof EmptyOrbSlot))
                ((AbstractOrb)AbstractDungeon.player.orbs.get(0)).onEndOfTurn();
        }
        isDone = true;
    }
}
