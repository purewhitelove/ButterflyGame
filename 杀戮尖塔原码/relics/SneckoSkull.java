// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SneckoSkull.java

package com.megacrit.cardcrawl.relics;


// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class SneckoSkull extends AbstractRelic
{

    public SneckoSkull()
    {
        super("Snake Skull", "snakeSkull.png", AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.FLAT);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(1).append(DESCRIPTIONS[1]).toString();
    }

    public AbstractRelic makeCopy()
    {
        return new SneckoSkull();
    }

    public static final String ID = "Snake Skull";
    public static final int EFFECT = 1;
}
