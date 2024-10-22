// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HelloPower.java

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

public class HelloPower extends AbstractPower
{

    public HelloPower(AbstractCreature owner, int cardAmt)
    {
        name = NAME;
        ID = "Hello";
        this.owner = owner;
        amount = cardAmt;
        updateDescription();
        loadRegion("hello");
    }

    public void atStartOfTurn()
    {
        if(!AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            flash();
            for(int i = 0; i < amount; i++)
                addToBot(new MakeTempCardInHandAction(AbstractDungeon.getCard(com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.COMMON, AbstractDungeon.cardRandomRng).makeCopy(), 1, false));

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
            description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[1]).toString();
        else
            description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[2]).toString();
    }

    public static final String POWER_ID = "Hello";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Hello");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
