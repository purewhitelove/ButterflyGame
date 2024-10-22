// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CreativeAIPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class CreativeAIPower extends AbstractPower
{

    public CreativeAIPower(AbstractCreature owner, int amt)
    {
        name = NAME;
        ID = "Creative AI";
        this.owner = owner;
        amount = amt;
        updateDescription();
        loadRegion("ai");
    }

    public void atStartOfTurn()
    {
        for(int i = 0; i < amount; i++)
        {
            AbstractCard card = AbstractDungeon.returnTrulyRandomCardInCombat(com.megacrit.cardcrawl.cards.AbstractCard.CardType.POWER).makeCopy();
            addToBot(new MakeTempCardInHandAction(card));
        }

    }

    public void updateDescription()
    {
        if(amount > 1)
            description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[2]).toString();
        else
            description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[1]).toString();
    }

    public static final String POWER_ID = "Creative AI";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Creative AI");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
