// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NinjaScroll.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class NinjaScroll extends AbstractRelic
{

    public NinjaScroll()
    {
        super("Ninja Scroll", "ninjaScroll.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.FLAT);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(3).append(DESCRIPTIONS[1]).toString();
    }

    public void atBattleStartPreDraw()
    {
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new MakeTempCardInHandAction(new Shiv(), 3, false));
    }

    public AbstractRelic makeCopy()
    {
        return new NinjaScroll();
    }

    public static final String ID = "Ninja Scroll";
    private static final int AMOUNT = 3;
}
