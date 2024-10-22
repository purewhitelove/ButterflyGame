// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CharacterSelectScreen.java

package com.megacrit.cardcrawl.screens.charSelect;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.CharacterManager;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.integrations.SteelSeries;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.metrics.BotDataUploader;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.screens.mainMenu.*;
import com.megacrit.cardcrawl.ui.buttons.ConfirmButton;
import com.megacrit.cardcrawl.ui.panels.SeedPanel;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.unlock.misc.*;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.screens.charSelect:
//            CharacterOption

public class CharacterSelectScreen
{

    public CharacterSelectScreen()
    {
        seedPanel = new SeedPanel();
        confirmButton = new ConfirmButton(TEXT[1]);
        cancelButton = new MenuCancelButton();
        options = new ArrayList();
        anySelected = false;
        bgCharImg = null;
        bgCharColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        bg_y_offset = 0.0F;
        isAscensionMode = false;
        isAscensionModeUnlocked = false;
        ascensionLevel = 0;
        ascLevelInfoString = "";
    }

    public void initialize()
    {
        options.add(new CharacterOption(TEXT[2], CardCrawlGame.characterManager.recreateCharacter(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.IRONCLAD), ImageMaster.CHAR_SELECT_IRONCLAD, ImageMaster.CHAR_SELECT_BG_IRONCLAD));
        if(!UnlockTracker.isCharacterLocked("The Silent"))
            options.add(new CharacterOption(TEXT[3], CardCrawlGame.characterManager.recreateCharacter(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.THE_SILENT), ImageMaster.CHAR_SELECT_SILENT, ImageMaster.CHAR_SELECT_BG_SILENT));
        else
            options.add(new CharacterOption(CardCrawlGame.characterManager.recreateCharacter(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.THE_SILENT)));
        if(!UnlockTracker.isCharacterLocked("Defect"))
            options.add(new CharacterOption(TEXT[4], CardCrawlGame.characterManager.recreateCharacter(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.DEFECT), ImageMaster.CHAR_SELECT_DEFECT, ImageMaster.CHAR_SELECT_BG_DEFECT));
        else
            options.add(new CharacterOption(CardCrawlGame.characterManager.recreateCharacter(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.DEFECT)));
        if(!UnlockTracker.isCharacterLocked("Watcher"))
            addCharacterOption(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.WATCHER);
        else
            options.add(new CharacterOption(CardCrawlGame.characterManager.recreateCharacter(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.WATCHER)));
        positionButtons();
        isAscensionMode = Settings.gamePref.getBoolean("Ascension Mode Default", false);
        FontHelper.cardTitleFont.getData().setScale(1.0F);
        ASC_LEFT_W = FontHelper.getSmartWidth(FontHelper.cardTitleFont, TEXT[6], 9999F, 0.0F);
        ASC_RIGHT_W = FontHelper.getSmartWidth(FontHelper.cardTitleFont, (new StringBuilder()).append(TEXT[7]).append("22").toString(), 9999F, 0.0F);
        ascensionModeHb = new Hitbox(ASC_LEFT_W + 100F * Settings.scale, 70F * Settings.scale);
        if(!Settings.isMobile)
            ascensionModeHb.move((float)Settings.WIDTH / 2.0F - ASC_LEFT_W / 2.0F - 50F * Settings.scale, 70F * Settings.scale);
        else
            ascensionModeHb.move((float)Settings.WIDTH / 2.0F - ASC_LEFT_W / 2.0F - 50F * Settings.scale, 100F * Settings.scale);
        ascLeftHb = new Hitbox(70F * Settings.scale, 70F * Settings.scale);
        ascRightHb = new Hitbox(70F * Settings.scale, 70F * Settings.scale);
        seedHb = new Hitbox(700F * Settings.scale, 60F * Settings.scale);
        seedHb.move(90F * Settings.scale, 70F * Settings.scale);
    }

    private void addCharacterOption(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass c)
    {
        AbstractPlayer p = CardCrawlGame.characterManager.recreateCharacter(c);
        options.add(p.getCharacterSelectOption());
    }

    private void positionButtons()
    {
        int count = options.size();
        float offsetX = (float)Settings.WIDTH / 2.0F - 330F * Settings.scale;
        for(int i = 0; i < count; i++)
            if(Settings.isMobile)
                ((CharacterOption)options.get(i)).hb.move(offsetX + (float)i * 220F * Settings.scale, 230F * Settings.yScale);
            else
                ((CharacterOption)options.get(i)).hb.move(offsetX + (float)i * 220F * Settings.scale, 190F * Settings.yScale);

    }

    public void open(boolean isEndless)
    {
        Settings.isEndless = isEndless;
        Settings.seedSet = false;
        Settings.seed = null;
        Settings.specialSeed = null;
        Settings.isTrial = false;
        CardCrawlGame.trial = null;
        cancelButton.show(TEXT[5]);
        CardCrawlGame.mainMenuScreen.screen = com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen.CurScreen.CHAR_SELECT;
    }

    private void setRandomSeed()
    {
        long sourceTime = System.nanoTime();
        Random rng = new Random(Long.valueOf(sourceTime));
        Settings.seedSourceTimestamp = sourceTime;
        Settings.seed = Long.valueOf(SeedHelper.generateUnoffensiveSeed(rng));
        Settings.seedSet = false;
    }

    public void update()
    {
        if(ascLeftHb != null)
            if(!Settings.isMobile)
            {
                ascLeftHb.move(((float)Settings.WIDTH / 2.0F + 200F * Settings.scale) - ASC_RIGHT_W * 0.25F, 70F * Settings.scale);
                ascRightHb.move((float)Settings.WIDTH / 2.0F + 200F * Settings.scale + ASC_RIGHT_W * 1.25F, 70F * Settings.scale);
            } else
            {
                ascLeftHb.move(((float)Settings.WIDTH / 2.0F + 200F * Settings.scale) - ASC_RIGHT_W * 0.25F, 100F * Settings.scale);
                ascRightHb.move((float)Settings.WIDTH / 2.0F + 200F * Settings.scale + ASC_RIGHT_W * 1.25F, 100F * Settings.scale);
            }
        anySelected = false;
        if(!seedPanel.shown)
        {
            Iterator iterator = options.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                CharacterOption o = (CharacterOption)iterator.next();
                o.update();
                if(o.selected)
                {
                    anySelected = true;
                    isAscensionModeUnlocked = UnlockTracker.isAscensionUnlocked(o.c);
                    if(!isAscensionModeUnlocked)
                        isAscensionMode = false;
                }
            } while(true);
            updateButtons();
            if(InputHelper.justReleasedClickLeft && !anySelected)
            {
                confirmButton.isDisabled = true;
                confirmButton.hide();
            }
            if(anySelected)
            {
                bgCharColor.a = MathHelper.fadeLerpSnap(bgCharColor.a, 1.0F);
                bg_y_offset = MathHelper.fadeLerpSnap(bg_y_offset, -0F);
            } else
            {
                bgCharColor.a = MathHelper.fadeLerpSnap(bgCharColor.a, 0.0F);
            }
            updateAscensionToggle();
            if(anySelected)
                seedHb.update();
        }
        seedPanel.update();
        if(seedHb.hovered && InputHelper.justClickedLeft || CInputActionSet.drawPile.isJustPressed())
        {
            InputHelper.justClickedLeft = false;
            seedHb.hovered = false;
            seedPanel.show();
        }
        CardCrawlGame.mainMenuScreen.superDarken = anySelected;
    }

    private void updateAscensionToggle()
    {
label0:
        {
            CharacterOption o;
label1:
            {
                if(!isAscensionModeUnlocked)
                    break label0;
                if(anySelected)
                {
                    ascensionModeHb.update();
                    ascRightHb.update();
                    ascLeftHb.update();
                }
                if(InputHelper.justClickedLeft)
                    if(ascensionModeHb.hovered)
                        ascensionModeHb.clickStarted = true;
                    else
                    if(ascRightHb.hovered)
                        ascRightHb.clickStarted = true;
                    else
                    if(ascLeftHb.hovered)
                        ascLeftHb.clickStarted = true;
                if(ascensionModeHb.clicked || CInputActionSet.proceed.isJustPressed())
                {
                    ascensionModeHb.clicked = false;
                    isAscensionMode = !isAscensionMode;
                    Settings.gamePref.putBoolean("Ascension Mode Default", isAscensionMode);
                    Settings.gamePref.flush();
                }
                if(!ascLeftHb.clicked && !CInputActionSet.pageLeftViewDeck.isJustPressed())
                    break label1;
                ascLeftHb.clicked = false;
                Iterator iterator = options.iterator();
                do
                {
                    if(!iterator.hasNext())
                        break label1;
                    o = (CharacterOption)iterator.next();
                } while(!o.selected);
                o.decrementAscensionLevel(ascensionLevel - 1);
            }
            if(!ascRightHb.clicked && !CInputActionSet.pageRightViewExhaust.isJustPressed())
                break label0;
            ascRightHb.clicked = false;
            Iterator iterator1 = options.iterator();
            do
            {
                if(!iterator1.hasNext())
                    break label0;
                o = (CharacterOption)iterator1.next();
            } while(!o.selected);
            o.incrementAscensionLevel(ascensionLevel + 1);
        }
    }

    public void justSelected()
    {
        bg_y_offset = 0.0F;
    }

    public void updateButtons()
    {
        cancelButton.update();
        confirmButton.update();
        if(cancelButton.hb.clicked || InputHelper.pressedEscape)
        {
            CardCrawlGame.mainMenuScreen.superDarken = false;
            InputHelper.pressedEscape = false;
            cancelButton.hb.clicked = false;
            cancelButton.hide();
            CardCrawlGame.mainMenuScreen.panelScreen.refresh();
            for(Iterator iterator = options.iterator(); iterator.hasNext();)
            {
                CharacterOption o = (CharacterOption)iterator.next();
                o.selected = false;
            }

            bgCharColor.a = 0.0F;
            anySelected = false;
        }
        if(confirmButton.hb.clicked)
        {
            confirmButton.hb.clicked = false;
            confirmButton.isDisabled = true;
            confirmButton.hide();
            if(Settings.seed == null)
                setRandomSeed();
            else
                Settings.seedSet = true;
            CardCrawlGame.mainMenuScreen.isFadingOut = true;
            CardCrawlGame.mainMenuScreen.fadeOutMusic();
            Settings.isDailyRun = false;
            boolean isTrialSeed = TrialHelper.isTrialSeed(SeedHelper.getString(Settings.seed.longValue()));
            if(isTrialSeed)
            {
                Settings.specialSeed = Settings.seed;
                long sourceTime = System.nanoTime();
                Random rng = new Random(Long.valueOf(sourceTime));
                Settings.seed = Long.valueOf(SeedHelper.generateUnoffensiveSeed(rng));
                Settings.isTrial = true;
            }
            ModHelper.setModsFalse();
            AbstractDungeon.generateSeeds();
            AbstractDungeon.isAscensionMode = isAscensionMode;
            if(isAscensionMode)
                AbstractDungeon.ascensionLevel = ascensionLevel;
            else
                AbstractDungeon.ascensionLevel = 0;
            confirmButton.hb.clicked = false;
            confirmButton.hide();
            CharacterOption selected = null;
            Iterator iterator1 = options.iterator();
            do
            {
                if(!iterator1.hasNext())
                    break;
                CharacterOption o = (CharacterOption)iterator1.next();
                if(o.selected)
                    selected = o;
            } while(true);
            if(selected != null && CardCrawlGame.steelSeries.isEnabled.booleanValue())
                CardCrawlGame.steelSeries.event_character_chosen(selected.c.chosenClass);
            if(Settings.isDemo || Settings.isPublisherBuild)
            {
                BotDataUploader poster = new BotDataUploader();
                poster.setValues(com.megacrit.cardcrawl.metrics.BotDataUploader.GameDataType.DEMO_EMBARK, null, null);
                Thread t = new Thread(poster);
                t.setName("LeaderboardPoster");
                t.run();
            }
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(bgCharColor);
        if(bgCharImg != null)
            if(Settings.isSixteenByTen)
                sb.draw(bgCharImg, (float)Settings.WIDTH / 2.0F - 960F, (float)Settings.HEIGHT / 2.0F - 600F, 960F, 600F, 1920F, 1200F, Settings.scale, Settings.scale, 0.0F, 0, 0, 1920, 1200, false, false);
            else
            if(Settings.isFourByThree)
                sb.draw(bgCharImg, (float)Settings.WIDTH / 2.0F - 960F, ((float)Settings.HEIGHT / 2.0F - 600F) + bg_y_offset, 960F, 600F, 1920F, 1200F, Settings.yScale, Settings.yScale, 0.0F, 0, 0, 1920, 1200, false, false);
            else
            if(Settings.isLetterbox)
                sb.draw(bgCharImg, (float)Settings.WIDTH / 2.0F - 960F, ((float)Settings.HEIGHT / 2.0F - 600F) + bg_y_offset, 960F, 600F, 1920F, 1200F, Settings.xScale, Settings.xScale, 0.0F, 0, 0, 1920, 1200, false, false);
            else
                sb.draw(bgCharImg, (float)Settings.WIDTH / 2.0F - 960F, ((float)Settings.HEIGHT / 2.0F - 600F) + bg_y_offset, 960F, 600F, 1920F, 1200F, Settings.scale, Settings.scale, 0.0F, 0, 0, 1920, 1200, false, false);
        cancelButton.render(sb);
        confirmButton.render(sb);
        renderAscensionMode(sb);
        renderSeedSettings(sb);
        seedPanel.render(sb);
        boolean anythingSelected = false;
        if(!seedPanel.shown)
        {
            CharacterOption o;
            for(Iterator iterator = options.iterator(); iterator.hasNext(); o.render(sb))
            {
                o = (CharacterOption)iterator.next();
                if(o.selected)
                    anythingSelected = true;
            }

        }
        if(!seedPanel.shown && !anythingSelected)
            if(!Settings.isMobile)
                FontHelper.renderFontCentered(sb, FontHelper.losePowerFont, CHOOSE_CHAR_MSG, (float)Settings.WIDTH / 2.0F, 340F * Settings.yScale, Settings.CREAM_COLOR, 1.2F);
            else
                FontHelper.renderFontCentered(sb, FontHelper.losePowerFont, CHOOSE_CHAR_MSG, (float)Settings.WIDTH / 2.0F, 380F * Settings.yScale, Settings.CREAM_COLOR, 1.2F);
    }

    private void renderSeedSettings(SpriteBatch sb)
    {
        if(!anySelected)
            return;
        Color textColor = Settings.GOLD_COLOR;
        if(seedHb.hovered)
        {
            textColor = Settings.GREEN_TEXT_COLOR;
            TipHelper.renderGenericTip((float)InputHelper.mX + 50F * Settings.scale, (float)InputHelper.mY + 100F * Settings.scale, TEXT[11], TEXT[12]);
        }
        if(!Settings.isControllerMode)
        {
            if(Settings.seedSet)
            {
                FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, TEXT[10], SEED_X, SEED_Y, 9999F, 0.0F, textColor);
                FontHelper.renderFontLeftTopAligned(sb, FontHelper.cardTitleFont, SeedHelper.getUserFacingSeedString(), (SEED_X - 30F * Settings.scale) + FontHelper.getSmartWidth(FontHelper.cardTitleFont, TEXT[13], 9999F, 0.0F), 90F * Settings.scale, Settings.BLUE_TEXT_COLOR);
            } else
            {
                FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, TEXT[13], SEED_X, SEED_Y, 9999F, 0.0F, textColor);
            }
        } else
        {
            if(Settings.seedSet)
            {
                FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, TEXT[10], SEED_X + 100F * Settings.scale, SEED_Y, 9999F, 0.0F, textColor);
                FontHelper.renderFontLeftTopAligned(sb, FontHelper.cardTitleFont, SeedHelper.getUserFacingSeedString(), (SEED_X - 30F * Settings.scale) + FontHelper.getSmartWidth(FontHelper.cardTitleFont, TEXT[13], 9999F, 0.0F) + 100F * Settings.scale, 90F * Settings.scale, Settings.BLUE_TEXT_COLOR);
            } else
            {
                FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, TEXT[13], SEED_X + 100F * Settings.scale, SEED_Y, 9999F, 0.0F, textColor);
            }
            sb.draw(ImageMaster.CONTROLLER_LT, 80F * Settings.scale - 32F, 80F * Settings.scale - 32F, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        }
        seedHb.render(sb);
    }

    private void renderAscensionMode(SpriteBatch sb)
    {
        if(!anySelected)
            return;
        if(isAscensionModeUnlocked)
        {
            if(!Settings.isMobile)
                sb.draw(ImageMaster.OPTION_TOGGLE, (float)Settings.WIDTH / 2.0F - ASC_LEFT_W - 16F - 30F * Settings.scale, ascensionModeHb.cY - 16F, 16F, 16F, 32F, 32F, Settings.scale, Settings.scale, 0.0F, 0, 0, 32, 32, false, false);
            else
                sb.draw(ImageMaster.CHECKBOX, (float)Settings.WIDTH / 2.0F - ASC_LEFT_W - 36F * Settings.scale - 32F, ascensionModeHb.cY - 32F, 32F, 32F, 64F, 64F, Settings.scale * 0.9F, Settings.scale * 0.9F, 0.0F, 0, 0, 64, 64, false, false);
            if(ascensionModeHb.hovered)
            {
                FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, TEXT[6], (float)Settings.WIDTH / 2.0F - ASC_LEFT_W / 2.0F, ascensionModeHb.cY, Settings.GREEN_TEXT_COLOR);
                TipHelper.renderGenericTip((float)InputHelper.mX - 140F * Settings.scale, (float)InputHelper.mY + 340F * Settings.scale, TEXT[8], TEXT[9]);
            } else
            {
                FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, TEXT[6], (float)Settings.WIDTH / 2.0F - ASC_LEFT_W / 2.0F, ascensionModeHb.cY, Settings.GOLD_COLOR);
            }
            FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, (new StringBuilder()).append(TEXT[7]).append(ascensionLevel).toString(), (float)Settings.WIDTH / 2.0F + ASC_RIGHT_W / 2.0F + 200F * Settings.scale, ascensionModeHb.cY, Settings.BLUE_TEXT_COLOR);
            if(isAscensionMode)
            {
                sb.setColor(Color.WHITE);
                if(!Settings.isMobile)
                    sb.draw(ImageMaster.OPTION_TOGGLE_ON, (float)Settings.WIDTH / 2.0F - ASC_LEFT_W - 16F - 30F * Settings.scale, ascensionModeHb.cY - 16F, 16F, 16F, 32F, 32F, Settings.scale, Settings.scale, 0.0F, 0, 0, 32, 32, false, false);
                else
                    sb.draw(ImageMaster.TICK, (float)Settings.WIDTH / 2.0F - ASC_LEFT_W - 36F * Settings.scale - 32F, ascensionModeHb.cY - 32F, 32F, 32F, 64F, 64F, Settings.scale * 0.9F, Settings.scale * 0.9F, 0.0F, 0, 0, 64, 64, false, false);
                if(Settings.isMobile)
                    FontHelper.renderFontCentered(sb, FontHelper.smallDialogOptionFont, ascLevelInfoString, (float)Settings.WIDTH / 2.0F, 60F * Settings.scale, Settings.CREAM_COLOR);
                else
                    FontHelper.renderFontCentered(sb, FontHelper.cardDescFont_N, ascLevelInfoString, (float)Settings.WIDTH / 2.0F, 35F * Settings.scale, Settings.CREAM_COLOR);
            }
            if(ascLeftHb.hovered || Settings.isControllerMode)
                sb.setColor(Color.WHITE);
            else
                sb.setColor(Color.LIGHT_GRAY);
            sb.draw(ImageMaster.CF_LEFT_ARROW, ascLeftHb.cX - 24F, ascLeftHb.cY - 24F, 24F, 24F, 48F, 48F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
            if(ascRightHb.hovered || Settings.isControllerMode)
                sb.setColor(Color.WHITE);
            else
                sb.setColor(Color.LIGHT_GRAY);
            sb.draw(ImageMaster.CF_RIGHT_ARROW, ascRightHb.cX - 24F, ascRightHb.cY - 24F, 24F, 24F, 48F, 48F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
            if(Settings.isControllerMode)
            {
                sb.draw(CInputActionSet.proceed.getKeyImg(), ascensionModeHb.cX - 100F * Settings.scale - 32F, ascensionModeHb.cY - 32F, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
                sb.draw(CInputActionSet.pageLeftViewDeck.getKeyImg(), ascLeftHb.cX - 60F * Settings.scale - 32F, ascLeftHb.cY - 32F, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
                sb.draw(CInputActionSet.pageRightViewExhaust.getKeyImg(), (ascRightHb.cX + 60F * Settings.scale) - 32F, ascRightHb.cY - 32F, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
            }
            ascensionModeHb.render(sb);
            ascLeftHb.render(sb);
            ascRightHb.render(sb);
        }
    }

    public void deselectOtherOptions(CharacterOption characterOption)
    {
        Iterator iterator = options.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            CharacterOption o = (CharacterOption)iterator.next();
            if(o != characterOption)
                o.selected = false;
        } while(true);
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private static final UIStrings uiStrings2;
    public static final String A_TEXT[];
    private static float ASC_LEFT_W;
    private static float ASC_RIGHT_W;
    private SeedPanel seedPanel;
    private static final float SEED_X;
    private static final float SEED_Y;
    private static final String CHOOSE_CHAR_MSG;
    public ConfirmButton confirmButton;
    public MenuCancelButton cancelButton;
    public ArrayList options;
    private boolean anySelected;
    public Texture bgCharImg;
    private Color bgCharColor;
    private static final float BG_Y_OFFSET_TARGET = 0F;
    private float bg_y_offset;
    public boolean isAscensionMode;
    private boolean isAscensionModeUnlocked;
    private Hitbox ascensionModeHb;
    private Hitbox ascLeftHb;
    private Hitbox ascRightHb;
    private Hitbox seedHb;
    public int ascensionLevel;
    public String ascLevelInfoString;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("CharacterSelectScreen");
        TEXT = uiStrings.TEXT;
        uiStrings2 = CardCrawlGame.languagePack.getUIString("AscensionModeDescriptions");
        A_TEXT = uiStrings2.TEXT;
        SEED_X = 60F * Settings.scale;
        SEED_Y = 90F * Settings.scale;
        CHOOSE_CHAR_MSG = TEXT[0];
    }
}
