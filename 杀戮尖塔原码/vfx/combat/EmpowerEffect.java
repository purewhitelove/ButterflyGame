// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EmpowerEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.combat:
//            EmpowerCircleEffect

public class EmpowerEffect extends AbstractGameEffect
{

    public EmpowerEffect(float x, float y)
    {
        CardCrawlGame.sound.play("CARD_POWER_IMPACT", 0.1F);
        for(int i = 0; i < 18; i++)
            AbstractDungeon.effectList.add(new EmpowerCircleEffect(x, y));

        CardCrawlGame.screenShake.rumble(0.25F);
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

    private static final float SHAKE_DURATION = 0.25F;
}
