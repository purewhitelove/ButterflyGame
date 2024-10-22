// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BattleHymnPower.java

package com.megacrit.cardcrawl.powers.watcher;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.tempCards.Smite;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BattleHymnPower extends AbstractPower
{

    public BattleHymnPower(AbstractCreature owner, int amt)
    {
        name = powerStrings.NAME;
        ID = "BattleHymn";
        this.owner = owner;
        amount = amt;
        updateDescription();
        loadRegion("hymn");
    }

    public void atStartOfTurn()
    {
        if(!AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            flash();
            addToBot(new MakeTempCardInHandAction(new Smite(), amount, false));
        }
    }

    public void stackPower(int stackAmount)
    {
        fontScale = 8F;
        amount += stackAmount;
    }

    public void updateDescription()
    {
        if(amount > 1)
            description = (new StringBuilder()).append(powerStrings.DESCRIPTIONS[0]).append(amount).append(powerStrings.DESCRIPTIONS[1]).toString();
        else
            description = (new StringBuilder()).append(powerStrings.DESCRIPTIONS[0]).append(amount).append(powerStrings.DESCRIPTIONS[2]).toString();
    }

    public static final String POWER_ID = "BattleHymn";
    private static final PowerStrings powerStrings;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("BattleHymn");
    }
}
