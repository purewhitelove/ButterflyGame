// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ReApplyPowersAction.java

package com.megacrit.cardcrawl.actions.utility;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ReApplyPowersAction extends AbstractGameAction
{

    public ReApplyPowersAction(AbstractCard card, AbstractMonster m)
    {
        duration = Settings.ACTION_DUR_FAST;
        this.card = card;
        this.m = m;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_FAST)
        {
            card.calculateCardDamage(m);
            isDone = true;
        }
    }

    private AbstractCard card;
    private AbstractMonster m;
}
