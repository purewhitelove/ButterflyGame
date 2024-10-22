// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TriggerMarksAction.java

package com.megacrit.cardcrawl.actions.watcher;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.ArrayList;
import java.util.Iterator;

public class TriggerMarksAction extends AbstractGameAction
{

    public TriggerMarksAction(AbstractCard callingCard)
    {
        card = callingCard;
    }

    public void update()
    {
        for(Iterator iterator = AbstractDungeon.getCurrRoom().monsters.monsters.iterator(); iterator.hasNext();)
        {
            AbstractMonster mo = (AbstractMonster)iterator.next();
            Iterator iterator1 = mo.powers.iterator();
            while(iterator1.hasNext()) 
            {
                AbstractPower p = (AbstractPower)iterator1.next();
                p.triggerMarks(card);
            }
        }

        isDone = true;
    }

    AbstractCard card;
}
