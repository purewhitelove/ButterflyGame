// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TutorialStrings.java

package com.megacrit.cardcrawl.localization;


// Referenced classes of package com.megacrit.cardcrawl.localization:
//            LocalizedStrings

public class TutorialStrings
{

    public TutorialStrings()
    {
    }

    public static TutorialStrings getMockTutorialString()
    {
        TutorialStrings retVal = new TutorialStrings();
        retVal.TEXT = LocalizedStrings.createMockStringArray(25);
        retVal.LABEL = LocalizedStrings.createMockStringArray(8);
        return retVal;
    }

    public String TEXT[];
    public String LABEL[];
}
