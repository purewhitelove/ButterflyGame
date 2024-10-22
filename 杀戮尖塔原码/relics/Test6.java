// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Test6.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class Test6 extends AbstractRelic
{

    public Test6()
    {
        super("Test 6", "test6.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.FLAT);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(3).append(DESCRIPTIONS[1]).append(100).append(DESCRIPTIONS[2]).toString();
    }

    public void onPlayerEndTurn()
    {
        if(hasEnoughGold())
        {
            flash();
            pulse = false;
            addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 3 * (AbstractDungeon.player.gold / 100)));
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }

    public void atTurnStart()
    {
        if(hasEnoughGold())
        {
            pulse = true;
            beginPulse();
        }
    }

    public void onVictory()
    {
        pulse = false;
    }

    private boolean hasEnoughGold()
    {
        return AbstractDungeon.player.gold >= 100;
    }

    public AbstractRelic makeCopy()
    {
        return new Test6();
    }

    public static final String ID = "Test 6";
    private static final int GOLD_REQ = 100;
    private static final int BLOCK_AMT = 3;
}
