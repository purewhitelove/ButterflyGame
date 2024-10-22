// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WingBoots.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class WingBoots extends AbstractRelic
{

    public WingBoots()
    {
        super("WingedGreaves", "winged.png", AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.FLAT);
        counter = 3;
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void setCounter(int setCounter)
    {
        counter = setCounter;
        if(counter == -2)
        {
            usedUp();
            counter = -2;
        }
    }

    public boolean canSpawn()
    {
        return Settings.isEndless || AbstractDungeon.floorNum <= 40;
    }

    public AbstractRelic makeCopy()
    {
        return new WingBoots();
    }

    public static final String ID = "WingedGreaves";
}
