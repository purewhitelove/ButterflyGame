// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BuffParticleEffect.java

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

public class BuffParticleEffect extends AbstractGameEffect
{

    public BuffParticleEffect(float x, float y)
    {
        scale = 0.0F;
        this.x = x + MathUtils.random(-25F, 25F) * Settings.scale;
        this.y = y + MathUtils.random(-20F, 10F) * Settings.scale;
        duration = 0.5F;
        rotation = MathUtils.random(-5F, 5F);
        switch(MathUtils.random(2))
        {
        case 0: // '\0'
            img = ImageMaster.vfxAtlas.findRegion("buffVFX1");
            break;

        case 1: // '\001'
            img = ImageMaster.vfxAtlas.findRegion("buffVFX2");
            break;

        default:
            img = ImageMaster.vfxAtlas.findRegion("buffVFX3");
            break;
        }
        renderBehind = MathUtils.randomBoolean();
        vY = MathUtils.random(30F, 50F) * Settings.scale;
        color = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        scale = MathUtils.random(1.0F, 1.5F) * Settings.scale;
    }

    public void update()
    {
        scale += Gdx.graphics.getDeltaTime() / 2.0F;
        if(duration > 0.5F)
            color.a = Interpolation.fade.apply(0.0F, 1.0F, 1.0F - (duration - 3F));
        else
        if(duration < 0.5F)
            color.a = Interpolation.fade.apply(1.0F, 0.0F, 1.0F - duration * 2.0F);
        y += Gdx.graphics.getDeltaTime() * vY;
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        sb.setColor(color);
        sb.draw(img, x - (float)img.packedWidth / 2.0F, y - (float)img.packedHeight / 2.0F, img.offsetX, img.offsetY, img.packedWidth, img.packedHeight, scale, scale, rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private static final float DURATION = 0.5F;
    private float x;
    private float y;
    private float vY;
    private float scale;
}
