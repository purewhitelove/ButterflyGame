// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FrostOrbActivateEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.combat:
//            FrostOrbActivateParticle

public class FrostOrbActivateEffect extends AbstractGameEffect
{

    public FrostOrbActivateEffect(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public void update()
    {
        AbstractDungeon.effectsQueue.add(new FrostOrbActivateParticle(0, x, y));
        AbstractDungeon.effectsQueue.add(new FrostOrbActivateParticle(1, x, y));
        AbstractDungeon.effectsQueue.add(new FrostOrbActivateParticle(2, x, y));
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
