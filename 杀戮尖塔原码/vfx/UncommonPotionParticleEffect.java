// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UncommonPotionParticleEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect

public class UncommonPotionParticleEffect extends AbstractGameEffect
{

    public UncommonPotionParticleEffect(float x, float y)
    {
        this(null);
        this.x = x;
        this.y = y;
    }

    public UncommonPotionParticleEffect(Hitbox hb)
    {
        this.hb = null;
        this.hb = hb;
        img = ImageMaster.GLOW_SPARK_2;
        duration = MathUtils.random(0.8F, 1.0F);
        scale = MathUtils.random(0.4F, 0.7F) * Settings.scale;
        dur_div2 = duration / 2.0F;
        color = new Color(0.6F, 0.7F, MathUtils.random(0.8F, 1.0F), 0.0F);
        oX = MathUtils.random(-25F, 25F) * Settings.scale;
        oY = MathUtils.random(-25F, 25F) * Settings.scale;
        oX -= (float)img.packedWidth / 2.0F;
        oY -= (float)img.packedHeight / 2.0F;
        vX = MathUtils.random(-5F, 5F) * Settings.scale;
        vY = MathUtils.random(-7F, 7F) * Settings.scale;
        renderBehind = MathUtils.randomBoolean(0.2F + (scale - 0.5F));
        rotation = MathUtils.random(-8F, 8F);
    }

    public void update()
    {
        if(duration > dur_div2)
            color.a = Interpolation.pow3In.apply(0.5F, 0.0F, (duration - dur_div2) / dur_div2);
        else
            color.a = Interpolation.pow3In.apply(0.0F, 0.5F, duration / dur_div2);
        oX += vX * Gdx.graphics.getDeltaTime();
        oY += vY * Gdx.graphics.getDeltaTime();
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.setBlendFunction(770, 1);
        if(hb != null)
            sb.draw(img, hb.cX + oX, hb.cY + oY, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * MathUtils.random(0.8F, 1.2F), scale * MathUtils.random(0.8F, 1.2F), rotation);
        else
            sb.draw(img, x + oX, y + oY, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * MathUtils.random(0.8F, 1.2F), scale * MathUtils.random(0.8F, 1.2F), rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private float oX;
    private float oY;
    private float vX;
    private float vY;
    private float dur_div2;
    private Hitbox hb;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
}
