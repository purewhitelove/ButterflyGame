// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SmilingMask.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.ShopRoom;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class SmilingMask extends AbstractRelic
{

    public SmilingMask()
    {
        super("Smiling Mask", "merchantMask.png", AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.FLAT);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(50).append(DESCRIPTIONS[1]).toString();
    }

    public void onEnterRoom(AbstractRoom room)
    {
        if(room instanceof ShopRoom)
        {
            flash();
            pulse = true;
        } else
        {
            pulse = false;
        }
    }

    public boolean canSpawn()
    {
        return (Settings.isEndless || AbstractDungeon.floorNum <= 48) && !(AbstractDungeon.getCurrRoom() instanceof ShopRoom);
    }

    public AbstractRelic makeCopy()
    {
        return new SmilingMask();
    }

    public static final String ID = "Smiling Mask";
    public static final int COST = 50;
}
