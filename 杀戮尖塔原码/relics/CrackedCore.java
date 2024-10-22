// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CrackedCore.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.Lightning;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class CrackedCore extends AbstractRelic
{

    public CrackedCore()
    {
        super("Cracked Core", "crackedOrb.png", AbstractRelic.RelicTier.STARTER, AbstractRelic.LandingSound.CLINK);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void atPreBattle()
    {
        AbstractDungeon.player.channelOrb(new Lightning());
    }

    public AbstractRelic makeCopy()
    {
        return new CrackedCore();
    }

    public static final String ID = "Cracked Core";
}
