// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SearingBlowEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.combat:
//            RedFireballEffect

public class SearingBlowEffect extends AbstractGameEffect
{

    public SearingBlowEffect(float x, float y, int timesUpgraded)
    {
        this.x = x;
        this.y = y;
        this.timesUpgraded = timesUpgraded;
        CardCrawlGame.screenShake.shake(com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity.HIGH, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur.SHORT, true);
    }

    public void update()
    {
        CardCrawlGame.sound.playA("ATTACK_FIRE", 0.3F);
        CardCrawlGame.sound.playA("ATTACK_HEAVY", -0.3F);
        float dst = 180F + (float)timesUpgraded * 3F;
        AbstractDungeon.effectsQueue.add(new RedFireballEffect(x - dst * Settings.scale, y, x + dst * Settings.scale, y - 50F * Settings.scale, timesUpgraded));
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
    private int timesUpgraded;
}
