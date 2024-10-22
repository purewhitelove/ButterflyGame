// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CorruptionPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class CorruptionPower extends AbstractPower
{

    public CorruptionPower(AbstractCreature owner)
    {
        name = NAME;
        ID = "Corruption";
        this.owner = owner;
        amount = -1;
        description = DESCRIPTIONS[0];
        loadRegion("corruption");
    }

    public void updateDescription()
    {
        description = DESCRIPTIONS[1];
    }

    public void onCardDraw(AbstractCard card)
    {
        if(card.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL)
            card.setCostForTurn(-9);
    }

    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if(card.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL)
        {
            flash();
            action.exhaustCard = true;
        }
    }

    public static final String POWER_ID = "Corruption";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Corruption");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
