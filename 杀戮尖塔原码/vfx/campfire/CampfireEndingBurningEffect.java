// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CampfireEndingBurningEffect.java

package com.megacrit.cardcrawl.vfx.campfire;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class CampfireEndingBurningEffect extends AbstractGameEffect
{

    public CampfireEndingBurningEffect()
    {
        flipX = MathUtils.randomBoolean();
        delayTimer = MathUtils.random(0.1F);
        setImg();
        startingDuration = 1.0F;
        duration = startingDuration;
        x = MathUtils.random(0.0F, Settings.WIDTH) - (float)img.packedWidth / 2.0F;
        y = (float)(-img.packedHeight) / 2.0F - 100F * Settings.scale;
        vX = MathUtils.random(-120F, 120F) * Settings.scale;
        vY = 0.0F;
        vY2 = MathUtils.random(1500F, 3000F) * Settings.scale;
        vY2 -= Math.abs(x - 1485F * Settings.scale) / 2.0F;
        color = new Color(1.0F, MathUtils.random(0.5F, 0.9F), MathUtils.random(0.2F, 0.5F), 0.0F);
        if(vX > 0.0F)
            rotation = MathUtils.random(0.0F, -15F);
        else
            rotation = MathUtils.random(0.0F, 15F);
        scale = MathUtils.random(1.5F, 4F) * Settings.scale;
    }

    public void update()
    {
        if(delayTimer > 0.0F)
        {
            delayTimer -= Gdx.graphics.getDeltaTime();
            return;
        }
        x += vX * Gdx.graphics.getDeltaTime();
        y += vY * Gdx.graphics.getDeltaTime();
        vY = MathHelper.slowColorLerpSnap(vY, vY2);
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
        else
            color.a = Interpolation.pow2Out.apply(0.0F, 0.8F, duration);
    }

    private void setImg()
    {
        int roll = MathUtils.random(2);
        if(roll == 0)
            img = ImageMaster.GLOW_SPARK;
        else
        if(roll == 1)
            img = ImageMaster.GLOW_SPARK;
        else
            img = ImageMaster.GLOW_SPARK_2;
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        sb.setColor(color);
        if(flipX && !img.isFlipX())
            img.flip(true, false);
        else
        if(!flipX && img.isFlipX())
            img.flip(true, false);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, rotation);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * MathUtils.random(0.8F, 1.2F), scale * MathUtils.random(0.8F, 1.2F), rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private float x;
    private float y;
    private float vX;
    private float vY2;
    private float vY;
    private float startingDuration;
    private boolean flipX;
    private float delayTimer;
}
