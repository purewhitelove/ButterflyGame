// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GenericSmokeEffect.java

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

public class GenericSmokeEffect extends AbstractGameEffect
{

    public GenericSmokeEffect(float x, float y)
    {
        color = Color.WHITE.cpy();
        color.r = MathUtils.random(0.4F, 0.6F);
        color.g = color.r - 0.05F;
        color.b = color.r - 0.1F;
        color.a = 0.0F;
        renderBehind = false;
        if(MathUtils.randomBoolean())
        {
            img = ImageMaster.EXHAUST_L;
            duration = MathUtils.random(0.9F, 1.2F);
            targetScale = MathUtils.random(0.9F, 1.3F);
        } else
        {
            img = ImageMaster.EXHAUST_S;
            duration = MathUtils.random(0.6F, 1.4F);
            targetScale = MathUtils.random(0.7F, 1.0F);
        }
        startDur = duration;
        this.x = x - (float)img.packedWidth / 2.0F;
        this.y = y - (float)img.packedHeight / 2.0F;
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
        y += vY;
        rotation += aV * Gdx.graphics.getDeltaTime();
        scale = Interpolation.swing.apply(0.01F, targetScale, 1.0F - duration / startDur);
        if(duration < startDur / 2.0F)
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
