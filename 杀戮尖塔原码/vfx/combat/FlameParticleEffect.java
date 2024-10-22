// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FlameParticleEffect.java

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

public class FlameParticleEffect extends AbstractGameEffect
{

    public FlameParticleEffect(float x, float y)
    {
        flipX = MathUtils.randomBoolean();
        delayTimer = MathUtils.random(0.15F);
        setImg();
        startingDuration = MathUtils.random(0.6F, 1.5F);
        duration = startingDuration;
        float r = MathUtils.random(-13F, 13F) * MathUtils.random(-13F, 13F);
        this.x = (x + r * Settings.scale) - (float)img.packedWidth / 2.0F;
        this.y = (y + MathUtils.random(-180F, 0.0F) * Settings.scale) - (float)img.packedHeight / 2.0F;
        vX = MathUtils.random(-25F, 25F) * Settings.scale;
        r = MathUtils.random(3F, 30F);
        vY = ((r * r) / startingDuration) * Settings.scale;
        vY2 = MathUtils.random(-100F, 100F) * Settings.scale;
        vS = MathUtils.random(-0.5F, 0.5F) * Settings.scale;
        color = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        color.g -= MathUtils.random(0.5F);
        color.b -= color.g - MathUtils.random(0.5F);
        rotation = MathUtils.random(-10F, 10F);
        scale = Settings.scale * MathUtils.random(0.2F, 1.5F);
        renderBehind = MathUtils.randomBoolean(0.5F);
    }

    public void update()
    {
        if(delayTimer > 0.0F)
        {
            delayTimer -= Gdx.graphics.getDeltaTime();
            return;
        }
        x += vX * Gdx.graphics.getDeltaTime();
        y += vY * Gdx.graphics.getDeltaTime();
        vY += vY2 * Gdx.graphics.getDeltaTime();
        vY *= 59F * Gdx.graphics.getDeltaTime();
        scale += vS * Gdx.graphics.getDeltaTime();
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
        else
        if(duration > startingDuration / 2.0F)
            color.a = Interpolation.fade.apply(0.0F, 0.5F, (startingDuration - duration) / (startingDuration / 2.0F));
        else
        if(duration < startingDuration / 2.0F)
            color.a = Interpolation.fade.apply(0.0F, 0.5F, duration / (startingDuration / 2.0F));
    }

    private void setImg()
    {
        int roll = MathUtils.random(2);
        if(roll == 0)
            img = ImageMaster.FLAME_1;
        else
        if(roll == 1)
            img = ImageMaster.FLAME_2;
        else
            img = ImageMaster.FLAME_3;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.setBlendFunction(770, 1);
        if(flipX && !img.isFlipX())
            img.flip(true, false);
        else
        if(!flipX && img.isFlipX())
            img.flip(true, false);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private float x;
    private float y;
    private float vX;
    private float vY;
    private float vY2;
    private float vS;
    private float startingDuration;
    private boolean flipX;
    private float delayTimer;
}
