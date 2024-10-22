// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FastSmoke.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class FastSmoke
{

    public FastSmoke(float x, float y)
    {
        scale = 0.01F;
        fadingIn = true;
        killed = false;
        targetScale = MathUtils.random(1.0F, 1.5F) * Settings.scale;
        fadeInTime = MathUtils.random(1.0F, 1.5F);
        fadeInTimer = fadeInTime;
        float darkness = MathUtils.random(0.4F, 0.9F);
        color = new Color(darkness + 0.1F, darkness + 0.1F, darkness + 0.05F, 1.0F);
        if(targetScale > 0.5F)
        {
            img = ImageMaster.EXHAUST_L;
        } else
        {
            img = ImageMaster.EXHAUST_S;
            vX /= 3F;
        }
        this.x = (x + MathUtils.random(-75F, 75F) * Settings.scale) - (float)img.packedWidth / 2.0F;
        this.y = (y + MathUtils.random(-75F, 75F) * Settings.scale) - (float)img.packedHeight / 2.0F;
        vY = MathUtils.random(50F * Settings.scale, 400F * Settings.scale);
        vX = MathUtils.random(-140F * Settings.scale, 140F * Settings.scale);
        rotation = MathUtils.random(360F);
        killSpeed = MathUtils.random(1.0F, 4F);
        decelerateY = MathUtils.random(1.0F * Settings.scale, 3F * Settings.scale);
    }

    public void update()
    {
        if(fadingIn)
        {
            fadeInTimer -= Gdx.graphics.getDeltaTime();
            if(fadeInTimer < 0.0F)
            {
                fadeInTimer = 0.0F;
                fadingIn = false;
            }
            scale = Interpolation.swingIn.apply(targetScale, 0.01F, fadeInTimer / fadeInTime);
        }
        y += vY * Gdx.graphics.getDeltaTime();
        vY -= Gdx.graphics.getDeltaTime() * decelerateY;
        x += vX * Gdx.graphics.getDeltaTime();
        rotation += vX * 2.0F * Gdx.graphics.getDeltaTime();
        if(vY < 0.0F)
            vY = 0.0F;
        if(killed)
        {
            color.a -= killSpeed * Gdx.graphics.getDeltaTime();
            if(color.a < 0.0F)
                color.a = 0.0F;
            scale += 5F * Gdx.graphics.getDeltaTime();
        }
    }

    public void kill()
    {
        killed = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, rotation);
    }

    private float x;
    private float y;
    private float vX;
    private float vY;
    private float rotation;
    private float fadeInTime;
    private float fadeInTimer;
    private float scale;
    private float targetScale;
    private boolean fadingIn;
    private Color color;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private boolean killed;
    private float killSpeed;
    private float decelerateY;
}
