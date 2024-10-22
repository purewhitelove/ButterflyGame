// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DiscardAction.java

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.screens.select.HandCardSelectScreen;
import java.util.ArrayList;
import java.util.Iterator;

public class DiscardAction extends AbstractGameAction
{

    public DiscardAction(AbstractCreature target, AbstractCreature source, int amount, boolean isRandom)
    {
        this(target, source, amount, isRandom, false);
    }

    public DiscardAction(AbstractCreature target, AbstractCreature source, int amount, boolean isRandom, boolean endTurn)
    {
        p = (AbstractPlayer)target;
        this.isRandom = isRandom;
        setValues(target, source, amount);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DISCARD;
        this.endTurn = endTurn;
        duration = DURATION;
    }

    public void update()
    {
        if(duration == DURATION)
        {
            if(AbstractDungeon.getMonsters().areMonstersBasicallyDead())
            {
                isDone = true;
                return;
            }
            if(p.hand.size() <= amount)
            {
                amount = p.hand.size();
                int tmp = p.hand.size();
                for(int i = 0; i < tmp; i++)
                {
                    AbstractCard c = p.hand.getTopCard();
                    p.hand.moveToDiscardPile(c);
                    if(!endTurn)
                        c.triggerOnManualDiscard();
                    GameActionManager.incrementDiscard(endTurn);
                }

                AbstractDungeon.player.hand.applyPowers();
                tickDuration();
                return;
            }
            if(isRandom)
            {
                for(int i = 0; i < amount; i++)
                {
                    AbstractCard c = p.hand.getRandomCard(AbstractDungeon.cardRandomRng);
                    p.hand.moveToDiscardPile(c);
                    c.triggerOnManualDiscard();
                    GameActionManager.incrementDiscard(endTurn);
                }

            } else
            {
                if(amount < 0)
                {
                    AbstractDungeon.handCardSelectScreen.open(TEXT[0], 99, true, true);
                    AbstractDungeon.player.hand.applyPowers();
                    tickDuration();
                    return;
                }
                numDiscarded = amount;
                if(p.hand.size() > amount)
                    AbstractDungeon.handCardSelectScreen.open(TEXT[0], amount, false);
                AbstractDungeon.player.hand.applyPowers();
                tickDuration();
                return;
            }
        }
        if(!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            for(Iterator iterator = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator(); iterator.hasNext(); GameActionManager.incrementDiscard(endTurn))
            {
                AbstractCard c = (AbstractCard)iterator.next();
                p.hand.moveToDiscardPile(c);
                c.triggerOnManualDiscard();
            }

            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        tickDuration();
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private AbstractPlayer p;
    private boolean isRandom;
    private boolean endTurn;
    public static int numDiscarded;
    private static final float DURATION;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("DiscardAction");
        TEXT = uiStrings.TEXT;
        DURATION = Settings.ACTION_DUR_XFAST;
    }
}
