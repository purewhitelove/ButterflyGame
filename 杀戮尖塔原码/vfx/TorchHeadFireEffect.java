// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TorchHeadFireEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect

public class TorchHeadFireEffect extends AbstractGameEffect
{

    public TorchHeadFireEffect(float x, float y)
    {
        flippedX = MathUtils.randomBoolean();
        img = getImg();
        this.x = x + MathUtils.random(-5F, 5F) * Settings.scale;
        this.y = y + MathUtils.random(-5F, 5F) * Settings.scale;
        vX = MathUtils.random(-30F, 30F) * Settings.scale;
        vY = MathUtils.random(20F, 100F) * Settings.scale;
        duration = 0.7F;
        color = Color.CHARTREUSE.cpy();
        color.a = 0.0F;
        scale = MathUtils.random(0.8F, 0.9F) * Settings.scale;
        renderBehind = MathUtils.randomBoolean();
    }

    private Texture getImg()
    {
        if(MathUtils.randomBoolean())
            return ImageMaster.GHOST_ORB_1;
        else
            return ImageMaster.GHOST_ORB_2;
    }

    public void update()
    {
        x += vX * Gdx.graphics.getDeltaTime();
        y += vY * Gdx.graphics.getDeltaTime();
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
        color.a = duration / 2.0F;
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        sb.setColor(color);
        sb.draw(img, x - 64F, y - 64F, 64F, 64F, 128F, 128F, scale * 1.2F, scale, 0.0F, 0, 0, 128, 128, flippedX, false);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private Texture img;
    private float x;
    private float y;
    private float vX;
    private float vY;
    private static final int W = 128;
    private boolean flippedX;
    private static final float DUR = 0.7F;
}
