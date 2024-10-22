// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BottomFogEffect.java

package com.megacrit.cardcrawl.vfx.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class BottomFogEffect extends AbstractGameEffect
{

    public BottomFogEffect(boolean renderBehind)
    {
        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();
        duration = MathUtils.random(10F, 12F);
        startingDuration = duration;
        switch(MathUtils.random(2))
        {
        case 0: // '\0'
            img = ImageMaster.SMOKE_1;
            break;

        case 1: // '\001'
            img = ImageMaster.SMOKE_2;
            break;

        default:
            img = ImageMaster.SMOKE_3;
            break;
        }
        x = MathUtils.random(-200F, 2120F) * Settings.scale - (float)img.packedWidth / 2.0F;
        y = ((float)Settings.HEIGHT / 2.0F + MathUtils.random(60F, 410F) * Settings.scale) - (float)img.packedHeight / 2.0F;
        vX = MathUtils.random(-200F, 200F) * Settings.scale;
        aV = MathUtils.random(-10F, 10F);
        this.renderBehind = renderBehind;
        float tmp = MathUtils.random(0.1F, 0.15F);
        color = new Color();
        color.r = tmp + MathUtils.random(0.1F);
        color.g = tmp;
        color.b = color.r + MathUtils.random(0.05F);
        scale = MathUtils.random(4F, 6F) * Settings.scale;
    }

    public void update()
    {
        x += vX * Gdx.graphics.getDeltaTime();
        rotation += aV * Gdx.graphics.getDeltaTime();
        if(startingDuration - duration < 5F)
            color.a = Interpolation.fade.apply(0.0F, 0.3F, (startingDuration - duration) / 5F);
        else
        if(duration < 5F)
            color.a = Interpolation.fade.apply(0.3F, 0.0F, 1.0F - duration / 5F);
        duration -= Gdx.graphics.getDeltaTime();
        scale += Gdx.graphics.getDeltaTime() / 3F;
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch spritebatch, float f, float f1)
    {
    }

    public void dispose()
    {
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        if(flipX && !img.isFlipX())
            img.flip(true, false);
        else
        if(!flipX && img.isFlipX())
            img.flip(true, false);
        if(flipY && !img.isFlipY())
            img.flip(false, true);
        else
        if(!flipY && img.isFlipY())
            img.flip(false, true);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, rotation);
    }

    private float x;
    private float y;
    private float vX;
    private float aV;
    private boolean flipX;
    private boolean flipY;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
}
