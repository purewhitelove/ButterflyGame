// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RainingGoldEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect, TouchPickupGold

public class RainingGoldEffect extends AbstractGameEffect
{

    public RainingGoldEffect(int amount)
    {
        staggerTimer = 0.0F;
        this.amount = amount;
        playerCentered = false;
        if(amount < 100)
        {
            min = 1;
            max = 7;
        } else
        {
            min = 3;
            max = 18;
        }
    }

    public RainingGoldEffect(int amount, boolean centerOnPlayer)
    {
        this(amount);
        playerCentered = centerOnPlayer;
    }

    public void update()
    {
        staggerTimer -= Gdx.graphics.getDeltaTime();
        if(staggerTimer < 0.0F)
        {
            int goldToSpawn = MathUtils.random(min, max);
            if(goldToSpawn <= amount)
            {
                amount -= goldToSpawn;
            } else
            {
                goldToSpawn = amount;
                isDone = true;
            }
            for(int i = 0; i < goldToSpawn; i++)
                AbstractDungeon.effectsQueue.add(new TouchPickupGold(playerCentered));

            staggerTimer = MathUtils.random(0.3F);
        }
    }

    public void render(SpriteBatch spritebatch)
    {
    }

    public void dispose()
    {
    }

    private int amount;
    private int min;
    private int max;
    private float staggerTimer;
    private boolean playerCentered;
}
