// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MarkOfTheBloom.java

package com.megacrit.cardcrawl.relics;


// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class MarkOfTheBloom extends AbstractRelic
{

    public MarkOfTheBloom()
    {
        super("Mark of the Bloom", "bloom.png", AbstractRelic.RelicTier.SPECIAL, AbstractRelic.LandingSound.MAGICAL);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public int onPlayerHeal(int healAmount)
    {
        flash();
        return 0;
    }

    public AbstractRelic makeCopy()
    {
        return new MarkOfTheBloom();
    }

    public static final String ID = "Mark of the Bloom";
}
