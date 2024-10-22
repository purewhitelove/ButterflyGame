// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PingHpEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class PingHpEffect extends AbstractGameEffect
{

    public PingHpEffect(float x)
    {
        duration = 1.5F;
        color = new Color(1.0F, 1.0F, 0.2F, 0.0F);
        this.x = x;
    }

    public void update()
    {
        scale = Interpolation.pow5In.apply(1.15F, 1.0F, duration / 1.5F);
        color.a = Interpolation.pow2Out.apply(0.0F, 0.7F, duration / 1.5F);
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.setBlendFunction(770, 1);
        sb.draw(ImageMaster.TP_HP, (x - 32F) + 32F * Settings.scale, (float)Settings.HEIGHT - 32F * Settings.scale - 32F, 32F, 32F, 64F, 64F, scale * Settings.scale, scale * Settings.scale, rotation, 0, 0, 64, 64, false, false);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private float x;
}
