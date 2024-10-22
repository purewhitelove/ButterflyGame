// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DeckPoofParticle.java

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

public class DeckPoofParticle extends AbstractGameEffect
{

    public DeckPoofParticle(float x, float y, boolean isDeck)
    {
        scale = Settings.scale;
        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();
        switch(MathUtils.random(2))
        {
        case 0: // '\0'
            img = ImageMaster.SMOKE_1;
            break;

        case 1: // '\001'
            img = ImageMaster.SMOKE_2;
            break;

        default:
            img = ImageMaster.SMOKE_3;
            break;
        }
        duration = 0.8F;
        startingDuration = duration;
        delay = MathUtils.random(0.0F, 0.2F);
        float t = MathUtils.random(-10F, 10F) * MathUtils.random(-10F, 10F);
        this.x = (x + t * Settings.scale) - (float)img.packedWidth / 2.0F;
        t = MathUtils.random(-10F, 10F) * MathUtils.random(-10F, 10F);
        this.y = (y + t * Settings.scale) - (float)img.packedHeight / 2.0F;
        if(isDeck)
        {
            float rg = MathUtils.random(0.4F, 0.8F);
            color = new Color(rg + 0.1F, rg, rg - 0.2F, 0.0F);
            vA = MathUtils.random(-400F, 400F) * Settings.scale;
        } else
        {
            float rb = MathUtils.random(0.3F, 0.5F);
            color = new Color(rb, 0.35F, rb + 0.1F, 0.0F);
            vA = MathUtils.random(-70F, 70F) * MathUtils.random(-70F, 70F) * Settings.scale;
        }
        vX = MathUtils.random(-70F, 70F) * Settings.scale;
        vY = MathUtils.random(-100F, 300F) * Settings.scale;
        scale = MathUtils.random(0.3F, 1.8F) * Settings.scale;
        rotation = MathUtils.random(360F);
    }

    public void update()
    {
        if(delay > 0.0F)
        {
            delay -= Gdx.graphics.getDeltaTime();
            return;
        }
        rotation += vA * Gdx.graphics.getDeltaTime();
        x += vX * Gdx.graphics.getDeltaTime();
        y += vY * Gdx.graphics.getDeltaTime();
        vY *= 0.99F;
        vX *= 0.99F;
        scale += Gdx.graphics.getDeltaTime() / 2.0F;
        if(duration > startingDuration / 2.0F)
            color.a = Interpolation.pow3Out.apply(0.0F, 1.0F, 1.0F - duration);
        else
            color.a = Interpolation.fade.apply(0.0F, 1.0F, duration * 2.0F);
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        if(flipX && !img.isFlipX())
            img.flip(true, false);
        else
        if(!flipX && img.isFlipX())
            img.flip(true, false);
        if(flipY && !img.isFlipY())
            img.flip(false, true);
        else
        if(!flipY && img.isFlipY())
            img.flip(false, true);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, rotation);
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private float vX;
    private float vY;
    private float vA;
    private float delay;
    private float scale;
    private boolean flipX;
    private boolean flipY;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
}
