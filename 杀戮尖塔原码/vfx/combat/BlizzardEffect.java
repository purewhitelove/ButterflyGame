// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BlizzardEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.combat:
//            FallingIceEffect

public class BlizzardEffect extends AbstractGameEffect
{

    public BlizzardEffect(int frostCount, boolean flipped)
    {
        this.flipped = false;
        this.frostCount = 5 + frostCount;
        this.flipped = flipped;
        if(this.frostCount > 50)
            this.frostCount = 50;
    }

    public void update()
    {
        CardCrawlGame.sound.playA("ORB_FROST_CHANNEL", -0.25F - (float)frostCount / 200F);
        CardCrawlGame.screenShake.shake(com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity.HIGH, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur.MED, true);
        AbstractDungeon.effectsQueue.add(new BorderLongFlashEffect(Color.SKY));
        for(int i = 0; i < frostCount; i++)
            AbstractDungeon.effectsQueue.add(new FallingIceEffect(frostCount, flipped));

        isDone = true;
    }

    public void render(SpriteBatch spritebatch)
    {
    }

    public void dispose()
    {
    }

    private int frostCount;
    private boolean flipped;
}
