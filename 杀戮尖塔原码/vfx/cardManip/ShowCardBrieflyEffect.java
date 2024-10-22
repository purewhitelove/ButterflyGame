// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ShowCardBrieflyEffect.java

package com.megacrit.cardcrawl.vfx.cardManip;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;
import java.util.Iterator;

public class ShowCardBrieflyEffect extends AbstractGameEffect
{

    public ShowCardBrieflyEffect(AbstractCard card)
    {
        this.card = card;
        duration = 2.5F;
        startingDuration = 2.5F;
        identifySpawnLocation((float)Settings.WIDTH - 96F * Settings.scale, (float)Settings.HEIGHT - 32F * Settings.scale);
        card.drawScale = 0.01F;
        card.targetDrawScale = 1.0F;
    }

    public ShowCardBrieflyEffect(AbstractCard card, float x, float y)
    {
        this.card = card;
        duration = 2.5F;
        startingDuration = 2.5F;
        this.card.drawScale = 0.01F;
        this.card.targetDrawScale = 1.0F;
        this.card.current_x = (float)Settings.WIDTH / 2.0F;
        this.card.current_y = (float)Settings.HEIGHT / 2.0F;
        this.card.target_x = x;
        this.card.target_y = y;
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
            if(e instanceof ShowCardBrieflyEffect)
                effectCount++;
        } while(true);
        card.current_x = (float)Settings.WIDTH / 2.0F;
        card.current_y = (float)Settings.HEIGHT / 2.0F;
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
        if(duration < 0.6F)
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

    private static final float EFFECT_DUR = 2.5F;
    private AbstractCard card;
    private static final float PADDING;

    static 
    {
        PADDING = 30F * Settings.scale;
    }
}
