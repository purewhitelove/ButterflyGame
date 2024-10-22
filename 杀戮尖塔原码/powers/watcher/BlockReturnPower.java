// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BlockReturnPower.java

package com.megacrit.cardcrawl.powers.watcher;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BlockReturnPower extends AbstractPower
{

    public BlockReturnPower(AbstractCreature owner, int blockAmt)
    {
        name = powerStrings.NAME;
        ID = "BlockReturnPower";
        this.owner = owner;
        amount = blockAmt;
        updateDescription();
        loadRegion("talk_to_hand");
        type = com.megacrit.cardcrawl.powers.AbstractPower.PowerType.DEBUFF;
    }

    public void stackPower(int stackAmount)
    {
        fontScale = 8F;
        amount += stackAmount;
        updateDescription();
    }

    public int onAttacked(DamageInfo info, int damageAmount)
    {
        if(info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS && info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != owner)
        {
            flash();
            addToTop(new GainBlockAction(AbstractDungeon.player, amount, Settings.FAST_MODE));
        }
        return damageAmount;
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(powerStrings.DESCRIPTIONS[0]).append(amount).append(powerStrings.DESCRIPTIONS[1]).toString();
    }

    public static final String POWER_ID = "BlockReturnPower";
    private static final PowerStrings powerStrings;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("BlockReturnPower");
    }
}
