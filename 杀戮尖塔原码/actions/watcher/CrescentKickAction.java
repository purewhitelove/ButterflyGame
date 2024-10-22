// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CrescentKickAction.java

package com.megacrit.cardcrawl.actions.watcher;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.deprecated.DEPRECATEDCrescentKick;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class CrescentKickAction extends AbstractGameAction
{

    public CrescentKickAction(AbstractPlayer p, DEPRECATEDCrescentKick card)
    {
        duration = Settings.ACTION_DUR_XFAST;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.BLOCK;
        this.card = card;
        target = p;
    }

    public void update()
    {
        if(card.hadVigor && target != null)
        {
            addToTop(new DrawCardAction(AbstractDungeon.player, 1));
            addToTop(new GainEnergyAction(1));
        }
        isDone = true;
    }

    private DEPRECATEDCrescentKick card;
}
