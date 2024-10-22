// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RunicCapacitor.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class RunicCapacitor extends AbstractRelic
{

    public RunicCapacitor()
    {
        super("Runic Capacitor", "runicCapacitor.png", AbstractRelic.RelicTier.SHOP, AbstractRelic.LandingSound.SOLID);
        firstTurn = true;
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void atPreBattle()
    {
        firstTurn = true;
    }

    public void atTurnStart()
    {
        if(firstTurn)
        {
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new IncreaseMaxOrbAction(3));
            firstTurn = false;
        }
    }

    public AbstractRelic makeCopy()
    {
        return new RunicCapacitor();
    }

    public static final String ID = "Runic Capacitor";
    private boolean firstTurn;
}
