// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ShowCardAndObtainEffect.java

package com.megacrit.cardcrawl.vfx.cardManip;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Omamori;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.CardPoofEffect;
import java.util.ArrayList;
import java.util.Iterator;

public class ShowCardAndObtainEffect extends AbstractGameEffect
{

    public ShowCardAndObtainEffect(AbstractCard card, float x, float y, boolean convergeCards)
    {
        if(card.color == com.megacrit.cardcrawl.cards.AbstractCard.CardColor.CURSE && AbstractDungeon.player.hasRelic("Omamori") && AbstractDungeon.player.getRelic("Omamori").counter != 0)
        {
            ((Omamori)AbstractDungeon.player.getRelic("Omamori")).use();
            duration = 0.0F;
            isDone = true;
            converge = convergeCards;
        }
        UnlockTracker.markCardAsSeen(card.cardID);
        CardHelper.obtain(card.cardID, card.rarity, card.color);
        this.card = card;
        if(Settings.FAST_MODE)
            duration = 0.5F;
        else
            duration = 2.0F;
        identifySpawnLocation(x, y);
        AbstractDungeon.effectsQueue.add(new CardPoofEffect(card.target_x, card.target_y));
        card.drawScale = 0.01F;
        card.targetDrawScale = 1.0F;
    }

    public ShowCardAndObtainEffect(AbstractCard card, float x, float y)
    {
        this(card, x, y, true);
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
            if(e instanceof ShowCardAndObtainEffect)
                effectCount++;
        } while(true);
        card.current_x = x;
        card.current_y = y;
        if(converge)
        {
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
        } else
        {
            card.target_x = card.current_x;
            card.target_y = card.current_y;
        }
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        card.update();
        if(duration < 0.0F)
        {
            AbstractRelic r;
            for(Iterator iterator = AbstractDungeon.player.relics.iterator(); iterator.hasNext(); r.onObtainCard(card))
                r = (AbstractRelic)iterator.next();

            isDone = true;
            card.shrink();
            AbstractDungeon.getCurrRoom().souls.obtain(card, true);
            AbstractRelic r;
            for(Iterator iterator1 = AbstractDungeon.player.relics.iterator(); iterator1.hasNext(); r.onMasterDeckChange())
                r = (AbstractRelic)iterator1.next();

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

    private static final float EFFECT_DUR = 2F;
    private static final float FAST_DUR = 0.5F;
    private AbstractCard card;
    private static final float PADDING;
    private boolean converge;

    static 
    {
        PADDING = 30F * Settings.scale;
    }
}
