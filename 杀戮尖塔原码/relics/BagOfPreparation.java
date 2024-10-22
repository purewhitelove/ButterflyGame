// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BagOfPreparation.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class BagOfPreparation extends AbstractRelic
{

    public BagOfPreparation()
    {
        super("Bag of Preparation", "bag_of_prep.png", AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.FLAT);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(2).append(DESCRIPTIONS[1]).toString();
    }

    public void atBattleStart()
    {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new DrawCardAction(AbstractDungeon.player, 2));
    }

    public AbstractRelic makeCopy()
    {
        return new BagOfPreparation();
    }

    public static final String ID = "Bag of Preparation";
    private static final int NUM_CARDS = 2;
}
