// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CardDisappearEffect.java

package com.megacrit.cardcrawl.vfx.cardManip;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;
import java.util.Iterator;

public class CardDisappearEffect extends AbstractGameEffect
{

    public CardDisappearEffect(AbstractCard card, float x, float y)
    {
        this.card = card;
        startingDuration = 2.0F;
        duration = startingDuration;
        identifySpawnLocation(x, y);
        card.drawScale = 0.01F;
        card.targetDrawScale = 1.0F;
        CardCrawlGame.sound.play("CARD_BURN");
    }

    private void identifySpawnLocation(float x, float y)
    {
        int effectCount = 0;
        Iterator iterator = AbstractDungeon.effectList.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractGameEffect e = (AbstractGameEffect)iterator.next();
            if(e instanceof CardDisappearEffect)
                effectCount++;
        } while(true);
        iterator = AbstractDungeon.topLevelEffects.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractGameEffect e = (AbstractGameEffect)iterator.next();
            if(e instanceof CardDisappearEffect)
                effectCount++;
        } while(true);
        card.current_x = x;
        card.current_y = y;
        card.target_y = (float)Settings.HEIGHT * 0.5F;
        switch(effectCount)
        {
        case 0: // '\0'
            card.target_x = (float)Settings.WIDTH * 0.5F;
            break;

        case 1: // '\001'
            card.target_x = (float)Settings.WIDTH * 0.5F - PADDING - AbstractCard.IMG_WIDTH;
            break;

        case 2: // '\002'
            card.target_x = (float)Settings.WIDTH * 0.5F + PADDING + AbstractCard.IMG_WIDTH;
            break;

        case 3: // '\003'
            card.target_x = (float)Settings.WIDTH * 0.5F - (PADDING + AbstractCard.IMG_WIDTH) * 2.0F;
            break;

        case 4: // '\004'
            card.target_x = (float)Settings.WIDTH * 0.5F + (PADDING + AbstractCard.IMG_WIDTH) * 2.0F;
            break;

        default:
            card.target_x = MathUtils.random((float)Settings.WIDTH * 0.1F, (float)Settings.WIDTH * 0.9F);
            card.target_y = MathUtils.random((float)Settings.HEIGHT * 0.2F, (float)Settings.HEIGHT * 0.8F);
            break;
        }
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.5F && !card.fadingOut)
            card.fadingOut = true;
        card.update();
        if(duration < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        if(!isDone)
            card.render(sb);
    }

    public void dispose()
    {
    }

    private AbstractCard card;
    private static final float PADDING;

    static 
    {
        PADDING = 30F * Settings.scale;
    }
}
