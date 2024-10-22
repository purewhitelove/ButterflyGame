// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FastingEffect.java

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

public class FastingEffect extends AbstractGameEffect
{

    public FastingEffect(float x, float y, Color c)
    {
        if(img == null)
            img = ImageMaster.WHITE_RING;
        startingDuration = 1.0F;
        duration = 1.0F;
        scale = 3F * Settings.scale;
        color = c.cpy();
        color.a = 0.0F;
        rotation = MathUtils.random(0.0F, 360F);
        this.x = x - (float)img.packedWidth / 2.0F;
        this.y = y - (float)img.packedHeight / 2.0F;
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        rotation -= Gdx.graphics.getDeltaTime() * 205F;
        if(duration > 0.5F)
        {
            color.a = Interpolation.fade.apply(0.45F, 0.0F, (duration - 0.5F) * 2.0F);
        } else
        {
            color.a = Interpolation.fade.apply(0.0F, 0.45F, duration * 2.0F);
            scale = Interpolation.swingOut.apply(0.0F, 3F * Settings.scale, duration * 2.0F);
        }
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        sb.setColor(color);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale + MathUtils.random(-0.05F, 0.05F), scale + MathUtils.random(-0.05F, 0.05F), rotation);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale + MathUtils.random(-0.05F, 0.05F), scale + MathUtils.random(-0.05F, 0.05F), rotation + 180F);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private static com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private float x;
    private float y;
}
