// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ModifyBlockAction.java

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import java.util.*;

public class ModifyBlockAction extends AbstractGameAction
{

    public ModifyBlockAction(UUID targetUUID, int amount)
    {
        setValues(target, source, amount);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION;
        uuid = targetUUID;
    }

    public void update()
    {
        Iterator iterator = GetAllInBattleInstances.get(uuid).iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            c.baseBlock += amount;
            if(c.baseDamage < 0)
                c.baseDamage = 0;
        } while(true);
        isDone = true;
    }

    UUID uuid;
}
