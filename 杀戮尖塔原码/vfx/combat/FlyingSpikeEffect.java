// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FlyingSpikeEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class FlyingSpikeEffect extends AbstractGameEffect
{

    public FlyingSpikeEffect(float x, float y, float startingRotation, float vX, float vY, Color color)
    {
        img = ImageMaster.THICK_3D_LINE;
        duration = 0.75F;
        rotation = startingRotation;
        this.x = x - (float)img.packedWidth / 2.0F;
        this.y = y - (float)img.packedHeight / 2.0F;
        this.vX = vX;
        this.vY = vY;
        this.color = new Color(color.r, color.g, color.b, 0.0F);
        renderBehind = true;
        scale = 0.01F;
        rotation += MathUtils.random(-5F, 5F);
    }

    public void update()
    {
        scale = duration * 2.0F * Settings.scale;
        x += vX * Gdx.graphics.getDeltaTime();
        y += vY * Gdx.graphics.getDeltaTime();
        duration -= Gdx.graphics.getDeltaTime();
        if(duration > 0.5F)
            color.a = (0.75F - duration) * 2.0F;
        else
            color.a = duration;
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        sb.setColor(color);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private static final float DURATION = 0.75F;
    private float x;
    private float y;
    private float vX;
    private float vY;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
}
