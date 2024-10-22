// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   InkBottle.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class InkBottle extends AbstractRelic
{

    public InkBottle()
    {
        super("InkBottle", "ink_bottle.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.CLINK);
        counter = 0;
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        counter++;
        if(counter == 10)
        {
            counter = 0;
            flash();
            pulse = false;
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new DrawCardAction(1));
        } else
        if(counter == 9)
        {
            beginPulse();
            pulse = true;
        }
    }

    public void atBattleStart()
    {
        if(counter == 9)
        {
            beginPulse();
            pulse = true;
        }
    }

    public AbstractRelic makeCopy()
    {
        return new InkBottle();
    }

    public static final String ID = "InkBottle";
    private static final int COUNT = 10;
}
