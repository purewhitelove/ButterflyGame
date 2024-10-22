// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IntimidateEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.combat:
//            WobblyLineEffect

public class IntimidateEffect extends AbstractGameEffect
{

    public IntimidateEffect(float newX, float newY)
    {
        duration = 1.0F;
        x = newX;
        y = newY;
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        vfxTimer -= Gdx.graphics.getDeltaTime();
        if(vfxTimer < 0.0F)
        {
            vfxTimer = 0.016F;
            AbstractDungeon.effectsQueue.add(new WobblyLineEffect(x, y, Settings.CREAM_COLOR.cpy()));
        }
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch spritebatch)
    {
    }

    public void dispose()
    {
    }

    private static final float EFFECT_DUR = 1F;
    private float x;
    private float y;
    private float vfxTimer;
    private static final float VFX_INTERVAL = 0.016F;
}
