// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MimicInfestation.java

package com.megacrit.cardcrawl.blights;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.BlightStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;

// Referenced classes of package com.megacrit.cardcrawl.blights:
//            AbstractBlight

public class MimicInfestation extends AbstractBlight
{

    public MimicInfestation()
    {
        super("MimicInfestation", NAME, DESC[0], "mimic.png", true);
    }

    public static final String ID = "MimicInfestation";
    private static final BlightStrings blightStrings;
    public static final String NAME;
    public static final String DESC[];

    static 
    {
        blightStrings = CardCrawlGame.languagePack.getBlightString("MimicInfestation");
        NAME = blightStrings.NAME;
        DESC = blightStrings.DESCRIPTION;
    }
}
