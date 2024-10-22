// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DaggerSprayEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.combat:
//            FlyingDaggerEffect

public class DaggerSprayEffect extends AbstractGameEffect
{

    public DaggerSprayEffect(boolean shouldFlip)
    {
        flipX = shouldFlip;
    }

    public void update()
    {
        isDone = true;
        if(flipX)
        {
            for(int i = 12; i > 0; i--)
            {
                float x = AbstractDungeon.player.hb.cX - MathUtils.random(0.0F, 450F) * Settings.scale;
                AbstractDungeon.effectsQueue.add(new FlyingDaggerEffect(x, AbstractDungeon.player.hb.cY + 120F * Settings.scale + (float)i * -18F * Settings.scale, (float)(i * 4) - 30F, true));
            }

        } else
        {
            for(int i = 0; i < 12; i++)
            {
                float x = AbstractDungeon.player.hb.cX + MathUtils.random(0.0F, 450F) * Settings.scale;
                AbstractDungeon.effectsQueue.add(new FlyingDaggerEffect(x, (AbstractDungeon.player.hb.cY - 100F * Settings.scale) + (float)i * 18F * Settings.scale, (float)(i * 4) - 20F, false));
            }

        }
    }

    public void render(SpriteBatch spritebatch)
    {
    }

    public void dispose()
    {
    }

    private boolean flipX;
}
