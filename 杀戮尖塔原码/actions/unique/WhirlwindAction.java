// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WhirlwindAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;

public class WhirlwindAction extends AbstractGameAction
{

    public WhirlwindAction(AbstractPlayer p, int multiDamage[], com.megacrit.cardcrawl.cards.DamageInfo.DamageType damageType, boolean freeToPlayOnce, int energyOnUse)
    {
        this.freeToPlayOnce = false;
        this.energyOnUse = -1;
        this.multiDamage = multiDamage;
        this.damageType = damageType;
        this.p = p;
        this.freeToPlayOnce = freeToPlayOnce;
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
        if(effect > 0)
        {
            for(int i = 0; i < effect; i++)
            {
                if(i == 0)
                {
                    addToBot(new SFXAction("ATTACK_WHIRLWIND"));
                    addToBot(new VFXAction(new WhirlwindEffect(), 0.0F));
                }
                addToBot(new SFXAction("ATTACK_HEAVY"));
                addToBot(new VFXAction(p, new CleaveEffect(), 0.0F));
                addToBot(new DamageAllEnemiesAction(p, multiDamage, damageType, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE, true));
            }

            if(!freeToPlayOnce)
                p.energy.use(EnergyPanel.totalCount);
        }
        isDone = true;
    }

    public int multiDamage[];
    private boolean freeToPlayOnce;
    private com.megacrit.cardcrawl.cards.DamageInfo.DamageType damageType;
    private AbstractPlayer p;
    private int energyOnUse;
}
