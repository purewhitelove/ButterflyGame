// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CeilingDustCloudEffect.java

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

public class CeilingDustCloudEffect extends AbstractGameEffect
{

    public CeilingDustCloudEffect(float x, float y)
    {
        if(img == null)
            img = ImageMaster.vfxAtlas.findRegion("env/dustCloud");
        this.x = (x + MathUtils.random(-40F, 40F) * Settings.scale) - (float)img.packedWidth / 2.0F;
        this.y = y - (float)img.packedHeight / 2.0F;
        float randY = MathUtils.random(-10F, 10F) * Settings.scale;
        y += randY;
        renderBehind = randY < 0.0F;
        vY = MathUtils.random(0.0F, 20F) * Settings.scale;
        vX = MathUtils.random(-30F, 30F) * Settings.scale;
        duration = MathUtils.random(3F, 7F);
        scale = Settings.scale * MathUtils.random(0.1F, 0.7F);
        rotation = MathUtils.random(0.0F, 360F);
        float c = MathUtils.random(0.1F, 0.3F);
        color = new Color(c + 0.1F, c, c, 0.0F);
        color.a = MathUtils.random(0.1F, 0.2F);
        startingAlpha = color.a;
        aV = MathUtils.random(-0.1F, 0.1F);
    }

    public void update()
    {
        rotation += aV;
        y -= vY * Gdx.graphics.getDeltaTime();
        x += vX * Gdx.graphics.getDeltaTime();
        vY += vYAccel * Gdx.graphics.getDeltaTime();
        vX *= 0.99F;
        scale += Gdx.graphics.getDeltaTime() * 0.2F;
        if(duration < 3F)
            color.a = Interpolation.fade.apply(startingAlpha, 0.0F, 1.0F - duration / 3F);
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
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
    private float vX;
    private float vYAccel;
    private float aV;
    private float startingAlpha;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
}
