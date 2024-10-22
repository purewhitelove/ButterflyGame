// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MonsterStrings.java

package com.megacrit.cardcrawl.localization;


// Referenced classes of package com.megacrit.cardcrawl.localization:
//            LocalizedStrings

public class MonsterStrings
{

    public MonsterStrings()
    {
    }

    public static MonsterStrings getMockMonsterString()
    {
        MonsterStrings retVal = new MonsterStrings();
        retVal.NAME = "[MISSING_NAME]";
        retVal.DIALOG = LocalizedStrings.createMockStringArray(5);
        retVal.MOVES = LocalizedStrings.createMockStringArray(5);
        return retVal;
    }

    public String NAME;
    public String DIALOG[];
    public String MOVES[];
}
