// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StrangeSpoon.java

package com.megacrit.cardcrawl.relics;


// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class StrangeSpoon extends AbstractRelic
{

    public StrangeSpoon()
    {
        super("Strange Spoon", "bigSpoon.png", AbstractRelic.RelicTier.SHOP, AbstractRelic.LandingSound.CLINK);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy()
    {
        return new StrangeSpoon();
    }

    public static final String ID = "Strange Spoon";
    public static final int DISCARD_CHANCE = 50;
}
