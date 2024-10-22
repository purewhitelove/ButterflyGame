// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   VerticalAuraParticle.java

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

public class VerticalAuraParticle extends AbstractGameEffect
{

    public VerticalAuraParticle(Color c, float x, float y)
    {
        fadeInTimer = 0.2F;
        fadeOutTimer = 0.8F;
        img = ImageMaster.VERTICAL_AURA;
        color = c.cpy();
        randomizeColor(color, 0.1F);
        color.a = 0.0F;
        this.x = (x + MathUtils.random(-200F, 200F) * Settings.scale) - (float)img.packedWidth / 2.0F;
        this.y = (y + MathUtils.random(-200F, 200F) * Settings.scale) - (float)img.packedHeight / 2.0F;
        vY = MathUtils.random(-300F, 300F) * Settings.scale;
        stallTimer = MathUtils.random(0.0F, 0.2F);
        scale = MathUtils.random(0.6F, 1.7F) * Settings.scale;
        renderBehind = true;
    }

    public void update()
    {
        if(stallTimer > 0.0F)
        {
            stallTimer -= Gdx.graphics.getDeltaTime();
            return;
        }
        y += vY * Gdx.graphics.getDeltaTime();
        if(fadeInTimer != 0.0F)
        {
            fadeInTimer -= Gdx.graphics.getDeltaTime();
            if(fadeInTimer < 0.0F)
                fadeInTimer = 0.0F;
            color.a = Interpolation.fade.apply(0.5F, 0.0F, fadeInTimer / 0.2F);
        } else
        if(fadeOutTimer != 0.0F)
        {
            fadeOutTimer -= Gdx.graphics.getDeltaTime();
            if(fadeOutTimer < 0.0F)
                fadeOutTimer = 0.0F;
            color.a = Interpolation.fade.apply(0.0F, 0.5F, fadeOutTimer / 0.8F);
        } else
        {
            isDone = true;
        }
    }

    private void randomizeColor(Color c, float amt)
    {
        float r = c.r + MathUtils.random(-amt, amt);
        float g = c.g + MathUtils.random(-amt, amt);
        float b = c.b + MathUtils.random(-amt, amt);
        if(r > 1.0F)
            r = 1.0F;
        else
        if(r < 0.0F)
            r = 0.0F;
        if(g > 1.0F)
            g = 1.0F;
        else
        if(g < 0.0F)
            g = 0.0F;
        if(b > 1.0F)
            b = 1.0F;
        else
        if(b < 0.0F)
            b = 0.0F;
        c.r = r;
        c.g = g;
        c.b = b;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, rotation);
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private float vY;
    private static final float FADE_IN_TIME = 0.2F;
    private static final float FADE_OUT_TIME = 0.8F;
    private float fadeInTimer;
    private float fadeOutTimer;
    private float stallTimer;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
}
