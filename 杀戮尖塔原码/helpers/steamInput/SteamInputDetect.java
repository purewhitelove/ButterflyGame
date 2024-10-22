// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SteamInputDetect.java

package com.megacrit.cardcrawl.helpers.steamInput;

import com.codedisaster.steamworks.SteamController;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.helpers.steamInput:
//            SteamInputHelper

public class SteamInputDetect
    implements Runnable
{

    public SteamInputDetect()
    {
    }

    public void run()
    {
        int tries = 0;
        while(!Thread.currentThread().isInterrupted()) 
            try
            {
                tries++;
                int num = 0;
                num = SteamInputHelper.controller.getConnectedControllers(SteamInputHelper.controllerHandles);
                if(num != 0)
                {
                    SteamInputHelper.initActions(SteamInputHelper.controllerHandles[0]);
                    Settings.isControllerMode = true;
                    Settings.isTouchScreen = false;
                    logger.info("Steam Input controller found!");
                    SteamInputHelper.numControllers = 1;
                    Thread.currentThread().interrupt();
                } else
                if(tries == 12)
                {
                    SteamInputHelper.numControllers = 999;
                    Thread.currentThread().interrupt();
                }
                Thread.sleep(500L);
            }
            catch(InterruptedException e)
            {
                logger.info("Steam input detect thread interrupted!");
                Thread.currentThread().interrupt();
            }
        logger.info("Steam input detect thread will die now.");
        CardCrawlGame.sInputDetectThread = null;
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/helpers/steamInput/SteamInputDetect.getName());

}
