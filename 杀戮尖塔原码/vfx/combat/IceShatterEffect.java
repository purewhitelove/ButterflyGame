// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IceShatterEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class IceShatterEffect extends AbstractGameEffect
{

    public IceShatterEffect(float x, float y)
    {
        if(MathUtils.randomBoolean())
            img = ImageMaster.FROST_ACTIVATE_VFX_1;
        else
            img = ImageMaster.FROST_ACTIVATE_VFX_2;
        this.x = x;
        this.y = y;
        vX = MathUtils.random(-300F, 300F) * Settings.scale;
        vY = MathUtils.random(-900F, 200F) * Settings.scale;
        duration = 0.5F;
        scale = MathUtils.random(0.75F, 1.25F) * Settings.scale;
        color = new Color(0.5F, 0.8F, 1.0F, MathUtils.random(0.9F, 1.0F));
        Vector2 derp = new Vector2(vX, vY);
        rotation = derp.angle() - 45F;
    }

    public void update()
    {
        x += vX * Gdx.graphics.getDeltaTime();
        y -= vY * Gdx.graphics.getDeltaTime();
        rotation += Gdx.graphics.getDeltaTime() * vX;
        vY += 2000F * Settings.scale * Gdx.graphics.getDeltaTime();
        color.a = duration * 2.0F;
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        sb.setColor(color);
        sb.draw(img, x, y, 32F, 32F, 64F, 64F, scale, scale, rotation, 0, 0, 64, 64, false, false);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private float vX;
    private float vY;
    private Texture img;
}
