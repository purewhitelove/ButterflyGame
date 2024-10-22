// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DeathScreenFloatyEffect.java

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

public class DeathScreenFloatyEffect extends AbstractGameEffect
{

    public DeathScreenFloatyEffect()
    {
        duration = MathUtils.random(3F, 12F);
        startingDuration = duration;
        int roll = MathUtils.random(5);
        if(roll == 0)
            img = ImageMaster.DEATH_VFX_1;
        else
        if(roll == 1)
            img = ImageMaster.DEATH_VFX_2;
        else
        if(roll == 2)
            img = ImageMaster.DEATH_VFX_3;
        else
        if(roll == 3)
            img = ImageMaster.DEATH_VFX_4;
        else
        if(roll == 4)
            img = ImageMaster.DEATH_VFX_5;
        else
            img = ImageMaster.DEATH_VFX_6;
        x = MathUtils.random(0.0F, Settings.WIDTH) - (float)img.packedWidth / 2.0F;
        y = MathUtils.random(0.0F, Settings.HEIGHT) - (float)img.packedHeight / 2.0F;
        vX = MathUtils.random(-20F, 20F) * Settings.scale * scale;
        vY = MathUtils.random(-60F, 60F) * Settings.scale * scale;
        vX2 = MathUtils.random(-20F, 20F) * Settings.scale * scale;
        vY2 = MathUtils.random(-60F, 60F) * Settings.scale * scale;
        aV = MathUtils.random(-50F, 50F);
        float tmp = MathUtils.random(0.2F, 0.4F);
        color = new Color();
        color.r = tmp + MathUtils.random(0.0F, 0.2F);
        color.g = tmp;
        color.b = tmp + MathUtils.random(0.0F, 0.2F);
        renderBehind = MathUtils.randomBoolean(0.8F);
        scale = MathUtils.random(12F, 20F) * Settings.scale;
    }

    public void update()
    {
        x += vX * Gdx.graphics.getDeltaTime();
        y += vY * Gdx.graphics.getDeltaTime();
        vX += vX2 * Gdx.graphics.getDeltaTime();
        vY += vY2 * Gdx.graphics.getDeltaTime();
        rotation += aV * Gdx.graphics.getDeltaTime();
        if(startingDuration - duration < 1.5F)
            color.a = Interpolation.fade.apply(0.0F, 0.3F, (startingDuration - duration) / 1.5F);
        else
        if(duration < 1.5F)
            color.a = Interpolation.fade.apply(0.3F, 0.0F, 1.0F - duration / 1.5F);
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

    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private float x;
    private float y;
    private float vX;
    private float vY;
    private float vX2;
    private float vY2;
    private float aV;
}
