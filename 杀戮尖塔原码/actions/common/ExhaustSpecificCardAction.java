// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ExhaustSpecificCardAction.java

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ExhaustSpecificCardAction extends AbstractGameAction
{

    public ExhaustSpecificCardAction(AbstractCard targetCard, CardGroup group, boolean isFast)
    {
        this.targetCard = targetCard;
        setValues(AbstractDungeon.player, AbstractDungeon.player, amount);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.EXHAUST;
        this.group = group;
        startingDuration = Settings.ACTION_DUR_FAST;
        duration = startingDuration;
    }

    public ExhaustSpecificCardAction(AbstractCard targetCard, CardGroup group)
    {
        this(targetCard, group, false);
    }

    public void update()
    {
        if(duration == startingDuration && group.contains(targetCard))
        {
            group.moveToExhaustPile(targetCard);
            CardCrawlGame.dungeon.checkForPactAchievement();
            targetCard.exhaustOnUseOnce = false;
            targetCard.freeToPlayOnce = false;
        }
        tickDuration();
    }

    private AbstractCard targetCard;
    private CardGroup group;
    private float startingDuration;
}
