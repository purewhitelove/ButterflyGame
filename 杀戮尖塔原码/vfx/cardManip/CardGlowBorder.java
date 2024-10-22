// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CardGlowBorder.java

package com.megacrit.cardcrawl.vfx.cardManip;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class CardGlowBorder extends AbstractGameEffect
{

    public CardGlowBorder(AbstractCard card)
    {
        this(card, Color.valueOf("30c8dcff"));
    }

    public CardGlowBorder(AbstractCard card, Color gColor)
    {
        this.card = card;
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
        duration = 1.2F;
        if(AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
            color = gColor.cpy();
        else
            color = Color.GREEN.cpy();
    }

    public void update()
    {
        scale = (1.0F + Interpolation.pow2Out.apply(0.03F, 0.11F, 1.0F - duration)) * card.drawScale * Settings.scale;
        color.a = duration / 2.0F;
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
        {
            isDone = true;
            duration = 0.0F;
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.draw(img, (card.current_x + img.offsetX) - (float)img.originalWidth / 2.0F, (card.current_y + img.offsetY) - (float)img.originalHeight / 2.0F, (float)img.originalWidth / 2.0F - img.offsetX, (float)img.originalHeight / 2.0F - img.offsetY, img.packedWidth, img.packedHeight, scale, scale, card.angle);
    }

    public void dispose()
    {
    }

    private AbstractCard card;
    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img;
    private float scale;
}
