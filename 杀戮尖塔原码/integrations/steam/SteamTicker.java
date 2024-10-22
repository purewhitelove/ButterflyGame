// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SteamTicker.java

package com.megacrit.cardcrawl.integrations.steam;

import com.codedisaster.steamworks.SteamAPI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SteamTicker
    implements Runnable
{

    public SteamTicker()
    {
    }

    public void run()
    {
        logger.info("Steam Ticker initialized.");
        for(; SteamAPI.isSteamRunning(); SteamAPI.runCallbacks())
            try
            {
                Thread.sleep(66L);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }

        logger.info("[ERROR] SteamAPI stopped running.");
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/integrations/steam/SteamTicker.getName());

}
