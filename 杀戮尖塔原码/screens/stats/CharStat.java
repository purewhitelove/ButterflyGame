// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CharStat.java

package com.megacrit.cardcrawl.screens.stats;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Prefs;
import com.megacrit.cardcrawl.localization.AchievementStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.screens.stats:
//            RunData

public class CharStat
{

    public CharStat(ArrayList allChars)
    {
        runs = new ArrayList();
        cardsUnlocked = 0;
        relicsUnlocked = 0;
        furthestAscent = 0;
        highestScore = 0;
        totalFloorsClimbed = 0;
        numVictory = 0;
        numDeath = 0;
        enemyKilled = 0;
        bossKilled = 0;
        playTime = 0L;
        fastestTime = 0xe8d4a50fffL;
        int highestFloorTmp = 0;
        int highestDailyTmp = 0;
        for(Iterator iterator = allChars.iterator(); iterator.hasNext();)
        {
            CharStat stat = (CharStat)iterator.next();
            cardsUnlocked += stat.cardsUnlocked;
            relicsUnlocked += stat.relicsUnlocked;
            if(stat.furthestAscent > highestFloorTmp)
            {
                furthestAscent = stat.furthestAscent;
                highestFloorTmp = furthestAscent;
            }
            if(stat.highestDaily > highestDailyTmp)
            {
                highestDaily = stat.highestDaily;
                highestDailyTmp = highestDaily;
            }
            if(stat.fastestTime < fastestTime && stat.fastestTime != 0L)
                fastestTime = stat.fastestTime;
            totalFloorsClimbed += stat.totalFloorsClimbed;
            numVictory += stat.numVictory;
            numDeath += stat.numDeath;
            enemyKilled += stat.enemyKilled;
            bossKilled += stat.bossKilled;
            playTime += stat.playTime;
        }

        info = (new StringBuilder()).append(TEXT[0]).append(formatHMSM(playTime)).append(" NL ").toString();
        new StringBuilder();
        this;
        JVM INSTR dup_x1 ;
        info;
        append();
        TEXT[1];
        append();
        numVictory;
        append();
        " NL ";
        append();
        toString();
        info;
        new StringBuilder();
        this;
        JVM INSTR dup_x1 ;
        info;
        append();
        TEXT[2];
        append();
        numDeath;
        append();
        " NL ";
        append();
        toString();
        info;
        new StringBuilder();
        this;
        JVM INSTR dup_x1 ;
        info;
        append();
        TEXT[3];
        append();
        totalFloorsClimbed;
        append();
        " NL ";
        append();
        toString();
        info;
        new StringBuilder();
        this;
        JVM INSTR dup_x1 ;
        info;
        append();
        TEXT[4];
        append();
        bossKilled;
        append();
        " NL ";
        append();
        toString();
        info;
        new StringBuilder();
        this;
        JVM INSTR dup_x1 ;
        info;
        append();
        TEXT[5];
        append();
        enemyKilled;
        append();
        " NL ";
        append();
        toString();
        info;
        int unlockedCardCount;
        int lockedCardCount;
        info2 = (new StringBuilder()).append(TEXT[7]).append(UnlockTracker.getCardsSeenString()).append(" NL ").toString();
        unlockedCardCount = UnlockTracker.unlockedRedCardCount + UnlockTracker.unlockedGreenCardCount + UnlockTracker.unlockedBlueCardCount + UnlockTracker.unlockedPurpleCardCount;
        lockedCardCount = UnlockTracker.lockedRedCardCount + UnlockTracker.lockedGreenCardCount + UnlockTracker.lockedBlueCardCount + UnlockTracker.lockedPurpleCardCount;
        new StringBuilder();
        this;
        JVM INSTR dup_x1 ;
        info2;
        append();
        TEXT[8];
        append();
        unlockedCardCount;
        append();
        "/";
        append();
        lockedCardCount;
        append();
        " NL ";
        append();
        toString();
        info2;
        new StringBuilder();
        this;
        JVM INSTR dup_x1 ;
        info2;
        append();
        TEXT[9];
        append();
        UnlockTracker.getRelicsSeenString();
        append();
        " NL ";
        append();
        toString();
        info2;
        new StringBuilder();
        this;
        JVM INSTR dup_x1 ;
        info2;
        append();
        TEXT[10];
        append();
        UnlockTracker.unlockedRelicCount;
        append();
        "/";
        append();
        UnlockTracker.lockedRelicCount;
        append();
        " NL ";
        append();
        toString();
        info2;
        if(fastestTime == 0xe8d4a50fffL) goto _L2; else goto _L1
_L1:
        new StringBuilder();
        this;
        JVM INSTR dup_x1 ;
        info2;
        append();
        TEXT[13];
        append();
        formatHMSM(fastestTime);
        append();
        " NL ";
        append();
        toString();
        info2;
_L2:
    }

    public CharStat(AbstractPlayer c)
    {
        runs = new ArrayList();
        pref = c.getPrefs();
        cardsUnlocked = calculateCardsUnlocked(c);
        cardsDiscovered = getSeenCardCount(c);
        cardsToDiscover = getCardCountForChar(c);
        relicsUnlocked = pref.getInteger("RELIC_UNLOCK", 0);
        furthestAscent = pref.getInteger("HIGHEST_FLOOR", 0);
        highestDaily = pref.getInteger("HIGHEST_DAILY", 0);
        totalFloorsClimbed = pref.getInteger("TOTAL_FLOORS", 0);
        numVictory = pref.getInteger("WIN_COUNT", 0);
        numDeath = pref.getInteger("LOSE_COUNT", 0);
        winStreak = pref.getInteger("WIN_STREAK", 0);
        bestWinStreak = pref.getInteger("BEST_WIN_STREAK", 0);
        enemyKilled = pref.getInteger("ENEMY_KILL", 0);
        bossKilled = pref.getInteger("BOSS_KILL", 0);
        playTime = pref.getLong("PLAYTIME", 0L);
        fastestTime = pref.getLong("FAST_VICTORY", 0L);
        highestScore = pref.getInteger("HIGHEST_SCORE", 0);
        info = (new StringBuilder()).append(TEXT[0]).append(formatHMSM(playTime)).append(" NL ").toString();
        new StringBuilder();
        this;
        JVM INSTR dup_x1 ;
        info;
        append();
        TEXT[7];
        append();
        cardsDiscovered;
        append();
        "/";
        append();
        cardsToDiscover;
        append();
        " NL ";
        append();
        toString();
        info;
        new StringBuilder();
        this;
        JVM INSTR dup_x1 ;
        info;
        append();
        TEXT[8];
        append();
        cardsUnlocked;
        append();
        "/";
        append();
        UnlockTracker.lockedRedCardCount;
        append();
        " NL ";
        append();
        toString();
        info;
        if(fastestTime == 0L) goto _L2; else goto _L1
_L1:
        new StringBuilder();
        this;
        JVM INSTR dup_x1 ;
        info;
        append();
        TEXT[13];
        append();
        formatHMSM(fastestTime);
        append();
        " NL ";
        append();
        toString();
        info;
_L2:
        new StringBuilder();
        this;
        JVM INSTR dup_x1 ;
        info;
        append();
        TEXT[23];
        append();
        highestScore;
        append();
        " NL ";
        append();
        toString();
        info;
        if(bestWinStreak <= 0) goto _L4; else goto _L3
_L3:
        new StringBuilder();
        this;
        JVM INSTR dup_x1 ;
        info;
        append();
        TEXT[22];
        append();
        bestWinStreak;
        append();
        " NL ";
        append();
        toString();
        info;
_L4:
        info2 = (new StringBuilder()).append(TEXT[17]).append(numVictory).append(" NL ").toString();
        new StringBuilder();
        this;
        JVM INSTR dup_x1 ;
        info2;
        append();
        TEXT[18];
        append();
        numDeath;
        append();
        " NL ";
        append();
        toString();
        info2;
        new StringBuilder();
        this;
        JVM INSTR dup_x1 ;
        info2;
        append();
        TEXT[19];
        append();
        totalFloorsClimbed;
        append();
        " NL ";
        append();
        toString();
        info2;
        new StringBuilder();
        this;
        JVM INSTR dup_x1 ;
        info2;
        append();
        TEXT[20];
        append();
        bossKilled;
        append();
        " NL ";
        append();
        toString();
        info2;
        new StringBuilder();
        this;
        JVM INSTR dup_x1 ;
        info2;
        append();
        TEXT[21];
        append();
        enemyKilled;
        append();
        " NL ";
        append();
        toString();
        info2;
        StringBuilder sb = new StringBuilder();
        sb.append("runs").append(File.separator);
        if(CardCrawlGame.saveSlot != 0)
            sb.append(CardCrawlGame.saveSlot).append("_");
        sb.append(c.chosenClass.name()).append(File.separator);
        FileHandle files[] = Gdx.files.local(sb.toString()).list();
        FileHandle afilehandle[] = files;
        int i = afilehandle.length;
        for(int j = 0; j < i;)
        {
            FileHandle file = afilehandle[j];
            try
            {
                runs.add(gson.fromJson(file.readString(), com/megacrit/cardcrawl/screens/stats/RunData));
                continue;
            }
            catch(Exception e)
            {
                file.delete();
                logger.warn("Deleted corrupt .run file, preventing crash!", e);
                j++;
            }
        }

        return;
    }

    private int calculateCardsUnlocked(AbstractPlayer c)
    {
        return c.getUnlockedCardCount();
    }

    private int getSeenCardCount(AbstractPlayer c)
    {
        return c.getSeenCardCount();
    }

    private int getCardCountForChar(AbstractPlayer c)
    {
        return c.getCardCount();
    }

    public void highestScore(int score)
    {
        if(score > highestScore)
        {
            highestScore = score;
            pref.putInteger("HIGHEST_SCORE", highestScore);
            pref.flush();
        }
    }

    public void furthestAscent(int floor)
    {
        if(floor > furthestAscent)
        {
            furthestAscent = floor;
            pref.putInteger("HIGHEST_FLOOR", furthestAscent);
            pref.flush();
        }
    }

    public void highestDaily(int score)
    {
        if(score > highestDaily)
        {
            highestDaily = score;
            pref.putInteger("HIGHEST_DAILY", highestDaily);
            pref.flush();
        }
    }

    public void incrementFloorClimbed()
    {
        totalFloorsClimbed++;
        pref.putInteger("TOTAL_FLOORS", totalFloorsClimbed);
        pref.flush();
    }

    public void incrementDeath()
    {
        numDeath++;
        if(!AbstractDungeon.isAscensionMode)
        {
            winStreak = 0;
            pref.putInteger("WIN_STREAK", winStreak);
        }
        pref.putInteger("LOSE_COUNT", numDeath);
        pref.flush();
    }

    public int getVictoryCount()
    {
        return numVictory;
    }

    public int getDeathCount()
    {
        return numDeath;
    }

    public void unlockAscension()
    {
        pref.putInteger("ASCENSION_LEVEL", 1);
        pref.putInteger("LAST_ASCENSION_LEVEL", 1);
    }

    public void incrementAscension()
    {
        if(!Settings.isTrial)
        {
            int derp = pref.getInteger("ASCENSION_LEVEL", 1);
            if(derp == AbstractDungeon.ascensionLevel)
            {
                if(++derp <= 20)
                {
                    pref.putInteger("ASCENSION_LEVEL", derp);
                    pref.putInteger("LAST_ASCENSION_LEVEL", derp);
                    pref.flush();
                    logger.info((new StringBuilder()).append("ASCENSION LEVEL IS NOW: ").append(derp).toString());
                } else
                {
                    pref.putInteger("ASCENSION_LEVEL", 20);
                    pref.putInteger("LAST_ASCENSION_LEVEL", 20);
                    pref.flush();
                    logger.info("MAX ASCENSION");
                }
            } else
            {
                logger.info("Played Ascension that wasn't Max");
            }
        }
    }

    public void incrementVictory()
    {
        numVictory++;
        if(!AbstractDungeon.isAscensionMode)
        {
            winStreak++;
            pref.putInteger("WIN_STREAK", winStreak);
            if(winStreak > pref.getInteger("BEST_WIN_STREAK", 0))
                pref.putInteger("BEST_WIN_STREAK", winStreak);
        }
        pref.putInteger("WIN_COUNT", numVictory);
        pref.flush();
    }

    public void incrementBossSlain()
    {
        bossKilled++;
        pref.putInteger("BOSS_KILL", bossKilled);
        pref.flush();
    }

    public void incrementEnemySlain()
    {
        enemyKilled++;
        pref.putInteger("ENEMY_KILL", enemyKilled);
        pref.flush();
    }

    public void incrementPlayTime(long time)
    {
        playTime += time;
        pref.putLong("PLAYTIME", playTime);
        pref.flush();
    }

    public static String formatHMSM(float t)
    {
        String res = "";
        long duration = (long)t;
        int seconds = (int)(duration % 60L);
        duration /= 60L;
        int minutes = (int)(duration % 60L);
        int hours = (int)t / 3600;
        if(hours > 0)
            res = String.format(TEXT[24], new Object[] {
                Integer.valueOf(hours), Integer.valueOf(minutes), Integer.valueOf(seconds)
            });
        else
            res = String.format(TEXT[25], new Object[] {
                Integer.valueOf(minutes), Integer.valueOf(seconds)
            });
        return res;
    }

    public static String formatHMSM(long t)
    {
        String res = "";
        long duration = t;
        int seconds = (int)(duration % 60L);
        duration /= 60L;
        int minutes = (int)(duration % 60L);
        int hours = (int)t / 3600;
        if(hours > 0)
            res = String.format(TEXT[26], new Object[] {
                Integer.valueOf(hours), Integer.valueOf(minutes), Integer.valueOf(seconds)
            });
        else
            res = String.format(TEXT[27], new Object[] {
                Integer.valueOf(minutes), Integer.valueOf(seconds)
            });
        return res;
    }

    public static String formatHMSM(int t)
    {
        String res = "";
        long duration = t;
        int seconds = (int)(duration % 60L);
        duration /= 60L;
        int minutes = (int)(duration % 60L);
        int hours = t / 3600;
        res = String.format(TEXT[28], new Object[] {
            Integer.valueOf(hours), Integer.valueOf(minutes), Integer.valueOf(seconds)
        });
        return res;
    }

    public void updateFastestVictory(long newTime)
    {
        if(newTime < fastestTime || fastestTime == 0L)
        {
            fastestTime = newTime;
            pref.putLong("FAST_VICTORY", fastestTime);
            pref.flush();
            logger.info((new StringBuilder()).append("Fastest victory time updated to: ").append(fastestTime).toString());
        } else
        {
            logger.info("Did not save fastest victory.");
        }
    }

    public void render(SpriteBatch sb, float screenX, float renderY)
    {
        FontHelper.renderSmartText(sb, FontHelper.panelNameFont, info, screenX + 75F * Settings.scale, renderY + 766F * Settings.yScale, 9999F, 38F * Settings.scale, Settings.CREAM_COLOR);
        if(info2 != null)
            FontHelper.renderSmartText(sb, FontHelper.panelNameFont, info2, screenX + 675F * Settings.scale, renderY + 766F * Settings.yScale, 9999F, 38F * Settings.scale, Settings.CREAM_COLOR);
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/screens/stats/CharStat.getName());
    private static final AchievementStrings achievementStrings;
    public static final String NAMES[];
    public static final String TEXT[];
    private Prefs pref;
    private String info;
    private String info2;
    private static Gson gson = new Gson();
    private int cardsUnlocked;
    private int relicsUnlocked;
    private int cardsDiscovered;
    private int cardsToDiscover;
    public int furthestAscent;
    public int highestScore;
    public int highestDaily;
    private int totalFloorsClimbed;
    private int numVictory;
    private int numDeath;
    public int winStreak;
    public int bestWinStreak;
    public int enemyKilled;
    public int bossKilled;
    public long playTime;
    public long fastestTime;
    private ArrayList runs;
    public static final String CARD_UNLOCK = "CARD_UNLOCK";
    public static final String RELIC_UNLOCK = "RELIC_UNLOCK";
    public static final String HIGHEST_FLOOR = "HIGHEST_FLOOR";
    public static final String HIGHEST_SCORE = "HIGHEST_SCORE";
    public static final String HIGHEST_DAILY = "HIGHEST_DAILY";
    public static final String TOTAL_FLOORS = "TOTAL_FLOORS";
    public static final String TOTAL_CRYSTALS_FED = "TOTAL_CRYSTALS_FED";
    public static final String WIN_COUNT = "WIN_COUNT";
    public static final String LOSE_COUNT = "LOSE_COUNT";
    public static final String WIN_STREAK = "WIN_STREAK";
    public static final String BEST_WIN_STREAK = "BEST_WIN_STREAK";
    public static final String ASCENSION_LEVEL = "ASCENSION_LEVEL";
    public static final String LAST_ASCENSION_LEVEL = "LAST_ASCENSION_LEVEL";
    public static final String ENEMY_KILL = "ENEMY_KILL";
    public static final String BOSS_KILL = "BOSS_KILL";
    public static final String PLAYTIME = "PLAYTIME";
    public static final String FASTEST_VICTORY = "FAST_VICTORY";

    static 
    {
        achievementStrings = CardCrawlGame.languagePack.getAchievementString("CharStat");
        NAMES = achievementStrings.NAMES;
        TEXT = achievementStrings.TEXT;
    }
}
