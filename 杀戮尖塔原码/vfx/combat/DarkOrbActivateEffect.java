// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DarkOrbActivateEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.combat:
//            DarkOrbActivateParticle

public class DarkOrbActivateEffect extends AbstractGameEffect
{

    public DarkOrbActivateEffect(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public void update()
    {
        for(int i = 0; i < 4; i++)
            AbstractDungeon.effectsQueue.add(new DarkOrbActivateParticle(x, y));

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
