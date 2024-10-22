// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HandCheckAction.java

package com.megacrit.cardcrawl.actions.utility;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class HandCheckAction extends AbstractGameAction
{

    public HandCheckAction()
    {
        player = AbstractDungeon.player;
    }

    public void update()
    {
        player.hand.applyPowers();
        player.hand.glowCheck();
        isDone = true;
    }

    private AbstractPlayer player;
}
