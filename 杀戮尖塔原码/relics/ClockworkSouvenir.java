// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ClockworkSouvenir.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ArtifactPower;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class ClockworkSouvenir extends AbstractRelic
{

    public ClockworkSouvenir()
    {
        super("ClockworkSouvenir", "clockwork.png", AbstractRelic.RelicTier.SHOP, AbstractRelic.LandingSound.HEAVY);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void atBattleStart()
    {
        flash();
        addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ArtifactPower(AbstractDungeon.player, 1), 1));
    }

    public AbstractRelic makeCopy()
    {
        return new ClockworkSouvenir();
    }

    public static final String ID = "ClockworkSouvenir";
}
