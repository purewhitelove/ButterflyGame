// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RegenPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.unique.RegenAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class RegenPower extends AbstractPower
{

    public RegenPower(AbstractCreature owner, int heal)
    {
        name = NAME;
        ID = "Regeneration";
        this.owner = owner;
        amount = heal;
        updateDescription();
        loadRegion("regen");
        type = AbstractPower.PowerType.BUFF;
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[1]).toString();
    }

    public void atEndOfTurn(boolean isPlayer)
    {
        flashWithoutSound();
        addToTop(new RegenAction(owner, amount));
    }

    public void stackPower(int stackAmount)
    {
        super.stackPower(stackAmount);
    }

    public static final String POWER_ID = "Regeneration";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Regeneration");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
