// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   VulnerablePower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.relics.OddMushroom;
import com.megacrit.cardcrawl.relics.PaperFrog;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class VulnerablePower extends AbstractPower
{

    public VulnerablePower(AbstractCreature owner, int amount, boolean isSourceMonster)
    {
        justApplied = false;
        name = NAME;
        ID = "Vulnerable";
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        loadRegion("vulnerable");
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
            addToBot(new RemoveSpecificPowerAction(owner, owner, "Vulnerable"));
        else
            addToBot(new ReducePowerAction(owner, owner, "Vulnerable", 1));
    }

    public void updateDescription()
    {
        if(amount == 1)
        {
            if(owner != null && owner.isPlayer && AbstractDungeon.player.hasRelic("Odd Mushroom"))
                description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(25).append(DESCRIPTIONS[1]).append(amount).append(DESCRIPTIONS[2]).toString();
            else
            if(owner != null && !owner.isPlayer && AbstractDungeon.player.hasRelic("Paper Frog"))
                description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(75).append(DESCRIPTIONS[1]).append(amount).append(DESCRIPTIONS[2]).toString();
            else
                description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(50).append(DESCRIPTIONS[1]).append(amount).append(DESCRIPTIONS[2]).toString();
        } else
        if(owner != null && owner.isPlayer && AbstractDungeon.player.hasRelic("Odd Mushroom"))
            description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(25).append(DESCRIPTIONS[1]).append(amount).append(DESCRIPTIONS[3]).toString();
        else
        if(owner != null && !owner.isPlayer && AbstractDungeon.player.hasRelic("Paper Frog"))
            description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(75).append(DESCRIPTIONS[1]).append(amount).append(DESCRIPTIONS[3]).toString();
        else
            description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(50).append(DESCRIPTIONS[1]).append(amount).append(DESCRIPTIONS[3]).toString();
    }

    public float atDamageReceive(float damage, com.megacrit.cardcrawl.cards.DamageInfo.DamageType type)
    {
        if(type == com.megacrit.cardcrawl.cards.DamageInfo.DamageType.NORMAL)
        {
            if(owner.isPlayer && AbstractDungeon.player.hasRelic("Odd Mushroom"))
                return damage * 1.25F;
            if(owner != null && !owner.isPlayer && AbstractDungeon.player.hasRelic("Paper Frog"))
                return damage * 1.75F;
            else
                return damage * 1.5F;
        } else
        {
            return damage;
        }
    }

    public static final String POWER_ID = "Vulnerable";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    private boolean justApplied;
    private static final float EFFECTIVENESS = 1.5F;
    private static final int EFFECTIVENESS_STRING = 50;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Vulnerable");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
