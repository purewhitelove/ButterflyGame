// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StrikeDummy.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class StrikeDummy extends AbstractRelic
{

    public StrikeDummy()
    {
        super("StrikeDummy", "dummy.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.HEAVY);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(3).append(DESCRIPTIONS[1]).toString();
    }

    public float atDamageModify(float damage, AbstractCard c)
    {
        if(c.hasTag(com.megacrit.cardcrawl.cards.AbstractCard.CardTags.STRIKE))
            return damage + 3F;
        else
            return damage;
    }

    public AbstractRelic makeCopy()
    {
        return new StrikeDummy();
    }

    public static final String ID = "StrikeDummy";
}
