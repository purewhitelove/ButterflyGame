// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SteamIntegration.java

package com.megacrit.cardcrawl.integrations.steam;

import com.codedisaster.steamworks.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.daily.DailyScreen;
import com.megacrit.cardcrawl.daily.TimeHelper;
import com.megacrit.cardcrawl.integrations.DistributorFactory;
import com.megacrit.cardcrawl.integrations.PublisherIntegration;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.leaderboards.FilterButton;
import com.megacrit.cardcrawl.screens.leaderboards.LeaderboardScreen;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import com.megacrit.cardcrawl.screens.stats.AchievementGrid;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.integrations.steam:
//            SSCallback, SUCallback, SFCallback, SteamTicker, 
//            SRCallback

public class SteamIntegration
    implements PublisherIntegration
{
    private static class StatTuple
    {

        String stat;
        int score;

        StatTuple(String statName, int scoreVal)
        {
            stat = statName;
            score = scoreVal;
        }
    }

    static final class LeaderboardTask extends Enum
    {

        public static LeaderboardTask[] values()
        {
            return (LeaderboardTask[])$VALUES.clone();
        }

        public static LeaderboardTask valueOf(String name)
        {
            return (LeaderboardTask)Enum.valueOf(com/megacrit/cardcrawl/integrations/steam/SteamIntegration$LeaderboardTask, name);
        }

        public static final LeaderboardTask RETRIEVE;
        public static final LeaderboardTask RETRIEVE_DAILY;
        public static final LeaderboardTask UPLOAD;
        public static final LeaderboardTask UPLOAD_DAILY;
        private static final LeaderboardTask $VALUES[];

        static 
        {
            RETRIEVE = new LeaderboardTask("RETRIEVE", 0);
            RETRIEVE_DAILY = new LeaderboardTask("RETRIEVE_DAILY", 1);
            UPLOAD = new LeaderboardTask("UPLOAD", 2);
            UPLOAD_DAILY = new LeaderboardTask("UPLOAD_DAILY", 3);
            $VALUES = (new LeaderboardTask[] {
                RETRIEVE, RETRIEVE_DAILY, UPLOAD, UPLOAD_DAILY
            });
        }

        private LeaderboardTask(String s, int i)
        {
            super(s, i);
        }
    }


    public SteamIntegration()
    {
        if(!Settings.isDev || Settings.isModded)
            try
            {
                SteamAPI.loadLibraries();
                if(SteamAPI.init())
                {
                    logger.info("[SUCCESS] Steam API initialized successfully.");
                    steamStats = new SteamUserStats(new SSCallback(this));
                    steamUser = new SteamUser(new SUCallback());
                    steamApps = new SteamApps();
                    steamFriends = new SteamFriends(new SFCallback());
                    logger.info((new StringBuilder()).append("BUILD ID: ").append(steamApps.getAppBuildId()).toString());
                    logger.info((new StringBuilder()).append("CURRENT LANG: ").append(steamApps.getCurrentGameLanguage()).toString());
                    SteamID id = steamApps.getAppOwner();
                    accountId = id.getAccountID();
                    logger.info((new StringBuilder()).append("ACCOUNT ID: ").append(accountId).toString());
                    thread = new Thread(new SteamTicker());
                    thread.setName("SteamTicker");
                    thread.start();
                } else
                {
                    logger.info("[FAILURE] Steam API failed to initialize correctly.");
                }
            }
            catch(SteamException e)
            {
                e.printStackTrace();
            }
        if(SteamAPI.isSteamRunning())
            requestGlobalStats(365);
    }

    public boolean isInitialized()
    {
        return steamUser != null && steamStats != null;
    }

    public ArrayList getAllCloudFiles()
    {
        SteamRemoteStorage remoteStorage = new SteamRemoteStorage(new SRCallback());
        int numFiles = remoteStorage.getFileCount();
        logger.info((new StringBuilder()).append("Num of files: ").append(numFiles).toString());
        ArrayList files = new ArrayList();
        for(int i = 0; i < numFiles; i++)
        {
            int sizes[] = new int[1];
            String file = remoteStorage.getFileNameAndSize(i, sizes);
            boolean exists = remoteStorage.fileExists(file);
            if(exists)
                files.add(file);
            logger.info((new StringBuilder()).append("# ").append(i).append(" : name=").append(file).append(", size=").append(sizes[0]).append(", exists=").append(exists ? "yes" : "no").toString());
        }

        remoteStorage.dispose();
        return files;
    }

    public void deleteAllCloudFiles()
    {
        deleteCloudFiles(getAllCloudFiles());
        logger.info("Deleted all Cloud Files");
    }

    private void deleteCloudFiles(ArrayList files)
    {
        SteamRemoteStorage remoteStorage = new SteamRemoteStorage(new SRCallback());
        String file;
        for(Iterator iterator = files.iterator(); iterator.hasNext(); remoteStorage.fileDelete(file))
        {
            file = (String)iterator.next();
            logger.info((new StringBuilder()).append("Deleting file: ").append(file).toString());
        }

        remoteStorage.dispose();
    }

    public static String basename(String path)
    {
        Path p = Paths.get(path, new String[0]);
        return p.getFileName().toString();
    }

    public void unlockAchievement(String id)
    {
        logger.info((new StringBuilder()).append("unlockAchievement: ").append(id).toString());
        if(steamStats != null)
            if(steamStats.setAchievement(id))
                steamStats.storeStats();
            else
                logger.info((new StringBuilder()).append("[ERROR] Could not find achievement ").append(id).toString());
    }

    public static void removeAllAchievementsBeCarefulNotToPush()
    {
        if(Settings.isDev && Settings.isBeta && steamStats != null && steamStats.resetAllStats(true))
            steamStats.storeStats();
    }

    public boolean incrementStat(String id, int incrementAmt)
    {
        logger.info((new StringBuilder()).append("incrementStat: ").append(id).toString());
        if(steamStats != null)
        {
            if(steamStats.setStatI(id, getStat(id) + incrementAmt))
            {
                return true;
            } else
            {
                logger.info((new StringBuilder()).append("Stat: ").append(id).append(" not found.").toString());
                return false;
            }
        } else
        {
            logger.info((new StringBuilder()).append("[ERROR] Could not find stat ").append(id).toString());
            return false;
        }
    }

    public int getStat(String id)
    {
        logger.info((new StringBuilder()).append("getStat: ").append(id).toString());
        if(steamStats != null)
            return steamStats.getStatI(id, 0);
        else
            return -1;
    }

    public boolean setStat(String id, int value)
    {
        logger.info((new StringBuilder()).append("setStat: ").append(id).toString());
        if(steamStats != null)
        {
            if(steamStats.setStatI(id, value))
            {
                logger.info((new StringBuilder()).append(id).append(" stat set to ").append(value).toString());
                return true;
            } else
            {
                logger.info((new StringBuilder()).append("Stat: ").append(id).append(" not found.").toString());
                return false;
            }
        } else
        {
            logger.info((new StringBuilder()).append("[ERROR] Could not find stat ").append(id).toString());
            return false;
        }
    }

    public long getGlobalStat(String id)
    {
        logger.info("getGlobalStat");
        if(steamStats != null)
            return steamStats.getGlobalStat(id, 0L);
        else
            return -1L;
    }

    private static void requestGlobalStats(int i)
    {
        logger.info("requestGlobalStats");
        if(steamStats != null)
            steamStats.requestGlobalStats(i);
    }

    public void getLeaderboardEntries(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass pClass, com.megacrit.cardcrawl.screens.leaderboards.FilterButton.RegionSetting rSetting, com.megacrit.cardcrawl.screens.leaderboards.FilterButton.LeaderboardType lType, int start, int end)
    {
        task = LeaderboardTask.RETRIEVE;
        startIndex = start;
        endIndex = end;
        if(lType == com.megacrit.cardcrawl.screens.leaderboards.FilterButton.LeaderboardType.FASTEST_WIN)
            gettingTime = true;
        else
            gettingTime = false;
        if(rSetting == com.megacrit.cardcrawl.screens.leaderboards.FilterButton.RegionSetting.GLOBAL)
            retrieveGlobal = true;
        else
            retrieveGlobal = false;
        if(steamStats != null)
            steamStats.findLeaderboard(createGetLeaderboardString(pClass, lType));
    }

    public void getDailyLeaderboard(long date, int start, int end)
    {
        task = LeaderboardTask.RETRIEVE_DAILY;
        startIndex = start;
        endIndex = end;
        retrieveGlobal = true;
        gettingTime = false;
        if(steamStats != null)
        {
            StringBuilder leaderboardRetrieveString = new StringBuilder("DAILY_");
            leaderboardRetrieveString.append(Long.toString(date));
            if(Settings.isBeta)
                leaderboardRetrieveString.append("_BETA");
            steamStats.findOrCreateLeaderboard(leaderboardRetrieveString.toString(), com.codedisaster.steamworks.SteamUserStats.LeaderboardSortMethod.Descending, com.codedisaster.steamworks.SteamUserStats.LeaderboardDisplayType.Numeric);
        }
    }

    private static String createGetLeaderboardString(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass pClass, com.megacrit.cardcrawl.screens.leaderboards.FilterButton.LeaderboardType lType)
    {
        String retVal = "";
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$characters$AbstractPlayer$PlayerClass[];
            static final int $SwitchMap$com$megacrit$cardcrawl$screens$leaderboards$FilterButton$LeaderboardType[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$screens$leaderboards$FilterButton$LeaderboardType = new int[com.megacrit.cardcrawl.screens.leaderboards.FilterButton.LeaderboardType.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$leaderboards$FilterButton$LeaderboardType[com.megacrit.cardcrawl.screens.leaderboards.FilterButton.LeaderboardType.AVG_FLOOR.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$leaderboards$FilterButton$LeaderboardType[com.megacrit.cardcrawl.screens.leaderboards.FilterButton.LeaderboardType.AVG_SCORE.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$leaderboards$FilterButton$LeaderboardType[com.megacrit.cardcrawl.screens.leaderboards.FilterButton.LeaderboardType.CONSECUTIVE_WINS.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$leaderboards$FilterButton$LeaderboardType[com.megacrit.cardcrawl.screens.leaderboards.FilterButton.LeaderboardType.FASTEST_WIN.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$leaderboards$FilterButton$LeaderboardType[com.megacrit.cardcrawl.screens.leaderboards.FilterButton.LeaderboardType.HIGH_SCORE.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$leaderboards$FilterButton$LeaderboardType[com.megacrit.cardcrawl.screens.leaderboards.FilterButton.LeaderboardType.SPIRE_LEVEL.ordinal()] = 6;
                }
                catch(NoSuchFieldError nosuchfielderror5) { }
                $SwitchMap$com$megacrit$cardcrawl$characters$AbstractPlayer$PlayerClass = new int[com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$characters$AbstractPlayer$PlayerClass[com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.IRONCLAD.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror6) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$characters$AbstractPlayer$PlayerClass[com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.THE_SILENT.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror7) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$characters$AbstractPlayer$PlayerClass[com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.DEFECT.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror8) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$characters$AbstractPlayer$PlayerClass[com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.WATCHER.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror9) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass[pClass.ordinal()])
        {
        case 1: // '\001'
            retVal = (new StringBuilder()).append(retVal).append("IRONCLAD").toString();
            break;

        case 2: // '\002'
            retVal = (new StringBuilder()).append(retVal).append("SILENT").toString();
            break;

        case 3: // '\003'
            retVal = (new StringBuilder()).append(retVal).append("DEFECT").toString();
            break;

        case 4: // '\004'
            retVal = (new StringBuilder()).append(retVal).append("WATCHER").toString();
            break;
        }
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.screens.leaderboards.FilterButton.LeaderboardType[lType.ordinal()])
        {
        case 1: // '\001'
            retVal = (new StringBuilder()).append(retVal).append("_AVG_FLOOR").toString();
            break;

        case 2: // '\002'
            retVal = (new StringBuilder()).append(retVal).append("_AVG_SCORE").toString();
            break;

        case 3: // '\003'
            retVal = (new StringBuilder()).append(retVal).append("_CONSECUTIVE_WINS").toString();
            break;

        case 4: // '\004'
            retVal = (new StringBuilder()).append(retVal).append("_FASTEST_WIN").toString();
            break;

        case 5: // '\005'
            retVal = (new StringBuilder()).append(retVal).append("_HIGH_SCORE").toString();
            break;

        case 6: // '\006'
            retVal = (new StringBuilder()).append(retVal).append("_SPIRE_LEVEL").toString();
            break;
        }
        if(Settings.isBeta)
            retVal = (new StringBuilder()).append(retVal).append("_BETA").toString();
        return retVal;
    }

    public void uploadLeaderboardScore(String name, int score)
    {
        if(steamUser == null || steamStats == null)
            return;
        if(isUploadingScore)
        {
            statsToUpload.add(new StatTuple(name, score));
        } else
        {
            logger.info(String.format("Uploading Steam Leaderboard score (%s: %d)", new Object[] {
                name, Integer.valueOf(score)
            }));
            isUploadingScore = true;
            task = LeaderboardTask.UPLOAD;
            lbScore = score;
            steamStats.findLeaderboard(name);
        }
    }

    public void uploadDailyLeaderboardScore(String name, int score)
    {
        if(!TimeHelper.isOfflineMode())
        {
            if(steamUser == null || steamStats == null)
            {
                logger.info("User is NOT connected to Steam, unable to upload daily score.");
                return;
            }
            if(isUploadingScore)
            {
                statsToUpload.add(new StatTuple(name, score));
            } else
            {
                logger.info(String.format("Uploading [DAILY] Steam Leaderboard score (%s: %d)", new Object[] {
                    name, Integer.valueOf(score)
                }));
                isUploadingScore = true;
                task = LeaderboardTask.UPLOAD_DAILY;
                lbScore = score;
                steamStats.findOrCreateLeaderboard(name, com.codedisaster.steamworks.SteamUserStats.LeaderboardSortMethod.Descending, com.codedisaster.steamworks.SteamUserStats.LeaderboardDisplayType.Numeric);
            }
        }
    }

    void didCompleteCallback(boolean success)
    {
        logger.info("didCompleteCallback");
        isUploadingScore = false;
        if(statsToUpload.size() > 0)
        {
            StatTuple uploadMe = (StatTuple)statsToUpload.remove();
            uploadLeaderboardScore(uploadMe.stat, uploadMe.score);
        }
    }

    static void uploadLeaderboardHelper()
    {
        logger.info("uploadLeaderboardHelper");
        steamStats.uploadLeaderboardScore(lbHandle, com.codedisaster.steamworks.SteamUserStats.LeaderboardUploadScoreMethod.KeepBest, lbScore, new int[0]);
    }

    static void uploadDailyLeaderboardHelper()
    {
        logger.info("uploadDailyLeaderboardHelper");
        steamStats.uploadLeaderboardScore(lbHandle, com.codedisaster.steamworks.SteamUserStats.LeaderboardUploadScoreMethod.KeepBest, lbScore, new int[0]);
    }

    static void getLeaderboardEntryHelper()
    {
        if(task == LeaderboardTask.RETRIEVE)
        {
            if(retrieveGlobal)
            {
                logger.info((new StringBuilder()).append("Downloading GLOBAL entries: ").append(startIndex).append(" - ").append(endIndex).toString());
                if(CardCrawlGame.mainMenuScreen.leaderboardsScreen.viewMyScore)
                {
                    steamStats.downloadLeaderboardEntries(lbHandle, com.codedisaster.steamworks.SteamUserStats.LeaderboardDataRequest.GlobalAroundUser, -9, 10);
                    CardCrawlGame.mainMenuScreen.leaderboardsScreen.viewMyScore = false;
                } else
                {
                    steamStats.downloadLeaderboardEntries(lbHandle, com.codedisaster.steamworks.SteamUserStats.LeaderboardDataRequest.Global, startIndex, endIndex);
                }
            } else
            {
                logger.info((new StringBuilder()).append("Downloading FRIEND entries: ").append(startIndex).append(" - ").append(endIndex).toString());
                steamStats.downloadLeaderboardEntries(lbHandle, com.codedisaster.steamworks.SteamUserStats.LeaderboardDataRequest.Friends, startIndex, endIndex);
            }
        } else
        if(task == LeaderboardTask.RETRIEVE_DAILY)
            if(CardCrawlGame.mainMenuScreen.dailyScreen.viewMyScore)
            {
                steamStats.downloadLeaderboardEntries(lbHandle, com.codedisaster.steamworks.SteamUserStats.LeaderboardDataRequest.GlobalAroundUser, -9, 10);
                CardCrawlGame.mainMenuScreen.dailyScreen.viewMyScore = false;
            } else
            {
                logger.info((new StringBuilder()).append("Downloading GLOBAL entries: ").append(startIndex).append(" - ").append(endIndex).toString());
                steamStats.downloadLeaderboardEntries(lbHandle, com.codedisaster.steamworks.SteamUserStats.LeaderboardDataRequest.Global, startIndex, endIndex);
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
            setRichPresenceData("status", msg);
        } else
        if(Settings.isTrial)
        {
            String msg = String.format(TEXT[1], new Object[] {
                Integer.valueOf(floor)
            });
            logger.debug((new StringBuilder()).append("Setting Rich Presence: ").append(msg).toString());
            setRichPresenceData("status", msg);
        } else
        if(Settings.language == com.megacrit.cardcrawl.core.Settings.GameLanguage.ENG || Settings.language == com.megacrit.cardcrawl.core.Settings.GameLanguage.DEU || Settings.language == com.megacrit.cardcrawl.core.Settings.GameLanguage.THA || Settings.language == com.megacrit.cardcrawl.core.Settings.GameLanguage.TUR || Settings.language == com.megacrit.cardcrawl.core.Settings.GameLanguage.KOR || Settings.language == com.megacrit.cardcrawl.core.Settings.GameLanguage.RUS || Settings.language == com.megacrit.cardcrawl.core.Settings.GameLanguage.SPA || Settings.language == com.megacrit.cardcrawl.core.Settings.GameLanguage.DUT)
        {
            String msg = String.format((new StringBuilder()).append(TEXT[4]).append(character).append(TEXT[2]).toString(), new Object[] {
                Integer.valueOf(ascension), Integer.valueOf(floor)
            });
            logger.debug((new StringBuilder()).append("Setting Rich Presence: ").append(msg).toString());
            setRichPresenceData("status", msg);
        } else
        {
            String msg = String.format((new StringBuilder()).append(character).append(TEXT[2]).append(TEXT[4]).toString(), new Object[] {
                Integer.valueOf(floor), Integer.valueOf(ascension)
            });
            logger.debug((new StringBuilder()).append("Setting Rich Presence: ").append(msg).toString());
            setRichPresenceData("status", msg);
        }
        setRichPresenceData("steam_display", "#Status");
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
            setRichPresenceData("status", msg);
        } else
        if(Settings.isTrial)
        {
            String msg = String.format(TEXT[1], new Object[] {
                Integer.valueOf(floor)
            });
            logger.debug((new StringBuilder()).append("Setting Rich Presence: ").append(msg).toString());
            setRichPresenceData("status", msg);
        } else
        {
            String msg = String.format((new StringBuilder()).append(character).append(TEXT[2]).toString(), new Object[] {
                Integer.valueOf(floor)
            });
            logger.debug((new StringBuilder()).append("Setting Rich Presence: ").append(msg).toString());
            setRichPresenceData("status", msg);
        }
        setRichPresenceData("steam_display", "#Status");
    }

    public void setRichPresenceDisplayInMenu()
    {
        if(TEXT == null)
            TEXT = CardCrawlGame.languagePack.getUIString("RichPresence").TEXT;
        logger.debug((new StringBuilder()).append("Setting Rich Presence: ").append(String.format(TEXT[3], new Object[0])).toString());
        setRichPresenceData("status", TEXT[3]);
        setRichPresenceData("steam_display", "#Status");
    }

    public int getNumUnlockedAchievements()
    {
        int retVal = 0;
        ArrayList keys = new ArrayList();
        keys.add("ADRENALINE");
        keys.add("ASCEND_0");
        keys.add("ASCEND_10");
        keys.add("ASCEND_20");
        keys.add("AUTOMATON");
        keys.add("BARRICADED");
        keys.add("CATALYST");
        keys.add("CHAMP");
        keys.add("COLLECTOR");
        keys.add("COME_AT_ME");
        keys.add("COMMON_SENSE");
        keys.add("CROW");
        keys.add("DONUT");
        keys.add("EMERALD");
        keys.add("EMERALD_PLUS");
        keys.add("FOCUSED");
        keys.add("GHOST_GUARDIAN");
        keys.add("GUARDIAN");
        keys.add("IMPERVIOUS");
        keys.add("INFINITY");
        keys.add("JAXXED");
        keys.add("LUCKY_DAY");
        keys.add("MINIMALIST");
        keys.add("NEON");
        keys.add("NINJA");
        keys.add("ONE_RELIC");
        keys.add("PERFECT");
        keys.add("PLAGUE");
        keys.add("POWERFUL");
        keys.add("PURITY");
        keys.add("RUBY");
        keys.add("RUBY_PLUS");
        keys.add("SAPPHIRE");
        keys.add("SAPPHIRE_PLUS");
        keys.add("AMETHYST");
        keys.add("AMETHYST_PLUS");
        keys.add("SHAPES");
        keys.add("SHRUG_IT_OFF");
        keys.add("SLIME_BOSS");
        keys.add("SPEED_CLIMBER");
        keys.add("THE_ENDING");
        keys.add("THE_PACT");
        keys.add("TIME_EATER");
        keys.add("TRANSIENT");
        keys.add("YOU_ARE_NOTHING");
        Iterator iterator = keys.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            String s = (String)iterator.next();
            if(steamStats.isAchieved(s, false))
                retVal++;
        } while(true);
        return retVal;
    }

    public com.megacrit.cardcrawl.integrations.DistributorFactory.Distributor getType()
    {
        return com.megacrit.cardcrawl.integrations.DistributorFactory.Distributor.STEAM;
    }

    private void setRichPresenceData(String key, String value)
    {
        if(steamFriends != null && !steamFriends.setRichPresence(key, value))
            logger.info((new StringBuilder()).append("Failed to set Steam Rich Presence: key=").append(key).append(" value=").append(value).toString());
    }

    public void dispose()
    {
        if(isInitialized())
            SteamAPI.shutdown();
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/integrations/steam/SteamIntegration.getName());
    private static String TEXT[] = null;
    static SteamUserStats steamStats;
    private static SteamUser steamUser;
    private static SteamApps steamApps;
    static SteamFriends steamFriends;
    private static Thread thread;
    static int accountId = -1;
    static SteamLeaderboardHandle lbHandle = null;
    static LeaderboardTask task = null;
    private static boolean retrieveGlobal = true;
    static boolean gettingTime = false;
    private static int lbScore = 0;
    private static int startIndex = 0;
    private static int endIndex = 0;
    private static boolean isUploadingScore = false;
    private static Queue statsToUpload = new LinkedList();

}
