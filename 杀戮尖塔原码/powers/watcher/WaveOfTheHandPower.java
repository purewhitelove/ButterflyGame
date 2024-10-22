// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WaveOfTheHandPower.java

package com.megacrit.cardcrawl.powers.watcher;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.ArrayList;
import java.util.Iterator;

public class WaveOfTheHandPower extends AbstractPower
{

    public WaveOfTheHandPower(AbstractCreature owner, int newAmount)
    {
        name = powerStrings.NAME;
        ID = "WaveOfTheHandPower";
        this.owner = owner;
        amount = newAmount;
        updateDescription();
        loadRegion("wave_of_the_hand");
    }

    public void onGainedBlock(float blockAmount)
    {
        if(blockAmount > 0.0F)
        {
            flash();
            AbstractCreature p = AbstractDungeon.player;
            AbstractMonster mo;
            for(Iterator iterator = AbstractDungeon.getCurrRoom().monsters.monsters.iterator(); iterator.hasNext(); addToBot(new ApplyPowerAction(mo, p, new WeakPower(mo, amount, false), amount, true, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE)))
                mo = (AbstractMonster)iterator.next();

        }
    }

    public void atEndOfRound()
    {
        addToBot(new RemoveSpecificPowerAction(owner, owner, "WaveOfTheHandPower"));
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(powerStrings.DESCRIPTIONS[0]).append(amount).append(powerStrings.DESCRIPTIONS[1]).toString();
    }

    public static final String POWER_ID = "WaveOfTheHandPower";
    private static final PowerStrings powerStrings;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("WaveOfTheHandPower");
    }
}
