// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EaIntegration.java

package com.megacrit.cardcrawl.integrations.ea;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.integrations.DistributorFactory;
import com.megacrit.cardcrawl.integrations.PublisherIntegration;
import com.megacrit.cardcrawl.screens.leaderboards.FilterButton;

public class EaIntegration
    implements PublisherIntegration
{

    public EaIntegration()
    {
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

    public void setRichPresenceDisplayPlaying(int i, String s)
    {
    }

    public void setRichPresenceDisplayPlaying(int i, int j, String s)
    {
    }

    public void setRichPresenceDisplayInMenu()
    {
    }

    public com.megacrit.cardcrawl.integrations.DistributorFactory.Distributor getType()
    {
        return com.megacrit.cardcrawl.integrations.DistributorFactory.Distributor.EA;
    }

    private void setRichPresenceData(String s, String s1)
    {
    }

    public int getNumUnlockedAchievements()
    {
        return 0;
    }
}
