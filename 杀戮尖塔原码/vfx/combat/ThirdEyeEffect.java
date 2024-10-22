// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ThirdEyeEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.combat:
//            ThirdEyeParticleEffect

public class ThirdEyeEffect extends AbstractGameEffect
{

    public ThirdEyeEffect(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public void update()
    {
        AbstractDungeon.effectsQueue.add(new ThirdEyeParticleEffect(x, y, 800F, 0.0F));
        AbstractDungeon.effectsQueue.add(new ThirdEyeParticleEffect(x, y, -800F, 0.0F));
        AbstractDungeon.effectsQueue.add(new ThirdEyeParticleEffect(x, y, 0.0F, 500F));
        AbstractDungeon.effectsQueue.add(new ThirdEyeParticleEffect(x, y, 0.0F, -500F));
        AbstractDungeon.effectsQueue.add(new ThirdEyeParticleEffect(x, y, 600F, 0.0F));
        AbstractDungeon.effectsQueue.add(new ThirdEyeParticleEffect(x, y, -600F, 0.0F));
        AbstractDungeon.effectsQueue.add(new ThirdEyeParticleEffect(x, y, 0.0F, 400F));
        AbstractDungeon.effectsQueue.add(new ThirdEyeParticleEffect(x, y, 0.0F, -400F));
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
}
