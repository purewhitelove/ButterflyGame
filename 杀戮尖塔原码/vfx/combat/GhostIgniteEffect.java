// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GhostIgniteEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.FireBurstParticleEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.combat:
//            LightFlareParticleEffect

public class GhostIgniteEffect extends AbstractGameEffect
{

    public GhostIgniteEffect(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public void update()
    {
        for(int i = 0; i < 25; i++)
        {
            AbstractDungeon.effectsQueue.add(new FireBurstParticleEffect(x, y));
            AbstractDungeon.effectsQueue.add(new LightFlareParticleEffect(x, y, Color.CHARTREUSE));
        }

        isDone = true;
    }

    public void render(SpriteBatch spritebatch)
    {
    }

    public void dispose()
    {
    }

    private static final int COUNT = 25;
    private float x;
    private float y;
}
