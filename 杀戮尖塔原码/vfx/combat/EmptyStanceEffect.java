// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EmptyStanceEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.combat:
//            EmptyStanceParticleEffect

public class EmptyStanceEffect extends AbstractGameEffect
{

    public EmptyStanceEffect(float x, float y)
    {
        numParticles = 10;
        duration = 0.0F;
        this.x = x;
        this.y = y;
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
        {
            if(numParticles == 10)
                AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.SKY, true));
            AbstractDungeon.effectsQueue.add(new EmptyStanceParticleEffect(x, y));
            AbstractDungeon.effectsQueue.add(new EmptyStanceParticleEffect(x, y));
            AbstractDungeon.effectsQueue.add(new EmptyStanceParticleEffect(x, y));
            numParticles--;
            if(numParticles <= 0)
                isDone = true;
        }
    }

    public void render(SpriteBatch spritebatch)
    {
    }

    public void dispose()
    {
    }

    private int numParticles;
    private float x;
    private float y;
}
