// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TimeHelper.java

package com.megacrit.cardcrawl.daily;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TimeHelper
{

    public TimeHelper()
    {
    }

    public static void setTime(long unixTime, boolean isOfflineMode)
    {
        if(!isTimeSet)
        {
            offlineMode = isOfflineMode;
            timeServerTime = unixTime;
            daySince1970 = timeServerTime / 0x15180L;
            logger.info((new StringBuilder()).append("Setting time to: ").append(timeServerTime).toString());
            endTimeMs = (daySince1970 + 1L) * 0x15180L * 1000L;
            logger.info("Day was set!");
            isTimeSet = true;
            format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        }
    }

    public static boolean isOfflineMode()
    {
        return offlineMode;
    }

    public static long daySince1970()
    {
        return daySince1970;
    }

    public static void update()
    {
        if(isTimeSet)
        {
            updateTimer -= Gdx.graphics.getDeltaTime();
            if(updateTimer < 0.0F)
                updateTimer = -1F;
        }
    }

    public static String getTodayDate()
    {
        return format.format(new Date(System.currentTimeMillis()));
    }

    public static String getDate(long numDaysSince1970)
    {
        long unixTimestampMs = (daySince1970 - numDaysSince1970) * 0x15180L * 1000L;
        return format.format(new Date(System.currentTimeMillis() - unixTimestampMs));
    }

    public static String getTimeLeft()
    {
        if(endTimeMs - System.currentTimeMillis() < 0L)
        {
            endTimeMs += 0x5265c00L;
            daySince1970++;
        }
        long remainingSec = (endTimeMs - System.currentTimeMillis()) / 1000L;
        long hours = remainingSec / 3600L;
        remainingSec %= 3600L;
        long minutes = remainingSec / 60L;
        return String.format("%02d:%02d:%02d", new Object[] {
            Long.valueOf(hours), Long.valueOf(minutes), Long.valueOf(remainingSec % 60L)
        });
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/daily/TimeHelper.getName());
    public static boolean isTimeSet = false;
    private static long daySince1970;
    private static long timeServerTime;
    private static long endTimeMs;
    private static final float UPDATE_FREQ = -1F;
    private static float updateTimer = -1F;
    private static boolean offlineMode = false;
    private static DateFormat format;

    static 
    {
        format = new SimpleDateFormat(CardCrawlGame.languagePack.getUIString("DailyScreen").TEXT[17]);
    }
}
