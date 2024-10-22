// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GrandFinalEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.*;
import java.util.ArrayList;

public class GrandFinalEffect extends AbstractGameEffect
{

    public GrandFinalEffect()
    {
        timer = 0.1F;
        duration = 2.0F;
    }

    public void update()
    {
        if(duration == 2.0F)
            AbstractDungeon.effectsQueue.add(new SpotlightEffect());
        duration -= Gdx.graphics.getDeltaTime();
        timer -= Gdx.graphics.getDeltaTime();
        if(timer < 0.0F)
        {
            timer += 0.1F;
            AbstractDungeon.effectsQueue.add(new PetalEffect());
            AbstractDungeon.effectsQueue.add(new PetalEffect());
        }
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch spritebatch)
    {
    }

    public void dispose()
    {
    }

    private float timer;
}
