// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IronWaveEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.combat:
//            IronWaveParticle

public class IronWaveEffect extends AbstractGameEffect
{

    public IronWaveEffect(float x, float y, float cX)
    {
        waveTimer = 0.0F;
        this.x = x + 120F * Settings.scale;
        this.y = y - 20F * Settings.scale;
        this.cX = cX;
    }

    public void update()
    {
        waveTimer -= Gdx.graphics.getDeltaTime();
        if(waveTimer < 0.0F)
        {
            waveTimer = 0.03F;
            x += 160F * Settings.scale;
            y -= 15F * Settings.scale;
            AbstractDungeon.effectsQueue.add(new IronWaveParticle(x, y));
            if(x > cX)
            {
                isDone = true;
                CardCrawlGame.sound.playA("ATTACK_DAGGER_6", -0.3F);
            }
        }
    }

    public void render(SpriteBatch spritebatch)
    {
    }

    public void dispose()
    {
    }

    private float waveTimer;
    private float x;
    private float y;
    private float cX;
    private static final float WAVE_INTERVAL = 0.03F;
}
