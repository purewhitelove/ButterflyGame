// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DEPRECATEDSerenityPower.java

package com.megacrit.cardcrawl.powers.deprecated;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.CalmStance;

public class DEPRECATEDSerenityPower extends AbstractPower
{

    public DEPRECATEDSerenityPower(AbstractCreature owner, int amt)
    {
        name = NAME;
        ID = "Serenity";
        this.owner = owner;
        amount = amt;
        updateDescription();
        loadRegion("platedarmor");
    }

    public void playApplyPowerSfx()
    {
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(LocalizedStrings.PERIOD).toString();
    }

    public int onAttacked(DamageInfo info, int damageAmount)
    {
        if(damageAmount > 0 && ((AbstractPlayer)owner).stance.ID.equals("Calm"))
        {
            flash();
            damageAmount -= amount;
            if(damageAmount < amount)
                damageAmount = 0;
        }
        return damageAmount;
    }

    public static final String POWER_ID = "Serenity";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Serenity");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
