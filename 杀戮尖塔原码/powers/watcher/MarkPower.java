// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MarkPower.java

package com.megacrit.cardcrawl.powers.watcher;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.PressurePoints;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class MarkPower extends AbstractPower
{

    public MarkPower(AbstractCreature owner, int amt)
    {
        name = powerStrings.NAME;
        ID = "PathToVictoryPower";
        this.owner = owner;
        amount = amt;
        updateDescription();
        loadRegion("pressure_points");
        type = com.megacrit.cardcrawl.powers.AbstractPower.PowerType.DEBUFF;
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(powerStrings.DESCRIPTIONS[0]).append(amount).append(powerStrings.DESCRIPTIONS[1]).toString();
    }

    public void triggerMarks(AbstractCard card)
    {
        if(card.cardID.equals("PathToVictory"))
            addToBot(new LoseHPAction(owner, null, amount, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE));
    }

    public static final String POWER_ID = "PathToVictoryPower";
    private static final PowerStrings powerStrings;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("PathToVictoryPower");
    }
}
