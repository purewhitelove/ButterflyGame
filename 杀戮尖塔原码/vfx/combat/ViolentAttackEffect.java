// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ViolentAttackEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.combat:
//            AnimatedSlashEffect, FlashAtkImgEffect

public class ViolentAttackEffect extends AbstractGameEffect
{

    public ViolentAttackEffect(float x, float y, Color setColor)
    {
        count = 5;
        this.x = x;
        this.y = y;
        duration = 0.0F;
        color = setColor;
        CardCrawlGame.screenShake.shake(com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity.MED, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur.SHORT, false);
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
        {
            CardCrawlGame.sound.playA("ATTACK_HEAVY", MathUtils.random(0.2F, 0.5F));
            AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(x + MathUtils.random(-100F, 100F) * Settings.scale, y + MathUtils.random(-100F, 100F) * Settings.scale, 0.0F, 0.0F, MathUtils.random(360F), MathUtils.random(2.5F, 4F), color, color));
            if(MathUtils.randomBoolean())
                AbstractDungeon.effectsQueue.add(new FlashAtkImgEffect(x + MathUtils.random(-150F, 150F) * Settings.scale, y + MathUtils.random(-150F, 150F) * Settings.scale, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            else
                AbstractDungeon.effectsQueue.add(new FlashAtkImgEffect(x + MathUtils.random(-150F, 150F) * Settings.scale, y + MathUtils.random(-150F, 150F) * Settings.scale, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            duration = MathUtils.random(0.05F, 0.1F);
            count--;
        }
        if(count == 0)
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
    private int count;
}
