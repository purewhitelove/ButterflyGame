// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CentennialPuzzle.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class CentennialPuzzle extends AbstractRelic
{

    public CentennialPuzzle()
    {
        super("Centennial Puzzle", "centennialPuzzle.png", AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.HEAVY);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(3).append(DESCRIPTIONS[1]).toString();
    }

    public void atPreBattle()
    {
        usedThisCombat = false;
        pulse = true;
        beginPulse();
    }

    public void wasHPLost(int damageAmount)
    {
        if(damageAmount > 0 && AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT && !usedThisCombat)
        {
            flash();
            pulse = false;
            addToTop(new DrawCardAction(AbstractDungeon.player, 3));
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            usedThisCombat = true;
            grayscale = true;
        }
    }

    public void justEnteredRoom(AbstractRoom room)
    {
        grayscale = false;
    }

    public void onVictory()
    {
        pulse = false;
    }

    public AbstractRelic makeCopy()
    {
        return new CentennialPuzzle();
    }

    public static final String ID = "Centennial Puzzle";
    private static final int NUM_CARDS = 3;
    private static boolean usedThisCombat = false;

}
