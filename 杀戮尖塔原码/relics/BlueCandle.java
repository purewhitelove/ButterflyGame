// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BlueCandle.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class BlueCandle extends AbstractRelic
{

    public BlueCandle()
    {
        super("Blue Candle", "blueCandle.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.MAGICAL);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(1).append(DESCRIPTIONS[1]).toString();
    }

    public AbstractRelic makeCopy()
    {
        return new BlueCandle();
    }

    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if(card.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.CURSE)
        {
            AbstractDungeon.player.getRelic("Blue Candle").flash();
            addToBot(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, 1, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE));
            card.exhaust = true;
            action.exhaustCard = true;
        }
    }

    public static final String ID = "Blue Candle";
    public static final int HP_LOSS = 1;
}
