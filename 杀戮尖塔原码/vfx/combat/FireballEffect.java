// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FireballEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.*;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.combat:
//            LightFlareParticleEffect, GhostIgniteEffect

public class FireballEffect extends AbstractGameEffect
{

    public FireballEffect(float startX, float startY, float targetX, float targetY)
    {
        vfxTimer = 0.0F;
        startingDuration = 0.5F;
        duration = 0.5F;
        this.startX = startX;
        this.startY = startY;
        this.targetX = targetX + MathUtils.random(-20F, 20F) * Settings.scale;
        this.targetY = targetY + MathUtils.random(-20F, 20F) * Settings.scale;
        x = startX;
        y = startY;
    }

    public void update()
    {
        x = Interpolation.fade.apply(targetX, startX, duration / startingDuration);
        y = Interpolation.fade.apply(targetY, startY, duration / startingDuration);
        vfxTimer -= Gdx.graphics.getDeltaTime();
        if(vfxTimer < 0.0F)
        {
            vfxTimer = 0.016F;
            AbstractDungeon.effectsQueue.add(new LightFlareParticleEffect(x, y, Color.CHARTREUSE));
            AbstractDungeon.effectsQueue.add(new FireBurstParticleEffect(x, y));
        }
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
        {
            isDone = true;
            AbstractDungeon.effectsQueue.add(new GhostIgniteEffect(x, y));
            AbstractDungeon.effectsQueue.add(new GhostlyWeakFireEffect(x, y));
        }
    }

    public void render(SpriteBatch spritebatch)
    {
    }

    public void dispose()
    {
    }

    private static final float FIREBALL_INTERVAL = 0.016F;
    private float x;
    private float y;
    private float startX;
    private float startY;
    private float targetX;
    private float targetY;
    private float vfxTimer;
}
