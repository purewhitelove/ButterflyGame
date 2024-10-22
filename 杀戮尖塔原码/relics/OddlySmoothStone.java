// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OddlySmoothStone.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class OddlySmoothStone extends AbstractRelic
{

    public OddlySmoothStone()
    {
        super("Oddly Smooth Stone", "smooth_stone.png", AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.SOLID);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(1).append(DESCRIPTIONS[1]).toString();
    }

    public void atBattleStart()
    {
        flash();
        addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, 1), 1));
        addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    public AbstractRelic makeCopy()
    {
        return new OddlySmoothStone();
    }

    public static final String ID = "Oddly Smooth Stone";
    private static final int CON_AMT = 1;
}
