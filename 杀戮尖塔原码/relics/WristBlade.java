// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WristBlade.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class WristBlade extends AbstractRelic
{

    public WristBlade()
    {
        super("WristBlade", "wBlade.png", AbstractRelic.RelicTier.BOSS, AbstractRelic.LandingSound.FLAT);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy()
    {
        return new WristBlade();
    }

    public float atDamageModify(float damage, AbstractCard c)
    {
        if(c.costForTurn == 0 || c.freeToPlayOnce && c.cost != -1)
            return damage + 4F;
        else
            return damage;
    }

    public static final String ID = "WristBlade";
}
