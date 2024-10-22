// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OddMushroom.java

package com.megacrit.cardcrawl.relics;


// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class OddMushroom extends AbstractRelic
{

    public OddMushroom()
    {
        super("Odd Mushroom", "mushroom.png", AbstractRelic.RelicTier.SPECIAL, AbstractRelic.LandingSound.FLAT);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy()
    {
        return new OddMushroom();
    }

    public static final String ID = "Odd Mushroom";
    public static final float VULN_EFFECTIVENESS = 1.25F;
    public static final int EFFECTIVENESS_STRING = 25;
}
