// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EntangleEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class EntangleEffect extends AbstractGameEffect
{

    public EntangleEffect(float x, float y, float dX, float dY)
    {
        tX = x - 32F;
        tY = y - 32F;
        this.dX = dX - 32F;
        this.dY = dY - 32F;
        this.x = dX;
        this.y = dY;
        startingDuration = 1.0F;
        duration = startingDuration;
        scale = 0.01F;
        color = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        renderBehind = false;
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        x = Interpolation.pow5In.apply(dX, tX, duration);
        y = Interpolation.pow5In.apply(dY, tY, duration);
        if(duration > startingDuration / 2.0F)
            color.a = Interpolation.fade.apply(1.0F, 0.01F, duration - startingDuration / 2.0F) * Settings.scale;
        else
            color.a = Interpolation.fade.apply(0.01F, 1.0F, duration / (startingDuration / 2.0F)) * Settings.scale;
        scale = Interpolation.bounceIn.apply(5F, 1.0F, duration) * Settings.scale;
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(new Color(1.0F, 1.0F, 1.0F, color.a));
        sb.setBlendFunction(770, 1);
        sb.draw(ImageMaster.WEB_VFX, x, y, 32F, 32F, 64F, 64F, scale, scale, 0.0F, 0, 0, 64, 64, false, false);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    float x;
    float y;
    float dX;
    float dY;
    float tX;
    float tY;
}
