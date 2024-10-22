// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EssenceOfDarknessAction.java

package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.Dark;
import java.util.ArrayList;

public class EssenceOfDarknessAction extends AbstractGameAction
{

    public EssenceOfDarknessAction(int potency)
    {
        duration = Settings.ACTION_DUR_FAST;
        amount = potency;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_FAST)
        {
            for(int i = 0; i < AbstractDungeon.player.orbs.size(); i++)
            {
                for(int j = 0; j < amount; j++)
                    AbstractDungeon.player.channelOrb(new Dark());

            }

            if(Settings.FAST_MODE)
            {
                isDone = true;
                return;
            }
        }
        tickDuration();
    }
}
