// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DuplicationPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class DuplicationPower extends AbstractPower
{

    public DuplicationPower(AbstractCreature owner, int amount)
    {
        name = powerStrings.NAME;
        ID = "DuplicationPower";
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        loadRegion("doubleTap");
    }

    public void updateDescription()
    {
        if(amount == 1)
            description = powerStrings.DESCRIPTIONS[0];
        else
            description = (new StringBuilder()).append(powerStrings.DESCRIPTIONS[1]).append(amount).append(powerStrings.DESCRIPTIONS[2]).toString();
    }

    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if(!card.purgeOnUse && amount > 0)
        {
            flash();
            AbstractMonster m = null;
            if(action.target != null)
                m = (AbstractMonster)action.target;
            AbstractCard tmp = card.makeSameInstanceOf();
            AbstractDungeon.player.limbo.addToBottom(tmp);
            tmp.current_x = card.current_x;
            tmp.current_y = card.current_y;
            tmp.target_x = (float)Settings.WIDTH / 2.0F - 300F * Settings.scale;
            tmp.target_y = (float)Settings.HEIGHT / 2.0F;
            if(m != null)
                tmp.calculateCardDamage(m);
            tmp.purgeOnUse = true;
            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, card.energyOnUse, true, true), true);
            amount--;
            if(amount == 0)
                addToBot(new RemoveSpecificPowerAction(owner, owner, "DuplicationPower"));
        }
    }

    public void atEndOfRound()
    {
        if(amount == 0)
            addToBot(new RemoveSpecificPowerAction(owner, owner, "DuplicationPower"));
        else
            addToBot(new ReducePowerAction(owner, owner, "DuplicationPower", 1));
    }

    public static final String POWER_ID = "DuplicationPower";
    private static final PowerStrings powerStrings;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("DuplicationPower");
    }
}
