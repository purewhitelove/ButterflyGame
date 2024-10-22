// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RefreshEnergyEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect

public class RefreshEnergyEffect extends AbstractGameEffect
{

    public RefreshEnergyEffect()
    {
        scale = Settings.scale / 1.2F;
        color = new Color(1.0F, 1.0F, 1.0F, 1.0F);
        img = ImageMaster.WHITE_RING;
        x = 198F * Settings.scale - (float)img.packedWidth / 2.0F;
        y = 190F * Settings.scale - (float)img.packedHeight / 2.0F;
        duration = 0.4F;
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        scale *= 1.0F + Gdx.graphics.getDeltaTime() * 2.5F;
        color.a = Interpolation.fade.apply(0.0F, 0.75F, duration / 0.4F);
        if(color.a < 0.0F)
            color.a = 0.0F;
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.setBlendFunction(770, 1);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * 1.5F, scale * 1.5F, rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private static final float EFFECT_DUR = 0.4F;
    private float scale;
    private Color color;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private float x;
    private float y;
}
