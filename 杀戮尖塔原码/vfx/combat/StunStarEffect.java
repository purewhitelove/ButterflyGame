// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StunStarEffect.java

package com.megacrit.cardcrawl.vfx.combat;

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

public class StunStarEffect extends AbstractGameEffect
{

    public StunStarEffect(float x, float y)
    {
        duration = 2.0F;
        img = ImageMaster.TINY_STAR;
        this.x = x - (float)img.packedWidth / 2.0F;
        this.y = y - (float)img.packedWidth / 2.0F;
        vX = 128F * Settings.scale;
        color = new Color(1.0F, 0.9F, 0.3F, 0.0F);
        renderBehind = false;
        scale = Settings.scale;
        rotation = MathUtils.random(360F);
    }

    public void update()
    {
        vX = MathUtils.cos(3.141593F * duration);
        vY = MathUtils.cos(3.141593F * duration * 2.0F);
        rotation -= Gdx.graphics.getDeltaTime() * 60F;
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
        else
        if(duration < 1.0F)
        {
            color.a = Interpolation.pow5Out.apply(duration);
            color.r = Interpolation.pow2Out.apply(duration);
            color.g = Interpolation.pow2Out.apply(duration) * 0.9F;
            color.b = Interpolation.pow2Out.apply(duration) * 0.3F;
        } else
        if(duration > 1.0F)
        {
            color.a = Interpolation.pow5Out.apply(1.0F - (duration - 1.0F));
            color.r = Interpolation.pow4Out.apply(1.0F - (duration - 1.0F));
            color.g = Interpolation.pow4Out.apply(1.0F - (duration - 1.0F)) * 0.9F;
            color.b = Interpolation.pow4Out.apply(1.0F - (duration - 1.0F)) * 0.3F;
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.draw(img, x - vX * 30F * Settings.scale, y - vY * 5F * Settings.scale, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, rotation);
    }

    public void dispose()
    {
    }

    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private static final float DURATION = 2F;
    private float x;
    private float y;
    private float vX;
    private float vY;
    private float scale;
}
