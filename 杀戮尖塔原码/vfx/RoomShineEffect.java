// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RoomShineEffect.java

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

public class RoomShineEffect extends AbstractGameEffect
{

    public RoomShineEffect()
    {
        img = ImageMaster.ROOM_SHINE_1;
        effectDuration = MathUtils.random(2.0F, 3F);
        duration = effectDuration;
        startingDuration = effectDuration;
        x = MathUtils.random(50F, 1870F) * Settings.scale - (float)img.packedWidth / 2.0F;
        y = MathUtils.random((float)Settings.HEIGHT * 0.1F, (float)Settings.HEIGHT * 0.85F) - (float)img.packedHeight / 2.0F;
        vY = MathUtils.random(10F, 50F) * Settings.scale;
        alpha = MathUtils.random(0.5F, 1.0F);
        color = new Color(1.0F, 1.0F, MathUtils.random(0.6F, 0.9F), alpha);
        scale = MathUtils.random(0.5F, 1.5F) * Settings.scale;
    }

    public void update()
    {
        if(vY != 0.0F)
        {
            y += vY * Gdx.graphics.getDeltaTime();
            MathUtils.lerp(vY, 0.0F, Gdx.graphics.getDeltaTime() * 10F);
            if(vY < 0.5F)
                vY = 0.0F;
        }
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
        else
        if(duration < effectDuration / 2.0F)
            color.a = Interpolation.exp5In.apply(0.0F, alpha, duration / (effectDuration / 2.0F));
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.setBlendFunction(770, 1);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * MathUtils.random(0.75F, 1.25F), scale * MathUtils.random(0.75F, 1.25F), rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private float effectDuration;
    private float x;
    private float y;
    private float vY;
    private float alpha;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
}
