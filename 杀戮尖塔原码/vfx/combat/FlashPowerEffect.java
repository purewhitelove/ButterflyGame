// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FlashPowerEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class FlashPowerEffect extends AbstractGameEffect
{

    public FlashPowerEffect(AbstractPower power)
    {
        scale = Settings.scale;
        if(!power.owner.isDeadOrEscaped())
        {
            x = power.owner.hb.cX;
            y = power.owner.hb.cY;
        }
        img = power.img;
        region128 = power.region128;
        if(img == null)
        {
            x -= region128.packedWidth / 2;
            y -= region128.packedHeight / 2;
        }
        duration = 0.7F;
        startingDuration = 0.7F;
        color = Color.WHITE.cpy();
        renderBehind = false;
    }

    public void update()
    {
        super.update();
        scale = Interpolation.exp5In.apply(Settings.scale, Settings.scale * 0.3F, duration / startingDuration);
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        sb.setColor(color);
        if(img != null)
        {
            sb.draw(img, x - 16F, y - 16F, 16F, 16F, 32F, 32F, scale * 12F, scale * 12F, 0.0F, 0, 0, 32, 32, false, false);
            sb.draw(img, x - 16F, y - 16F, 16F, 16F, 32F, 32F, scale * 10F, scale * 10F, 0.0F, 0, 0, 32, 32, false, false);
            sb.draw(img, x - 16F, y - 16F, 16F, 16F, 32F, 32F, scale * 8F, scale * 8F, 0.0F, 0, 0, 32, 32, false, false);
            sb.draw(img, x - 16F, y - 16F, 16F, 16F, 32F, 32F, scale * 7F, scale * 7F, 0.0F, 0, 0, 32, 32, false, false);
        } else
        {
            sb.draw(region128, x, y, (float)region128.packedWidth / 2.0F, (float)region128.packedHeight / 2.0F, region128.packedWidth, region128.packedHeight, scale * 3F, scale * 3F, 0.0F);
        }
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private Texture img;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion region128;
    private static final int W = 32;
    private float scale;
}
