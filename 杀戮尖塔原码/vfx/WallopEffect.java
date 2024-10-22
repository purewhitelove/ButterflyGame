// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WallopEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect, StarBounceEffect

public class WallopEffect extends AbstractGameEffect
{

    public WallopEffect(int damage, float x, float y)
    {
        this.damage = 0;
        this.damage = damage;
        this.x = x;
        this.y = y;
        if(this.damage > 50)
            this.damage = 50;
    }

    public void update()
    {
        for(int i = 0; i < damage; i++)
            AbstractDungeon.effectsQueue.add(new StarBounceEffect(x, y));

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
    private int damage;
}
