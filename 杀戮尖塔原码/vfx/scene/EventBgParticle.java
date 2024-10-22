// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EventBgParticle.java

package com.megacrit.cardcrawl.vfx.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class EventBgParticle extends AbstractGameEffect
{

    public EventBgParticle()
    {
        if(img == null)
        {
            img = ImageMaster.vfxAtlas.findRegion("eventBgParticle1");
            img2 = ImageMaster.vfxAtlas.findRegion("eventBgParticle2");
        }
        if(MathUtils.randomBoolean())
            useImg = img;
        else
            useImg = img2;
        duration = 20F;
        startingDuration = duration;
        x = (float)Settings.WIDTH / 2.0F - (float)(useImg.packedWidth / 2);
        y = (float)Settings.HEIGHT / 2.0F - (float)(useImg.packedHeight / 2);
        scale = Settings.scale * MathUtils.random(0.3F, 3F);
        rotation = MathUtils.random(360F);
        offsetX = MathUtils.random(600F, 670F);
        aV = MathUtils.random(0.01F, 7F) + offsetX / 300F;
        offsetX *= Settings.scale;
        scale += offsetX / 900F;
        float g = MathUtils.random(0.05F, 0.1F);
        color = new Color(0.0F, g, g, 0.1F);
        renderBehind = true;
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        rotation += Gdx.graphics.getDeltaTime() * aV;
        if(duration < 0.0F)
            isDone = true;
        if(duration > 16F)
            color.a = Interpolation.fade.apply(0.3F, 0.0F, (duration - 16F) / 4F);
        else
        if(duration < 4F)
            color.a = Interpolation.fade.apply(0.0F, 0.3F, duration / 4F);
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.draw(useImg, x - offsetX, y, (float)useImg.packedWidth / 2.0F + offsetX, (float)useImg.packedHeight / 2.0F, useImg.packedWidth, useImg.packedHeight, scale, scale, rotation);
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private float aV;
    private float offsetX;
    private static com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private static com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img2;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion useImg;
}
