// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   VaultPower.java

package com.megacrit.cardcrawl.powers.watcher;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class VaultPower extends AbstractPower
{

    public VaultPower(AbstractCreature target, AbstractCreature source, int amount)
    {
        name = powerStrings.NAME;
        ID = "Vault";
        owner = target;
        this.source = source;
        this.amount = amount;
        updateDescription();
        loadRegion("carddraw");
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(powerStrings.DESCRIPTIONS[0]).append(amount).append(powerStrings.DESCRIPTIONS[1]).toString();
    }

    public void atEndOfRound()
    {
        flash();
        addToBot(new DamageAction(owner, new DamageInfo(source, amount, com.megacrit.cardcrawl.cards.DamageInfo.DamageType.NORMAL), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        addToBot(new RemoveSpecificPowerAction(owner, owner, "Vault"));
    }

    public static final String POWER_ID = "Vault";
    private static final PowerStrings powerStrings;
    private AbstractCreature source;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Vault");
    }
}
