// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GamblingChip.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.GamblingChipAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class GamblingChip extends AbstractRelic
{

    public GamblingChip()
    {
        super("Gambling Chip", "gamblingChip.png", AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.FLAT);
        activated = false;
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void atBattleStartPreDraw()
    {
        activated = false;
    }

    public void atTurnStartPostDraw()
    {
        if(!activated)
        {
            activated = true;
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new GamblingChipAction(AbstractDungeon.player));
        }
    }

    public AbstractRelic makeCopy()
    {
        return new GamblingChip();
    }

    public static final String ID = "Gambling Chip";
    private boolean activated;
}
