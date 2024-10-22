// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WaterSplashParticleEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class WaterSplashParticleEffect extends AbstractGameEffect
{

    public WaterSplashParticleEffect(float x, float y)
    {
        img = ImageMaster.DECK_GLOW_1;
        duration = MathUtils.random(0.5F, 1.0F);
        this.x = (x - (float)(img.packedWidth / 2)) + MathUtils.random(-10F, 10F) * Settings.scale;
        this.y = y - (float)(img.packedHeight / 2) - 40F * Settings.scale;
        color = new Color(1.0F, 0.2F, 0.1F, 0.0F);
        color.a = 0.0F;
        scale = MathUtils.random(1.5F, 3.5F) * Settings.scale;
        vX = MathUtils.random(-120F, 120F) * Settings.scale;
        vY = MathUtils.random(150F, 300F) * Settings.scale;
        floor = y - 40F * Settings.scale;
    }

    public void update()
    {
        vY -= 1000F * Settings.scale * Gdx.graphics.getDeltaTime();
        x += vX * Gdx.graphics.getDeltaTime();
        y += vY * Gdx.graphics.getDeltaTime();
        Vector2 test = new Vector2(vX, vY);
        rotation = test.angle() + 45F;
        scale -= Gdx.graphics.getDeltaTime() / 2.0F;
        if(y < floor && vY < 0.0F && duration > 0.2F)
            duration = 0.2F;
        if(duration < 0.2F)
            color.a = Interpolation.fade.apply(0.0F, 1.0F, duration * 5F);
        else
            color.a = 1.0F;
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale * 0.54F, rotation);
    }

    public void dispose()
    {
    }

    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private float x;
    private float y;
    private float vX;
    private float vY;
    private float floor;
}
