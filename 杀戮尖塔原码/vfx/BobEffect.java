// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BobEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;

public class BobEffect
{

    public BobEffect()
    {
        this(DEFAULT_DIST, 4F);
    }

    public BobEffect(float speed)
    {
        this(DEFAULT_DIST, speed);
    }

    public BobEffect(float dist, float speed)
    {
        y = 0.0F;
        timer = MathUtils.random(0.0F, 359F);
        this.speed = speed;
        this.dist = dist;
    }

    public void update()
    {
        y = MathUtils.sin(timer) * dist;
        timer += Gdx.graphics.getDeltaTime() * speed;
    }

    public float y;
    public float speed;
    public float dist;
    private float timer;
    private static final float DEFAULT_DIST;
    private static final float DEFAULT_SPEED = 4F;

    static 
    {
        DEFAULT_DIST = 5F * Settings.scale;
    }
}
