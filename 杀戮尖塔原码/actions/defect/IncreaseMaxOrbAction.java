// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IncreaseMaxOrbAction.java

package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class IncreaseMaxOrbAction extends AbstractGameAction
{

    public IncreaseMaxOrbAction(int slotIncrease)
    {
        duration = Settings.ACTION_DUR_FAST;
        amount = slotIncrease;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.BLOCK;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_FAST)
        {
            for(int i = 0; i < amount; i++)
                AbstractDungeon.player.increaseMaxOrbSlots(1, true);

        }
        tickDuration();
    }
}
