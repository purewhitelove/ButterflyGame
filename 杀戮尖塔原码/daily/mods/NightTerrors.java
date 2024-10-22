// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NightTerrors.java

package com.megacrit.cardcrawl.daily.mods;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.RunModStrings;

// Referenced classes of package com.megacrit.cardcrawl.daily.mods:
//            AbstractDailyMod

public class NightTerrors extends AbstractDailyMod
{

    public NightTerrors()
    {
        super("Night Terrors", NAME, DESC, "night_terrors.png", false);
    }

    public static final String ID = "Night Terrors";
    private static final RunModStrings modStrings;
    public static final String NAME;
    public static final String DESC;
    public static final float HEAL_AMT = 1F;
    public static final int MAX_HP_LOSS = 5;

    static 
    {
        modStrings = CardCrawlGame.languagePack.getRunModString("Night Terrors");
        NAME = modStrings.NAME;
        DESC = modStrings.DESCRIPTION;
    }
}
