// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ClangClangClangEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect, UpgradeShineParticleEffect

public class ClangClangClangEffect extends AbstractGameEffect
{

    public ClangClangClangEffect(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public void update()
    {
        for(int i = 0; i < 30; i++)
            AbstractDungeon.effectsQueue.add(new UpgradeShineParticleEffect(x + MathUtils.random(-10F, 10F) * Settings.scale, y + MathUtils.random(-10F, 10F) * Settings.scale));

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
