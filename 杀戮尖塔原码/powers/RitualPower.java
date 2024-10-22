// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RitualPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower, StrengthPower

public class RitualPower extends AbstractPower
{

    public RitualPower(AbstractCreature owner, int strAmt, boolean playerControlled)
    {
        skipFirst = true;
        name = NAME;
        ID = "Ritual";
        this.owner = owner;
        amount = strAmt;
        onPlayer = playerControlled;
        updateDescription();
        loadRegion("ritual");
    }

    public void updateDescription()
    {
        if(!onPlayer)
            description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[1]).toString();
        else
            description = (new StringBuilder()).append(DESCRIPTIONS[2]).append(amount).append(DESCRIPTIONS[1]).toString();
    }

    public void atEndOfTurn(boolean isPlayer)
    {
        if(isPlayer)
        {
            flash();
            addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, amount), amount));
        }
    }

    public void atEndOfRound()
    {
        if(!onPlayer)
            if(!skipFirst)
            {
                flash();
                addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, amount), amount));
            } else
            {
                skipFirst = false;
            }
    }

    public static final String POWER_ID = "Ritual";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    private boolean skipFirst;
    private boolean onPlayer;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Ritual");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
