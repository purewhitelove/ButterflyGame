// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AnimateOrbAction.java

package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AnimateOrbAction extends AbstractGameAction
{

    public AnimateOrbAction(int amount)
    {
        orbCount = amount;
    }

    public void update()
    {
        for(int i = 0; i < orbCount; i++)
            AbstractDungeon.player.triggerEvokeAnimation(i);

        isDone = true;
    }

    private int orbCount;
}
