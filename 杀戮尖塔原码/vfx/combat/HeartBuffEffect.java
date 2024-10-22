// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HeartBuffEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.combat:
//            SwirlyBloodEffect

public class HeartBuffEffect extends AbstractGameEffect
{

    public HeartBuffEffect(float x, float y)
    {
        this.x = x;
        this.y = y;
        duration = 0.5F;
        scale = 0.0F;
    }

    public void update()
    {
        if(duration == 0.5F)
            CardCrawlGame.sound.playA("BUFF_2", -0.6F);
        scale -= Gdx.graphics.getDeltaTime();
        if(scale < 0.0F)
        {
            scale = 0.05F;
            AbstractDungeon.effectsQueue.add(new SwirlyBloodEffect(x + MathUtils.random(-150F, 150F) * Settings.scale, y + MathUtils.random(-150F, 150F) * Settings.scale));
            AbstractDungeon.effectsQueue.add(new SwirlyBloodEffect(x + MathUtils.random(-150F, 150F) * Settings.scale, y + MathUtils.random(-150F, 150F) * Settings.scale));
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

    float x;
    float y;
}
