// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EvokeWithoutRemovingOrbAction.java

package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class EvokeWithoutRemovingOrbAction extends AbstractGameAction
{

    public EvokeWithoutRemovingOrbAction(int amount)
    {
        if(Settings.FAST_MODE)
            startDuration = Settings.ACTION_DUR_FAST;
        else
            startDuration = Settings.ACTION_DUR_XFAST;
        duration = startDuration;
        orbCount = amount;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DAMAGE;
    }

    public void update()
    {
        if(duration == startDuration)
        {
            for(int i = 0; i < orbCount; i++)
                AbstractDungeon.player.evokeWithoutLosingOrb();

        }
        tickDuration();
    }

    private int orbCount;
}
