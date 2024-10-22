// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ThousandCutsPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class ThousandCutsPower extends AbstractPower
{

    public ThousandCutsPower(AbstractCreature owner, int amount)
    {
        name = NAME;
        ID = "Thousand Cuts";
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        loadRegion("thousandCuts");
    }

    public void onAfterCardPlayed(AbstractCard card)
    {
        flash();
        addToBot(new SFXAction("ATTACK_HEAVY"));
        if(Settings.FAST_MODE)
            addToBot(new VFXAction(new CleaveEffect()));
        else
            addToBot(new VFXAction(owner, new CleaveEffect(), 0.2F));
        addToBot(new DamageAllEnemiesAction(owner, DamageInfo.createDamageMatrix(amount, true), com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE, true));
    }

    public void stackPower(int stackAmount)
    {
        fontScale = 8F;
        amount += stackAmount;
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[1]).toString();
    }

    public static final String POWER_ID = "Thousand Cuts";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Thousand Cuts");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
