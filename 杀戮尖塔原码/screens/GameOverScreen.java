// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GameOverScreen.java

package com.megacrit.cardcrawl.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheEnding;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.integrations.PublisherIntegration;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.relics.SpiritPoop;
import com.megacrit.cardcrawl.screens.stats.StatsScreen;
import com.megacrit.cardcrawl.ui.buttons.ReturnToMenuButton;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.UnlockTextEffect;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.screens:
//            GameOverStat

public abstract class GameOverScreen
{

    public GameOverScreen()
    {
        unlockBundle = null;
        stats = new ArrayList();
        fadeBgColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
        whiteUiColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        creamUiColor = Settings.CREAM_COLOR.cpy();
        progressBarX = 576F * Settings.xScale;
        progressBarWidth = 768F * Settings.xScale;
        statsTimer = 0.0F;
        statAnimateTimer = 0.0F;
        progressBarTimer = 2.0F;
        progressBarAlpha = 0.0F;
        maxLevel = false;
        score = 0;
        unlockLevel = 0;
        playedWhir = false;
    }

    public static void resetScoreChecks()
    {
        IS_POOPY = false;
        IS_SPEEDSTER = false;
        IS_LIGHT_SPEED = false;
        IS_HIGHLANDER = false;
        IS_FULL_SET = 0;
        IS_SHINY = false;
        IS_PAUPER = false;
        IS_LIBRARY = false;
        IS_ENCYCLOPEDIA = false;
        IS_WELL_FED = false;
        IS_STUFFED = false;
        IS_CURSES = false;
        IS_ON_MY_OWN = false;
        IS_MONEY_MONEY = false;
        IS_RAINING_MONEY = false;
        IS_I_LIKE_GOLD = false;
        IS_MYSTERY_MACHINE = false;
    }

    protected void animateProgressBar()
    {
        if(maxLevel)
            return;
        progressBarTimer -= Gdx.graphics.getDeltaTime();
        if(progressBarTimer < 0.0F)
            progressBarTimer = 0.0F;
        if(progressBarTimer > 2.0F)
            return;
        if(!playedWhir)
        {
            playedWhir = true;
            whirId = CardCrawlGame.sound.play("UNLOCK_WHIR");
        }
        unlockProgress = Interpolation.pow2In.apply(unlockTargetProgress, unlockTargetStart, progressBarTimer / 2.0F);
        if(unlockProgress >= (float)unlockCost && unlockLevel != 5)
            if(unlockLevel == 4)
            {
                unlockProgress = unlockCost;
                unlockLevel++;
                AbstractDungeon.topLevelEffects.add(new UnlockTextEffect());
            } else
            {
                unlockTargetProgress = (float)score - ((float)unlockCost - unlockTargetStart);
                progressBarTimer = 3F;
                AbstractDungeon.topLevelEffects.add(new UnlockTextEffect());
                CardCrawlGame.sound.stop("UNLOCK_WHIR", whirId);
                playedWhir = false;
                unlockProgress = 0.0F;
                unlockTargetStart = 0.0F;
                if(unlockTargetProgress > (float)(nextUnlockCost - 1))
                    unlockTargetProgress = nextUnlockCost - 1;
                unlockCost = nextUnlockCost;
                unlockLevel++;
            }
        progressPercent = unlockProgress / (float)unlockCost;
    }

    protected void calculateUnlockProgress()
    {
        score = calcScore(isVictory);
        unlockLevel = UnlockTracker.getUnlockLevel(AbstractDungeon.player.chosenClass);
        if(unlockLevel >= 5)
        {
            maxLevel = true;
            return;
        }
        if(score == 0)
            playedWhir = true;
        unlockProgress = UnlockTracker.getCurrentProgress(AbstractDungeon.player.chosenClass);
        unlockTargetStart = unlockProgress;
        unlockCost = UnlockTracker.getCurrentScoreCost(AbstractDungeon.player.chosenClass);
        unlockTargetProgress = unlockProgress + (float)score;
        nextUnlockCost = UnlockTracker.incrementUnlockRamp(unlockCost);
        if(unlockTargetProgress >= (float)unlockCost)
        {
            unlockBundle = UnlockTracker.getUnlockBundle(AbstractDungeon.player.chosenClass, unlockLevel);
            if(unlockLevel == 4)
                unlockTargetProgress = unlockCost;
            else
            if(unlockTargetProgress > (((float)unlockCost - unlockProgress) + (float)nextUnlockCost) - 1.0F)
                unlockTargetProgress = (((float)unlockCost - unlockProgress) + (float)nextUnlockCost) - 1.0F;
        }
        logger.info((new StringBuilder()).append("SCOR: ").append(score).toString());
        logger.info((new StringBuilder()).append("PROG: ").append(unlockProgress).toString());
        logger.info((new StringBuilder()).append("STRT: ").append(unlockTargetStart).toString());
        logger.info((new StringBuilder()).append("TRGT: ").append(unlockTargetProgress).toString());
        logger.info((new StringBuilder()).append("COST: ").append(unlockCost).toString());
        UnlockTracker.addScore(AbstractDungeon.player.chosenClass, score);
        progressPercent = unlockTargetStart / (float)unlockCost;
    }

    public static boolean shouldUploadMetricData()
    {
        return Settings.UPLOAD_DATA && CardCrawlGame.publisherIntegration.isInitialized() && Settings.isStandardRun();
    }

    protected void submitVictoryMetrics()
    {
    }

    protected boolean canUploadLeaderboards()
    {
        return !Settings.isModded && !Settings.isTrial && !Settings.seedSet;
    }

    protected void uploadToSteamLeaderboards()
    {
        if(!canUploadLeaderboards())
        {
            return;
        } else
        {
            uploadScoreHelper(AbstractDungeon.player.getLeaderboardCharacterName());
            StatsScreen.updateHighestScore(score);
            return;
        }
    }

    protected void uploadScoreHelper(String characterString)
    {
        StringBuilder highScoreString = new StringBuilder();
        StringBuilder fastestWinString = new StringBuilder(characterString);
        if(Settings.isDailyRun)
        {
            highScoreString.append((new StringBuilder()).append("DAILY_").append(Long.toString(Settings.dailyDate)).toString());
            long lastDaily = Settings.dailyPref.getLong("LAST_DAILY", 0L);
            Settings.hasDoneDailyToday = lastDaily == Settings.dailyDate;
            if(Settings.hasDoneDailyToday)
                logger.info((new StringBuilder()).append("Player has already done the daily for: ").append(Settings.dailyDate).toString());
        } else
        {
            highScoreString.append(characterString);
            highScoreString.append("_HIGH_SCORE");
        }
        fastestWinString.append("_FASTEST_WIN");
        if(Settings.isBeta)
        {
            highScoreString.append("_BETA");
            fastestWinString.append("_BETA");
        }
        if(Settings.isDailyRun && !Settings.hasDoneDailyToday)
        {
            logger.info((new StringBuilder()).append("Uploading score for day: ").append(Settings.dailyDate).append("\nScore is: ").append(score).toString());
            Settings.dailyPref.putLong("LAST_DAILY", Settings.dailyDate);
            Settings.dailyPref.flush();
            CardCrawlGame.publisherIntegration.uploadDailyLeaderboardScore(highScoreString.toString(), score);
        } else
        if(!Settings.isDailyRun)
            CardCrawlGame.publisherIntegration.uploadLeaderboardScore(highScoreString.toString(), score);
        if(isVictory && playtime < 18000L && playtime > 280L && !Settings.isDailyRun)
            CardCrawlGame.publisherIntegration.uploadLeaderboardScore(fastestWinString.toString(), Math.toIntExact(playtime));
    }

    public static int calcScore(boolean victory)
    {
        floorPoints = 0;
        monsterPoints = 0;
        elite1Points = 0;
        elite2Points = 0;
        elite3Points = 0;
        bossPoints = 0;
        ascensionPoints = 0;
        int tmp = AbstractDungeon.floorNum * 5;
        floorPoints = AbstractDungeon.floorNum * 5;
        monsterPoints = CardCrawlGame.monstersSlain * 2;
        elite1Points = CardCrawlGame.elites1Slain * 10;
        elite2Points = CardCrawlGame.elites2Slain * 20;
        elite3Points = CardCrawlGame.elites3Slain * 30;
        bossPoints = 0;
        int bossMultiplier = 50;
        for(int i = 0; i < AbstractDungeon.bossCount; i++)
        {
            bossPoints += bossMultiplier;
            bossMultiplier += 50;
        }

        tmp += monsterPoints;
        tmp += elite1Points;
        tmp += elite2Points;
        tmp += elite3Points;
        tmp += bossPoints;
        tmp += CardCrawlGame.champion * 25;
        if(CardCrawlGame.perfect >= 3)
            tmp += 200;
        else
            tmp += CardCrawlGame.perfect * 50;
        if(CardCrawlGame.overkill)
            tmp += 25;
        if(CardCrawlGame.combo)
            tmp += 25;
        if(AbstractDungeon.isAscensionMode)
        {
            ascensionPoints = MathUtils.round((float)tmp * (0.05F * (float)AbstractDungeon.ascensionLevel));
            tmp += ascensionPoints;
        }
        tmp += checkScoreBonus(victory);
        return tmp;
    }

    protected static int checkScoreBonus(boolean victory)
    {
        int points = 0;
        if(AbstractDungeon.player.hasRelic("Spirit Poop"))
        {
            IS_POOPY = true;
            points--;
        }
        IS_FULL_SET = AbstractDungeon.player.masterDeck.fullSetCheck();
        if(IS_FULL_SET > 0)
            points += 25 * IS_FULL_SET;
        if(AbstractDungeon.player.relics.size() >= 25)
        {
            IS_SHINY = true;
            points += 50;
        }
        if(AbstractDungeon.player.masterDeck.size() >= 50)
        {
            IS_ENCYCLOPEDIA = true;
            points += 50;
        } else
        if(AbstractDungeon.player.masterDeck.size() >= 35)
        {
            IS_LIBRARY = true;
            points += 25;
        }
        int tmpDiff = AbstractDungeon.player.maxHealth - AbstractDungeon.player.startingMaxHP;
        if(tmpDiff >= 30)
        {
            IS_STUFFED = true;
            points += 50;
        } else
        if(tmpDiff >= 15)
        {
            IS_WELL_FED = true;
            points += 25;
        }
        if(AbstractDungeon.player.masterDeck.cursedCheck())
        {
            IS_CURSES = true;
            points += 100;
        }
        if(CardCrawlGame.goldGained >= 3000)
        {
            IS_I_LIKE_GOLD = true;
            points += 75;
        } else
        if(CardCrawlGame.goldGained >= 2000)
        {
            IS_RAINING_MONEY = true;
            points += 50;
        } else
        if(CardCrawlGame.goldGained >= 1000)
        {
            IS_MONEY_MONEY = true;
            points += 25;
        }
        if(CardCrawlGame.mysteryMachine >= 15)
        {
            IS_MYSTERY_MACHINE = true;
            points += 25;
        }
        if(victory)
        {
            logger.info((new StringBuilder()).append("PLAYTIME: ").append(CardCrawlGame.playtime).toString());
            if((long)CardCrawlGame.playtime <= 2700L)
            {
                IS_LIGHT_SPEED = true;
                points += 50;
            } else
            if((long)CardCrawlGame.playtime <= 3600L)
            {
                IS_SPEEDSTER = true;
                points += 25;
            }
            if(AbstractDungeon.player.masterDeck.highlanderCheck())
            {
                IS_HIGHLANDER = true;
                points += 100;
            }
            if(AbstractDungeon.player.masterDeck.pauperCheck())
            {
                IS_PAUPER = true;
                points += 50;
            }
            if(isVictory && (CardCrawlGame.dungeon instanceof TheEnding))
                points += 250;
        }
        return points;
    }

    protected void renderStatsScreen(SpriteBatch sb)
    {
        if(showingStats)
        {
            fadeBgColor.a = (1.0F - statsTimer) * 0.6F;
            sb.setColor(fadeBgColor);
            sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
            float y = STAT_START_Y + ((float)stats.size() * STAT_OFFSET_Y) / 2.0F;
            if(stats.size() >= 10)
                y = STAT_START_Y + ((float)(stats.size() / 2) * STAT_OFFSET_Y) / 2.0F;
            for(int i = 0; i < stats.size(); i++)
            {
                if(stats.size() <= 10)
                {
                    if(i == stats.size() - 2)
                        ((GameOverStat)stats.get(i)).renderLine(sb, false, y);
                    else
                        ((GameOverStat)stats.get(i)).render(sb, (float)Settings.WIDTH / 2.0F - 220F * Settings.scale, y);
                } else
                if(i != stats.size() - 1)
                {
                    if(i < (stats.size() - 1) / 2)
                        ((GameOverStat)stats.get(i)).render(sb, 440F * Settings.xScale, y);
                    else
                        ((GameOverStat)stats.get(i)).render(sb, 1050F * Settings.xScale, y + STAT_OFFSET_Y * (float)((stats.size() - 1) / 2));
                } else
                {
                    ((GameOverStat)stats.get(i)).renderLine(sb, true, y + STAT_OFFSET_Y * (float)(stats.size() / 2));
                    ((GameOverStat)stats.get(i)).render(sb, 740F * Settings.xScale, y + STAT_OFFSET_Y * (float)(stats.size() / 2 - 1));
                }
                y -= STAT_OFFSET_Y;
            }

            renderProgressBar(sb);
        }
    }

    protected void renderProgressBar(SpriteBatch sb)
    {
        if(maxLevel)
            return;
        whiteUiColor.a = progressBarAlpha * 0.3F;
        sb.setColor(whiteUiColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, progressBarX, (float)Settings.HEIGHT * 0.2F, progressBarWidth, 14F * Settings.scale);
        sb.setColor(new Color(1.0F, 0.8F, 0.3F, progressBarAlpha * 0.9F));
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, progressBarX, (float)Settings.HEIGHT * 0.2F, progressBarWidth * progressPercent, 14F * Settings.scale);
        sb.setColor(new Color(0.0F, 0.0F, 0.0F, progressBarAlpha * 0.25F));
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, progressBarX, (float)Settings.HEIGHT * 0.2F, progressBarWidth * progressPercent, 4F * Settings.scale);
        String derp = (new StringBuilder()).append("[").append((int)unlockProgress).append("/").append(unlockCost).append("]").toString();
        creamUiColor.a = progressBarAlpha * 0.9F;
        FontHelper.renderFontLeftTopAligned(sb, FontHelper.topPanelInfoFont, derp, 576F * Settings.xScale, (float)Settings.HEIGHT * 0.2F - 12F * Settings.scale, creamUiColor);
        if(5 - unlockLevel == 1)
            derp = (new StringBuilder()).append(TEXT[42]).append(5 - unlockLevel).toString();
        else
            derp = (new StringBuilder()).append(TEXT[41]).append(5 - unlockLevel).toString();
        FontHelper.renderFontRightTopAligned(sb, FontHelper.topPanelInfoFont, derp, 1344F * Settings.xScale, (float)Settings.HEIGHT * 0.2F - 12F * Settings.scale, creamUiColor);
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/screens/GameOverScreen.getName());
    private static final UIStrings uiStrings;
    private static final String TEXT[];
    protected ReturnToMenuButton returnButton;
    public static boolean isVictory;
    protected ArrayList unlockBundle;
    protected ArrayList stats;
    protected Color fadeBgColor;
    protected Color whiteUiColor;
    protected Color creamUiColor;
    private float progressBarX;
    private float progressBarWidth;
    protected boolean showingStats;
    protected float statsTimer;
    protected float statAnimateTimer;
    protected float progressBarTimer;
    protected float progressBarAlpha;
    protected static final float STAT_OFFSET_Y;
    protected static final float STAT_START_Y;
    protected boolean maxLevel;
    protected float progressPercent;
    protected float unlockTargetProgress;
    protected float unlockTargetStart;
    protected float unlockProgress;
    protected long playtime;
    protected int score;
    protected int unlockCost;
    protected int nextUnlockCost;
    protected int unlockLevel;
    protected static final float STATS_TRANSITION_TIME = 0.5F;
    protected static final float STAT_ANIM_INTERVAL = 0.1F;
    protected static final float PROGRESS_BAR_ANIM_TIME = 2F;
    protected static final ScoreBonusStrings EXORDIUM_ELITE;
    protected static final ScoreBonusStrings CITY_ELITE;
    protected static final ScoreBonusStrings BEYOND_ELITE;
    protected static final ScoreBonusStrings BOSSES_SLAIN;
    protected static final ScoreBonusStrings ASCENSION;
    protected static final ScoreBonusStrings CHAMPION;
    protected static final ScoreBonusStrings PERFECT;
    protected static final ScoreBonusStrings BEYOND_PERFECT;
    protected static final ScoreBonusStrings OVERKILL;
    protected static final ScoreBonusStrings COMBO;
    protected static final ScoreBonusStrings POOPY;
    protected static final ScoreBonusStrings SPEEDSTER;
    protected static final ScoreBonusStrings LIGHT_SPEED;
    protected static final ScoreBonusStrings MONEY_MONEY;
    protected static final ScoreBonusStrings RAINING_MONEY;
    protected static final ScoreBonusStrings I_LIKE_GOLD;
    protected static final ScoreBonusStrings HIGHLANDER;
    protected static final ScoreBonusStrings SHINY;
    protected static final ScoreBonusStrings COLLECTOR;
    protected static final ScoreBonusStrings PAUPER;
    protected static final ScoreBonusStrings LIBRARIAN;
    protected static final ScoreBonusStrings ENCYCLOPEDIAN;
    protected static final ScoreBonusStrings WELL_FED;
    protected static final ScoreBonusStrings STUFFED;
    protected static final ScoreBonusStrings CURSES;
    protected static final ScoreBonusStrings MYSTERY_MACHINE;
    protected static final ScoreBonusStrings ON_MY_OWN_TERMS;
    protected static final ScoreBonusStrings HEARTBREAKER;
    protected static boolean IS_POOPY = false;
    protected static boolean IS_SPEEDSTER = false;
    protected static boolean IS_LIGHT_SPEED = false;
    protected static boolean IS_HIGHLANDER = false;
    protected static int IS_FULL_SET = 0;
    protected static boolean IS_SHINY = false;
    protected static boolean IS_PAUPER = false;
    protected static boolean IS_LIBRARY = false;
    protected static boolean IS_ENCYCLOPEDIA = false;
    protected static boolean IS_WELL_FED = false;
    protected static boolean IS_STUFFED = false;
    protected static boolean IS_CURSES = false;
    protected static boolean IS_ON_MY_OWN = false;
    protected static boolean IS_MONEY_MONEY = false;
    protected static boolean IS_RAINING_MONEY = false;
    protected static boolean IS_I_LIKE_GOLD = false;
    protected static boolean IS_MYSTERY_MACHINE = false;
    protected static final int POOPY_SCORE = -1;
    protected static final int SPEEDER_SCORE = 25;
    protected static final int LIGHT_SPEED_SCORE = 50;
    protected static final int HIGHLANDER_SCORE = 100;
    protected static final int FULL_SET_SCORE = 25;
    protected static final int SHINY_SCORE = 50;
    protected static final int PAUPER_SCORE = 50;
    protected static final int LIBRARY_SCORE = 25;
    protected static final int ENCYCLOPEDIA_SCORE = 50;
    protected static final int WELL_FED_SCORE = 25;
    protected static final int STUFFED_SCORE = 50;
    protected static final int CURSES_SCORE = 100;
    protected static final int ON_MY_OWN_SCORE = 50;
    protected static final int MONEY_MONEY_SCORE = 25;
    protected static final int RAINING_MONEY_SCORE = 50;
    protected static final int I_LIKE_GOLD_SCORE = 75;
    protected static final int CHAMPION_SCORE = 25;
    protected static final int PERFECT_SCORE = 50;
    protected static final int BEYOND_PERFECT_SCORE = 200;
    protected static final int OVERKILL_SCORE = 25;
    protected static final int COMBO_SCORE = 25;
    protected static final int MYSTERY_MACHINE_SCORE = 25;
    protected static final int HEARTBREAKER_SCORE = 250;
    protected static int floorPoints;
    protected static int monsterPoints;
    protected static int elite1Points;
    protected static int elite2Points;
    protected static int elite3Points;
    protected static int bossPoints;
    protected static int ascensionPoints;
    protected static final int FLOOR_MULTIPLIER = 5;
    protected static final int ENEMY_MULTIPLIER = 2;
    protected static final int ELITE_MULTIPLIER_1 = 10;
    protected static final int ELITE_MULTIPLIER_2 = 20;
    protected static final int ELITE_MULTIPLIER_3 = 30;
    protected static final int BOSS_MULTIPLIER = 50;
    protected static final float ASCENSION_MULTIPLIER = 0.05F;
    protected boolean playedWhir;
    protected long whirId;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("DeathScreen");
        TEXT = uiStrings.TEXT;
        STAT_OFFSET_Y = 36F * Settings.scale;
        STAT_START_Y = (float)Settings.HEIGHT / 2.0F - 20F * Settings.scale;
        EXORDIUM_ELITE = CardCrawlGame.languagePack.getScoreString("Exordium Elites Killed");
        CITY_ELITE = CardCrawlGame.languagePack.getScoreString("City Elites Killed");
        BEYOND_ELITE = CardCrawlGame.languagePack.getScoreString("Beyond Elites Killed");
        BOSSES_SLAIN = CardCrawlGame.languagePack.getScoreString("Bosses Slain");
        ASCENSION = CardCrawlGame.languagePack.getScoreString("Ascension");
        CHAMPION = CardCrawlGame.languagePack.getScoreString("Champion");
        PERFECT = CardCrawlGame.languagePack.getScoreString("Perfect");
        BEYOND_PERFECT = CardCrawlGame.languagePack.getScoreString("Beyond Perfect");
        OVERKILL = CardCrawlGame.languagePack.getScoreString("Overkill");
        COMBO = CardCrawlGame.languagePack.getScoreString("Combo");
        POOPY = CardCrawlGame.languagePack.getScoreString("Poopy");
        SPEEDSTER = CardCrawlGame.languagePack.getScoreString("Speedster");
        LIGHT_SPEED = CardCrawlGame.languagePack.getScoreString("Light Speed");
        MONEY_MONEY = CardCrawlGame.languagePack.getScoreString("Money Money");
        RAINING_MONEY = CardCrawlGame.languagePack.getScoreString("Raining Money");
        I_LIKE_GOLD = CardCrawlGame.languagePack.getScoreString("I Like Gold");
        HIGHLANDER = CardCrawlGame.languagePack.getScoreString("Highlander");
        SHINY = CardCrawlGame.languagePack.getScoreString("Shiny");
        COLLECTOR = CardCrawlGame.languagePack.getScoreString("Collector");
        PAUPER = CardCrawlGame.languagePack.getScoreString("Pauper");
        LIBRARIAN = CardCrawlGame.languagePack.getScoreString("Librarian");
        ENCYCLOPEDIAN = CardCrawlGame.languagePack.getScoreString("Encyclopedian");
        WELL_FED = CardCrawlGame.languagePack.getScoreString("Well Fed");
        STUFFED = CardCrawlGame.languagePack.getScoreString("Stuffed");
        CURSES = CardCrawlGame.languagePack.getScoreString("Curses");
        MYSTERY_MACHINE = CardCrawlGame.languagePack.getScoreString("Mystery Machine");
        ON_MY_OWN_TERMS = CardCrawlGame.languagePack.getScoreString("On My Own Terms");
        HEARTBREAKER = CardCrawlGame.languagePack.getScoreString("Heartbreaker");
    }
}
