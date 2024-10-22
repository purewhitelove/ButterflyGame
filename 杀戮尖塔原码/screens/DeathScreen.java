// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DeathScreen.java

package com.megacrit.cardcrawl.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.integrations.PublisherIntegration;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.metrics.MetricData;
import com.megacrit.cardcrawl.metrics.Metrics;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.neow.NeowUnlockScreen;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.screens.stats.AchievementGrid;
import com.megacrit.cardcrawl.screens.stats.StatsScreen;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.ui.buttons.*;
import com.megacrit.cardcrawl.ui.panels.PotionPopUp;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import com.megacrit.cardcrawl.unlock.*;
import com.megacrit.cardcrawl.unlock.misc.*;
import com.megacrit.cardcrawl.vfx.*;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.screens:
//            GameOverScreen, GameOverStat, DungeonMapScreen

public class DeathScreen extends GameOverScreen
{

    public DeathScreen(MonsterGroup m)
    {
        particles = new ArrayList();
        deathAnimWaitTimer = 1.0F;
        deathTextTimer = 5F;
        defeatTextColor = Color.WHITE.cpy();
        deathTextColor = Settings.BLUE_TEXT_COLOR.cpy();
        defectUnlockedThisRun = false;
        playtime = (long)CardCrawlGame.playtime;
        if(playtime < 0L)
            playtime = 0L;
        AbstractDungeon.getCurrRoom().clearEvent();
        resetScoreChecks();
        AbstractDungeon.is_victory = false;
        AbstractCard c;
        for(Iterator iterator = AbstractDungeon.player.hand.group.iterator(); iterator.hasNext(); c.unhover())
            c = (AbstractCard)iterator.next();

        if(AbstractDungeon.player.stance != null)
            AbstractDungeon.player.stance.stopIdleSfx();
        AbstractDungeon.dungeonMapScreen.closeInstantly();
        AbstractDungeon.screen = com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.DEATH;
        AbstractDungeon.overlayMenu.showBlackScreen(1.0F);
        AbstractDungeon.previousScreen = null;
        AbstractDungeon.overlayMenu.cancelButton.hideInstantly();
        AbstractDungeon.isScreenUp = true;
        deathText = getDeathText();
        monsters = m;
        logger.info((new StringBuilder()).append("PLAYTIME: ").append(playtime).toString());
        if(AbstractDungeon.getCurrRoom() instanceof RestRoom)
            ((RestRoom)AbstractDungeon.getCurrRoom()).cutFireSound();
        isVictory = AbstractDungeon.getCurrRoom() instanceof VictoryRoom;
        if(!isVictory)
        {
            PublisherIntegration pubInteg = CardCrawlGame.publisherIntegration;
            String winStreakStatId;
            if(Settings.isBeta)
                winStreakStatId = (new StringBuilder()).append(AbstractDungeon.player.getWinStreakKey()).append("_BETA").toString();
            else
                winStreakStatId = AbstractDungeon.player.getWinStreakKey();
            pubInteg.setStat(winStreakStatId, 0);
            logger.info((new StringBuilder()).append("WIN STREAK  ").append(pubInteg.getStat(winStreakStatId)).toString());
        }
        showingStats = false;
        returnButton = new ReturnToMenuButton();
        returnButton.appear((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT * 0.15F, TEXT[0]);
        if(AbstractDungeon.getCurrRoom() instanceof VictoryRoom)
            AbstractDungeon.dynamicBanner.appear(TEXT[1]);
        else
            AbstractDungeon.dynamicBanner.appear(getDeathBannerText());
        if(Settings.isStandardRun())
            if(AbstractDungeon.floorNum >= 16)
            {
                logger.info("Neow available");
                CardCrawlGame.playerPref.putInteger((new StringBuilder()).append(AbstractDungeon.player.chosenClass.name()).append("_SPIRITS").toString(), 1);
            } else
            {
                logger.info("No Neow for you");
                CardCrawlGame.playerPref.putInteger((new StringBuilder()).append(AbstractDungeon.player.chosenClass.name()).append("_SPIRITS").toString(), 0);
                AbstractDungeon.bossCount = 0;
            }
        CardCrawlGame.music.dispose();
        CardCrawlGame.sound.play("DEATH_STINGER", true);
        String bgmKey = null;
        switch(MathUtils.random(0, 3))
        {
        case 0: // '\0'
            bgmKey = "STS_DeathStinger_1_v3_MUSIC.ogg";
            break;

        case 1: // '\001'
            bgmKey = "STS_DeathStinger_2_v3_MUSIC.ogg";
            break;

        case 2: // '\002'
            bgmKey = "STS_DeathStinger_3_v3_MUSIC.ogg";
            break;

        case 3: // '\003'
            bgmKey = "STS_DeathStinger_4_v3_MUSIC.ogg";
            break;
        }
        CardCrawlGame.music.playTempBgmInstantly(bgmKey, false);
        if(isVictory)
        {
            UnlockTracker.unlockAchievement(AbstractDungeon.player.getAchievementKey());
            submitVictoryMetrics();
            if(playtime != 0L)
                StatsScreen.updateVictoryTime(playtime);
            StatsScreen.incrementVictory(AbstractDungeon.player.getCharStat());
            if(AbstractDungeon.ascensionLevel == 10 && !Settings.isTrial)
                UnlockTracker.unlockAchievement("ASCEND_10");
            else
            if(AbstractDungeon.ascensionLevel == 20 && !Settings.isTrial)
                UnlockTracker.unlockAchievement("ASCEND_20");
        } else
        {
            if(AbstractDungeon.ascensionLevel == 20 && AbstractDungeon.actNum == 4)
                UnlockTracker.unlockAchievement("ASCEND_20");
            submitDefeatMetrics(m);
            StatsScreen.incrementDeath(AbstractDungeon.player.getCharStat());
        }
        if(Settings.isStandardRun() && AbstractDungeon.actNum > 3)
            StatsScreen.incrementVictoryIfZero(AbstractDungeon.player.getCharStat());
        defeatTextColor.a = 0.0F;
        deathTextColor.a = 0.0F;
        if(playtime != 0L)
            StatsScreen.incrementPlayTime(playtime);
        if(Settings.isStandardRun())
            StatsScreen.updateFurthestAscent(AbstractDungeon.floorNum);
        else
        if(Settings.isDailyRun)
            StatsScreen.updateHighestDailyScore(AbstractDungeon.floorNum);
        if(SaveHelper.shouldDeleteSave())
            SaveAndContinue.deleteSave(AbstractDungeon.player);
        calculateUnlockProgress();
        if(!Settings.isEndless)
            uploadToSteamLeaderboards();
        createGameOverStats();
        CardCrawlGame.playerPref.flush();
    }

    private void createGameOverStats()
    {
        stats.clear();
        stats.add(new GameOverStat((new StringBuilder()).append(TEXT[2]).append(" (").append(AbstractDungeon.floorNum).append(")").toString(), null, Integer.toString(floorPoints)));
        stats.add(new GameOverStat((new StringBuilder()).append(TEXT[43]).append(" (").append(CardCrawlGame.monstersSlain).append(")").toString(), null, Integer.toString(monsterPoints)));
        stats.add(new GameOverStat((new StringBuilder()).append(EXORDIUM_ELITE.NAME).append(" (").append(CardCrawlGame.elites1Slain).append(")").toString(), null, Integer.toString(elite1Points)));
        if(Settings.isEndless)
        {
            if(CardCrawlGame.elites2Slain > 0)
                stats.add(new GameOverStat((new StringBuilder()).append(CITY_ELITE.NAME).append(" (").append(CardCrawlGame.elites2Slain).append(")").toString(), null, Integer.toString(elite2Points)));
        } else
        if((CardCrawlGame.dungeon instanceof TheCity) || (CardCrawlGame.dungeon instanceof TheBeyond) || (CardCrawlGame.dungeon instanceof TheEnding))
            stats.add(new GameOverStat((new StringBuilder()).append(CITY_ELITE.NAME).append(" (").append(CardCrawlGame.elites2Slain).append(")").toString(), null, Integer.toString(elite2Points)));
        if(Settings.isEndless)
        {
            if(CardCrawlGame.elites3Slain > 0)
                stats.add(new GameOverStat((new StringBuilder()).append(BEYOND_ELITE.NAME).append(" (").append(CardCrawlGame.elites3Slain).append(")").toString(), null, Integer.toString(elite3Points)));
        } else
        if((CardCrawlGame.dungeon instanceof TheBeyond) || (CardCrawlGame.dungeon instanceof TheEnding))
            stats.add(new GameOverStat((new StringBuilder()).append(BEYOND_ELITE.NAME).append(" (").append(CardCrawlGame.elites3Slain).append(")").toString(), null, Integer.toString(elite3Points)));
        stats.add(new GameOverStat((new StringBuilder()).append(BOSSES_SLAIN.NAME).append(" (").append(AbstractDungeon.bossCount).append(")").toString(), null, Integer.toString(bossPoints)));
        if(IS_POOPY)
            stats.add(new GameOverStat(POOPY.NAME, POOPY.DESCRIPTIONS[0], Integer.toString(-1)));
        if(IS_SPEEDSTER)
            stats.add(new GameOverStat(SPEEDSTER.NAME, SPEEDSTER.DESCRIPTIONS[0], Integer.toString(25)));
        if(IS_LIGHT_SPEED)
            stats.add(new GameOverStat(LIGHT_SPEED.NAME, LIGHT_SPEED.DESCRIPTIONS[0], Integer.toString(50)));
        if(IS_HIGHLANDER)
            stats.add(new GameOverStat(HIGHLANDER.NAME, HIGHLANDER.DESCRIPTIONS[0], Integer.toString(100)));
        if(IS_SHINY)
            stats.add(new GameOverStat(SHINY.NAME, SHINY.DESCRIPTIONS[0], Integer.toString(50)));
        if(IS_I_LIKE_GOLD)
            stats.add(new GameOverStat((new StringBuilder()).append(I_LIKE_GOLD.NAME).append(" (").append(CardCrawlGame.goldGained).append(")").toString(), I_LIKE_GOLD.DESCRIPTIONS[0], Integer.toString(75)));
        else
        if(IS_RAINING_MONEY)
            stats.add(new GameOverStat((new StringBuilder()).append(RAINING_MONEY.NAME).append(" (").append(CardCrawlGame.goldGained).append(")").toString(), RAINING_MONEY.DESCRIPTIONS[0], Integer.toString(50)));
        else
        if(IS_MONEY_MONEY)
            stats.add(new GameOverStat((new StringBuilder()).append(MONEY_MONEY.NAME).append(" (").append(CardCrawlGame.goldGained).append(")").toString(), MONEY_MONEY.DESCRIPTIONS[0], Integer.toString(25)));
        if(IS_MYSTERY_MACHINE)
            stats.add(new GameOverStat((new StringBuilder()).append(MYSTERY_MACHINE.NAME).append(" (").append(CardCrawlGame.mysteryMachine).append(")").toString(), MYSTERY_MACHINE.DESCRIPTIONS[0], Integer.toString(25)));
        if(IS_FULL_SET > 0)
            stats.add(new GameOverStat((new StringBuilder()).append(COLLECTOR.NAME).append(" (").append(IS_FULL_SET).append(")").toString(), COLLECTOR.DESCRIPTIONS[0], Integer.toString(25 * IS_FULL_SET)));
        if(IS_PAUPER)
            stats.add(new GameOverStat(PAUPER.NAME, PAUPER.DESCRIPTIONS[0], Integer.toString(50)));
        if(IS_LIBRARY)
            stats.add(new GameOverStat(LIBRARIAN.NAME, LIBRARIAN.DESCRIPTIONS[0], Integer.toString(25)));
        if(IS_ENCYCLOPEDIA)
            stats.add(new GameOverStat(ENCYCLOPEDIAN.NAME, ENCYCLOPEDIAN.DESCRIPTIONS[0], Integer.toString(50)));
        if(IS_STUFFED)
            stats.add(new GameOverStat(STUFFED.NAME, STUFFED.DESCRIPTIONS[0], Integer.toString(50)));
        else
        if(IS_WELL_FED)
            stats.add(new GameOverStat(WELL_FED.NAME, WELL_FED.DESCRIPTIONS[0], Integer.toString(25)));
        if(IS_CURSES)
            stats.add(new GameOverStat(CURSES.NAME, CURSES.DESCRIPTIONS[0], Integer.toString(100)));
        if(IS_ON_MY_OWN)
            stats.add(new GameOverStat(ON_MY_OWN_TERMS.NAME, ON_MY_OWN_TERMS.DESCRIPTIONS[0], Integer.toString(50)));
        if(CardCrawlGame.champion > 0)
            stats.add(new GameOverStat((new StringBuilder()).append(CHAMPION.NAME).append(" (").append(CardCrawlGame.champion).append(")").toString(), CHAMPION.DESCRIPTIONS[0], Integer.toString(25 * CardCrawlGame.champion)));
        if(CardCrawlGame.perfect >= 3)
            stats.add(new GameOverStat(BEYOND_PERFECT.NAME, BEYOND_PERFECT.DESCRIPTIONS[0], Integer.toString(200)));
        else
        if(CardCrawlGame.perfect > 0)
            stats.add(new GameOverStat((new StringBuilder()).append(PERFECT.NAME).append(" (").append(CardCrawlGame.perfect).append(")").toString(), PERFECT.DESCRIPTIONS[0], Integer.toString(50 * CardCrawlGame.perfect)));
        if(CardCrawlGame.overkill)
            stats.add(new GameOverStat(OVERKILL.NAME, OVERKILL.DESCRIPTIONS[0], Integer.toString(25)));
        if(CardCrawlGame.combo)
            stats.add(new GameOverStat(COMBO.NAME, COMBO.DESCRIPTIONS[0], Integer.toString(25)));
        if(AbstractDungeon.isAscensionMode)
            stats.add(new GameOverStat((new StringBuilder()).append(ASCENSION.NAME).append(" (").append(AbstractDungeon.ascensionLevel).append(")").toString(), ASCENSION.DESCRIPTIONS[0], Integer.toString(ascensionPoints)));
        stats.add(new GameOverStat());
        stats.add(new GameOverStat(TEXT[6], null, Integer.toString(score)));
    }

    private void submitDefeatMetrics(MonsterGroup m)
    {
        if(m != null && !m.areMonstersDead() && !m.areMonstersBasicallyDead())
            CardCrawlGame.metricData.addEncounterData();
        Metrics metrics = new Metrics();
        metrics.gatherAllDataAndSave(true, false, m);
        if(shouldUploadMetricData())
        {
            metrics.setValues(true, false, m, com.megacrit.cardcrawl.metrics.Metrics.MetricRequestType.UPLOAD_METRICS);
            Thread t = new Thread(metrics);
            t.setName("Metrics");
            t.start();
        }
    }

    protected void submitVictoryMetrics()
    {
        Metrics metrics = new Metrics();
        metrics.gatherAllDataAndSave(false, false, null);
        if(shouldUploadMetricData())
        {
            metrics.setValues(false, false, null, com.megacrit.cardcrawl.metrics.Metrics.MetricRequestType.UPLOAD_METRICS);
            Thread t = new Thread(metrics);
            t.start();
        }
        if(Settings.isStandardRun())
            StatsScreen.updateFurthestAscent(AbstractDungeon.floorNum);
        if(SaveHelper.shouldDeleteSave())
            SaveAndContinue.deleteSave(AbstractDungeon.player);
    }

    private String getDeathBannerText()
    {
        ArrayList list = new ArrayList();
        list.add(TEXT[7]);
        list.add(TEXT[8]);
        list.add(TEXT[9]);
        list.add(TEXT[10]);
        list.add(TEXT[11]);
        list.add(TEXT[12]);
        list.add(TEXT[13]);
        list.add(TEXT[14]);
        return (String)list.get(MathUtils.random(list.size() - 1));
    }

    private String getDeathText()
    {
        ArrayList list = new ArrayList();
        list.add(TEXT[15]);
        list.add(TEXT[16]);
        list.add(TEXT[17]);
        list.add(TEXT[18]);
        list.add(TEXT[19]);
        list.add(TEXT[20]);
        list.add(TEXT[21]);
        list.add(TEXT[22]);
        list.add(TEXT[23]);
        list.add(TEXT[24]);
        list.add(TEXT[25]);
        list.add(TEXT[26]);
        list.add(TEXT[27]);
        list.add(TEXT[28]);
        list.add(TEXT[29]);
        if(AbstractDungeon.player.chosenClass == com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.THE_SILENT)
            list.add("...");
        return (String)list.get(MathUtils.random(list.size() - 1));
    }

    public void hide()
    {
        returnButton.hide();
        AbstractDungeon.dynamicBanner.hide();
    }

    public void reopen()
    {
        reopen(false);
    }

    public void reopen(boolean fromVictoryUnlock)
    {
        AbstractDungeon.previousScreen = com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.DEATH;
        statsTimer = 0.5F;
        if(isVictory)
            AbstractDungeon.dynamicBanner.appearInstantly(TEXT[1]);
        else
            AbstractDungeon.dynamicBanner.appearInstantly(TEXT[30]);
        AbstractDungeon.overlayMenu.showBlackScreen(1.0F);
        if(fromVictoryUnlock)
            returnButton.appear((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT * 0.15F, TEXT[31]);
        else
        if(!showingStats)
            returnButton.appear((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT * 0.15F, TEXT[32]);
        else
        if(unlockBundle == null)
        {
            if(!isVictory)
            {
                if(UnlockTracker.isCharacterLocked("The Silent") || UnlockTracker.isCharacterLocked("Defect") && AbstractDungeon.player.chosenClass == com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.THE_SILENT || willWatcherUnlock())
                    returnButton.appear((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT * 0.15F, TEXT[40]);
                else
                    returnButton.appear((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT * 0.15F, TEXT[34]);
            } else
            {
                returnButton.appear((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT * 0.15F, TEXT[35]);
            }
        } else
        {
            returnButton.appear((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT * 0.15F, TEXT[36]);
        }
    }

    private boolean willWatcherUnlock()
    {
        if(defectUnlockedThisRun || !UnlockTracker.isCharacterLocked("Watcher"))
            return false;
        else
            return !UnlockTracker.isCharacterLocked("Defect") && (UnlockTracker.isAchievementUnlocked("RUBY") || UnlockTracker.isAchievementUnlocked("EMERALD") || UnlockTracker.isAchievementUnlocked("SAPPHIRE"));
    }

    public void update()
    {
        if(Settings.isDebug && InputHelper.justClickedRight)
            UnlockTracker.resetUnlockProgress(AbstractDungeon.player.chosenClass);
        updateControllerInput();
        returnButton.update();
        if(returnButton.hb.clicked || returnButton.show && CInputActionSet.select.isJustPressed())
        {
            CInputActionSet.topPanel.unpress();
            if(Settings.isControllerMode)
                Gdx.input.setCursorPosition(10, Settings.HEIGHT / 2);
            returnButton.hb.clicked = false;
            if(!showingStats)
            {
                showingStats = true;
                statsTimer = 0.5F;
                logger.info("Clicked");
                returnButton = new ReturnToMenuButton();
                updateAscensionProgress();
                if(unlockBundle == null)
                {
                    if(!isVictory)
                    {
                        if(UnlockTracker.isCharacterLocked("The Silent") || UnlockTracker.isCharacterLocked("Defect") && AbstractDungeon.player.chosenClass == com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.THE_SILENT || willWatcherUnlock())
                            returnButton.appear((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT * 0.15F, TEXT[40]);
                        else
                            returnButton.appear((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT * 0.15F, TEXT[37]);
                    } else
                    {
                        returnButton.appear((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT * 0.15F, TEXT[39]);
                    }
                } else
                {
                    returnButton.appear((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT * 0.15F, TEXT[40]);
                }
            } else
            if(unlockBundle != null)
            {
                AbstractDungeon.gUnlockScreen.open(unlockBundle, false);
                AbstractDungeon.previousScreen = com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.DEATH;
                AbstractDungeon.screen = com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.NEOW_UNLOCK;
                unlockBundle = null;
                if(UnlockTracker.isCharacterLocked("The Silent"))
                    returnButton.appear((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT * 0.15F, TEXT[40]);
                else
                    returnButton.label = TEXT[37];
            } else
            if(isVictory)
            {
                returnButton.hide();
                if(AbstractDungeon.unlocks.isEmpty() || Settings.isDemo)
                {
                    if(Settings.isDemo || Settings.isDailyRun)
                        CardCrawlGame.startOver();
                    else
                    if(UnlockTracker.isCharacterLocked("The Silent"))
                    {
                        AbstractDungeon.unlocks.add(new TheSilentUnlock());
                        AbstractDungeon.unlockScreen.open((AbstractUnlock)AbstractDungeon.unlocks.remove(0));
                    } else
                    if(UnlockTracker.isCharacterLocked("Defect") && AbstractDungeon.player.chosenClass == com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.THE_SILENT)
                    {
                        AbstractDungeon.unlocks.add(new DefectUnlock());
                        defectUnlockedThisRun = true;
                        AbstractDungeon.unlockScreen.open((AbstractUnlock)AbstractDungeon.unlocks.remove(0));
                    } else
                    if(willWatcherUnlock())
                    {
                        AbstractDungeon.unlocks.add(new WatcherUnlock());
                        AbstractDungeon.unlockScreen.open((AbstractUnlock)AbstractDungeon.unlocks.remove(0));
                    } else
                    {
                        CardCrawlGame.playCreditsBgm = false;
                        CardCrawlGame.startOverButShowCredits();
                    }
                } else
                {
                    AbstractDungeon.unlocks.clear();
                    Settings.isTrial = false;
                    Settings.isDailyRun = false;
                    Settings.isEndless = false;
                    CardCrawlGame.trial = null;
                    if(Settings.isDailyRun)
                    {
                        CardCrawlGame.startOver();
                    } else
                    {
                        CardCrawlGame.playCreditsBgm = false;
                        CardCrawlGame.startOverButShowCredits();
                    }
                }
            } else
            {
                returnButton.hide();
                if(AbstractDungeon.unlocks.isEmpty() || Settings.isDemo || Settings.isDailyRun || Settings.isTrial)
                {
                    if(UnlockTracker.isCharacterLocked("The Silent"))
                    {
                        AbstractDungeon.unlocks.add(new TheSilentUnlock());
                        AbstractDungeon.unlockScreen.open((AbstractUnlock)AbstractDungeon.unlocks.remove(0));
                    } else
                    if(UnlockTracker.isCharacterLocked("Defect") && AbstractDungeon.player.chosenClass == com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.THE_SILENT)
                    {
                        AbstractDungeon.unlocks.add(new DefectUnlock());
                        defectUnlockedThisRun = true;
                        AbstractDungeon.unlockScreen.open((AbstractUnlock)AbstractDungeon.unlocks.remove(0));
                    } else
                    if(willWatcherUnlock())
                    {
                        AbstractDungeon.unlocks.add(new WatcherUnlock());
                        AbstractDungeon.unlockScreen.open((AbstractUnlock)AbstractDungeon.unlocks.remove(0));
                    } else
                    {
                        Settings.isTrial = false;
                        Settings.isDailyRun = false;
                        Settings.isEndless = false;
                        CardCrawlGame.trial = null;
                        CardCrawlGame.startOver();
                    }
                } else
                {
                    AbstractDungeon.unlocks.clear();
                    Settings.isTrial = false;
                    Settings.isDailyRun = false;
                    Settings.isEndless = false;
                    CardCrawlGame.trial = null;
                    CardCrawlGame.playCreditsBgm = false;
                    CardCrawlGame.startOverButShowCredits();
                }
            }
        }
        updateStatsScreen();
        if(deathAnimWaitTimer != 0.0F)
        {
            deathAnimWaitTimer -= Gdx.graphics.getDeltaTime();
            if(deathAnimWaitTimer < 0.0F)
            {
                deathAnimWaitTimer = 0.0F;
                AbstractDungeon.player.playDeathAnimation();
            }
        } else
        {
            deathTextTimer -= Gdx.graphics.getDeltaTime();
            if(deathTextTimer < 0.0F)
                deathTextTimer = 0.0F;
            deathTextColor.a = Interpolation.fade.apply(0.0F, 1.0F, 1.0F - deathTextTimer / 5F);
            defeatTextColor.a = Interpolation.fade.apply(0.0F, 1.0F, 1.0F - deathTextTimer / 5F);
        }
        if(monsters != null)
        {
            monsters.update();
            monsters.updateAnimations();
        }
        if((float)particles.size() < 50F)
            particles.add(new DeathScreenFloatyEffect());
    }

    private void updateControllerInput()
    {
        if(!Settings.isControllerMode || AbstractDungeon.topPanel.selectPotionMode || !AbstractDungeon.topPanel.potionUi.isHidden || AbstractDungeon.player.viewingRelics)
            return;
        boolean anyHovered = false;
        int index = 0;
        if(stats != null)
        {
            Iterator iterator = stats.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                GameOverStat s = (GameOverStat)iterator.next();
                if(s.hb.hovered)
                {
                    anyHovered = true;
                    break;
                }
                index++;
            } while(true);
        }
        if(!anyHovered)
            index = -1;
        if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
        {
            index--;
            if(stats.size() > 10)
            {
                int numItemsInRightColumn = (stats.size() - 2) / 2;
                if(stats.size() % 2 == 0)
                    numItemsInRightColumn--;
                if(index == numItemsInRightColumn)
                    index = stats.size() - 1;
                else
                if(index < 0)
                    index = stats.size() - 1;
                else
                if(index == stats.size() - 2)
                    index--;
            } else
            if(index < 0)
                index = stats.size() - 1;
            else
            if(index == stats.size() - 2)
                index--;
            CInputHelper.setCursor(((GameOverStat)stats.get(index)).hb);
        } else
        if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
        {
            if(index == -1)
            {
                index = 0;
                CInputHelper.setCursor(((GameOverStat)stats.get(index)).hb);
                return;
            }
            index++;
            if(stats.size() > 10)
            {
                int numItemsInLeftColumn = (stats.size() - 2) / 2;
                if(stats.size() % 2 != 0)
                    numItemsInLeftColumn++;
                if(index == numItemsInLeftColumn)
                    index = stats.size() - 1;
            } else
            {
                if(index > stats.size() - 1)
                    index = 0;
                if(index == stats.size() - 2)
                    index++;
            }
            if(index > stats.size() - 3)
                index = stats.size() - 1;
            CInputHelper.setCursor(((GameOverStat)stats.get(index)).hb);
        } else
        if(CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed() || CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed())
        {
            if(stats.size() > 10)
            {
                int numItemsInLeftColumn = (stats.size() - 2) / 2;
                if(stats.size() % 2 != 0)
                    numItemsInLeftColumn++;
                if(index < numItemsInLeftColumn - 1)
                    index += numItemsInLeftColumn;
                else
                if(index == numItemsInLeftColumn - 1)
                {
                    if(stats.size() % 2 != 0)
                        index += numItemsInLeftColumn - 1;
                    else
                        index += numItemsInLeftColumn;
                } else
                if(index >= numItemsInLeftColumn && index < stats.size() - 2)
                    index -= numItemsInLeftColumn;
            }
            if(index > stats.size() - 1)
                index = stats.size() - 1;
            if(index != -1)
                CInputHelper.setCursor(((GameOverStat)stats.get(index)).hb);
        }
    }

    private void updateAscensionProgress()
    {
        if((isVictory || AbstractDungeon.actNum >= 4) && AbstractDungeon.isAscensionMode && Settings.isStandardRun() && AbstractDungeon.ascensionLevel < 20 && StatsScreen.isPlayingHighestAscension(AbstractDungeon.player.getPrefs()))
        {
            StatsScreen.incrementAscension(AbstractDungeon.player.getCharStat());
            AbstractDungeon.topLevelEffects.add(new AscensionLevelUpTextEffect());
        } else
        if(!AbstractDungeon.ascensionCheck && UnlockTracker.isAscensionUnlocked(AbstractDungeon.player) && !Settings.seedSet)
            AbstractDungeon.topLevelEffects.add(new AscensionUnlockedTextEffect());
    }

    private void updateStatsScreen()
    {
        if(showingStats)
        {
            progressBarAlpha = MathHelper.slowColorLerpSnap(progressBarAlpha, 1.0F);
            statsTimer -= Gdx.graphics.getDeltaTime();
            if(statsTimer < 0.0F)
                statsTimer = 0.0F;
            returnButton.y = Interpolation.pow3In.apply((float)Settings.HEIGHT * 0.1F, (float)Settings.HEIGHT * 0.15F, (statsTimer * 1.0F) / 0.5F);
            AbstractDungeon.dynamicBanner.y = Interpolation.pow3In.apply((float)Settings.HEIGHT / 2.0F + 320F * Settings.scale, DynamicBanner.Y, (statsTimer * 1.0F) / 0.5F);
            GameOverStat i;
            for(Iterator iterator = stats.iterator(); iterator.hasNext(); i.update())
                i = (GameOverStat)iterator.next();

            if(statAnimateTimer < 0.0F)
            {
                boolean allStatsShown = true;
                Iterator iterator1 = stats.iterator();
                do
                {
                    if(!iterator1.hasNext())
                        break;
                    GameOverStat i = (GameOverStat)iterator1.next();
                    if(!i.hidden)
                        continue;
                    i.hidden = false;
                    statAnimateTimer = 0.1F;
                    allStatsShown = false;
                    break;
                } while(true);
                if(allStatsShown)
                    animateProgressBar();
            } else
            {
                statAnimateTimer -= Gdx.graphics.getDeltaTime();
            }
        }
    }

    public void render(SpriteBatch sb)
    {
        Iterator i = particles.iterator();
        do
        {
            if(!i.hasNext())
                break;
            DeathScreenFloatyEffect e = (DeathScreenFloatyEffect)i.next();
            if(e.renderBehind)
                e.render(sb);
            e.update();
            if(e.isDone)
                i.remove();
        } while(true);
        AbstractDungeon.player.render(sb);
        if(monsters != null)
            monsters.render(sb);
        sb.setBlendFunction(770, 1);
        i = particles.iterator();
        do
        {
            if(!i.hasNext())
                break;
            DeathScreenFloatyEffect e = (DeathScreenFloatyEffect)i.next();
            if(!e.renderBehind)
                e.render(sb);
        } while(true);
        sb.setBlendFunction(770, 771);
        renderStatsScreen(sb);
        if(!showingStats && !isVictory)
            FontHelper.renderFontCentered(sb, FontHelper.topPanelInfoFont, deathText, (float)Settings.WIDTH / 2.0F, DEATH_TEXT_Y, deathTextColor);
        returnButton.render(sb);
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/screens/DeathScreen.getName());
    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private MonsterGroup monsters;
    private String deathText;
    private ArrayList particles;
    private static final float NUM_PARTICLES = 50F;
    private float deathAnimWaitTimer;
    private static final float DEATH_TEXT_TIME = 5F;
    private float deathTextTimer;
    private Color defeatTextColor;
    private Color deathTextColor;
    private static final float DEATH_TEXT_Y;
    private boolean defectUnlockedThisRun;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("DeathScreen");
        TEXT = uiStrings.TEXT;
        DEATH_TEXT_Y = (float)Settings.HEIGHT - 360F * Settings.scale;
    }
}
