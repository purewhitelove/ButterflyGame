// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ExhaustAction.java

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.select.HandCardSelectScreen;
import java.util.ArrayList;
import java.util.Iterator;

public class ExhaustAction extends AbstractGameAction
{

    public ExhaustAction(int amount, boolean isRandom, boolean anyNumber, boolean canPickZero)
    {
        this.anyNumber = anyNumber;
        p = AbstractDungeon.player;
        this.canPickZero = canPickZero;
        this.isRandom = isRandom;
        this.amount = amount;
        duration = startDuration = Settings.ACTION_DUR_FAST;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.EXHAUST;
    }

    public ExhaustAction(AbstractCreature target, AbstractCreature source, int amount, boolean isRandom, boolean anyNumber)
    {
        this(amount, isRandom, anyNumber);
        this.target = target;
        this.source = source;
    }

    public ExhaustAction(AbstractCreature target, AbstractCreature source, int amount, boolean isRandom)
    {
        this(amount, isRandom, false, false);
        this.target = target;
        this.source = source;
    }

    public ExhaustAction(AbstractCreature target, AbstractCreature source, int amount, boolean isRandom, boolean anyNumber, boolean canPickZero)
    {
        this(amount, isRandom, anyNumber, canPickZero);
        this.target = target;
        this.source = source;
    }

    public ExhaustAction(boolean isRandom, boolean anyNumber, boolean canPickZero)
    {
        this(99, isRandom, anyNumber, canPickZero);
    }

    public ExhaustAction(int amount, boolean canPickZero)
    {
        this(amount, false, false, canPickZero);
    }

    public ExhaustAction(int amount, boolean isRandom, boolean anyNumber)
    {
        this(amount, isRandom, anyNumber, false);
    }

    public ExhaustAction(int amount, boolean isRandom, boolean anyNumber, boolean canPickZero, float duration)
    {
        this(amount, isRandom, anyNumber, canPickZero);
        this.duration = startDuration = duration;
    }

    public void update()
    {
        if(duration == startDuration)
        {
            if(p.hand.size() == 0)
            {
                isDone = true;
                return;
            }
            if(!anyNumber && p.hand.size() <= amount)
            {
                amount = p.hand.size();
                numExhausted = amount;
                int tmp = p.hand.size();
                for(int i = 0; i < tmp; i++)
                {
                    AbstractCard c = p.hand.getTopCard();
                    p.hand.moveToExhaustPile(c);
                }

                CardCrawlGame.dungeon.checkForPactAchievement();
                return;
            }
            if(isRandom)
            {
                for(int i = 0; i < amount; i++)
                    p.hand.moveToExhaustPile(p.hand.getRandomCard(AbstractDungeon.cardRandomRng));

                CardCrawlGame.dungeon.checkForPactAchievement();
            } else
            {
                numExhausted = amount;
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], amount, anyNumber, canPickZero);
                tickDuration();
                return;
            }
        }
        if(!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            AbstractCard c;
            for(Iterator iterator = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator(); iterator.hasNext(); p.hand.moveToExhaustPile(c))
                c = (AbstractCard)iterator.next();

            CardCrawlGame.dungeon.checkForPactAchievement();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        tickDuration();
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private AbstractPlayer p;
    private boolean isRandom;
    private boolean anyNumber;
    private boolean canPickZero;
    public static int numExhausted;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
        TEXT = uiStrings.TEXT;
    }
}
