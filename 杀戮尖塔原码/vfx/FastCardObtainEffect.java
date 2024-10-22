// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FastCardObtainEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Omamori;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect

public class FastCardObtainEffect extends AbstractGameEffect
{

    public FastCardObtainEffect(AbstractCard card, float x, float y)
    {
        if(card.color == com.megacrit.cardcrawl.cards.AbstractCard.CardColor.CURSE && AbstractDungeon.player.hasRelic("Omamori") && AbstractDungeon.player.getRelic("Omamori").counter != 0)
        {
            ((Omamori)AbstractDungeon.player.getRelic("Omamori")).use();
            duration = 0.0F;
            isDone = true;
        }
        CardHelper.obtain(card.cardID, card.rarity, card.color);
        this.card = card;
        duration = 0.01F;
        card.current_x = x;
        card.current_y = y;
        CardCrawlGame.sound.play("CARD_SELECT");
    }

    public void update()
    {
        if(isDone)
            return;
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

    private AbstractCard card;
}
