// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FiendFireAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class FiendFireAction extends AbstractGameAction
{

    public FiendFireAction(AbstractCreature target, DamageInfo info)
    {
        this.info = info;
        setValues(target, info);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.WAIT;
        attackEffect = com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE;
        startingDuration = Settings.ACTION_DUR_FAST;
        duration = startingDuration;
    }

    public void update()
    {
        int count = AbstractDungeon.player.hand.size();
        for(int i = 0; i < count; i++)
            addToTop(new DamageAction(target, info, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE));

        for(int i = 0; i < count; i++)
            if(Settings.FAST_MODE)
                addToTop(new ExhaustAction(1, true, true, false, Settings.ACTION_DUR_XFAST));
            else
                addToTop(new ExhaustAction(1, true, true));

        isDone = true;
    }

    private DamageInfo info;
    private float startingDuration;
}
