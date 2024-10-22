// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CollectorCurseEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.RoomTintEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect, BorderLongFlashEffect, CollectorStakeEffect

public class CollectorCurseEffect extends AbstractGameEffect
{

    public CollectorCurseEffect(float x, float y)
    {
        stakeTimer = 0.0F;
        this.x = x;
        this.y = y;
        count = 13;
    }

    public void update()
    {
        stakeTimer -= Gdx.graphics.getDeltaTime();
        if(stakeTimer < 0.0F)
        {
            if(count == 13)
            {
                CardCrawlGame.sound.playA("ATTACK_HEAVY", -0.5F);
                AbstractDungeon.effectsQueue.add(new RoomTintEffect(Color.BLACK.cpy(), 0.8F));
                AbstractDungeon.effectsQueue.add(new BorderLongFlashEffect(new Color(1.0F, 0.0F, 1.0F, 0.7F)));
            }
            AbstractDungeon.effectsQueue.add(new CollectorStakeEffect(x + MathUtils.random(-50F, 50F) * Settings.scale, y + MathUtils.random(-60F, 60F) * Settings.scale));
            stakeTimer = 0.04F;
            count--;
            if(count == 0)
                isDone = true;
        }
    }

    public void render(SpriteBatch spritebatch)
    {
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private int count;
    private float stakeTimer;
}
