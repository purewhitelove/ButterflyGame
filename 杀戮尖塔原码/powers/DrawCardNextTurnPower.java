// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DrawCardNextTurnPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class DrawCardNextTurnPower extends AbstractPower
{

    public DrawCardNextTurnPower(AbstractCreature owner, int drawAmount)
    {
        name = NAME;
        ID = "Draw Card";
        this.owner = owner;
        amount = drawAmount;
        updateDescription();
        loadRegion("carddraw");
        priority = 20;
    }

    public void updateDescription()
    {
        if(amount > 1)
            description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[1]).toString();
        else
            description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[2]).toString();
    }

    public void atStartOfTurnPostDraw()
    {
        flash();
        addToBot(new DrawCardAction(owner, amount));
        addToBot(new RemoveSpecificPowerAction(owner, owner, "Draw Card"));
    }

    public static final String POWER_ID = "Draw Card";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Draw Card");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
