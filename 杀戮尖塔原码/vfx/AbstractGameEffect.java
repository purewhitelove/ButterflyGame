// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractGameEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.megacrit.cardcrawl.core.Settings;

public abstract class AbstractGameEffect
    implements Disposable
{

    public AbstractGameEffect()
    {
        isDone = false;
        scale = Settings.scale;
        rotation = 0.0F;
        renderBehind = false;
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < startingDuration / 2.0F)
            color.a = duration / (startingDuration / 2.0F);
        if(duration < 0.0F)
        {
            isDone = true;
            color.a = 0.0F;
        }
    }

    public abstract void render(SpriteBatch spritebatch);

    public void render(SpriteBatch spritebatch, float f, float f1)
    {
    }

    public abstract void dispose();

    public float duration;
    public float startingDuration;
    protected Color color;
    public boolean isDone;
    protected float scale;
    protected float rotation;
    public boolean renderBehind;
}
