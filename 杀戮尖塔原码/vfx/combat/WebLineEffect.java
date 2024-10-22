// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WebLineEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class WebLineEffect extends AbstractGameEffect
{

    public WebLineEffect(float x, float y, boolean facingLeft)
    {
        this.x = x + MathUtils.random(-20F, 20F) * Settings.scale;
        this.y = (y - 128F) + MathUtils.random(-10F, 10F) * Settings.scale;
        startingDuration = 1.0F;
        duration = startingDuration;
        rotation = MathUtils.random(185F, 170F);
        if(!facingLeft)
            rotation += 180F;
        scale = MathUtils.random(0.8F, 1.0F) * Settings.scale;
        float g = MathUtils.random(0.6F, 0.9F);
        color = new Color(g, g, g + 0.1F, 0.0F);
        renderBehind = false;
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        if(duration > startingDuration / 2.0F)
            color.a = Interpolation.fade.apply(0.8F, 0.01F, duration - startingDuration / 2.0F) * Settings.scale;
        else
            color.a = Interpolation.pow5Out.apply(0.01F, 0.8F, duration / (startingDuration / 2.0F)) * Settings.scale;
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(new Color(1.0F, 1.0F, 1.0F, color.a));
        sb.setBlendFunction(770, 1);
        sb.draw(ImageMaster.HORIZONTAL_LINE, x, y, 0.0F, 128F, 256F, 256F, scale * 2.0F * (MathUtils.cos(duration * 16F) / 4F + 1.5F), scale, rotation, 0, 0, 256, 256, false, false);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    float x;
    float y;
}
