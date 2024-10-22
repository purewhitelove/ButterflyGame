// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NemesisFireParticle.java

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

public class NemesisFireParticle extends AbstractGameEffect
{

    public NemesisFireParticle(float x, float y)
    {
        duration = MathUtils.random(0.5F, 1.0F);
        startingDuration = duration;
        img = getImg();
        this.x = x - (float)(img.packedWidth / 2);
        this.y = y - (float)(img.packedHeight / 2);
        scale = Settings.scale * MathUtils.random(0.25F, 0.5F);
        vY = MathUtils.random(1.0F, 10F) * Settings.scale;
        vY *= vY;
        rotation = MathUtils.random(-20F, 20F);
        color = new Color(0.1F, 0.2F, 0.1F, 0.01F);
    }

    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion getImg()
    {
        switch(MathUtils.random(0, 2))
        {
        case 0: // '\0'
            return ImageMaster.TORCH_FIRE_1;

        case 1: // '\001'
            return ImageMaster.TORCH_FIRE_2;
        }
        return ImageMaster.TORCH_FIRE_3;
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
        color.a = Interpolation.fade.apply(0.0F, 1.0F, duration / startingDuration);
        y += vY * Gdx.graphics.getDeltaTime();
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
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
}
