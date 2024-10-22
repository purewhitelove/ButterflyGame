// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EquilibriumPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class EquilibriumPower extends AbstractPower
{

    public EquilibriumPower(AbstractCreature owner, int amount)
    {
        name = NAME;
        ID = "Equilibrium";
        this.owner = owner;
        this.amount = amount;
        description = DESCRIPTIONS[0];
        loadRegion("retain");
        isTurnBased = true;
    }

    public void updateDescription()
    {
        if(amount == 1)
            description = DESCRIPTIONS[0];
        else
            description = (new StringBuilder()).append(DESCRIPTIONS[1]).append(amount).append(DESCRIPTIONS[2]).toString();
    }

    public void atEndOfTurn(boolean isPlayer)
    {
        if(isPlayer)
        {
            Iterator iterator = AbstractDungeon.player.hand.group.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractCard c = (AbstractCard)iterator.next();
                if(!c.isEthereal)
                    c.retain = true;
            } while(true);
        }
    }

    public void atEndOfRound()
    {
        if(amount == 0)
            addToBot(new RemoveSpecificPowerAction(owner, owner, "Equilibrium"));
        else
            addToBot(new ReducePowerAction(owner, owner, "Equilibrium", 1));
    }

    public static final String POWER_ID = "Equilibrium";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Equilibrium");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
