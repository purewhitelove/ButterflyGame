// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MalleablePower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class MalleablePower extends AbstractPower
{

    public MalleablePower(AbstractCreature owner)
    {
        this(owner, 3);
    }

    public MalleablePower(AbstractCreature owner, int amt)
    {
        name = NAME;
        ID = "Malleable";
        this.owner = owner;
        amount = amt;
        basePower = amt;
        updateDescription();
        loadRegion("malleable");
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[1]).append(NAME).append(DESCRIPTIONS[2]).append(basePower).append(DESCRIPTIONS[3]).toString();
    }

    public void atEndOfTurn(boolean isPlayer)
    {
        if(owner.isPlayer)
        {
            return;
        } else
        {
            amount = basePower;
            updateDescription();
            return;
        }
    }

    public void atEndOfRound()
    {
        if(!owner.isPlayer)
        {
            return;
        } else
        {
            amount = basePower;
            updateDescription();
            return;
        }
    }

    public int onAttacked(DamageInfo info, int damageAmount)
    {
        if(damageAmount < owner.currentHealth && damageAmount > 0 && info.owner != null && info.type == com.megacrit.cardcrawl.cards.DamageInfo.DamageType.NORMAL && info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS)
        {
            flash();
            if(owner.isPlayer)
                addToTop(new GainBlockAction(owner, owner, amount));
            else
                addToBot(new GainBlockAction(owner, owner, amount));
            amount++;
            updateDescription();
        }
        return damageAmount;
    }

    public void stackPower(int stackAmount)
    {
        amount += stackAmount;
        basePower += stackAmount;
    }

    public static final String POWER_ID = "Malleable";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    private static final int STARTING_BLOCK = 3;
    private int basePower;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Malleable");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
