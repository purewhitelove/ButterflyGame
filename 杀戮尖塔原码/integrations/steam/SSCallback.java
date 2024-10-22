// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SSCallback.java

package com.megacrit.cardcrawl.integrations.steam;

import com.codedisaster.steamworks.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.daily.DailyScreen;
import com.megacrit.cardcrawl.screens.leaderboards.LeaderboardEntry;
import com.megacrit.cardcrawl.screens.leaderboards.LeaderboardScreen;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.integrations.steam:
//            SteamIntegration

public class SSCallback
    implements SteamUserStatsCallback
{

    public SSCallback(SteamIntegration steamIntegration)
    {
        this.steamIntegration = steamIntegration;
    }

    public void onGlobalStatsReceived(long arg0, SteamResult arg1)
    {
        logger.info((new StringBuilder()).append("1Bloop: ").append(arg0).toString());
    }

    public void onLeaderboardFindResult(SteamLeaderboardHandle handle, boolean found)
    {
        logger.info("onLeaderboardFindResult");
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$integrations$steam$SteamIntegration$LeaderboardTask[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$integrations$steam$SteamIntegration$LeaderboardTask = new int[SteamIntegration.LeaderboardTask.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$integrations$steam$SteamIntegration$LeaderboardTask[SteamIntegration.LeaderboardTask.UPLOAD_DAILY.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$integrations$steam$SteamIntegration$LeaderboardTask[SteamIntegration.LeaderboardTask.UPLOAD.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$integrations$steam$SteamIntegration$LeaderboardTask[SteamIntegration.LeaderboardTask.RETRIEVE_DAILY.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$integrations$steam$SteamIntegration$LeaderboardTask[SteamIntegration.LeaderboardTask.RETRIEVE.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
            }
        }

        if(found)
            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.integrations.steam.SteamIntegration.LeaderboardTask[SteamIntegration.task.ordinal()])
            {
            case 1: // '\001'
                SteamIntegration.lbHandle = handle;
                SteamIntegration.uploadDailyLeaderboardHelper();
                break;

            case 2: // '\002'
                SteamIntegration.lbHandle = handle;
                SteamIntegration.uploadLeaderboardHelper();
                break;

            case 3: // '\003'
                SteamIntegration.lbHandle = handle;
                SteamIntegration.getLeaderboardEntryHelper();
                break;

            case 4: // '\004'
                SteamIntegration.lbHandle = handle;
                SteamIntegration.getLeaderboardEntryHelper();
                break;
            }
    }

    public void onLeaderboardScoreUploaded(boolean success, SteamLeaderboardHandle handle, int score, boolean changed, int globalRankNew, int globalRankPrevious)
    {
        if(!success)
            logger.info((new StringBuilder()).append("Failed to upload leaderboard data: ").append(score).toString());
        else
        if(!changed)
            logger.info((new StringBuilder()).append("Leaderboard data not changed for data: ").append(score).toString());
        else
            logger.info((new StringBuilder()).append("Successfully uploaded leaderboard data: ").append(score).toString());
        steamIntegration.didCompleteCallback(success);
    }

    public void onNumberOfCurrentPlayersReceived(boolean flag, int i)
    {
    }

    public void onLeaderboardScoresDownloaded(SteamLeaderboardHandle handle, SteamLeaderboardEntriesHandle entries, int numEntries)
    {
        if(SteamIntegration.task == SteamIntegration.LeaderboardTask.RETRIEVE)
        {
            logger.info((new StringBuilder()).append("Downloaded ").append(numEntries).append(" entries").toString());
            int details[] = new int[16];
            CardCrawlGame.mainMenuScreen.leaderboardsScreen.entries.clear();
            for(int i = 0; i < numEntries; i++)
            {
                SteamLeaderboardEntry entry = new SteamLeaderboardEntry();
                if(SteamIntegration.steamStats.getDownloadedLeaderboardEntry(entries, i, entry, details))
                {
                    int rTemp = entry.getGlobalRank();
                    if(i == 0)
                        CardCrawlGame.mainMenuScreen.leaderboardsScreen.currentStartIndex = rTemp;
                    else
                    if(i == numEntries)
                        CardCrawlGame.mainMenuScreen.leaderboardsScreen.currentEndIndex = rTemp;
                    CardCrawlGame.mainMenuScreen.leaderboardsScreen.entries.add(new LeaderboardEntry(rTemp, SteamIntegration.steamFriends.getFriendPersonaName(entry.getSteamIDUser()), entry.getScore(), SteamIntegration.gettingTime, SteamIntegration.accountId != -1 && SteamIntegration.accountId == entry.getSteamIDUser().getAccountID()));
                } else
                {
                    logger.info((new StringBuilder()).append("FAILED TO GET LEADERBOARD ENTRY: ").append(i).toString());
                }
            }

            CardCrawlGame.mainMenuScreen.leaderboardsScreen.waiting = false;
        } else
        if(SteamIntegration.task == SteamIntegration.LeaderboardTask.RETRIEVE_DAILY)
        {
            logger.info((new StringBuilder()).append("[DAILY] Downloaded ").append(numEntries).append(" entries").toString());
            int details[] = new int[16];
            CardCrawlGame.mainMenuScreen.dailyScreen.entries.clear();
            for(int i = 0; i < numEntries; i++)
            {
                SteamLeaderboardEntry entry = new SteamLeaderboardEntry();
                if(SteamIntegration.steamStats.getDownloadedLeaderboardEntry(entries, i, entry, details))
                {
                    int rTemp = entry.getGlobalRank();
                    if(i == 0)
                        CardCrawlGame.mainMenuScreen.dailyScreen.currentStartIndex = rTemp;
                    else
                    if(i == numEntries)
                        CardCrawlGame.mainMenuScreen.dailyScreen.currentEndIndex = rTemp;
                    CardCrawlGame.mainMenuScreen.dailyScreen.entries.add(new LeaderboardEntry(rTemp, SteamIntegration.steamFriends.getFriendPersonaName(entry.getSteamIDUser()), entry.getScore(), SteamIntegration.gettingTime, SteamIntegration.accountId != -1 && SteamIntegration.accountId == entry.getSteamIDUser().getAccountID()));
                } else
                {
                    logger.info((new StringBuilder()).append("FAILED TO GET LEADERBOARD ENTRY: ").append(i).toString());
                }
            }

            CardCrawlGame.mainMenuScreen.dailyScreen.waiting = false;
        }
    }

    public void onUserAchievementStored(long arg0, boolean arg1, String arg2, int arg3, int arg4)
    {
        logger.info("Achievement Stored");
    }

    public void onUserStatsReceived(long arg0, SteamID arg1, SteamResult arg2)
    {
        logger.info((new StringBuilder()).append("SteamID: ").append(arg1.getAccountID()).toString());
        logger.info((new StringBuilder()).append("APPID: ").append(arg0).toString());
    }

    public void onUserStatsStored(long arg0, SteamResult arg1)
    {
        logger.info("Stat Stored");
    }

    public void onUserStatsUnloaded(SteamID steamid)
    {
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/integrations/steam/SSCallback.getName());
    private SteamIntegration steamIntegration;

}
