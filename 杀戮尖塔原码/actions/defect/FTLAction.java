// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FTLAction.java

package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;

public class FTLAction extends AbstractGameAction
{

    public FTLAction(AbstractCreature target, DamageInfo info, int cardPlayCount)
    {
        this.cardPlayCount = 0;
        this.info = info;
        this.target = target;
        this.cardPlayCount = cardPlayCount;
    }

    public void update()
    {
        addToBot(new DamageAction(target, info, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        if(AbstractDungeon.actionManager.cardsPlayedThisTurn.size() - 1 < cardPlayCount)
            addToTop(new DrawCardAction(AbstractDungeon.player, 1));
        isDone = true;
    }

    private DamageInfo info;
    private AbstractCreature target;
    private int cardPlayCount;
}
