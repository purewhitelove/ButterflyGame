// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ApotheosisAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;
import java.util.Iterator;

public class ApotheosisAction extends AbstractGameAction
{

    public ApotheosisAction()
    {
        duration = Settings.ACTION_DUR_MED;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.WAIT;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_MED)
        {
            AbstractPlayer p = AbstractDungeon.player;
            upgradeAllCardsInGroup(p.hand);
            upgradeAllCardsInGroup(p.drawPile);
            upgradeAllCardsInGroup(p.discardPile);
            upgradeAllCardsInGroup(p.exhaustPile);
            isDone = true;
        }
    }

    private void upgradeAllCardsInGroup(CardGroup cardGroup)
    {
        Iterator iterator = cardGroup.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.canUpgrade())
            {
                if(cardGroup.type == com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.HAND)
                    c.superFlash();
                c.upgrade();
                c.applyPowers();
            }
        } while(true);
    }
}
