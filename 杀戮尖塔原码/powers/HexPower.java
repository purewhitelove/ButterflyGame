// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HexPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class HexPower extends AbstractPower
{

    public HexPower(AbstractCreature owner, int amount)
    {
        name = NAME;
        ID = "Hex";
        this.owner = owner;
        this.amount = amount;
        description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[1]).toString();
        loadRegion("hex");
        type = AbstractPower.PowerType.DEBUFF;
    }

    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if(card.type != com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK)
        {
            flash();
            addToBot(new MakeTempCardInDrawPileAction(new Dazed(), amount, true, true));
        }
    }

    public static final String POWER_ID = "Hex";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Hex");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
