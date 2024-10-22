// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SetDontTriggerAction.java

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class SetDontTriggerAction extends AbstractGameAction
{

    public SetDontTriggerAction(AbstractCard card, boolean dontTrigger)
    {
        this.card = card;
        trigger = dontTrigger;
    }

    public void update()
    {
        card.dontTriggerOnUseCard = trigger;
        isDone = true;
    }

    private AbstractCard card;
    private boolean trigger;
}
