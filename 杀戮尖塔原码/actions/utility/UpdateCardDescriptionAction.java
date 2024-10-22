// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UpdateCardDescriptionAction.java

package com.megacrit.cardcrawl.actions.utility;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class UpdateCardDescriptionAction extends AbstractGameAction
{

    public UpdateCardDescriptionAction(AbstractCard targetCard)
    {
        this.targetCard = targetCard;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.TEXT;
        duration = 0.5F;
    }

    public void update()
    {
        if(duration == 0.5F)
            targetCard.initializeDescription();
        tickDuration();
    }

    private AbstractCard targetCard;
}
