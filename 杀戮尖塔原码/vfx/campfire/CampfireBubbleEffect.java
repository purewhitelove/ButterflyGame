// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CampfireBubbleEffect.java

package com.megacrit.cardcrawl.vfx.campfire;

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

public class CampfireBubbleEffect extends AbstractGameEffect
{

    public CampfireBubbleEffect(boolean isLarge)
    {
        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();
        duration = MathUtils.random(10F, 20F);
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
        x = MathUtils.random(-300F, 300F) * Settings.scale - (float)img.packedWidth / 2.0F;
        if(isLarge)
            y = MathUtils.random(-200F, 230F) * Settings.scale - (float)img.packedHeight / 2.0F;
        else
            y = MathUtils.random(0.0F, 230F) * Settings.scale - (float)img.packedHeight / 2.0F;
        aV = MathUtils.random(-30F, 30F);
        rotation = MathUtils.random(0.0F, 360F);
        float tmp = MathUtils.random(0.8F, 1.0F);
        color = new Color();
        color.r = tmp;
        color.g = tmp - 0.03F;
        color.b = tmp - 0.07F;
        scale = MathUtils.random(6F, 9F) * Settings.scale;
    }

    public void update()
    {
        rotation += aV * Gdx.graphics.getDeltaTime();
        if(startingDuration - duration < 3F)
            color.a = Interpolation.fade.apply(0.0F, 0.5F, (startingDuration - duration) / 3F);
        else
        if(duration < 3F)
            color.a = Interpolation.fade.apply(0.5F, 0.0F, 1.0F - duration / 3F);
        duration -= Gdx.graphics.getDeltaTime();
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
        sb.draw(img, srcX + x, srcY + y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, rotation);
    }

    public void dispose()
    {
    }

    public void render(SpriteBatch spritebatch)
    {
    }

    private float x;
    private float y;
    private float aV;
    private boolean flipX;
    private boolean flipY;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
}
