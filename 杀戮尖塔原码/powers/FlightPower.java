// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FlightPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.Byrd;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class FlightPower extends AbstractPower
{

    public FlightPower(AbstractCreature owner, int amount)
    {
        name = NAME;
        ID = "Flight";
        this.owner = owner;
        this.amount = amount;
        storedAmount = amount;
        updateDescription();
        loadRegion("flight");
        priority = 50;
    }

    public void playApplyPowerSfx()
    {
        CardCrawlGame.sound.play("POWER_FLIGHT", 0.05F);
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[1]).toString();
    }

    public void atStartOfTurn()
    {
        amount = storedAmount;
        updateDescription();
    }

    public float atDamageFinalReceive(float damage, com.megacrit.cardcrawl.cards.DamageInfo.DamageType type)
    {
        return calculateDamageTakenAmount(damage, type);
    }

    private float calculateDamageTakenAmount(float damage, com.megacrit.cardcrawl.cards.DamageInfo.DamageType type)
    {
        if(type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS && type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS)
            return damage / 2.0F;
        else
            return damage;
    }

    public int onAttacked(DamageInfo info, int damageAmount)
    {
        Boolean willLive = Boolean.valueOf(calculateDamageTakenAmount(damageAmount, info.type) < (float)owner.currentHealth);
        if(info.owner != null && info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS && info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS && damageAmount > 0 && willLive.booleanValue())
        {
            flash();
            addToBot(new ReducePowerAction(owner, owner, "Flight", 1));
        }
        return damageAmount;
    }

    public void onRemove()
    {
        addToBot(new ChangeStateAction((AbstractMonster)owner, "GROUNDED"));
    }

    public static final String POWER_ID = "Flight";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    private int storedAmount;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Flight");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
