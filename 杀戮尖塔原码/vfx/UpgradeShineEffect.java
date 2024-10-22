// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UpgradeShineEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect, UpgradeHammerImprintEffect, UpgradeShineParticleEffect

public class UpgradeShineEffect extends AbstractGameEffect
{

    public UpgradeShineEffect(float x, float y)
    {
        clang1 = false;
        clang2 = false;
        this.x = x;
        this.y = y;
        duration = 0.8F;
    }

    public void update()
    {
        if(duration < 0.6F && !clang1)
        {
            CardCrawlGame.sound.play("CARD_UPGRADE");
            clang1 = true;
            clank(x - 80F * Settings.scale, y + 0.0F * Settings.scale);
            CardCrawlGame.screenShake.shake(com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity.HIGH, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur.SHORT, false);
        }
        if(duration < 0.2F && !clang2)
        {
            clang2 = true;
            clank(x + 90F * Settings.scale, y - 110F * Settings.scale);
            CardCrawlGame.screenShake.shake(com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity.HIGH, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur.SHORT, false);
        }
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
        {
            clank(x + 30F * Settings.scale, y + 120F * Settings.scale);
            isDone = true;
            CardCrawlGame.screenShake.shake(com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity.HIGH, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur.SHORT, false);
        }
    }

    private void clank(float x, float y)
    {
        AbstractDungeon.topLevelEffectsQueue.add(new UpgradeHammerImprintEffect(x, y));
        if(Settings.DISABLE_EFFECTS)
            return;
        for(int i = 0; i < 30; i++)
            AbstractDungeon.topLevelEffectsQueue.add(new UpgradeShineParticleEffect(x + MathUtils.random(-10F, 10F) * Settings.scale, y + MathUtils.random(-10F, 10F) * Settings.scale));

    }

    public void render(SpriteBatch spritebatch)
    {
    }

    public void dispose()
    {
    }

    private float x;
    private float y;
    private boolean clang1;
    private boolean clang2;
}
