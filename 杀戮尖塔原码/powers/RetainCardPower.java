// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RetainCardPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.unique.RetainCardsAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.relics.RunicPyramid;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower, EquilibriumPower

public class RetainCardPower extends AbstractPower
{

    public RetainCardPower(AbstractCreature owner, int numCards)
    {
        name = NAME;
        ID = "Retain Cards";
        this.owner = owner;
        amount = numCards;
        updateDescription();
        loadRegion("retain");
    }

    public void updateDescription()
    {
        if(amount == 1)
            description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[1]).toString();
        else
            description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[2]).toString();
    }

    public void atEndOfTurn(boolean isPlayer)
    {
        if(isPlayer && !AbstractDungeon.player.hand.isEmpty() && !AbstractDungeon.player.hasRelic("Runic Pyramid") && !AbstractDungeon.player.hasPower("Equilibrium"))
            addToBot(new RetainCardsAction(owner, amount));
    }

    public static final String POWER_ID = "Retain Cards";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Retain Cards");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
