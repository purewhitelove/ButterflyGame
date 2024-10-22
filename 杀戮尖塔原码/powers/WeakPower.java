// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WeakPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.relics.PaperCrane;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class WeakPower extends AbstractPower
{

    public WeakPower(AbstractCreature owner, int amount, boolean isSourceMonster)
    {
        justApplied = false;
        name = NAME;
        ID = "Weakened";
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        loadRegion("weak");
        if(isSourceMonster)
            justApplied = true;
        type = AbstractPower.PowerType.DEBUFF;
        isTurnBased = true;
        priority = 99;
    }

    public void atEndOfRound()
    {
        if(justApplied)
        {
            justApplied = false;
            return;
        }
        if(amount == 0)
            addToBot(new RemoveSpecificPowerAction(owner, owner, "Weakened"));
        else
            addToBot(new ReducePowerAction(owner, owner, "Weakened", 1));
    }

    public void updateDescription()
    {
        if(amount == 1)
        {
            if(owner != null && !owner.isPlayer && AbstractDungeon.player.hasRelic("Paper Crane"))
                description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(40).append(DESCRIPTIONS[1]).append(amount).append(DESCRIPTIONS[2]).toString();
            else
                description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(25).append(DESCRIPTIONS[1]).append(amount).append(DESCRIPTIONS[2]).toString();
        } else
        if(owner != null && !owner.isPlayer && AbstractDungeon.player.hasRelic("Paper Crane"))
            description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(40).append(DESCRIPTIONS[1]).append(amount).append(DESCRIPTIONS[3]).toString();
        else
            description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(25).append(DESCRIPTIONS[1]).append(amount).append(DESCRIPTIONS[3]).toString();
    }

    public float atDamageGive(float damage, com.megacrit.cardcrawl.cards.DamageInfo.DamageType type)
    {
        if(type == com.megacrit.cardcrawl.cards.DamageInfo.DamageType.NORMAL)
        {
            if(!owner.isPlayer && AbstractDungeon.player.hasRelic("Paper Crane"))
                return damage * 0.6F;
            else
                return damage * 0.75F;
        } else
        {
            return damage;
        }
    }

    public static final String POWER_ID = "Weakened";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    private boolean justApplied;
    private static final int EFFECTIVENESS_STRING = 25;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Weakened");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
