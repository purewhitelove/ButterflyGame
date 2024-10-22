// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrnamentalFan.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class OrnamentalFan extends AbstractRelic
{

    public OrnamentalFan()
    {
        super("Ornamental Fan", "ornamentalFan.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.FLAT);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(4).append(DESCRIPTIONS[1]).toString();
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
                flash();
                counter = 0;
                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 4));
            }
        }
    }

    public void onVictory()
    {
        counter = -1;
    }

    public AbstractRelic makeCopy()
    {
        return new OrnamentalFan();
    }

    public static final String ID = "Ornamental Fan";
    private static final int BLOCK = 4;
}
