// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EvokeOrbAction.java

package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class EvokeOrbAction extends AbstractGameAction
{

    public EvokeOrbAction(int amount)
    {
        if(Settings.FAST_MODE)
            duration = Settings.ACTION_DUR_XFAST;
        else
            duration = Settings.ACTION_DUR_FAST;
        duration = startDuration;
        orbCount = amount;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DAMAGE;
    }

    public void update()
    {
        if(duration == startDuration)
        {
            for(int i = 0; i < orbCount; i++)
                AbstractDungeon.player.evokeOrb();

        }
        tickDuration();
    }

    private int orbCount;
}
