// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NlothsGift.java

package com.megacrit.cardcrawl.relics;


// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class NlothsGift extends AbstractRelic
{

    public NlothsGift()
    {
        super("Nloth's Gift", "nlothsGift.png", AbstractRelic.RelicTier.SPECIAL, AbstractRelic.LandingSound.FLAT);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public int changeRareCardRewardChance(int rareCardChance)
    {
        return rareCardChance * 3;
    }

    public AbstractRelic makeCopy()
    {
        return new NlothsGift();
    }

    public static final String ID = "Nloth's Gift";
    public static final float MULTIPLIER = 3F;
}
