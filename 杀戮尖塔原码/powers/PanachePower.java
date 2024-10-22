// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PanachePower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class PanachePower extends AbstractPower
{

    public PanachePower(AbstractCreature owner, int damage)
    {
        name = NAME;
        ID = "Panache";
        this.owner = owner;
        amount = 5;
        this.damage = damage;
        updateDescription();
        loadRegion("panache");
    }

    public void updateDescription()
    {
        if(amount == 1)
            description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[1]).append(damage).append(DESCRIPTIONS[2]).toString();
        else
            description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[3]).append(damage).append(DESCRIPTIONS[2]).toString();
    }

    public void stackPower(int stackAmount)
    {
        fontScale = 8F;
        damage += stackAmount;
        updateDescription();
    }

    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        amount--;
        if(amount == 0)
        {
            flash();
            amount = 5;
            addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(damage, true), com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }
        updateDescription();
    }

    public void atStartOfTurn()
    {
        amount = 5;
        updateDescription();
    }

    public static final String POWER_ID = "Panache";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final int CARD_AMT = 5;
    private int damage;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Panache");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
