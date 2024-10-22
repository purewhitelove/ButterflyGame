// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EnergyBlockAction.java

package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class EnergyBlockAction extends AbstractGameAction
{

    public EnergyBlockAction(boolean upgraded)
    {
        upg = false;
        duration = Settings.ACTION_DUR_FAST;
        upg = upgraded;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_FAST)
            if(upg)
                addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, EnergyPanel.totalCount * 2));
            else
                addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, EnergyPanel.totalCount));
        tickDuration();
    }

    private boolean upg;
}
