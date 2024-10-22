// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Nunchaku.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class Nunchaku extends AbstractRelic
{

    public Nunchaku()
    {
        super("Nunchaku", "nunchaku.png", AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.FLAT);
        counter = 0;
    }

    public String getUpdatedDescription()
    {
        if(AbstractDungeon.player != null)
            return setDescription(AbstractDungeon.player.chosenClass);
        else
            return setDescription(null);
    }

    private String setDescription(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass c)
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(10).append(DESCRIPTIONS[1]).toString();
    }

    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if(card.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK)
        {
            counter++;
            if(counter % 10 == 0)
            {
                counter = 0;
                flash();
                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                addToBot(new GainEnergyAction(1));
            }
        }
    }

    public AbstractRelic makeCopy()
    {
        return new Nunchaku();
    }

    public static final String ID = "Nunchaku";
    private static final int NUM_CARDS = 10;
}
