// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RunicCube.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class RunicCube extends AbstractRelic
{

    public RunicCube()
    {
        super("Runic Cube", "runicCube.png", AbstractRelic.RelicTier.BOSS, AbstractRelic.LandingSound.FLAT);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(1).append(DESCRIPTIONS[1]).toString();
    }

    public void wasHPLost(int damageAmount)
    {
        if(AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT && damageAmount > 0)
        {
            flash();
            addToTop(new DrawCardAction(AbstractDungeon.player, 1));
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }

    public AbstractRelic makeCopy()
    {
        return new RunicCube();
    }

    public static final String ID = "Runic Cube";
    private static final int NUM_CARDS = 1;
}
