// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HbBlockBrokenEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class HbBlockBrokenEffect extends AbstractGameEffect
{

    public HbBlockBrokenEffect(float x, float y)
    {
        rotateSpeed = 55F;
        color = new Color(0.6F, 0.93F, 0.98F, 1.0F);
        duration = 1.1F;
        this.x = x;
        this.y = y;
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
        if(duration < 0.7F)
        {
            offsetX = Interpolation.circleOut.apply(0.0F, DEST_X, 1.0F - duration / 0.7F);
            offsetY = Interpolation.fade.apply(0.0F, DEST_Y, 1.0F - duration / 0.7F);
            offsetAngle += Gdx.graphics.getDeltaTime() * rotateSpeed;
            color.a = Interpolation.fade.apply(1.0F, 0.0F, 1.0F - duration / 0.7F);
        } else
        {
            offsetX -= Gdx.graphics.getDeltaTime() * INITIAL_VX;
            offsetAngle += Gdx.graphics.getDeltaTime() * 5F;
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.draw(ImageMaster.BLOCK_ICON_L, (x - 32F) + offsetX, (y - 32F) + offsetY, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, offsetAngle, 0, 0, 64, 64, false, false);
        sb.draw(ImageMaster.BLOCK_ICON_R, x - 32F - offsetX, (y - 32F) + offsetY, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, -offsetAngle, 0, 0, 64, 64, false, false);
    }

    public void dispose()
    {
    }

    private static final float WAIT_DUR = 0.4F;
    private static final float EFFECT_DUR = 0.7F;
    private static final int W = 64;
    private static final float DEST_X;
    private static final float DEST_Y;
    private static final float INITIAL_VX;
    private static final float INITIAL_AV = 5F;
    private float x;
    private float y;
    private float offsetAngle;
    private float rotateSpeed;
    private float offsetX;
    private float offsetY;

    static 
    {
        DEST_X = -15F * Settings.scale;
        DEST_Y = -10F * Settings.scale;
        INITIAL_VX = 5F * Settings.scale;
    }
}
