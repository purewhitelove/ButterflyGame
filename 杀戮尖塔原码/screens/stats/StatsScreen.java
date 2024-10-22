// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StatsScreen.java

package com.megacrit.cardcrawl.screens.stats;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.CharacterManager;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.integrations.PublisherIntegration;
import com.megacrit.cardcrawl.localization.AchievementStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.screens.mainMenu.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.unlock.misc.*;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.screens.stats:
//            CharStat, AchievementGrid, AchievementItem

public class StatsScreen
    implements ScrollBarListener
{

    public StatsScreen()
    {
        button = new MenuCancelButton();
        allCharsHb = new Hitbox(150F * Settings.scale, 150F * Settings.scale);
        ironcladHb = new Hitbox(150F * Settings.scale, 150F * Settings.scale);
        screenUp = false;
        screenX = HIDE_X;
        targetX = HIDE_X;
        grabbedScreen = false;
        grabStartY = 0.0F;
        scrollTargetY = 0.0F;
        scrollY = 0.0F;
        scrollLowerBound = -Settings.DEFAULT_SCROLL_LIMIT;
        scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT;
        scrollBar = null;
        logger.info("Loading character stats.");
        CardCrawlGame.characterManager.refreshAllCharStats();
        all = new CharStat(CardCrawlGame.characterManager.getAllCharacterStats());
        achievements = new AchievementGrid();
        Settings.totalPlayTime = all.playTime;
    }

    public void refreshData()
    {
        logger.info("Refreshing stats screen data.");
        CardCrawlGame.characterManager.refreshAllCharStats();
        all = new CharStat(CardCrawlGame.characterManager.getAllCharacterStats());
        achievements = new AchievementGrid();
        Settings.totalPlayTime = all.playTime;
    }

    public void update()
    {
        updateControllerInput();
        if(Settings.isControllerMode && controllerHb != null)
            if((float)Gdx.input.getY() > (float)Settings.HEIGHT * 0.75F)
                scrollTargetY += Settings.SCROLL_SPEED;
            else
            if((float)Gdx.input.getY() < (float)Settings.HEIGHT * 0.25F)
                scrollTargetY -= Settings.SCROLL_SPEED;
        if(Settings.isControllerMode && controllerHb != null)
            Gdx.input.setCursorPosition((int)controllerHb.cX, (int)((float)Settings.HEIGHT - controllerHb.cY));
        button.update();
        if(button.hb.clicked || InputHelper.pressedEscape)
        {
            InputHelper.pressedEscape = false;
            CardCrawlGame.mainMenuScreen.screen = com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen.CurScreen.MAIN_MENU;
            hide();
        }
        screenX = MathHelper.uiLerpSnap(screenX, targetX);
        boolean isDraggingScrollBar = scrollBar.update();
        if(!isDraggingScrollBar)
            updateScrolling();
        achievements.update();
    }

    private void updateControllerInput()
    {
        if(!Settings.isControllerMode)
            return;
        boolean anyHovered = false;
        int index = 0;
        allCharsHb.update();
        ironcladHb.update();
        if(silentHb != null)
            silentHb.update();
        if(defectHb != null)
            defectHb.update();
        if(watcherHb != null)
            watcherHb.update();
        if(allCharsHb != null && allCharsHb.hovered)
            anyHovered = true;
        index = 0;
        if(!anyHovered)
        {
            Iterator iterator = achievements.items.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AchievementItem a = (AchievementItem)iterator.next();
                a.hb.update();
                if(a.hb.hovered)
                {
                    anyHovered = true;
                    break;
                }
                index++;
            } while(true);
        }
        if(!anyHovered)
        {
            if(ironcladHb != null && ironcladHb.hovered)
                anyHovered = true;
            if(silentHb != null && silentHb.hovered)
                anyHovered = true;
            if(defectHb != null && defectHb.hovered)
                anyHovered = true;
            if(watcherHb != null && watcherHb.hovered)
                anyHovered = true;
        }
        if(!anyHovered)
        {
            CInputHelper.setCursor(allCharsHb);
            controllerHb = allCharsHb;
        } else
        if(allCharsHb.hovered)
        {
            if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
            {
                CInputHelper.setCursor(((AchievementItem)achievements.items.get(0)).hb);
                controllerHb = ((AchievementItem)achievements.items.get(0)).hb;
            }
        } else
        if(ironcladHb.hovered)
        {
            if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
            {
                index = achievements.items.size() - achievements.items.size() % 5;
                CInputHelper.setCursor(((AchievementItem)achievements.items.get(index)).hb);
                controllerHb = ((AchievementItem)achievements.items.get(index)).hb;
            } else
            if((CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed()) && silentHb != null)
            {
                CInputHelper.setCursor(silentHb);
                controllerHb = silentHb;
            }
        } else
        if(silentHb != null && silentHb.hovered)
        {
            if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
            {
                CInputHelper.setCursor(ironcladHb);
                controllerHb = ironcladHb;
            } else
            if((CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed()) && defectHb != null)
            {
                CInputHelper.setCursor(defectHb);
                controllerHb = defectHb;
            }
        } else
        if(defectHb != null && defectHb.hovered)
        {
            if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
            {
                CInputHelper.setCursor(silentHb);
                controllerHb = silentHb;
            } else
            if((CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed()) && watcherHb != null)
            {
                CInputHelper.setCursor(watcherHb);
                controllerHb = watcherHb;
            }
        } else
        if(watcherHb != null && watcherHb.hovered)
        {
            if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
            {
                CInputHelper.setCursor(defectHb);
                controllerHb = defectHb;
            }
        } else
        if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
        {
            if((index -= 5) < 0)
            {
                CInputHelper.setCursor(allCharsHb);
                controllerHb = allCharsHb;
            } else
            {
                CInputHelper.setCursor(((AchievementItem)achievements.items.get(index)).hb);
                controllerHb = ((AchievementItem)achievements.items.get(index)).hb;
            }
        } else
        if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
        {
            if((index += 5) > achievements.items.size() - 1)
            {
                CInputHelper.setCursor(ironcladHb);
                controllerHb = ironcladHb;
            } else
            {
                CInputHelper.setCursor(((AchievementItem)achievements.items.get(index)).hb);
                controllerHb = ((AchievementItem)achievements.items.get(index)).hb;
            }
        } else
        if(CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
        {
            if((--index % 5 == 4 || index == -1) && (index += 5) > achievements.items.size() - 1)
                index = achievements.items.size() - 1;
            CInputHelper.setCursor(((AchievementItem)achievements.items.get(index)).hb);
            controllerHb = ((AchievementItem)achievements.items.get(index)).hb;
        } else
        if(CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed())
        {
            if(++index % 5 == 0)
                index -= 5;
            if(index > achievements.items.size() - 1)
                index = achievements.items.size() - achievements.items.size() % 5;
            CInputHelper.setCursor(((AchievementItem)achievements.items.get(index)).hb);
            controllerHb = ((AchievementItem)achievements.items.get(index)).hb;
        }
    }

    private void updateScrolling()
    {
        int y = InputHelper.mY;
        if(!grabbedScreen)
        {
            if(InputHelper.scrolledDown)
                scrollTargetY += Settings.SCROLL_SPEED;
            else
            if(InputHelper.scrolledUp)
                scrollTargetY -= Settings.SCROLL_SPEED;
            if(InputHelper.justClickedLeft)
            {
                grabbedScreen = true;
                grabStartY = (float)y - scrollTargetY;
            }
        } else
        if(InputHelper.isMouseDown)
            scrollTargetY = (float)y - grabStartY;
        else
            grabbedScreen = false;
        scrollY = MathHelper.scrollSnapLerpSpeed(scrollY, scrollTargetY);
        resetScrolling();
        updateBarPosition();
    }

    private void calculateScrollBounds()
    {
        if(!UnlockTracker.isCharacterLocked("Watcher"))
            scrollUpperBound = 3400F * Settings.scale;
        else
        if(!UnlockTracker.isCharacterLocked("Defect"))
            scrollUpperBound = 3000F * Settings.scale;
        else
        if(!UnlockTracker.isCharacterLocked("The Silent"))
            scrollUpperBound = 2550F * Settings.scale;
        else
            scrollUpperBound = 2250F * Settings.scale;
        scrollLowerBound = 100F * Settings.yScale;
    }

    private void resetScrolling()
    {
        if(scrollTargetY < scrollLowerBound)
            scrollTargetY = MathHelper.scrollSnapLerpSpeed(scrollTargetY, scrollLowerBound);
        else
        if(scrollTargetY > scrollUpperBound)
            scrollTargetY = MathHelper.scrollSnapLerpSpeed(scrollTargetY, scrollUpperBound);
    }

    public void open()
    {
        if(!Settings.isConsoleBuild)
        {
            if(CardCrawlGame.publisherIntegration.isInitialized() && CardCrawlGame.publisherIntegration.getNumUnlockedAchievements() >= 45)
                CardCrawlGame.publisherIntegration.unlockAchievement("ETERNAL_ONE");
            retroactiveAmethystUnlock();
        }
        if(UnlockTracker.isAchievementUnlocked("RUBY_PLUS") && UnlockTracker.isAchievementUnlocked("EMERALD_PLUS") && UnlockTracker.isAchievementUnlocked("SAPPHIRE_PLUS"))
            UnlockTracker.unlockAchievement("THE_ENDING");
        if(atlas == null)
        {
            atlas = new TextureAtlas(Gdx.files.internal("achievements/achievements.atlas"));
            logger.info("Loaded texture Achievement texture atlas.");
        }
        controllerHb = null;
        scrollTargetY = 0.0F;
        debugCharacterUnlock();
        targetX = SHOW_X;
        button.show(TEXT[0]);
        screenUp = true;
        CardCrawlGame.mainMenuScreen.screen = com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen.CurScreen.STATS;
        if(scrollBar == null)
        {
            refreshData();
            calculateScrollBounds();
            scrollBar = new ScrollBar(this);
            logger.info("Creating new scrollbar for Stats Screen.");
        } else
        {
            achievements.refreshImg();
        }
        if(Settings.isControllerMode)
        {
            Gdx.input.setCursorPosition((int)allCharsHb.cX, Settings.HEIGHT - (int)allCharsHb.cY);
            controllerHb = allCharsHb;
        }
        debugAchievementUnlock();
    }

    private void debugAchievementUnlock()
    {
        if(Settings.isInfo)
        {
            AchievementItem i;
            for(Iterator iterator = achievements.items.iterator(); iterator.hasNext(); UnlockTracker.unlockAchievement(i.key))
                i = (AchievementItem)iterator.next();

        }
    }

    private void debugCharacterUnlock()
    {
        if(Settings.isInfo)
        {
            String s;
            for(Iterator iterator = UnlockTracker.lockedCharacters.iterator(); iterator.hasNext(); UnlockTracker.hardUnlockOverride(s))
                s = (String)iterator.next();

        }
    }

    public void hide()
    {
        if(atlas != null)
        {
            atlas.dispose();
            atlas = null;
        }
        CardCrawlGame.sound.play("DECK_CLOSE", 0.1F);
        targetX = HIDE_X;
        button.hide();
        screenUp = false;
        CardCrawlGame.mainMenuScreen.panelScreen.refresh();
    }

    public static void updateFurthestAscent(int floor)
    {
        AbstractDungeon.player.getCharStat().furthestAscent(floor);
    }

    public static void updateHighestScore(int score)
    {
        AbstractDungeon.player.getCharStat().highestScore(score);
    }

    public static void updateHighestDailyScore(int score)
    {
        AbstractDungeon.player.getCharStat().highestDaily(score);
    }

    public static void updateVictoryTime(long time)
    {
        logger.info("Saving fastest victory...");
        AbstractDungeon.player.getCharStat().updateFastestVictory(time);
    }

    public static void incrementFloorClimbed()
    {
        AbstractDungeon.player.getCharStat().incrementFloorClimbed();
    }

    public static boolean isPlayingHighestAscension(Prefs p)
    {
        return AbstractDungeon.ascensionLevel == p.getInteger("ASCENSION_LEVEL", 1);
    }

    public static void retroactiveAscend10Unlock(Prefs pref)
    {
        if(pref.getInteger("ASCENSION_LEVEL", 0) >= 11)
            UnlockTracker.unlockAchievement("ASCEND_10");
    }

    public static void retroactiveAscend20Unlock(Prefs pref)
    {
        if(pref.getInteger("ASCENSION_LEVEL", 0) >= 21)
            UnlockTracker.unlockAchievement("ASCEND_20");
    }

    public static void retroactiveAmethystUnlock()
    {
        if(UnlockTracker.isAchievementUnlocked("AMETHYST_PLUS"))
            UnlockTracker.unlockAchievement("AMETHYST");
    }

    public static int getVictory(CharStat c)
    {
        return c.getVictoryCount();
    }

    public static void unlockAscension(CharStat c)
    {
        c.unlockAscension();
    }

    public static void incrementVictory(CharStat c)
    {
        c.incrementVictory();
    }

    public static void incrementAscension(CharStat c)
    {
        c.incrementAscension();
    }

    public static void incrementDeath(CharStat c)
    {
        c.incrementDeath();
    }

    public static void incrementVictoryIfZero(CharStat c)
    {
        if(c.getVictoryCount() == 0)
            c.incrementVictory();
    }

    public static void incrementEnemySlain()
    {
        AbstractDungeon.player.getCharStat().incrementEnemySlain();
    }

    public static void incrementBossSlain()
    {
        AbstractDungeon.player.getCharStat().incrementBossSlain();
    }

    public static void incrementPlayTime(long time)
    {
        AbstractDungeon.player.getCharStat().incrementPlayTime(time);
    }

    public void render(SpriteBatch sb)
    {
        renderStatScreen(sb);
        button.render(sb);
        scrollBar.render(sb);
    }

    private void renderStatScreen(SpriteBatch sb)
    {
        float renderY = scrollY;
        renderHeader(sb, NAMES[0], screenX, renderY);
        all.render(sb, screenX, renderY);
        renderY -= 400F * Settings.scale;
        renderHeader(sb, NAMES[1], screenX, renderY);
        achievements.render(sb, renderY);
        renderY -= 2200F * Settings.scale;
        for(Iterator iterator = CardCrawlGame.characterManager.getAllCharacters().iterator(); iterator.hasNext();)
        {
            AbstractPlayer c = (AbstractPlayer)iterator.next();
            c.renderStatScreen(sb, screenX, renderY);
            renderY -= 400F * Settings.scale;
        }

        if(Settings.isControllerMode)
        {
            allCharsHb.move(300F * Settings.scale, scrollY + 600F * Settings.scale);
            ironcladHb.move(300F * Settings.scale, scrollY - 1600F * Settings.scale);
            if(silentHb != null)
                silentHb.move(300F * Settings.scale, scrollY - 2000F * Settings.scale);
            if(defectHb != null)
                defectHb.move(300F * Settings.scale, scrollY - 2400F * Settings.scale);
            if(watcherHb != null)
                watcherHb.move(300F * Settings.scale, scrollY - 2800F * Settings.scale);
        }
    }

    public static void renderHeader(SpriteBatch sb, String text, float screenX, float renderY)
    {
        FontHelper.renderSmartText(sb, FontHelper.charTitleFont, text, screenX + 50F * Settings.scale, renderY + 850F * Settings.yScale, 9999F, 32F * Settings.scale, Settings.CREAM_COLOR);
    }

    public boolean statScreenUnlocked()
    {
        ArrayList allCharStats = CardCrawlGame.characterManager.getAllCharacterStats();
        for(Iterator iterator = allCharStats.iterator(); iterator.hasNext();)
        {
            CharStat cs = (CharStat)iterator.next();
            if(cs.bossKilled > 0 || cs.getDeathCount() > 0)
                return true;
        }

        return false;
    }

    public boolean dailiesUnlocked()
    {
        if(Settings.isDemo)
            return false;
        else
            return AbstractDungeon.player.getCharStat().furthestAscent > 17;
    }

    public boolean trialsUnlocked()
    {
        return AbstractDungeon.player.getCharStat().getVictoryCount() > 0;
    }

    public int getTotalVictories()
    {
        ArrayList allCharStats = CardCrawlGame.characterManager.getAllCharacterStats();
        int victoryTotal = 0;
        for(Iterator iterator = allCharStats.iterator(); iterator.hasNext();)
        {
            CharStat cs = (CharStat)iterator.next();
            victoryTotal += cs.getVictoryCount();
        }

        return victoryTotal;
    }

    public void scrolledUsingBar(float newPercent)
    {
        scrollY = MathHelper.valueFromPercentBetween(scrollLowerBound, scrollUpperBound, newPercent);
        scrollTargetY = scrollY;
        updateBarPosition();
    }

    private void updateBarPosition()
    {
        float percent = MathHelper.percentFromValueBetween(scrollLowerBound, scrollUpperBound, scrollY);
        scrollBar.parentScrolledToPercent(percent);
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/screens/stats/StatsScreen.getName());
    private static final AchievementStrings achievementStrings;
    public static final String NAMES[];
    public static final String TEXT[];
    public MenuCancelButton button;
    public Hitbox allCharsHb;
    public Hitbox ironcladHb;
    public Hitbox silentHb;
    public Hitbox defectHb;
    public Hitbox watcherHb;
    public Hitbox controllerHb;
    public boolean screenUp;
    private static final float SHOW_X;
    private static final float HIDE_X;
    private float screenX;
    private float targetX;
    private boolean grabbedScreen;
    private float grabStartY;
    private float scrollTargetY;
    private float scrollY;
    private float scrollLowerBound;
    private float scrollUpperBound;
    private ScrollBar scrollBar;
    public static CharStat all;
    public static AchievementGrid achievements;
    public static TextureAtlas atlas = null;

    static 
    {
        achievementStrings = CardCrawlGame.languagePack.getAchievementString("StatsScreen");
        NAMES = achievementStrings.NAMES;
        TEXT = achievementStrings.TEXT;
        SHOW_X = 300F * Settings.scale;
        HIDE_X = -800F * Settings.scale;
    }
}
