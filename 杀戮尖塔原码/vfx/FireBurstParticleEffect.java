// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FireBurstParticleEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect

public class FireBurstParticleEffect extends AbstractGameEffect
{

    public FireBurstParticleEffect(float x, float y)
    {
        int roll = MathUtils.random(0, 2);
        if(roll == 0)
            img = ImageMaster.TORCH_FIRE_1;
        else
        if(roll == 1)
            img = ImageMaster.TORCH_FIRE_2;
        else
            img = ImageMaster.TORCH_FIRE_3;
        duration = MathUtils.random(0.5F, 1.0F);
        this.x = x - (float)(img.packedWidth / 2);
        this.y = y - (float)(img.packedHeight / 2);
        color = new Color(MathUtils.random(0.1F, 0.3F), MathUtils.random(0.8F, 1.0F), MathUtils.random(0.1F, 0.3F), 0.0F);
        color.a = 0.0F;
        rotation = MathUtils.random(-10F, 10F);
        scale = MathUtils.random(2.0F, 4F) * Settings.scale;
        vX = MathUtils.random(-900F, 900F) * Settings.scale;
        vY = MathUtils.random(0.0F, 500F) * Settings.scale;
        floor = MathUtils.random(100F, 250F) * Settings.scale;
    }

    public void update()
    {
        vY += (GRAVITY / scale) * Gdx.graphics.getDeltaTime();
        x += vX * Gdx.graphics.getDeltaTime() * MathUtils.sinDeg(Gdx.graphics.getDeltaTime());
        y += vY * Gdx.graphics.getDeltaTime();
        if(scale > 0.3F * Settings.scale)
            scale -= Gdx.graphics.getDeltaTime() * 2.0F;
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
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, rotation);
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
        GRAVITY = 180F * Settings.scale;
    }
}
