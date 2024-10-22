// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Midas.java

package com.megacrit.cardcrawl.daily.mods;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.RunModStrings;

// Referenced classes of package com.megacrit.cardcrawl.daily.mods:
//            AbstractDailyMod

public class Midas extends AbstractDailyMod
{

    public Midas()
    {
        super("Midas", NAME, DESC, "midas.png", false);
    }

    public static final String ID = "Midas";
    private static final RunModStrings modStrings;
    public static final String NAME;
    public static final String DESC;
    public static final float MULTIPLIER = 2F;

    static 
    {
        modStrings = CardCrawlGame.languagePack.getRunModString("Midas");
        NAME = modStrings.NAME;
        DESC = modStrings.DESCRIPTION;
    }
}
