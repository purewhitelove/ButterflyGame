// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FrostOrbActivateParticle.java

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

public class FrostOrbActivateParticle extends AbstractGameEffect
{

    public FrostOrbActivateParticle(int index, float x, float y)
    {
        img = null;
        cX = x;
        cY = y;
        sX = cX;
        sY = cY;
        rotation = MathUtils.random(-5F, 5F);
        switch(index)
        {
        case 0: // '\0'
            tX = sX;
            tY = sY + 5F * Settings.scale;
            img = ImageMaster.FROST_ACTIVATE_VFX_1;
            tRotation = MathUtils.random(-5F, 5F);
            break;

        case 1: // '\001'
            tX = sX - 10F * Settings.scale;
            tY = sY - 5F * Settings.scale;
            img = ImageMaster.FROST_ACTIVATE_VFX_2;
            tRotation = rotation + MathUtils.random(-30F, 30F);
            break;

        default:
            tX = sX + 10F * Settings.scale;
            tY = sY - 5F * Settings.scale;
            tRotation = rotation - MathUtils.random(-30F, 30F);
            img = ImageMaster.FROST_ACTIVATE_VFX_3;
            break;
        }
        renderBehind = false;
        color = new Color(0.5F, 0.95F, 1.0F, 0.9F);
        scale = 2.0F * Settings.scale;
        startingDuration = 0.3F;
        duration = startingDuration;
    }

    public void update()
    {
        color.a = Interpolation.pow2Out.apply(0.2F, 0.9F, duration / startingDuration);
        if(color.a < 0.0F)
            color.a = 0.0F;
        cX = Interpolation.swingIn.apply(tX, sX, duration / startingDuration);
        cY = Interpolation.swingIn.apply(tY, sY, duration / startingDuration);
        rotation = Interpolation.swingIn.apply(tRotation, 0.0F, duration / startingDuration);
        scale = Interpolation.fade.apply(2.4F, 2.0F, duration / startingDuration) * Settings.scale;
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.setBlendFunction(770, 1);
        sb.draw(img, cX - 32F, cY - 32F, 32F, 32F, 64F, 64F, scale, scale, rotation, 0, 0, 64, 64, false, false);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private Texture img;
    public static final int W = 64;
    private float cX;
    private float cY;
    private float sX;
    private float sY;
    private float tX;
    private float tY;
    private float tRotation;
}
