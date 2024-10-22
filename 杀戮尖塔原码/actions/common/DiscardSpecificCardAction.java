// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DiscardSpecificCardAction.java

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DiscardSpecificCardAction extends AbstractGameAction
{

    public DiscardSpecificCardAction(AbstractCard targetCard)
    {
        this.targetCard = targetCard;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DISCARD;
        duration = Settings.ACTION_DUR_FAST;
    }

    public DiscardSpecificCardAction(AbstractCard targetCard, CardGroup group)
    {
        this.targetCard = targetCard;
        this.group = group;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DISCARD;
        duration = Settings.ACTION_DUR_FAST;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_FAST)
        {
            if(group == null)
                group = AbstractDungeon.player.hand;
            if(group.contains(targetCard))
            {
                group.moveToDiscardPile(targetCard);
                GameActionManager.incrementDiscard(false);
                targetCard.triggerOnManualDiscard();
            }
        }
        tickDuration();
    }

    private AbstractCard targetCard;
    private CardGroup group;
}
