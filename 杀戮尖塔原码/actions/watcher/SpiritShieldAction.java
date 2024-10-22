// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SpiritShieldAction.java

package com.megacrit.cardcrawl.actions.watcher;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;

public class SpiritShieldAction extends AbstractGameAction
{

    public SpiritShieldAction(int blockPerCard)
    {
        this.blockPerCard = blockPerCard;
    }

    public void update()
    {
        if(!AbstractDungeon.player.hand.isEmpty())
            addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, AbstractDungeon.player.hand.group.size() * blockPerCard));
        isDone = true;
    }

    private int blockPerCard;
}
