// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SilentGainPowerEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class SilentGainPowerEffect extends AbstractGameEffect
{

    public SilentGainPowerEffect(AbstractPower power)
    {
        region48 = null;
        if(power.img == null)
            region48 = power.region48;
        duration = 2.0F;
        startingDuration = 2.0F;
        scale = Settings.scale;
        color = new Color(1.0F, 1.0F, 1.0F, 0.5F);
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        if(duration > 0.5F)
            scale = Interpolation.exp5Out.apply(3F * Settings.scale, Settings.scale, -(duration - 2.0F) / 1.5F);
        else
            color.a = Interpolation.fade.apply(0.5F, 0.0F, 1.0F - duration);
    }

    public void render(SpriteBatch sb, float x, float y)
    {
        sb.setColor(color);
        sb.setBlendFunction(770, 1);
        if(region48 != null)
            sb.draw(region48, x - (float)region48.packedWidth / 2.0F, y - (float)region48.packedHeight / 2.0F, (float)region48.packedWidth / 2.0F, (float)region48.packedHeight / 2.0F, region48.packedWidth, region48.packedHeight, scale, scale, 0.0F);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    public void render(SpriteBatch spritebatch)
    {
    }

    private static final float EFFECT_DUR = 2F;
    private float scale;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion region48;
}
