// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Circlet.java

package com.megacrit.cardcrawl.relics;


// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class Circlet extends AbstractRelic
{

    public Circlet()
    {
        super("Circlet", "circlet.png", AbstractRelic.RelicTier.SPECIAL, AbstractRelic.LandingSound.CLINK);
        counter = 1;
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void onEquip()
    {
        flash();
    }

    public void onUnequip()
    {
    }

    public AbstractRelic makeCopy()
    {
        return new Circlet();
    }

    public static final String ID = "Circlet";
}
