// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GoldPlatedCables.java

package com.megacrit.cardcrawl.relics;


// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class GoldPlatedCables extends AbstractRelic
{

    public GoldPlatedCables()
    {
        super("Cables", "cables.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.FLAT);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy()
    {
        return new GoldPlatedCables();
    }

    public static final String ID = "Cables";
}
