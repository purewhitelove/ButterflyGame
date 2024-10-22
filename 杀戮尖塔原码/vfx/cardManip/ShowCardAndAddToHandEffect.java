// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ShowCardAndAddToHandEffect.java

package com.megacrit.cardcrawl.vfx.cardManip;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.CorruptionPower;
import com.megacrit.cardcrawl.powers.watcher.MasterRealityPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.CardPoofEffect;
import java.util.ArrayList;
import java.util.Iterator;

public class ShowCardAndAddToHandEffect extends AbstractGameEffect
{

    public ShowCardAndAddToHandEffect(AbstractCard card, float offsetX, float offsetY)
    {
        this.card = card;
        UnlockTracker.markCardAsSeen(card.cardID);
        card.current_x = (float)Settings.WIDTH / 2.0F;
        card.current_y = (float)Settings.HEIGHT / 2.0F;
        card.target_x = offsetX;
        card.target_y = offsetY;
        duration = 0.8F;
        card.drawScale = 0.75F;
        card.targetDrawScale = 0.75F;
        card.transparency = 0.01F;
        card.targetTransparency = 1.0F;
        card.fadingOut = false;
        playCardObtainSfx();
        if(card.type != com.megacrit.cardcrawl.cards.AbstractCard.CardType.CURSE && card.type != com.megacrit.cardcrawl.cards.AbstractCard.CardType.STATUS && AbstractDungeon.player.hasPower("MasterRealityPower"))
            card.upgrade();
        AbstractDungeon.player.hand.addToHand(card);
        card.triggerWhenCopied();
        AbstractDungeon.player.hand.refreshHandLayout();
        AbstractDungeon.player.hand.applyPowers();
        AbstractDungeon.player.onCardDrawOrDiscard();
        if(AbstractDungeon.player.hasPower("Corruption") && card.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL)
            card.setCostForTurn(-9);
    }

    public ShowCardAndAddToHandEffect(AbstractCard card)
    {
        this.card = card;
        identifySpawnLocation((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F);
        duration = 0.8F;
        card.drawScale = 0.75F;
        card.targetDrawScale = 0.75F;
        card.transparency = 0.01F;
        card.targetTransparency = 1.0F;
        card.fadingOut = false;
        if(card.type != com.megacrit.cardcrawl.cards.AbstractCard.CardType.CURSE && card.type != com.megacrit.cardcrawl.cards.AbstractCard.CardType.STATUS && AbstractDungeon.player.hasPower("MasterRealityPower"))
            card.upgrade();
        AbstractDungeon.player.hand.addToHand(card);
        card.triggerWhenCopied();
        AbstractDungeon.player.hand.refreshHandLayout();
        AbstractDungeon.player.hand.applyPowers();
        AbstractDungeon.player.onCardDrawOrDiscard();
        if(AbstractDungeon.player.hasPower("Corruption") && card.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL)
            card.setCostForTurn(-9);
    }

    private void playCardObtainSfx()
    {
        int effectCount = 0;
        Iterator iterator = AbstractDungeon.effectList.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractGameEffect e = (AbstractGameEffect)iterator.next();
            if(e instanceof ShowCardAndAddToHandEffect)
                effectCount++;
        } while(true);
        if(effectCount < 2)
            CardCrawlGame.sound.play("CARD_OBTAIN");
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
            if(e instanceof ShowCardAndAddToHandEffect)
                effectCount++;
        } while(true);
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
        card.current_x = card.target_x;
        card.current_y = card.target_y - 200F * Settings.scale;
        AbstractDungeon.effectsQueue.add(new CardPoofEffect(card.target_x, card.target_y));
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
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

    private static final float EFFECT_DUR = 0.8F;
    private AbstractCard card;
    private static final float PADDING;

    static 
    {
        PADDING = 25F * Settings.scale;
    }
}
