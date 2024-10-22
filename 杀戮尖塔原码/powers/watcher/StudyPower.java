// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StudyPower.java

package com.megacrit.cardcrawl.powers.watcher;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.tempCards.Insight;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class StudyPower extends AbstractPower
{

    public StudyPower(AbstractCreature owner, int amount)
    {
        name = powerStrings.NAME;
        ID = "Study";
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        loadRegion("draw");
        type = com.megacrit.cardcrawl.powers.AbstractPower.PowerType.BUFF;
        isTurnBased = false;
    }

    public void atEndOfTurn(boolean playerTurn)
    {
        addToBot(new MakeTempCardInDrawPileAction(new Insight(), amount, true, true));
    }

    public void updateDescription()
    {
        if(amount > 1)
            description = (new StringBuilder()).append(powerStrings.DESCRIPTIONS[0]).append(amount).append(powerStrings.DESCRIPTIONS[1]).toString();
        else
            description = (new StringBuilder()).append(powerStrings.DESCRIPTIONS[0]).append(amount).append(powerStrings.DESCRIPTIONS[2]).toString();
    }

    public static final String POWER_ID = "Study";
    private static final PowerStrings powerStrings;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Study");
    }
}
