// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdditiveSlashImpactEffect.java

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
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class AdditiveSlashImpactEffect extends AbstractGameEffect
{

    public AdditiveSlashImpactEffect(float x, float y, Color color)
    {
        if(img == null)
            img = ImageMaster.vfxAtlas.findRegion("ui/impactLineThick");
        this.x = x - (float)img.packedWidth / 2.0F;
        this.y = y - (float)img.packedHeight / 2.0F;
        this.color = color;
        duration = 0.4F;
        scale = 0.01F;
        targetScale = MathUtils.random(3F, 5F);
        rotation = MathUtils.random(360F);
    }

    public void update()
    {
        if(duration > 0.2F)
        {
            color.a = Interpolation.fade.apply(0.0F, 0.8F, (duration - 0.2F) * 5F);
            scale = Interpolation.fade.apply(0.01F, targetScale, (duration - 0.2F) * 5F) * Settings.scale;
        } else
        {
            color.a = Interpolation.fade.apply(0.0F, 0.8F, duration * 5F);
            scale = Interpolation.fade.apply(0.01F, targetScale, duration * 5F) * Settings.scale;
        }
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.setBlendFunction(770, 1);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale / 3F, scale, rotation);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale / 6F, scale * 0.5F, rotation + 90F);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private float targetScale;
    private static com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
}
