// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CeilingDustEffect.java

package com.megacrit.cardcrawl.vfx.scene;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.FallingDustEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.scene:
//            CeilingDustCloudEffect

public class CeilingDustEffect extends AbstractGameEffect
{

    public CeilingDustEffect()
    {
        count = 20;
        setPosition();
    }

    private void setPosition()
    {
        x = MathUtils.random(0.0F, 1870F) * Settings.scale;
    }

    public void update()
    {
        if(count != 0)
        {
            int num = MathUtils.random(0, 8);
            count -= num;
            for(int i = 0; i < num; i++)
            {
                AbstractDungeon.effectsQueue.add(new FallingDustEffect(x, AbstractDungeon.floorY + 640F * Settings.scale));
                if(MathUtils.randomBoolean(0.8F))
                    AbstractDungeon.effectsQueue.add(new CeilingDustCloudEffect(x, AbstractDungeon.floorY + 640F * Settings.scale));
            }

            if(count <= 0)
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
    private float x;
}
