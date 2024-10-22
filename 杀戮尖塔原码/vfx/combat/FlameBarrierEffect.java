// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FlameBarrierEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.vfx.scene.TorchParticleXLEffect;
import java.util.ArrayList;

public class FlameBarrierEffect extends AbstractGameEffect
{

    public FlameBarrierEffect(float x, float y)
    {
        flashedBorder = true;
        v = new Vector2(0.0F, 0.0F);
        duration = 0.5F;
        this.x = x;
        this.y = y;
        renderBehind = false;
    }

    public void update()
    {
        if(flashedBorder)
        {
            CardCrawlGame.sound.play("ATTACK_FLAME_BARRIER", 0.05F);
            flashedBorder = false;
            AbstractDungeon.effectsQueue.add(new BorderFlashEffect(new Color(1.0F, 1.0F, 0.1F, 1.0F)));
            AbstractDungeon.effectsQueue.add(new BorderLongFlashEffect(new Color(1.0F, 0.4F, 0.1F, 1.0F)));
        }
        float tmp = Interpolation.fade.apply(-209F, 30F, duration / 0.5F) * 0.01745329F;
        v.x = MathUtils.cos(tmp) * X_RADIUS;
        v.y = -MathUtils.sin(tmp) * Y_RADIUS;
        AbstractDungeon.effectsQueue.add(new TorchParticleXLEffect(x + v.x + MathUtils.random(-30F, 30F) * Settings.scale, y + v.y + MathUtils.random(-10F, 10F) * Settings.scale));
        AbstractDungeon.effectsQueue.add(new TorchParticleXLEffect(x + v.x + MathUtils.random(-30F, 30F) * Settings.scale, y + v.y + MathUtils.random(-10F, 10F) * Settings.scale));
        AbstractDungeon.effectsQueue.add(new TorchParticleXLEffect(x + v.x + MathUtils.random(-30F, 30F) * Settings.scale, y + v.y + MathUtils.random(-10F, 10F) * Settings.scale));
        AbstractDungeon.effectsQueue.add(new ExhaustEmberEffect(x + v.x, y + v.y));
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
    private float y;
    private static final float X_RADIUS;
    private static final float Y_RADIUS;
    private boolean flashedBorder;
    private Vector2 v;

    static 
    {
        X_RADIUS = 200F * Settings.scale;
        Y_RADIUS = 250F * Settings.scale;
    }
}
