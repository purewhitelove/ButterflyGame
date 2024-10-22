// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ExhaustCardEffect.java

package com.megacrit.cardcrawl.vfx.cardManip;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.*;
import java.util.ArrayList;

public class ExhaustCardEffect extends AbstractGameEffect
{

    public ExhaustCardEffect(AbstractCard c)
    {
        duration = 1.0F;
        this.c = c;
    }

    public void update()
    {
        if(duration == 1.0F)
        {
            CardCrawlGame.sound.play("CARD_EXHAUST", 0.2F);
            for(int i = 0; i < 90; i++)
                AbstractDungeon.effectsQueue.add(new ExhaustBlurEffect(c.current_x, c.current_y));

            for(int i = 0; i < 50; i++)
                AbstractDungeon.effectsQueue.add(new ExhaustEmberEffect(c.current_x, c.current_y));

        }
        duration -= Gdx.graphics.getDeltaTime();
        if(!c.fadingOut && duration < 0.7F && !AbstractDungeon.player.hand.contains(c))
            c.fadingOut = true;
        if(duration < 0.0F)
        {
            isDone = true;
            c.resetAttributes();
        }
    }

    public void render(SpriteBatch sb)
    {
        c.render(sb);
    }

    public void dispose()
    {
    }

    private AbstractCard c;
    private static final float DUR = 1F;
}
