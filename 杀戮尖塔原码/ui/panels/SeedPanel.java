// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SeedPanel.java

package com.megacrit.cardcrawl.ui.panels;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Clipboard;
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

public class SeedPanel
{

    public SeedPanel()
    {
        yesHb = new Hitbox(160F * Settings.scale, 70F * Settings.scale);
        noHb = new Hitbox(160F * Settings.scale, 70F * Settings.scale);
        screenColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
        uiColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        animTimer = 0.0F;
        waitTimer = 0.0F;
        shown = false;
        sourceScreen = null;
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
            if(animTimer != 0.0F)
            {
                animTimer -= Gdx.graphics.getDeltaTime();
                if(animTimer < 0.0F)
                    animTimer = 0.0F;
                screenColor.a = Interpolation.fade.apply(0.8F, 0.0F, (animTimer * 1.0F) / 0.25F);
                uiColor.a = Interpolation.fade.apply(1.0F, 0.0F, (animTimer * 1.0F) / 0.25F);
            } else
            {
                updateYes();
                updateNo();
                if(InputActionSet.confirm.isJustPressed())
                    confirm();
                else
                if(InputHelper.pressedEscape)
                {
                    InputHelper.pressedEscape = false;
                    cancel();
                } else
                if(InputHelper.isPasteJustPressed())
                {
                    Clipboard clipBoard = Gdx.app.getClipboard();
                    String pasteText = clipBoard.getContents();
                    String seedText = SeedHelper.sterilizeString(pasteText);
                    if(!seedText.isEmpty())
                        textField = seedText;
                }
            }
        } else
        if(animTimer != 0.0F)
        {
            animTimer -= Gdx.graphics.getDeltaTime();
            if(animTimer < 0.0F)
                animTimer = 0.0F;
            screenColor.a = Interpolation.fade.apply(0.0F, 0.8F, (animTimer * 1.0F) / 0.25F);
            uiColor.a = Interpolation.fade.apply(0.0F, 1.0F, (animTimer * 1.0F) / 0.25F);
        }
    }

    private void updateYes()
    {
        yesHb.update();
        if(yesHb.justHovered)
            CardCrawlGame.sound.play("UI_HOVER");
        if(InputHelper.justClickedLeft && yesHb.hovered)
        {
            CardCrawlGame.sound.play("UI_CLICK_1");
            yesHb.clickStarted = true;
        }
        if(CInputActionSet.proceed.isJustPressed())
        {
            CInputActionSet.proceed.unpress();
            yesHb.clicked = true;
        }
        if(yesHb.clicked)
        {
            yesHb.clicked = false;
            confirm();
        }
    }

    private void updateNo()
    {
        noHb.update();
        if(noHb.justHovered)
            CardCrawlGame.sound.play("UI_HOVER");
        if(InputHelper.justClickedLeft && noHb.hovered)
        {
            CardCrawlGame.sound.play("UI_CLICK_1");
            noHb.clickStarted = true;
        }
        if(CInputActionSet.cancel.isJustPressed())
            noHb.clicked = true;
        if(noHb.clicked)
        {
            noHb.clicked = false;
            cancel();
        }
    }

    public void show()
    {
        Gdx.input.setInputProcessor(new TypeHelper(true));
        if(SteamInputHelper.numControllers == 1 && CardCrawlGame.clientUtils != null && CardCrawlGame.clientUtils.isSteamRunningOnSteamDeck())
            CardCrawlGame.clientUtils.showFloatingGamepadTextInput(com.codedisaster.steamworks.SteamUtils.FloatingGamepadTextInputMode.ModeSingleLine, 0, 0, Settings.WIDTH, (int)((float)Settings.HEIGHT * 0.25F));
        yesHb.move(860F * Settings.scale, Settings.OPTION_Y - 118F * Settings.scale);
        noHb.move(1062F * Settings.scale, Settings.OPTION_Y - 118F * Settings.scale);
        shown = true;
        animTimer = 0.25F;
        textField = SeedHelper.getUserFacingSeedString();
    }

    public void show(com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen.CurScreen sourceScreen)
    {
        show();
        this.sourceScreen = sourceScreen;
    }

    public void confirm()
    {
        textField = textField.trim();
        try
        {
            SeedHelper.setSeed(textField);
        }
        catch(NumberFormatException e)
        {
            Settings.seed = Long.valueOf(0x7fffffffffffffffL);
        }
        close();
    }

    public void cancel()
    {
        close();
    }

    public void close()
    {
        yesHb.move(-1000F, -1000F);
        noHb.move(-1000F, -1000F);
        shown = false;
        animTimer = 0.25F;
        Gdx.input.setInputProcessor(new ScrollInputProcessor());
        if(sourceScreen == null)
        {
            CardCrawlGame.mainMenuScreen.screen = com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen.CurScreen.CHAR_SELECT;
        } else
        {
            CardCrawlGame.mainMenuScreen.screen = sourceScreen;
            sourceScreen = null;
        }
    }

    public static boolean isFull()
    {
        return textField.length() >= SeedHelper.SEED_DEFAULT_LENGTH;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(screenColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        sb.setColor(uiColor);
        sb.draw(ImageMaster.OPTION_CONFIRM, (float)Settings.WIDTH / 2.0F - 180F, Settings.OPTION_Y - 207F, 180F, 207F, 360F, 414F, Settings.scale, Settings.scale, 0.0F, 0, 0, 360, 414, false, false);
        sb.draw(ImageMaster.RENAME_BOX, (float)Settings.WIDTH / 2.0F - 160F, Settings.OPTION_Y - 160F, 160F, 160F, 320F, 320F, Settings.scale * 1.1F, Settings.scale * 1.1F, 0.0F, 0, 0, 320, 320, false, false);
        FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, textField, (float)Settings.WIDTH / 2.0F - 120F * Settings.scale, Settings.OPTION_Y + 4F * Settings.scale, 100000F, 0.0F, uiColor, 0.82F);
        if(!isFull())
        {
            float tmpAlpha = ((MathUtils.cosDeg((System.currentTimeMillis() / 3L) % 360L) + 1.25F) / 3F) * uiColor.a;
            FontHelper.renderSmartText(sb, FontHelper.cardTitleFont, "_", ((float)Settings.WIDTH / 2.0F - 122F * Settings.scale) + FontHelper.getSmartWidth(FontHelper.cardTitleFont, textField, 1000000F, 0.0F, 0.82F), Settings.OPTION_Y + 4F * Settings.scale, 100000F, 0.0F, new Color(1.0F, 1.0F, 1.0F, tmpAlpha), 0.82F);
        }
        Color c = Settings.GOLD_COLOR.cpy();
        c.a = uiColor.a;
        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, TEXT[1], (float)Settings.WIDTH / 2.0F, Settings.OPTION_Y + 126F * Settings.scale, c);
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
        if(yesHb.clickStarted)
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
        if(shown)
        {
            if(Settings.isControllerMode)
            {
                sb.draw(CInputActionSet.proceed.getKeyImg(), 770F * Settings.scale - 32F, Settings.OPTION_Y - 32F - 140F * Settings.scale, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
                sb.draw(CInputActionSet.cancel.getKeyImg(), 1150F * Settings.scale - 32F, Settings.OPTION_Y - 32F - 140F * Settings.scale, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
            }
            yesHb.render(sb);
            noHb.render(sb);
            if(!Settings.usesTrophies)
                FontHelper.renderFontCentered(sb, FontHelper.panelNameFont, TEXT[4], (float)Settings.WIDTH / 2.0F, 100F * Settings.scale, new Color(1.0F, 0.3F, 0.3F, c.a));
            else
                FontHelper.renderFontCentered(sb, FontHelper.panelNameFont, TEXT[5], (float)Settings.WIDTH / 2.0F, 100F * Settings.scale, new Color(1.0F, 0.3F, 0.3F, c.a));
        }
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    public String title;
    public static String textField = "";
    public Hitbox yesHb;
    public Hitbox noHb;
    private static final int CONFIRM_W = 360;
    private static final int CONFIRM_H = 414;
    private static final int YES_W = 173;
    private static final int NO_W = 161;
    private static final int BUTTON_H = 74;
    private Color screenColor;
    private Color uiColor;
    private float animTimer;
    private float waitTimer;
    private static final float ANIM_TIME = 0.25F;
    public boolean shown;
    private static final float SCREEN_DARKNESS = 0.8F;
    public com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen.CurScreen sourceScreen;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("SeedPanel");
        TEXT = uiStrings.TEXT;
    }
}
