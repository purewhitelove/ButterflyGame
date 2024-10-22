// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DiscordIntegration.java

package com.megacrit.cardcrawl.integrations.discord;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.integrations.DistributorFactory;
import com.megacrit.cardcrawl.integrations.PublisherIntegration;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.leaderboards.FilterButton;
import java.io.File;
import net.arikia.dev.drpc.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DiscordIntegration
    implements PublisherIntegration
{

    public DiscordIntegration()
    {
        String appId = "406644123832156160";
        File unpackPath = new File("discord-rpc");
        unpackPath.deleteOnExit();
        if(!unpackPath.canWrite())
            logger.warn((new StringBuilder()).append("cannot write to dll unpack path: ").append(unpackPath.getAbsolutePath()).toString());
        if(!unpackPath.exists() && !unpackPath.mkdir())
            logger.warn((new StringBuilder()).append("Failed to create the directory for ").append(unpackPath.getAbsolutePath()).toString());
        logger.info((new StringBuilder()).append("Unpacking discord rpc dll to ").append(unpackPath.getAbsolutePath()).toString());
        DiscordRPC.discordInitialize(appId, new DiscordEventHandlers(), true, null, unpackPath);
    }

    public boolean isInitialized()
    {
        return false;
    }

    public void dispose()
    {
    }

    public void deleteAllCloudFiles()
    {
    }

    public boolean setStat(String id, int value)
    {
        return false;
    }

    public int getStat(String id)
    {
        return 0;
    }

    public boolean incrementStat(String id, int incrementAmt)
    {
        return false;
    }

    public long getGlobalStat(String id)
    {
        return 0L;
    }

    public void uploadDailyLeaderboardScore(String s, int i)
    {
    }

    public void uploadLeaderboardScore(String s, int i)
    {
    }

    public void unlockAchievement(String s)
    {
    }

    public void getLeaderboardEntries(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass playerclass, com.megacrit.cardcrawl.screens.leaderboards.FilterButton.RegionSetting regionsetting, com.megacrit.cardcrawl.screens.leaderboards.FilterButton.LeaderboardType leaderboardtype, int i, int j)
    {
    }

    public void getDailyLeaderboard(long l, int i, int j)
    {
    }

    public void setRichPresenceDisplayPlaying(int floor, String character)
    {
        if(TEXT == null)
            TEXT = CardCrawlGame.languagePack.getUIString("RichPresence").TEXT;
        if(Settings.isDailyRun)
        {
            String msg = String.format(TEXT[0], new Object[] {
                Integer.valueOf(floor)
            });
            logger.debug((new StringBuilder()).append("Setting Rich Presence: ").append(msg).toString());
            setRichPresenceData("", msg);
        } else
        if(Settings.isTrial)
        {
            String msg = String.format(TEXT[1], new Object[] {
                Integer.valueOf(floor)
            });
            logger.debug((new StringBuilder()).append("Setting Rich Presence: ").append(msg).toString());
            setRichPresenceData("", msg);
        } else
        {
            String msg = String.format((new StringBuilder()).append(character).append(TEXT[2]).toString(), new Object[] {
                Integer.valueOf(floor)
            });
            logger.debug((new StringBuilder()).append("Setting Rich Presence: ").append(msg).toString());
            setRichPresenceData("", msg);
        }
    }

    public void setRichPresenceDisplayPlaying(int floor, int ascension, String character)
    {
        if(TEXT == null)
            TEXT = CardCrawlGame.languagePack.getUIString("RichPresence").TEXT;
        if(Settings.isDailyRun)
        {
            String msg = String.format(TEXT[0], new Object[] {
                Integer.valueOf(floor)
            });
            logger.debug((new StringBuilder()).append("Setting Rich Presence: ").append(msg).toString());
            setRichPresenceData("", msg);
        } else
        if(Settings.isTrial)
        {
            String msg = String.format(TEXT[1], new Object[] {
                Integer.valueOf(floor)
            });
            logger.debug((new StringBuilder()).append("Setting Rich Presence: ").append(msg).toString());
            setRichPresenceData("", msg);
        } else
        if(Settings.language == com.megacrit.cardcrawl.core.Settings.GameLanguage.ENG || Settings.language == com.megacrit.cardcrawl.core.Settings.GameLanguage.DEU || Settings.language == com.megacrit.cardcrawl.core.Settings.GameLanguage.TUR || Settings.language == com.megacrit.cardcrawl.core.Settings.GameLanguage.KOR || Settings.language == com.megacrit.cardcrawl.core.Settings.GameLanguage.RUS || Settings.language == com.megacrit.cardcrawl.core.Settings.GameLanguage.SPA || Settings.language == com.megacrit.cardcrawl.core.Settings.GameLanguage.DUT)
        {
            String msg = String.format((new StringBuilder()).append(TEXT[4]).append(character).append(TEXT[2]).toString(), new Object[] {
                Integer.valueOf(ascension), Integer.valueOf(floor)
            });
            logger.debug((new StringBuilder()).append("Setting Rich Presence: ").append(msg).toString());
            setRichPresenceData("", msg);
        } else
        {
            String msg = String.format((new StringBuilder()).append(character).append(TEXT[2]).append(TEXT[4]).toString(), new Object[] {
                Integer.valueOf(floor), Integer.valueOf(ascension)
            });
            logger.debug((new StringBuilder()).append("Setting Rich Presence: ").append(msg).toString());
            setRichPresenceData("", msg);
        }
    }

    public void setRichPresenceDisplayInMenu()
    {
        if(TEXT == null)
            TEXT = CardCrawlGame.languagePack.getUIString("RichPresence").TEXT;
        logger.debug((new StringBuilder()).append("Setting Rich Presence: ").append(TEXT[3]).toString());
        setRichPresenceData(TEXT[3], "");
    }

    public int getNumUnlockedAchievements()
    {
        return 0;
    }

    public com.megacrit.cardcrawl.integrations.DistributorFactory.Distributor getType()
    {
        return com.megacrit.cardcrawl.integrations.DistributorFactory.Distributor.DISCORD;
    }

    private void setRichPresenceData(String state, String details)
    {
        DiscordRichPresence rich = (new net.arikia.dev.drpc.DiscordRichPresence.Builder(state)).setDetails(details).build();
        DiscordRPC.discordUpdatePresence(rich);
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/integrations/discord/DiscordIntegration.getName());
    private String TEXT[];

}
