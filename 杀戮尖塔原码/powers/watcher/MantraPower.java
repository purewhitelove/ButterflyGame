// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MantraPower.java

package com.megacrit.cardcrawl.powers.watcher;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.DivinityStance;

public class MantraPower extends AbstractPower
{

    public MantraPower(AbstractCreature owner, int amount)
    {
        name = powerStrings.NAME;
        ID = "Mantra";
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        loadRegion("mantra");
        type = com.megacrit.cardcrawl.powers.AbstractPower.PowerType.BUFF;
        AbstractDungeon.actionManager.mantraGained += amount;
    }

    public void playApplyPowerSfx()
    {
        CardCrawlGame.sound.play("POWER_MANTRA", 0.05F);
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(powerStrings.DESCRIPTIONS[0]).append(10).append(powerStrings.DESCRIPTIONS[1]).toString();
    }

    public void stackPower(int stackAmount)
    {
        super.stackPower(stackAmount);
        if(amount >= 10)
        {
            addToTop(new ChangeStanceAction("Divinity"));
            amount -= 10;
            if(amount <= 0)
                addToTop(new RemoveSpecificPowerAction(owner, owner, "Mantra"));
        }
    }

    public static final String POWER_ID = "Mantra";
    private static final PowerStrings powerStrings;
    private final int PRAYER_REQUIRED = 10;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Mantra");
    }
}
