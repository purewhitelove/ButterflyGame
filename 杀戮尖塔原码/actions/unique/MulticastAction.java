// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MulticastAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class MulticastAction extends AbstractGameAction
{

    public MulticastAction(AbstractPlayer p, int energyOnUse, boolean upgraded, boolean freeToPlayOnce)
    {
        this.freeToPlayOnce = false;
        this.energyOnUse = -1;
        this.p = p;
        duration = Settings.ACTION_DUR_XFAST;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
        this.upgraded = upgraded;
        this.freeToPlayOnce = freeToPlayOnce;
    }

    public void update()
    {
        if(AbstractDungeon.player.hasOrb())
        {
            int effect = EnergyPanel.totalCount;
            if(energyOnUse != -1)
                effect = energyOnUse;
            if(p.hasRelic("Chemical X"))
            {
                effect += 2;
                p.getRelic("Chemical X").flash();
            }
            if(upgraded)
                effect++;
            if(effect > 0)
            {
                for(int i = 0; i < effect - 1; i++)
                    addToBot(new EvokeWithoutRemovingOrbAction(1));

                addToBot(new AnimateOrbAction(1));
                addToBot(new EvokeOrbAction(1));
                if(!freeToPlayOnce)
                    p.energy.use(EnergyPanel.totalCount);
            }
        }
        isDone = true;
    }

    private boolean freeToPlayOnce;
    private AbstractPlayer p;
    private int energyOnUse;
    private boolean upgraded;
}
