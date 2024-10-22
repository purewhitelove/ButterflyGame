// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ToolsOfTheTradePower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class ToolsOfTheTradePower extends AbstractPower
{

    public ToolsOfTheTradePower(AbstractCreature owner, int drawAmount)
    {
        name = NAME;
        ID = "Tools Of The Trade";
        this.owner = owner;
        amount = drawAmount;
        updateDescription();
        loadRegion("tools");
        priority = 25;
    }

    public void updateDescription()
    {
        if(amount == 1)
            description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[1]).append(amount).append(DESCRIPTIONS[2]).toString();
        else
            description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[3]).append(amount).append(DESCRIPTIONS[4]).toString();
    }

    public void atStartOfTurnPostDraw()
    {
        flash();
        addToBot(new DrawCardAction(owner, amount));
        addToBot(new DiscardAction(owner, owner, amount, false));
    }

    public static final String POWER_ID = "Tools Of The Trade";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Tools Of The Trade");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
