// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ExplosionSmallEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.DarkSmokePuffEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.combat:
//            SmokingEmberEffect

public class ExplosionSmallEffect extends AbstractGameEffect
{

    public ExplosionSmallEffect(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public void update()
    {
        AbstractDungeon.effectsQueue.add(new DarkSmokePuffEffect(x, y));
        for(int i = 0; i < 12; i++)
            AbstractDungeon.effectsQueue.add(new SmokingEmberEffect(x + MathUtils.random(-50F, 50F) * Settings.scale, y + MathUtils.random(-50F, 50F) * Settings.scale));

        CardCrawlGame.sound.playA("ATTACK_FIRE", MathUtils.random(-0.2F, -0.1F));
        isDone = true;
    }

    public void render(SpriteBatch spritebatch)
    {
    }

    public void dispose()
    {
    }

    private static final int EMBER_COUNT = 12;
    private float x;
    private float y;
}
