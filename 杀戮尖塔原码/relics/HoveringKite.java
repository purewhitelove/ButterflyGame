// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HoveringKite.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class HoveringKite extends AbstractRelic
{

    public HoveringKite()
    {
        super("HoveringKite", "kite.png", AbstractRelic.RelicTier.BOSS, AbstractRelic.LandingSound.MAGICAL);
        triggeredThisTurn = false;
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void atTurnStart()
    {
        triggeredThisTurn = false;
    }

    public void onManualDiscard()
    {
        if(!triggeredThisTurn)
        {
            triggeredThisTurn = true;
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new GainEnergyAction(1));
        }
    }

    public AbstractRelic makeCopy()
    {
        return new HoveringKite();
    }

    public static final String ID = "HoveringKite";
    private boolean triggeredThisTurn;
}
