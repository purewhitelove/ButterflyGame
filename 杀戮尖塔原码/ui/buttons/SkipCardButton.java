// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SkipCardButton.java

package com.megacrit.cardcrawl.ui.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.*;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;

public class SkipCardButton
{

    public SkipCardButton()
    {
        current_x = HIDE_X;
        target_x = current_x;
        isHidden = true;
        textColor = Color.WHITE.cpy();
        btnColor = Color.WHITE.cpy();
        screenDisabled = false;
        hb = new Hitbox(0.0F, 0.0F, HITBOX_W, HITBOX_H);
        controllerImgTextWidth = 0.0F;
        hb.move((float)Settings.WIDTH / 2.0F, TAKE_Y);
    }

    public void update()
    {
        if(isHidden)
            return;
        hb.update();
        if(hb.justHovered)
            CardCrawlGame.sound.play("UI_HOVER");
        if(hb.hovered && InputHelper.justClickedLeft)
        {
            hb.clickStarted = true;
            CardCrawlGame.sound.play("UI_CLICK_1");
        }
        if((hb.clicked || InputActionSet.cancel.isJustPressed() || CInputActionSet.cancel.isJustPressed()) && !screenDisabled)
        {
            hb.clicked = false;
            AbstractDungeon.closeCurrentScreen();
        }
        screenDisabled = false;
        if(current_x != target_x)
        {
            current_x = MathUtils.lerp(current_x, target_x, Gdx.graphics.getDeltaTime() * 9F);
            if(Math.abs(current_x - target_x) < Settings.UI_SNAP_THRESHOLD)
            {
                current_x = target_x;
                hb.move(current_x, TAKE_Y);
            }
        }
        textColor.a = MathHelper.fadeLerpSnap(textColor.a, 1.0F);
        btnColor.a = textColor.a;
    }

    public void hideInstantly()
    {
        current_x = HIDE_X;
        target_x = HIDE_X;
        isHidden = true;
        textColor.a = 0.0F;
        btnColor.a = 0.0F;
    }

    public void hide()
    {
        isHidden = true;
    }

    public void show()
    {
        isHidden = false;
        textColor.a = 0.0F;
        btnColor.a = 0.0F;
        current_x = HIDE_X;
        target_x = SHOW_X;
        hb.move(SHOW_X, TAKE_Y);
    }

    public void show(boolean singingBowl)
    {
        isHidden = false;
        textColor.a = 0.0F;
        btnColor.a = 0.0F;
        current_x = HIDE_X;
        target_x = SHOW_X - 165F * Settings.scale;
    }

    public void render(SpriteBatch sb)
    {
        if(isHidden)
            return;
        renderButton(sb);
        if(FontHelper.getSmartWidth(FontHelper.buttonLabelFont, TEXT[0], 9999F, 0.0F) > 200F * Settings.scale)
            FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, TEXT[0], current_x, TAKE_Y, textColor, 0.8F);
        else
            FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, TEXT[0], current_x, TAKE_Y, textColor);
    }

    private void renderButton(SpriteBatch sb)
    {
        sb.setColor(btnColor);
        sb.draw(ImageMaster.REWARD_SCREEN_TAKE_BUTTON, current_x - 256F, TAKE_Y - 128F, 256F, 128F, 512F, 256F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 256, false, false);
        if(hb.hovered && !hb.clickStarted)
        {
            sb.setBlendFunction(770, 1);
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.3F));
            sb.draw(ImageMaster.REWARD_SCREEN_TAKE_BUTTON, current_x - 256F, TAKE_Y - 128F, 256F, 128F, 512F, 256F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 256, false, false);
            sb.setBlendFunction(770, 771);
        }
        if(Settings.isControllerMode)
        {
            if(controllerImgTextWidth == 0.0F)
                controllerImgTextWidth = FontHelper.getSmartWidth(FontHelper.buttonLabelFont, TEXT[0], 99999F, 0.0F) / 2.0F;
            sb.setColor(Color.WHITE);
            sb.draw(CInputActionSet.cancel.getKeyImg(), current_x - 32F - controllerImgTextWidth - 38F * Settings.scale, TAKE_Y - 32F, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        }
        hb.render(sb);
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private static final int W = 512;
    private static final int H = 256;
    public static final float TAKE_Y;
    private static final float SHOW_X;
    private static final float HIDE_X;
    private float current_x;
    private float target_x;
    private boolean isHidden;
    private Color textColor;
    private Color btnColor;
    public boolean screenDisabled;
    private static final float HITBOX_W;
    private static final float HITBOX_H;
    public Hitbox hb;
    private float controllerImgTextWidth;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("CardRewardScreen");
        TEXT = uiStrings.TEXT;
        TAKE_Y = (float)Settings.HEIGHT / 2.0F - 340F * Settings.scale;
        SHOW_X = (float)Settings.WIDTH / 2.0F;
        HIDE_X = (float)Settings.WIDTH / 2.0F;
        HITBOX_W = 260F * Settings.scale;
        HITBOX_H = 80F * Settings.scale;
    }
}
