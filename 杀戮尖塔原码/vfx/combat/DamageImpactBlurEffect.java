// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DamageImpactBlurEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class DamageImpactBlurEffect extends AbstractGameEffect
{

    public DamageImpactBlurEffect(float x, float y)
    {
        img = ImageMaster.STRIKE_BLUR;
        duration = MathUtils.random(0.5F, 0.75F);
        startingDuration = duration;
        this.x = x - (float)img.packedWidth / 2.0F;
        this.y = y - (float)img.packedHeight / 2.0F;
        rotation = 0.0F;
        vX = MathUtils.random(-42000F * Settings.scale, 42000F * Settings.scale);
        vY = MathUtils.random(-42000F * Settings.scale, 42000F * Settings.scale);
        startScale = MathUtils.random(4F, 8F);
        renderBehind = true;
        float tmp = MathUtils.random(0.1F, 0.3F);
        color = new Color(tmp, tmp, tmp, 1.0F);
    }

    public void update()
    {
        x += vX * Gdx.graphics.getDeltaTime();
        y += vY * Gdx.graphics.getDeltaTime();
        vX *= 56F * Gdx.graphics.getDeltaTime();
        vY *= 56F * Gdx.graphics.getDeltaTime();
        scale = Settings.scale * ((duration / startingDuration) * 2.0F + startScale);
        color.a = duration;
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
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
    private float vX;
    private float vY;
    private float startScale;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
}
