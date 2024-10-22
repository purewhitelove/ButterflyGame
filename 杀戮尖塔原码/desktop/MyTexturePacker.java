// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MyTexturePacker.java

package com.megacrit.cardcrawl.desktop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import java.io.PrintStream;

public class MyTexturePacker
{

    public MyTexturePacker()
    {
    }

    public static void main(String arg[])
    {
        packTextures();
    }

    private static void packTextures()
    {
        com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings settings = new com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings();
        settings.maxWidth = 2048;
        settings.maxHeight = 2048;
        System.out.println("Done!");
    }
}
