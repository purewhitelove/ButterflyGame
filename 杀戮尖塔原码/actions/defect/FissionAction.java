// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FissionAction.java

package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

// Referenced classes of package com.megacrit.cardcrawl.actions.defect:
//            EvokeAllOrbsAction, RemoveAllOrbsAction

public class FissionAction extends AbstractGameAction
{

    public FissionAction(boolean upgraded)
    {
        this.upgraded = false;
        duration = Settings.ACTION_DUR_XFAST;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.ENERGY;
        this.upgraded = upgraded;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_XFAST)
        {
            int orbCount = AbstractDungeon.player.filledOrbCount();
            addToTop(new DrawCardAction(AbstractDungeon.player, orbCount));
            addToTop(new GainEnergyAction(orbCount));
            if(upgraded)
                addToTop(new EvokeAllOrbsAction());
            else
                addToTop(new RemoveAllOrbsAction());
        }
        tickDuration();
    }

    private boolean upgraded;
}
