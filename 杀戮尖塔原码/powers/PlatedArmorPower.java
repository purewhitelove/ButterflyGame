// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PlatedArmorPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.ShelledParasite;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class PlatedArmorPower extends AbstractPower
{

    public PlatedArmorPower(AbstractCreature owner, int amt)
    {
        name = NAME;
        ID = "Plated Armor";
        this.owner = owner;
        amount = amt;
        updateDescription();
        loadRegion("platedarmor");
    }

    public void stackPower(int stackAmount)
    {
        fontScale = 8F;
        amount += stackAmount;
        if(amount > 999)
            amount = 999;
        updateDescription();
    }

    public void playApplyPowerSfx()
    {
        CardCrawlGame.sound.play("POWER_PLATED", 0.05F);
    }

    public void updateDescription()
    {
        if(owner.isPlayer)
            description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[1]).toString();
        else
            description = (new StringBuilder()).append(DESCRIPTIONS[2]).append(amount).append(DESCRIPTIONS[3]).toString();
    }

    public void wasHPLost(DamageInfo info, int damageAmount)
    {
        if(info.owner != null && info.owner != owner && info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS && info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS && damageAmount > 0)
        {
            flash();
            addToBot(new ReducePowerAction(owner, owner, "Plated Armor", 1));
        }
    }

    public void onRemove()
    {
        if(!owner.isPlayer)
            addToBot(new ChangeStateAction((AbstractMonster)owner, "ARMOR_BREAK"));
    }

    public void atEndOfTurnPreEndTurnCards(boolean isPlayer)
    {
        flash();
        addToBot(new GainBlockAction(owner, owner, amount));
    }

    public static final String POWER_ID = "Plated Armor";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    private static final int DECREMENT_AMT = 1;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Plated Armor");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
