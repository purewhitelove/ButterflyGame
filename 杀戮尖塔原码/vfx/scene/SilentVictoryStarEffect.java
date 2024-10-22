// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SilentVictoryStarEffect.java

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

public class SilentVictoryStarEffect extends AbstractGameEffect
{

    public SilentVictoryStarEffect()
    {
        duration = MathUtils.random(10F, 20F);
        startingDuration = duration;
        renderBehind = true;
        if(MathUtils.randomBoolean())
        {
            img = ImageMaster.ROOM_SHINE_1;
            rotation = MathUtils.random(-5F, 5F);
        } else
        {
            img = ImageMaster.GLOW_SPARK_2;
        }
        x = MathUtils.random(-100F, 1870F) * Settings.xScale - (float)img.packedWidth / 2.0F;
        float h = MathUtils.random(0.15F, 0.9F);
        y = (float)Settings.HEIGHT * h;
        vX = MathUtils.random(12F, 20F) * Settings.scale;
        vY = MathUtils.random(-5F, 5F) * Settings.scale;
        color = new Color(MathUtils.random(0.55F, 0.6F), MathUtils.random(0.8F, 1.0F), MathUtils.random(0.95F, 1.0F), 0.0F);
        scale = h * MathUtils.random(1.5F, 1.8F) * Settings.scale;
    }

    public void update()
    {
        y += vY * Gdx.graphics.getDeltaTime();
        x += vX * Gdx.graphics.getDeltaTime();
        if(duration > startingDuration / 2.0F)
            color.a = Interpolation.fade.apply(0.9F, 0.0F, (duration - startingDuration / 2.0F) / (startingDuration / 2.0F));
        else
            color.a = Interpolation.fade.apply(0.0F, 0.9F, duration / (startingDuration / 2.0F));
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * MathUtils.random(0.9F, 1.1F), scale * MathUtils.random(0.8F, 1.3F), rotation);
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private float vX;
    private float vY;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
}
