// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DEPRECATEDExperiencedAction.java

package com.megacrit.cardcrawl.actions.deprecated;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;
import java.util.Iterator;

public class DEPRECATEDExperiencedAction extends AbstractGameAction
{

    public DEPRECATEDExperiencedAction(int blockPerCard, AbstractCard card)
    {
        this.blockPerCard = blockPerCard;
        this.card = card;
    }

    public void update()
    {
        int upgradeCount = 0;
        Iterator iterator = AbstractDungeon.player.hand.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.upgraded && c != card)
                upgradeCount++;
        } while(true);
        addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, upgradeCount * blockPerCard));
        isDone = true;
    }

    private int blockPerCard;
    private AbstractCard card;
}
