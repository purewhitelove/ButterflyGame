// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Orichalcum.java

package com.megacrit.cardcrawl.relics;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class Orichalcum extends AbstractRelic
{

    public Orichalcum()
    {
        super("Orichalcum", "orichalcum.png", AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.HEAVY);
        trigger = false;
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(6).append(DESCRIPTIONS[1]).toString();
    }

    public void onPlayerEndTurn()
    {
        if(AbstractDungeon.player.currentBlock == 0 || trigger)
        {
            trigger = false;
            flash();
            stopPulse();
            addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 6));
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }

    public void atTurnStart()
    {
        trigger = false;
        if(AbstractDungeon.player.currentBlock == 0)
            beginLongPulse();
    }

    public int onPlayerGainedBlock(float blockAmount)
    {
        if(blockAmount > 0.0F)
            stopPulse();
        return MathUtils.floor(blockAmount);
    }

    public void onVictory()
    {
        stopPulse();
    }

    public AbstractRelic makeCopy()
    {
        return new Orichalcum();
    }

    public static final String ID = "Orichalcum";
    private static final int BLOCK_AMT = 6;
    public boolean trigger;
}
