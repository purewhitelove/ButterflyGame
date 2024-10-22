// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MenuCancelButton.java

package com.megacrit.cardcrawl.screens.mainMenu;

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

public class MenuCancelButton
{

    public MenuCancelButton()
    {
        current_x = HIDE_X;
        target_x = current_x;
        isHidden = true;
        glowAlpha = 0.0F;
        glowColor = Settings.GOLD_COLOR.cpy();
        buttonText = "NOT_SET";
        hb = new Hitbox(300F * Settings.scale, 100F * Settings.scale);
        hb.move(SHOW_X - 106F * Settings.scale, DRAW_Y + 60F * Settings.scale);
    }

    public void update()
    {
        if(!isHidden)
        {
            updateGlow();
            hb.update();
            if(InputHelper.justClickedLeft && hb.hovered)
            {
                hb.clickStarted = true;
                CardCrawlGame.sound.play("UI_CLICK_1");
            }
            if(hb.justHovered)
                CardCrawlGame.sound.play("UI_HOVER");
            if(CInputActionSet.cancel.isJustPressed())
                hb.clicked = true;
        }
        if(current_x != target_x)
        {
            current_x = MathUtils.lerp(current_x, target_x, Gdx.graphics.getDeltaTime() * 9F);
            if(Math.abs(current_x - target_x) < Settings.UI_SNAP_THRESHOLD)
                current_x = target_x;
        }
    }

    private void updateGlow()
    {
        glowAlpha += Gdx.graphics.getDeltaTime() * 3F;
        if(glowAlpha < 0.0F)
            glowAlpha *= -1F;
        float tmp = MathUtils.cos(glowAlpha);
        if(tmp < 0.0F)
            glowColor.a = -tmp / 2.0F + 0.3F;
        else
            glowColor.a = tmp / 2.0F + 0.3F;
    }

    public boolean hovered()
    {
        return hb.hovered;
    }

    public void hide()
    {
        if(!isHidden)
        {
            hb.clicked = false;
            hb.hovered = false;
            InputHelper.justClickedLeft = false;
            target_x = HIDE_X;
            isHidden = true;
        }
    }

    public void hideInstantly()
    {
        if(!isHidden)
        {
            hb.hovered = false;
            InputHelper.justClickedLeft = false;
            target_x = HIDE_X;
            current_x = target_x;
            isHidden = true;
        }
    }

    public void show(String buttonText)
    {
        if(isHidden)
        {
            glowAlpha = 0.0F;
            current_x = HIDE_X;
            target_x = SHOW_X;
            isHidden = false;
            this.buttonText = buttonText;
        } else
        {
            current_x = HIDE_X;
            this.buttonText = buttonText;
        }
        hb.hovered = false;
    }

    public void showInstantly(String buttonText)
    {
        current_x = SHOW_X;
        target_x = SHOW_X;
        isHidden = false;
        this.buttonText = buttonText;
        hb.hovered = false;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(Color.WHITE);
        renderShadow(sb);
        sb.setColor(glowColor);
        renderOutline(sb);
        sb.setColor(Color.WHITE);
        renderButton(sb);
        if(hb.hovered && !hb.clickStarted)
        {
            sb.setBlendFunction(770, 1);
            sb.setColor(HOVER_BLEND_COLOR);
            renderButton(sb);
            sb.setBlendFunction(770, 771);
        }
        Color tmpColor = Settings.LIGHT_YELLOW_COLOR;
        if(hb.clickStarted)
            tmpColor = Color.LIGHT_GRAY;
        if(Settings.isControllerMode)
            FontHelper.renderFontLeft(sb, FontHelper.buttonLabelFont, buttonText, (current_x + TEXT_OFFSET_X) - 30F * Settings.scale, DRAW_Y + TEXT_OFFSET_Y, tmpColor);
        else
            FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, buttonText, current_x + TEXT_OFFSET_X, DRAW_Y + TEXT_OFFSET_Y, tmpColor);
        renderControllerUi(sb);
        if(!isHidden)
            hb.render(sb);
    }

    private void renderShadow(SpriteBatch sb)
    {
        sb.draw(ImageMaster.CANCEL_BUTTON_SHADOW, current_x - 256F, DRAW_Y - 128F, 256F, 128F, 512F, 256F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 256, false, false);
    }

    private void renderOutline(SpriteBatch sb)
    {
        sb.draw(ImageMaster.CANCEL_BUTTON_OUTLINE, current_x - 256F, DRAW_Y - 128F, 256F, 128F, 512F, 256F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 256, false, false);
    }

    private void renderButton(SpriteBatch sb)
    {
        sb.draw(ImageMaster.CANCEL_BUTTON, current_x - 256F, DRAW_Y - 128F, 256F, 128F, 512F, 256F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 256, false, false);
    }

    private void renderControllerUi(SpriteBatch sb)
    {
        if(Settings.isControllerMode)
        {
            sb.setColor(Color.WHITE);
            sb.draw(CInputActionSet.cancel.getKeyImg(), current_x - 32F - 210F * Settings.scale, (DRAW_Y - 32F) + 57F * Settings.scale, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        }
    }

    private static final int W = 512;
    private static final int H = 256;
    private static final Color HOVER_BLEND_COLOR = new Color(1.0F, 1.0F, 1.0F, 0.4F);
    private static final float SHOW_X;
    private static final float DRAW_Y;
    public static final float HIDE_X;
    public float current_x;
    private float target_x;
    public boolean isHidden;
    private float glowAlpha;
    private Color glowColor;
    private String buttonText;
    private static final float TEXT_OFFSET_X;
    private static final float TEXT_OFFSET_Y;
    public Hitbox hb;

    static 
    {
        SHOW_X = 256F * Settings.scale;
        DRAW_Y = 128F * Settings.scale;
        HIDE_X = SHOW_X - 400F * Settings.scale;
        TEXT_OFFSET_X = -136F * Settings.scale;
        TEXT_OFFSET_Y = 57F * Settings.scale;
    }
}
