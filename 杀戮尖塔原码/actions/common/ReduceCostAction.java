// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ReduceCostAction.java

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import java.util.*;

public class ReduceCostAction extends AbstractGameAction
{

    public ReduceCostAction(AbstractCard card)
    {
        this.card = null;
        this.card = card;
    }

    public ReduceCostAction(UUID targetUUID, int amount)
    {
        card = null;
        uuid = targetUUID;
        this.amount = amount;
        duration = Settings.ACTION_DUR_XFAST;
    }

    public void update()
    {
        if(card == null)
        {
            AbstractCard c;
            for(Iterator iterator = GetAllInBattleInstances.get(uuid).iterator(); iterator.hasNext(); c.modifyCostForCombat(-1))
                c = (AbstractCard)iterator.next();

        } else
        {
            card.modifyCostForCombat(-1);
        }
        isDone = true;
    }

    UUID uuid;
    private AbstractCard card;
}
