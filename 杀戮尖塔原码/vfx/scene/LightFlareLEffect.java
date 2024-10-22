// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LightFlareLEffect.java

package com.megacrit.cardcrawl.vfx.scene;

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
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class LightFlareLEffect extends AbstractGameEffect
{

    public LightFlareLEffect(float x, float y)
    {
        if(imgs[0] == null)
        {
            imgs[0] = ImageMaster.vfxAtlas.findRegion("env/lightFlare1");
            imgs[1] = ImageMaster.vfxAtlas.findRegion("env/lightFlare2");
        }
        duration = MathUtils.random(2.0F, 3F);
        startingDuration = duration;
        img = imgs[MathUtils.random(imgs.length - 1)];
        this.x = x - (float)(img.packedWidth / 2);
        this.y = y - (float)(img.packedHeight / 2);
        scale = Settings.scale * MathUtils.random(6F, 7F);
        rotation = MathUtils.random(360F);
        if(!renderGreen)
            color = new Color(MathUtils.random(0.6F, 1.0F), MathUtils.random(0.4F, 0.7F), MathUtils.random(0.2F, 0.3F), 0.01F);
        else
            color = new Color(MathUtils.random(0.1F, 0.3F), MathUtils.random(0.5F, 0.9F), MathUtils.random(0.1F, 0.3F), 0.01F);
        renderBehind = true;
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
        if(startingDuration - duration < 1.0F)
            color.a = Interpolation.fade.apply(0.2F, 0.0F, duration / startingDuration);
        else
            color.a = Interpolation.fade.apply(0.0F, 0.2F, duration / startingDuration);
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

    private float x;
    private float y;
    public static com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion imgs[] = new com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion[2];
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    public static boolean renderGreen = false;

}
