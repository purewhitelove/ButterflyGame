// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IntenseZoomEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.combat:
//            IntenseZoomParticle

public class IntenseZoomEffect extends AbstractGameEffect
{

    public IntenseZoomEffect(float x, float y, boolean isBlack)
    {
        this.x = x;
        this.y = y;
        this.isBlack = isBlack;
    }

    public void update()
    {
        if(isBlack)
            AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.BLACK, isBlack));
        else
            AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Settings.GOLD_COLOR, isBlack));
        for(int i = 0; i < 10; i++)
            AbstractDungeon.effectsQueue.add(new IntenseZoomParticle(x, y, isBlack));

        isDone = true;
    }

    public void render(SpriteBatch spritebatch)
    {
    }

    public void dispose()
    {
    }

    private boolean isBlack;
    private float x;
    private float y;
    private static final int AMT = 10;
}
