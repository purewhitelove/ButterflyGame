// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DamageImpactLineEffect.java

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
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class DamageImpactLineEffect extends AbstractGameEffect
{

    public DamageImpactLineEffect(float x, float y)
    {
        if(MathUtils.randomBoolean())
            img = ImageMaster.STRIKE_LINE;
        else
            img = ImageMaster.STRIKE_LINE_2;
        duration = 0.5F;
        startingDuration = 0.5F;
        this.x = x - (float)img.packedWidth / 2.0F;
        this.y = y - (float)img.packedHeight / 2.0F;
        speed = MathUtils.random(20F * Settings.scale, 40F * Settings.scale);
        speedVector = new Vector2(MathUtils.random(-1F, 1.0F), MathUtils.random(-1F, 1.0F));
        speedVector.nor();
        speedVector.angle();
        rotation = speedVector.angle();
        speedVector.x *= speed;
        speedVector.y *= speed;
        if(MathUtils.randomBoolean())
            color = Color.RED.cpy();
        else
            color = Color.ORANGE.cpy();
    }

    public void update()
    {
        speed -= Gdx.graphics.getDeltaTime() * 60F;
        speedVector.nor();
        speedVector.x *= speed * Gdx.graphics.getDeltaTime() * 60F;
        speedVector.y *= speed * Gdx.graphics.getDeltaTime() * 60F;
        x += speedVector.x;
        y += speedVector.y;
        scale = (Settings.scale * duration) / 0.5F;
        super.update();
    }

    public void render(SpriteBatch sb)
    {
        if(!isDone)
        {
            sb.setColor(color);
            sb.draw(img, x, y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, rotation);
        }
    }

    public void dispose()
    {
    }

    private static final float EFFECT_DUR = 0.5F;
    private float x;
    private float y;
    private Vector2 speedVector;
    private float speed;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
}
