// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Melange.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class Melange extends AbstractRelic
{

    public Melange()
    {
        super("Melange", "melange.png", AbstractRelic.RelicTier.SHOP, AbstractRelic.LandingSound.MAGICAL);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(3).append(DESCRIPTIONS[1]).toString();
    }

    public void onShuffle()
    {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new ScryAction(3));
    }

    public AbstractRelic makeCopy()
    {
        return new Melange();
    }

    public static final String ID = "Melange";
}
