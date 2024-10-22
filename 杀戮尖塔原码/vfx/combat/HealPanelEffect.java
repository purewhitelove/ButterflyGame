// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HealPanelEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class HealPanelEffect extends AbstractGameEffect
{

    public HealPanelEffect(float x)
    {
        this.x = x;
        if(img == null)
            img = ImageMaster.loadImage("images/ui/topPanel/panel_heart_white.png");
        color = Color.CHARTREUSE.cpy();
        color.a = 0.0F;
        duration = 1.5F;
        scale = Settings.scale;
    }

    public void update()
    {
        scale = Interpolation.exp10In.apply(1.2F, 2.0F, duration / 1.5F) * Settings.scale;
        if(duration > 1.0F)
            color.a = Interpolation.pow5In.apply(0.6F, 0.0F, (duration - 1.0F) * 2.0F);
        else
            color.a = Interpolation.fade.apply(0.0F, 0.6F, duration);
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.setBlendFunction(770, 1);
        sb.draw(img, (x - 32F) + 32F * Settings.scale, (float)Settings.HEIGHT - 32F * Settings.scale - 32F, 32F, 32F, 64F, 64F, scale, scale, rotation, 0, 0, 64, 64, false, false);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private float x;
    private static Texture img = null;

}
