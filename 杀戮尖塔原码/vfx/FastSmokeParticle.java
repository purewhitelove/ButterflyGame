// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FastSmokeParticle.java

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

public class FastSmokeParticle extends AbstractGameEffect
{

    public FastSmokeParticle(float x, float y)
    {
        scale = 0.01F;
        if(img == null)
            img = ImageMaster.EXHAUST_L;
        targetScale = MathUtils.random(0.3F, 0.6F) * Settings.scale;
        color = new Color(1.0F, MathUtils.random(0.8F, 1.0F), MathUtils.random(0.5F, 0.8F), 1.0F);
        this.x = x - (float)img.packedWidth / 2.0F;
        this.y = y - (float)img.packedHeight / 2.0F;
        rotation = MathUtils.random(360F);
        duration = 0.6F;
    }

    public void update()
    {
        if(color.b > 0.1F)
        {
            color.b -= Gdx.graphics.getDeltaTime() * 4F;
            color.g -= Gdx.graphics.getDeltaTime() * 3F;
            color.r -= Gdx.graphics.getDeltaTime() * 0.5F;
        } else
        if(color.g > 0.1F)
            color.g -= Gdx.graphics.getDeltaTime() * 4F;
        else
        if(color.r > 0.1F)
            color.r -= Gdx.graphics.getDeltaTime() * 4F;
        if(color.b < 0.0F)
            color.b = 0.0F;
        if(color.g < 0.0F)
            color.g = 0.0F;
        if(color.r < 0.0F)
            color.r = 0.0F;
        scale = Interpolation.swingIn.apply(targetScale, 0.1F, duration / 0.6F);
        rotation += vX * 2.0F * Gdx.graphics.getDeltaTime();
        color.a = duration;
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
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
    private float vX;
    private float scale;
    private float targetScale;
    private static com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
}
