// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SkillBurnPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class SkillBurnPower extends AbstractPower
{

    public SkillBurnPower(AbstractCreature owner, int amount)
    {
        justApplied = true;
        name = NAME;
        ID = "Skill Burn";
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        loadRegion("skillBurn");
        type = AbstractPower.PowerType.DEBUFF;
        isTurnBased = true;
    }

    public void atEndOfRound()
    {
        if(justApplied)
        {
            justApplied = false;
            return;
        } else
        {
            addToBot(new ReducePowerAction(owner, owner, "Skill Burn", 1));
            return;
        }
    }

    public void updateDescription()
    {
        if(amount == 1)
            description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[1]).toString();
        else
            description = (new StringBuilder()).append(DESCRIPTIONS[2]).append(amount).append(DESCRIPTIONS[3]).toString();
    }

    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if(card.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL)
        {
            flash();
            action.exhaustCard = true;
        }
    }

    public static final String POWER_ID = "Skill Burn";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    private boolean justApplied;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Skill Burn");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
