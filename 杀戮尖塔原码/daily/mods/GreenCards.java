// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GreenCards.java

package com.megacrit.cardcrawl.daily.mods;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.RunModStrings;

// Referenced classes of package com.megacrit.cardcrawl.daily.mods:
//            AbstractDailyMod

public class GreenCards extends AbstractDailyMod
{

    public GreenCards()
    {
        super("Green Cards", NAME, DESC, "green.png", true, com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.THE_SILENT);
    }

    public static final String ID = "Green Cards";
    private static final RunModStrings modStrings;
    public static final String NAME;
    public static final String DESC;

    static 
    {
        modStrings = CardCrawlGame.languagePack.getRunModString("Green Cards");
        NAME = modStrings.NAME;
        DESC = modStrings.DESCRIPTION;
    }
}
