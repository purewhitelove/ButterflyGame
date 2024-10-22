// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TitleDustEffect.java

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

public class TitleDustEffect extends AbstractGameEffect
{

    public TitleDustEffect()
    {
        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();
        duration = MathUtils.random(3F, 4F);
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
        x = -600F * Settings.scale - (float)img.packedWidth / 2.0F;
        y = MathUtils.random(1.0F, 20F);
        y *= y * Settings.scale;
        y -= (float)img.packedHeight / 2.0F;
        vX = MathUtils.random(400F, 1200F) * Settings.scale;
        vY = MathUtils.random(-20F, 20F) * Settings.scale;
        aV = MathUtils.random(-50F, 50F);
        float tmp = MathUtils.random(0.2F, 0.3F);
        color = new Color();
        color.g = tmp + MathUtils.random(0.1F);
        color.r = color.g + MathUtils.random(0.1F);
        color.b = tmp;
        scale = MathUtils.random(6F, 8F) * Settings.scale;
    }

    public void update()
    {
        x += vX * Gdx.graphics.getDeltaTime();
        y += vY * Gdx.graphics.getDeltaTime();
        rotation += aV * Gdx.graphics.getDeltaTime();
        if(startingDuration - duration < 1.0F)
            color.a = Interpolation.fade.apply(0.2F, 0.2F, (startingDuration - duration) / 1.0F);
        else
        if(duration < 1.0F)
            color.a = Interpolation.fade.apply(0.2F, 0.0F, 1.0F - duration / 1.0F);
        duration -= Gdx.graphics.getDeltaTime();
        scale += Gdx.graphics.getDeltaTime() / 3F;
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb, float srcX, float srcY)
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
        sb.draw(img, x, y + srcY, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, rotation);
    }

    public void dispose()
    {
    }

    public void render(SpriteBatch spritebatch)
    {
    }

    private float x;
    private float y;
    private float vX;
    private float vY;
    private float aV;
    private boolean flipX;
    private boolean flipY;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
}
