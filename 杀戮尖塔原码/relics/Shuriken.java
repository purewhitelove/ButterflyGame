// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Shuriken.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class Shuriken extends AbstractRelic
{

    public Shuriken()
    {
        super("Shuriken", "shuriken.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.CLINK);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(3).append(DESCRIPTIONS[1]).append(1).append(DESCRIPTIONS[2]).toString();
    }

    public void atTurnStart()
    {
        counter = 0;
    }

    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if(card.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK)
        {
            counter++;
            if(counter % 3 == 0)
            {
                counter = 0;
                flash();
                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 1), 1));
            }
        }
    }

    public void onVictory()
    {
        counter = -1;
    }

    public AbstractRelic makeCopy()
    {
        return new Shuriken();
    }

    public static final String ID = "Shuriken";
    private static final int STR_AMT = 1;
    private static final int NUM_CARDS = 3;
}
