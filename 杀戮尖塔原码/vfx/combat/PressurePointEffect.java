// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PressurePointEffect.java

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

public class PressurePointEffect extends AbstractGameEffect
{

    public PressurePointEffect(float setX, float setY)
    {
        img = ImageMaster.DAGGER_STREAK;
        setX -= 120F * Settings.scale;
        setY -= -80F * Settings.scale;
        endX = setX - (float)img.packedWidth / 2.0F;
        endY = setY - (float)img.packedHeight / 2.0F;
        x = endX + MathUtils.random(-550F, -450F) * Settings.scale;
        y = endY + MathUtils.random(380F, 320F) * Settings.scale;
        startingDuration = 0.3F;
        duration = 0.3F;
        scaleMultiplier = MathUtils.random(0.05F, 0.2F);
        rotation = 150F;
        color = Color.VIOLET.cpy();
        color.a = 0.0F;
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        x = Interpolation.swingIn.apply(endX, x, duration * 3.33F);
        y = Interpolation.swingIn.apply(endY, y, duration * 3.33F);
        if(duration < 0.0F)
        {
            isDone = true;
            duration = 0.0F;
        }
        color.a = 1.0F - duration;
        scale = duration * Settings.scale + scaleMultiplier;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale * 1.5F, rotation);
        sb.setBlendFunction(770, 1);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * 0.75F, scale * 0.75F, rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private float endX;
    private float endY;
    private float scaleMultiplier;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
}
