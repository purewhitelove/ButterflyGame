// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PenNib.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.PenNibPower;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class PenNib extends AbstractRelic
{

    public PenNib()
    {
        super("Pen Nib", "penNib.png", AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.CLINK);
        counter = 0;
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if(card.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK)
        {
            counter++;
            if(counter == 10)
            {
                counter = 0;
                flash();
                pulse = false;
            } else
            if(counter == 9)
            {
                beginPulse();
                pulse = true;
                AbstractDungeon.player.hand.refreshHandLayout();
                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PenNibPower(AbstractDungeon.player, 1), 1, true));
            }
        }
    }

    public void atBattleStart()
    {
        if(counter == 9)
        {
            beginPulse();
            pulse = true;
            AbstractDungeon.player.hand.refreshHandLayout();
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PenNibPower(AbstractDungeon.player, 1), 1, true));
        }
    }

    public AbstractRelic makeCopy()
    {
        return new PenNib();
    }

    public static final String ID = "Pen Nib";
    public static final int COUNT = 10;
}
