// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BlockedNumberEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class BlockedNumberEffect extends AbstractGameEffect
{

    public BlockedNumberEffect(float x, float y, String msg)
    {
        scale = 1.0F;
        swayTimer = 0.0F;
        duration = 2.3F;
        startingDuration = 2.3F;
        this.x = x;
        this.y = y;
        this.msg = msg;
        color = new Color(0.4F, 0.8F, 0.9F, 1.0F);
    }

    public void update()
    {
        swayTimer -= Gdx.graphics.getDeltaTime() * 4F;
        x = x + MathUtils.cos(swayTimer) * 2.0F;
        y += GRAVITY_Y * Gdx.graphics.getDeltaTime();
        super.update();
        scale = ((Settings.scale * duration) / 2.3F) * 3F;
    }

    public void render(SpriteBatch sb)
    {
        if(scale <= 0.0F)
            scale = 0.01F;
        FontHelper.damageNumberFont.getData().setScale(scale);
        FontHelper.renderFontCentered(sb, FontHelper.damageNumberFont, msg, x, y, color);
    }

    public void dispose()
    {
    }

    private static final float EFFECT_DUR = 2.3F;
    private float x;
    private float y;
    private static final float GRAVITY_Y;
    private String msg;
    private float scale;
    private float swayTimer;

    static 
    {
        GRAVITY_Y = 75F * Settings.scale;
    }
}
