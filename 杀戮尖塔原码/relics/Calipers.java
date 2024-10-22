// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Calipers.java

package com.megacrit.cardcrawl.relics;


// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class Calipers extends AbstractRelic
{

    public Calipers()
    {
        super("Calipers", "calipers.png", AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.CLINK);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(15).append(DESCRIPTIONS[1]).toString();
    }

    public AbstractRelic makeCopy()
    {
        return new Calipers();
    }

    public static final String ID = "Calipers";
    public static final int BLOCK_LOSS = 15;
}
