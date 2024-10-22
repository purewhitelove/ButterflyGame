// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ScrapeEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.combat:
//            AnimatedSlashEffect

public class ScrapeEffect extends AbstractGameEffect
{

    public ScrapeEffect(float x, float y)
    {
        this.x = x;
        this.y = y;
        color = Color.MAROON;
        color2 = Color.SCARLET;
        startingDuration = 0.1F;
        duration = startingDuration;
    }

    public void update()
    {
        if(MathUtils.randomBoolean())
            CardCrawlGame.sound.playA("ATTACK_DAGGER_5", MathUtils.random(0.0F, -0.3F));
        else
            CardCrawlGame.sound.playA("ATTACK_DAGGER_6", MathUtils.random(0.0F, -0.3F));
        float oX = -50F * Settings.scale;
        float oY = 20F * Settings.scale;
        float sX = -35F * Settings.scale;
        float sY = 20F * Settings.scale;
        float dX = -150F;
        float dY = -400F;
        float angle = 155F;
        AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(x + sX * 1.5F + oX, y + sY * 1.5F + oY, dX, dY, angle, color, color2));
        AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(x + sX * 0.5F + oX, y + sY * 0.5F + oY, dX, dY, angle, color, color2));
        AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect((x - sX * 0.5F) + oX, (y - sY * 0.5F) + oY, dX, dY, angle, color, color2));
        AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect((x - sX * 1.5F) + oX, (y - sY * 1.5F) + oY, dX, dY, angle, color, color2));
        oX = 50F * Settings.scale;
        oY = 20F * Settings.scale;
        sX = 35F * Settings.scale;
        sY = 20F * Settings.scale;
        dX = 150F;
        dY = -400F;
        angle = -155F;
        AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(x + sX * 1.5F + oX, y + sY * 1.5F + oY, dX, dY, angle, color, color2));
        AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(x + sX * 0.5F + oX, y + sY * 0.5F + oY, dX, dY, angle, color, color2));
        AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect((x - sX * 0.5F) + oX, (y - sY * 0.5F) + oY, dX, dY, angle, color, color2));
        AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect((x - sX * 1.5F) + oX, (y - sY * 1.5F) + oY, dX, dY, angle, color, color2));
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
    private Color color2;
}
