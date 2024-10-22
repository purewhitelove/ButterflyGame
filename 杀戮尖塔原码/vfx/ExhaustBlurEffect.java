// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ExhaustBlurEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect

public class ExhaustBlurEffect extends AbstractGameEffect
{

    public ExhaustBlurEffect(float x, float y)
    {
        color = Color.BLACK.cpy();
        color.r = MathUtils.random(0.1F, 0.4F);
        color.g = color.r;
        color.b = color.r + 0.05F;
        if(MathUtils.randomBoolean())
        {
            img = ImageMaster.EXHAUST_L;
            duration = MathUtils.random(0.9F, 1.2F);
            targetScale = MathUtils.random(0.5F, 1.3F);
        } else
        {
            img = ImageMaster.EXHAUST_S;
            duration = MathUtils.random(0.6F, 1.4F);
            targetScale = MathUtils.random(0.3F, 1.0F);
        }
        startDur = duration;
        this.x = (x + MathUtils.random(-150F * Settings.scale, 150F * Settings.scale)) - (float)img.packedWidth / 2.0F;
        this.y = (y + MathUtils.random(-240F * Settings.scale, 150F * Settings.scale)) - (float)img.packedHeight / 2.0F;
        scale = 0.01F;
        rotation = MathUtils.random(360F);
        aV = MathUtils.random(-250F, 250F);
        vY = MathUtils.random(1.0F * Settings.scale, 5F * Settings.scale);
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
        x += MathUtils.random(-2F * Settings.scale, 2.0F * Settings.scale);
        y += MathUtils.random(-2F * Settings.scale, 2.0F * Settings.scale);
        y += vY * Gdx.graphics.getDeltaTime() * 60F;
        rotation += aV * Gdx.graphics.getDeltaTime();
        scale = Interpolation.swing.apply(0.01F, targetScale, 1.0F - duration / startDur);
        if(duration < 0.33F)
            color.a = duration * 3F;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, rotation);
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private float vY;
    private float aV;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private float startDur;
    private float targetScale;
}
