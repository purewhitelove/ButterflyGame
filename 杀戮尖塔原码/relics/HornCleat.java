// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HornCleat.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class HornCleat extends AbstractRelic
{

    public HornCleat()
    {
        super("HornCleat", "horn_cleat.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.HEAVY);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(14).append(DESCRIPTIONS[1]).toString();
    }

    public void atBattleStart()
    {
        counter = 0;
    }

    public void atTurnStart()
    {
        if(!grayscale)
            counter++;
        if(counter == 2)
        {
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 14));
            counter = -1;
            grayscale = true;
        }
    }

    public void onVictory()
    {
        counter = -1;
        grayscale = false;
    }

    public AbstractRelic makeCopy()
    {
        return new HornCleat();
    }

    public static final String ID = "HornCleat";
    private static final int TURN_ACTIVATION = 2;
}
