// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WindyParticleEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class WindyParticleEffect extends AbstractGameEffect
{

    public WindyParticleEffect(Color setColor, boolean reverse)
    {
        if(!reverse)
        {
            x = MathUtils.random(-400F, -100F) * Settings.scale - 128F;
            vX = MathUtils.random(1500F, 2500F) * Settings.scale;
        } else
        {
            x = ((float)Settings.WIDTH + MathUtils.random(400F, 100F) * Settings.scale) - 128F;
            vX = MathUtils.random(-1500F, -2500F) * Settings.scale;
        }
        y = MathUtils.random(0.15F, 0.85F) * (float)Settings.HEIGHT - 128F;
        vY = MathUtils.random(-100F, 100F) * Settings.scale;
        duration = 2.0F;
        scale = MathUtils.random(1.5F, 3F);
        vX *= scale;
        scale *= Settings.scale;
        scaleY = MathUtils.random(0.5F, 2.0F) * Settings.scale;
        color = setColor.cpy();
        color.a = MathUtils.random(0.5F, 1.0F);
        if(scaleY < 1.0F * Settings.scale)
            renderBehind = true;
    }

    public WindyParticleEffect()
    {
        x = MathUtils.random(-400F, -100F) * Settings.scale - 128F;
        y = MathUtils.random(0.15F, 0.85F) * (float)Settings.HEIGHT - 128F;
        vX = MathUtils.random(1500F, 2500F) * Settings.scale;
        vY = MathUtils.random(-100F, 100F) * Settings.scale;
        duration = 2.0F;
        scale = MathUtils.random(1.5F, 3F);
        vX *= scale;
        scale *= Settings.scale;
        scaleY = MathUtils.random(0.5F, 2.0F) * Settings.scale;
        color = new Color(0.9F, 0.9F, 1.0F, MathUtils.random(0.5F, 1.0F));
        if(scaleY < 1.0F * Settings.scale)
            renderBehind = true;
    }

    public void update()
    {
        x += vX * Gdx.graphics.getDeltaTime();
        y += vY * Gdx.graphics.getDeltaTime();
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        sb.setColor(color);
        sb.draw(ImageMaster.HORIZONTAL_LINE, x, y, 128F, 128F, 256F, 256F, scale * MathUtils.random(0.7F, 1.3F), scaleY * MathUtils.random(0.7F, 1.3F), rotation, 0, 0, 256, 256, false, false);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private float scaleY;
    private float x;
    private float y;
    private float vX;
    private float vY;
}
