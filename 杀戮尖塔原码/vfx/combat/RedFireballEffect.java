// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RedFireballEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.combat:
//            RedFireBurstParticleEffect

public class RedFireballEffect extends AbstractGameEffect
{

    public RedFireballEffect(float startX, float startY, float targetX, float targetY, int timesUpgraded)
    {
        vfxTimer = 0.0F;
        startingDuration = 0.3F;
        duration = 0.3F;
        this.startX = startX;
        this.startY = startY;
        this.targetX = targetX;
        this.targetY = targetY;
        this.timesUpgraded = timesUpgraded;
        x = startX;
        y = startY;
    }

    public void update()
    {
        x = Interpolation.fade.apply(targetX, startX, duration / startingDuration);
        if(duration > startingDuration / 2.0F)
            y = Interpolation.pow4In.apply(startY, targetY, ((duration - startingDuration / 2.0F) / startingDuration) * 2.0F);
        else
            y = Interpolation.pow4Out.apply(targetY, startY, (duration / startingDuration) * 2.0F);
        vfxTimer -= Gdx.graphics.getDeltaTime();
        if(vfxTimer < 0.0F)
        {
            vfxTimer += 0.016F;
            AbstractDungeon.effectsQueue.add(new RedFireBurstParticleEffect(x, y, timesUpgraded));
            AbstractDungeon.effectsQueue.add(new RedFireBurstParticleEffect(x, y, timesUpgraded));
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

    private static final float FIREBALL_INTERVAL = 0.016F;
    private float x;
    private float y;
    private float startX;
    private float startY;
    private float targetX;
    private float targetY;
    private float vfxTimer;
    private int timesUpgraded;
}
