// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SymbioticVirus.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.Dark;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class SymbioticVirus extends AbstractRelic
{

    public SymbioticVirus()
    {
        super("Symbiotic Virus", "virus.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.MAGICAL);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void atPreBattle()
    {
        AbstractDungeon.player.channelOrb(new Dark());
    }

    public AbstractRelic makeCopy()
    {
        return new SymbioticVirus();
    }

    public static final String ID = "Symbiotic Virus";
}
