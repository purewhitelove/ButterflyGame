// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BirdFacedUrn.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class BirdFacedUrn extends AbstractRelic
{

    public BirdFacedUrn()
    {
        super("Bird Faced Urn", "bird_urn.png", AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.SOLID);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(2).append(DESCRIPTIONS[1]).toString();
    }

    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if(card.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.POWER)
        {
            flash();
            addToTop(new HealAction(AbstractDungeon.player, AbstractDungeon.player, 2));
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }

    public AbstractRelic makeCopy()
    {
        return new BirdFacedUrn();
    }

    public static final String ID = "Bird Faced Urn";
    private static final int HEAL_AMT = 2;
}
