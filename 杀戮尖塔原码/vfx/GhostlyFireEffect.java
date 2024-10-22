// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GhostlyFireEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect

public class GhostlyFireEffect extends AbstractGameEffect
{

    public GhostlyFireEffect(float x, float y)
    {
        img = getImg();
        this.x = (x + MathUtils.random(-2F, 2.0F) * Settings.scale) - (float)img.packedWidth / 2.0F;
        this.y = (y + MathUtils.random(-2F, 2.0F) * Settings.scale) - (float)img.packedHeight / 2.0F;
        vX = MathUtils.random(-10F, 10F) * Settings.scale;
        vY = MathUtils.random(20F, 150F) * Settings.scale;
        duration = 1.0F;
        color = Color.CHARTREUSE.cpy();
        color.a = 0.0F;
        scale = Settings.scale * MathUtils.random(5F, 6F);
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
        x += vX * Gdx.graphics.getDeltaTime();
        y += vY * Gdx.graphics.getDeltaTime();
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
        if(scale > 0.1F)
            scale -= Gdx.graphics.getDeltaTime() / 4F;
        color.a = duration / 2.0F;
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        sb.setColor(color);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * MathUtils.random(0.95F, 1.05F), scale * MathUtils.random(0.95F, 1.05F), rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private float x;
    private float y;
    private float vX;
    private float vY;
    private static final float DUR = 1F;
}
