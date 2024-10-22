// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FluxAction.java

package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.*;
import java.util.ArrayList;

public class FluxAction extends AbstractGameAction
{

    public FluxAction()
    {
        duration = Settings.ACTION_DUR_FAST;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.SPECIAL;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_FAST)
        {
            for(int i = 0; i < AbstractDungeon.player.orbs.size(); i++)
                if(!(AbstractDungeon.player.orbs.get(i) instanceof EmptyOrbSlot) && !(AbstractDungeon.player.orbs.get(i) instanceof Plasma))
                {
                    AbstractOrb plasma = new Plasma();
                    plasma.cX = ((AbstractOrb)AbstractDungeon.player.orbs.get(i)).cX;
                    plasma.cY = ((AbstractOrb)AbstractDungeon.player.orbs.get(i)).cY;
                    plasma.setSlot(i, AbstractDungeon.player.maxOrbs);
                    AbstractDungeon.player.orbs.set(i, plasma);
                }

        }
        tickDuration();
    }
}
