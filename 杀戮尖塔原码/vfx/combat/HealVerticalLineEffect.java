// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HealVerticalLineEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class HealVerticalLineEffect extends AbstractGameEffect
{

    public HealVerticalLineEffect(float x, float y)
    {
        img = ImageMaster.STRIKE_LINE;
        img2 = ImageMaster.STRIKE_LINE_2;
        duration = MathUtils.random(0.6F, 1.3F);
        startingDuration = duration;
        this.x = x;
        this.y = y;
        staggerTimer = MathUtils.random(0.0F, 0.5F);
        float tmp = MathUtils.random(5F, 20F);
        vY = tmp * tmp * Settings.scale;
        rotation = 90F;
        if(MathUtils.randomBoolean())
            color = Color.CHARTREUSE.cpy();
        else
            color = new Color(1.0F, 1.0F, 0.5F, 1.0F);
        color.a = 0.0F;
        renderBehind = MathUtils.randomBoolean(0.3F);
    }

    public void update()
    {
        if(staggerTimer > 0.0F)
        {
            staggerTimer -= Gdx.graphics.getDeltaTime();
            return;
        }
        y += vY * Gdx.graphics.getDeltaTime();
        scale = (Settings.scale * duration) / startingDuration;
        duration -= Gdx.graphics.getDeltaTime();
        if(duration / startingDuration > 0.5F)
        {
            color.a = 1.0F - duration / startingDuration;
            color.a += MathUtils.random(0.0F, 0.2F);
        } else
        {
            color.a = duration / startingDuration;
            color.a += MathUtils.random(0.0F, 0.2F);
        }
        if(duration < 0.0F)
        {
            isDone = true;
            color.a = 0.0F;
        }
    }

    public void render(SpriteBatch sb)
    {
        if(staggerTimer > 0.0F)
        {
            return;
        } else
        {
            sb.setBlendFunction(770, 1);
            sb.setColor(color);
            sb.draw(img, x - (float)img.packedWidth / 2.0F, y - (float)img.packedHeight / 2.0F, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * MathUtils.random(0.7F, 2.0F), scale * 0.8F, rotation + MathUtils.random(-2F, 2.0F));
            sb.setColor(new Color(1.0F, 1.0F, 0.7F, color.a));
            sb.draw(img2, x - (float)img2.packedWidth / 2.0F, y - (float)img2.packedHeight / 2.0F, (float)img2.packedWidth / 2.0F, (float)img2.packedHeight / 2.0F, img2.packedWidth, img2.packedHeight, scale * 1.5F, scale * MathUtils.random(1.2F, 2.4F), rotation);
            sb.draw(img2, x - (float)img2.packedWidth / 2.0F, y - (float)img2.packedHeight / 2.0F, (float)img2.packedWidth / 2.0F, (float)img2.packedHeight / 2.0F, img2.packedWidth, img2.packedHeight, scale * 1.5F, scale * MathUtils.random(1.2F, 2.4F), rotation);
            sb.setBlendFunction(770, 771);
            return;
        }
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private float vY;
    private float staggerTimer;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img2;
}
