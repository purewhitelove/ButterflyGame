// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CollectPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class CollectPower extends AbstractPower
{

    public CollectPower(AbstractCreature owner, int numTurns)
    {
        name = NAME;
        ID = "Collect";
        this.owner = owner;
        amount = numTurns;
        isTurnBased = true;
        if(amount >= 999)
            amount = 999;
        updateDescription();
        loadRegion("energized_blue");
    }

    public void stackPower(int stackAmount)
    {
        super.stackPower(stackAmount);
        if(amount >= 999)
            amount = 999;
    }

    public void updateDescription()
    {
        if(amount == 1)
            description = DESCRIPTIONS[0];
        else
            description = (new StringBuilder()).append(DESCRIPTIONS[1]).append(amount).append(DESCRIPTIONS[2]).toString();
    }

    public void onEnergyRecharge()
    {
        flash();
        AbstractCard card = new Miracle();
        card.upgrade();
        addToBot(new MakeTempCardInHandAction(card));
        if(amount <= 1)
            addToBot(new RemoveSpecificPowerAction(owner, owner, "Collect"));
        else
            addToBot(new ReducePowerAction(owner, owner, "Collect", 1));
    }

    public static final String POWER_ID = "Collect";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Collect");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
