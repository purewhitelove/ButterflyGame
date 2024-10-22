// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DarkOrbActivateParticle.java

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

public class DarkOrbActivateParticle extends AbstractGameEffect
{

    public DarkOrbActivateParticle(float x, float y)
    {
        this.x = x;
        this.y = y;
        renderBehind = true;
        duration = 0.25F;
        startingDuration = 0.25F;
        color = new Color(MathUtils.random(0.5F, 1.0F), MathUtils.random(0.6F, 1.0F), 1.0F, 0.5F);
        scale = MathUtils.random(1.0F, 2.0F) * Settings.scale;
        rotation = MathUtils.random(-8F, 8F);
        flipHorizontal = MathUtils.randomBoolean();
        flipVertical = MathUtils.randomBoolean();
        scale = Settings.scale;
        scaleY = 2.0F * Settings.scale;
        aV = MathUtils.random(-100F, 100F);
    }

    public void update()
    {
        rotation += Gdx.graphics.getDeltaTime() * aV;
        scale = Interpolation.pow4Out.apply(5F, 1.0F, duration * 4F) * Settings.scale;
        scaleY = Interpolation.bounceOut.apply(0.2F, 2.0F, duration * 4F) * Settings.scale;
        color.a = Interpolation.pow5Out.apply(0.01F, 0.5F, duration * 4F);
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.setBlendFunction(770, 1);
        sb.draw(ImageMaster.DARK_ORB_ACTIVATE_VFX, x - 70F, y - 70F, 70F, 70F, 140F, 140F, scale, scaleY, rotation, 0, 0, 140, 140, flipHorizontal, flipVertical);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private float scaleY;
    private float aV;
    private static final int W = 140;
    private boolean flipHorizontal;
    private boolean flipVertical;
}
