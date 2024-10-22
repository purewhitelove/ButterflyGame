// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Muzzle.java

package com.megacrit.cardcrawl.blights;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.BlightStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;

// Referenced classes of package com.megacrit.cardcrawl.blights:
//            AbstractBlight

public class Muzzle extends AbstractBlight
{

    public Muzzle()
    {
        super("FullBelly", NAME, DESC[0], "muzzle.png", true);
    }

    public static final String ID = "FullBelly";
    private static final BlightStrings blightStrings;
    public static final String NAME;
    public static final String DESC[];

    static 
    {
        blightStrings = CardCrawlGame.languagePack.getBlightString("FullBelly");
        NAME = blightStrings.NAME;
        DESC = blightStrings.DESCRIPTION;
    }
}
