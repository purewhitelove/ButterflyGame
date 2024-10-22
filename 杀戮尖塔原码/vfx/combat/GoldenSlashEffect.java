// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GoldenSlashEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.combat:
//            AnimatedSlashEffect

public class GoldenSlashEffect extends AbstractGameEffect
{

    public GoldenSlashEffect(float x, float y, boolean isVertical)
    {
        this.x = x;
        this.y = y;
        startingDuration = 0.1F;
        duration = startingDuration;
        this.isVertical = isVertical;
    }

    public void update()
    {
        CardCrawlGame.sound.playA("ATTACK_IRON_2", -0.4F);
        CardCrawlGame.sound.playA("ATTACK_HEAVY", -0.4F);
        if(isVertical)
            AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(x, y - 30F * Settings.scale, 0.0F, -500F, 180F, 5F, Color.GOLD, Color.GOLD));
        else
            AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(x, y - 30F * Settings.scale, -500F, -500F, 135F, 4F, Color.GOLD, Color.GOLD));
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
    private boolean isVertical;
}
