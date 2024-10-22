// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CaptainsWheel.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class CaptainsWheel extends AbstractRelic
{

    public CaptainsWheel()
    {
        super("CaptainsWheel", "captain_wheel.png", AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.FLAT);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(18).append(DESCRIPTIONS[1]).toString();
    }

    public void atBattleStart()
    {
        counter = 0;
    }

    public void atTurnStart()
    {
        if(!grayscale)
            counter++;
        if(counter == 3)
        {
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 18));
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
        return new CaptainsWheel();
    }

    public static final String ID = "CaptainsWheel";
    private static final int TURN_ACTIVATION = 3;
}
