// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DEPRECATEDRoomFadeParticle.java

package com.megacrit.cardcrawl.vfx.deprecated;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class DEPRECATEDRoomFadeParticle extends AbstractGameEffect
{

    public DEPRECATEDRoomFadeParticle(float y)
    {
        this.y = y;
        x = (float)Settings.WIDTH + (float)img.packedWidth * 1.5F;
        y -= img.packedHeight / 2;
        duration = 1.0F;
        startingDuration = 1.0F;
        color = AbstractDungeon.fadeColor.cpy();
        color.a = 1.0F;
        scale *= 2.0F;
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
        x = Interpolation.pow2Out.apply(0.0F - (float)img.packedWidth * 1.5F, (float)Settings.WIDTH + (float)img.packedWidth * 1.5F, duration / 1.0F);
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
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private static final float DUR = 1F;
}
