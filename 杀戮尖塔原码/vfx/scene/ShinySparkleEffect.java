// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ShinySparkleEffect.java

package com.megacrit.cardcrawl.vfx.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class ShinySparkleEffect extends AbstractGameEffect
{

    public ShinySparkleEffect()
    {
        startingDuration = MathUtils.random(1.0F, 2.0F);
        duration = startingDuration;
        scale = Settings.scale * MathUtils.random(0.4F, 1.0F);
        x = MathUtils.random(0, Settings.WIDTH);
        y = MathUtils.random(-100F, 550F) * Settings.scale + AbstractDungeon.floorY;
        vX = MathUtils.random(-24F, 24F) * Settings.scale;
        vY = MathUtils.random(-24F, 36F) * Settings.scale;
        float colorTmp = MathUtils.random(0.6F, 1.0F);
        color = new Color(colorTmp - 0.3F, colorTmp, colorTmp + MathUtils.random(-0.1F, 0.1F), 0.0F);
        color.a = 0.0F;
        aV = MathUtils.random(-120F, 120F);
    }

    public void update()
    {
        rotation += aV * Gdx.graphics.getDeltaTime();
        duration -= Gdx.graphics.getDeltaTime();
        x += vX * Gdx.graphics.getDeltaTime();
        y += vY * Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
        if(duration > startingDuration / 2.0F)
        {
            float tmp = duration - startingDuration / 2.0F;
            color.a = Interpolation.fade.apply(0.0F, 1.0F, startingDuration / 2.0F - tmp) / 4F;
        } else
        {
            color.a = Interpolation.fade.apply(0.0F, 1.0F, duration / (startingDuration / 2.0F)) / 4F;
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.draw(ImageMaster.WOBBLY_ORB_VFX, x - 16F, y - 16F, 16F, 16F, 32F, 32F, scale * MathUtils.random(1.0F, 1.2F), scale / 4F, 0.0F, 0, 0, 32, 32, false, false);
        sb.draw(ImageMaster.WOBBLY_ORB_VFX, x - 16F, y - 16F, 16F, 16F, 32F, 32F, scale * MathUtils.random(1.0F, 1.5F), scale / 4F, 90F, 0, 0, 32, 32, false, false);
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private float vX;
    private float vY;
    private float aV;
    private static final int W = 32;
}
