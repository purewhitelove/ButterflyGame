// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TestLauncher.java

package com.megacrit.cardcrawl.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.megacrit.cardcrawl.core.TestGame;

public class TestLauncher
{

    public TestLauncher()
    {
    }

    public static void main(String arg[])
    {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.resizable = false;
        config.title = "1600x900 Fullscreen Test";
        config.width = 1600;
        config.height = 900;
        config.fullscreen = true;
        config.foregroundFPS = 60;
        config.backgroundFPS = 24;
        new LwjglApplication(new TestGame(), config);
    }
}
