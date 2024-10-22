// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DarkImpulseAction.java

package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.relics.GoldPlatedCables;
import java.util.ArrayList;
import java.util.Iterator;

public class DarkImpulseAction extends AbstractGameAction
{

    public DarkImpulseAction()
    {
        duration = Settings.ACTION_DUR_FAST;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_FAST && !AbstractDungeon.player.orbs.isEmpty())
        {
            Iterator iterator = AbstractDungeon.player.orbs.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractOrb o = (AbstractOrb)iterator.next();
                if(o instanceof Dark)
                {
                    o.onStartOfTurn();
                    o.onEndOfTurn();
                }
            } while(true);
            if(AbstractDungeon.player.hasRelic("Cables") && !(AbstractDungeon.player.orbs.get(0) instanceof EmptyOrbSlot) && (AbstractDungeon.player.orbs.get(0) instanceof Dark))
            {
                ((AbstractOrb)AbstractDungeon.player.orbs.get(0)).onStartOfTurn();
                ((AbstractOrb)AbstractDungeon.player.orbs.get(0)).onEndOfTurn();
            }
        }
        tickDuration();
    }
}
