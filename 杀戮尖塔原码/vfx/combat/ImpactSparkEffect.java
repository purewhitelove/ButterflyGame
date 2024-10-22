// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ImpactSparkEffect.java

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

public class ImpactSparkEffect extends AbstractGameEffect
{

    public ImpactSparkEffect(float x, float y)
    {
        img = ImageMaster.GLOW_SPARK_2;
        duration = MathUtils.random(0.5F, 1.0F);
        this.x = x - (float)(img.packedWidth / 2);
        this.y = y - (float)(img.packedHeight / 2);
        color = Color.WHITE.cpy();
        rotation = MathUtils.random(0.0F, 360F);
        scale = MathUtils.random(0.2F, 1.0F) * Settings.scale;
        vX = MathUtils.random(-1500F, 1500F) * Settings.scale;
        vY = MathUtils.random(-0F, 1000F) * Settings.scale;
        floor = MathUtils.random(100F, 250F) * Settings.scale;
    }

    public void update()
    {
        vY -= GRAVITY / scale;
        x += vX * Gdx.graphics.getDeltaTime();
        y += vY * Gdx.graphics.getDeltaTime();
        Vector2 test = new Vector2(vX, vY);
        rotation = test.angle();
        if(y < floor)
        {
            vY = -vY * 0.75F;
            y = floor + 0.1F * Settings.scale;
            vX *= 1.1F;
        }
        if(1.0F - duration < 0.1F)
            color.a = Interpolation.fade.apply(0.0F, 0.5F, (1.0F - duration) * 10F);
        else
            color.a = Interpolation.pow2Out.apply(0.0F, 0.5F, duration);
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        sb.setColor(color);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * MathUtils.random(0.7F, 1.3F), scale * MathUtils.random(0.7F, 1.3F), rotation);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * MathUtils.random(0.7F, 1.3F), scale * MathUtils.random(0.7F, 1.3F), rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private static final float DUR = 1F;
    private float x;
    private float y;
    private float vX;
    private float vY;
    private float floor;
    private static final float GRAVITY;

    static 
    {
        GRAVITY = 20F * Settings.scale;
    }
}
