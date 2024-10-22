// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CleaveEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class CleaveEffect extends AbstractGameEffect
{

    public CleaveEffect(boolean usedByMonster)
    {
        fadeInTimer = 0.05F;
        fadeOutTimer = 0.4F;
        img = ImageMaster.vfxAtlas.findRegion("combat/cleave");
        color = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        x = (float)Settings.WIDTH * 0.3F - (float)img.packedWidth / 2.0F;
        y = (AbstractDungeon.floorY + 100F * Settings.scale) - (float)img.packedHeight / 2.0F;
        vX = 100F * Settings.scale;
        stallTimer = MathUtils.random(0.0F, 0.2F);
        scale = 1.2F * Settings.scale;
        rotation = MathUtils.random(-5F, 1.0F);
    }

    public CleaveEffect()
    {
        fadeInTimer = 0.05F;
        fadeOutTimer = 0.4F;
        img = ImageMaster.vfxAtlas.findRegion("combat/cleave");
        color = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        x = (float)Settings.WIDTH * 0.7F - (float)img.packedWidth / 2.0F;
        y = (AbstractDungeon.floorY + 100F * Settings.scale) - (float)img.packedHeight / 2.0F;
        vX = 100F * Settings.scale;
        stallTimer = MathUtils.random(0.0F, 0.2F);
        scale = 1.2F * Settings.scale;
        rotation = MathUtils.random(-5F, 1.0F);
    }

    public void update()
    {
        if(stallTimer > 0.0F)
        {
            stallTimer -= Gdx.graphics.getDeltaTime();
            return;
        }
        x += vX * Gdx.graphics.getDeltaTime();
        rotation += MathUtils.random(-0.5F, 0.5F);
        scale += 0.005F * Settings.scale;
        if(fadeInTimer != 0.0F)
        {
            fadeInTimer -= Gdx.graphics.getDeltaTime();
            if(fadeInTimer < 0.0F)
                fadeInTimer = 0.0F;
            color.a = Interpolation.fade.apply(1.0F, 0.0F, fadeInTimer / 0.05F);
        } else
        if(fadeOutTimer != 0.0F)
        {
            fadeOutTimer -= Gdx.graphics.getDeltaTime();
            if(fadeOutTimer < 0.0F)
                fadeOutTimer = 0.0F;
            color.a = Interpolation.pow2.apply(0.0F, 1.0F, fadeOutTimer / 0.4F);
        } else
        {
            isDone = true;
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, rotation);
        sb.setBlendFunction(770, 1);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private float vX;
    private static final float FADE_IN_TIME = 0.05F;
    private static final float FADE_OUT_TIME = 0.4F;
    private float fadeInTimer;
    private float fadeOutTimer;
    private float stallTimer;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
}
