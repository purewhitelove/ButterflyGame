// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Colossus.java

package com.megacrit.cardcrawl.daily.mods;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.RunModStrings;

// Referenced classes of package com.megacrit.cardcrawl.daily.mods:
//            AbstractDailyMod

public class Colossus extends AbstractDailyMod
{

    public Colossus()
    {
        super("MonsterHunter", NAME, DESC, "colossus.png", false);
    }

    public static final String ID = "MonsterHunter";
    private static final RunModStrings modStrings;
    public static final String NAME;
    public static final String DESC;
    public static final float modAmount = 1.5F;

    static 
    {
        modStrings = CardCrawlGame.languagePack.getRunModString("MonsterHunter");
        NAME = modStrings.NAME;
        DESC = modStrings.DESCRIPTION;
    }
}
