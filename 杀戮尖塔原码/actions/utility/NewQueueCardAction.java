// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NewQueueCardAction.java

package com.megacrit.cardcrawl.actions.utility;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NewQueueCardAction extends AbstractGameAction
{

    public NewQueueCardAction(AbstractCard card, AbstractCreature target, boolean immediateCard, boolean autoplayCard)
    {
        this.card = card;
        this.target = target;
        this.immediateCard = immediateCard;
        this.autoplayCard = autoplayCard;
        randomTarget = false;
    }

    public NewQueueCardAction(AbstractCard card, AbstractCreature target, boolean immediateCard)
    {
        this(card, target, immediateCard, false);
    }

    public NewQueueCardAction(AbstractCard card, AbstractCreature target)
    {
        this(card, target, false);
    }

    public NewQueueCardAction(AbstractCard card, boolean randomTarget, boolean immediateCard, boolean autoplayCard)
    {
        this(card, ((AbstractCreature) (null)), immediateCard, autoplayCard);
        this.randomTarget = randomTarget;
    }

    public NewQueueCardAction(AbstractCard card, boolean randomTarget, boolean immediateCard)
    {
        this(card, randomTarget, immediateCard, false);
    }

    public NewQueueCardAction(AbstractCard card, boolean randomTarget)
    {
        this(card, randomTarget, false);
    }

    public NewQueueCardAction()
    {
        this(null, ((AbstractCreature) (null)));
    }

    public void update()
    {
        if(card == null)
        {
            if(!queueContainsEndTurnCard())
                AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem());
        } else
        if(!queueContains(card))
            if(randomTarget)
            {
                AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(card, true, EnergyPanel.getCurrentEnergy(), false, autoplayCard), immediateCard);
            } else
            {
                if(!(target instanceof AbstractMonster) && target != null)
                {
                    logger.info("WARNING: NewQueueCardAction does not contain an AbstractMonster!");
                    isDone = true;
                    return;
                }
                AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(card, (AbstractMonster)target, EnergyPanel.getCurrentEnergy(), false, autoplayCard), immediateCard);
            }
        isDone = true;
    }

    private boolean queueContains(AbstractCard card)
    {
        for(Iterator iterator = AbstractDungeon.actionManager.cardQueue.iterator(); iterator.hasNext();)
        {
            CardQueueItem i = (CardQueueItem)iterator.next();
            if(i.card == card)
                return true;
        }

        return false;
    }

    private boolean queueContainsEndTurnCard()
    {
        for(Iterator iterator = AbstractDungeon.actionManager.cardQueue.iterator(); iterator.hasNext();)
        {
            CardQueueItem i = (CardQueueItem)iterator.next();
            if(i.card == null)
                return true;
        }

        return false;
    }

    private AbstractCard card;
    private boolean randomTarget;
    private boolean immediateCard;
    private boolean autoplayCard;
    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/actions/utility/NewQueueCardAction.getName());

}
