// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FrozenEye.java

package com.megacrit.cardcrawl.relics;


// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class FrozenEye extends AbstractRelic
{

    public FrozenEye()
    {
        super("Frozen Eye", "frozenEye.png", AbstractRelic.RelicTier.SHOP, AbstractRelic.LandingSound.SOLID);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy()
    {
        return new FrozenEye();
    }

    public static final String ID = "Frozen Eye";
}
