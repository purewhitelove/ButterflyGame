// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BlockPerNonAttackAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;
import java.util.Iterator;

public class BlockPerNonAttackAction extends AbstractGameAction
{

    public BlockPerNonAttackAction(int blockAmount)
    {
        blockPerCard = blockAmount;
        setValues(AbstractDungeon.player, AbstractDungeon.player);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.BLOCK;
    }

    public void update()
    {
        ArrayList cardsToExhaust = new ArrayList();
        Iterator iterator = AbstractDungeon.player.hand.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.type != com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK)
                cardsToExhaust.add(c);
        } while(true);
        for(Iterator iterator1 = cardsToExhaust.iterator(); iterator1.hasNext(); addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, blockPerCard)))
        {
            AbstractCard c = (AbstractCard)iterator1.next();
        }

        AbstractCard c;
        for(Iterator iterator2 = cardsToExhaust.iterator(); iterator2.hasNext(); addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand)))
            c = (AbstractCard)iterator2.next();

        isDone = true;
    }

    private int blockPerCard;
}
