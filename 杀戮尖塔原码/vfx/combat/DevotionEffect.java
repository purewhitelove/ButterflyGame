// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DevotionEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.scene.TorchParticleXLEffect;
import java.util.ArrayList;

public class DevotionEffect extends AbstractGameEffect
{

    public DevotionEffect()
    {
        count = 0;
    }

    public void update()
    {
        if(count == 0)
            AbstractDungeon.effectsQueue.add(new BorderLongFlashEffect(Color.SKY, true));
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
        {
            count++;
            duration = MathUtils.random(0.1F, 0.2F);
            float x = (float)(Settings.WIDTH * count) / 7F;
            float y = MathUtils.random(AbstractDungeon.floorY - 80F * Settings.scale, AbstractDungeon.floorY + 50F * Settings.scale);
            for(int i = 0; i < 5; i++)
                AbstractDungeon.effectsQueue.add(new TorchParticleXLEffect(x, y, MathUtils.random(1.1F, 1.6F)));

        }
        if(count >= 6)
            isDone = true;
    }

    public void render(SpriteBatch spritebatch)
    {
    }

    public void dispose()
    {
    }

    int count;
}
