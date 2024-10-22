// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WhirlwindEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.combat:
//            WindyParticleEffect

public class WhirlwindEffect extends AbstractGameEffect
{

    public WhirlwindEffect(Color setColor, boolean reverse)
    {
        count = 0;
        timer = 0.0F;
        this.reverse = false;
        color = setColor.cpy();
        this.reverse = reverse;
    }

    public WhirlwindEffect()
    {
        this(new Color(0.9F, 0.9F, 1.0F, 1.0F), false);
    }

    public void update()
    {
        timer -= Gdx.graphics.getDeltaTime();
        if(timer < 0.0F)
        {
            timer += 0.05F;
            if(count == 0)
                AbstractDungeon.effectsQueue.add(new BorderLongFlashEffect(color.cpy()));
            AbstractDungeon.effectsQueue.add(new WindyParticleEffect(color, reverse));
            count++;
            if(count == 18)
                isDone = true;
        }
    }

    public void render(SpriteBatch spritebatch)
    {
    }

    public void dispose()
    {
    }

    private int count;
    private float timer;
    private boolean reverse;
}
