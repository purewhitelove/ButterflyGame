// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LikeWaterPower.java

package com.megacrit.cardcrawl.powers.watcher;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.CalmStance;

public class LikeWaterPower extends AbstractPower
{

    public LikeWaterPower(AbstractCreature owner, int amt)
    {
        name = powerStrings.NAME;
        ID = "LikeWaterPower";
        this.owner = owner;
        amount = amt;
        updateDescription();
        loadRegion("like_water");
    }

    public void stackPower(int stackAmount)
    {
        fontScale = 8F;
        amount += stackAmount;
        if(amount > 999)
            amount = 999;
        updateDescription();
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(powerStrings.DESCRIPTIONS[0]).append(amount).append(powerStrings.DESCRIPTIONS[1]).toString();
    }

    public void atEndOfTurnPreEndTurnCards(boolean isPlayer)
    {
        if(isPlayer)
        {
            AbstractPlayer p = (AbstractPlayer)owner;
            if(p.stance.ID.equals("Calm"))
            {
                flash();
                addToBot(new GainBlockAction(owner, owner, amount));
            }
        }
    }

    public static final String POWER_ID = "LikeWaterPower";
    private static final PowerStrings powerStrings;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("LikeWaterPower");
    }
}
