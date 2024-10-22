// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CrushJointsAction.java

package com.megacrit.cardcrawl.actions.watcher;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import java.util.ArrayList;

public class CrushJointsAction extends AbstractGameAction
{

    public CrushJointsAction(AbstractMonster monster, int vulnAmount)
    {
        m = monster;
        magicNumber = vulnAmount;
    }

    public void update()
    {
        if(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() >= 2 && ((AbstractCard)AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 2)).type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL)
            addToTop(new ApplyPowerAction(m, AbstractDungeon.player, new VulnerablePower(m, magicNumber, false), magicNumber));
        isDone = true;
    }

    private AbstractMonster m;
    private int magicNumber;
}
