// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StrikeEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.combat:
//            DamageNumberEffect, DamageImpactLineEffect, DamageImpactCurvyEffect, BlockedWordEffect, 
//            BlockImpactLineEffect

public class StrikeEffect extends AbstractGameEffect
{

    public StrikeEffect(AbstractCreature target, float x, float y, int number)
    {
        AbstractDungeon.effectsQueue.add(new DamageNumberEffect(target, x, y, number));
        for(int i = 0; i < 18; i++)
            AbstractDungeon.effectsQueue.add(new DamageImpactLineEffect(x, y));

        for(int i = 0; i < 5; i++)
            AbstractDungeon.effectsQueue.add(new DamageImpactCurvyEffect(x, y));

        if(number < 5)
            CardCrawlGame.screenShake.shake(com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity.LOW, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur.SHORT, false);
        else
        if(number < 20)
            CardCrawlGame.screenShake.shake(com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity.MED, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur.SHORT, false);
        else
            CardCrawlGame.screenShake.shake(com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity.HIGH, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur.SHORT, false);
    }

    public StrikeEffect(AbstractCreature target, float x, float y, String msg)
    {
        AbstractDungeon.effectsQueue.add(new BlockedWordEffect(target, x, y, msg));
        for(int i = 0; i < 18; i++)
            AbstractDungeon.effectsQueue.add(new BlockImpactLineEffect(x, y));

        CardCrawlGame.screenShake.shake(com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity.MED, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur.SHORT, false);
    }

    public void update()
    {
        isDone = true;
    }

    public void render(SpriteBatch spritebatch)
    {
    }

    public void dispose()
    {
    }
}
