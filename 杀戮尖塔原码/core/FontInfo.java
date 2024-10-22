// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FontInfo.java

package com.megacrit.cardcrawl.core;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class FontInfo
{

    public FontInfo(String setName, BitmapFont setFont)
    {
        name = setName;
        font = setFont;
    }

    public BitmapFont font;
    public String name;
}
