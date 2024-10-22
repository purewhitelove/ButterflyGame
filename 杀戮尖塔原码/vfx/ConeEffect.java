// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ConeEffect.java

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

public class ConeEffect extends AbstractGameEffect
{

    public ConeEffect()
    {
        switch(MathUtils.random(1, 6))
        {
        case 1: // '\001'
            img = ImageMaster.CONE_3;
            break;

        default:
            if(MathUtils.randomBoolean())
                img = ImageMaster.CONE_1;
            else
                img = ImageMaster.CONE_2;
            break;
        }
        x = (float)Settings.WIDTH / 2.0F;
        y = (float)Settings.HEIGHT / 2.0F - (float)img.packedHeight / 2.0F;
        duration = MathUtils.random(2.0F, 5F);
        startingDuration = duration;
        rotation = MathUtils.random(360F);
        aV = MathUtils.random(-10F, 10F);
        aV *= 2.0F;
        color = new Color(1.0F, MathUtils.random(0.7F, 0.8F), 0.2F, 0.0F);
        scale = Settings.scale;
    }

    public void update()
    {
        rotation += aV * Gdx.graphics.getDeltaTime();
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
        else
        if(startingDuration - duration < 1.0F)
            color.a = (startingDuration - duration) / 3F;
        else
        if(duration < 1.0F)
            color.a = Interpolation.fade.apply(0.0F, 0.33F, duration);
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.draw(img, x, y, 0.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * 1.1F, scale * 1.1F, rotation);
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private float aV;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
}
