// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CampfireBurningEffect.java

package com.megacrit.cardcrawl.vfx.campfire;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class CampfireBurningEffect extends AbstractGameEffect
{

    public CampfireBurningEffect()
    {
        flipX = MathUtils.randomBoolean();
        delayTimer = MathUtils.random(0.1F);
        setImg();
        startingDuration = 1.75F;
        duration = startingDuration;
        x = MathUtils.random(1200F, 1770F) * Settings.xScale - (float)img.packedWidth / 2.0F;
        y = (float)Settings.HEIGHT / 2.0F - 200F * Settings.yScale - (float)img.packedHeight / 2.0F;
        vX = MathUtils.random(-70F, 70F) * Settings.xScale;
        vY = 0.0F;
        vY2 = MathUtils.random(250F, 450F) * Settings.scale;
        vY2 -= Math.abs(x - 1485F * Settings.scale) / 2.0F;
        if(CardCrawlGame.dungeon instanceof TheBeyond)
        {
            color = new Color(0.0F, 1.0F, 1.0F, 0.0F);
            color.g -= MathUtils.random(0.4F);
            color.b -= MathUtils.random(0.4F);
        } else
        if(CardCrawlGame.dungeon instanceof TheCity)
        {
            color = new Color(1.0F, 1.0F, 1.0F, 0.0F);
            color.r -= MathUtils.random(0.5F);
            color.b -= color.r - MathUtils.random(0.0F, 0.2F);
        } else
        {
            color = new Color(1.0F, 1.0F, 1.0F, 0.0F);
            color.g -= MathUtils.random(0.5F);
            color.b -= color.g - MathUtils.random(0.0F, 0.2F);
        }
        rotation = MathUtils.random(-10F, 10F);
        scale = MathUtils.random(3F, 4F);
        brightness = MathUtils.random(0.2F, 0.6F);
    }

    public void update()
    {
        if(delayTimer > 0.0F)
        {
            delayTimer -= Gdx.graphics.getDeltaTime();
            return;
        }
        x += vX * Gdx.graphics.getDeltaTime();
        y += vY * Gdx.graphics.getDeltaTime();
        vY = MathHelper.slowColorLerpSnap(vY, vY2);
        scale *= 1.0F - Gdx.graphics.getDeltaTime() / 10F;
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
        else
        if(startingDuration - duration < 0.75F)
            color.a = Interpolation.fade.apply(0.0F, brightness, (startingDuration - duration) / 0.75F);
        else
        if(duration < 1.0F)
            color.a = Interpolation.fade.apply(0.0F, brightness, duration / 1.0F);
    }

    private void setImg()
    {
        int roll = MathUtils.random(2);
        if(roll == 0)
            img = ImageMaster.FLAME_1;
        else
        if(roll == 1)
            img = ImageMaster.FLAME_2;
        else
            img = ImageMaster.FLAME_3;
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        sb.setColor(color);
        if(flipX && !img.isFlipX())
            img.flip(true, false);
        else
        if(!flipX && img.isFlipX())
            img.flip(true, false);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * Settings.scale, scale * Settings.scale, rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private float brightness;
    private float x;
    private float y;
    private float vX;
    private float vY2;
    private float vY;
    private float startingDuration;
    private boolean flipX;
    private float delayTimer;
}
