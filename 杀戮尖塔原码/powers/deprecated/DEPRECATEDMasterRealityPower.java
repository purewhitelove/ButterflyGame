// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DEPRECATEDMasterRealityPower.java

package com.megacrit.cardcrawl.powers.deprecated;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DEPRECATEDMasterRealityPower extends AbstractPower
{

    public DEPRECATEDMasterRealityPower(AbstractCreature owner, int amt)
    {
        name = powerStrings.NAME;
        ID = "MasterRealityPower";
        this.owner = owner;
        amount = amt;
        updateDescription();
        loadRegion("master_smite");
    }

    public void onAfterCardPlayed(AbstractCard card)
    {
        if(card.retain || card.selfRetain)
        {
            flash();
            addToBot(new DamageRandomEnemyAction(new DamageInfo(null, amount), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE));
        }
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(powerStrings.DESCRIPTIONS[0]).append(amount).append(powerStrings.DESCRIPTIONS[1]).toString();
    }

    public static final String POWER_ID = "MasterRealityPower";
    private static final PowerStrings powerStrings;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("MasterRealityPower");
    }
}
