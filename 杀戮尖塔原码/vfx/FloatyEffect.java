// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FloatyEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.math.MathUtils;

public class FloatyEffect
{

    public FloatyEffect(float distanceScale, float speedScale)
    {
        scale = distanceScale;
        this.speedScale = speedScale;
        minV = 0.4F * scale;
        maxV = 3F * scale;
        threshold = 0.95F * distanceScale;
        vX = MathUtils.random(-maxV * speedScale, maxV * speedScale);
        vY = MathUtils.random(-maxV * speedScale, maxV * speedScale);
    }

    public void update()
    {
        x += vX * Gdx.graphics.getDeltaTime();
        y += vY * Gdx.graphics.getDeltaTime();
        if(y > threshold)
            vY = -MathUtils.random(minV * speedScale, maxV * speedScale);
        else
        if(y < -threshold)
            vY = MathUtils.random(minV * speedScale, maxV * speedScale);
        if(x > threshold)
            vX = -MathUtils.random(minV * speedScale, maxV * speedScale);
        else
        if(x < -threshold)
            vX = MathUtils.random(minV * speedScale, maxV * speedScale);
    }

    public float x;
    public float y;
    public float vX;
    public float vY;
    private float scale;
    private float minV;
    private float maxV;
    private float threshold;
    private static final float SPEED_MIN = 0.4F;
    private static final float SPEED_MAX = 3F;
    private static final float EDGE_THRESHOLD = 0.95F;
    private float speedScale;
}
