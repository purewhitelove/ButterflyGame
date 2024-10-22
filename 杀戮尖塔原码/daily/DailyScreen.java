// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DailyScreen.java

package com.megacrit.cardcrawl.daily;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.CharacterManager;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.daily.mods.AbstractDailyMod;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.integrations.DistributorFactory;
import com.megacrit.cardcrawl.integrations.PublisherIntegration;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.screens.leaderboards.LeaderboardEntry;
import com.megacrit.cardcrawl.screens.mainMenu.*;
import com.megacrit.cardcrawl.ui.buttons.ConfirmButton;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.daily:
//            TimeHelper, TimeLookup

public class DailyScreen
{

    public DailyScreen()
    {
        lastDaily = 0L;
        todaysChar = null;
        screenColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
        fadeColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        redTextColor = new Color(1.0F, 0.3F, 0.3F, 0.0F);
        creamColor = new Color(1.0F, 0.965F, 0.886F, 0.0F);
        alphaTarget = 0.0F;
        cancelButton = new MenuCancelButton();
        confirmButton = new ConfirmButton(TEXT[4]);
        screenX = -1100F * Settings.scale;
        targetX = -1100F * Settings.scale;
        header_x = 186F * Settings.scale;
        body_x = 208F * Settings.scale;
        char_x = 304F * Settings.scale;
        mode_desc_x = 264F * Settings.scale;
        mod_icon_x = 200F * Settings.scale;
        center_mod_offset_x = 500F * Settings.scale;
        timeLookupActive = false;
        timeUpdated = false;
        currentDay = 0L;
        waiting = true;
        viewMyScore = false;
        currentStartIndex = 1;
        currentEndIndex = 20;
        entries = new ArrayList();
        viewMyScoreHb = new Hitbox(250F * Settings.scale, 80F * Settings.scale);
        lineFadeInTimer = 0.0F;
        if(!DistributorFactory.isLeaderboardEnabled())
        {
            header_x += center_mod_offset_x;
            body_x += center_mod_offset_x;
            char_x += center_mod_offset_x;
            mode_desc_x += center_mod_offset_x;
            mod_icon_x += center_mod_offset_x;
        }
        date_x = header_x + FontHelper.getWidth(FontHelper.charTitleFont, TEXT[0], 1.0F) + 12F * Settings.scale;
        prevHb = new Hitbox(110F * Settings.scale, 110F * Settings.scale);
        prevHb.move(880F * Settings.scale, 530F * Settings.scale);
        nextHb = new Hitbox(110F * Settings.scale, 110F * Settings.scale);
        nextHb.move(1740F * Settings.scale, 530F * Settings.scale);
        prevDayHb = new Hitbox(80F * Settings.scale, 80F * Settings.scale);
        prevDayHb.move(1320F * Settings.scale, 900F * Settings.scale);
        nextDayHb = new Hitbox(80F * Settings.scale, 80F * Settings.scale);
        nextDayHb.move(1600F * Settings.scale, 900F * Settings.scale);
        viewMyScoreHb.move(1300F * Settings.scale, 80F * Settings.scale);
    }

    public void update()
    {
        cancelButton.update();
        if(cancelButton.hb.clicked)
        {
            FontHelper.ClearLeaderboardFontTextures();
            hide();
            CardCrawlGame.mainMenuScreen.panelScreen.refresh();
        }
        confirmButton.update();
        screenColor.a = MathHelper.popLerpSnap(screenColor.a, alphaTarget);
        screenX = MathHelper.uiLerpSnap(screenX, targetX);
        pingTimeServer();
        if(confirmButton.hb.clicked)
        {
            confirmButton.hb.clicked = false;
            CardCrawlGame.chosenCharacter = todaysChar.chosenClass;
            CardCrawlGame.mainMenuScreen.isFadingOut = true;
            hide();
            Settings.isTrial = false;
            Settings.isDailyRun = true;
            Settings.dailyDate = TimeHelper.daySince1970();
            Settings.isEndless = false;
            CardCrawlGame.mainMenuScreen.fadeOutMusic();
        }
        updateLeaderboardSection();
    }

    private void updateLeaderboardSection()
    {
        if(waiting && currentDay == 0L && TimeHelper.daySince1970() != 0L)
        {
            currentDay = TimeHelper.daySince1970();
            getData(1, 20);
        }
        if(!entries.isEmpty() && !waiting)
            lineFadeInTimer = MathHelper.slowColorLerpSnap(lineFadeInTimer, 1.0F);
        updateDateChangeArrows();
        updateArrows();
        updateViewMyScore();
    }

    private void updateDateChangeArrows()
    {
        if(waiting)
            return;
        prevDayHb.update();
        if(prevDayHb.justHovered)
            CardCrawlGame.sound.play("UI_HOVER");
        else
        if(prevDayHb.hovered && InputHelper.justClickedLeft)
        {
            prevDayHb.clickStarted = true;
            CardCrawlGame.sound.play("UI_CLICK_1");
        } else
        if(prevDayHb.clicked || CInputActionSet.drawPile.isJustPressed())
        {
            if(currentDay == 0L)
                currentDay = TimeHelper.daySince1970();
            currentDay--;
            prevDayHb.clicked = false;
            waiting = true;
            float xOffset = FontHelper.getSmartWidth(FontHelper.panelNameFont, TimeHelper.getDate(currentDay), 9999F, 0.0F);
            nextDayHb.move(prevDayHb.cX + xOffset + 76F * Settings.scale, nextDayHb.cY);
            getData(1, 20);
        }
        if(currentDay != 0L && currentDay < TimeHelper.daySince1970())
            nextDayHb.update();
        if(nextDayHb.justHovered)
            CardCrawlGame.sound.play("UI_HOVER");
        else
        if(nextDayHb.hovered && InputHelper.justClickedLeft)
        {
            nextDayHb.clickStarted = true;
            CardCrawlGame.sound.play("UI_CLICK_1");
        } else
        if(nextDayHb.clicked || CInputActionSet.discardPile.isJustPressed() && currentDay != 0L && currentDay < TimeHelper.daySince1970())
        {
            if(currentDay == 0L)
                currentDay = TimeHelper.daySince1970();
            currentDay++;
            nextDayHb.clicked = false;
            waiting = true;
            float xOffset = FontHelper.getSmartWidth(FontHelper.panelNameFont, TimeHelper.getDate(currentDay), 9999F, 0.0F);
            nextDayHb.move(prevDayHb.cX + xOffset + 76F * Settings.scale, nextDayHb.cY);
            getData(1, 20);
        }
    }

    private void updateArrows()
    {
        if(waiting)
            return;
        if(entries.size() == 20)
        {
            nextHb.update();
            if(nextHb.justHovered)
                CardCrawlGame.sound.play("UI_HOVER");
            else
            if(nextHb.hovered && InputHelper.justClickedLeft)
            {
                nextHb.clickStarted = true;
                CardCrawlGame.sound.play("UI_CLICK_1");
            } else
            if(nextHb.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed())
            {
                nextHb.clicked = false;
                currentStartIndex = currentEndIndex + 1;
                currentEndIndex = currentStartIndex + 19;
                waiting = true;
                getData(currentStartIndex, currentEndIndex);
            }
        }
        if(currentStartIndex != 1)
        {
            prevHb.update();
            if(prevHb.justHovered)
                CardCrawlGame.sound.play("UI_HOVER");
            else
            if(prevHb.hovered && InputHelper.justClickedLeft)
            {
                prevHb.clickStarted = true;
                CardCrawlGame.sound.play("UI_CLICK_1");
            } else
            if(prevHb.clicked || CInputActionSet.pageLeftViewDeck.isJustPressed())
            {
                prevHb.clicked = false;
                currentStartIndex = currentStartIndex - 20;
                if(currentStartIndex < 1)
                    currentStartIndex = 1;
                currentEndIndex = currentStartIndex + 19;
                waiting = true;
                getData(currentStartIndex, currentEndIndex);
            }
        }
    }

    private void updateViewMyScore()
    {
        if(waiting)
            return;
        viewMyScoreHb.update();
        if(viewMyScoreHb.justHovered)
            CardCrawlGame.sound.play("UI_HOVER");
        else
        if(viewMyScoreHb.hovered && InputHelper.justClickedLeft)
        {
            viewMyScoreHb.clickStarted = true;
            CardCrawlGame.sound.play("UI_CLICK_1");
        } else
        if(viewMyScoreHb.clicked || CInputActionSet.topPanel.isJustPressed())
        {
            viewMyScoreHb.clicked = false;
            viewMyScore = true;
            waiting = true;
            getData(currentStartIndex, currentEndIndex);
        }
    }

    private void getData(int startIndex, int endIndex)
    {
        if(currentDay != 0L)
            CardCrawlGame.publisherIntegration.getDailyLeaderboard(currentDay, startIndex, endIndex);
    }

    private void pingTimeServer()
    {
        if(TimeHelper.isTimeSet && !timeUpdated)
        {
            timeUpdated = true;
            dFormat = new SimpleDateFormat(TEXT[6]);
            dFormat.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
            determineLoadout();
            getData(1, 20);
        } else
        if(!timeLookupActive)
        {
            TimeLookup.fetchDailyTimeAsync();
            timeLookupActive = true;
        }
    }

    public void determineLoadout()
    {
        long todaySeed = TimeHelper.daySince1970();
        Settings.specialSeed = Long.valueOf(todaySeed);
        logger.info((new StringBuilder()).append("Today's mods: ").append(ModHelper.getEnabledModIDs().toString()).toString());
        AbstractDungeon.isAscensionMode = false;
        todayRng = new Random(Long.valueOf(todaySeed));
        todaysChar = CardCrawlGame.characterManager.getRandomCharacter(todayRng);
        Settings.seed = Long.valueOf(todayRng.randomLong());
        String seedText = SeedHelper.getString(Settings.seed.longValue());
        if(BadWordChecker.containsBadWord(seedText))
            Settings.seed = Long.valueOf(SeedHelper.generateUnoffensiveSeed(todayRng));
        AbstractDungeon.generateSeeds();
        ModHelper.setTodaysMods(todaySeed, todaysChar.chosenClass);
        confirmButton.isDisabled = false;
        confirmButton.show();
        logger.info((new StringBuilder()).append(TEXT[5]).append(todaysChar.chosenClass.name()).toString());
    }

    public void open()
    {
        CardCrawlGame.mainMenuScreen.screen = com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen.CurScreen.DAILY;
        CardCrawlGame.mainMenuScreen.darken();
        alphaTarget = 0.8F;
        cancelButton.show(TEXT[3]);
        targetX = 100F * Settings.scale;
        confirmButton.isDisabled = true;
        confirmButton.hide();
        waiting = true;
        timeUpdated = false;
        viewMyScore = false;
        currentStartIndex = 1;
        currentEndIndex = 20;
        entries.clear();
    }

    public void hide()
    {
        alphaTarget = 0.0F;
        cancelButton.hide();
        targetX = -1100F * Settings.scale;
        confirmButton.hide();
    }

    public void render(SpriteBatch sb)
    {
        renderTitle(sb);
        if(TimeHelper.isTimeSet)
        {
            renderTimeLeft(sb);
            renderCharacterAndNotice(sb);
            renderMods(sb);
            confirmButton.render(sb);
        } else
        {
            FontHelper.renderSmartText(sb, FontHelper.charDescFont, TEXT[1], screenX + 50F * Settings.scale, 786F * Settings.scale, 9999F, MOD_LINE_SPACING, Settings.CREAM_COLOR);
        }
        renderScoreHeaders(sb);
        renderScoreDateToggler(sb);
        renderViewMyScoreBox(sb);
        renderArrows(sb);
        renderScores(sb);
        renderDateToggleArrows(sb);
        renderDisclaimer(sb);
        cancelButton.render(sb);
    }

    private void renderDateToggleArrows(SpriteBatch sb)
    {
        sb.draw(ImageMaster.CF_LEFT_ARROW, prevDayHb.cX - 24F, prevDayHb.cY - 24F, 24F, 24F, 48F, 48F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
        if(Settings.isControllerMode)
            sb.draw(CInputActionSet.drawPile.getKeyImg(), prevDayHb.cX - 32F - 60F * Settings.scale, (prevDayHb.cY - 32F) + 0.0F * Settings.scale, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        if(currentDay != 0L && currentDay < TimeHelper.daySince1970())
        {
            sb.draw(ImageMaster.CF_RIGHT_ARROW, nextDayHb.cX - 24F, nextDayHb.cY - 24F, 24F, 24F, 48F, 48F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
            nextDayHb.render(sb);
            if(Settings.isControllerMode)
                sb.draw(CInputActionSet.discardPile.getKeyImg(), (nextDayHb.cX - 32F) + 60F * Settings.scale, (nextDayHb.cY - 32F) + 0.0F * Settings.scale, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        }
        prevDayHb.render(sb);
    }

    private void renderScoreDateToggler(SpriteBatch sb)
    {
        if(currentDay == 0L)
            FontHelper.renderFontLeftTopAligned(sb, FontHelper.panelNameFont, TimeHelper.getTodayDate(), 1360F * Settings.scale, 910F * Settings.scale, new Color(0.53F, 0.808F, 0.92F, lineFadeInTimer));
        else
            FontHelper.renderFontLeftTopAligned(sb, FontHelper.panelNameFont, TimeHelper.getDate(currentDay), 1360F * Settings.scale, 910F * Settings.scale, new Color(0.53F, 0.808F, 0.92F, lineFadeInTimer));
    }

    private void renderTitle(SpriteBatch sb)
    {
        FontHelper.renderFontLeftDownAligned(sb, FontHelper.charTitleFont, TEXT[0], header_x, TITLE_Y, Settings.GOLD_COLOR);
        String offlineModeNotice = "";
        if(TimeHelper.isOfflineMode())
            offlineModeNotice = TEXT[16];
        if(TimeHelper.isTimeSet)
            FontHelper.renderFontLeftDownAligned(sb, FontHelper.charDescFont, (new StringBuilder()).append(TimeHelper.getTodayDate()).append(offlineModeNotice).toString(), date_x, TITLE_Y, Color.SKY);
    }

    private void renderTimeLeft(SpriteBatch sb)
    {
        FontHelper.renderFontLeftDownAligned(sb, FontHelper.charDescFont, (new StringBuilder()).append(TEXT[7]).append(TimeHelper.getTimeLeft()).toString(), body_x, TIME_LEFT_Y, Settings.CREAM_COLOR);
    }

    private void renderCharacterAndNotice(SpriteBatch sb)
    {
        if(todaysChar == null)
            return;
        Texture img = null;
        StringBuilder builder = new StringBuilder("#y");
        builder.append(TEXT_2[2]);
        builder.append(" NL ");
        img = todaysChar.getCustomModeCharacterButtonImage();
        if(lastDaily != TimeHelper.daySince1970())
            builder.append(todaysChar.getLocalizedCharacterName());
        if(img != null)
            sb.draw(img, header_x, CHAR_IMAGE_Y, 128F * Settings.scale, 128F * Settings.scale);
        if(lastDaily == TimeHelper.daySince1970())
            FontHelper.renderSmartText(sb, FontHelper.charDescFont, TEXT[2], char_x, CHAR_HEADER_Y, 9999F, MOD_LINE_SPACING, Settings.CREAM_COLOR);
        else
            FontHelper.renderSmartText(sb, FontHelper.charDescFont, builder.toString(), char_x, CHAR_HEADER_Y, 9999F, MOD_LINE_SPACING, Settings.CREAM_COLOR);
    }

    private void renderMods(SpriteBatch sb)
    {
        FontHelper.renderFont(sb, FontHelper.charDescFont, TEXT[13], header_x, MOD_HEADER_Y, Settings.GOLD_COLOR);
        float y = MOD_HEADER_Y - MOD_SECTION_SPACING;
        for(Iterator iterator = ModHelper.enabledMods.iterator(); iterator.hasNext();)
        {
            AbstractDailyMod mod = (AbstractDailyMod)iterator.next();
            StringBuilder builder = new StringBuilder();
            if(mod.positive)
                builder.append(FontHelper.colorString(mod.name, "g"));
            else
                builder.append(FontHelper.colorString(mod.name, "r"));
            builder.append(": ");
            builder.append(mod.description);
            FontHelper.renderSmartText(sb, FontHelper.charDescFont, builder.toString(), mode_desc_x, y, MOD_LINE_W, MOD_LINE_SPACING, Settings.CREAM_COLOR);
            sb.draw(mod.img, mod_icon_x, y - 44F * Settings.scale, 64F * Settings.scale, 64F * Settings.scale);
            y += FontHelper.getSmartHeight(FontHelper.charDescFont, builder.toString(), MOD_LINE_W, MOD_LINE_SPACING) - MOD_SECTION_SPACING;
        }

    }

    private void renderArrows(SpriteBatch sb)
    {
        boolean renderLeftArrow = true;
        if(currentStartIndex != 1 && renderLeftArrow)
        {
            fadeColor.a = lineFadeInTimer;
            sb.setColor(fadeColor);
            sb.draw(ImageMaster.POPUP_ARROW, prevHb.cX - 128F, prevHb.cY - 128F, 128F, 128F, 256F, 256F, Settings.scale * 0.75F, Settings.scale * 0.75F, 0.0F, 0, 0, 256, 256, false, false);
            if(prevHb.hovered)
            {
                sb.setBlendFunction(770, 1);
                fadeColor.a = lineFadeInTimer / 2.0F;
                sb.setColor(fadeColor);
                sb.draw(ImageMaster.POPUP_ARROW, prevHb.cX - 128F, prevHb.cY - 128F, 128F, 128F, 256F, 256F, Settings.scale * 0.75F, Settings.scale * 0.75F, 0.0F, 0, 0, 256, 256, false, false);
                sb.setBlendFunction(770, 771);
            }
            if(Settings.isControllerMode)
                sb.draw(CInputActionSet.pageLeftViewDeck.getKeyImg(), (prevHb.cX - 32F) + 0.0F * Settings.scale, prevHb.cY - 32F - 100F * Settings.scale, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
            prevHb.render(sb);
        }
        if(entries.size() == 20)
        {
            fadeColor.a = lineFadeInTimer;
            sb.setColor(fadeColor);
            sb.draw(ImageMaster.POPUP_ARROW, nextHb.cX - 128F, nextHb.cY - 128F, 128F, 128F, 256F, 256F, Settings.scale * 0.75F, Settings.scale * 0.75F, 0.0F, 0, 0, 256, 256, true, false);
            if(nextHb.hovered)
            {
                sb.setBlendFunction(770, 1);
                fadeColor.a = lineFadeInTimer / 2.0F;
                sb.setColor(fadeColor);
                sb.draw(ImageMaster.POPUP_ARROW, nextHb.cX - 128F, nextHb.cY - 128F, 128F, 128F, 256F, 256F, Settings.scale * 0.75F, Settings.scale * 0.75F, 0.0F, 0, 0, 256, 256, true, false);
                sb.setBlendFunction(770, 771);
            }
            if(Settings.isControllerMode)
                sb.draw(CInputActionSet.pageRightViewExhaust.getKeyImg(), (nextHb.cX - 32F) + 0.0F * Settings.scale, nextHb.cY - 32F - 100F * Settings.scale, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
            nextHb.render(sb);
        }
    }

    private void renderDisclaimer(SpriteBatch sb)
    {
        redTextColor.a = lineFadeInTimer;
        if(!Settings.usesTrophies)
            FontHelper.renderFontCentered(sb, FontHelper.panelNameFont, TEXT[15], (float)Settings.WIDTH * 0.26F, 80F * Settings.scale * lineFadeInTimer, redTextColor);
        else
            FontHelper.renderFontCentered(sb, FontHelper.panelNameFont, TEXT[18], (float)Settings.WIDTH * 0.26F, 80F * Settings.scale * lineFadeInTimer, redTextColor);
    }

    private void renderScoreHeaders(SpriteBatch sb)
    {
        creamColor.a = lineFadeInTimer;
        FontHelper.renderFontRightTopAligned(sb, FontHelper.charTitleFont, TEXT[14], 1570F * Settings.scale, 980F * Settings.scale, new Color(0.937F, 0.784F, 0.317F, lineFadeInTimer));
        FontHelper.renderFontLeftTopAligned(sb, FontHelper.panelNameFont, TEXT_2[6], RANK_X, 860F * Settings.scale, creamColor);
        FontHelper.renderFontLeftTopAligned(sb, FontHelper.panelNameFont, TEXT_2[7], NAME_X, 860F * Settings.scale, creamColor);
        FontHelper.renderFontLeftTopAligned(sb, FontHelper.panelNameFont, TEXT_2[8], SCORE_X, 860F * Settings.scale, creamColor);
        sb.setColor(creamColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 1138F * Settings.scale, 168F * Settings.scale, LINE_THICKNESS, 692F * Settings.scale);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 1480F * Settings.scale, 168F * Settings.scale, LINE_THICKNESS, 692F * Settings.scale);
        sb.setColor(new Color(0.0F, 0.0F, 0.0F, lineFadeInTimer * 0.75F));
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 982F * Settings.scale, 814F * Settings.scale, 630F * Settings.scale, 16F * Settings.scale);
        sb.setColor(creamColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 982F * Settings.scale, 820F * Settings.scale, 630F * Settings.scale, LINE_THICKNESS);
    }

    private void renderViewMyScoreBox(SpriteBatch sb)
    {
        if(waiting)
            return;
        FontHelper.cardTitleFont.getData().setScale(1.0F);
        if(viewMyScoreHb.hovered)
            FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, TEXT_2[5], 1310F * Settings.scale, 80F * Settings.scale, Settings.GREEN_TEXT_COLOR);
        else
            FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, TEXT_2[5], 1310F * Settings.scale, 80F * Settings.scale, Settings.GOLD_COLOR);
        if(Settings.isControllerMode)
            sb.draw(CInputActionSet.topPanel.getKeyImg(), 1270F * Settings.scale - 32F - FontHelper.getSmartWidth(FontHelper.cardTitleFont, TEXT_2[5], 9999F, 0.0F) / 2.0F, -32F + 80F * Settings.scale, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        viewMyScoreHb.render(sb);
    }

    private void renderScores(SpriteBatch sb)
    {
        if(DistributorFactory.isLeaderboardEnabled())
            if(!waiting)
            {
                if(entries.isEmpty())
                {
                    FontHelper.renderFontCentered(sb, FontHelper.panelNameFont, TEXT_2[12], 1300F * Settings.scale, 540F * Settings.scale, Settings.GOLD_COLOR);
                } else
                {
                    for(int i = 0; i < entries.size(); i++)
                        ((LeaderboardEntry)entries.get(i)).render(sb, i);

                }
            } else
            if(CardCrawlGame.publisherIntegration.isInitialized())
                FontHelper.renderFontCentered(sb, FontHelper.panelNameFont, TEXT_2[9], 1300F * Settings.scale, 540F * Settings.scale, Settings.GOLD_COLOR);
            else
                FontHelper.renderFontCentered(sb, FontHelper.panelNameFont, TEXT_2[11], 1300F * Settings.scale, 540F * Settings.scale, Settings.RED_TEXT_COLOR);
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/daily/DailyScreen.getName());
    public static final String TEXT[];
    public static final String TEXT_2[];
    private long lastDaily;
    public AbstractPlayer todaysChar;
    private Color screenColor;
    private Color fadeColor;
    private Color redTextColor;
    private Color creamColor;
    private float alphaTarget;
    private MenuCancelButton cancelButton;
    private ConfirmButton confirmButton;
    private float screenX;
    private float targetX;
    private float header_x;
    private float date_x;
    private float body_x;
    private float char_x;
    private float mode_desc_x;
    private float mod_icon_x;
    private float center_mod_offset_x;
    private static final int CHAR_W = 128;
    private static final int MOD_W = 64;
    private static final float TITLE_Y;
    private static final float TIME_LEFT_Y;
    private static final float CHAR_IMAGE_Y;
    private static final float CHAR_HEADER_Y;
    private static final float MOD_HEADER_Y;
    private static final float MOD_LINE_W;
    private static final float MOD_LINE_SPACING;
    private static final float MOD_SECTION_SPACING;
    private DateFormat dFormat;
    private boolean timeLookupActive;
    private boolean timeUpdated;
    private Random todayRng;
    private long currentDay;
    public boolean waiting;
    public boolean viewMyScore;
    public int currentStartIndex;
    public int currentEndIndex;
    public ArrayList entries;
    private Hitbox prevHb;
    private Hitbox nextHb;
    private Hitbox viewMyScoreHb;
    private Hitbox prevDayHb;
    private Hitbox nextDayHb;
    public static final float RANK_X;
    public static final float NAME_X;
    public static final float SCORE_X;
    private float lineFadeInTimer;
    private static final float LINE_THICKNESS;

    static 
    {
        TEXT = CardCrawlGame.languagePack.getUIString("DailyScreen").TEXT;
        TEXT_2 = CardCrawlGame.languagePack.getUIString("LeaderboardsScreen").TEXT;
        TITLE_Y = (float)Settings.HEIGHT / 2.0F + 350F * Settings.scale;
        TIME_LEFT_Y = (float)Settings.HEIGHT / 2.0F + 310F * Settings.scale;
        CHAR_IMAGE_Y = (float)Settings.HEIGHT / 2.0F + 160F * Settings.scale;
        CHAR_HEADER_Y = (float)Settings.HEIGHT / 2.0F + 250F * Settings.scale;
        MOD_HEADER_Y = (float)Settings.HEIGHT / 2.0F + 136F * Settings.scale;
        MOD_LINE_W = 500F * Settings.scale;
        MOD_LINE_SPACING = 30F * Settings.scale;
        MOD_SECTION_SPACING = 60F * Settings.scale;
        RANK_X = 1000F * Settings.scale;
        NAME_X = 1160F * Settings.scale;
        SCORE_X = 1500F * Settings.scale;
        LINE_THICKNESS = 4F * Settings.scale;
    }
}
