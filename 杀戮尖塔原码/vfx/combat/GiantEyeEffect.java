// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GiantEyeEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class GiantEyeEffect extends AbstractGameEffect
{

    public GiantEyeEffect(float setX, float setY, Color setColor)
    {
        duration = 1.0F;
        startingDuration = 1.0F;
        color = setColor.cpy();
        color.a = 0.0F;
        img = ImageMaster.EYE_ANIM_0;
        x = setX - (float)img.packedWidth / 2.0F;
        y = setY - (float)img.packedHeight / 2.0F;
    }

    public void update()
    {
        if(1.0F - duration < 0.1F)
            color.a = Interpolation.fade.apply(0.0F, 0.9F, (1.0F - duration) * 10F);
        else
            color.a = Interpolation.pow2Out.apply(0.0F, 0.9F, duration);
        if(duration > startingDuration * 0.85F)
            img = ImageMaster.EYE_ANIM_0;
        else
        if(duration > startingDuration * 0.8F)
            img = ImageMaster.EYE_ANIM_1;
        else
        if(duration > startingDuration * 0.75F)
            img = ImageMaster.EYE_ANIM_2;
        else
        if(duration > startingDuration * 0.7F)
            img = ImageMaster.EYE_ANIM_3;
        else
        if(duration > startingDuration * 0.65F)
            img = ImageMaster.EYE_ANIM_4;
        else
        if(duration > startingDuration * 0.6F)
            img = ImageMaster.EYE_ANIM_5;
        else
        if(duration > startingDuration * 0.55F)
            img = ImageMaster.EYE_ANIM_6;
        else
        if(duration > startingDuration * 0.38F)
            img = ImageMaster.EYE_ANIM_5;
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        sb.setColor(color);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, (scale + MathUtils.random(-0.02F, 0.02F)) * 3F, (scale + MathUtils.random(-0.03F, 0.03F)) * 3F, rotation);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, (scale + MathUtils.random(-0.02F, 0.02F)) * 3F, (scale + MathUtils.random(-0.03F, 0.03F)) * 3F, rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private float x;
    private float y;
}
