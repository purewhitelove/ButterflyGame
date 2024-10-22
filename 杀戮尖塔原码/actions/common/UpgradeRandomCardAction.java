// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UpgradeRandomCardAction.java

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;
import java.util.Iterator;

public class UpgradeRandomCardAction extends AbstractGameAction
{

    public UpgradeRandomCardAction()
    {
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION;
        p = AbstractDungeon.player;
        duration = Settings.ACTION_DUR_FAST;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_FAST)
        {
            if(p.hand.group.size() <= 0)
            {
                isDone = true;
                return;
            }
            CardGroup upgradeable = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);
            Iterator iterator = p.hand.group.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractCard c = (AbstractCard)iterator.next();
                if(c.canUpgrade() && c.type != com.megacrit.cardcrawl.cards.AbstractCard.CardType.STATUS)
                    upgradeable.addToTop(c);
            } while(true);
            if(upgradeable.size() > 0)
            {
                upgradeable.shuffle();
                ((AbstractCard)upgradeable.group.get(0)).upgrade();
                ((AbstractCard)upgradeable.group.get(0)).superFlash();
                ((AbstractCard)upgradeable.group.get(0)).applyPowers();
            }
            isDone = true;
            return;
        } else
        {
            tickDuration();
            return;
        }
    }

    private AbstractPlayer p;
}
