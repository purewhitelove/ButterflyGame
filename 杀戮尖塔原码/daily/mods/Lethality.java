// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Lethality.java

package com.megacrit.cardcrawl.daily.mods;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.RunModStrings;

// Referenced classes of package com.megacrit.cardcrawl.daily.mods:
//            AbstractDailyMod

public class Lethality extends AbstractDailyMod
{

    public Lethality()
    {
        super("Lethality", NAME, DESC, "lethal_enemies.png", false);
    }

    public static final String ID = "Lethality";
    private static final RunModStrings modStrings;
    public static final String NAME;
    public static final String DESC;
    public static final int STR_AMT = 3;

    static 
    {
        modStrings = CardCrawlGame.languagePack.getRunModString("Lethality");
        NAME = modStrings.NAME;
        DESC = modStrings.DESCRIPTION;
    }
}
