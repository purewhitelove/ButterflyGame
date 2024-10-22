// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RoomTintEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class RoomTintEffect extends AbstractGameEffect
{

    public RoomTintEffect(Color color, float tintTransparency)
    {
        this(color, tintTransparency, 2.0F, true);
    }

    public RoomTintEffect(Color color, float tintTransparency, float setDuration, boolean renderBehind)
    {
        this.renderBehind = renderBehind;
        startingDuration = setDuration;
        duration = setDuration;
        this.color = color;
        this.color.a = 0.0F;
        this.tintTransparency = tintTransparency;
    }

    public void update()
    {
        if(duration > startingDuration * 0.5F)
            color.a = Interpolation.fade.apply(tintTransparency, 0.0F, (duration - startingDuration * 0.5F) / startingDuration);
        else
        if(duration < startingDuration * 0.5F)
            color.a = Interpolation.fade.apply(0.0F, tintTransparency, duration / startingDuration / 0.5F);
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
    }

    public void dispose()
    {
    }

    private static final float DEFAULT_DUR = 2F;
    private float tintTransparency;
}
