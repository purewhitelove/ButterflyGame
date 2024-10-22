// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ReturnToMenuButton.java

package com.megacrit.cardcrawl.ui.buttons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.vfx.TintEffect;

public class ReturnToMenuButton
{

    public ReturnToMenuButton()
    {
        tint = new TintEffect();
        textTint = new TintEffect();
        pressed = false;
        isMoving = false;
        show = false;
        tint.color.a = 0.0F;
        textTint.color.a = 0.0F;
        hb = new Hitbox(-10000F, -10000F, BUTTON_W, BUTTON_H);
    }

    public void appear(float x, float y, String label)
    {
        this.x = x;
        this.y = y;
        this.label = label;
        pressed = false;
        isMoving = true;
        show = true;
        tint.changeColor(IDLE_COLOR, 9F);
        textTint.changeColor(TEXT_SHOW_COLOR, 9F);
    }

    public void hide()
    {
        show = false;
        isMoving = false;
        tint.changeColor(FADE_COLOR, 9F);
        textTint.changeColor(FADE_COLOR, 9F);
    }

    public void update()
    {
        tint.update();
        textTint.update();
        if(show)
        {
            hb.move(x, y);
            hb.update();
            if(InputHelper.justClickedLeft && hb.hovered)
            {
                hb.clickStarted = true;
                CardCrawlGame.sound.play("UI_CLICK_1");
            }
            if(hb.justHovered)
                CardCrawlGame.sound.play("UI_HOVER");
            if(hb.hovered || Settings.isControllerMode)
                tint.changeColor(HIGHLIGHT_COLOR, 18F);
            else
                tint.changeColor(IDLE_COLOR, 9F);
        }
    }

    public void render(SpriteBatch sb)
    {
        if(textTint.color.a == 0.0F || label == null)
            return;
        if(hb.clickStarted)
            sb.setColor(Color.LIGHT_GRAY);
        else
            sb.setColor(tint.color);
        sb.draw(ImageMaster.DYNAMIC_BTN_IMG2, x - 256F, y - 256F, 256F, 256F, 512F, 512F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 512, false, false);
        if(hb.clickStarted)
            FontHelper.renderFontCentered(sb, FontHelper.panelEndTurnFont, label, x, y, Color.LIGHT_GRAY);
        else
            FontHelper.renderFontCentered(sb, FontHelper.panelEndTurnFont, label, x, y, tint.color);
        if(Settings.isControllerMode)
            sb.draw(CInputActionSet.select.getKeyImg(), (float)Settings.WIDTH / 2.0F - 32F - 100F * Settings.scale, y - 32F, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        if(!pressed && show)
            hb.render(sb);
    }

    public static final int RAW_W = 512;
    private static final float BUTTON_W;
    private static final float BUTTON_H;
    private static final float LERP_SPEED = 9F;
    private static final Color TEXT_SHOW_COLOR = new Color(0.9F, 0.9F, 0.9F, 1.0F);
    private static final Color HIGHLIGHT_COLOR = new Color(1.0F, 1.0F, 1.0F, 1.0F);
    private static final Color IDLE_COLOR = new Color(0.7F, 0.7F, 0.7F, 1.0F);
    private static final Color FADE_COLOR = new Color(0.3F, 0.3F, 0.3F, 1.0F);
    public String label;
    public float x;
    public float y;
    public Hitbox hb;
    protected TintEffect tint;
    protected TintEffect textTint;
    public boolean pressed;
    public boolean isMoving;
    public boolean show;
    public int height;
    public int width;

    static 
    {
        BUTTON_W = 240F * Settings.scale;
        BUTTON_H = 160F * Settings.scale;
    }
}
