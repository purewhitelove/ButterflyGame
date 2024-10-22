// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HealEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.combat:
//            HealNumberEffect, HealVerticalLineEffect

public class HealEffect extends AbstractGameEffect
{

    public HealEffect(float x, float y, int amount)
    {
        int roll = MathUtils.random(0, 2);
        if(roll == 0)
            CardCrawlGame.sound.play("HEAL_1");
        else
        if(roll == 1)
            CardCrawlGame.sound.play("HEAL_2");
        else
            CardCrawlGame.sound.play("HEAL_3");
        AbstractDungeon.effectsQueue.add(new HealNumberEffect(x, y, amount));
        for(int i = 0; i < 18; i++)
            AbstractDungeon.effectsQueue.add(new HealVerticalLineEffect(x + MathUtils.random(-X_JITTER * 1.5F, X_JITTER * 1.5F), y + OFFSET_Y + MathUtils.random(-Y_JITTER, Y_JITTER)));

    }

    public void update()
    {
        isDone = true;
    }

    public void render(SpriteBatch spritebatch)
    {
    }

    public void dispose()
    {
    }

    private static final float X_JITTER;
    private static final float Y_JITTER;
    private static final float OFFSET_Y;

    static 
    {
        X_JITTER = 120F * Settings.scale;
        Y_JITTER = 120F * Settings.scale;
        OFFSET_Y = -50F * Settings.scale;
    }
}
