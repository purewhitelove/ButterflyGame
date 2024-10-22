// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ShieldParticleEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect

public class ShieldParticleEffect extends AbstractGameEffect
{

    public ShieldParticleEffect(float x, float y)
    {
        scale = Settings.scale / 2.0F;
        duration = 2.0F;
        this.x = x;
        this.y = y;
        renderBehind = true;
        color = new Color(1.0F, 1.0F, 1.0F, 0.0F);
    }

    public void update()
    {
        scale += Gdx.graphics.getDeltaTime() * Settings.scale * 1.1F;
        if(duration > 1.0F)
            color.a = Interpolation.fade.apply(0.0F, 0.3F, 1.0F - (duration - 1.0F));
        else
            color.a = Interpolation.fade.apply(0.3F, 0.0F, 1.0F - duration);
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        sb.setColor(color);
        sb.draw(ImageMaster.INTENT_DEFEND, x - 32F, y - 32F, 32F, 32F, 64F, 64F, scale, scale, 0.0F, 0, 0, 64, 64, false, false);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private static final int RAW_W = 64;
    private float x;
    private float y;
    private float scale;
}
