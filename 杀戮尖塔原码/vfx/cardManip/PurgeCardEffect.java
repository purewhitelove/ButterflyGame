// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PurgeCardEffect.java

package com.megacrit.cardcrawl.vfx.cardManip;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.DamageImpactCurvyEffect;
import java.util.ArrayList;
import java.util.Iterator;

public class PurgeCardEffect extends AbstractGameEffect
{

    public PurgeCardEffect(AbstractCard card)
    {
        this(card, (float)Settings.WIDTH - 96F * Settings.scale, (float)Settings.HEIGHT - 32F * Settings.scale);
    }

    public PurgeCardEffect(AbstractCard card, float x, float y)
    {
        this.card = card;
        startingDuration = 2.0F;
        duration = startingDuration;
        identifySpawnLocation(x, y);
        card.drawScale = 0.01F;
        card.targetDrawScale = 1.0F;
        CardCrawlGame.sound.play("CARD_BURN");
        initializeVfx();
    }

    private void initializeVfx()
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[];
            static final int $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardColor[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardColor = new int[com.megacrit.cardcrawl.cards.AbstractCard.CardColor.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardColor[com.megacrit.cardcrawl.cards.AbstractCard.CardColor.BLUE.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardColor[com.megacrit.cardcrawl.cards.AbstractCard.CardColor.COLORLESS.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardColor[com.megacrit.cardcrawl.cards.AbstractCard.CardColor.GREEN.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardColor[com.megacrit.cardcrawl.cards.AbstractCard.CardColor.RED.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardColor[com.megacrit.cardcrawl.cards.AbstractCard.CardColor.CURSE.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
                $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity = new int[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror5) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.RARE.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror6) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.BASIC.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror7) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.COMMON.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror8) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.CURSE.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror9) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.SPECIAL.ordinal()] = 6;
                }
                catch(NoSuchFieldError nosuchfielderror10) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardRarity[card.rarity.ordinal()])
        {
        case 1: // '\001'
            rarityColor = new Color(0.2F, 0.8F, 0.8F, 0.01F);
            break;

        case 2: // '\002'
            rarityColor = new Color(0.8F, 0.7F, 0.2F, 0.01F);
            break;

        case 3: // '\003'
        case 4: // '\004'
        case 5: // '\005'
        case 6: // '\006'
        default:
            rarityColor = new Color(0.6F, 0.6F, 0.6F, 0.01F);
            break;
        }
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardColor[card.color.ordinal()])
        {
        case 1: // '\001'
            color = new Color(0.1F, 0.4F, 0.7F, 0.01F);
            break;

        case 2: // '\002'
            color = new Color(0.4F, 0.4F, 0.4F, 0.01F);
            break;

        case 3: // '\003'
            color = new Color(0.2F, 0.7F, 0.2F, 0.01F);
            break;

        case 4: // '\004'
            color = new Color(0.9F, 0.3F, 0.2F, 0.01F);
            break;

        case 5: // '\005'
        default:
            color = new Color(0.2F, 0.15F, 0.2F, 0.01F);
            break;
        }
        scale = Settings.scale;
        scaleY = Settings.scale;
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
            if(e instanceof PurgeCardEffect)
                effectCount++;
        } while(true);
        iterator = AbstractDungeon.topLevelEffects.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractGameEffect e = (AbstractGameEffect)iterator.next();
            if(e instanceof PurgeCardEffect)
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
        if(duration < 0.5F)
        {
            if(!card.fadingOut)
            {
                card.fadingOut = true;
                if(!Settings.DISABLE_EFFECTS)
                {
                    for(int i = 0; i < 16; i++)
                        AbstractDungeon.topLevelEffectsQueue.add(new DamageImpactCurvyEffect(card.current_x, card.current_y, color, false));

                    for(int i = 0; i < 8; i++)
                        AbstractDungeon.effectsQueue.add(new DamageImpactCurvyEffect(card.current_x, card.current_y, rarityColor, false));

                }
            }
            updateVfx();
        }
        card.update();
        if(duration < 0.0F)
            isDone = true;
    }

    private void updateVfx()
    {
        color.a = MathHelper.fadeLerpSnap(color.a, 0.5F);
        rarityColor.a = color.a;
        scale = Interpolation.swingOut.apply(1.6F, 1.0F, duration * 2.0F) * Settings.scale;
        scaleY = Interpolation.fade.apply(0.005F, 1.0F, duration * 2.0F) * Settings.scale;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(Color.WHITE);
        card.render(sb);
        renderVfx(sb);
    }

    private void renderVfx(SpriteBatch sb)
    {
        sb.setColor(color);
        com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img = ImageMaster.CARD_POWER_BG_SILHOUETTE;
        sb.draw(img, (card.current_x + img.offsetX) - (float)img.originalWidth / 2.0F, (card.current_y + img.offsetY) - (float)img.originalHeight / 2.0F, (float)img.originalWidth / 2.0F - img.offsetX, (float)img.originalHeight / 2.0F - img.offsetY, img.packedWidth, img.packedHeight, scale * MathUtils.random(0.95F, 1.05F), scaleY * MathUtils.random(0.95F, 1.05F), rotation);
        sb.setBlendFunction(770, 1);
        sb.setColor(rarityColor);
        img = ImageMaster.CARD_SUPER_SHADOW;
        sb.draw(img, (card.current_x + img.offsetX) - (float)img.originalWidth / 2.0F, (card.current_y + img.offsetY) - (float)img.originalHeight / 2.0F, (float)img.originalWidth / 2.0F - img.offsetX, (float)img.originalHeight / 2.0F - img.offsetY, img.packedWidth, img.packedHeight, scale * 0.75F * MathUtils.random(0.95F, 1.05F), scaleY * 0.75F * MathUtils.random(0.95F, 1.05F), rotation);
        sb.draw(img, (card.current_x + img.offsetX) - (float)img.originalWidth / 2.0F, (card.current_y + img.offsetY) - (float)img.originalHeight / 2.0F, (float)img.originalWidth / 2.0F - img.offsetX, (float)img.originalHeight / 2.0F - img.offsetY, img.packedWidth, img.packedHeight, scale * 0.5F * MathUtils.random(0.95F, 1.05F), scaleY * 0.5F * MathUtils.random(0.95F, 1.05F), rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private AbstractCard card;
    private static final float PADDING;
    private float scaleY;
    private Color rarityColor;

    static 
    {
        PADDING = 30F * Settings.scale;
    }
}
