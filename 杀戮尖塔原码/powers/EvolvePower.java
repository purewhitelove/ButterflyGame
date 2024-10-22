// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EvolvePower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower, NoDrawPower

public class EvolvePower extends AbstractPower
{

    public EvolvePower(AbstractCreature owner, int drawAmt)
    {
        name = powerStrings.NAME;
        ID = "Evolve";
        this.owner = owner;
        amount = drawAmt;
        updateDescription();
        loadRegion("evolve");
    }

    public void updateDescription()
    {
        if(amount == 1)
            description = powerStrings.DESCRIPTIONS[0];
        else
            description = (new StringBuilder()).append(powerStrings.DESCRIPTIONS[1]).append(amount).append(powerStrings.DESCRIPTIONS[2]).toString();
    }

    public void onCardDraw(AbstractCard card)
    {
        if(card.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.STATUS && !owner.hasPower("No Draw"))
        {
            flash();
            addToBot(new DrawCardAction(owner, amount));
        }
    }

    public static final String POWER_ID = "Evolve";
    private static final PowerStrings powerStrings;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Evolve");
    }
}
