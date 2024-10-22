// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LightFlareParticleEffect.java

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

public class LightFlareParticleEffect extends AbstractGameEffect
{

    public LightFlareParticleEffect(float x, float y, Color color)
    {
        pos = new Vector2();
        img = ImageMaster.STRIKE_BLUR;
        duration = MathUtils.random(0.5F, 1.1F);
        startingDuration = duration;
        pos.x = x - (float)img.packedWidth / 2.0F;
        pos.y = y - (float)img.packedHeight / 2.0F;
        speed = MathUtils.random(200F, 300F) * Settings.scale;
        speedStart = speed;
        speedTarget = MathUtils.random(20F, 30F) * Settings.scale;
        this.color = color.cpy();
        this.color.a = 0.0F;
        renderBehind = true;
        rotation = MathUtils.random(360F);
        waveIntensity = MathUtils.random(5F, 10F);
        waveSpeed = MathUtils.random(-20F, 20F);
        speedTarget = MathUtils.random(0.1F, 0.5F);
        scale = MathUtils.random(0.2F, 1.0F) * Settings.scale;
    }

    public void update()
    {
        Vector2 tmp = new Vector2(MathUtils.cosDeg(rotation), MathUtils.sinDeg(rotation));
        tmp.x *= speed * Gdx.graphics.getDeltaTime();
        tmp.y *= speed * Gdx.graphics.getDeltaTime();
        speed = Interpolation.pow2OutInverse.apply(speedStart, speedTarget, 1.0F - duration / startingDuration);
        pos.x += tmp.x;
        pos.y += tmp.y;
        rotation += MathUtils.cos(duration * waveSpeed) * waveIntensity;
        if(duration < 0.5F)
            color.a = Interpolation.fade.apply(0.0F, 1.0F, duration * 2.0F);
        else
            color.a = 1.0F;
        super.update();
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        sb.setColor(new Color(color.r, color.g, color.b, color.a / 4F));
        sb.draw(img, pos.x, pos.y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * 4F, scale * 4F, rotation);
        sb.setColor(color);
        sb.draw(img, pos.x, pos.y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private Vector2 pos;
    private float speed;
    private float speedStart;
    private float speedTarget;
    private float waveIntensity;
    private float waveSpeed;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
}
