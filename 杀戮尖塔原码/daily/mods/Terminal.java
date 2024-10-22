// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Terminal.java

package com.megacrit.cardcrawl.daily.mods;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.RunModStrings;

// Referenced classes of package com.megacrit.cardcrawl.daily.mods:
//            AbstractDailyMod

public class Terminal extends AbstractDailyMod
{

    public Terminal()
    {
        super("Terminal", NAME, DESC, "tough_enemies.png", false);
    }

    public static final String ID = "Terminal";
    private static final RunModStrings modStrings;
    public static final String NAME;
    public static final String DESC;
    public static final int ARMOR_AMT = 5;

    static 
    {
        modStrings = CardCrawlGame.languagePack.getRunModString("Terminal");
        NAME = modStrings.NAME;
        DESC = modStrings.DESCRIPTION;
    }
}
