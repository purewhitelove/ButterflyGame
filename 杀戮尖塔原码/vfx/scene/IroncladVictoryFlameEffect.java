// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IroncladVictoryFlameEffect.java

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

public class IroncladVictoryFlameEffect extends AbstractGameEffect
{

    public IroncladVictoryFlameEffect()
    {
        flipX = MathUtils.randomBoolean();
        duration = 1.0F;
        startingDuration = duration;
        renderBehind = MathUtils.randomBoolean();
        switch(MathUtils.random(2))
        {
        case 0: // '\0'
            img = ImageMaster.FLAME_1;
            break;

        case 1: // '\001'
            img = ImageMaster.FLAME_2;
            break;

        default:
            img = ImageMaster.FLAME_3;
            break;
        }
        x = MathUtils.random(600F, 1320F) * Settings.xScale - (float)img.packedWidth / 2.0F;
        y = -300F * Settings.scale - (float)img.packedHeight / 2.0F;
        if(x > 960F * Settings.xScale)
            vX = MathUtils.random(0.0F, -120F) * Settings.xScale;
        else
            vX = MathUtils.random(120F, 0.0F) * Settings.xScale;
        vY = MathUtils.random(600F, 800F) * Settings.scale;
        color = new Color(MathUtils.random(0.4F, 0.8F), MathUtils.random(0.1F, 0.4F), MathUtils.random(0.4F, 0.9F), 0.8F);
        renderBehind = false;
        scale = MathUtils.random(6F, 7F) * Settings.scale;
    }

    public void update()
    {
        x += vX * Gdx.graphics.getDeltaTime();
        y += vY * Gdx.graphics.getDeltaTime();
        color.a = Interpolation.pow3Out.apply(0.0F, 0.8F, duration / startingDuration);
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
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, rotation);
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private float vX;
    private float vY;
    private boolean flipX;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
}
