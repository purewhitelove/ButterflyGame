// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RechargingCorePower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class RechargingCorePower extends AbstractPower
{

    public RechargingCorePower(AbstractCreature owner, int amount)
    {
        name = NAME;
        ID = "RechargingCore";
        this.owner = owner;
        this.amount = amount;
        turnTimer = 3;
        updateDescription();
        loadRegion("conserve");
        type = AbstractPower.PowerType.BUFF;
        isTurnBased = true;
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(turnTimer).toString();
        if(turnTimer != 1) goto _L2; else goto _L1
_L1:
        new StringBuilder();
        this;
        JVM INSTR dup_x1 ;
        description;
        append();
        DESCRIPTIONS[1];
        append();
        toString();
        description;
          goto _L3
_L2:
        new StringBuilder();
        this;
        JVM INSTR dup_x1 ;
        description;
        append();
        DESCRIPTIONS[2];
        append();
        toString();
        description;
_L3:
        int i = 0;
_L5:
        if(i >= amount)
            break; /* Loop/switch isn't completed */
        new StringBuilder();
        this;
        JVM INSTR dup_x1 ;
        description;
        append();
        DESCRIPTIONS[3];
        append();
        toString();
        description;
        i++;
        if(true) goto _L5; else goto _L4
_L4:
        new StringBuilder();
        this;
        JVM INSTR dup_x1 ;
        description;
        append();
        " .";
        append();
        toString();
        description;
    }

    public void atStartOfTurn()
    {
        updateDescription();
        if(turnTimer == 1)
        {
            flash();
            turnTimer = 3;
            addToBot(new GainEnergyAction(amount));
        } else
        {
            turnTimer--;
        }
        updateDescription();
    }

    public static final String POWER_ID = "RechargingCore";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    private int turnTimer;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("RechargingCore");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
