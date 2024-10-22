// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TintEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;

public class TintEffect
{

    public TintEffect()
    {
        color = new Color(1.0F, 1.0F, 1.0F, 1.0F);
        targetColor = new Color(1.0F, 1.0F, 1.0F, 1.0F);
        lerpSpeed = 3F;
    }

    public void changeColor(Color targetColor, float lerpSpeed)
    {
        this.targetColor = targetColor;
        this.lerpSpeed = lerpSpeed;
    }

    public void changeColor(Color targetColor)
    {
        changeColor(targetColor, 3F);
    }

    public void fadeOut()
    {
        targetColor.set(color.r, color.g, color.b, 0.0F);
        lerpSpeed = 3F;
    }

    public void update()
    {
        color.lerp(targetColor, Gdx.graphics.getDeltaTime() * lerpSpeed);
    }

    public Color color;
    private Color targetColor;
    private float lerpSpeed;
}
