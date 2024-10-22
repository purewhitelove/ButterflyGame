// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CampfireSmokeEffect.java

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

public class CampfireSmokeEffect extends AbstractGameEffect
{

    public CampfireSmokeEffect()
    {
        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();
        duration = MathUtils.random(7F, 11F);
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
        x = 188F * Settings.scale - (float)img.packedWidth / 2.0F;
        y = 60F * Settings.scale - (float)img.packedHeight / 2.0F;
        vX = MathUtils.random(-20F, 20F) * Settings.scale;
        vY = MathUtils.random(10F, 60F) * Settings.scale;
        aV = MathUtils.random(-50F, 50F);
        float tmp = MathUtils.random(0.2F, 0.35F);
        color = new Color();
        color.r = tmp;
        color.g = tmp;
        color.b = tmp;
        scale = MathUtils.random(0.8F, 1.2F) * Settings.scale;
    }

    public void update()
    {
        x += vX * Gdx.graphics.getDeltaTime();
        y += vY * Gdx.graphics.getDeltaTime();
        vX *= 0.99F;
        rotation += aV * Gdx.graphics.getDeltaTime();
        if(startingDuration - duration < 1.5F)
            color.a = Interpolation.fade.apply(0.0F, 0.4F, (startingDuration - duration) / 1.5F);
        else
        if(duration < 4F)
            color.a = Interpolation.fade.apply(0.4F, 0.0F, 1.0F - duration / 4F);
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
    private float vX;
    private float vY;
    private float aV;
    private boolean flipX;
    private boolean flipY;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
}
