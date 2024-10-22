// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DescriptionLine.java

package com.megacrit.cardcrawl.cards;

import com.megacrit.cardcrawl.core.Settings;

public class DescriptionLine
{

    public DescriptionLine(String text, float width)
    {
        this.text = text.trim();
        this.width = width -= offsetter;
    }

    public String[] getCachedTokenizedText()
    {
        if(cachedTokenizedText == null)
            cachedTokenizedText = tokenize(text);
        return cachedTokenizedText;
    }

    public String[] getCachedTokenizedTextCN()
    {
        if(cachedTokenizedTextCN == null)
            cachedTokenizedTextCN = tokenizeCN(text);
        return cachedTokenizedTextCN;
    }

    private static String[] tokenize(String desc)
    {
        String tokenized[];
        int i;
        tokenized = desc.split("\\s+");
        i = 0;
_L2:
        if(i >= tokenized.length)
            break; /* Loop/switch isn't completed */
        new StringBuilder();
        tokenized;
        i;
        JVM INSTR dup2_x1 ;
        JVM INSTR aaload ;
        append();
        ' ';
        append();
        toString();
        JVM INSTR aastore ;
        i++;
        if(true) goto _L2; else goto _L1
_L1:
        return tokenized;
    }

    private static String[] tokenizeCN(String desc)
    {
        String tokenized[] = desc.split("\\s+");
        for(int i = 0; i < tokenized.length; i++)
            tokenized[i] = tokenized[i].replace("!", "");

        return tokenized;
    }

    public String getText()
    {
        return text;
    }

    public String text;
    public float width;
    private String cachedTokenizedText[];
    private String cachedTokenizedTextCN[];
    private static final float offsetter;

    static 
    {
        offsetter = 10F * Settings.scale;
    }
}
