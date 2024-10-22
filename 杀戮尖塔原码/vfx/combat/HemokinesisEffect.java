// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HemokinesisEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.combat:
//            HemokinesisParticle

public class HemokinesisEffect extends AbstractGameEffect
{

    public HemokinesisEffect(float sX, float sY, float tX, float tY)
    {
        x = sX;
        y = sY;
        this.tX = tX;
        this.tY = tY;
        scale = 0.12F;
        duration = 0.5F;
    }

    public void update()
    {
        scale -= Gdx.graphics.getDeltaTime();
        if(scale < 0.0F)
        {
            AbstractDungeon.effectsQueue.add(new HemokinesisParticle(x + MathUtils.random(60F, -60F) * Settings.scale, y + MathUtils.random(60F, -60F) * Settings.scale, tX, tY, AbstractDungeon.player.flipHorizontal));
            scale = 0.04F;
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

    private float x;
    private float y;
    private float tX;
    private float tY;
}
