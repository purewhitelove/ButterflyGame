// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RunModStrings.java

package com.megacrit.cardcrawl.localization;


public class RunModStrings
{

    public RunModStrings()
    {
    }

    public static RunModStrings getMockModString()
    {
        RunModStrings retVal = new RunModStrings();
        retVal.NAME = "[MISSING_NAME]";
        retVal.DESCRIPTION = "MISSING_DESCRIPTION]";
        return retVal;
    }

    public String NAME;
    public String DESCRIPTION;
}
