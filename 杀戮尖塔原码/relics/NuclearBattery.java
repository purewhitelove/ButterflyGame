// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NuclearBattery.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.Plasma;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class NuclearBattery extends AbstractRelic
{

    public NuclearBattery()
    {
        super("Nuclear Battery", "battery.png", AbstractRelic.RelicTier.BOSS, AbstractRelic.LandingSound.HEAVY);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void atPreBattle()
    {
        AbstractDungeon.player.channelOrb(new Plasma());
    }

    public AbstractRelic makeCopy()
    {
        return new NuclearBattery();
    }

    public static final String ID = "Nuclear Battery";
}
