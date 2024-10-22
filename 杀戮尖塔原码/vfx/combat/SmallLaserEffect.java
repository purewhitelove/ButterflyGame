// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SmallLaserEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class SmallLaserEffect extends AbstractGameEffect
{

    public SmallLaserEffect(float sX, float sY, float dX, float dY)
    {
        if(img == null)
            img = ImageMaster.vfxAtlas.findRegion("combat/laserThin");
        this.sX = sX;
        this.sY = sY;
        this.dX = dX;
        this.dY = dY;
        dst = Vector2.dst(this.sX, this.sY, this.dX, this.dY) / Settings.scale;
        color = Color.CYAN.cpy();
        duration = 0.5F;
        startingDuration = 0.5F;
        rotation = MathUtils.atan2(dX - sX, dY - sY);
        rotation *= 57.29578F;
        rotation = -rotation + 90F;
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        if(duration > startingDuration / 2.0F)
            color.a = Interpolation.pow2In.apply(1.0F, 0.0F, (duration - 0.25F) * 4F);
        else
            color.a = Interpolation.bounceIn.apply(0.0F, 1.0F, duration * 4F);
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        sb.setColor(color);
        sb.draw(img, sX, (sY - (float)img.packedHeight / 2.0F) + 10F * Settings.scale, 0.0F, (float)img.packedHeight / 2.0F, dst, 50F, scale + MathUtils.random(-0.01F, 0.01F), scale, rotation);
        sb.setColor(new Color(0.3F, 0.3F, 1.0F, color.a));
        sb.draw(img, sX, sY - (float)img.packedHeight / 2.0F, 0.0F, (float)img.packedHeight / 2.0F, dst, MathUtils.random(50F, 90F), scale + MathUtils.random(-0.02F, 0.02F), scale, rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private float sX;
    private float sY;
    private float dX;
    private float dY;
    private float dst;
    private static final float DUR = 0.5F;
    private static com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
}
