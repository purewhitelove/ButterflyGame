// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TimeWarpTurnEndEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class TimeWarpTurnEndEffect extends AbstractGameEffect
{

    public TimeWarpTurnEndEffect()
    {
        if(img == null)
            img = AbstractPower.atlas.findRegion("128/time");
        startingDuration = 2.0F;
        duration = startingDuration;
        scale = Settings.scale * 3F;
        x = (float)Settings.WIDTH * 0.5F - (float)img.packedWidth / 2.0F;
        y = (float)img.packedHeight / 2.0F;
        color = Color.WHITE.cpy();
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
        if(duration < 1.0F)
            color.a = Interpolation.fade.apply(0.0F, 1.0F, duration);
        else
            y = Interpolation.swingIn.apply((float)Settings.HEIGHT * 0.7F - (float)img.packedHeight / 2.0F, (float)(-img.packedHeight) / 2.0F, duration - 1.0F);
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, duration * 360F);
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private static com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img = null;

}
