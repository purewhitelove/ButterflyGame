// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PaperFrog.java

package com.megacrit.cardcrawl.relics;


// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class PaperFrog extends AbstractRelic
{

    public PaperFrog()
    {
        super("Paper Frog", "paperFrog.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.FLAT);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy()
    {
        return new PaperFrog();
    }

    public static final String ID = "Paper Frog";
    public static final float VULN_EFFECTIVENESS = 1.75F;
    public static final int EFFECTIVENESS_STRING = 75;
}
