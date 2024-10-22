// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EmptyStanceParticleEffect.java

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

public class EmptyStanceParticleEffect extends AbstractGameEffect
{

    public EmptyStanceParticleEffect(float x, float y)
    {
        if(img == null)
            img = ImageMaster.STRIKE_BLUR;
        startingDuration = 0.6F;
        duration = startingDuration;
        pos = new Vector2(x, y);
        rotationSpeed = MathUtils.random(120F, 150F);
        rotation = MathUtils.random(360F);
        scale = MathUtils.random(0.7F, 2.5F) * Settings.scale;
        color = new Color(MathUtils.random(0.2F, 0.4F), MathUtils.random(0.6F, 0.8F), 1.0F, 0.0F);
        renderBehind = MathUtils.randomBoolean(0.8F);
    }

    public void update()
    {
        pos2 = new Vector2(MathUtils.cosDeg(rotation), MathUtils.sinDeg(rotation));
        pos2.nor();
        pos2.x *= 10F;
        pos2.y *= 10F;
        pos3 = pos.sub(pos2);
        rotation += Gdx.graphics.getDeltaTime() * rotationSpeed;
        duration -= Gdx.graphics.getDeltaTime();
        if(duration > startingDuration / 2.0F)
            color.a = Interpolation.fade.apply(1.0F, 0.0F, (duration - startingDuration / 2.0F) * 2.0F);
        else
            color.a = Interpolation.fade.apply(0.0F, 1.0F, duration * 2.0F);
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        if(pos3 != null)
        {
            sb.setBlendFunction(770, 1);
            sb.setColor(color);
            sb.draw(img, pos3.x - (float)img.packedWidth / 2.0F, pos3.y - (float)img.packedHeight / 2.0F, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale + MathUtils.random(-0.08F, 0.08F), scale + MathUtils.random(-0.08F, 0.08F), rotation);
            sb.setBlendFunction(770, 771);
        }
    }

    public void dispose()
    {
    }

    private float rotationSpeed;
    private Vector2 pos;
    private Vector2 pos2;
    private Vector2 pos3;
    private static com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
}
