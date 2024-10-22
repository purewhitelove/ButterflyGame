// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MembershipCard.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.ShopRoom;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class MembershipCard extends AbstractRelic
{

    public MembershipCard()
    {
        super("Membership Card", "membershipCard.png", AbstractRelic.RelicTier.SHOP, AbstractRelic.LandingSound.MAGICAL);
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
        return new MembershipCard();
    }

    public static final String ID = "Membership Card";
    public static final float MULTIPLIER = 0.5F;
}
