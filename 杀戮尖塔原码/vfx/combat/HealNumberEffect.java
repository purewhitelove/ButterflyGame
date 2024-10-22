// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HealNumberEffect.java

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

public class HealNumberEffect extends AbstractGameEffect
{

    public HealNumberEffect(float x, float y, int number)
    {
        scale = 1.0F;
        duration = 1.2F;
        startingDuration = 1.2F;
        this.x = x;
        this.y = y + OFFSET_Y;
        vX = MathUtils.random(100F * Settings.scale, 150F * Settings.scale);
        if(MathUtils.randomBoolean())
            vX = -vX;
        vY = MathUtils.random(400F * Settings.scale, 500F * Settings.scale);
        this.number = number;
        color = Color.CHARTREUSE.cpy();
    }

    public void update()
    {
        x += Gdx.graphics.getDeltaTime() * vX;
        y += Gdx.graphics.getDeltaTime() * vY;
        vY += Gdx.graphics.getDeltaTime() * GRAVITY_Y;
        super.update();
        scale = ((Settings.scale * duration) / 1.2F) * 3F;
        if(scale <= 0.0F)
            scale = 0.01F;
    }

    public void render(SpriteBatch sb)
    {
        FontHelper.damageNumberFont.getData().setScale(scale);
        FontHelper.renderFontCentered(sb, FontHelper.damageNumberFont, Integer.toString(number), x, y, color);
    }

    public void dispose()
    {
    }

    private static final float EFFECT_DUR = 1.2F;
    private float x;
    private float y;
    private float vX;
    private float vY;
    private static final float OFFSET_Y;
    private static final float GRAVITY_Y;
    private int number;
    private float scale;

    static 
    {
        OFFSET_Y = 150F * Settings.scale;
        GRAVITY_Y = -2000F * Settings.scale;
    }
}
