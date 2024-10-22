// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CardFlashVfx.java

package com.megacrit.cardcrawl.vfx.cardManip;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class CardFlashVfx extends AbstractGameEffect
{

    public CardFlashVfx(AbstractCard card, boolean isSuper)
    {
        this(card, new Color(1.0F, 0.8F, 0.2F, 0.0F), isSuper);
    }

    public CardFlashVfx(AbstractCard card, Color c, boolean isSuper)
    {
        yScale = 0.0F;
        this.isSuper = false;
        this.card = card;
        this.isSuper = isSuper;
        duration = 0.5F;
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardType[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardType = new int[com.megacrit.cardcrawl.cards.AbstractCard.CardType.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardType[com.megacrit.cardcrawl.cards.AbstractCard.CardType.POWER.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardType[com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
            }
        }

        if(isSuper)
            img = ImageMaster.CARD_FLASH_VFX;
        else
            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardType[card.type.ordinal()])
            {
            case 1: // '\001'
                img = ImageMaster.CARD_POWER_BG_SILHOUETTE;
                break;

            case 2: // '\002'
                img = ImageMaster.CARD_ATTACK_BG_SILHOUETTE;
                break;

            default:
                img = ImageMaster.CARD_SKILL_BG_SILHOUETTE;
                break;
            }
        color = c;
    }

    public CardFlashVfx(AbstractCard card)
    {
        this(card, new Color(1.0F, 0.8F, 0.2F, 0.0F), false);
    }

    public CardFlashVfx(AbstractCard card, Color c)
    {
        yScale = 0.0F;
        isSuper = false;
        this.card = card;
        duration = 0.5F;
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardType[card.type.ordinal()])
        {
        case 1: // '\001'
            img = ImageMaster.CARD_POWER_BG_SILHOUETTE;
            break;

        case 2: // '\002'
            img = ImageMaster.CARD_ATTACK_BG_SILHOUETTE;
            break;

        default:
            img = ImageMaster.CARD_SKILL_BG_SILHOUETTE;
            break;
        }
        color = c;
        isSuper = false;
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
        else
            yScale = Interpolation.bounceIn.apply(1.2F, 1.1F, duration * 2.0F);
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        color.a = duration;
        sb.setColor(color);
        if(isSuper)
        {
            sb.draw(img, (card.current_x + img.offsetX) - (float)img.originalWidth / 2.0F, (card.current_y + img.offsetY) - (float)img.originalHeight / 2.0F, (float)img.originalWidth / 2.0F - img.offsetX, (float)img.originalHeight / 2.0F - img.offsetY, img.packedWidth, img.packedHeight, card.drawScale * ((yScale + 1.0F) * 0.52F) * Settings.scale, card.drawScale * ((yScale + 1.0F) * 0.53F) * Settings.scale, card.angle);
            sb.draw(img, (card.current_x + img.offsetX) - (float)img.originalWidth / 2.0F, (card.current_y + img.offsetY) - (float)img.originalHeight / 2.0F, (float)img.originalWidth / 2.0F - img.offsetX, (float)img.originalHeight / 2.0F - img.offsetY, img.packedWidth, img.packedHeight, card.drawScale * ((yScale + 1.0F) * 0.55F) * Settings.scale, card.drawScale * ((yScale + 1.0F) * 0.57F) * Settings.scale, card.angle);
            sb.draw(img, (card.current_x + img.offsetX) - (float)img.originalWidth / 2.0F, (card.current_y + img.offsetY) - (float)img.originalHeight / 2.0F, (float)img.originalWidth / 2.0F - img.offsetX, (float)img.originalHeight / 2.0F - img.offsetY, img.packedWidth, img.packedHeight, card.drawScale * ((yScale + 1.0F) * 0.58F) * Settings.scale, card.drawScale * ((yScale + 1.0F) * 0.6F) * Settings.scale, card.angle);
        } else
        {
            sb.draw(img, (card.current_x + img.offsetX) - (float)img.originalWidth / 2.0F, (card.current_y + img.offsetY) - (float)img.originalHeight / 2.0F, (float)img.originalWidth / 2.0F - img.offsetX, (float)img.originalHeight / 2.0F - img.offsetY, img.packedWidth, img.packedHeight, card.drawScale * ((yScale + 1.0F) * 0.52F) * Settings.scale, card.drawScale * ((yScale + 1.0F) * 0.52F) * Settings.scale, card.angle);
            sb.draw(img, (card.current_x + img.offsetX) - (float)img.originalWidth / 2.0F, (card.current_y + img.offsetY) - (float)img.originalHeight / 2.0F, (float)img.originalWidth / 2.0F - img.offsetX, (float)img.originalHeight / 2.0F - img.offsetY, img.packedWidth, img.packedHeight, card.drawScale * ((yScale + 1.0F) * 0.55F) * Settings.scale, card.drawScale * ((yScale + 1.0F) * 0.55F) * Settings.scale, card.angle);
            sb.draw(img, (card.current_x + img.offsetX) - (float)img.originalWidth / 2.0F, (card.current_y + img.offsetY) - (float)img.originalHeight / 2.0F, (float)img.originalWidth / 2.0F - img.offsetX, (float)img.originalHeight / 2.0F - img.offsetY, img.packedWidth, img.packedHeight, card.drawScale * ((yScale + 1.0F) * 0.58F) * Settings.scale, card.drawScale * ((yScale + 1.0F) * 0.58F) * Settings.scale, card.angle);
        }
        sb.setBlendFunction(770, 771);
    }

    public void dispose()
    {
    }

    private AbstractCard card;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private float yScale;
    private boolean isSuper;
}
