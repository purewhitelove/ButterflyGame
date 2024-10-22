// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ScrapeAction.java

package com.megacrit.cardcrawl.actions.defect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @deprecated Class ScrapeAction is deprecated
 */

public class ScrapeAction extends AbstractGameAction
{

    public ScrapeAction(AbstractCreature source, int amount, boolean endTurnDraw)
    {
        shuffleCheck = false;
        if(endTurnDraw)
            AbstractDungeon.topLevelEffects.add(new PlayerTurnEffect());
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
        if(Settings.FAST_MODE)
            duration = Settings.ACTION_DUR_XFAST;
        else
            duration = Settings.ACTION_DUR_FASTER;
    }

    public ScrapeAction(AbstractCreature source, int amount)
    {
        this(source, amount, false);
    }

    public void update()
    {
        if(amount <= 0)
        {
            isDone = true;
            return;
        }
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
        if(!shuffleCheck)
        {
            if(amount > deckSize)
            {
                int tmp = amount - deckSize;
                addToTop(new ScrapeAction(AbstractDungeon.player, tmp));
                addToTop(new EmptyDeckShuffleAction());
                if(deckSize != 0)
                    addToTop(new ScrapeAction(AbstractDungeon.player, deckSize));
                amount = 0;
                isDone = true;
            }
            shuffleCheck = true;
        }
        duration -= Gdx.graphics.getDeltaTime();
        if(amount != 0 && duration < 0.0F)
        {
            if(Settings.FAST_MODE)
                duration = Settings.ACTION_DUR_XFAST;
            else
                duration = Settings.ACTION_DUR_FASTER;
            amount--;
            if(!AbstractDungeon.player.drawPile.isEmpty())
            {
                scrapedCards.add(AbstractDungeon.player.drawPile.getTopCard());
                AbstractDungeon.player.draw();
                AbstractDungeon.player.hand.refreshHandLayout();
            } else
            {
                logger.warn((new StringBuilder()).append("Player attempted to draw from an empty drawpile mid-DrawAction?MASTER DECK: ").append(AbstractDungeon.player.masterDeck.getCardNames()).toString());
                isDone = true;
            }
            if(amount == 0)
                isDone = true;
        }
    }

    private boolean shuffleCheck;
    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/actions/common/DrawCardAction.getName());
    public static ArrayList scrapedCards = new ArrayList();

}
