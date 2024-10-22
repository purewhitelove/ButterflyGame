// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SpookierChestEffect.java

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
import com.megacrit.cardcrawl.rewards.chests.AbstractChest;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class SpookierChestEffect extends AbstractGameEffect
{

    public SpookierChestEffect()
    {
        flipX = MathUtils.randomBoolean();
        flipY = MathUtils.randomBoolean();
        duration = 3F;
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
        x = (AbstractChest.CHEST_LOC_X + MathUtils.random(-30F, 30F) * Settings.scale) - (float)img.packedWidth / 2.0F;
        y = AbstractChest.CHEST_LOC_Y - MathUtils.random(120F, 190F) * Settings.scale - (float)img.packedHeight / 2.0F;
        vX = MathUtils.random(-120F, 120F) * Settings.scale;
        vY = MathUtils.random(-50F, 100F) * Settings.scale;
        aV = MathUtils.random(-150F, 150F);
        float tmp = MathUtils.random(0.3F, 0.9F);
        color = new Color();
        color.r = tmp * MathUtils.random(0.7F, 1.0F);
        color.g = tmp * MathUtils.random(0.5F, 0.9F);
        color.b = tmp;
        renderBehind = true;
        scale = MathUtils.random(0.5F, 3F) * Settings.scale;
    }

    public void update()
    {
        x += vX * Gdx.graphics.getDeltaTime();
        y += vY * Gdx.graphics.getDeltaTime();
        rotation += aV * Gdx.graphics.getDeltaTime();
        if(startingDuration - duration < 1.0F)
            color.a = Interpolation.fade.apply(0.0F, 0.3F, (startingDuration - duration) / 1.0F);
        else
        if(duration < 2.0F)
            color.a = Interpolation.fade.apply(0.3F, 0.0F, 1.0F - duration / 2.0F);
        duration -= Gdx.graphics.getDeltaTime();
        scale += Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
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

    public void dispose()
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
