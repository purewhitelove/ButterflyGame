// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IncreaseMiscAction.java

package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import java.util.*;

public class IncreaseMiscAction extends AbstractGameAction
{

    public IncreaseMiscAction(UUID targetUUID, int miscValue, int miscIncrease)
    {
        this.miscIncrease = miscIncrease;
        uuid = targetUUID;
    }

    public void update()
    {
        Iterator iterator = AbstractDungeon.player.masterDeck.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.uuid.equals(uuid))
            {
                c.misc += miscIncrease;
                c.applyPowers();
                c.baseBlock = c.misc;
                c.isBlockModified = false;
            }
        } while(true);
        for(Iterator iterator1 = GetAllInBattleInstances.get(uuid).iterator(); iterator1.hasNext();)
        {
            AbstractCard c = (AbstractCard)iterator1.next();
            c.misc += miscIncrease;
            c.applyPowers();
            c.baseBlock = c.misc;
        }

        isDone = true;
    }

    private int miscIncrease;
    private UUID uuid;
}
