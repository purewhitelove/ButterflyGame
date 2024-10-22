// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ClashEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.UpgradeShineParticleEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.combat:
//            AnimatedSlashEffect

public class ClashEffect extends AbstractGameEffect
{

    public ClashEffect(float x, float y)
    {
        this.x = x;
        this.y = y;
        startingDuration = 0.1F;
        duration = startingDuration;
    }

    public void update()
    {
        CardCrawlGame.sound.playA("ATTACK_WHIFF_1", 0.4F);
        CardCrawlGame.sound.playA("ATTACK_IRON_1", -0.1F);
        CardCrawlGame.sound.playA("ATTACK_IRON_3", -0.1F);
        AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(x, y - 30F * Settings.scale, -500F, -500F, 135F, 4F, Color.SCARLET, Color.GOLD));
        AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(x, y - 30F * Settings.scale, 500F, -500F, 225F, 4F, Color.SKY, Color.CYAN));
        for(int i = 0; i < 15; i++)
            AbstractDungeon.effectsQueue.add(new UpgradeShineParticleEffect(x + MathUtils.random(-40F, 40F) * Settings.scale, y + MathUtils.random(-40F, 40F) * Settings.scale));

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
}
