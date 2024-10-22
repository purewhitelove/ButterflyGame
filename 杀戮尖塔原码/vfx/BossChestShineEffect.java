// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BossChestShineEffect.java

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

public class BossChestShineEffect extends AbstractGameEffect
{

    public BossChestShineEffect(float x, float y)
    {
        img = ImageMaster.ROOM_SHINE_2;
        effectDuration = MathUtils.random(1.0F, 2.0F);
        duration = effectDuration;
        startingDuration = effectDuration;
        this.x = x;
        this.y = y;
        vY = MathUtils.random(10F, 50F) * Settings.scale;
        alpha = MathUtils.random(0.7F, 1.0F);
        color = new Color(1.0F, MathUtils.random(0.4F, 0.9F), 1.0F, alpha);
        scale = 0.01F;
        targetScale = MathUtils.random(0.7F, 1.3F);
        rotation = MathUtils.random(-3F, 3F);
    }

    public BossChestShineEffect()
    {
        img = ImageMaster.ROOM_SHINE_2;
        effectDuration = MathUtils.random(1.0F, 3F);
        duration = effectDuration;
        startingDuration = effectDuration;
        x = ((float)Settings.WIDTH / 2.0F + MathUtils.random(-450F, 450F) * Settings.scale) - (float)img.packedWidth / 2.0F;
        y = ((float)Settings.HEIGHT / 2.0F + MathUtils.random(-200F, 250F) * Settings.scale) - (float)img.packedHeight / 2.0F;
        vY = MathUtils.random(10F, 50F) * Settings.scale;
        alpha = MathUtils.random(0.7F, 1.0F);
        color = new Color(1.0F, MathUtils.random(0.4F, 0.9F), 1.0F, alpha);
        scale = 0.01F;
        targetScale = MathUtils.random(0.5F, 1.3F);
        rotation = MathUtils.random(-3F, 3F);
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
        float t = (effectDuration - duration) * 2.0F;
        if(t > 1.0F)
            t = 1.0F;
        float tmp = Interpolation.bounceOut.apply(0.01F, targetScale, t);
        scale = tmp * tmp * Settings.scale;
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
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * MathUtils.random(0.9F, 1.1F), scale * MathUtils.random(0.7F, 1.3F), rotation);
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
    private float targetScale;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
}
