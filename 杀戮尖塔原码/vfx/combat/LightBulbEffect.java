// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LightBulbEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class LightBulbEffect extends AbstractGameEffect
{

    public LightBulbEffect(Hitbox hb)
    {
        img = AbstractPower.atlas.findRegion("128/curiosity");
        x = hb.cX - (float)img.packedHeight / 2.0F;
        y = (hb.cY + hb.height / 2.0F) - (float)img.packedHeight / 2.0F;
        startY = y - 50F * Settings.scale;
        dstY = y + 70F * Settings.scale;
        startingDuration = 0.8F;
        duration = startingDuration;
        color = Color.WHITE.cpy();
        color.a = 0.0F;
    }

    public void update()
    {
        y = Interpolation.swingIn.apply(dstY, startY, duration * (1.0F / startingDuration));
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < startingDuration * 0.8F)
            color.a = Interpolation.fade.apply(0.0F, 1.0F, duration * (1.0F / startingDuration / 0.5F));
        else
            color.a = MathHelper.fadeLerpSnap(color.a, 0.0F);
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        sb.setColor(color);
        sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private float startY;
    private float dstY;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
}
