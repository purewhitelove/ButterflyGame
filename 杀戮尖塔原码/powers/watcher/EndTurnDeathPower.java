// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EndTurnDeathPower.java

package com.megacrit.cardcrawl.powers.watcher;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

public class EndTurnDeathPower extends AbstractPower
{

    public EndTurnDeathPower(AbstractCreature owner)
    {
        name = powerStrings.NAME;
        ID = "EndTurnDeath";
        this.owner = owner;
        amount = -1;
        updateDescription();
        loadRegion("end_turn_death");
    }

    public void updateDescription()
    {
        description = powerStrings.DESCRIPTIONS[0];
    }

    public void atStartOfTurn()
    {
        flash();
        addToBot(new VFXAction(new LightningEffect(owner.hb.cX, owner.hb.cY)));
        addToBot(new LoseHPAction(owner, owner, 0x1869f));
        addToBot(new RemoveSpecificPowerAction(owner, owner, "EndTurnDeath"));
    }

    public static final String POWER_ID = "EndTurnDeath";
    private static final PowerStrings powerStrings;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("EndTurnDeath");
    }
}
