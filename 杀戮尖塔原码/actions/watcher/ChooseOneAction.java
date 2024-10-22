// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChooseOneAction.java

package com.megacrit.cardcrawl.actions.watcher;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import java.util.ArrayList;

public class ChooseOneAction extends AbstractGameAction
{

    public ChooseOneAction(ArrayList choices)
    {
        duration = Settings.ACTION_DUR_FAST;
        this.choices = choices;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_FAST)
        {
            AbstractDungeon.cardRewardScreen.chooseOneOpen(choices);
            tickDuration();
            return;
        } else
        {
            tickDuration();
            return;
        }
    }

    private ArrayList choices;
}
