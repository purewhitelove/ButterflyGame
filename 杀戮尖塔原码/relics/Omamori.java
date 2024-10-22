// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Omamori.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class Omamori extends AbstractRelic
{

    public Omamori()
    {
        super("Omamori", "omamori.png", AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.FLAT);
        counter = 2;
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void setCounter(int setCounter)
    {
        counter = setCounter;
        if(setCounter == 0)
            usedUp();
        else
        if(setCounter == 1)
            description = DESCRIPTIONS[1];
    }

    public void use()
    {
        flash();
        counter--;
        if(counter == 0)
            setCounter(0);
        else
            description = DESCRIPTIONS[1];
    }

    public boolean canSpawn()
    {
        return Settings.isEndless || AbstractDungeon.floorNum <= 48;
    }

    public AbstractRelic makeCopy()
    {
        return new Omamori();
    }

    public static final String ID = "Omamori";
}
