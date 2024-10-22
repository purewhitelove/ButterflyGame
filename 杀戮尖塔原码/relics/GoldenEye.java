// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GoldenEye.java

package com.megacrit.cardcrawl.relics;


// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class GoldenEye extends AbstractRelic
{

    public GoldenEye()
    {
        super("GoldenEye", "golden_eye.png", AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.HEAVY);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(2).append(DESCRIPTIONS[1]).toString();
    }

    public AbstractRelic makeCopy()
    {
        return new GoldenEye();
    }

    public static final String ID = "GoldenEye";
}
