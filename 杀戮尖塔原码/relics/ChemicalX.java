// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChemicalX.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.localization.LocalizedStrings;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class ChemicalX extends AbstractRelic
{

    public ChemicalX()
    {
        super("Chemical X", "chemicalX.png", AbstractRelic.RelicTier.SHOP, AbstractRelic.LandingSound.CLINK);
    }

    public String getUpdatedDescription()
    {
        if(!DESCRIPTIONS[1].equals(""))
            return (new StringBuilder()).append(DESCRIPTIONS[0]).append(2).append(DESCRIPTIONS[1]).toString();
        else
            return (new StringBuilder()).append(DESCRIPTIONS[0]).append(2).append(LocalizedStrings.PERIOD).toString();
    }

    public AbstractRelic makeCopy()
    {
        return new ChemicalX();
    }

    public static final String ID = "Chemical X";
    public static final int BOOST = 2;
}
