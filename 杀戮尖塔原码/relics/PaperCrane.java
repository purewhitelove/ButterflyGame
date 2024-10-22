// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PaperCrane.java

package com.megacrit.cardcrawl.relics;


// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class PaperCrane extends AbstractRelic
{

    public PaperCrane()
    {
        super("Paper Crane", "paperCrane.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.FLAT);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy()
    {
        return new PaperCrane();
    }

    public static final String ID = "Paper Crane";
    public static final float WEAK_EFFECTIVENESS = 0.6F;
    public static final int EFFECTIVENESS_STRING = 40;
}
