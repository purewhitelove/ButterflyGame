// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HandDrill.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.VulnerablePower;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class HandDrill extends AbstractRelic
{

    public HandDrill()
    {
        super("HandDrill", "drill.png", AbstractRelic.RelicTier.SHOP, AbstractRelic.LandingSound.FLAT);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void onBlockBroken(AbstractCreature m)
    {
        flash();
        addToBot(new RelicAboveCreatureAction(m, this));
        addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new VulnerablePower(m, 2, false), 2));
    }

    public AbstractRelic makeCopy()
    {
        return new HandDrill();
    }

    public static final String ID = "HandDrill";
    public static final int VULNERABLE_AMT = 2;
}
