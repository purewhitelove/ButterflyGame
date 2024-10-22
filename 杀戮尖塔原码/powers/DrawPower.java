// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DrawPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class DrawPower extends AbstractPower
{

    public DrawPower(AbstractCreature owner, int amount)
    {
        name = NAME;
        ID = "Draw";
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        loadRegion("draw");
        if(amount < 0)
        {
            type = AbstractPower.PowerType.DEBUFF;
            loadRegion("draw2");
        } else
        {
            type = AbstractPower.PowerType.BUFF;
            loadRegion("draw");
        }
        isTurnBased = false;
        AbstractDungeon.player.gameHandSize += amount;
    }

    public void onRemove()
    {
        AbstractDungeon.player.gameHandSize -= amount;
    }

    public void reducePower(int reduceAmount)
    {
        fontScale = 8F;
        amount -= reduceAmount;
        if(amount == 0)
            addToTop(new RemoveSpecificPowerAction(owner, owner, "Draw"));
    }

    public void updateDescription()
    {
        if(amount > 0)
        {
            if(amount == 1)
                description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[1]).toString();
            else
                description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[3]).toString();
            type = AbstractPower.PowerType.BUFF;
        } else
        {
            if(amount == -1)
                description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[2]).toString();
            else
                description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[4]).toString();
            type = AbstractPower.PowerType.DEBUFF;
        }
    }

    public static final String POWER_ID = "Draw";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Draw");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
