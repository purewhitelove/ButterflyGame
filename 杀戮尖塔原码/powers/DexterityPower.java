// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DexterityPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class DexterityPower extends AbstractPower
{

    public DexterityPower(AbstractCreature owner, int amount)
    {
        name = NAME;
        ID = "Dexterity";
        this.owner = owner;
        this.amount = amount;
        if(this.amount >= 999)
            this.amount = 999;
        if(this.amount <= -999)
            this.amount = -999;
        updateDescription();
        loadRegion("dexterity");
        canGoNegative = true;
    }

    public void playApplyPowerSfx()
    {
        CardCrawlGame.sound.play("POWER_DEXTERITY", 0.05F);
    }

    public void stackPower(int stackAmount)
    {
        fontScale = 8F;
        amount += stackAmount;
        if(amount == 0)
            addToTop(new RemoveSpecificPowerAction(owner, owner, "Dexterity"));
        if(amount >= 999)
            amount = 999;
        if(amount <= -999)
            amount = -999;
    }

    public void reducePower(int reduceAmount)
    {
        fontScale = 8F;
        amount -= reduceAmount;
        if(amount == 0)
            addToTop(new RemoveSpecificPowerAction(owner, owner, "Dexterity"));
        if(amount >= 999)
            amount = 999;
        if(amount <= -999)
            amount = -999;
    }

    public void updateDescription()
    {
        if(amount > 0)
        {
            description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[2]).toString();
            type = AbstractPower.PowerType.BUFF;
        } else
        {
            int tmp = -amount;
            description = (new StringBuilder()).append(DESCRIPTIONS[1]).append(tmp).append(DESCRIPTIONS[2]).toString();
            type = AbstractPower.PowerType.DEBUFF;
        }
    }

    public float modifyBlock(float blockAmount)
    {
        if((blockAmount += amount) < 0.0F)
            return 0.0F;
        else
            return blockAmount;
    }

    public static final String POWER_ID = "Dexterity";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Dexterity");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
