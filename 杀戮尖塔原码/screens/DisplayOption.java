// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DisplayOption.java

package com.megacrit.cardcrawl.screens;


public class DisplayOption
    implements Comparable
{

    public DisplayOption(int width, int height)
    {
        aspectRatio = " TAB TAB";
        this.width = width;
        this.height = height;
    }

    public DisplayOption(int width, int height, boolean showAspectRatio)
    {
        aspectRatio = " TAB TAB";
        this.width = width;
        this.height = height;
        if(showAspectRatio)
            appendAspectRatio();
    }

    private void appendAspectRatio()
    {
        float ratio = (float)width / (float)height;
        if(ratio > 1.25F && ratio < 1.26F)
            aspectRatio = " (5:4)";
        else
        if(ratio > 1.32F && ratio < 1.34F)
            aspectRatio = " (4:3)";
        else
        if(ratio > 1.76F && ratio < 1.78F)
            aspectRatio = " (16:9)";
        else
        if(ratio > 1.59F && ratio < 1.61F)
            aspectRatio = " (16:10)";
        else
        if(ratio > 2.32F && ratio < 2.34F)
            aspectRatio = " (21:9)";
        else
            aspectRatio = (new StringBuilder()).append(" (").append(String.format("#.##", new Object[] {
                Float.valueOf(ratio)
            })).append(")").toString();
    }

    public int compareTo(DisplayOption other)
    {
        if(width == other.width)
        {
            if(height == other.height)
                return 0;
            return height >= other.height ? 1 : -1;
        }
        return width >= other.width ? 1 : -1;
    }

    public boolean equals(Object other)
    {
        return ((DisplayOption)other).width == width && ((DisplayOption)other).height == height;
    }

    public String toString()
    {
        return (new StringBuilder()).append("(").append(width).append(",").append(height).append(")").toString();
    }

    public String uiString()
    {
        return (new StringBuilder()).append(width).append(" x ").append(height).append(aspectRatio).toString();
    }

    public volatile int compareTo(Object obj)
    {
        return compareTo((DisplayOption)obj);
    }

    public int width;
    public int height;
    public String aspectRatio;
}
