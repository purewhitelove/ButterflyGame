// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DerpRock.java

package com.megacrit.cardcrawl.relics.deprecated;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class DerpRock extends AbstractRelic
{

    public DerpRock()
    {
        super("Derp Rock", "derpRock.png", com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.STARTER, com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound.HEAVY);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(1).append(DESCRIPTIONS[1]).toString();
    }

    public void atPreBattle()
    {
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    public AbstractRelic makeCopy()
    {
        return new DerpRock();
    }

    public static final String ID = "Derp Rock";
    public static final int CHARGE_AMT = 1;
}
