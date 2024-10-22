// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BloodShotEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.combat:
//            BloodShotParticleEffect

public class BloodShotEffect extends AbstractGameEffect
{

    public BloodShotEffect(float sX, float sY, float tX, float tY, int count)
    {
        this.count = 0;
        timer = 0.0F;
        this.sX = sX - 20F * Settings.scale;
        this.sY = sY + 80F * Settings.scale;
        this.tX = tX;
        this.tY = tY;
        this.count = count;
    }

    public void update()
    {
        timer -= Gdx.graphics.getDeltaTime();
        if(timer < 0.0F)
        {
            timer += MathUtils.random(0.05F, 0.15F);
            AbstractDungeon.effectsQueue.add(new BloodShotParticleEffect(sX, sY, tX, tY));
            count--;
            if(count == 0)
                isDone = true;
        }
    }

    public void render(SpriteBatch spritebatch)
    {
    }

    public void dispose()
    {
    }

    private float sX;
    private float sY;
    private float tX;
    private float tY;
    private int count;
    private float timer;
}
