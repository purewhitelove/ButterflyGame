// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MawBank.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.ShopRoom;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class MawBank extends AbstractRelic
{

    public MawBank()
    {
        super("MawBank", "bank.png", AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.CLINK);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(12).append(DESCRIPTIONS[1]).toString();
    }

    public void onEnterRoom(AbstractRoom room)
    {
        if(!usedUp)
        {
            flash();
            AbstractDungeon.player.gainGold(12);
        }
    }

    public void onSpendGold()
    {
        if(!usedUp)
        {
            flash();
            setCounter(-2);
        }
    }

    public void setCounter(int setCounter)
    {
        counter = setCounter;
        if(setCounter == -2)
        {
            usedUp();
            counter = -2;
        }
    }

    public boolean canSpawn()
    {
        return (Settings.isEndless || AbstractDungeon.floorNum <= 48) && !(AbstractDungeon.getCurrRoom() instanceof ShopRoom);
    }

    public AbstractRelic makeCopy()
    {
        return new MawBank();
    }

    public static final String ID = "MawBank";
    private static final int GOLD_AMT = 12;
}
