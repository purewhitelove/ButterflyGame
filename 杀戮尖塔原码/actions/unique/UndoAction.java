// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UndoAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class UndoAction extends AbstractGameAction
{

    public UndoAction()
    {
        p = AbstractDungeon.player;
        duration = Settings.ACTION_DUR_MED;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_MED)
        {
            if(GameActionManager.turn == 1)
            {
                isDone = true;
                return;
            }
            if(p.currentHealth < GameActionManager.playerHpLastTurn)
                p.heal(GameActionManager.playerHpLastTurn - p.currentHealth, true);
            else
            if(p.currentHealth > GameActionManager.playerHpLastTurn)
                addToTop(new DamageAction(p, new DamageInfo(p, p.currentHealth - GameActionManager.playerHpLastTurn, com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE));
        }
        tickDuration();
    }

    private AbstractPlayer p;
}
