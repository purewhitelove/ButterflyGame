// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TungstenRod.java

package com.megacrit.cardcrawl.relics;


// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class TungstenRod extends AbstractRelic
{

    public TungstenRod()
    {
        super("TungstenRod", "tungsten.png", AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.HEAVY);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(1).append(DESCRIPTIONS[1]).toString();
    }

    public int onLoseHpLast(int damageAmount)
    {
        if(damageAmount > 0)
        {
            flash();
            return damageAmount - 1;
        } else
        {
            return damageAmount;
        }
    }

    public AbstractRelic makeCopy()
    {
        return new TungstenRod();
    }

    public static final String ID = "TungstenRod";
}
