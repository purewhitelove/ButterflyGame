// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FireFlyEffect.java

package com.megacrit.cardcrawl.vfx.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;

public class FireFlyEffect extends AbstractGameEffect
{

    public FireFlyEffect(Color setColor)
    {
        trailTimer = 0.0F;
        prevPositions = new ArrayList();
        startingDuration = MathUtils.random(6F, 14F);
        duration = startingDuration;
        this.setColor = setColor;
        img = ImageMaster.STRIKE_BLUR;
        x = (float)MathUtils.random(0, Settings.WIDTH) - (float)img.packedWidth / 2.0F;
        y = (MathUtils.random(-100F, 400F) * Settings.scale + AbstractDungeon.floorY) - (float)img.packedHeight / 2.0F;
        vX = MathUtils.random(18F, 90F) * Settings.scale;
        aX = MathUtils.random(-5F, 5F) * Settings.scale;
        waveDst = vX * MathUtils.random(0.03F, 0.07F);
        scale = (Settings.scale * vX) / 60F;
        if(MathUtils.randomBoolean())
            vX = -vX;
        vY = MathUtils.random(-36F, 36F) * Settings.scale;
        color = setColor.cpy();
        baseAlpha = 0.25F;
        color.a = 0.0F;
    }

    public void update()
    {
        trailTimer -= Gdx.graphics.getDeltaTime();
        if(trailTimer < 0.0F)
        {
            trailTimer = 0.04F;
            prevPositions.add(new Vector2(x, y));
            if(prevPositions.size() > 30)
                prevPositions.remove(0);
        }
        duration -= Gdx.graphics.getDeltaTime();
        x += vX * Gdx.graphics.getDeltaTime();
        vX += aX * Gdx.graphics.getDeltaTime();
        if(!prevPositions.isEmpty() && (((Vector2)prevPositions.get(0)).x < 0.0F || ((Vector2)prevPositions.get(0)).x > (float)Settings.WIDTH))
            isDone = true;
        y += vY * Gdx.graphics.getDeltaTime();
        y += ((MathUtils.sin(duration * waveDst) * waveDst) / 4F) * Gdx.graphics.getDeltaTime() * 60F;
        if(duration < 0.0F)
            isDone = true;
        if(duration > startingDuration / 2.0F)
        {
            float tmp = duration - startingDuration / 2.0F;
            color.a = Interpolation.fade.apply(0.0F, 1.0F, startingDuration / 2.0F - tmp) * baseAlpha;
        } else
        {
            color.a = Interpolation.fade.apply(0.0F, 1.0F, duration / (startingDuration / 2.0F)) * baseAlpha;
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        setColor.a = color.a;
        for(int i = prevPositions.size() - 1; i > 0; i--)
        {
            setColor.a *= 0.95F;
            sb.setColor(setColor);
            sb.draw(img, ((Vector2)prevPositions.get(i)).x, ((Vector2)prevPositions.get(i)).y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, (scale * (float)(i + 5)) / (float)prevPositions.size(), (scale * (float)(i + 5)) / (float)prevPositions.size(), rotation);
        }

        sb.setColor(color);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * MathUtils.random(2.5F, 3F), scale * MathUtils.random(2.5F, 3F), rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private float vX;
    private float vY;
    private float aX;
    private float waveDst;
    private float baseAlpha;
    private float trailTimer;
    private static final float TRAIL_TIME = 0.04F;
    private static final int TRAIL_MAX_AMT = 30;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private Color setColor;
    private ArrayList prevPositions;
}
