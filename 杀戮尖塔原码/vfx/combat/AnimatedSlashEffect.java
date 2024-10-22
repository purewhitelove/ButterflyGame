// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AnimatedSlashEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class AnimatedSlashEffect extends AbstractGameEffect
{

    public AnimatedSlashEffect(float x, float y, float dX, float dY, float angle, Color color1, Color color2)
    {
        this(x, y, dX, dY, angle, 2.0F, color1, color2);
    }

    public AnimatedSlashEffect(float x, float y, float dX, float dY, float angle, float targetScale, Color color1, 
            Color color2)
    {
        this.x = x - 64F - (dX / 2.0F) * Settings.scale;
        this.y = y - 64F - (dY / 2.0F) * Settings.scale;
        sX = this.x;
        sY = this.y;
        tX = this.x + (dX / 2.0F) * Settings.scale;
        tY = this.y + (dY / 2.0F) * Settings.scale;
        color = color1.cpy();
        this.color2 = color2.cpy();
        color.a = 0.0F;
        startingDuration = 0.4F;
        duration = startingDuration;
        this.targetScale = targetScale;
        scaleX = 0.01F;
        scaleY = 0.01F;
        rotation = -135F;
        rotation = angle;
    }

    public void update()
    {
        if(duration > startingDuration / 2.0F)
        {
            color.a = Interpolation.exp10In.apply(0.8F, 0.0F, (duration - startingDuration / 2.0F) / (startingDuration / 2.0F));
            scaleX = Interpolation.exp10In.apply(targetScale, 0.1F, (duration - startingDuration / 2.0F) / (startingDuration / 2.0F));
            scaleY = scaleX;
            x = Interpolation.fade.apply(tX, sX, (duration - startingDuration / 2.0F) / (startingDuration / 2.0F));
            y = Interpolation.fade.apply(tY, sY, (duration - startingDuration / 2.0F) / (startingDuration / 2.0F));
        } else
        {
            scaleX = Interpolation.pow2In.apply(0.5F, targetScale, duration / (startingDuration / 2.0F));
            color.a = Interpolation.pow5In.apply(0.0F, 0.8F, duration / (startingDuration / 2.0F));
        }
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color2);
        sb.setBlendFunction(770, 1);
        sb.draw(ImageMaster.ANIMATED_SLASH_VFX, x, y, 64F, 64F, 128F, 128F, scaleX * 0.4F * MathUtils.random(0.95F, 1.05F) * Settings.scale, scaleY * 0.7F * MathUtils.random(0.95F, 1.05F) * Settings.scale, rotation, 0, 0, 128, 128, false, false);
        sb.setColor(color);
        sb.draw(ImageMaster.ANIMATED_SLASH_VFX, x, y, 64F, 64F, 128F, 128F, scaleX * 0.7F * MathUtils.random(0.95F, 1.05F) * Settings.scale, scaleY * MathUtils.random(0.95F, 1.05F) * Settings.scale, rotation, 0, 0, 128, 128, false, false);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private float sX;
    private float sY;
    private float tX;
    private float tY;
    private float scaleX;
    private float scaleY;
    private float targetScale;
    private Color color2;
}
