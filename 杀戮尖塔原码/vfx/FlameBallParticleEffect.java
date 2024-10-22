// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FlameBallParticleEffect.java

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
import com.megacrit.cardcrawl.helpers.ImageMaster;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect

public class FlameBallParticleEffect extends AbstractGameEffect
{

    public FlameBallParticleEffect(float x, float y, int intensity)
    {
        int roll = MathUtils.random(0, 2);
        if(roll == 0)
            img = ImageMaster.SMOKE_1;
        else
        if(roll == 1)
            img = ImageMaster.SMOKE_2;
        else
            img = ImageMaster.SMOKE_3;
        scale = (1.0F + (float)intensity * 0.1F) * Settings.scale;
        duration = MathUtils.random(1.0F, 1.6F);
        this.x = x - (float)(img.packedWidth / 2);
        this.y = (y - (float)(img.packedHeight / 2)) + (float)intensity * 3F * Settings.scale;
        color = new Color(MathUtils.random(0.8F, 1.0F), MathUtils.random(0.2F, 0.8F), MathUtils.random(0.0F, 0.4F), 0.0F);
        color.a = 0.0F;
        rotation = MathUtils.random(-5F, 5F);
        vY = MathUtils.random(10F, 30F) * Settings.scale;
        renderBehind = MathUtils.randomBoolean();
        startingDuration = 1.0F;
    }

    public void update()
    {
        y += vY * Gdx.graphics.getDeltaTime();
        if(duration > startingDuration / 2.0F)
            color.a = Interpolation.fade.apply(0.7F, 0.0F, duration - startingDuration / 2.0F) * Settings.scale;
        else
            color.a = Interpolation.fade.apply(0.0F, 0.7F, duration / (startingDuration / 2.0F)) * Settings.scale;
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        sb.setColor(color);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F + 20F * Settings.scale, img.packedWidth, img.packedHeight, scale, scale, rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private float x;
    private float y;
    private float vY;
}
