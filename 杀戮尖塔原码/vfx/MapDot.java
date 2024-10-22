// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MapDot.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.screens.DungeonMapScreen;

public class MapDot
{

    public MapDot(float x, float y, float rotation, boolean jitter)
    {
        if(jitter)
        {
            this.x = x + MathUtils.random(-DIST_JITTER, DIST_JITTER);
            this.y = y + MathUtils.random(-DIST_JITTER, DIST_JITTER);
            this.rotation = rotation + MathUtils.random(-20F, 20F);
        } else
        {
            this.x = x;
            this.y = y;
            this.rotation = rotation;
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.draw(ImageMaster.MAP_DOT_1, x - 8F, (y - 8F) + DungeonMapScreen.offsetY + OFFSET_Y, 8F, 8F, 16F, 16F, Settings.scale, Settings.scale, rotation, 0, 0, 16, 16, false, false);
    }

    private float x;
    private float y;
    private float rotation;
    private static final int RAW_W = 16;
    private static final float DIST_JITTER;
    private static final float OFFSET_Y;

    static 
    {
        DIST_JITTER = 4F * Settings.scale;
        OFFSET_Y = 172F * Settings.scale;
    }
}
