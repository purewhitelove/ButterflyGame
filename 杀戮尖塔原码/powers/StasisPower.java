// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StasisPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class StasisPower extends AbstractPower
{

    public StasisPower(AbstractCreature owner, AbstractCard card)
    {
        name = powerStrings.NAME;
        ID = "Stasis";
        this.owner = owner;
        this.card = card;
        amount = -1;
        updateDescription();
        loadRegion("stasis");
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(powerStrings.DESCRIPTIONS[0]).append(FontHelper.colorString(card.name, "y")).append(powerStrings.DESCRIPTIONS[1]).toString();
    }

    public void onDeath()
    {
        if(AbstractDungeon.player.hand.size() != 10)
            addToBot(new MakeTempCardInHandAction(card, false, true));
        else
            addToBot(new MakeTempCardInDiscardAction(card, true));
    }

    public static final String POWER_ID = "Stasis";
    private static final PowerStrings powerStrings;
    private AbstractCard card;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Stasis");
    }
}
