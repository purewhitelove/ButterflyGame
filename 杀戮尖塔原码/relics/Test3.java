// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Test3.java

package com.megacrit.cardcrawl.relics;


// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class Test3 extends AbstractRelic
{

    public Test3()
    {
        super("Test 3", "test3.png", AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.FLAT);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void onEquip()
    {
    }

    public AbstractRelic makeCopy()
    {
        return new Test3();
    }

    public static final String ID = "Test 3";
}
