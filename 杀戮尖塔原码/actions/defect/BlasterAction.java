// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BlasterAction.java

package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import java.util.ArrayList;
import java.util.Iterator;

public class BlasterAction extends AbstractGameAction
{

    public BlasterAction()
    {
        duration = Settings.ACTION_DUR_FAST;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_FAST)
        {
            int counter = 0;
            Iterator iterator = AbstractDungeon.player.orbs.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractOrb o = (AbstractOrb)iterator.next();
                if(!(o instanceof EmptyOrbSlot))
                    counter++;
            } while(true);
            if(counter != 0)
                addToBot(new GainEnergyAction(counter));
        }
        tickDuration();
    }
}
