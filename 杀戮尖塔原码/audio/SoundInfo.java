// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SoundInfo.java

package com.megacrit.cardcrawl.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.math.Interpolation;

public class SoundInfo
{

    public SoundInfo(String name, long id)
    {
        isDone = false;
        fadeDuration = 5F;
        volumeMultiplier = 1.0F;
        this.name = name;
        this.id = id;
    }

    public void update()
    {
        if(fadeDuration != 0.0F)
        {
            fadeDuration -= Gdx.graphics.getDeltaTime();
            volumeMultiplier = Interpolation.fade.apply(1.0F, 0.0F, 1.0F - fadeDuration / 5F);
            if(fadeDuration < 0.0F)
            {
                isDone = true;
                fadeDuration = 0.0F;
            }
        }
    }

    public String name;
    public long id;
    public boolean isDone;
    private static final float FADE_OUT_DURATION = 5F;
    private float fadeDuration;
    public float volumeMultiplier;
}
