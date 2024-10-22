// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DieDieDieEffect.java

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
//            ThrowShivEffect

public class DieDieDieEffect extends AbstractGameEffect
{

    public DieDieDieEffect()
    {
        interval = 0.0F;
        duration = 0.5F;
    }

    public void update()
    {
        interval -= Gdx.graphics.getDeltaTime();
        if(interval < 0.0F)
        {
            interval = MathUtils.random(0.02F, 0.05F);
            int derp = MathUtils.random(1, 4);
            for(int i = 0; i < derp; i++)
                AbstractDungeon.effectsQueue.add(new ThrowShivEffect(MathUtils.random(1200F, 2000F) * Settings.scale, AbstractDungeon.floorY + MathUtils.random(-100F, 500F) * Settings.scale));

        }
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch spritebatch)
    {
    }

    public void dispose()
    {
    }

    private float interval;
}
