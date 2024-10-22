// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EventStrings.java

package com.megacrit.cardcrawl.localization;


// Referenced classes of package com.megacrit.cardcrawl.localization:
//            LocalizedStrings

public class EventStrings
{

    public EventStrings()
    {
    }

    public static EventStrings getMockEventString()
    {
        EventStrings retVal = new EventStrings();
        retVal.NAME = "[MISSING_NAME]";
        retVal.DESCRIPTIONS = LocalizedStrings.createMockStringArray(12);
        retVal.OPTIONS = LocalizedStrings.createMockStringArray(12);
        return retVal;
    }

    public String NAME;
    public String DESCRIPTIONS[];
    public String OPTIONS[];
}
