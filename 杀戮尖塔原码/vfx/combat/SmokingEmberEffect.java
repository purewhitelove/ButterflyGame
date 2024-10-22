// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SmokingEmberEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.FastSmokeParticle;
import java.util.ArrayList;

public class SmokingEmberEffect extends AbstractGameEffect
{

    public SmokingEmberEffect(float x, float y)
    {
        smokeTimer = 0.0F;
        this.x = x;
        this.y = y;
        vX = MathUtils.random(-600F, 600F) * Settings.scale;
        vY = MathUtils.random(-200F, 600F) * Settings.scale;
        gravity = 800F * Settings.scale;
        scale = MathUtils.random(0.2F, 0.4F) * Settings.scale;
        duration = MathUtils.random(0.3F, 0.6F);
    }

    public void update()
    {
        x += vX * Gdx.graphics.getDeltaTime();
        y += vY * Gdx.graphics.getDeltaTime();
        vY -= gravity * Gdx.graphics.getDeltaTime();
        smokeTimer -= Gdx.graphics.getDeltaTime();
        if(smokeTimer < 0.0F)
        {
            smokeTimer = 0.01F;
            AbstractDungeon.effectsQueue.add(new FastSmokeParticle(x, y));
        }
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch spritebatch)
    {
    }

    public void dispose()
    {
    }

    private float x;
    private float vX;
    private float y;
    private float vY;
    private float gravity;
    private static final float INTERVAL = 0.01F;
    private float smokeTimer;
}
