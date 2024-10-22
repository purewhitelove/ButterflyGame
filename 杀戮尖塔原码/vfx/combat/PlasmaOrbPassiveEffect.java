// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PlasmaOrbPassiveEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class PlasmaOrbPassiveEffect extends AbstractGameEffect
{

    public PlasmaOrbPassiveEffect(float x, float y)
    {
        img = ImageMaster.GLOW_SPARK_2;
        effectDuration = 1.0F;
        duration = effectDuration;
        startingDuration = effectDuration;
        this.x = x + MathUtils.random(-30F, 30F) * Settings.scale;
        this.y = y + MathUtils.random(-30F, 30F) * Settings.scale;
        sX = this.x;
        sY = this.y;
        tX = x;
        tY = y;
        int tmp = MathUtils.random(2);
        if(tmp == 0)
            color = Settings.LIGHT_YELLOW_COLOR.cpy();
        else
        if(tmp == 1)
            color = Color.CYAN.cpy();
        else
            color = Color.SALMON.cpy();
        scale = MathUtils.random(0.3F, 1.2F) * Settings.scale;
        renderBehind = true;
    }

    public void update()
    {
        x = Interpolation.swingOut.apply(tX, sX, duration);
        y = Interpolation.swingOut.apply(tY, sY, duration);
        super.update();
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.setBlendFunction(770, 1);
        sb.draw(img, x - (float)img.packedWidth / 2.0F, y - (float)img.packedWidth / 2.0F, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * MathUtils.random(0.7F, 1.4F), scale * MathUtils.random(0.7F, 1.4F), rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private float effectDuration;
    private float x;
    private float y;
    private float sX;
    private float sY;
    private float tX;
    private float tY;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
}
