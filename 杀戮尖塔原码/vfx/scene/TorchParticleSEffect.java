// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TorchParticleSEffect.java

package com.megacrit.cardcrawl.vfx.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class TorchParticleSEffect extends AbstractGameEffect
{

    public TorchParticleSEffect(float x, float y)
    {
        duration = MathUtils.random(1.5F, 3F);
        startingDuration = duration;
        img = getImg();
        this.x = (x - (float)(img.packedWidth / 2)) + MathUtils.random(-3F, 3F) * Settings.scale;
        this.y = y - (float)(img.packedHeight / 2);
        scale = Settings.scale * MathUtils.random(0.5F, 1.0F);
        vY = MathUtils.random(1.0F, 10F);
        vY *= vY * Settings.scale;
        rotation = MathUtils.random(-20F, 20F);
        if(!renderGreen)
            color = new Color(MathUtils.random(0.6F, 1.0F), MathUtils.random(0.3F, 0.6F), MathUtils.random(0.0F, 0.3F), 0.01F);
        else
            color = new Color(MathUtils.random(0.1F, 0.3F), MathUtils.random(0.5F, 0.9F), MathUtils.random(0.1F, 0.3F), 0.01F);
        renderBehind = true;
    }

    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion getImg()
    {
        switch(MathUtils.random(0, 2))
        {
        case 0: // '\0'
            return ImageMaster.TORCH_FIRE_1;

        case 1: // '\001'
            return ImageMaster.TORCH_FIRE_2;
        }
        return ImageMaster.TORCH_FIRE_3;
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
        color.a = Interpolation.fade.apply(0.0F, 0.75F, duration / startingDuration);
        y += vY * Gdx.graphics.getDeltaTime();
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        sb.setColor(color);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private float vY;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    public static boolean renderGreen = false;

}
