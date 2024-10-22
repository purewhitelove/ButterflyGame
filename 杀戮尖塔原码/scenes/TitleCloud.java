// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TitleCloud.java

package com.megacrit.cardcrawl.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;

public class TitleCloud
{

    public TitleCloud(com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion region, float vX, float x)
    {
        this.region = region;
        this.vX = vX;
        this.x = x;
        y = ((float)Settings.HEIGHT - 1100F * Settings.scale) + MathUtils.random(-50F, 50F) * Settings.scale;
        vY = MathUtils.random(-vX / 10F, vX / 10F) * Settings.scale;
        sliderJiggle = MathUtils.random(-4F, 4F);
    }

    public void update()
    {
        x += vX * Gdx.graphics.getDeltaTime();
        y += vY * Gdx.graphics.getDeltaTime();
        if(vX > 0.0F && x > 1920F * Settings.xScale)
        {
            x = -1920F * Settings.xScale;
            vX = MathUtils.random(10F, 50F) * Settings.xScale;
            y = ((float)Settings.HEIGHT - 1100F * Settings.scale) + MathUtils.random(-50F, 50F) * Settings.scale;
            vY = MathUtils.random(-vX / 5F, vX / 5F) * Settings.scale;
        } else
        if(x < -1920F * Settings.xScale)
        {
            x = 1920F * Settings.xScale;
            vX = MathUtils.random(-50F, -10F) * Settings.xScale;
            y = ((float)Settings.HEIGHT - 1100F * Settings.scale) + MathUtils.random(-50F, 50F) * Settings.scale;
            vY = MathUtils.random(-vX / 5F, vX / 5F) * Settings.scale;
        }
    }

    public void render(SpriteBatch sb, float slider)
    {
        renderRegion(sb, region, x, (-55F + sliderJiggle) * Settings.scale * slider + y);
    }

    private void renderRegion(SpriteBatch sb, com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion region, float x, float y)
    {
        sb.draw(region.getTexture(), region.offsetX * Settings.scale + x, region.offsetY * Settings.scale + y, 0.0F, 0.0F, region.packedWidth, region.packedHeight, Settings.scale, Settings.scale, 0.0F, region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(), false, false);
    }

    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion region;
    private float x;
    private float y;
    private float vX;
    private float vY;
    private float sliderJiggle;
}
