// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ThreadAndNeedle.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class ThreadAndNeedle extends AbstractRelic
{

    public ThreadAndNeedle()
    {
        super("Thread and Needle", "threadAndNeedle.png", AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.CLINK);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(4).append(DESCRIPTIONS[1]).toString();
    }

    public void atBattleStart()
    {
        flash();
        addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PlatedArmorPower(AbstractDungeon.player, 4), 4));
        addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    public AbstractRelic makeCopy()
    {
        return new ThreadAndNeedle();
    }

    public static final String ID = "Thread and Needle";
    private static final int ARMOR_AMT = 4;
}
