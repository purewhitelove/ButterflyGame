// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RenamePopup.java

package com.megacrit.cardcrawl.ui.panels;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.codedisaster.steamworks.SteamUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.*;
import com.megacrit.cardcrawl.helpers.steamInput.SteamInputHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import com.megacrit.cardcrawl.screens.mainMenu.SaveSlotScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RenamePopup
{

    public RenamePopup()
    {
        slot = 0;
        newSave = false;
        shown = false;
        yesHb = new Hitbox(160F * Settings.scale, 70F * Settings.scale);
        noHb = new Hitbox(160F * Settings.scale, 70F * Settings.scale);
        faderColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
        uiColor = new Color(1.0F, 0.965F, 0.886F, 0.0F);
        waitTimer = 0.0F;
        yesHb.move(854F * Settings.scale, Settings.OPTION_Y - 120F * Settings.scale);
        noHb.move(1066F * Settings.scale, Settings.OPTION_Y - 120F * Settings.scale);
    }

    public void update()
    {
        if(Gdx.input.isKeyPressed(67) && !textField.equals("") && waitTimer <= 0.0F)
        {
            textField = textField.substring(0, textField.length() - 1);
            waitTimer = 0.09F;
        }
        if(waitTimer > 0.0F)
            waitTimer -= Gdx.graphics.getDeltaTime();
        if(shown)
        {
            faderColor.a = MathHelper.fadeLerpSnap(faderColor.a, 0.75F);
            uiColor.a = MathHelper.fadeLerpSnap(uiColor.a, 1.0F);
            updateButtons();
            if(Gdx.input.isKeyJustPressed(66))
                confirm();
            else
            if(InputHelper.pressedEscape)
            {
                InputHelper.pressedEscape = false;
                cancel();
            }
        } else
        {
            faderColor.a = MathHelper.fadeLerpSnap(faderColor.a, 0.0F);
            uiColor.a = MathHelper.fadeLerpSnap(uiColor.a, 0.0F);
        }
    }

    private void updateButtons()
    {
        yesHb.update();
        if(yesHb.justHovered)
            CardCrawlGame.sound.play("UI_HOVER");
        if(yesHb.hovered && InputHelper.justClickedLeft)
        {
            CardCrawlGame.sound.play("UI_CLICK_1");
            yesHb.clickStarted = true;
        } else
        if(yesHb.clicked)
        {
            confirm();
            yesHb.clicked = false;
        }
        noHb.update();
        if(noHb.justHovered)
            CardCrawlGame.sound.play("UI_HOVER");
        if(noHb.hovered && InputHelper.justClickedLeft)
        {
            CardCrawlGame.sound.play("UI_CLICK_1");
            noHb.clickStarted = true;
        } else
        if(noHb.clicked)
        {
            cancel();
            noHb.clicked = false;
        }
        if(CInputActionSet.proceed.isJustPressed())
        {
            CInputActionSet.proceed.unpress();
            confirm();
        } else
        if(CInputActionSet.cancel.isJustPressed() || InputActionSet.cancel.isJustPressed())
        {
            CInputActionSet.cancel.unpress();
            cancel();
        }
    }

    public void confirm()
    {
        textField = textField.trim();
        if(textField.equals(""))
            return;
        CardCrawlGame.mainMenuScreen.saveSlotScreen.curPop = com.megacrit.cardcrawl.screens.mainMenu.SaveSlotScreen.CurrentPopup.NONE;
        shown = false;
        Gdx.input.setInputProcessor(new ScrollInputProcessor());
        if(newSave)
        {
            boolean saveSlotPrefSave = false;
            logger.info("UPDATING DEFAULT SLOT: ", Integer.valueOf(slot));
            CardCrawlGame.saveSlotPref.putInteger("DEFAULT_SLOT", slot);
            saveSlotPrefSave = true;
            CardCrawlGame.reloadPrefs();
            if(!CardCrawlGame.saveSlotPref.getString(SaveHelper.slotName("PROFILE_NAME", slot), "").equals(textField))
            {
                logger.info((new StringBuilder()).append("NAME CHANGE IN SLOT ").append(slot).append(": ").append(textField).toString());
                CardCrawlGame.saveSlotPref.putString(SaveHelper.slotName("PROFILE_NAME", slot), textField);
                saveSlotPrefSave = true;
            }
            if(saveSlotPrefSave)
                CardCrawlGame.saveSlotPref.flush();
            if(CardCrawlGame.playerPref.getString("alias", "").equals(""))
                CardCrawlGame.playerPref.putString("alias", CardCrawlGame.generateRandomAlias());
            CardCrawlGame.alias = CardCrawlGame.playerPref.getString("alias", "");
            CardCrawlGame.playerPref.putString("name", textField);
            CardCrawlGame.playerPref.flush();
            CardCrawlGame.playerName = textField;
        } else
        if(!CardCrawlGame.saveSlotPref.getString(SaveHelper.slotName("PROFILE_NAME", slot), "").equals(textField))
        {
            logger.info((new StringBuilder()).append("RENAMING ").append(slot).append(": ").append(textField).toString());
            CardCrawlGame.saveSlotPref.putString(SaveHelper.slotName("PROFILE_NAME", slot), textField);
            CardCrawlGame.saveSlotPref.flush();
            CardCrawlGame.mainMenuScreen.saveSlotScreen.rename(slot, textField);
            CardCrawlGame.playerName = textField;
        }
    }

    public void cancel()
    {
        CardCrawlGame.mainMenuScreen.saveSlotScreen.curPop = com.megacrit.cardcrawl.screens.mainMenu.SaveSlotScreen.CurrentPopup.NONE;
        shown = false;
        Gdx.input.setInputProcessor(new ScrollInputProcessor());
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(faderColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        renderPopupBg(sb);
        renderTextbox(sb);
        renderHeader(sb);
        renderButtons(sb);
    }

    private void renderHeader(SpriteBatch sb)
    {
        Color c = Settings.GOLD_COLOR.cpy();
        c.a = uiColor.a;
        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, TEXT[1], (float)Settings.WIDTH / 2.0F, Settings.OPTION_Y + 150F * Settings.scale, c);
    }

    private void renderPopupBg(SpriteBatch sb)
    {
        sb.setColor(uiColor);
        sb.draw(ImageMaster.OPTION_CONFIRM, (float)Settings.WIDTH / 2.0F - 180F, Settings.OPTION_Y - 207F, 180F, 207F, 360F, 414F, Settings.scale, Settings.scale, 0.0F, 0, 0, 360, 414, false, false);
    }

    private void renderTextbox(SpriteBatch sb)
    {
        sb.draw(ImageMaster.RENAME_BOX, (float)Settings.WIDTH / 2.0F - 160F, (Settings.OPTION_Y + 20F * Settings.scale) - 160F, 160F, 160F, 320F, 320F, Settings.scale, Settings.scale, 0.0F, 0, 0, 320, 320, false, false);
        FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, textField, (float)Settings.WIDTH / 2.0F - 120F * Settings.scale, Settings.OPTION_Y + 24F * Settings.scale, 100000F, 0.0F, uiColor, 0.82F);
        float tmpAlpha = ((MathUtils.cosDeg((System.currentTimeMillis() / 3L) % 360L) + 1.25F) / 3F) * uiColor.a;
        FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, "_", ((float)Settings.WIDTH / 2.0F - 122F * Settings.scale) + FontHelper.getSmartWidth(FontHelper.cardTitleFont, textField, 1000000F, 0.0F, 0.82F), Settings.OPTION_Y + 24F * Settings.scale, 100000F, 0.0F, new Color(1.0F, 1.0F, 1.0F, tmpAlpha));
    }

    private void renderButtons(SpriteBatch sb)
    {
        sb.setColor(uiColor);
        Color c = Settings.GOLD_COLOR.cpy();
        c.a = uiColor.a;
        if(yesHb.clickStarted)
        {
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, uiColor.a * 0.9F));
            sb.draw(ImageMaster.OPTION_YES, (float)Settings.WIDTH / 2.0F - 86.5F - 100F * Settings.scale, Settings.OPTION_Y - 37F - 120F * Settings.scale, 86.5F, 37F, 173F, 74F, Settings.scale, Settings.scale, 0.0F, 0, 0, 173, 74, false, false);
            sb.setColor(new Color(uiColor));
        } else
        {
            sb.draw(ImageMaster.OPTION_YES, (float)Settings.WIDTH / 2.0F - 86.5F - 100F * Settings.scale, Settings.OPTION_Y - 37F - 120F * Settings.scale, 86.5F, 37F, 173F, 74F, Settings.scale, Settings.scale, 0.0F, 0, 0, 173, 74, false, false);
        }
        if(!yesHb.clickStarted && yesHb.hovered)
        {
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, uiColor.a * 0.25F));
            sb.setBlendFunction(770, 1);
            sb.draw(ImageMaster.OPTION_YES, (float)Settings.WIDTH / 2.0F - 86.5F - 100F * Settings.scale, Settings.OPTION_Y - 37F - 120F * Settings.scale, 86.5F, 37F, 173F, 74F, Settings.scale, Settings.scale, 0.0F, 0, 0, 173, 74, false, false);
            sb.setBlendFunction(770, 771);
            sb.setColor(uiColor);
        }
        if(yesHb.clickStarted || textField.trim().equals(""))
            c = Color.LIGHT_GRAY.cpy();
        else
        if(yesHb.hovered)
            c = Settings.CREAM_COLOR.cpy();
        else
            c = Settings.GOLD_COLOR.cpy();
        c.a = uiColor.a;
        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, TEXT[2], (float)Settings.WIDTH / 2.0F - 110F * Settings.scale, Settings.OPTION_Y - 118F * Settings.scale, c, 0.82F);
        sb.draw(ImageMaster.OPTION_NO, ((float)Settings.WIDTH / 2.0F - 80.5F) + 106F * Settings.scale, Settings.OPTION_Y - 37F - 120F * Settings.scale, 80.5F, 37F, 161F, 74F, Settings.scale, Settings.scale, 0.0F, 0, 0, 161, 74, false, false);
        if(!noHb.clickStarted && noHb.hovered)
        {
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, uiColor.a * 0.25F));
            sb.setBlendFunction(770, 1);
            sb.draw(ImageMaster.OPTION_NO, ((float)Settings.WIDTH / 2.0F - 80.5F) + 106F * Settings.scale, Settings.OPTION_Y - 37F - 120F * Settings.scale, 80.5F, 37F, 161F, 74F, Settings.scale, Settings.scale, 0.0F, 0, 0, 161, 74, false, false);
            sb.setBlendFunction(770, 771);
            sb.setColor(uiColor);
        }
        if(noHb.clickStarted)
            c = Color.LIGHT_GRAY.cpy();
        else
        if(noHb.hovered)
            c = Settings.CREAM_COLOR.cpy();
        else
            c = Settings.GOLD_COLOR.cpy();
        c.a = uiColor.a;
        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, TEXT[3], (float)Settings.WIDTH / 2.0F + 110F * Settings.scale, Settings.OPTION_Y - 118F * Settings.scale, c, 0.82F);
        if(Settings.isControllerMode)
        {
            sb.draw(CInputActionSet.proceed.getKeyImg(), 770F * Settings.xScale - 32F, Settings.OPTION_Y - 32F - 140F * Settings.scale, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
            sb.draw(CInputActionSet.cancel.getKeyImg(), 1150F * Settings.xScale - 32F, Settings.OPTION_Y - 32F - 140F * Settings.scale, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        }
        if(shown)
        {
            yesHb.render(sb);
            noHb.render(sb);
        }
    }

    public void open(int slot, boolean isNewSave)
    {
        Gdx.input.setInputProcessor(new TypeHelper());
        if(SteamInputHelper.numControllers == 1 && CardCrawlGame.clientUtils != null && CardCrawlGame.clientUtils.isSteamRunningOnSteamDeck())
            CardCrawlGame.clientUtils.showFloatingGamepadTextInput(com.codedisaster.steamworks.SteamUtils.FloatingGamepadTextInputMode.ModeSingleLine, 0, 0, Settings.WIDTH, (int)((float)Settings.HEIGHT * 0.25F));
        this.slot = slot;
        newSave = isNewSave;
        shown = true;
        textField = "";
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/ui/panels/RenamePopup.getName());
    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private int slot;
    private boolean newSave;
    private boolean shown;
    public static String textField = "";
    public Hitbox yesHb;
    public Hitbox noHb;
    private static final int CONFIRM_W = 360;
    private static final int CONFIRM_H = 414;
    private static final int YES_W = 173;
    private static final int NO_W = 161;
    private static final int BUTTON_H = 74;
    private Color faderColor;
    private Color uiColor;
    private float waitTimer;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("RenamePanel");
        TEXT = uiStrings.TEXT;
    }
}
