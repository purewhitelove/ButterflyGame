// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CollectAction.java

package com.megacrit.cardcrawl.actions.watcher;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.powers.CollectPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class CollectAction extends AbstractGameAction
{

    public CollectAction(AbstractPlayer p, boolean freeToPlayOnce, int energyOnUse, boolean upgraded)
    {
        this.freeToPlayOnce = false;
        this.p = p;
        this.freeToPlayOnce = freeToPlayOnce;
        duration = Settings.ACTION_DUR_XFAST;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
        this.upgraded = upgraded;
    }

    public void update()
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
            addToBot(new ApplyPowerAction(p, p, new CollectPower(p, effect), effect));
            if(!freeToPlayOnce)
                p.energy.use(EnergyPanel.totalCount);
        }
        isDone = true;
    }

    private boolean freeToPlayOnce;
    private boolean upgraded;
    private AbstractPlayer p;
    private int energyOnUse;
}
