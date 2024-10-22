// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BonfireParticleEffect.java

package com.megacrit.cardcrawl.vfx.scene;

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

public class BonfireParticleEffect extends AbstractGameEffect
{

    public BonfireParticleEffect(boolean isAbove, boolean isBlue)
    {
        img = getImg();
        x = 170F * Settings.scale + MathUtils.random(-25F, 25F) * Settings.scale;
        y = 44F * Settings.scale;
        effectDuration = MathUtils.random(1.0F, 2.0F);
        duration = effectDuration;
        startingDuration = effectDuration;
        vY = MathUtils.random(0.0F, 200F) * Settings.scale;
        vX = MathUtils.random(-30F, 30F) * Settings.scale;
        rotation = MathUtils.random(-10F, 10F);
        scale = MathUtils.random(0.8F, 2.5F);
        vY /= scale;
        vX /= scale * 2.0F;
        int roll = MathUtils.random(2);
        if(!isBlue)
        {
            if(roll == 0)
                color = Color.ORANGE.cpy();
            else
            if(roll == 1)
                color = Color.GOLDENROD.cpy();
            else
                color = Color.CORAL.cpy();
        } else
        if(roll == 0)
            color = Color.CYAN.cpy();
        else
        if(roll == 1)
            color = Color.SKY.cpy();
        else
            color = Color.TEAL.cpy();
        rotator = MathUtils.random(-10F, 10F);
    }

    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion getImg()
    {
        switch(MathUtils.random(0, 2))
        {
        case 0: // '\0'
            return ImageMaster.TORCH_FIRE_1;

        case 1: // '\001'
            return ImageMaster.TORCH_FIRE_2;
        }
        return ImageMaster.TORCH_FIRE_3;
    }

    public void update()
    {
        rotation += rotator * Gdx.graphics.getDeltaTime();
        y += vY * Gdx.graphics.getDeltaTime();
        x += vX * Gdx.graphics.getDeltaTime();
        vX *= 0.995F;
        duration -= Gdx.graphics.getDeltaTime();
        if(duration > startingDuration / 2.0F)
            color.a = Interpolation.exp10In.apply(0.4F, 0.0F, (duration - startingDuration / 2.0F) / (startingDuration / 2.0F));
        else
            color.a = Interpolation.pow2In.apply(0.0F, 0.4F, duration / (startingDuration / 2.0F));
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb, float x2, float y2)
    {
        sb.setColor(color);
        sb.setBlendFunction(770, 1);
        sb.draw(img, x + x2, y + y2, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * Settings.scale, scale * Settings.scale, rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    public void render(SpriteBatch spritebatch)
    {
    }

    private float effectDuration;
    private float x;
    private float y;
    private float vY;
    private float vX;
    private float rotator;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
}
