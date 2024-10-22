// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DarkOrbPassiveEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class DarkOrbPassiveEffect extends AbstractGameEffect
{

    public DarkOrbPassiveEffect(float x, float y)
    {
        int roll = MathUtils.random(2);
        switch(roll)
        {
        case 0: // '\0'
            img = ImageMaster.DARK_ORB_PASSIVE_VFX_1;
            break;

        case 1: // '\001'
            img = ImageMaster.DARK_ORB_PASSIVE_VFX_2;
            break;

        default:
            img = ImageMaster.DARK_ORB_PASSIVE_VFX_3;
            break;
        }
        color = new Color(MathUtils.random(0.0F, 1.0F), 0.3F, MathUtils.random(0.7F, 1.0F), 0.01F);
        renderBehind = false;
        duration = 2.0F;
        startingDuration = 2.0F;
        this.x = x;
        this.y = y;
        rotation = MathUtils.random(360F);
        startingScale = MathUtils.random(1.2F, 1.8F) * Settings.scale;
        scale = startingScale;
        rotationSpeed = MathUtils.random(100F, 360F);
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        rotation += Gdx.graphics.getDeltaTime() * rotationSpeed;
        if(duration > 1.0F)
            color.a = Interpolation.fade.apply(1.0F, 0.0F, duration - 1.0F);
        else
            color.a = Interpolation.fade.apply(0.0F, 1.0F, duration);
        scale = Interpolation.swingOut.apply(0.01F, startingScale, duration / 2.0F) * Settings.scale;
        if(scale < 0.0F || duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.setBlendFunction(770, 1);
        sb.draw(img, x - 37F, y - 37F, 37F, 37F, 74F, 74F, scale, scale, rotation, 0, 0, 74, 74, false, false);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private float startingScale;
    private float rotationSpeed;
    private Texture img;
    private static final int W = 74;
}
