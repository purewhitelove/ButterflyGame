// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ShineLinesEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.helpers.ImageMaster;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect

public class ShineLinesEffect extends AbstractGameEffect
{

    public ShineLinesEffect(float x, float y)
    {
        img = ImageMaster.GRAB_COIN;
        duration = 0.25F;
        startingDuration = 0.25F;
        this.x = x - (float)img.packedWidth / 2.0F;
        this.y = y - (float)img.packedHeight / 2.0F;
        rotation = MathUtils.random(0.0F, 360F);
        color = Color.WHITE.cpy();
        scale = 0.0F;
    }

    public void update()
    {
        super.update();
        scale += Gdx.graphics.getDeltaTime() * 8F;
    }

    public void render(SpriteBatch sb)
    {
        if(!isDone)
        {
            sb.setColor(color);
            sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, rotation);
        }
    }

    public void dispose()
    {
    }

    private static final float EFFECT_DUR = 0.25F;
    private float x;
    private float y;
    private static final float SCALE_INCREASE_RATE = 8F;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
}
