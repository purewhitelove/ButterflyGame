// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PurpleCards.java

package com.megacrit.cardcrawl.daily.mods;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.RunModStrings;

// Referenced classes of package com.megacrit.cardcrawl.daily.mods:
//            AbstractDailyMod

public class PurpleCards extends AbstractDailyMod
{

    public PurpleCards()
    {
        super("Purple Cards", NAME, DESC, "purple.png", true, com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.WATCHER);
    }

    public static final String ID = "Purple Cards";
    private static final RunModStrings modStrings;
    public static final String NAME;
    public static final String DESC;

    static 
    {
        modStrings = CardCrawlGame.languagePack.getRunModString("Purple Cards");
        NAME = modStrings.NAME;
        DESC = modStrings.DESCRIPTION;
    }
}
