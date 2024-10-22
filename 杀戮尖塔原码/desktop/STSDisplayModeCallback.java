// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   STSDisplayModeCallback.java

package com.megacrit.cardcrawl.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglGraphics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class STSDisplayModeCallback extends LwjglApplicationConfiguration
    implements com.badlogic.gdx.backends.lwjgl.LwjglGraphics.SetDisplayModeCallback
{

    public STSDisplayModeCallback()
    {
    }

    public LwjglApplicationConfiguration onFailure(LwjglApplicationConfiguration initialConfig)
    {
        logger.error((new StringBuilder()).append("Failure to display LwjglApplication. InitialConfig=").append(initialConfig.toString()).toString());
        initialConfig.width = 1280;
        initialConfig.height = 720;
        initialConfig.fullscreen = false;
        return initialConfig;
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/desktop/STSDisplayModeCallback.getName());

}
