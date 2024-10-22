// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FlashIntentEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.combat:
//            FlashIntentParticle

public class FlashIntentEffect extends AbstractGameEffect
{

    public FlashIntentEffect(Texture img, AbstractMonster m)
    {
        intervalTimer = 0.0F;
        duration = 1.0F;
        this.img = img;
        this.m = m;
    }

    public void update()
    {
        intervalTimer -= Gdx.graphics.getDeltaTime();
        if(intervalTimer < 0.0F && !m.isDying)
        {
            intervalTimer = 0.17F;
            AbstractDungeon.effectsQueue.add(new FlashIntentParticle(img, m));
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

    private static final float DURATION = 1F;
    private static final float FLASH_INTERVAL = 0.17F;
    private float intervalTimer;
    private Texture img;
    private AbstractMonster m;
}
