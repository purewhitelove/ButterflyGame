// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DiscerningMonocle.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.ShopRoom;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class DiscerningMonocle extends AbstractRelic
{

    public DiscerningMonocle()
    {
        super("Discerning Monocle", "monocle.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.CLINK);
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

    public AbstractRelic makeCopy()
    {
        return new DiscerningMonocle();
    }

    public static final String ID = "Discerning Monocle";
    public static final float MULTIPLIER = 0.8F;
}
