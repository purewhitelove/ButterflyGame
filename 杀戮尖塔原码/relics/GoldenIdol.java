// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GoldenIdol.java

package com.megacrit.cardcrawl.relics;


// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class GoldenIdol extends AbstractRelic
{

    public GoldenIdol()
    {
        super("Golden Idol", "goldenIdolRelic.png", AbstractRelic.RelicTier.SPECIAL, AbstractRelic.LandingSound.HEAVY);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy()
    {
        return new GoldenIdol();
    }

    public static final String ID = "Golden Idol";
    public static final float MULTIPLIER = 0.25F;
}
