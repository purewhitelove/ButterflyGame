// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SaveSlot.java

package com.megacrit.cardcrawl.screens.mainMenu;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.stats.CharStat;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.screens.mainMenu:
//            MainMenuScreen, SaveSlotScreen

public class SaveSlot
{

    public SaveSlot(int slot)
    {
        name = "";
        playtime = 0L;
        completionPercentage = 0.0F;
        emptySlot = false;
        index = 0;
        iconImg = null;
        if(slot == CardCrawlGame.saveSlot && !CardCrawlGame.playerName.equals(CardCrawlGame.saveSlotPref.getString(SaveHelper.slotName("PROFILE_NAME", slot), "")))
        {
            logger.info("Migrating from legacy save.");
            migration(slot);
        }
        name = CardCrawlGame.saveSlotPref.getString(SaveHelper.slotName("PROFILE_NAME", slot), "");
        if(name.equals(""))
            emptySlot = true;
        if(!emptySlot)
            loadInfo(slot);
        index = slot;
        slotHb = new Hitbox(800F * Settings.scale, 260F * Settings.scale);
        slotHb.move((float)Settings.WIDTH / 2.0F, ((float)Settings.HEIGHT / 2.0F + 280F * Settings.scale) - (float)index * 280F * Settings.scale);
        renameHb = new Hitbox(90F * Settings.scale, 90F * Settings.scale);
        renameHb.move(1400F * Settings.xScale, slotHb.cY + 44F * Settings.scale);
        deleteHb = new Hitbox(90F * Settings.scale, 90F * Settings.scale);
        deleteHb.move(1400F * Settings.xScale, renameHb.cY - 91F * Settings.scale);
        switch(index)
        {
        case 0: // '\0'
            iconImg = ImageMaster.PROFILE_A;
            break;

        case 1: // '\001'
            iconImg = ImageMaster.PROFILE_B;
            break;

        case 2: // '\002'
            iconImg = ImageMaster.PROFILE_C;
            break;
        }
    }

    private void loadInfo(int slot)
    {
        completionPercentage = CardCrawlGame.saveSlotPref.getFloat(SaveHelper.slotName("COMPLETION", slot), 0.0F);
        playtime = CardCrawlGame.saveSlotPref.getLong(SaveHelper.slotName("PLAYTIME", slot), 0L);
    }

    private void migration(int slot)
    {
        CardCrawlGame.saveSlotPref.putString(SaveHelper.slotName("PROFILE_NAME", slot), CardCrawlGame.playerName);
        CardCrawlGame.saveSlotPref.putFloat(SaveHelper.slotName("COMPLETION", slot), UnlockTracker.getCompletionPercentage());
        completionPercentage = CardCrawlGame.saveSlotPref.getFloat(SaveHelper.slotName("COMPLETION", slot), 0.0F);
        CardCrawlGame.saveSlotPref.putLong(SaveHelper.slotName("PLAYTIME", slot), UnlockTracker.getTotalPlaytime());
        playtime = CardCrawlGame.saveSlotPref.getLong(SaveHelper.slotName("PLAYTIME", slot), 0L);
        CardCrawlGame.saveSlotPref.flush();
    }

    public void update()
    {
        if(!emptySlot)
        {
            deleteHb.update();
            renameHb.update();
            if(slotHb.hovered && CInputActionSet.topPanel.isJustPressed())
            {
                CInputActionSet.topPanel.unpress();
                deleteHb.clicked = true;
                deleteHb.hovered = true;
            }
            if(slotHb.hovered && CInputActionSet.proceed.isJustPressed())
            {
                CInputActionSet.proceed.unpress();
                renameHb.clicked = true;
                renameHb.hovered = true;
            }
        }
        if(!deleteHb.hovered && !renameHb.hovered)
        {
            slotHb.update();
            if(slotHb.hovered && CInputActionSet.select.isJustPressed())
                slotHb.clicked = true;
            else
            if(slotHb.justHovered)
                CardCrawlGame.sound.play("UI_HOVER");
            if(slotHb.hovered && InputHelper.justClickedLeft)
            {
                slotHb.clickStarted = true;
                CardCrawlGame.sound.play("UI_CLICK_1");
            } else
            if(slotHb.clicked)
            {
                slotHb.clicked = false;
                CardCrawlGame.saveSlot = index;
                if(name.equals(""))
                    CardCrawlGame.mainMenuScreen.saveSlotScreen.openRenamePopup(index, true);
                else
                    CardCrawlGame.mainMenuScreen.saveSlotScreen.confirm(index);
            }
        } else
        {
            slotHb.unhover();
            if(deleteHb.justHovered)
                CardCrawlGame.sound.play("UI_HOVER");
            else
            if(deleteHb.hovered && InputHelper.justClickedLeft)
            {
                deleteHb.clickStarted = true;
                CardCrawlGame.sound.play("UI_CLICK_1");
            } else
            if(deleteHb.clicked)
            {
                deleteHb.clicked = false;
                CardCrawlGame.mainMenuScreen.saveSlotScreen.openDeletePopup(index);
            }
            if(renameHb.justHovered)
                CardCrawlGame.sound.play("UI_HOVER");
            else
            if(renameHb.hovered && InputHelper.justClickedLeft)
            {
                renameHb.clickStarted = true;
                CardCrawlGame.sound.play("UI_CLICK_1");
            } else
            if(renameHb.clicked)
            {
                renameHb.clicked = false;
                CardCrawlGame.mainMenuScreen.saveSlotScreen.openRenamePopup(index, false);
            }
        }
    }

    public void render(SpriteBatch sb)
    {
        renderSlotImage(sb);
        renderText(sb);
        if(!emptySlot)
        {
            renderDeleteIcon(sb);
            renderRenameIcon(sb);
        }
        renderHbs(sb);
    }

    private void renderText(SpriteBatch sb)
    {
        Color c = Settings.GOLD_COLOR.cpy();
        c.a = uiColor.a;
        if(!emptySlot)
        {
            FontHelper.renderFontLeft(sb, FontHelper.buttonLabelFont, name, 740F * Settings.xScale, slotHb.cY + 26F * Settings.scale, c);
            FontHelper.renderFontLeft(sb, FontHelper.charDescFont, (new StringBuilder()).append(TEXT[0]).append(CharStat.formatHMSM((int)playtime)).toString(), 740F * Settings.xScale, slotHb.cY - 24F * Settings.scale, uiColor);
            if(completionPercentage == 100F)
                FontHelper.renderFontCentered(sb, FontHelper.charTitleFont, "100%", 1200F * Settings.xScale, slotHb.cY + 0.0F * Settings.scale, c);
            else
            if(completionPercentage == 0.0F)
                FontHelper.renderFontCentered(sb, FontHelper.charTitleFont, "0%", 1200F * Settings.xScale, slotHb.cY + 0.0F * Settings.scale, c);
            else
                FontHelper.renderFontCentered(sb, FontHelper.charTitleFont, (new StringBuilder()).append(String.format("%.1f", new Object[] {
                    Float.valueOf(completionPercentage)
                })).append("%").toString(), 1200F * Settings.xScale, slotHb.cY + 0.0F * Settings.scale, c);
        } else
        {
            FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, TEXT[1], (float)Settings.WIDTH / 2.0F, slotHb.cY, c);
        }
    }

    private void renderSlotImage(SpriteBatch sb)
    {
        float scale = Settings.xScale <= Settings.yScale ? Settings.yScale : Settings.xScale;
        if(slotHb.hovered)
            scale *= 1.02F;
        sb.draw(ImageMaster.PROFILE_SLOT, slotHb.cX - 400F, slotHb.cY - 130F, 400F, 130F, 800F, 260F, scale, scale * 0.9F, 0.0F, 0, 0, 800, 260, false, false);
        sb.draw(iconImg, slotHb.cX - 290F * Settings.xScale - 50F, slotHb.cY - 50F, 50F, 50F, 100F, 100F, Settings.scale, Settings.scale, 0.0F, 0, 0, 100, 100, false, false);
        if(slotHb.hovered)
        {
            sb.setColor(Settings.HALF_TRANSPARENT_WHITE_COLOR);
            sb.setBlendFunction(770, 1);
            sb.draw(ImageMaster.PROFILE_SLOT, slotHb.cX - 400F, slotHb.cY - 130F, 400F, 130F, 800F, 260F, scale, scale * 0.9F, 0.0F, 0, 0, 800, 260, false, false);
            sb.setBlendFunction(770, 771);
            sb.setColor(uiColor);
        }
    }

    private void renderHbs(SpriteBatch sb)
    {
        if(CardCrawlGame.mainMenuScreen.saveSlotScreen.shown)
        {
            slotHb.render(sb);
            deleteHb.render(sb);
            renameHb.render(sb);
        }
    }

    private void renderDeleteIcon(SpriteBatch sb)
    {
        float scale = Settings.scale;
        if(deleteHb.hovered)
            scale = Settings.scale * 1.04F;
        sb.draw(ImageMaster.PROFILE_DELETE, deleteHb.cX - 50F, deleteHb.cY - 50F, 50F, 50F, 100F, 100F, scale, scale, 0.0F, 0, 0, 100, 100, false, false);
        if(deleteHb.hovered)
        {
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.4F));
            sb.setBlendFunction(770, 1);
            sb.draw(ImageMaster.PROFILE_DELETE, deleteHb.cX - 50F, deleteHb.cY - 50F, 50F, 50F, 100F, 100F, scale, scale, 0.0F, 0, 0, 100, 100, false, false);
            sb.setBlendFunction(770, 771);
            sb.setColor(uiColor);
        }
        if(slotHb.hovered && Settings.isControllerMode)
            sb.draw(CInputActionSet.topPanel.getKeyImg(), (deleteHb.cX - 32F) + 82F * Settings.scale, deleteHb.cY - 32F, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
    }

    private void renderRenameIcon(SpriteBatch sb)
    {
        float scale = Settings.scale;
        if(renameHb.hovered)
            scale = Settings.scale * 1.04F;
        sb.draw(ImageMaster.PROFILE_RENAME, renameHb.cX - 50F, renameHb.cY - 50F, 50F, 50F, 100F, 100F, scale, scale, 0.0F, 0, 0, 100, 100, false, false);
        if(renameHb.hovered)
        {
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.4F));
            sb.setBlendFunction(770, 1);
            sb.draw(ImageMaster.PROFILE_RENAME, renameHb.cX - 50F, renameHb.cY - 50F, 50F, 50F, 100F, 100F, scale, scale, 0.0F, 0, 0, 100, 100, false, false);
            sb.setBlendFunction(770, 771);
            sb.setColor(uiColor);
        }
        if(slotHb.hovered && Settings.isControllerMode)
            sb.draw(CInputActionSet.proceed.getKeyImg(), (renameHb.cX - 32F) + 82F * Settings.scale, renameHb.cY - 32F, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
    }

    public void clear()
    {
        name = "";
        emptySlot = true;
        completionPercentage = 0.0F;
        playtime = 0L;
        deleteHb.unhover();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String setName)
    {
        name = setName;
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/screens/mainMenu/SaveSlot.getName());
    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private String name;
    private long playtime;
    private float completionPercentage;
    public boolean emptySlot;
    private int index;
    private Texture iconImg;
    public Hitbox slotHb;
    public Hitbox deleteHb;
    public Hitbox renameHb;
    public static Color uiColor = null;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("SaveSlot");
        TEXT = uiStrings.TEXT;
    }
}
