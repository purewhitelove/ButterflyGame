// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StarBounceEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect

public class StarBounceEffect extends AbstractGameEffect
{

    public StarBounceEffect(float x, float y)
    {
        img = ImageMaster.TINY_STAR;
        duration = MathUtils.random(0.5F, 1.0F);
        this.x = x - (float)(img.packedWidth / 2);
        this.y = y - (float)(img.packedHeight / 2);
        color = new Color(MathUtils.random(0.8F, 1.0F), MathUtils.random(0.6F, 0.8F), MathUtils.random(0.0F, 0.6F), 0.0F);
        color.a = 0.0F;
        rotation = MathUtils.random(0.0F, 360F);
        scale = MathUtils.random(0.5F, 2.0F) * Settings.scale;
        vX = MathUtils.random(-900F, 900F) * Settings.scale;
        vY = MathUtils.random(500F, 1000F) * Settings.scale;
        floor = MathUtils.random(100F, 250F) * Settings.scale;
    }

    public void update()
    {
        vY -= (GRAVITY / scale) * Gdx.graphics.getDeltaTime();
        x += vX * Gdx.graphics.getDeltaTime();
        y += vY * Gdx.graphics.getDeltaTime();
        Vector2 test = new Vector2(vX, vY);
        rotation = test.angle();
        if(y < floor)
        {
            vY = -vY * 0.75F;
            y = floor + 0.1F;
            vX *= 1.1F;
        }
        if(1.0F - duration < 0.1F)
            color.a = Interpolation.fade.apply(0.0F, 1.0F, (1.0F - duration) * 10F);
        else
            color.a = Interpolation.pow2Out.apply(0.0F, 1.0F, duration);
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        sb.setColor(color);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * MathUtils.random(0.8F, 1.2F), scale * MathUtils.random(0.8F, 1.2F), rotation);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * MathUtils.random(0.8F, 1.2F), scale * MathUtils.random(0.8F, 1.2F), rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private static final float DUR = 1F;
    private float x;
    private float y;
    private float vX;
    private float vY;
    private float floor;
    private static final float GRAVITY;

    static 
    {
        GRAVITY = 3000F * Settings.scale;
    }
}
