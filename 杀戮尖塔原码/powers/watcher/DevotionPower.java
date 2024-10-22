// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DevotionPower.java

package com.megacrit.cardcrawl.powers.watcher;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.DivinityStance;

// Referenced classes of package com.megacrit.cardcrawl.powers.watcher:
//            MantraPower

public class DevotionPower extends AbstractPower
{

    public DevotionPower(AbstractCreature owner, int newAmount)
    {
        name = powerStrings.NAME;
        ID = "DevotionPower";
        this.owner = owner;
        amount = newAmount;
        updateDescription();
        loadRegion("devotion");
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(powerStrings.DESCRIPTIONS[0]).append(amount).append(powerStrings.DESCRIPTIONS[1]).toString();
    }

    public void atStartOfTurnPostDraw()
    {
        flash();
        if(!AbstractDungeon.player.hasPower("Mantra") && amount >= 10)
            addToBot(new ChangeStanceAction("Divinity"));
        else
            addToBot(new ApplyPowerAction(owner, owner, new MantraPower(owner, amount), amount));
    }

    public static final String POWER_ID = "DevotionPower";
    private static final PowerStrings powerStrings;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("DevotionPower");
    }
}
