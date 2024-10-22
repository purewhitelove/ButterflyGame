// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StanceChangeAbsorptionParticle.java

package com.megacrit.cardcrawl.vfx.stance;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class StanceChangeAbsorptionParticle extends AbstractGameEffect
{

    public StanceChangeAbsorptionParticle(Color color, float x, float y)
    {
        startingDuration = 1.0F;
        duration = startingDuration;
        this.color = color.cpy();
        this.color.r -= MathUtils.random(0.1F);
        this.color.g -= MathUtils.random(0.1F);
        this.color.b -= MathUtils.random(0.1F);
        rotation = MathUtils.random(360F);
        oX = x;
        oY = y;
        distOffset = MathUtils.random(-200F, 1000F);
        renderBehind = true;
        aV = MathUtils.random(50F, 80F);
        scaleOffset = MathUtils.random(1.0F);
    }

    public void update()
    {
        x = oX + MathUtils.cosDeg(rotation) * (800F + distOffset) * duration * Settings.scale;
        y = oY + MathUtils.sinDeg(rotation) * (800F + distOffset) * duration * Settings.scale;
        duration -= Gdx.graphics.getDeltaTime();
        rotation -= duration * Interpolation.pow5Out.apply(aV, 2.0F, duration);
        scale = (duration + 0.2F + scaleOffset) * Settings.scale;
        color.a = Interpolation.pow2Out.apply(0.0F, 1.0F, duration);
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.setBlendFunction(770, 1);
        sb.draw(ImageMaster.WOBBLY_ORB_VFX, x - 16F, y - 16F, 16F, 16F, 32F, 32F, scale * MathUtils.random(0.5F, 2.0F), scale * MathUtils.random(0.5F, 2.0F), rotation - 200F, 0, 0, 32, 32, false, false);
        sb.draw(ImageMaster.WOBBLY_ORB_VFX, x - 16F, y - 16F, 16F, 16F, 32F, 32F, scale * MathUtils.random(0.6F, 2.5F), scale * MathUtils.random(0.6F, 2.5F), rotation - 200F, 0, 0, 32, 32, false, false);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private float oX;
    private float oY;
    private float x;
    private float y;
    private float aV;
    private float distOffset;
    private float scaleOffset;
}
