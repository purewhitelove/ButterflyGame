// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AwakenedWingParticle.java

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

public class AwakenedWingParticle extends AbstractGameEffect
{

    public AwakenedWingParticle()
    {
        duration = 2.0F;
        startingDuration = duration;
        img = ImageMaster.THICK_3D_LINE_2;
        scale = 0.01F;
        rotation = MathUtils.random(25F, 85F);
        renderBehind = MathUtils.randomBoolean(0.2F);
        if(renderBehind)
            tScale = MathUtils.random(0.8F, 1.2F);
        color = new Color(0.3F, 0.3F, MathUtils.random(0.3F, 0.35F), MathUtils.random(0.5F, 0.9F));
        int roll = MathUtils.random(0, 2);
        if(roll == 0)
        {
            x = MathUtils.random(-340F, -170F) * Settings.scale;
            y = MathUtils.random(-20F, 20F) * Settings.scale;
            tScale = MathUtils.random(0.4F, 0.5F);
        } else
        if(roll == 1)
        {
            x = MathUtils.random(-220F, -20F) * Settings.scale;
            y = MathUtils.random(-40F, -10F) * Settings.scale;
            tScale = MathUtils.random(0.4F, 0.5F);
        } else
        {
            x = MathUtils.random(-270F, -60F) * Settings.scale;
            y = MathUtils.random(-30F, -0F) * Settings.scale;
            tScale = MathUtils.random(0.4F, 0.7F);
        }
        x += 155F * Settings.scale;
        y += 30F * Settings.scale;
        x -= img.packedWidth / 2;
        y -= img.packedHeight / 2;
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
        if(duration > 1.0F)
            scale = Interpolation.bounceIn.apply(tScale, 0.01F, duration - 1.0F) * Settings.scale;
        if(duration < 0.2F)
            color.a = Interpolation.fade.apply(0.0F, 0.5F, duration * 5F);
    }

    public void render(SpriteBatch spritebatch)
    {
    }

    public void render(SpriteBatch sb, float x, float y)
    {
        float derp = MathUtils.random(3F, 5F);
        sb.setColor(new Color(0.4F, 1.0F, 1.0F, color.a / 2.0F));
        sb.setBlendFunction(770, 1);
        sb.draw(img, this.x + x, this.y + y, (float)img.packedWidth * 0.08F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * MathUtils.random(1.1F, 1.25F), scale, rotation + derp);
        sb.setBlendFunction(770, 771);
        sb.setColor(color);
        sb.draw(img, this.x + x, this.y + y, (float)img.packedWidth * 0.08F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, rotation + derp);
        sb.setColor(new Color(0.0F, 0.0F, 0.0F, color.a / 5F));
        sb.draw(img, this.x + x, this.y + y, (float)img.packedWidth * 0.08F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * 0.7F, scale * 0.7F, (rotation + derp) - 40F);
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private float tScale;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
}
