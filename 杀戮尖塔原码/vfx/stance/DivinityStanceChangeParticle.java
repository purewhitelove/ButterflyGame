// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DivinityStanceChangeParticle.java

package com.megacrit.cardcrawl.vfx.stance;

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
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class DivinityStanceChangeParticle extends AbstractGameEffect
{

    public DivinityStanceChangeParticle(Color color, float x, float y)
    {
        img = ImageMaster.STRIKE_LINE;
        startingDuration = 0.5F;
        duration = startingDuration;
        this.color = color.cpy();
        rotation = MathUtils.random(360F);
        oX = (x - (float)img.packedWidth / 2.0F) + MathUtils.random(-10F, 10F) * Settings.scale;
        oY = (y - (float)img.packedHeight / 2.0F) + MathUtils.random(-10F, 10F) * Settings.scale;
        distOffset = MathUtils.random(800F, 1200F);
        renderBehind = true;
        aV = MathUtils.random(50F, 80F);
        scaleOffset = MathUtils.random(4F, 5F);
        aV = MathUtils.random(0.4F);
    }

    public void update()
    {
        if(aV > 0.0F)
        {
            aV -= Gdx.graphics.getDeltaTime();
            return;
        }
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
        {
            isDone = true;
            return;
        } else
        {
            x = oX + MathUtils.cosDeg(rotation) * distOffset * Interpolation.pow2In.apply(0.02F, 0.95F, duration * 2.0F) * Settings.scale;
            y = oY + MathUtils.sinDeg(rotation) * distOffset * Interpolation.pow3In.apply(0.02F, 0.95F, duration * 2.0F) * Settings.scale;
            duration -= Gdx.graphics.getDeltaTime();
            scale = scaleOffset * (duration + 0.1F) * Settings.scale;
            color.a = Interpolation.pow3In.apply(0.0F, 1.0F, duration * 2.0F);
            return;
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.setBlendFunction(770, 1);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private float oX;
    private float oY;
    private float x;
    private float y;
    private float aV;
    private float distOffset;
    private float scaleOffset;
}
