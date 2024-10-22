// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TinyChest.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class TinyChest extends AbstractRelic
{

    public TinyChest()
    {
        super("Tiny Chest", "tinyChest.png", AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.SOLID);
        counter = -1;
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(4).append(DESCRIPTIONS[1]).toString();
    }

    public void onEquip()
    {
        counter = 0;
    }

    public boolean canSpawn()
    {
        return Settings.isEndless || AbstractDungeon.floorNum <= 35;
    }

    public AbstractRelic makeCopy()
    {
        return new TinyChest();
    }

    public static final String ID = "Tiny Chest";
    public static final int ROOM_COUNT = 4;
}
