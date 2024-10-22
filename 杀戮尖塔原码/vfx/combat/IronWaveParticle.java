// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IronWaveParticle.java

package com.megacrit.cardcrawl.vfx.combat;

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
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class IronWaveParticle extends AbstractGameEffect
{

    public IronWaveParticle(float x, float y)
    {
        impactHook = false;
        if(img == null)
            img = ImageMaster.vfxAtlas.findRegion("combat/weightyImpact");
        scale = Settings.scale;
        this.x = x - (float)img.packedWidth / 2.0F;
        this.y = (float)Settings.HEIGHT - (float)img.packedHeight / 2.0F;
        duration = 0.5F;
        targetY = y - 180F * Settings.scale;
        rotation = 0.0F;
        color = new Color(1.0F, 1.0F, 0.1F, 0.0F);
    }

    public void update()
    {
        y = Interpolation.fade.apply(Settings.HEIGHT, targetY, 1.0F - duration / 0.5F);
        scale += Gdx.graphics.getDeltaTime();
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
        else
        if(duration < 0.2F)
        {
            if(!impactHook)
            {
                impactHook = true;
                CardCrawlGame.screenShake.shake(com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity.LOW, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur.SHORT, false);
            }
            color.a = Interpolation.pow2Out.apply(0.0F, 1.0F, duration * 5F);
        } else
        {
            color.a = Interpolation.fade.apply(1.0F, 0.0F, duration / 0.5F);
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        color.g = 1.0F;
        sb.setColor(color);
        sb.draw(img, x, y + 140F * Settings.scale, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth / 2.0F, (float)img.packedHeight * (duration + 0.2F) * 3F, scale * MathUtils.random(0.99F, 1.01F) * 0.5F, scale * MathUtils.random(0.99F, 1.01F) * 2.0F * (duration + 0.8F), rotation);
        color.g = 0.7F;
        sb.setColor(color);
        sb.draw(img, x - 50F * Settings.scale, y + 140F * Settings.scale, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth / 2.0F, (float)img.packedHeight * (duration + 0.2F) * 2.0F, scale * MathUtils.random(0.99F, 1.01F) * 0.6F, scale * MathUtils.random(0.99F, 1.01F) * 2.0F * (duration + 0.8F), rotation);
        color.g = 0.5F;
        sb.setColor(color);
        sb.draw(img, x - 100F * Settings.scale, y + 140F * Settings.scale, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, (float)img.packedHeight * (duration + 0.2F) * 1.0F, scale * MathUtils.random(0.99F, 1.01F) * 0.75F, scale * MathUtils.random(0.99F, 1.01F) * 2.0F * (duration + 0.8F), rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private static final float EFFECT_DUR = 0.5F;
    private float x;
    private float y;
    private float targetY;
    private static com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private boolean impactHook;
}
