// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SmokeBlurEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class SmokeBlurEffect extends AbstractGameEffect
{

    public SmokeBlurEffect(float x, float y)
    {
        color = new Color(0.0F, 0.0F, 0.0F, 1.0F);
        color.r = MathUtils.random(0.5F, 0.6F);
        color.g = color.r + MathUtils.random(0.0F, 0.2F);
        color.b = 0.2F;
        if(MathUtils.randomBoolean())
        {
            img = ImageMaster.EXHAUST_L;
            duration = MathUtils.random(2.0F, 2.5F);
            targetScale = MathUtils.random(0.8F, 2.2F);
        } else
        {
            img = ImageMaster.EXHAUST_S;
            duration = MathUtils.random(2.0F, 2.5F);
            targetScale = MathUtils.random(0.8F, 1.2F);
        }
        startDur = duration;
        this.x = (x + MathUtils.random(-180F * Settings.scale, 150F * Settings.scale)) - (float)img.packedWidth / 2.0F;
        this.y = (y + MathUtils.random(-240F * Settings.scale, 150F * Settings.scale)) - (float)img.packedHeight / 2.0F;
        scale = 0.01F;
        rotation = MathUtils.random(360F);
        aV = MathUtils.random(-250F, 250F);
        vY = MathUtils.random(1.0F * Settings.scale, 5F * Settings.scale);
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
        x += MathUtils.random(-2F * Settings.scale, 2.0F * Settings.scale);
        x += vY;
        y += MathUtils.random(-2F * Settings.scale, 2.0F * Settings.scale);
        y += vY;
        rotation += aV * Gdx.graphics.getDeltaTime();
        scale = Interpolation.exp10Out.apply(0.01F, targetScale, 1.0F - duration / startDur);
        if(duration < 0.33F)
            color.a = duration * 3F;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, rotation);
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private float vY;
    private float aV;
    private float startDur;
    private float targetScale;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
}
