// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SmokeBombEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.combat:
//            SmokeBlurEffect

public class SmokeBombEffect extends AbstractGameEffect
{

    public SmokeBombEffect(float x, float y)
    {
        this.x = x;
        this.y = y;
        duration = 0.2F;
    }

    public void update()
    {
        if(duration == 0.2F)
        {
            CardCrawlGame.sound.play("ATTACK_WHIFF_2");
            for(int i = 0; i < 90; i++)
                AbstractDungeon.effectsQueue.add(new SmokeBlurEffect(x, y));

        }
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
        {
            CardCrawlGame.sound.play("APPEAR");
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
}
