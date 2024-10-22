// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RegalPillow.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class RegalPillow extends AbstractRelic
{

    public RegalPillow()
    {
        super("Regal Pillow", "regal_pillow.png", AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.MAGICAL);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(15).append(DESCRIPTIONS[1]).toString();
    }

    public AbstractRelic makeCopy()
    {
        return new RegalPillow();
    }

    public boolean canSpawn()
    {
        return Settings.isEndless || AbstractDungeon.floorNum <= 48;
    }

    public static final String ID = "Regal Pillow";
    public static final int HEAL_AMT = 15;
}
