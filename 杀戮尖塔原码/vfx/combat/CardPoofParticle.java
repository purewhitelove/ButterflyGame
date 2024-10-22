// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CardPoofParticle.java

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

public class CardPoofParticle extends AbstractGameEffect
{

    public CardPoofParticle(float x, float y)
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
        duration = 0.6F;
        startingDuration = duration;
        delay = MathUtils.random(0.0F, 0.1F);
        float t = MathUtils.random(-160F, 160F);
        this.x = (x + t * Settings.scale) - (float)img.packedWidth / 2.0F;
        t = MathUtils.random(-180F, 180F);
        this.y = (y + t * Settings.scale) - (float)img.packedHeight / 2.0F;
        float rg = MathUtils.random(0.4F, 0.8F);
        color = new Color(rg + 0.05F, rg, rg + 0.05F, 0.0F);
        vA = MathUtils.random(-400F, 400F) * Settings.scale;
        vX = MathUtils.random(-170F, 170F) * Settings.scale;
        vY = MathUtils.random(-170F, 170F) * Settings.scale;
        scale = MathUtils.random(0.8F, 2.5F) * Settings.scale;
        rotation = MathUtils.random(360F);
        renderBehind = true;
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
        scale += Gdx.graphics.getDeltaTime() * 5F;
        if(duration > startingDuration / 2.0F)
            color.a = Interpolation.pow3Out.apply(0.0F, 0.7F, 1.0F - duration);
        else
            color.a = Interpolation.fade.apply(0.0F, 0.7F, duration * 2.0F);
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
