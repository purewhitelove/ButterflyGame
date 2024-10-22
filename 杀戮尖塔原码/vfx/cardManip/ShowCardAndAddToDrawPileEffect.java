// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ShowCardAndAddToDrawPileEffect.java

package com.megacrit.cardcrawl.vfx.cardManip;

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
import com.megacrit.cardcrawl.powers.watcher.MasterRealityPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.CardPoofEffect;
import java.util.ArrayList;
import java.util.Iterator;

public class ShowCardAndAddToDrawPileEffect extends AbstractGameEffect
{

    public ShowCardAndAddToDrawPileEffect(AbstractCard srcCard, float x, float y, boolean randomSpot, boolean cardOffset, boolean toBottom)
    {
        this.randomSpot = false;
        this.cardOffset = false;
        card = srcCard.makeStatEquivalentCopy();
        this.cardOffset = cardOffset;
        duration = 1.5F;
        this.randomSpot = randomSpot;
        if(cardOffset)
        {
            identifySpawnLocation(x, y);
        } else
        {
            card.target_x = x;
            card.target_y = y;
        }
        AbstractDungeon.effectsQueue.add(new CardPoofEffect(card.target_x, card.target_y));
        card.drawScale = 0.01F;
        card.targetDrawScale = 1.0F;
        if(card.type != com.megacrit.cardcrawl.cards.AbstractCard.CardType.CURSE && card.type != com.megacrit.cardcrawl.cards.AbstractCard.CardType.STATUS && AbstractDungeon.player.hasPower("MasterRealityPower"))
            card.upgrade();
        CardCrawlGame.sound.play("CARD_OBTAIN");
        if(toBottom)
            AbstractDungeon.player.drawPile.addToBottom(card);
        else
        if(randomSpot)
            AbstractDungeon.player.drawPile.addToRandomSpot(card);
        else
            AbstractDungeon.player.drawPile.addToTop(card);
    }

    public ShowCardAndAddToDrawPileEffect(AbstractCard srcCard, float x, float y, boolean randomSpot, boolean cardOffset)
    {
        this(srcCard, x, y, randomSpot, cardOffset, false);
    }

    public ShowCardAndAddToDrawPileEffect(AbstractCard srcCard, float x, float y, boolean randomSpot)
    {
        this(srcCard, x, y, randomSpot, false);
    }

    public ShowCardAndAddToDrawPileEffect(AbstractCard srcCard, boolean randomSpot, boolean toBottom)
    {
        this.randomSpot = false;
        cardOffset = false;
        card = srcCard.makeStatEquivalentCopy();
        duration = 1.5F;
        this.randomSpot = randomSpot;
        card.target_x = MathUtils.random((float)Settings.WIDTH * 0.1F, (float)Settings.WIDTH * 0.9F);
        card.target_y = MathUtils.random((float)Settings.HEIGHT * 0.8F, (float)Settings.HEIGHT * 0.2F);
        AbstractDungeon.effectsQueue.add(new CardPoofEffect(card.target_x, card.target_y));
        card.drawScale = 0.01F;
        card.targetDrawScale = 1.0F;
        if(toBottom)
            AbstractDungeon.player.drawPile.addToBottom(srcCard);
        else
        if(randomSpot)
            AbstractDungeon.player.drawPile.addToRandomSpot(srcCard);
        else
            AbstractDungeon.player.drawPile.addToTop(srcCard);
    }

    private void identifySpawnLocation(float x, float y)
    {
        int effectCount = 0;
        if(cardOffset)
            effectCount = 1;
        Iterator iterator = AbstractDungeon.effectList.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractGameEffect e = (AbstractGameEffect)iterator.next();
            if(e instanceof ShowCardAndAddToDrawPileEffect)
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
        card.update();
        if(duration < 0.0F)
        {
            isDone = true;
            card.shrink();
            AbstractDungeon.getCurrRoom().souls.onToDeck(card, randomSpot, true);
        }
    }

    public void render(SpriteBatch sb)
    {
        if(!isDone)
            card.render(sb);
    }

    public void dispose()
    {
    }

    private static final float EFFECT_DUR = 1.5F;
    private AbstractCard card;
    private static final float PADDING;
    private boolean randomSpot;
    private boolean cardOffset;

    static 
    {
        PADDING = 30F * Settings.scale;
    }
}
