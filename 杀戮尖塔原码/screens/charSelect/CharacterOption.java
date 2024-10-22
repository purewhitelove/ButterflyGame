// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CharacterOption.java

package com.megacrit.cardcrawl.screens.charSelect;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import com.megacrit.cardcrawl.screens.stats.CharStat;
import com.megacrit.cardcrawl.ui.buttons.ConfirmButton;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.screens.charSelect:
//            CharacterSelectScreen

public class CharacterOption
{

    public CharacterOption(String optionName, AbstractPlayer c, Texture buttonImg, Texture portraitImg)
    {
        selected = false;
        locked = false;
        glowColor = new Color(1.0F, 0.8F, 0.2F, 0.0F);
        infoX = START_INFO_X;
        infoY = (float)Settings.HEIGHT / 2.0F;
        name = "";
        maxAscensionLevel = 1;
        name = optionName;
        hb = new Hitbox(HB_W, HB_W);
        this.buttonImg = buttonImg;
        this.portraitImg = portraitImg;
        this.c = c;
        charInfo = null;
        charInfo = c.getLoadout();
        hp = charInfo.hp;
        gold = charInfo.gold;
        flavorText = charInfo.flavorText;
        unlocksRemaining = 5 - UnlockTracker.getUnlockLevel(c.chosenClass);
    }

    public CharacterOption(String optionName, AbstractPlayer c, String buttonUrl, String portraitImg)
    {
        selected = false;
        locked = false;
        glowColor = new Color(1.0F, 0.8F, 0.2F, 0.0F);
        infoX = START_INFO_X;
        infoY = (float)Settings.HEIGHT / 2.0F;
        name = "";
        maxAscensionLevel = 1;
        name = optionName;
        hb = new Hitbox(HB_W, HB_W);
        buttonImg = ImageMaster.loadImage((new StringBuilder()).append("images/ui/charSelect/").append(buttonUrl).toString());
        portraitUrl = c.getPortraitImageName();
        this.c = c;
        charInfo = null;
        charInfo = c.getLoadout();
        hp = charInfo.hp;
        gold = charInfo.gold;
        flavorText = charInfo.flavorText;
        unlocksRemaining = 5 - UnlockTracker.getUnlockLevel(c.chosenClass);
    }

    public CharacterOption(AbstractPlayer c)
    {
        selected = false;
        locked = false;
        glowColor = new Color(1.0F, 0.8F, 0.2F, 0.0F);
        infoX = START_INFO_X;
        infoY = (float)Settings.HEIGHT / 2.0F;
        name = "";
        maxAscensionLevel = 1;
        hb = new Hitbox(HB_W, HB_W);
        buttonImg = ImageMaster.CHAR_SELECT_LOCKED;
        locked = true;
        this.c = c;
    }

    public void saveChosenAscensionLevel(int level)
    {
        Prefs pref = c.getPrefs();
        pref.putInteger("LAST_ASCENSION_LEVEL", level);
        pref.flush();
    }

    public void incrementAscensionLevel(int level)
    {
        if(level > maxAscensionLevel)
        {
            return;
        } else
        {
            saveChosenAscensionLevel(level);
            CardCrawlGame.mainMenuScreen.charSelectScreen.ascensionLevel = level;
            CardCrawlGame.mainMenuScreen.charSelectScreen.ascLevelInfoString = CharacterSelectScreen.A_TEXT[level - 1];
            return;
        }
    }

    public void decrementAscensionLevel(int level)
    {
        if(level == 0)
        {
            return;
        } else
        {
            saveChosenAscensionLevel(level);
            CardCrawlGame.mainMenuScreen.charSelectScreen.ascensionLevel = level;
            CardCrawlGame.mainMenuScreen.charSelectScreen.ascLevelInfoString = CharacterSelectScreen.A_TEXT[level - 1];
            return;
        }
    }

    public void update()
    {
        updateHitbox();
        updateInfoPosition();
    }

    private void updateHitbox()
    {
        hb.update();
        if(hb.justHovered)
            CardCrawlGame.sound.playA("UI_HOVER", -0.3F);
        if(hb.hovered && locked)
            if(c.chosenClass == com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.THE_SILENT)
                TipHelper.renderGenericTip((float)InputHelper.mX + 70F * Settings.xScale, (float)InputHelper.mY - 10F * Settings.scale, TEXT[0], TEXT[1]);
            else
            if(c.chosenClass == com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.DEFECT)
                TipHelper.renderGenericTip((float)InputHelper.mX + 70F * Settings.xScale, (float)InputHelper.mY - 10F * Settings.scale, TEXT[0], TEXT[3]);
            else
            if(c.chosenClass == com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.WATCHER)
                TipHelper.renderGenericTip((float)InputHelper.mX + 70F * Settings.xScale, (float)InputHelper.mY - 10F * Settings.scale, TEXT[0], TEXT[10]);
        if(InputHelper.justClickedLeft && !locked && hb.hovered)
        {
            CardCrawlGame.sound.playA("UI_CLICK_1", -0.4F);
            hb.clickStarted = true;
        }
        if(hb.clicked)
        {
            hb.clicked = false;
            if(!selected)
            {
                CardCrawlGame.mainMenuScreen.charSelectScreen.deselectOtherOptions(this);
                selected = true;
                CardCrawlGame.mainMenuScreen.charSelectScreen.justSelected();
                CardCrawlGame.chosenCharacter = c.chosenClass;
                CardCrawlGame.mainMenuScreen.charSelectScreen.confirmButton.isDisabled = false;
                CardCrawlGame.mainMenuScreen.charSelectScreen.confirmButton.show();
                if(portraitUrl != null)
                    CardCrawlGame.mainMenuScreen.charSelectScreen.bgCharImg = ImageMaster.loadImage((new StringBuilder()).append("images/ui/charSelect/").append(portraitUrl).toString());
                else
                    CardCrawlGame.mainMenuScreen.charSelectScreen.bgCharImg = portraitImg;
                Prefs pref = c.getPrefs();
                if(!locked)
                    c.doCharSelectScreenSelectEffect();
                if(pref != null)
                {
                    CardCrawlGame.mainMenuScreen.charSelectScreen.ascensionLevel = pref.getInteger("LAST_ASCENSION_LEVEL", 1);
                    if(20 < CardCrawlGame.mainMenuScreen.charSelectScreen.ascensionLevel)
                        CardCrawlGame.mainMenuScreen.charSelectScreen.ascensionLevel = 20;
                    maxAscensionLevel = pref.getInteger("ASCENSION_LEVEL", 1);
                    if(20 < maxAscensionLevel)
                        maxAscensionLevel = 20;
                    int ascensionLevel = CardCrawlGame.mainMenuScreen.charSelectScreen.ascensionLevel;
                    if(ascensionLevel > maxAscensionLevel)
                        ascensionLevel = maxAscensionLevel;
                    if(ascensionLevel > 0)
                        CardCrawlGame.mainMenuScreen.charSelectScreen.ascLevelInfoString = CharacterSelectScreen.A_TEXT[ascensionLevel - 1];
                    else
                        CardCrawlGame.mainMenuScreen.charSelectScreen.ascLevelInfoString = "";
                }
            }
        }
    }

    private void updateInfoPosition()
    {
        if(selected)
            infoX = MathHelper.uiLerpSnap(infoX, DEST_INFO_X);
        else
            infoX = MathHelper.uiLerpSnap(infoX, START_INFO_X);
    }

    public void render(SpriteBatch sb)
    {
        renderOptionButton(sb);
        renderInfo(sb);
        hb.render(sb);
    }

    private void renderOptionButton(SpriteBatch sb)
    {
        if(selected)
        {
            glowColor.a = 0.25F + (MathUtils.cosDeg((System.currentTimeMillis() / 4L) % 360L) + 1.25F) / 3.5F;
            sb.setColor(glowColor);
        } else
        {
            sb.setColor(BLACK_OUTLINE_COLOR);
        }
        sb.draw(ImageMaster.CHAR_OPT_HIGHLIGHT, hb.cX - 110F, hb.cY - 110F, 110F, 110F, 220F, 220F, Settings.scale, Settings.scale, 0.0F, 0, 0, 220, 220, false, false);
        if(selected || hb.hovered)
            sb.setColor(Color.WHITE);
        else
            sb.setColor(Color.LIGHT_GRAY);
        sb.draw(buttonImg, hb.cX - 110F, hb.cY - 110F, 110F, 110F, 220F, 220F, Settings.scale, Settings.scale, 0.0F, 0, 0, 220, 220, false, false);
    }

    private void renderInfo(SpriteBatch sb)
    {
        if(!name.equals(""))
            if(!Settings.isMobile)
            {
                FontHelper.renderSmartText(sb, FontHelper.bannerNameFont, name, infoX - 35F * Settings.scale, infoY + NAME_OFFSET_Y, 99999F, 38F * Settings.scale, Settings.GOLD_COLOR);
                sb.draw(ImageMaster.TP_HP, infoX - 10F * Settings.scale - 32F, (infoY + 95F * Settings.scale) - 32F, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
                FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, (new StringBuilder()).append(TEXT[4]).append(hp).toString(), infoX + 18F * Settings.scale, infoY + 102F * Settings.scale, 10000F, 10000F, Settings.RED_TEXT_COLOR);
                sb.draw(ImageMaster.TP_GOLD, (infoX + 190F * Settings.scale) - 32F, (infoY + 95F * Settings.scale) - 32F, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
                FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, (new StringBuilder()).append(TEXT[5]).append(Integer.toString(gold)).toString(), infoX + 220F * Settings.scale, infoY + 102F * Settings.scale, 10000F, 10000F, Settings.GOLD_COLOR);
                FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, flavorText, infoX - 26F * Settings.scale, infoY + 40F * Settings.scale, 10000F, 30F * Settings.scale, Settings.CREAM_COLOR);
                if(unlocksRemaining > 0)
                {
                    FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, (new StringBuilder()).append(Integer.toString(unlocksRemaining)).append(TEXT[6]).toString(), infoX - 26F * Settings.scale, infoY - 112F * Settings.scale, 10000F, 10000F, Settings.CREAM_COLOR);
                    int unlockProgress = UnlockTracker.getCurrentProgress(c.chosenClass);
                    int unlockCost = UnlockTracker.getCurrentScoreCost(c.chosenClass);
                    FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, (new StringBuilder()).append(Integer.toString(unlockProgress)).append("/").append(unlockCost).append(TEXT[9]).toString(), infoX - 26F * Settings.scale, infoY - 140F * Settings.scale, 10000F, 10000F, Settings.CREAM_COLOR);
                }
                renderRelics(sb);
            } else
            {
                FontHelper.renderSmartText(sb, FontHelper.bannerNameFont, name, infoX - 35F * Settings.scale, infoY + 350F * Settings.scale, 99999F, 38F * Settings.scale, Settings.GOLD_COLOR, 1.1F);
                sb.draw(ImageMaster.TP_HP, infoX - 10F * Settings.scale - 32F, (infoY + 230F * Settings.scale) - 32F, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
                FontHelper.renderSmartText(sb, FontHelper.buttonLabelFont, (new StringBuilder()).append(TEXT[4]).append(hp).toString(), infoX + 18F * Settings.scale, infoY + 243F * Settings.scale, 10000F, 10000F, Settings.RED_TEXT_COLOR, 0.8F);
                sb.draw(ImageMaster.TP_GOLD, (infoX + 260F * Settings.scale) - 32F, (infoY + 230F * Settings.scale) - 32F, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
                FontHelper.renderSmartText(sb, FontHelper.buttonLabelFont, (new StringBuilder()).append(TEXT[5]).append(Integer.toString(gold)).toString(), infoX + 290F * Settings.scale, infoY + 243F * Settings.scale, 10000F, 10000F, Settings.GOLD_COLOR, 0.8F);
                if(selected)
                    FontHelper.renderSmartText(sb, FontHelper.buttonLabelFont, flavorText, infoX - 26F * Settings.scale, infoY + 170F * Settings.scale, 10000F, 40F * Settings.scale, Settings.CREAM_COLOR, 0.9F);
                if(unlocksRemaining > 0)
                {
                    FontHelper.renderSmartText(sb, FontHelper.buttonLabelFont, (new StringBuilder()).append(Integer.toString(unlocksRemaining)).append(TEXT[6]).toString(), infoX - 26F * Settings.scale, infoY - 60F * Settings.scale, 10000F, 10000F, Settings.CREAM_COLOR, 0.8F);
                    int unlockProgress = UnlockTracker.getCurrentProgress(c.chosenClass);
                    int unlockCost = UnlockTracker.getCurrentScoreCost(c.chosenClass);
                    FontHelper.renderSmartText(sb, FontHelper.buttonLabelFont, (new StringBuilder()).append(Integer.toString(unlockProgress)).append("/").append(unlockCost).append(TEXT[9]).toString(), infoX - 26F * Settings.scale, infoY - 100F * Settings.scale, 10000F, 10000F, Settings.CREAM_COLOR, 0.8F);
                }
                renderRelics(sb);
            }
    }

    private void renderRelics(SpriteBatch sb)
    {
        if(charInfo.relics.size() == 1)
        {
            if(!Settings.isMobile)
            {
                sb.setColor(Settings.QUARTER_TRANSPARENT_BLACK_COLOR);
                sb.draw(RelicLibrary.getRelic((String)charInfo.relics.get(0)).outlineImg, infoX - 64F, infoY - 60F * Settings.scale - 64F, 64F, 64F, 128F, 128F, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);
                sb.setColor(Color.WHITE);
                sb.draw(RelicLibrary.getRelic((String)charInfo.relics.get(0)).img, infoX - 64F, infoY - 60F * Settings.scale - 64F, 64F, 64F, 128F, 128F, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);
                FontHelper.renderSmartText(sb, FontHelper.tipHeaderFont, RelicLibrary.getRelic((String)charInfo.relics.get(0)).name, infoX + 44F * Settings.scale, infoY - 40F * Settings.scale, 10000F, 10000F, Settings.GOLD_COLOR);
                String relicString = RelicLibrary.getRelic((String)charInfo.relics.get(0)).description;
                if(charInfo.name.equals(TEXT[7]))
                    relicString = TEXT[8];
                FontHelper.renderSmartText(sb, FontHelper.tipBodyFont, relicString, infoX + 44F * Settings.scale, infoY - 66F * Settings.scale, 10000F, 10000F, Settings.CREAM_COLOR);
            } else
            {
                sb.setColor(Settings.QUARTER_TRANSPARENT_BLACK_COLOR);
                sb.draw(RelicLibrary.getRelic((String)charInfo.relics.get(0)).outlineImg, infoX - 64F, (infoY + 30F * Settings.scale) - 64F, 64F, 64F, 128F, 128F, Settings.scale * 1.4F, Settings.scale * 1.4F, 0.0F, 0, 0, 128, 128, false, false);
                sb.setColor(Color.WHITE);
                sb.draw(RelicLibrary.getRelic((String)charInfo.relics.get(0)).img, infoX - 64F, (infoY + 30F * Settings.scale) - 64F, 64F, 64F, 128F, 128F, Settings.scale * 1.4F, Settings.scale * 1.4F, 0.0F, 0, 0, 128, 128, false, false);
                FontHelper.renderSmartText(sb, FontHelper.topPanelInfoFont, RelicLibrary.getRelic((String)charInfo.relics.get(0)).name, infoX + 60F * Settings.scale, infoY + 60F * Settings.scale, 10000F, 10000F, Settings.GOLD_COLOR);
                String relicString = RelicLibrary.getRelic((String)charInfo.relics.get(0)).description;
                if(charInfo.name.equals(TEXT[7]))
                    relicString = TEXT[8];
                if(selected)
                    FontHelper.renderSmartText(sb, FontHelper.topPanelInfoFont, relicString, infoX + 60F * Settings.scale, infoY + 24F * Settings.scale, 10000F, 10000F, Settings.CREAM_COLOR);
            }
        } else
        {
            for(int i = 0; i < charInfo.relics.size(); i++)
            {
                AbstractRelic r = RelicLibrary.getRelic((String)charInfo.relics.get(i));
                r.updateDescription(charInfo.player.chosenClass);
                Hitbox relicHitbox = new Hitbox(80F * Settings.scale * (0.01F + (1.0F - 0.019F * (float)charInfo.relics.size())), 80F * Settings.scale);
                relicHitbox.move(infoX + (float)i * 72F * Settings.scale * (0.01F + (1.0F - 0.019F * (float)charInfo.relics.size())), infoY - 60F * Settings.scale);
                relicHitbox.render(sb);
                relicHitbox.update();
                if(relicHitbox.hovered)
                    if((float)InputHelper.mX < 1400F * Settings.scale)
                        TipHelper.queuePowerTips((float)InputHelper.mX + 60F * Settings.scale, (float)InputHelper.mY - 50F * Settings.scale, r.tips);
                    else
                        TipHelper.queuePowerTips((float)InputHelper.mX - 350F * Settings.scale, (float)InputHelper.mY - 50F * Settings.scale, r.tips);
                sb.setColor(new Color(0.0F, 0.0F, 0.0F, 0.25F));
                sb.draw(r.outlineImg, (infoX - 64F) + (float)i * 72F * Settings.scale * (0.01F + (1.0F - 0.019F * (float)charInfo.relics.size())), infoY - 60F * Settings.scale - 64F, 64F, 64F, 128F, 128F, Settings.scale * (0.01F + (1.0F - 0.019F * (float)charInfo.relics.size())), Settings.scale * (0.01F + (1.0F - 0.019F * (float)charInfo.relics.size())), 0.0F, 0, 0, 128, 128, false, false);
                sb.setColor(Color.WHITE);
                sb.draw(r.img, (infoX - 64F) + (float)i * 72F * Settings.scale * (0.01F + (1.0F - 0.019F * (float)charInfo.relics.size())), infoY - 60F * Settings.scale - 64F, 64F, 64F, 128F, 128F, Settings.scale * (0.01F + (1.0F - 0.019F * (float)charInfo.relics.size())), Settings.scale * (0.01F + (1.0F - 0.019F * (float)charInfo.relics.size())), 0.0F, 0, 0, 128, 128, false, false);
            }

        }
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private Texture buttonImg;
    private Texture portraitImg;
    private String portraitUrl;
    public AbstractPlayer c;
    public boolean selected;
    public boolean locked;
    public Hitbox hb;
    private static final float HB_W;
    private static final int BUTTON_W = 220;
    public static final String ASSETS_DIR = "images/ui/charSelect/";
    private static final Color BLACK_OUTLINE_COLOR = new Color(0.0F, 0.0F, 0.0F, 0.5F);
    private Color glowColor;
    private static final int ICON_W = 64;
    private static final float DEST_INFO_X;
    private static final float START_INFO_X;
    private float infoX;
    private float infoY;
    public String name;
    private static final float NAME_OFFSET_Y;
    private String hp;
    private int gold;
    private String flavorText;
    private CharSelectInfo charInfo;
    private int unlocksRemaining;
    private int maxAscensionLevel;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("CharacterOption");
        TEXT = uiStrings.TEXT;
        HB_W = 150F * Settings.scale;
        DEST_INFO_X = Settings.isMobile ? 160F * Settings.scale : 200F * Settings.scale;
        START_INFO_X = -800F * Settings.xScale;
        NAME_OFFSET_Y = 200F * Settings.scale;
    }
}
