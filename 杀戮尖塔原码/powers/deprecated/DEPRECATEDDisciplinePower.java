// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DEPRECATEDDisciplinePower.java

package com.megacrit.cardcrawl.powers.deprecated;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class DEPRECATEDDisciplinePower extends AbstractPower
{

    public DEPRECATEDDisciplinePower(AbstractCreature owner)
    {
        name = powerStrings.NAME;
        ID = "DisciplinePower";
        this.owner = owner;
        updateDescription();
        loadRegion("no_stance");
        type = com.megacrit.cardcrawl.powers.AbstractPower.PowerType.BUFF;
        amount = -1;
    }

    public void atEndOfTurn(boolean isPlayer)
    {
        if(EnergyPanel.totalCount > 0)
        {
            amount = EnergyPanel.totalCount;
            fontScale = 8F;
        }
    }

    public void atStartOfTurn()
    {
        if(amount != -1)
        {
            addToTop(new DrawCardAction(amount));
            amount = -1;
            fontScale = 8F;
            flash();
        }
    }

    public void updateDescription()
    {
        description = powerStrings.DESCRIPTIONS[0];
    }

    public static final String POWER_ID = "DisciplinePower";
    private static final PowerStrings powerStrings;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("DisciplinePower");
    }
}
