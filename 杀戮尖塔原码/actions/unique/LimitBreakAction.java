// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LimitBreakAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class LimitBreakAction extends AbstractGameAction
{

    public LimitBreakAction()
    {
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.WAIT;
        p = AbstractDungeon.player;
        duration = Settings.ACTION_DUR_XFAST;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_XFAST && p.hasPower("Strength"))
        {
            int strAmt = p.getPower("Strength").amount;
            addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, strAmt), strAmt));
        }
        tickDuration();
    }

    private AbstractPlayer p;
}
