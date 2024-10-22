// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RipAndTearEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.combat:
//            AnimatedSlashEffect

public class RipAndTearEffect extends AbstractGameEffect
{

    public RipAndTearEffect(float x, float y, Color color1, Color color2)
    {
        this.x = x;
        this.y = y;
        color = color1;
        this.color2 = color2;
        startingDuration = 0.1F;
        duration = startingDuration;
    }

    public void update()
    {
        if(MathUtils.randomBoolean())
            CardCrawlGame.sound.playA("ATTACK_DAGGER_5", MathUtils.random(0.0F, -0.3F));
        else
            CardCrawlGame.sound.playA("ATTACK_DAGGER_6", MathUtils.random(0.0F, -0.3F));
        if(!flipped)
        {
            float baseAngle = 135F;
            AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(x - 45F, y + 45F, -150F, -150F, baseAngle + MathUtils.random(-10F, 10F), color, color2));
            AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(x, y, -150F, -150F, baseAngle + MathUtils.random(-10F, 10F), color, color2));
            AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(x + 45F, y - 45F, -150F, -150F, baseAngle + MathUtils.random(-10F, 10F), color, color2));
        } else
        {
            float baseAngle = -135F;
            AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(x - 45F, y - 45F, 150F, -150F, baseAngle + MathUtils.random(-10F, 10F), color, color2));
            AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(x, y, 150F, -150F, baseAngle + MathUtils.random(-10F, 10F), color, color2));
            AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(x + 40F, y + 40F, 150F, -150F, baseAngle + MathUtils.random(-10F, 10F), color, color2));
        }
        flipped = !flipped;
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
    private static boolean flipped = false;

}
