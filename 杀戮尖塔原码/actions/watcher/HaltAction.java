// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HaltAction.java

package com.megacrit.cardcrawl.actions.watcher;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.WrathStance;

public class HaltAction extends AbstractGameAction
{

    public HaltAction(AbstractCreature target, int block, int additional)
    {
        this.target = target;
        amount = block;
        additionalAmt = additional;
    }

    public void update()
    {
        if(AbstractDungeon.player.stance.ID.equals("Wrath"))
            addToTop(new GainBlockAction(target, amount + additionalAmt));
        else
            addToTop(new GainBlockAction(target, amount));
        isDone = true;
    }

    int additionalAmt;
}
