// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FastDrawCardAction.java

package com.megacrit.cardcrawl.actions.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.NoDrawPower;
import com.megacrit.cardcrawl.vfx.PlayerTurnEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.actions.common:
//            EmptyDeckShuffleAction

public class FastDrawCardAction extends AbstractGameAction
{

    public FastDrawCardAction(AbstractCreature source, int amount, boolean endTurnDraw)
    {
        shuffleCheck = false;
        if(endTurnDraw)
            AbstractDungeon.effectList.add(new PlayerTurnEffect());
        else
        if(AbstractDungeon.player.hasPower("No Draw"))
        {
            AbstractDungeon.player.getPower("No Draw").flash();
            setValues(AbstractDungeon.player, source, amount);
            isDone = true;
            duration = 0.0F;
            actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.WAIT;
            return;
        }
        setValues(AbstractDungeon.player, source, amount);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DRAW;
        duration = Settings.ACTION_DUR_XFAST;
    }

    public FastDrawCardAction(AbstractCreature source, int amount)
    {
        this(source, amount, false);
    }

    public void update()
    {
        int deckSize = AbstractDungeon.player.drawPile.size();
        int discardSize = AbstractDungeon.player.discardPile.size();
        if(SoulGroup.isActive())
            return;
        if(deckSize + discardSize == 0)
        {
            isDone = true;
            return;
        }
        if(!shuffleCheck)
        {
            if(amount + AbstractDungeon.player.hand.size() > 10)
            {
                int handSizeAndDraw = 10 - (amount + AbstractDungeon.player.hand.size());
                amount += handSizeAndDraw;
                AbstractDungeon.player.createHandIsFullDialog();
            }
            if(amount > deckSize)
            {
                int tmp = amount - deckSize;
                addToTop(new FastDrawCardAction(AbstractDungeon.player, tmp));
                addToTop(new EmptyDeckShuffleAction());
                if(deckSize != 0)
                    addToTop(new FastDrawCardAction(AbstractDungeon.player, deckSize));
                amount = 0;
                isDone = true;
            }
            shuffleCheck = true;
        }
        duration -= Gdx.graphics.getDeltaTime();
        if(amount != 0 && duration < 0.0F)
        {
            duration = Settings.ACTION_DUR_XFAST;
            amount--;
            AbstractDungeon.player.draw();
            AbstractDungeon.player.hand.refreshHandLayout();
            if(amount == 0)
                isDone = true;
        }
    }

    private boolean shuffleCheck;
}
