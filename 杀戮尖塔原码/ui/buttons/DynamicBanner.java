// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DynamicBanner.java

package com.megacrit.cardcrawl.ui.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.TintEffect;

public class DynamicBanner
{

    public DynamicBanner()
    {
        animateTimer = 0.0F;
        tint = new TintEffect();
        textTint = new TintEffect();
        pressed = false;
        isMoving = false;
        show = false;
        isLarge = false;
        tint.color.a = 0.0F;
        textTint.color.a = 0.0F;
    }

    public void appear()
    {
        appear(Y, label);
    }

    public void appear(String label)
    {
        appear(Y, label);
    }

    public void appearInstantly(String label)
    {
        appearInstantly(Y, label);
    }

    public void appear(float y, String label)
    {
        startY = y + Y_OFFSET;
        this.y = y + Y_OFFSET;
        targetY = y;
        this.label = label;
        scale = 0.25F;
        pressed = false;
        isMoving = true;
        show = true;
        animateTimer = 0.5F;
        tint.changeColor(IDLE_COLOR, 9F);
        textTint.changeColor(TEXT_SHOW_COLOR, 9F);
    }

    public void appearInstantly(float y, String label)
    {
        isMoving = false;
        animateTimer = 0.0F;
        this.y = y;
        targetY = y;
        scale = 1.2F;
        this.label = label;
        pressed = false;
        show = true;
        tint.changeColor(IDLE_COLOR, 9F);
        textTint.changeColor(TEXT_SHOW_COLOR, 9F);
    }

    public void hide()
    {
        show = false;
        isMoving = false;
        tint.changeColor(FADE_COLOR, 18F);
        textTint.changeColor(FADE_COLOR, 18F);
    }

    public void update()
    {
        tint.update();
        textTint.update();
        if(show)
        {
            animateTimer -= Gdx.graphics.getDeltaTime();
            if(animateTimer < 0.0F)
            {
                animateTimer = 0.0F;
                isMoving = false;
            } else
            {
                y = Interpolation.swingOut.apply(startY, targetY, (0.5F - animateTimer) * 2.0F);
                scale = Interpolation.swingOut.apply(0.0F, 1.2F, (0.5F - animateTimer) * 2.0F);
                if(scale <= 0.0F)
                    scale = 0.01F;
            }
            tint.changeColor(IDLE_COLOR, 9F);
        }
    }

    public void render(SpriteBatch sb)
    {
        if(textTint.color.a != 0.0F && label != null)
        {
            sb.setColor(tint.color);
            sb.draw(ImageMaster.VICTORY_BANNER, (float)Settings.WIDTH / 2.0F - 556F, y - 119F, 556F, 119F, 1112F, 238F, Settings.scale, Settings.scale, 0.0F, 0, 0, 1112, 238, false, false);
            FontHelper.renderFontCentered(sb, FontHelper.losePowerFont, label, (float)Settings.WIDTH / 2.0F, y + 22F * Settings.scale, textTint.color, scale);
        }
    }

    public static final int RAW_W = 1112;
    public static final int RAW_H = 238;
    private static final float Y_OFFSET;
    private static final float ANIM_TIME = 0.5F;
    private static final float LERP_SPEED = 9F;
    private static final Color TEXT_SHOW_COLOR = new Color(0.9F, 0.9F, 0.9F, 1.0F);
    private static final Color IDLE_COLOR = new Color(0.7F, 0.7F, 0.7F, 1.0F);
    private static final Color FADE_COLOR = new Color(1.0F, 1.0F, 1.0F, 0.0F);
    private String label;
    private float animateTimer;
    public float y;
    public float targetY;
    public float startY;
    public float scale;
    public static final float Y;
    protected TintEffect tint;
    protected TintEffect textTint;
    public boolean pressed;
    public boolean isMoving;
    public boolean show;
    public boolean isLarge;
    public int height;
    public int width;

    static 
    {
        Y_OFFSET = -50F * Settings.scale;
        Y = (float)Settings.HEIGHT / 2.0F + 260F * Settings.scale;
    }
}
