// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DefectVictoryEyesEffect.java

package com.megacrit.cardcrawl.vfx.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class DefectVictoryEyesEffect extends AbstractGameEffect
{

    public DefectVictoryEyesEffect()
    {
        renderBehind = true;
        if(img == null)
            img = ImageMaster.loadImage("images/vfx/defect/eyes2.png");
        x = (float)Settings.WIDTH / 2.0F;
        y = (float)Settings.HEIGHT / 2.0F - 50F * Settings.scale;
        scale = 1.5F * Settings.scale;
        color = new Color(0.5F, 0.8F, 1.0F, 0.0F);
    }

    public void update()
    {
        color.a = MathHelper.slowColorLerpSnap(color.a, 0.5F);
        duration += Gdx.graphics.getDeltaTime();
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.setBlendFunction(770, 1);
        sb.draw(img, x - 512F, y - 180F, 512F, 180F, 1024F, 360F, scale * (MathUtils.cos(duration * 4F) / 20F + 1.0F), scale * MathUtils.random(0.99F, 1.01F), rotation, 0, 0, 1024, 360, false, false);
        sb.draw(img, x - 512F, y - 180F, 512F, 180F, 1024F, 360F, scale * (MathUtils.cos(duration * 5F) / 30F + 1.0F) * MathUtils.random(0.99F, 1.01F), scale * MathUtils.random(0.99F, 1.01F), rotation, 0, 0, 1024, 360, false, false);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private static Texture img;
}
