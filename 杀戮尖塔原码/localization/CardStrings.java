// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CardStrings.java

package com.megacrit.cardcrawl.localization;


public class CardStrings
{

    public CardStrings()
    {
    }

    public static CardStrings getMockCardString()
    {
        CardStrings retVal = new CardStrings();
        retVal.NAME = "[MISSING_TITLE]";
        retVal.DESCRIPTION = "[MISSING_DESCRIPTION]";
        retVal.UPGRADE_DESCRIPTION = "[MISSING_DESCRIPTION+]";
        retVal.EXTENDED_DESCRIPTION = (new String[] {
            "[MISSING_0]", "[MISSING_1]", "[MISSING_2]"
        });
        return retVal;
    }

    public String NAME;
    public String DESCRIPTION;
    public String UPGRADE_DESCRIPTION;
    public String EXTENDED_DESCRIPTION[];
}
