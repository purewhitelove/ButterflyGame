// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GameDataStringBuilder.java

package com.megacrit.cardcrawl.helpers;


public class GameDataStringBuilder
{

    public GameDataStringBuilder()
    {
        bldr = new StringBuilder();
    }

    public void addFieldData(String value)
    {
        bldr.append(value).append("\t");
    }

    public void addFieldData(int value)
    {
        addFieldData(Integer.toString(value));
    }

    public void addFieldData(boolean value)
    {
        addFieldData(Boolean.toString(value));
    }

    public String toString()
    {
        return bldr.toString();
    }

    private StringBuilder bldr;
}
