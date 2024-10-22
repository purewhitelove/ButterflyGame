// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SanctityEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.combat:
//            LightRayFlyOutEffect

public class SanctityEffect extends AbstractGameEffect
{

    public SanctityEffect(float newX, float newY)
    {
        count = 10;
        x = newX;
        y = newY;
    }

    public void update()
    {
        vfxTimer -= Gdx.graphics.getDeltaTime();
        if(vfxTimer < 0.0F)
        {
            count--;
            vfxTimer = MathUtils.random(0.0F, 0.02F);
            for(int i = 0; i < 3; i++)
                AbstractDungeon.effectsQueue.add(new LightRayFlyOutEffect(x, y, new Color(1.0F, 0.9F, 0.7F, 0.0F)));

        }
        if(count <= 0)
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
    private float vfxTimer;
    private int count;
}
