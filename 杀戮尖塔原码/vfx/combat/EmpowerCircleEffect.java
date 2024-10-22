// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EmpowerCircleEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class EmpowerCircleEffect extends AbstractGameEffect
{

    public EmpowerCircleEffect(float x, float y)
    {
        duration = MathUtils.random(0.8F, 3.2F);
        startingDuration = duration;
        if(MathUtils.randomBoolean())
            img = ImageMaster.POWER_UP_1;
        else
            img = ImageMaster.POWER_UP_2;
        this.x = x - (float)img.packedWidth / 2.0F;
        this.y = y - (float)img.packedHeight / 2.0F;
        vX = MathUtils.random(-6000F * Settings.scale, 6000F * Settings.scale);
        vY = MathUtils.random(-6000F * Settings.scale, 6000F * Settings.scale);
        rotation = (new Vector2(vX, vY)).angle();
        if(MathUtils.randomBoolean())
            color = Settings.CREAM_COLOR.cpy();
        else
            color = Color.SLATE.cpy();
        renderBehind = true;
    }

    public void update()
    {
        x += vX * Gdx.graphics.getDeltaTime();
        y += vY * Gdx.graphics.getDeltaTime();
        vX = MathHelper.fadeLerpSnap(vX, 0.0F);
        vY = MathHelper.fadeLerpSnap(vY, 0.0F);
        scale = (Settings.scale * duration) / startingDuration;
        super.update();
    }

    public void render(SpriteBatch sb)
    {
        if(!isDone)
        {
            sb.setColor(color);
            sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale * MathUtils.random(0.9F, 1.1F), scale * MathUtils.random(0.9F, 1.1F), rotation);
        }
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private float vX;
    private float vY;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
}
