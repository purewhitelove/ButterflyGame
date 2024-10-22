// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Inserter.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class Inserter extends AbstractRelic
{

    public Inserter()
    {
        super("Inserter", "inserter.png", AbstractRelic.RelicTier.BOSS, AbstractRelic.LandingSound.SOLID);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void onEquip()
    {
        counter = 0;
    }

    public void atTurnStart()
    {
        if(counter == -1)
            counter += 2;
        else
            counter++;
        if(counter == 2)
        {
            counter = 0;
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new IncreaseMaxOrbAction(1));
        }
    }

    public AbstractRelic makeCopy()
    {
        return new Inserter();
    }

    public static final String ID = "Inserter";
    private static final int NUM_TURNS = 2;
}
