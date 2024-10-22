// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BlurWaveAdditiveEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class BlurWaveAdditiveEffect extends AbstractGameEffect
{

    public BlurWaveAdditiveEffect(float x, float y, Color color, float chosenSpeed)
    {
        img = ImageMaster.BLUR_WAVE;
        stallTimer = MathUtils.random(0.0F, 0.3F);
        rotation = MathUtils.random(360F);
        scale = MathUtils.random(0.5F, 0.9F);
        this.x = x - (float)img.packedWidth / 2.0F;
        this.y = y - (float)img.packedHeight / 2.0F;
        duration = 2.0F;
        this.color = color;
        renderBehind = MathUtils.randomBoolean();
        speedStart = chosenSpeed;
        speedTarget = 2000F * Settings.scale;
        speed = speedStart;
        flipper = 270F;
        color.g -= MathUtils.random(0.1F);
        color.b -= MathUtils.random(0.2F);
        color.a = 0.0F;
    }

    public void update()
    {
        stallTimer -= Gdx.graphics.getDeltaTime();
        if(stallTimer < 0.0F)
        {
            Vector2 tmp = new Vector2(MathUtils.cosDeg(rotation), MathUtils.sinDeg(rotation));
            tmp.x *= speed * Gdx.graphics.getDeltaTime();
            tmp.y *= speed * Gdx.graphics.getDeltaTime();
            speed = Interpolation.fade.apply(speedStart, speedTarget, 1.0F - duration / 2.0F);
            x += tmp.x;
            y += tmp.y;
            scale *= 1.0F + Gdx.graphics.getDeltaTime() * 2.0F;
            duration -= Gdx.graphics.getDeltaTime();
            if(duration < 0.0F)
                isDone = true;
            else
            if(duration > 1.5F)
                color.a = Interpolation.fade.apply(0.0F, 0.7F, (2.0F - duration) * 2.0F);
            else
            if(duration < 0.5F)
                color.a = Interpolation.fade.apply(0.0F, 0.7F, duration * 2.0F);
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        sb.setColor(color);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale + MathUtils.random(-0.08F, 0.08F), scale + MathUtils.random(-0.08F, 0.08F), rotation + flipper + MathUtils.random(-3F, 3F));
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private static final float EFFECT_DUR = 2F;
    private float x;
    private float y;
    private float speed;
    private float speedStart;
    private float speedTarget;
    private float stallTimer;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private float flipper;
}
