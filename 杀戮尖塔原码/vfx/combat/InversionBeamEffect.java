// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   InversionBeamEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class InversionBeamEffect extends AbstractGameEffect
{

    public InversionBeamEffect(float x)
    {
        startingDuration = 0.5F;
        duration = startingDuration;
        this.x = x;
        y = 0.01F;
        renderBehind = MathUtils.randomBoolean();
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
        else
        if(duration < startingDuration / 2.0F)
            y = Interpolation.fade.apply(0.01F, 50F, duration / (startingDuration / 2.0F)) * Settings.scale;
        else
            y = Interpolation.fade.apply(50F, 0.01F, (duration - startingDuration / 2.0F) / (startingDuration / 2.0F)) * Settings.scale;
        scale = Interpolation.bounce.apply(0.01F, 5F, duration / startingDuration);
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(775, 769);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, x - y / 2.0F, 0.0F, y, (float)Settings.HEIGHT - 64F * Settings.scale);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
}
