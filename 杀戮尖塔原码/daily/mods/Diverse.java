// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Diverse.java

package com.megacrit.cardcrawl.daily.mods;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.RunModStrings;

// Referenced classes of package com.megacrit.cardcrawl.daily.mods:
//            AbstractDailyMod

public class Diverse extends AbstractDailyMod
{

    public Diverse()
    {
        super("Diverse", NAME, DESC, "diverse.png", true);
    }

    public static final String ID = "Diverse";
    private static final RunModStrings modStrings;
    public static final String NAME;
    public static final String DESC;
    public static final int NON_DEFECT_MASTER_MAX_ORBS = 1;

    static 
    {
        modStrings = CardCrawlGame.languagePack.getRunModString("Diverse");
        NAME = modStrings.NAME;
        DESC = modStrings.DESCRIPTION;
    }
}
