// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NoBlockPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class NoBlockPower extends AbstractPower
{

    public NoBlockPower(AbstractCreature owner, int amount, boolean isSourceMonster)
    {
        justApplied = false;
        name = NAME;
        ID = "NoBlockPower";
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        loadRegion("noBlock");
        if(AbstractDungeon.actionManager.turnHasEnded && isSourceMonster)
            justApplied = true;
        type = AbstractPower.PowerType.DEBUFF;
        isTurnBased = true;
    }

    public void atEndOfRound()
    {
        if(justApplied)
        {
            justApplied = false;
            return;
        }
        if(amount == 0)
            addToBot(new RemoveSpecificPowerAction(owner, owner, "NoBlockPower"));
        else
            addToBot(new ReducePowerAction(owner, owner, "NoBlockPower", 1));
    }

    public void updateDescription()
    {
        description = DESCRIPTIONS[0];
    }

    public float modifyBlockLast(float blockAmount)
    {
        return 0.0F;
    }

    public static final String POWER_ID = "NoBlockPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    private boolean justApplied;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("NoBlockPower");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
