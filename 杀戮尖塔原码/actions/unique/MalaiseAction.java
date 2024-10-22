// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MalaiseAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class MalaiseAction extends AbstractGameAction
{

    public MalaiseAction(AbstractPlayer p, AbstractMonster m, boolean upgraded, boolean freeToPlayOnce, int energyOnUse)
    {
        this.freeToPlayOnce = false;
        this.upgraded = false;
        this.energyOnUse = -1;
        this.p = p;
        this.m = m;
        this.freeToPlayOnce = freeToPlayOnce;
        this.upgraded = upgraded;
        duration = Settings.ACTION_DUR_XFAST;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
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
            addToBot(new ApplyPowerAction(m, p, new StrengthPower(m, -effect), -effect));
            addToBot(new ApplyPowerAction(m, p, new WeakPower(m, effect, false), effect));
            if(!freeToPlayOnce)
                p.energy.use(EnergyPanel.totalCount);
        }
        isDone = true;
    }

    private boolean freeToPlayOnce;
    private boolean upgraded;
    private AbstractPlayer p;
    private AbstractMonster m;
    private int energyOnUse;
}
