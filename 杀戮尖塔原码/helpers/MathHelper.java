// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MathHelper.java

package com.megacrit.cardcrawl.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;

public class MathHelper
{

    public MathHelper()
    {
    }

    public static float cardLerpSnap(float startX, float targetX)
    {
        if(startX != targetX)
        {
            startX = MathUtils.lerp(startX, targetX, Gdx.graphics.getDeltaTime() * 6F);
            if(Math.abs(startX - targetX) < Settings.CARD_SNAP_THRESHOLD)
                startX = targetX;
        }
        return startX;
    }

    public static float cardScaleLerpSnap(float startX, float targetX)
    {
        if(startX != targetX)
        {
            startX = MathUtils.lerp(startX, targetX, Gdx.graphics.getDeltaTime() * 7.5F);
            if(Math.abs(startX - targetX) < 0.003F)
                startX = targetX;
        }
        return startX;
    }

    public static float uiLerpSnap(float startX, float targetX)
    {
        if(startX != targetX)
        {
            startX = MathUtils.lerp(startX, targetX, Gdx.graphics.getDeltaTime() * 9F);
            if(Math.abs(startX - targetX) < Settings.UI_SNAP_THRESHOLD)
                startX = targetX;
        }
        return startX;
    }

    public static float orbLerpSnap(float startX, float targetX)
    {
        if(startX != targetX)
        {
            startX = MathUtils.lerp(startX, targetX, Gdx.graphics.getDeltaTime() * 6F);
            if(Math.abs(startX - targetX) < Settings.UI_SNAP_THRESHOLD)
                startX = targetX;
        }
        return startX;
    }

    public static float mouseLerpSnap(float startX, float targetX)
    {
        if(startX != targetX)
        {
            startX = MathUtils.lerp(startX, targetX, Gdx.graphics.getDeltaTime() * 20F);
            if(Math.abs(startX - targetX) < Settings.UI_SNAP_THRESHOLD)
                startX = targetX;
        }
        return startX;
    }

    public static float scaleLerpSnap(float startX, float targetX)
    {
        if(startX != targetX)
        {
            startX = MathUtils.lerp(startX, targetX, Gdx.graphics.getDeltaTime() * 8F);
            if(Math.abs(startX - targetX) < 0.003F)
                startX = targetX;
        }
        return startX;
    }

    public static float fadeLerpSnap(float startX, float targetX)
    {
        if(startX != targetX)
        {
            startX = MathUtils.lerp(startX, targetX, Gdx.graphics.getDeltaTime() * 12F);
            if(Math.abs(startX - targetX) < 0.01F)
                startX = targetX;
        }
        return startX;
    }

    public static float popLerpSnap(float startX, float targetX)
    {
        if(startX != targetX)
        {
            startX = MathUtils.lerp(startX, targetX, Gdx.graphics.getDeltaTime() * 8F);
            if(Math.abs(startX - targetX) < 0.003F)
                startX = targetX;
        }
        return startX;
    }

    public static float angleLerpSnap(float startX, float targetX)
    {
        if(startX != targetX)
        {
            startX = MathUtils.lerp(startX, targetX, Gdx.graphics.getDeltaTime() * 12F);
            if(Math.abs(startX - targetX) < 0.003F)
                startX = targetX;
        }
        return startX;
    }

    public static float slowColorLerpSnap(float startX, float targetX)
    {
        if(startX != targetX)
        {
            startX = MathUtils.lerp(startX, targetX, Gdx.graphics.getDeltaTime() * 3F);
            if(Math.abs(startX - targetX) < 0.01F)
                startX = targetX;
        }
        return startX;
    }

    public static float scrollSnapLerpSpeed(float startX, float targetX)
    {
        if(startX != targetX)
        {
            startX = MathUtils.lerp(startX, targetX, Gdx.graphics.getDeltaTime() * 10F);
            if(Math.abs(startX - targetX) < Settings.UI_SNAP_THRESHOLD)
                startX = targetX;
        }
        return startX;
    }

    public static float valueFromPercentBetween(float min, float max, float percent)
    {
        float diff = max - min;
        return min + diff * percent;
    }

    public static float percentFromValueBetween(float min, float max, float value)
    {
        float diff = max - min;
        float offset = value - min;
        return offset / diff;
    }
}
