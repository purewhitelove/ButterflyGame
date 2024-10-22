// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ThirdEyeParticleEffect.java

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

public class ThirdEyeParticleEffect extends AbstractGameEffect
{

    public ThirdEyeParticleEffect(float x, float y, float vX, float vY)
    {
        int roll = MathUtils.random(2);
        if(roll == 0)
            img = ImageMaster.FLAME_1;
        else
        if(roll == 1)
            img = ImageMaster.FLAME_3;
        else
            img = ImageMaster.FLAME_3;
        this.x = x - (float)img.packedWidth / 2.0F;
        this.y = y - (float)img.packedHeight / 2.0F;
        this.vX = vX * Settings.scale;
        this.vY = vY * Settings.scale;
        rotation = (new Vector2(vX, vY)).angle() - 90F;
        scale = 3F * Settings.scale;
        color = Color.VIOLET.cpy();
        color.a = 0.0F;
        startingDuration = 0.6F;
        duration = startingDuration;
        renderBehind = true;
    }

    public void update()
    {
        x += vX * Gdx.graphics.getDeltaTime();
        y += vY * Gdx.graphics.getDeltaTime();
        if(duration > startingDuration / 2.0F)
        {
            color.a = Interpolation.pow2Out.apply(0.7F, 0.0F, (duration - startingDuration / 2.0F) / (startingDuration / 2.0F));
        } else
        {
            color.a = Interpolation.fade.apply(0.0F, 0.5F, duration / (startingDuration / 2.0F));
            scale = Interpolation.fade.apply(0.01F, 3F, duration / (startingDuration / 2.0F)) * Settings.scale;
        }
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.setBlendFunction(770, 1);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale + MathUtils.random(-0.05F, 0.05F), scale + MathUtils.random(-0.05F, 0.05F), rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private float vX;
    private float vY;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
}
