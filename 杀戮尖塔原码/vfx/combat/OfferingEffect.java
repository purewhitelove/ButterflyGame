// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OfferingEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.vfx.combat:
//            WaterDropEffect

public class OfferingEffect extends AbstractGameEffect
{

    public OfferingEffect()
    {
        count = 0;
        timer = 0.0F;
    }

    public void update()
    {
        timer -= Gdx.graphics.getDeltaTime();
        if(timer < 0.0F)
        {
            timer += 0.3F;
            switch(count)
            {
            case 0: // '\0'
                CardCrawlGame.sound.playA("ATTACK_FIRE", -0.5F);
                CardCrawlGame.sound.playA("BLOOD_SPLAT", -0.75F);
                AbstractDungeon.effectsQueue.add(new BorderLongFlashEffect(new Color(1.0F, 0.1F, 0.1F, 1.0F)));
                AbstractDungeon.effectsQueue.add(new WaterDropEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY + 250F * Settings.scale));
                break;

            case 1: // '\001'
                AbstractDungeon.effectsQueue.add(new WaterDropEffect(AbstractDungeon.player.hb.cX + 150F * Settings.scale, AbstractDungeon.player.hb.cY - 80F * Settings.scale));
                break;

            case 2: // '\002'
                AbstractDungeon.effectsQueue.add(new WaterDropEffect(AbstractDungeon.player.hb.cX - 200F * Settings.scale, AbstractDungeon.player.hb.cY + 50F * Settings.scale));
                break;

            case 3: // '\003'
                AbstractDungeon.effectsQueue.add(new WaterDropEffect(AbstractDungeon.player.hb.cX + 200F * Settings.scale, AbstractDungeon.player.hb.cY + 50F * Settings.scale));
                break;

            case 4: // '\004'
                AbstractDungeon.effectsQueue.add(new WaterDropEffect(AbstractDungeon.player.hb.cX - 150F * Settings.scale, AbstractDungeon.player.hb.cY - 80F * Settings.scale));
                break;
            }
            count++;
            if(count == 6)
                isDone = true;
        }
    }

    public void render(SpriteBatch spritebatch)
    {
    }

    public void dispose()
    {
    }

    private int count;
    private float timer;
}
