// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PublisherIntegration.java

package com.megacrit.cardcrawl.integrations;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.screens.leaderboards.FilterButton;

// Referenced classes of package com.megacrit.cardcrawl.integrations:
//            DistributorFactory

public interface PublisherIntegration
{

    public abstract boolean isInitialized();

    public abstract void dispose();

    public abstract void deleteAllCloudFiles();

    public abstract boolean setStat(String s, int i);

    public abstract int getStat(String s);

    public abstract boolean incrementStat(String s, int i);

    public abstract long getGlobalStat(String s);

    public abstract void uploadDailyLeaderboardScore(String s, int i);

    public abstract void uploadLeaderboardScore(String s, int i);

    public abstract void unlockAchievement(String s);

    public abstract void getLeaderboardEntries(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass playerclass, com.megacrit.cardcrawl.screens.leaderboards.FilterButton.RegionSetting regionsetting, com.megacrit.cardcrawl.screens.leaderboards.FilterButton.LeaderboardType leaderboardtype, int i, int j);

    public abstract void getDailyLeaderboard(long l, int i, int j);

    public abstract void setRichPresenceDisplayPlaying(int i, String s);

    public abstract void setRichPresenceDisplayPlaying(int i, int j, String s);

    public abstract void setRichPresenceDisplayInMenu();

    public abstract int getNumUnlockedAchievements();

    public abstract DistributorFactory.Distributor getType();
}
