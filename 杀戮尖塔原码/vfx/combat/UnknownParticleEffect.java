// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UnknownParticleEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class UnknownParticleEffect extends AbstractGameEffect
{

    public UnknownParticleEffect(float x, float y)
    {
        duration = 1.5F;
        if(renderNum == 0)
        {
            targetScale = Settings.scale * 0.8F;
            rotation = 24F;
            this.x = x - 24F * Settings.scale;
            this.y = y - MathUtils.random(6F, 10F) * Settings.scale;
            if(MathUtils.randomBoolean())
                color = Color.GOLDENROD.cpy();
            else
                color = Color.GOLD.cpy();
            renderBehind = true;
        } else
        if(renderNum == 1)
        {
            targetScale = Settings.scale * 1.2F;
            rotation = 0.0F;
            this.x = x;
            this.y = y;
            color = Color.WHITE.cpy();
            renderBehind = false;
        } else
        {
            targetScale = Settings.scale * 0.8F;
            rotation = -24F;
            this.x = x + 24F * Settings.scale;
            this.y = y - MathUtils.random(6F, 10F) * Settings.scale;
            if(MathUtils.randomBoolean())
                color = Color.GOLDENROD.cpy();
            else
                color = Color.GOLD.cpy();
            renderBehind = true;
        }
        scale = 0.01F;
        renderNum++;
        if(renderNum > 2)
            renderNum = 0;
        img = ImageMaster.INTENT_UNKNOWN_L;
    }

    public void update()
    {
        if(duration > 0.5F)
            scale = Interpolation.elasticOut.apply(0.01F, targetScale, 1.5F - duration);
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
        else
        if(duration < 0.5F)
            color.a = duration * 2.0F;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.draw(img, x - 64F, y - 64F, 64F, 64F, 128F, 128F, scale, scale, rotation, 0, 0, 128, 128, false, false);
    }

    public void dispose()
    {
    }

    private Texture img;
    private static final int RAW_W = 128;
    private static final float DURATION = 1.5F;
    private static int renderNum = 0;
    private float x;
    private float y;
    private float scale;
    private float targetScale;

}
