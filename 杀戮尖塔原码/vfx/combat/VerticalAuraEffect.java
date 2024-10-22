// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   VerticalAuraEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.combat:
//            VerticalAuraParticle

public class VerticalAuraEffect extends AbstractGameEffect
{

    public VerticalAuraEffect(Color c, float x, float y)
    {
        color = c;
        this.x = x;
        this.y = y;
    }

    public void update()
    {
        for(int i = 0; (float)i < 20F; i++)
            AbstractDungeon.effectsQueue.add(new VerticalAuraParticle(color, x, y));

        isDone = true;
    }

    public void render(SpriteBatch spritebatch)
    {
    }

    public void dispose()
    {
    }

    private static final float NUM_PARTICLES = 20F;
    private float x;
    private float y;
}
