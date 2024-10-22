// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ConfirmButton.java

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

// Referenced classes of package com.megacrit.cardcrawl.ui.buttons:
//            CardSelectConfirmButton

public class ConfirmButton
{

    public ConfirmButton()
    {
        current_x = HIDE_X;
        target_x = current_x;
        controller_offset_x = 0.0F;
        isHidden = true;
        isDisabled = true;
        isHovered = false;
        glowAlpha = 0.0F;
        glowColor = Color.WHITE.cpy();
        buttonText = "NOT_SET";
        hb = new Hitbox(0.0F, 0.0F, 300F * Settings.scale, 100F * Settings.scale);
        updateText(CardSelectConfirmButton.TEXT[0]);
        hb.move(SHOW_X + 106F * Settings.scale, DRAW_Y + 60F * Settings.scale);
    }

    public ConfirmButton(String label)
    {
        current_x = HIDE_X;
        target_x = current_x;
        controller_offset_x = 0.0F;
        isHidden = true;
        isDisabled = true;
        isHovered = false;
        glowAlpha = 0.0F;
        glowColor = Color.WHITE.cpy();
        buttonText = "NOT_SET";
        hb = new Hitbox(0.0F, 0.0F, 300F * Settings.scale, 100F * Settings.scale);
        updateText(label);
        hb.move(SHOW_X + 106F * Settings.scale, DRAW_Y + 60F * Settings.scale);
    }

    public void updateText(String label)
    {
        buttonText = label;
        controller_offset_x = FontHelper.getSmartWidth(FontHelper.buttonLabelFont, label, 99999F, 0.0F) / 2.0F;
    }

    public void update()
    {
        if(!isHidden)
        {
            updateGlow();
            hb.update();
            if(InputHelper.justClickedLeft && hb.hovered && !isDisabled)
            {
                hb.clickStarted = true;
                CardCrawlGame.sound.play("UI_CLICK_1");
            }
            if(hb.justHovered && !isDisabled)
                CardCrawlGame.sound.play("UI_HOVER");
            isHovered = hb.hovered;
            if(CInputActionSet.select.isJustPressed())
            {
                CInputActionSet.select.unpress();
                hb.clicked = true;
            }
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

    public void hideInstantly()
    {
        current_x = HIDE_X;
        target_x = HIDE_X;
        isHidden = true;
    }

    public void hide()
    {
        if(!isHidden)
        {
            target_x = HIDE_X;
            isHidden = true;
        }
    }

    public void show()
    {
        if(isHidden)
        {
            glowAlpha = 0.0F;
            target_x = SHOW_X;
            isHidden = false;
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(Color.WHITE);
        renderShadow(sb);
        sb.setColor(glowColor);
        renderOutline(sb);
        sb.setColor(Color.WHITE);
        renderButton(sb);
        if(hb.hovered && !isDisabled && !hb.clickStarted)
        {
            sb.setBlendFunction(770, 1);
            sb.setColor(HOVER_BLEND_COLOR);
            renderButton(sb);
            sb.setBlendFunction(770, 771);
        }
        if(isDisabled)
            FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, buttonText, current_x + TEXT_OFFSET_X, DRAW_Y + TEXT_OFFSET_Y, TEXT_DISABLED_COLOR);
        else
        if(hb.clickStarted)
            FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, buttonText, current_x + TEXT_OFFSET_X, DRAW_Y + TEXT_OFFSET_Y, Color.LIGHT_GRAY);
        else
        if(hb.hovered)
            FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, buttonText, current_x + TEXT_OFFSET_X, DRAW_Y + TEXT_OFFSET_Y, Settings.LIGHT_YELLOW_COLOR);
        else
            FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, buttonText, current_x + TEXT_OFFSET_X, DRAW_Y + TEXT_OFFSET_Y, Settings.LIGHT_YELLOW_COLOR);
        renderControllerUi(sb);
        if(!isHidden)
            hb.render(sb);
    }

    private void renderShadow(SpriteBatch sb)
    {
        sb.draw(ImageMaster.CONFIRM_BUTTON_SHADOW, current_x - 256F, DRAW_Y - 128F, 256F, 128F, 512F, 256F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 256, false, false);
    }

    private void renderOutline(SpriteBatch sb)
    {
        sb.draw(ImageMaster.CONFIRM_BUTTON_OUTLINE, current_x - 256F, DRAW_Y - 128F, 256F, 128F, 512F, 256F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 256, false, false);
    }

    private void renderButton(SpriteBatch sb)
    {
        sb.draw(ImageMaster.CONFIRM_BUTTON, current_x - 256F, DRAW_Y - 128F, 256F, 128F, 512F, 256F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 256, false, false);
    }

    private void renderControllerUi(SpriteBatch sb)
    {
        if(Settings.isControllerMode)
        {
            sb.setColor(Color.WHITE);
            sb.draw(CInputActionSet.select.getKeyImg(), (current_x - 32F - controller_offset_x) + 96F * Settings.scale, (DRAW_Y - 32F) + 57F * Settings.scale, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        }
    }

    private static final int W = 512;
    private static final int H = 256;
    private static final Color HOVER_BLEND_COLOR = new Color(1.0F, 1.0F, 1.0F, 0.3F);
    private static final Color TEXT_DISABLED_COLOR = new Color(0.6F, 0.6F, 0.6F, 1.0F);
    private static final float SHOW_X;
    private static final float DRAW_Y;
    private static final float HIDE_X;
    private float current_x;
    private float target_x;
    private float controller_offset_x;
    private boolean isHidden;
    public boolean isDisabled;
    public boolean isHovered;
    private float glowAlpha;
    private Color glowColor;
    private String buttonText;
    private static final float TEXT_OFFSET_X;
    private static final float TEXT_OFFSET_Y;
    public Hitbox hb;

    static 
    {
        SHOW_X = (float)Settings.WIDTH - 256F * Settings.scale;
        DRAW_Y = 128F * Settings.scale;
        HIDE_X = SHOW_X + 400F * Settings.scale;
        TEXT_OFFSET_X = 136F * Settings.scale;
        TEXT_OFFSET_Y = 57F * Settings.scale;
    }
}
