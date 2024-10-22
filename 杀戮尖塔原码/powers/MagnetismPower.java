// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MagnetismPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.MonsterGroup;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class MagnetismPower extends AbstractPower
{

    public MagnetismPower(AbstractCreature owner, int cardAmount)
    {
        name = NAME;
        ID = "Magnetism";
        this.owner = owner;
        amount = cardAmount;
        updateDescription();
        loadRegion("magnet");
    }

    public void atStartOfTurn()
    {
        if(!AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            flash();
            for(int i = 0; i < amount; i++)
                addToBot(new MakeTempCardInHandAction(AbstractDungeon.returnTrulyRandomColorlessCardInCombat().makeCopy(), 1, false));

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
            description = String.format(PLURAL_DESCRIPTION, new Object[] {
                Integer.valueOf(amount)
            });
        else
            description = String.format(SINGULAR_DESCRIPTION, new Object[] {
                Integer.valueOf(amount)
            });
    }

    public static final String POWER_ID = "Magnetism";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String SINGULAR_DESCRIPTION;
    public static final String PLURAL_DESCRIPTION;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Magnetism");
        NAME = powerStrings.NAME;
        SINGULAR_DESCRIPTION = powerStrings.DESCRIPTIONS[0];
        PLURAL_DESCRIPTION = powerStrings.DESCRIPTIONS[1];
    }
}
