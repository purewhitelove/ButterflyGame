// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SlowFireParticleEffect.java

package com.megacrit.cardcrawl.vfx.scene;

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

public class SlowFireParticleEffect extends AbstractGameEffect
{

    public SlowFireParticleEffect()
    {
        setImg();
        renderBehind = true;
        startingDuration = 2.0F;
        duration = startingDuration;
        x = MathUtils.random(0.0F, Settings.WIDTH) - (float)img.packedWidth / 2.0F;
        y = (float)(-img.packedHeight) / 2.0F - 100F * Settings.scale;
        vX = MathUtils.random(-120F, 120F) * Settings.scale;
        vY2 = MathUtils.random(5F, 30F);
        vY2 *= vY2;
        vY2 *= Settings.scale;
        color = new Color(MathUtils.random(0.3F, 0.4F), MathUtils.random(0.3F, 0.7F), MathUtils.random(0.8F, 1.0F), 0.0F);
        if(vX > 0.0F)
            rotation = MathUtils.random(0.0F, -15F);
        else
            rotation = MathUtils.random(0.0F, 15F);
        scale = MathUtils.random(0.3F, 3F) * Settings.scale;
    }

    public void update()
    {
        x += vX * Gdx.graphics.getDeltaTime();
        y += vY2 * Gdx.graphics.getDeltaTime();
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
        else
            color.a = Interpolation.pow2Out.apply(0.0F, 0.7F, duration);
    }

    private void setImg()
    {
        if(MathUtils.randomBoolean())
            img = ImageMaster.GLOW_SPARK_2;
        else
            img = ImageMaster.GLOW_SPARK;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * MathUtils.random(0.8F, 1.2F), scale * MathUtils.random(0.8F, 1.2F), rotation);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * MathUtils.random(0.8F, 1.2F), scale * MathUtils.random(0.8F, 1.2F), rotation);
    }

    public void dispose()
    {
    }

    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private float x;
    private float y;
    private float vX;
    private float vY2;
    private float startingDuration;
}
