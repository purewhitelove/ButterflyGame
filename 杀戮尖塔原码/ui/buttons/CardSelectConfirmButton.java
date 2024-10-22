// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CardSelectConfirmButton.java

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
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;

public class CardSelectConfirmButton
{

    public CardSelectConfirmButton()
    {
        current_x = HIDE_X;
        target_x = current_x;
        isHidden = true;
        isDisabled = true;
        textColor = Color.WHITE.cpy();
        btnColor = Color.WHITE.cpy();
        target_a = 0.0F;
        buttonText = "NOT_SET";
        hb = new Hitbox(0.0F, 0.0F, HITBOX_W, HITBOX_H);
        buttonText = TEXT[0];
        hb.move((float)Settings.WIDTH / 2.0F, TAKE_Y);
    }

    public void update()
    {
        if(!isHidden)
            hb.update();
        if(!isDisabled)
        {
            if(hb.justHovered)
                CardCrawlGame.sound.play("UI_HOVER");
            if(hb.hovered && InputHelper.justClickedLeft)
            {
                hb.clickStarted = true;
                CardCrawlGame.sound.play("UI_CLICK_1");
            }
        }
        if(current_x != target_x)
        {
            current_x = MathUtils.lerp(current_x, target_x, Gdx.graphics.getDeltaTime() * 9F);
            if(Math.abs(current_x - target_x) < Settings.UI_SNAP_THRESHOLD)
                current_x = target_x;
        }
        textColor.a = MathHelper.fadeLerpSnap(textColor.a, target_a);
        btnColor.a = textColor.a;
    }

    public void hideInstantly()
    {
        current_x = HIDE_X;
        target_x = HIDE_X;
        isHidden = true;
        target_a = 0.0F;
        textColor.a = 0.0F;
    }

    public void hide()
    {
        if(!isHidden)
        {
            target_a = 0.0F;
            target_x = HIDE_X;
            isHidden = true;
        }
    }

    public void show()
    {
        if(isHidden)
        {
            textColor.a = 0.0F;
            target_a = 1.0F;
            target_x = SHOW_X;
            isHidden = false;
        }
    }

    public void disable()
    {
        if(!isDisabled)
        {
            hb.hovered = false;
            isDisabled = true;
            btnColor = Color.GRAY.cpy();
            textColor = Color.LIGHT_GRAY.cpy();
        }
    }

    public void enable()
    {
        if(isDisabled)
        {
            isDisabled = false;
            btnColor = Color.WHITE.cpy();
            textColor = Settings.CREAM_COLOR.cpy();
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(btnColor);
        renderButton(sb);
        if(hb.hovered && !isDisabled && !hb.clickStarted)
        {
            sb.setBlendFunction(770, 1);
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.3F));
            renderButton(sb);
            sb.setBlendFunction(770, 771);
        }
        if(!isHidden)
            FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, buttonText, (float)Settings.WIDTH / 2.0F, TAKE_Y, textColor);
    }

    private void renderButton(SpriteBatch sb)
    {
        if(!isHidden)
        {
            if(isDisabled)
            {
                sb.draw(ImageMaster.REWARD_SCREEN_TAKE_USED_BUTTON, (float)Settings.WIDTH / 2.0F - 256F, TAKE_Y - 128F, 256F, 128F, 512F, 256F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 256, false, false);
            } else
            {
                if(hb.clickStarted)
                    sb.setColor(Color.LIGHT_GRAY);
                sb.draw(ImageMaster.REWARD_SCREEN_TAKE_BUTTON, (float)Settings.WIDTH / 2.0F - 256F, TAKE_Y - 128F, 256F, 128F, 512F, 256F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 256, false, false);
            }
            if(Settings.isControllerMode)
                sb.draw(CInputActionSet.proceed.getKeyImg(), hb.cX - 32F - 100F * Settings.scale, (hb.cY - 32F) + 2.0F * Settings.scale, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
            hb.render(sb);
        }
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private static final int W = 512;
    private static final int H = 256;
    private static final float TAKE_Y;
    private static final float SHOW_X;
    private static final float HIDE_X;
    private float current_x;
    private float target_x;
    private boolean isHidden;
    public boolean isDisabled;
    private Color textColor;
    private Color btnColor;
    private float target_a;
    private String buttonText;
    private static final float HITBOX_W;
    private static final float HITBOX_H;
    public Hitbox hb;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("Confirm Button");
        TEXT = uiStrings.TEXT;
        TAKE_Y = 475F * Settings.scale;
        SHOW_X = (float)Settings.WIDTH - 256F * Settings.scale;
        HIDE_X = SHOW_X + 400F * Settings.scale;
        HITBOX_W = 260F * Settings.scale;
        HITBOX_H = 80F * Settings.scale;
    }
}
