// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NlothsMask.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class NlothsMask extends AbstractRelic
{

    public NlothsMask()
    {
        super("NlothsMask", "nloth.png", AbstractRelic.RelicTier.SPECIAL, AbstractRelic.LandingSound.FLAT);
        counter = 1;
    }

    public void onChestOpenAfter(boolean bossChest)
    {
        if(!bossChest && counter > 0)
        {
            counter--;
            flash();
            AbstractDungeon.getCurrRoom().removeOneRelicFromRewards();
            if(counter == 0)
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

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy()
    {
        return new NlothsMask();
    }

    public static final String ID = "NlothsMask";
}
