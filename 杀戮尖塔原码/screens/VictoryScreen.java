// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   VictoryScreen.java

package com.megacrit.cardcrawl.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.metrics.Metrics;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.neow.NeowUnlockScreen;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.screens.stats.AchievementGrid;
import com.megacrit.cardcrawl.screens.stats.StatsScreen;
import com.megacrit.cardcrawl.ui.buttons.*;
import com.megacrit.cardcrawl.ui.panels.PotionPopUp;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.vfx.scene.*;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.screens:
//            GameOverScreen, GameOverStat, DungeonMapScreen

public class VictoryScreen extends GameOverScreen
{

    public VictoryScreen(MonsterGroup m)
    {
        effect = new ArrayList();
        effectTimer = 0.0F;
        effectTimer2 = 0.0F;
        unlockedBetaArt = 0;
        isVictory = true;
        playtime = (long)CardCrawlGame.playtime;
        if(playtime < 0L)
            playtime = 0L;
        AbstractDungeon.getCurrRoom().clearEvent();
        resetScoreChecks();
        AbstractDungeon.is_victory = true;
        AbstractDungeon.player.drawX = (float)Settings.WIDTH / 2.0F;
        AbstractDungeon.dungeonMapScreen.closeInstantly();
        AbstractDungeon.screen = com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.VICTORY;
        AbstractDungeon.overlayMenu.showBlackScreen(1.0F);
        AbstractDungeon.previousScreen = null;
        AbstractDungeon.overlayMenu.cancelButton.hideInstantly();
        AbstractDungeon.isScreenUp = true;
        monsters = m;
        logger.info((new StringBuilder()).append("PLAYTIME: ").append(playtime).toString());
        if(AbstractDungeon.getCurrRoom() instanceof RestRoom)
            ((RestRoom)AbstractDungeon.getCurrRoom()).cutFireSound();
        showingStats = false;
        returnButton = new ReturnToMenuButton();
        returnButton.appear((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT * 0.15F, TEXT[0]);
        AbstractDungeon.dynamicBanner.appear(TEXT[12]);
        if(Settings.isStandardRun())
            CardCrawlGame.playerPref.putInteger((new StringBuilder()).append(AbstractDungeon.player.chosenClass.name()).append("_SPIRITS").toString(), 1);
        unlockedBetaArt = -1;
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$characters$AbstractPlayer$PlayerClass[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$characters$AbstractPlayer$PlayerClass = new int[com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$characters$AbstractPlayer$PlayerClass[com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.IRONCLAD.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$characters$AbstractPlayer$PlayerClass[com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.THE_SILENT.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$characters$AbstractPlayer$PlayerClass[com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.DEFECT.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$characters$AbstractPlayer$PlayerClass[com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.WATCHER.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass[AbstractDungeon.player.chosenClass.ordinal()])
        {
        case 1: // '\001'
            if(!UnlockTracker.isAchievementUnlocked("RUBY_PLUS"))
            {
                unlockedBetaArt = 0;
                UnlockTracker.unlockAchievement("RUBY_PLUS");
            }
            break;

        case 2: // '\002'
            if(!UnlockTracker.isAchievementUnlocked("EMERALD_PLUS"))
            {
                unlockedBetaArt = 1;
                UnlockTracker.unlockAchievement("EMERALD_PLUS");
                CampfireUI.hidden = true;
            }
            break;

        case 3: // '\003'
            if(!UnlockTracker.isAchievementUnlocked("SAPPHIRE_PLUS"))
            {
                unlockedBetaArt = 2;
                UnlockTracker.unlockAchievement("SAPPHIRE_PLUS");
                effectTimer2 = 5F;
            }
            break;

        case 4: // '\004'
            if(!UnlockTracker.isAchievementUnlocked("AMETHYST_PLUS"))
            {
                unlockedBetaArt = 4;
                UnlockTracker.unlockAchievement("AMETHYST");
                UnlockTracker.unlockAchievement("AMETHYST_PLUS");
            }
            break;
        }
        if(UnlockTracker.isAchievementUnlocked("RUBY_PLUS") && UnlockTracker.isAchievementUnlocked("EMERALD_PLUS") && UnlockTracker.isAchievementUnlocked("SAPPHIRE_PLUS"))
            UnlockTracker.unlockAchievement("THE_ENDING");
        submitVictoryMetrics();
        if(playtime != 0L)
            StatsScreen.updateVictoryTime(playtime);
        StatsScreen.incrementVictory(AbstractDungeon.player.getCharStat());
        StatsScreen.incrementAscension(AbstractDungeon.player.getCharStat());
        if(AbstractDungeon.ascensionLevel == 10 && !Settings.isTrial)
            UnlockTracker.unlockAchievement("ASCEND_10");
        else
        if(AbstractDungeon.ascensionLevel == 20 && !Settings.isTrial)
            UnlockTracker.unlockAchievement("ASCEND_20");
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
        stats.add(new GameOverStat((new StringBuilder()).append(TEXT[1]).append(" (").append(AbstractDungeon.floorNum).append(")").toString(), null, Integer.toString(floorPoints)));
        stats.add(new GameOverStat((new StringBuilder()).append(TEXT[8]).append(" (").append(CardCrawlGame.monstersSlain).append(")").toString(), null, Integer.toString(monsterPoints)));
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
        if(CardCrawlGame.dungeon instanceof TheEnding)
            stats.add(new GameOverStat(HEARTBREAKER.NAME, HEARTBREAKER.DESCRIPTIONS[0], Integer.toString(250)));
        stats.add(new GameOverStat());
        stats.add(new GameOverStat(TEXT[4], null, Integer.toString(score)));
    }

    protected void submitVictoryMetrics()
    {
        Metrics metrics = new Metrics();
        metrics.gatherAllDataAndSave(false, true, null);
        if(Settings.UPLOAD_DATA)
        {
            metrics.setValues(false, true, null, com.megacrit.cardcrawl.metrics.Metrics.MetricRequestType.UPLOAD_METRICS);
            Thread t = new Thread(metrics);
            t.start();
        }
        if(Settings.isStandardRun())
            StatsScreen.updateFurthestAscent(AbstractDungeon.floorNum);
        if(SaveHelper.shouldDeleteSave())
            SaveAndContinue.deleteSave(AbstractDungeon.player);
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
        AbstractDungeon.dynamicBanner.appearInstantly(TEXT[12]);
        AbstractDungeon.overlayMenu.showBlackScreen(1.0F);
        if(fromVictoryUnlock)
            returnButton.appear((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT * 0.15F, TEXT[0]);
        else
        if(!showingStats)
            returnButton.appear((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT * 0.15F, TEXT[0]);
        else
        if(unlockBundle == null)
            returnButton.appear((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT * 0.15F, TEXT[0]);
        else
            returnButton.appear((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT * 0.15F, TEXT[5]);
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
                updateAscensionAndBetaArtProgress();
                if(unlockBundle == null)
                    returnButton.appear((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT * 0.15F, TEXT[0]);
                else
                    returnButton.appear((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT * 0.15F, TEXT[5]);
            } else
            if(unlockBundle != null)
            {
                AbstractDungeon.gUnlockScreen.open(unlockBundle, true);
                AbstractDungeon.previousScreen = com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.VICTORY;
                AbstractDungeon.screen = com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.NEOW_UNLOCK;
                unlockBundle = null;
                returnButton.label = TEXT[5];
            } else
            {
                returnButton.hide();
                if(AbstractDungeon.unlocks.isEmpty() || Settings.isDemo)
                {
                    CardCrawlGame.playCreditsBgm = true;
                    CardCrawlGame.startOverButShowCredits();
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
                        CardCrawlGame.playCreditsBgm = true;
                        CardCrawlGame.startOverButShowCredits();
                    }
                }
            }
        }
        updateStatsScreen();
        if(monsters != null)
        {
            monsters.update();
            monsters.updateAnimations();
        }
        updateVfx();
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

    private void updateAscensionAndBetaArtProgress()
    {
        if(AbstractDungeon.isAscensionMode && !Settings.seedSet && !Settings.isTrial && AbstractDungeon.ascensionLevel < 20 && StatsScreen.isPlayingHighestAscension(AbstractDungeon.player.getPrefs()))
        {
            StatsScreen.incrementAscension(AbstractDungeon.player.getCharStat());
            AbstractDungeon.topLevelEffects.add(new AscensionLevelUpTextEffect());
        } else
        if(!AbstractDungeon.ascensionCheck && UnlockTracker.isAscensionUnlocked(AbstractDungeon.player))
            AbstractDungeon.topLevelEffects.add(new AscensionUnlockedTextEffect());
        else
        if(unlockedBetaArt != -1)
            AbstractDungeon.topLevelEffects.add(new BetaCardArtUnlockedTextEffect(unlockedBetaArt));
    }

    private void updateVfx()
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass[AbstractDungeon.player.chosenClass.ordinal()])
        {
        default:
            break;

        case 1: // '\001'
            effectTimer -= Gdx.graphics.getDeltaTime();
            if(effectTimer < 0.0F)
            {
                effect.add(new SlowFireParticleEffect());
                effect.add(new SlowFireParticleEffect());
                effect.add(new IroncladVictoryFlameEffect());
                effect.add(new IroncladVictoryFlameEffect());
                effect.add(new IroncladVictoryFlameEffect());
                effectTimer = 0.1F;
            }
            break;

        case 2: // '\002'
            effectTimer -= Gdx.graphics.getDeltaTime();
            if(effectTimer < 0.0F)
            {
                if(effect.size() < 100)
                {
                    effect.add(new SilentVictoryStarEffect());
                    effect.add(new SilentVictoryStarEffect());
                    effect.add(new SilentVictoryStarEffect());
                    effect.add(new SilentVictoryStarEffect());
                }
                effectTimer = 0.1F;
            }
            effectTimer2 += Gdx.graphics.getDeltaTime();
            if(effectTimer2 > 1.0F)
                effectTimer2 = 1.0F;
            break;

        case 3: // '\003'
            boolean foundEyeVfx = false;
            Iterator iterator = effect.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractGameEffect e = (AbstractGameEffect)iterator.next();
                if(!(e instanceof DefectVictoryEyesEffect))
                    continue;
                foundEyeVfx = true;
                break;
            } while(true);
            if(!foundEyeVfx)
                effect.add(new DefectVictoryEyesEffect());
            if(effect.size() < 15)
                effect.add(new DefectVictoryNumberEffect());
            break;

        case 4: // '\004'
            boolean createdEffect = false;
            Iterator iterator1 = effect.iterator();
            do
            {
                if(!iterator1.hasNext())
                    break;
                AbstractGameEffect e = (AbstractGameEffect)iterator1.next();
                if(!(e instanceof WatcherVictoryEffect))
                    continue;
                createdEffect = true;
                break;
            } while(true);
            if(!createdEffect)
                effect.add(new WatcherVictoryEffect());
            break;
        }
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
        sb.setBlendFunction(770, 1);
        Iterator i = effect.iterator();
        do
        {
            if(!i.hasNext())
                break;
            AbstractGameEffect e = (AbstractGameEffect)i.next();
            if(e.renderBehind)
                e.render(sb);
            e.update();
            if(e.isDone)
                i.remove();
        } while(true);
        sb.setBlendFunction(770, 771);
        if(AbstractDungeon.player.chosenClass == com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.THE_SILENT)
        {
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, effectTimer2));
            AbstractDungeon.player.renderShoulderImg(sb);
        }
        sb.setBlendFunction(770, 1);
        i = effect.iterator();
        do
        {
            if(!i.hasNext())
                break;
            AbstractGameEffect e = (AbstractGameEffect)i.next();
            if(!e.renderBehind)
                e.render(sb);
        } while(true);
        sb.setBlendFunction(770, 771);
        renderStatsScreen(sb);
        returnButton.render(sb);
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/screens/VictoryScreen.getName());
    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private MonsterGroup monsters;
    private ArrayList effect;
    private float effectTimer;
    private float effectTimer2;
    public static long STINGER_ID = -999L;
    public static String STINGER_KEY = "";
    private int unlockedBetaArt;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("VictoryScreen");
        TEXT = uiStrings.TEXT;
    }
}
