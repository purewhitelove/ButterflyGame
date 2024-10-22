// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LosePowerEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class LosePowerEffect extends AbstractGameEffect
{

    public LosePowerEffect(float x, float y, Texture img)
    {
        shakeLeft = true;
        this.img = img;
        duration = 1.0F;
        startingDuration = 1.0F;
        this.x = x;
        this.y = y;
        shake_offset = 0.0F;
        offset_y = 0.0F;
        shakeVelo = SHAKE_VELO_START;
        popVelo = POP_VELO_START;
        color = Color.WHITE.cpy();
    }

    public void update()
    {
        if(duration != startingDuration);
        offset_y += popVelo * Gdx.graphics.getDeltaTime();
        popVelo -= FALL_SPEED;
        shakeVelo -= Gdx.graphics.getDeltaTime() * SHAKE_TAPER_SPEED;
        if(shakeLeft)
        {
            shake_offset -= shakeVelo * Gdx.graphics.getDeltaTime();
            if(shake_offset < -SHAKE_DIST)
                shakeLeft = !shakeLeft;
        } else
        {
            shake_offset += shakeVelo * Gdx.graphics.getDeltaTime();
            if(shake_offset > SHAKE_DIST)
                shakeLeft = !shakeLeft;
        }
        if(color.g > 0.3F)
        {
            color.g -= Gdx.graphics.getDeltaTime();
            color.b -= Gdx.graphics.getDeltaTime();
        }
        duration -= Gdx.graphics.getDeltaTime();
        if(duration > 1.0F)
            color.a = (1.5F - duration) * 2.0F;
        else
        if(duration < 1.0F && duration > 0.33F)
            color.a = 1.0F;
        else
        if(duration < 0.33F && duration > 0.0F)
            color.a = duration * 3F;
        else
        if(duration < 0.0F)
        {
            isDone = true;
            color.a = 0.0F;
        }
    }

    public void render(SpriteBatch sb)
    {
        if(!isDone)
        {
            sb.setColor(color);
            sb.draw(img, (x - IMG_WIDTH * 0.25F) + shake_offset, (y - IMG_WIDTH * 0.25F) + offset_y, IMG_WIDTH * 1.5F, IMG_WIDTH * 1.5F);
        }
    }

    public void dispose()
    {
    }

    private static final float EFFECT_DUR = 1F;
    private float x;
    private float y;
    private float shake_offset;
    private float offset_y;
    private Texture img;
    private static final float IMG_WIDTH;
    private float shakeVelo;
    private float popVelo;
    private static final float POP_VELO_START;
    private static final float SHAKE_VELO_START;
    private static final float SHAKE_DIST;
    private static final float FALL_SPEED;
    private static final float SHAKE_TAPER_SPEED;
    private boolean shakeLeft;

    static 
    {
        IMG_WIDTH = 64F * Settings.scale;
        POP_VELO_START = 150F * Settings.scale;
        SHAKE_VELO_START = 200F * Settings.scale;
        SHAKE_DIST = 16F * Settings.scale;
        FALL_SPEED = 8F * Settings.scale;
        SHAKE_TAPER_SPEED = 30F * Settings.scale;
    }
}
