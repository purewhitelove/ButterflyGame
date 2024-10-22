// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PutOnBottomOfDeckAction.java

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.select.HandCardSelectScreen;
import java.util.ArrayList;
import java.util.Iterator;

public class PutOnBottomOfDeckAction extends AbstractGameAction
{

    public PutOnBottomOfDeckAction(AbstractCreature target, AbstractCreature source, int amount, boolean isRandom)
    {
        this.target = target;
        p = (AbstractPlayer)target;
        setValues(target, source, amount);
        duration = Settings.ACTION_DUR_FAST;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_FAST)
        {
            if(p.hand.size() < amount)
                amount = p.hand.size();
            if(isRandom)
            {
                for(int i = 0; i < amount; i++)
                    p.hand.moveToBottomOfDeck(p.hand.getRandomCard(AbstractDungeon.cardRandomRng));

            } else
            {
                if(p.hand.group.size() > amount)
                {
                    numPlaced = amount;
                    AbstractDungeon.handCardSelectScreen.open("put on the bottom of your draw pile", amount, false);
                    tickDuration();
                    return;
                }
                for(int i = 0; i < p.hand.size(); i++)
                    p.hand.moveToBottomOfDeck(p.hand.getRandomCard(AbstractDungeon.cardRandomRng));

            }
        }
        if(!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            AbstractCard c;
            for(Iterator iterator = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator(); iterator.hasNext(); p.hand.moveToBottomOfDeck(c))
                c = (AbstractCard)iterator.next();

            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        tickDuration();
    }

    private AbstractPlayer p;
    private boolean isRandom;
    public static int numPlaced;
}
