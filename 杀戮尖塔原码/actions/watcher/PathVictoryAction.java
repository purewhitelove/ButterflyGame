// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PathVictoryAction.java

package com.megacrit.cardcrawl.actions.watcher;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.NoDrawPower;

public class PathVictoryAction extends AbstractGameAction
{

    public PathVictoryAction()
    {
        if(AbstractDungeon.player.hasPower("No Draw"))
        {
            AbstractDungeon.player.getPower("No Draw").flash();
            setValues(AbstractDungeon.player, source, 1);
            isDone = true;
            duration = 0.0F;
            actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.WAIT;
            return;
        }
        setValues(AbstractDungeon.player, source, amount);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DRAW;
        if(Settings.FAST_MODE)
            duration = Settings.ACTION_DUR_XFAST;
        else
            duration = Settings.ACTION_DUR_FASTER;
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
        if(AbstractDungeon.player.hand.size() == 10)
        {
            AbstractDungeon.player.createHandIsFullDialog();
            isDone = true;
            return;
        }
        if(deckSize == 0 && discardSize != 0)
        {
            addToTop(new PathVictoryAction());
            addToTop(new EmptyDeckShuffleAction());
            isDone = true;
            return;
        }
        if(deckSize != 0)
        {
            AbstractCard c = AbstractDungeon.player.drawPile.getTopCard();
            c.setCostForTurn(0);
            AbstractDungeon.player.draw();
            AbstractDungeon.player.hand.refreshHandLayout();
            isDone = true;
            return;
        } else
        {
            return;
        }
    }
}
