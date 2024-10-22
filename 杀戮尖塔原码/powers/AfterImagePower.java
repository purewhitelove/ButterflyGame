// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AfterImagePower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class AfterImagePower extends AbstractPower
{

    public AfterImagePower(AbstractCreature owner, int amount)
    {
        name = powerStrings.NAME;
        ID = "After Image";
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        loadRegion("afterImage");
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(powerStrings.DESCRIPTIONS[0]).append(amount).append(powerStrings.DESCRIPTIONS[1]).toString();
    }

    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if(Settings.FAST_MODE)
            addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, amount, true));
        else
            addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, amount));
        flash();
    }

    public static final String POWER_ID = "After Image";
    private static final PowerStrings powerStrings;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("After Image");
    }
}
