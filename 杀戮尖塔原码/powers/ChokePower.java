// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChokePower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class ChokePower extends AbstractPower
{

    public ChokePower(AbstractCreature owner, int amount)
    {
        name = NAME;
        ID = "Choked";
        this.owner = owner;
        this.amount = amount;
        description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[1]).toString();
        loadRegion("choke");
        type = AbstractPower.PowerType.DEBUFF;
    }

    public void atStartOfTurn()
    {
        addToBot(new RemoveSpecificPowerAction(owner, owner, "Choked"));
    }

    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        flash();
        addToBot(new LoseHPAction(owner, null, amount));
    }

    public static final String POWER_ID = "Choked";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Choked");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
