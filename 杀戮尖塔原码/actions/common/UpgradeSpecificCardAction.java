// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UpgradeSpecificCardAction.java

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

public class UpgradeSpecificCardAction extends AbstractGameAction
{

    public UpgradeSpecificCardAction(AbstractCard cardToUpgrade)
    {
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION;
        c = cardToUpgrade;
        duration = Settings.ACTION_DUR_FAST;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_FAST)
        {
            if(c.canUpgrade() && c.type != com.megacrit.cardcrawl.cards.AbstractCard.CardType.STATUS)
            {
                c.upgrade();
                c.superFlash();
                c.applyPowers();
            }
            isDone = true;
            return;
        } else
        {
            tickDuration();
            return;
        }
    }

    private AbstractCard c;
}
