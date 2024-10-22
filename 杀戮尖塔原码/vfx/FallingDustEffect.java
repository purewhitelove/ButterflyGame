// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FallingDustEffect.java

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

public class FallingDustEffect extends AbstractGameEffect
{

    public FallingDustEffect(float x, float y)
    {
        this.x = x + MathUtils.random(-6F, 6F) * Settings.scale;
        this.y = y;
        float randY = MathUtils.random(-10F, 10F) * Settings.scale;
        y += randY;
        renderBehind = randY < 0.0F;
        vY = MathUtils.random(0.0F, 140F) * Settings.scale;
        if(MathUtils.randomBoolean())
            vX = MathUtils.random(-20F, 20F) * Settings.scale;
        else
            vX = 0.0F;
        vYAccel = MathUtils.random(4F, 9F) * Settings.scale;
        duration = MathUtils.random(3F, 7F);
        img = setImg();
        scale = Settings.scale * MathUtils.random(0.5F, 0.7F);
        rotation = MathUtils.random(0.0F, 360F);
        float c = MathUtils.random(0.1F, 0.3F);
        color = new Color(c + 0.1F, c, c, 0.0F);
        color.a = MathUtils.random(0.8F, 0.9F);
        startingAlpha = color.a;
        aV = MathUtils.random(-1F, 1.0F);
    }

    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion setImg()
    {
        switch(MathUtils.random(0, 5))
        {
        case 0: // '\0'
            return ImageMaster.DUST_1;

        case 1: // '\001'
            return ImageMaster.DUST_2;

        case 2: // '\002'
            return ImageMaster.DUST_3;

        case 3: // '\003'
            return ImageMaster.DUST_4;

        case 4: // '\004'
            return ImageMaster.DUST_5;
        }
        return ImageMaster.DUST_6;
    }

    public void update()
    {
        rotation += aV;
        y -= vY * Gdx.graphics.getDeltaTime();
        x += vX * Gdx.graphics.getDeltaTime();
        vY += vYAccel * Gdx.graphics.getDeltaTime();
        vX *= 0.99F;
        if(duration < 3F)
            color.a = Interpolation.fade.apply(startingAlpha, 0.0F, 1.0F - duration / 3F);
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.draw(img, x, y, img.offsetX, img.offsetY, img.packedWidth, img.packedHeight, scale, scale, rotation);
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private float vY;
    private float vX;
    private float vYAccel;
    private float aV;
    private float startingAlpha;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
}
