// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UpgradeHammerImprintEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect

public class UpgradeHammerImprintEffect extends AbstractGameEffect
{

    public UpgradeHammerImprintEffect(float x, float y)
    {
        img = ImageMaster.UPGRADE_HAMMER_IMPACT;
        shineColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        this.x = x - (float)(img.packedWidth / 2);
        this.y = y - (float)(img.packedHeight / 2);
        color = Color.WHITE.cpy();
        color.a = 0.7F;
        duration = 0.7F;
        scale = Settings.scale / MathUtils.random(1.0F, 1.5F);
        rotation = MathUtils.random(0.0F, 360F);
        hammerGlowScale = 1.0F - duration;
        hammerGlowScale *= hammerGlowScale;
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
        color.a = duration;
        hammerGlowScale = 1.7F - duration;
        hammerGlowScale *= hammerGlowScale * hammerGlowScale;
        scale += Gdx.graphics.getDeltaTime() / 20F;
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        sb.setColor(color);
        sb.draw(img, x + MathUtils.random(-2F, 2.0F) * Settings.scale, y + MathUtils.random(-2F, 2.0F) * Settings.scale, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, rotation);
        shineColor.a = color.a / 10F;
        sb.setColor(shineColor);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, hammerGlowScale, hammerGlowScale, rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private static final float DUR = 0.7F;
    private float x;
    private float y;
    private float hammerGlowScale;
    private Color shineColor;
}
