// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StepThroughTimeAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class StepThroughTimeAction extends AbstractGameAction
{

    public StepThroughTimeAction()
    {
        source = AbstractDungeon.player;
        duration = Settings.ACTION_DUR_FAST;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_FAST)
        {
            int diff = GameActionManager.playerHpLastTurn - AbstractDungeon.player.currentHealth;
            if(diff > 0)
                addToTop(new HealAction(source, source, diff));
            else
            if(diff < 0)
                addToTop(new LoseHPAction(source, source, diff));
        }
        tickDuration();
    }
}
