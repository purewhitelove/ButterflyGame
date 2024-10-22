// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DustEffect.java

package com.megacrit.cardcrawl.vfx.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class DustEffect extends AbstractGameEffect
{

    public DustEffect()
    {
        startingDuration = MathUtils.random(5F, 14F);
        duration = startingDuration;
        img = getImg();
        scale = Settings.scale * MathUtils.random(0.1F, 0.8F);
        x = MathUtils.random(0, Settings.WIDTH);
        y = MathUtils.random(-100F, 400F) * Settings.scale + AbstractDungeon.floorY;
        vX = MathUtils.random(-12F, 12F) * Settings.scale;
        vY = MathUtils.random(-12F, 30F) * Settings.scale;
        float colorTmp = MathUtils.random(0.1F, 0.7F);
        color = new Color(colorTmp, colorTmp, colorTmp, 0.0F);
        baseAlpha = 1.0F - colorTmp;
        color.a = 0.0F;
        rotation = MathUtils.random(0.0F, 360F);
        aV = MathUtils.random(-120F, 120F);
    }

    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion getImg()
    {
        switch(MathUtils.random(0, 5))
        {
        case 0: // '\0'
            return ImageMaster.DUST_1;

        case 1: // '\001'
            return ImageMaster.DUST_2;

        case 2: // '\002'
            return ImageMaster.DUST_3;

        case 3: // '\003'
            return ImageMaster.DUST_4;

        case 4: // '\004'
            return ImageMaster.DUST_5;
        }
        return ImageMaster.DUST_6;
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
            color.a = Interpolation.fade.apply(0.0F, 1.0F, startingDuration / 2.0F - tmp) * baseAlpha;
        } else
        {
            color.a = Interpolation.fade.apply(0.0F, 1.0F, duration / (startingDuration / 2.0F)) * baseAlpha;
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.draw(img, x, y, img.offsetX, img.offsetY, img.packedWidth, img.packedHeight, scale, scale, rotation);
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private float vX;
    private float vY;
    private float aV;
    private float baseAlpha;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
}
