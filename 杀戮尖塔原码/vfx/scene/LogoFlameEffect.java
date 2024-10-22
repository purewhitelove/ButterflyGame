// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LogoFlameEffect.java

package com.megacrit.cardcrawl.vfx.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class LogoFlameEffect extends AbstractGameEffect
{

    public LogoFlameEffect()
    {
        int roll = MathUtils.random(2);
        switch(roll)
        {
        case 0: // '\0'
            img = ImageMaster.vfxAtlas.findRegion("buffVFX1");
            break;

        case 1: // '\001'
            img = ImageMaster.vfxAtlas.findRegion("buffVFX2");
            break;

        default:
            img = ImageMaster.vfxAtlas.findRegion("buffVFX3");
            break;
        }
        offsetX = MathUtils.random(10F, 30F) * Settings.scale;
        offsetY = MathUtils.random(230F, 240F) * Settings.scale;
        vX = MathUtils.random(-10F, 10F) * Settings.scale;
        vY = MathUtils.random(30F, 70F) * Settings.scale;
        duration = MathUtils.random(1.0F, 1.5F);
        startingDuration = duration;
        color = Color.WHITE.cpy();
        color.r = MathUtils.random(1.0F);
        scale = Settings.scale;
    }

    public void update()
    {
        offsetX += vX * Gdx.graphics.getDeltaTime();
        offsetY += vY * Gdx.graphics.getDeltaTime();
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
        else
        if(duration < 0.5F)
        {
            color.a = duration;
            scale = duration * 2.0F * Settings.scale;
        } else
        if(startingDuration - duration < 0.5F)
            color.a = startingDuration - duration;
        else
            color.a = 0.5F;
    }

    public void render(SpriteBatch spritebatch)
    {
    }

    public void render(SpriteBatch sb, float x, float y)
    {
        sb.setColor(color);
        sb.draw(img, x + offsetX, y + offsetY, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, rotation);
    }

    public void dispose()
    {
    }

    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private float offsetX;
    private float offsetY;
    private float vX;
    private float vY;
    private float startingDuration;
}
