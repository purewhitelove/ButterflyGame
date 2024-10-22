// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WobblyCircleEffect.java

package com.megacrit.cardcrawl.vfx.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class WobblyCircleEffect extends AbstractGameEffect
{

    public WobblyCircleEffect()
    {
        startingDuration = MathUtils.random(2.0F, 3F);
        duration = startingDuration;
        scale = Settings.scale * MathUtils.random(0.2F, 0.5F);
        pos = new Vector2(MathUtils.random(0, Settings.WIDTH), MathUtils.random(-100F, 500F) * Settings.scale + AbstractDungeon.floorY);
        vX = MathUtils.random(-72F, 72F) * Settings.scale;
        vY = MathUtils.random(-24F, 24F) * Settings.scale;
        float colorTmp = MathUtils.random(0.7F, 1.0F);
        color = new Color(MathUtils.random(0.7F, 0.8F), colorTmp, colorTmp, 0.0F);
        color.a = 0.0F;
        aV = MathUtils.random(0.2F, 0.4F);
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        pos.add(vX * Gdx.graphics.getDeltaTime(), vY * Gdx.graphics.getDeltaTime());
        float dst = pos.dst(InputHelper.mX, InputHelper.mY);
        if(dst < 200F * Settings.scale)
            pos.add((pos.x - (float)InputHelper.mX) * Gdx.graphics.getDeltaTime(), (pos.y - (float)InputHelper.mY) * Gdx.graphics.getDeltaTime());
        if(duration < 0.0F)
            isDone = true;
        if(duration > startingDuration / 2.0F)
        {
            float tmp = duration - startingDuration / 2.0F;
            color.a = Interpolation.fade.apply(0.0F, 1.0F, startingDuration / 2.0F - tmp) * aV;
        } else
        {
            color.a = Interpolation.fade.apply(0.0F, 1.0F, duration / (startingDuration / 2.0F)) * aV;
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.draw(ImageMaster.WOBBLY_ORB_VFX, pos.x - 16F, pos.y - 16F, 16F, 16F, 32F, 32F, scale, scale, 0.0F, 0, 0, 32, 32, false, false);
    }

    public void dispose()
    {
    }

    private Vector2 pos;
    private float vX;
    private float vY;
    private float aV;
    private static final int W = 32;
}
