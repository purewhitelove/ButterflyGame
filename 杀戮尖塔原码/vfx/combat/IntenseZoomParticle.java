// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IntenseZoomParticle.java

package com.megacrit.cardcrawl.vfx.combat;

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

public class IntenseZoomParticle extends AbstractGameEffect
{

    public IntenseZoomParticle(float x, float y, boolean isBlack)
    {
        flickerDuration = 0.0F;
        this.isBlack = false;
        int i = MathUtils.random(2);
        if(i == 0)
            img = ImageMaster.CONE_2;
        else
        if(i == 1)
            img = ImageMaster.CONE_4;
        else
            img = ImageMaster.CONE_5;
        duration = 1.5F;
        this.isBlack = isBlack;
        if(isBlack)
            color = Color.BLACK.cpy();
        else
            color = Settings.GOLD_COLOR.cpy();
        this.x = x;
        this.y = y - (float)img.packedHeight / 2.0F;
        randomize();
    }

    public void update()
    {
        flickerDuration -= Gdx.graphics.getDeltaTime();
        if(flickerDuration < 0.0F)
        {
            randomize();
            flickerDuration = MathUtils.random(0.0F, 0.05F);
        }
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
    }

    public void randomize()
    {
        rotation = MathUtils.random(360F);
        offsetX = MathUtils.random(200F, 600F) * Settings.scale * (2.0F - duration);
        lengthX = MathUtils.random(1.0F, 1.3F);
        lengthY = MathUtils.random(0.9F, 1.2F);
        if(isBlack)
            color.a = MathUtils.random(0.5F, 1.0F) * Interpolation.pow2Out.apply(duration / 1.5F);
        else
            color.a = MathUtils.random(0.2F, 0.7F) * Interpolation.pow2Out.apply(duration / 1.5F);
    }

    public void render(SpriteBatch sb)
    {
        if(!isBlack)
            sb.setBlendFunction(770, 1);
        sb.setColor(color);
        sb.draw(img, x + offsetX, y, -offsetX, (float)img.packedHeight / 2.0F, (float)img.packedWidth * lengthX, (float)img.packedHeight * lengthY, scale, scale, rotation);
        if(!isBlack)
            sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private Color color;
    private float offsetX;
    private float flickerDuration;
    private float lengthX;
    private float lengthY;
    private boolean isBlack;
}
