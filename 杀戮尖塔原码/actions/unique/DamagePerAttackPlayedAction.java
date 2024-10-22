// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DamagePerAttackPlayedAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;
import java.util.Iterator;

public class DamagePerAttackPlayedAction extends AbstractGameAction
{

    public DamagePerAttackPlayedAction(AbstractCreature target, DamageInfo info, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect effect)
    {
        this.info = info;
        setValues(target, info);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DAMAGE;
        attackEffect = effect;
    }

    public DamagePerAttackPlayedAction(AbstractCreature target, DamageInfo info)
    {
        this(target, info, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE);
    }

    public void update()
    {
        isDone = true;
        if(target != null && target.currentHealth > 0)
        {
            int count = 0;
            Iterator iterator = AbstractDungeon.actionManager.cardsPlayedThisTurn.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractCard c = (AbstractCard)iterator.next();
                if(c.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK)
                    count++;
            } while(true);
            count--;
            for(int i = 0; i < count; i++)
                addToTop(new DamageAction(target, info, attackEffect));

        }
    }

    private DamageInfo info;
}
