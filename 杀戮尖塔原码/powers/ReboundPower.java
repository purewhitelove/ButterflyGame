// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ReboundPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class ReboundPower extends AbstractPower
{

    public ReboundPower(AbstractCreature owner)
    {
        justEvoked = true;
        name = NAME;
        ID = "Rebound";
        this.owner = owner;
        amount = 1;
        updateDescription();
        loadRegion("rebound");
        isTurnBased = true;
        type = AbstractPower.PowerType.BUFF;
    }

    public void updateDescription()
    {
        if(amount > 1)
            description = (new StringBuilder()).append(DESCRIPTIONS[1]).append(amount).append(DESCRIPTIONS[2]).toString();
        else
            description = DESCRIPTIONS[0];
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action)
    {
        if(justEvoked)
        {
            justEvoked = false;
            return;
        }
        if(card.type != com.megacrit.cardcrawl.cards.AbstractCard.CardType.POWER)
        {
            flash();
            action.reboundCard = true;
        }
        addToBot(new ReducePowerAction(owner, owner, "Rebound", 1));
    }

    public void atEndOfTurn(boolean isPlayer)
    {
        if(isPlayer)
            addToBot(new RemoveSpecificPowerAction(owner, owner, "Rebound"));
    }

    public static final String POWER_ID = "Rebound";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    private boolean justEvoked;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Rebound");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
