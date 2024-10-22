// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MapCircleEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect

public class MapCircleEffect extends AbstractGameEffect
{

    public MapCircleEffect(float x, float y, float angle)
    {
        img = ImageMaster.MAP_CIRCLE_1;
        this.x = x;
        this.y = y;
        scale = Settings.scale;
        duration = 1.2F;
        startingDuration = 1.2F;
        scale = 3F * Settings.scale;
        rotation = angle;
    }

    public void update()
    {
        if(Settings.FAST_MODE)
            duration -= Gdx.graphics.getDeltaTime();
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 1.0F)
            img = ImageMaster.MAP_CIRCLE_5;
        else
        if(duration < 1.05F)
            img = ImageMaster.MAP_CIRCLE_4;
        else
        if(duration < 1.1F)
            img = ImageMaster.MAP_CIRCLE_3;
        else
        if(duration < 1.15F)
            img = ImageMaster.MAP_CIRCLE_2;
        scale = MathHelper.scaleLerpSnap(scale, 1.5F * Settings.scale);
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(new Color(0.09F, 0.13F, 0.17F, 1.0F));
        sb.draw(img, x - 96F, y - 96F, 96F, 96F, 192F, 192F, scale, scale, rotation, 0, 0, 192, 192, false, false);
    }

    public void dispose()
    {
    }

    public static Texture img;
    private float x;
    private float y;
    public static final int W = 192;
}
