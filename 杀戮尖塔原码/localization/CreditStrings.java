// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CreditStrings.java

package com.megacrit.cardcrawl.localization;


// Referenced classes of package com.megacrit.cardcrawl.localization:
//            LocalizedStrings

public class CreditStrings
{

    public CreditStrings()
    {
    }

    public static CreditStrings getMockCreditString()
    {
        CreditStrings retVal = new CreditStrings();
        retVal.HEADER = "[MISSING_HEADER]";
        retVal.NAMES = LocalizedStrings.createMockStringArray(8);
        return null;
    }

    public String HEADER;
    public String NAMES[];
}
