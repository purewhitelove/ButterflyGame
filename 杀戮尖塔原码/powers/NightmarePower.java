// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NightmarePower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class NightmarePower extends AbstractPower
{

    public NightmarePower(AbstractCreature owner, int cardAmt, AbstractCard copyMe)
    {
        name = NAME;
        ID = "Night Terror";
        this.owner = owner;
        amount = cardAmt;
        loadRegion("nightmare");
        card = copyMe.makeStatEquivalentCopy();
        card.resetAttributes();
        updateDescription();
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(" ").append(FontHelper.colorString(card.name, "y")).append(DESCRIPTIONS[1]).toString();
    }

    public void atStartOfTurn()
    {
        addToBot(new MakeTempCardInHandAction(card, amount));
        addToBot(new RemoveSpecificPowerAction(owner, owner, "Night Terror"));
    }

    public static final String POWER_ID = "Night Terror";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    private AbstractCard card;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Night Terror");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
