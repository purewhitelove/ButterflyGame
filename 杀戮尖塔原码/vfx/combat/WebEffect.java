// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WebEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.combat:
//            WebLineEffect, WebParticleEffect

public class WebEffect extends AbstractGameEffect
{

    public WebEffect(AbstractCreature target, float srcX, float srcY)
    {
        timer = 0.0F;
        count = 0;
        this.target = target;
        this.srcX = srcX;
        this.srcY = srcY;
        duration = 1.0F;
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        timer -= Gdx.graphics.getDeltaTime();
        if(timer < 0.0F)
        {
            timer += 0.1F;
            switch(count)
            {
            case 0: // '\0'
                AbstractDungeon.effectsQueue.add(new WebLineEffect(srcX, srcY, true));
                AbstractDungeon.effectsQueue.add(new WebLineEffect(srcX, srcY, true));
                AbstractDungeon.effectsQueue.add(new WebParticleEffect(target.hb.cX - 90F * Settings.scale, target.hb.cY - 10F * Settings.scale));
                break;

            case 1: // '\001'
                AbstractDungeon.effectsQueue.add(new WebLineEffect(srcX, srcY, true));
                AbstractDungeon.effectsQueue.add(new WebLineEffect(srcX, srcY, true));
                break;

            case 2: // '\002'
                AbstractDungeon.effectsQueue.add(new WebLineEffect(srcX, srcY, true));
                AbstractDungeon.effectsQueue.add(new WebLineEffect(srcX, srcY, true));
                AbstractDungeon.effectsQueue.add(new WebParticleEffect(target.hb.cX + 70F * Settings.scale, target.hb.cY + 80F * Settings.scale));
                break;

            case 4: // '\004'
                AbstractDungeon.effectsQueue.add(new WebParticleEffect(target.hb.cX + 30F * Settings.scale, target.hb.cY - 100F * Settings.scale));
                break;
            }
            count++;
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

    private float timer;
    private int count;
    private AbstractCreature target;
    private float srcX;
    private float srcY;
}
