// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DEPRECATEDHotHotPower.java

package com.megacrit.cardcrawl.powers.deprecated;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.Buffer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DEPRECATEDHotHotPower extends AbstractPower
{

    public DEPRECATEDHotHotPower(AbstractCreature owner, int amount)
    {
        name = NAME;
        ID = "HotHot";
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        loadRegion("corruption");
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[1]).toString();
    }

    public int onAttacked(DamageInfo info, int damageAmount)
    {
        if(info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS && info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != owner && damageAmount > 0 && !owner.hasPower("Buffer"))
        {
            flash();
            AbstractDungeon.actionManager.addToTop(new DamageAction(info.owner, new DamageInfo(owner, amount, com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE, true));
        }
        return damageAmount;
    }

    public static final String POWER_ID = "HotHot";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("HotHot");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
