// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Courier.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.ShopRoom;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class Courier extends AbstractRelic
{

    public Courier()
    {
        super("The Courier", "courier.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.FLAT);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
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
        return new Courier();
    }

    public static final String ID = "The Courier";
    public static final float MULTIPLIER = 0.8F;
}
