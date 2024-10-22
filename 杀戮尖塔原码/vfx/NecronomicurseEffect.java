// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NecronomicurseEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect

public class NecronomicurseEffect extends AbstractGameEffect
{

    public NecronomicurseEffect(AbstractCard card, float x, float y)
    {
        waitTimer = 2.0F;
        this.card = card;
        if(Settings.FAST_MODE)
            duration = 0.5F;
        else
            duration = 1.5F;
        identifySpawnLocation(x, y);
        card.drawScale = 0.01F;
        card.targetDrawScale = 1.0F;
        CardCrawlGame.sound.play("CARD_SELECT");
        AbstractDungeon.player.masterDeck.addToTop(card);
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
            if(e instanceof NecronomicurseEffect)
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
        if(waitTimer > 0.0F)
        {
            waitTimer -= Gdx.graphics.getDeltaTime();
            return;
        }
        duration -= Gdx.graphics.getDeltaTime();
        card.current_x = card.current_x + MathUtils.random(-10F, 10F) * Settings.scale;
        card.current_y = card.current_y + MathUtils.random(-10F, 10F) * Settings.scale;
        card.update();
        if(duration < 0.0F)
        {
            isDone = true;
            card.shrink();
            AbstractDungeon.getCurrRoom().souls.obtain(card, false);
        }
    }

    public void render(SpriteBatch sb)
    {
        if(!isDone && waitTimer < 0.0F)
            card.render(sb);
    }

    public void dispose()
    {
    }

    private static final float EFFECT_DUR = 1.5F;
    private static final float FAST_DUR = 0.5F;
    private AbstractCard card;
    private static final float PADDING;
    private float waitTimer;

    static 
    {
        PADDING = 30F * Settings.scale;
    }
}
