// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DEPRECATEDArmorSelectedAmountAction.java

package com.megacrit.cardcrawl.actions.deprecated;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.screens.select.HandCardSelectScreen;

public class DEPRECATEDArmorSelectedAmountAction extends AbstractGameAction
{

    public DEPRECATEDArmorSelectedAmountAction(AbstractCreature target, AbstractCreature source, int multiplier)
    {
        setValues(target, source, multiplier);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.POWER;
    }

    public void update()
    {
        if(duration == 0.5F)
        {
            amount = AbstractDungeon.handCardSelectScreen.numSelected * amount;
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(target, target, new MetallicizePower(target, amount), amount));
        }
        tickDuration();
    }
}
