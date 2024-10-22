// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GainGoldAction.java

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class GainGoldAction extends AbstractGameAction
{

    public GainGoldAction(int amount)
    {
        this.amount = amount;
    }

    public void update()
    {
        AbstractDungeon.player.gainGold(amount);
        isDone = true;
    }
}
