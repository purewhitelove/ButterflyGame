// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RingOfTheSerpent.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic, SnakeRing

public class RingOfTheSerpent extends AbstractRelic
{

    public RingOfTheSerpent()
    {
        super("Ring of the Serpent", "serpent_ring.png", AbstractRelic.RelicTier.BOSS, AbstractRelic.LandingSound.CLINK);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(DESCRIPTIONS[1]).toString();
    }

    public void onEquip()
    {
        AbstractDungeon.player.masterHandSize++;
    }

    public void onUnequip()
    {
        AbstractDungeon.player.masterHandSize--;
    }

    public void atTurnStart()
    {
        flash();
    }

    public boolean canSpawn()
    {
        return AbstractDungeon.player.hasRelic("Ring of the Snake");
    }

    public AbstractRelic makeCopy()
    {
        return new RingOfTheSerpent();
    }

    public static final String ID = "Ring of the Serpent";
    private static final int NUM_CARDS = 1;
}
