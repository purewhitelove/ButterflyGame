// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LeaderboardScreen.java

package com.megacrit.cardcrawl.screens.leaderboards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.integrations.PublisherIntegration;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.mainMenu.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.unlock.misc.DefectUnlock;
import com.megacrit.cardcrawl.unlock.misc.WatcherUnlock;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.screens.leaderboards:
//            FilterButton, LeaderboardEntry

public class LeaderboardScreen
{
    private static final class LeaderboardSelectionType extends Enum
    {

        public static LeaderboardSelectionType[] values()
        {
            return (LeaderboardSelectionType[])$VALUES.clone();
        }

        public static LeaderboardSelectionType valueOf(String name)
        {
            return (LeaderboardSelectionType)Enum.valueOf(com/megacrit/cardcrawl/screens/leaderboards/LeaderboardScreen$LeaderboardSelectionType, name);
        }

        public static final LeaderboardSelectionType NONE;
        public static final LeaderboardSelectionType CHAR;
        public static final LeaderboardSelectionType REGION;
        public static final LeaderboardSelectionType TYPE;
        private static final LeaderboardSelectionType $VALUES[];

        static 
        {
            NONE = new LeaderboardSelectionType("NONE", 0);
            CHAR = new LeaderboardSelectionType("CHAR", 1);
            REGION = new LeaderboardSelectionType("REGION", 2);
            TYPE = new LeaderboardSelectionType("TYPE", 3);
            $VALUES = (new LeaderboardSelectionType[] {
                NONE, CHAR, REGION, TYPE
            });
        }

        private LeaderboardSelectionType(String s, int i)
        {
            super(s, i);
        }
    }


    public LeaderboardScreen()
    {
        button = new MenuCancelButton();
        screenUp = false;
        waiting = true;
        refresh = true;
        entries = new ArrayList();
        charButtons = new ArrayList();
        regionButtons = new ArrayList();
        typeButtons = new ArrayList();
        charLabel = TEXT[2];
        regionLabel = TEXT[3];
        typeLabel = TEXT[4];
        viewMyScoreHb = new Hitbox(250F * Settings.scale, 80F * Settings.scale);
        viewMyScore = false;
        lineFadeInTimer = 0.0F;
        prevHb = new Hitbox(110F * Settings.scale, 110F * Settings.scale);
        prevHb.move(880F * Settings.scale, 530F * Settings.scale);
        nextHb = new Hitbox(110F * Settings.scale, 110F * Settings.scale);
        nextHb.move(1740F * Settings.scale, 530F * Settings.scale);
        viewMyScoreHb.move(1300F * Settings.scale, 80F * Settings.scale);
    }

    public void update()
    {
        updateControllerInput();
        FilterButton b;
        for(Iterator iterator = charButtons.iterator(); iterator.hasNext(); b.update())
            b = (FilterButton)iterator.next();

        FilterButton b;
        for(Iterator iterator1 = regionButtons.iterator(); iterator1.hasNext(); b.update())
            b = (FilterButton)iterator1.next();

        FilterButton b;
        for(Iterator iterator2 = typeButtons.iterator(); iterator2.hasNext(); b.update())
            b = (FilterButton)iterator2.next();

        button.update();
        if(button.hb.clicked || InputHelper.pressedEscape)
        {
            InputHelper.pressedEscape = false;
            hide();
        }
        if(!entries.isEmpty() && !waiting)
            lineFadeInTimer = MathHelper.slowColorLerpSnap(lineFadeInTimer, 1.0F);
        if(refresh)
        {
            refresh = false;
            waiting = true;
            lineFadeInTimer = 0.0F;
            currentStartIndex = 1;
            currentEndIndex = 20;
            getData(currentStartIndex, currentEndIndex);
        }
        updateViewMyScore();
        updateArrows();
    }

    private void updateControllerInput()
    {
        if(!Settings.isControllerMode)
            return;
        LeaderboardSelectionType type = LeaderboardSelectionType.NONE;
        boolean anyHovered = false;
        int index = 0;
        Iterator iterator = charButtons.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            FilterButton b = (FilterButton)iterator.next();
            if(b.hb.hovered)
            {
                anyHovered = true;
                type = LeaderboardSelectionType.CHAR;
                break;
            }
            index++;
        } while(true);
        if(!anyHovered)
        {
            index = 0;
            Iterator iterator1 = regionButtons.iterator();
            do
            {
                if(!iterator1.hasNext())
                    break;
                FilterButton b = (FilterButton)iterator1.next();
                if(b.hb.hovered)
                {
                    anyHovered = true;
                    type = LeaderboardSelectionType.REGION;
                    break;
                }
                index++;
            } while(true);
        }
        if(!anyHovered)
        {
            index = 0;
            Iterator iterator2 = typeButtons.iterator();
            do
            {
                if(!iterator2.hasNext())
                    break;
                FilterButton b = (FilterButton)iterator2.next();
                if(b.hb.hovered)
                {
                    anyHovered = true;
                    type = LeaderboardSelectionType.TYPE;
                    break;
                }
                index++;
            } while(true);
        }
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$screens$leaderboards$LeaderboardScreen$LeaderboardSelectionType[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$screens$leaderboards$LeaderboardScreen$LeaderboardSelectionType = new int[LeaderboardSelectionType.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$leaderboards$LeaderboardScreen$LeaderboardSelectionType[LeaderboardSelectionType.CHAR.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$leaderboards$LeaderboardScreen$LeaderboardSelectionType[LeaderboardSelectionType.REGION.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$leaderboards$LeaderboardScreen$LeaderboardSelectionType[LeaderboardSelectionType.TYPE.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
            }
        }

        if(!anyHovered)
            Gdx.input.setCursorPosition((int)((FilterButton)charButtons.get(0)).hb.cX, Settings.HEIGHT - (int)((FilterButton)charButtons.get(0)).hb.cY);
        else
            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.screens.leaderboards.LeaderboardScreen.LeaderboardSelectionType[type.ordinal()])
            {
            default:
                break;

            case 1: // '\001'
                if(CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
                {
                    if(--index < 0)
                        return;
                    Gdx.input.setCursorPosition((int)((FilterButton)charButtons.get(index)).hb.cX, Settings.HEIGHT - (int)((FilterButton)charButtons.get(index)).hb.cY);
                    break;
                }
                if(CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed())
                {
                    if(++index > charButtons.size() - 1)
                        return;
                    Gdx.input.setCursorPosition((int)((FilterButton)charButtons.get(index)).hb.cX, Settings.HEIGHT - (int)((FilterButton)charButtons.get(index)).hb.cY);
                    break;
                }
                if(!CInputActionSet.down.isJustPressed() && !CInputActionSet.altDown.isJustPressed())
                    break;
                if(index > regionButtons.size() - 1)
                    index = regionButtons.size() - 1;
                Gdx.input.setCursorPosition((int)((FilterButton)regionButtons.get(index)).hb.cX, Settings.HEIGHT - (int)((FilterButton)regionButtons.get(index)).hb.cY);
                break;

            case 2: // '\002'
                if(CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
                {
                    if(--index < 0)
                        return;
                    CInputHelper.setCursor(((FilterButton)regionButtons.get(index)).hb);
                    break;
                }
                if(CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed())
                {
                    if(++index > regionButtons.size() - 1)
                        return;
                    CInputHelper.setCursor(((FilterButton)regionButtons.get(index)).hb);
                    break;
                }
                if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
                {
                    if(index > typeButtons.size() - 1)
                        index = typeButtons.size() - 1;
                    CInputHelper.setCursor(((FilterButton)typeButtons.get(index)).hb);
                    break;
                }
                if(!CInputActionSet.up.isJustPressed() && !CInputActionSet.altUp.isJustPressed())
                    break;
                if(index > charButtons.size() - 1)
                    index = charButtons.size() - 1;
                CInputHelper.setCursor(((FilterButton)charButtons.get(index)).hb);
                break;

            case 3: // '\003'
                if(CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
                {
                    if(--index < 0)
                        return;
                    CInputHelper.setCursor(((FilterButton)typeButtons.get(index)).hb);
                    break;
                }
                if(CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed())
                {
                    if(++index > typeButtons.size() - 1)
                        return;
                    CInputHelper.setCursor(((FilterButton)typeButtons.get(index)).hb);
                    break;
                }
                if(!CInputActionSet.up.isJustPressed() && !CInputActionSet.altUp.isJustPressed())
                    break;
                if(index > regionButtons.size() - 1)
                    index = regionButtons.size() - 1;
                CInputHelper.setCursor(((FilterButton)regionButtons.get(index)).hb);
                break;
            }
    }

    private void updateViewMyScore()
    {
        if(((FilterButton)regionButtons.get(0)).active)
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

    private void getData(int startIndex, int endIndex)
    {
        com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass tmpClass = null;
        FilterButton.RegionSetting rSetting = null;
        FilterButton.LeaderboardType lType = null;
        Iterator iterator = charButtons.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            FilterButton b = (FilterButton)iterator.next();
            if(!b.active)
                continue;
            tmpClass = b.pClass;
            break;
        } while(true);
        iterator = regionButtons.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            FilterButton b = (FilterButton)iterator.next();
            if(!b.active)
                continue;
            rSetting = b.rType;
            break;
        } while(true);
        iterator = typeButtons.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            FilterButton b = (FilterButton)iterator.next();
            if(!b.active)
                continue;
            lType = b.lType;
            break;
        } while(true);
        if(tmpClass != null && rSetting != null && lType != null)
            CardCrawlGame.publisherIntegration.getLeaderboardEntries(tmpClass, rSetting, lType, startIndex, endIndex);
    }

    public void open()
    {
        CardCrawlGame.mainMenuScreen.screen = com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen.CurScreen.LEADERBOARD;
        if(charButtons.isEmpty())
        {
            charButtons.add(new FilterButton("ironclad.png", true, com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.IRONCLAD));
            charButtons.add(new FilterButton("silent.png", false, com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.THE_SILENT));
            if(!UnlockTracker.isCharacterLocked("Defect"))
                charButtons.add(new FilterButton("defect.png", false, com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.DEFECT));
            if(!UnlockTracker.isCharacterLocked("Watcher"))
                charButtons.add(new FilterButton("watcher.png", false, com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.WATCHER));
            regionButtons.add(new FilterButton("friends.png", true, FilterButton.RegionSetting.FRIEND));
            regionButtons.add(new FilterButton("global.png", false, FilterButton.RegionSetting.GLOBAL));
            typeButtons.add(new FilterButton("score.png", true, FilterButton.LeaderboardType.HIGH_SCORE));
            typeButtons.add(new FilterButton("chain.png", false, FilterButton.LeaderboardType.CONSECUTIVE_WINS));
            typeButtons.add(new FilterButton("time.png", false, FilterButton.LeaderboardType.FASTEST_WIN));
        }
        button.show(TEXT[0]);
        screenUp = true;
    }

    public void hide()
    {
        CardCrawlGame.sound.play("DECK_CLOSE", 0.1F);
        button.hide();
        screenUp = false;
        FontHelper.ClearLeaderboardFontTextures();
        CardCrawlGame.mainMenuScreen.panelScreen.refresh();
    }

    public void render(SpriteBatch sb)
    {
        renderScoreHeaders(sb);
        renderScores(sb);
        renderViewMyScoreBox(sb);
        renderFilters(sb);
        renderArrows(sb);
        button.render(sb);
    }

    private void renderFilters(SpriteBatch sb)
    {
        FontHelper.renderFontLeftTopAligned(sb, FontHelper.charTitleFont, TEXT[1], 240F * Settings.scale, 920F * Settings.scale, Settings.GOLD_COLOR);
        FontHelper.renderFontLeftTopAligned(sb, FontHelper.panelNameFont, charLabel, 280F * Settings.scale, 860F * Settings.scale, Settings.CREAM_COLOR);
        FontHelper.renderFontLeftTopAligned(sb, FontHelper.panelNameFont, regionLabel, 280F * Settings.scale, 680F * Settings.scale, Settings.CREAM_COLOR);
        FontHelper.renderFontLeftTopAligned(sb, FontHelper.panelNameFont, typeLabel, 280F * Settings.scale, 500F * Settings.scale, Settings.CREAM_COLOR);
        for(int i = 0; i < charButtons.size(); i++)
            ((FilterButton)charButtons.get(i)).render(sb, 340F * Settings.scale + (float)i * FILTER_SPACING_X, 780F * Settings.scale);

        for(int i = 0; i < regionButtons.size(); i++)
            ((FilterButton)regionButtons.get(i)).render(sb, 340F * Settings.scale + (float)i * FILTER_SPACING_X, 600F * Settings.scale);

        for(int i = 0; i < typeButtons.size(); i++)
            ((FilterButton)typeButtons.get(i)).render(sb, 340F * Settings.scale + (float)i * FILTER_SPACING_X, 420F * Settings.scale);

    }

    private void renderArrows(SpriteBatch sb)
    {
        boolean renderLeftArrow = true;
        Iterator iterator = regionButtons.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            FilterButton b = (FilterButton)iterator.next();
            if(b.rType == FilterButton.RegionSetting.FRIEND && entries.size() < 20)
                renderLeftArrow = false;
        } while(true);
        if(currentStartIndex != 1 && renderLeftArrow)
        {
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, lineFadeInTimer));
            sb.draw(ImageMaster.POPUP_ARROW, prevHb.cX - 128F, prevHb.cY - 128F, 128F, 128F, 256F, 256F, Settings.scale * 0.75F, Settings.scale * 0.75F, 0.0F, 0, 0, 256, 256, false, false);
            if(prevHb.hovered)
            {
                sb.setBlendFunction(770, 1);
                sb.setColor(new Color(1.0F, 1.0F, 1.0F, lineFadeInTimer / 2.0F));
                sb.draw(ImageMaster.POPUP_ARROW, prevHb.cX - 128F, prevHb.cY - 128F, 128F, 128F, 256F, 256F, Settings.scale * 0.75F, Settings.scale * 0.75F, 0.0F, 0, 0, 256, 256, false, false);
                sb.setBlendFunction(770, 771);
            }
            if(Settings.isControllerMode)
                sb.draw(CInputActionSet.pageLeftViewDeck.getKeyImg(), (prevHb.cX - 32F) + 0.0F * Settings.scale, prevHb.cY - 32F - 100F * Settings.scale, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
            prevHb.render(sb);
        }
        if(entries.size() == 20)
        {
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, lineFadeInTimer));
            sb.draw(ImageMaster.POPUP_ARROW, nextHb.cX - 128F, nextHb.cY - 128F, 128F, 128F, 256F, 256F, Settings.scale * 0.75F, Settings.scale * 0.75F, 0.0F, 0, 0, 256, 256, true, false);
            if(nextHb.hovered)
            {
                sb.setBlendFunction(770, 1);
                sb.setColor(new Color(1.0F, 1.0F, 1.0F, lineFadeInTimer / 2.0F));
                sb.draw(ImageMaster.POPUP_ARROW, nextHb.cX - 128F, nextHb.cY - 128F, 128F, 128F, 256F, 256F, Settings.scale * 0.75F, Settings.scale * 0.75F, 0.0F, 0, 0, 256, 256, true, false);
                sb.setBlendFunction(770, 771);
            }
            if(Settings.isControllerMode)
                sb.draw(CInputActionSet.pageRightViewExhaust.getKeyImg(), (nextHb.cX - 32F) + 0.0F * Settings.scale, nextHb.cY - 32F - 100F * Settings.scale, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
            nextHb.render(sb);
        }
    }

    private void renderScoreHeaders(SpriteBatch sb)
    {
        Color creamColor = new Color(1.0F, 0.965F, 0.886F, lineFadeInTimer);
        FontHelper.renderFontLeftTopAligned(sb, FontHelper.charTitleFont, TEXT[10], 960F * Settings.scale, 920F * Settings.scale, new Color(0.937F, 0.784F, 0.317F, lineFadeInTimer));
        FontHelper.renderFontLeftTopAligned(sb, FontHelper.panelNameFont, TEXT[6], RANK_X, 860F * Settings.scale, creamColor);
        FontHelper.renderFontLeftTopAligned(sb, FontHelper.panelNameFont, TEXT[7], NAME_X, 860F * Settings.scale, creamColor);
        FontHelper.renderFontLeftTopAligned(sb, FontHelper.panelNameFont, TEXT[8], SCORE_X, 860F * Settings.scale, creamColor);
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
        if(((FilterButton)regionButtons.get(0)).active || waiting)
            return;
        if(viewMyScoreHb.hovered)
            FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, TEXT[5], 1310F * Settings.scale, 80F * Settings.scale, Settings.GREEN_TEXT_COLOR);
        else
            FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, TEXT[5], 1310F * Settings.scale, 80F * Settings.scale, Settings.GOLD_COLOR);
        if(Settings.isControllerMode)
            sb.draw(CInputActionSet.topPanel.getKeyImg(), 1270F * Settings.scale - 32F - FontHelper.getSmartWidth(FontHelper.cardTitleFont, TEXT[5], 9999F, 0.0F) / 2.0F, -32F + 80F * Settings.scale, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        viewMyScoreHb.render(sb);
    }

    private void renderScores(SpriteBatch sb)
    {
        if(!waiting)
        {
            if(entries.isEmpty())
            {
                FontHelper.renderFontCentered(sb, FontHelper.panelNameFont, TEXT[12], 1300F * Settings.scale, 540F * Settings.scale, Settings.GOLD_COLOR);
            } else
            {
                for(int i = 0; i < entries.size(); i++)
                    ((LeaderboardEntry)entries.get(i)).render(sb, i);

            }
        } else
        if(CardCrawlGame.publisherIntegration.isInitialized())
            FontHelper.renderFontCentered(sb, FontHelper.panelNameFont, TEXT[9], 1300F * Settings.scale, 540F * Settings.scale, Settings.GOLD_COLOR);
        else
            FontHelper.renderFontCentered(sb, FontHelper.panelNameFont, TEXT[11], 1300F * Settings.scale, 540F * Settings.scale, Settings.RED_TEXT_COLOR);
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    public MenuCancelButton button;
    public boolean screenUp;
    public boolean waiting;
    public boolean refresh;
    public ArrayList entries;
    public ArrayList charButtons;
    public ArrayList regionButtons;
    public ArrayList typeButtons;
    public static final float RANK_X;
    public static final float NAME_X;
    public static final float SCORE_X;
    public String charLabel;
    public String regionLabel;
    public String typeLabel;
    public int currentStartIndex;
    public int currentEndIndex;
    private static final float FILTER_SPACING_X;
    private Hitbox prevHb;
    private Hitbox nextHb;
    private Hitbox viewMyScoreHb;
    public boolean viewMyScore;
    private float lineFadeInTimer;
    private static final float LINE_THICKNESS;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("LeaderboardsScreen");
        TEXT = uiStrings.TEXT;
        RANK_X = 1000F * Settings.scale;
        NAME_X = 1160F * Settings.scale;
        SCORE_X = 1500F * Settings.scale;
        FILTER_SPACING_X = 100F * Settings.scale;
        LINE_THICKNESS = 4F * Settings.scale;
    }
}
