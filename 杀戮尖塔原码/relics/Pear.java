// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Pear.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class Pear extends AbstractRelic
{

    public Pear()
    {
        super("Pear", "pear.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.FLAT);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(10).append(LocalizedStrings.PERIOD).toString();
    }

    public void onEquip()
    {
        AbstractDungeon.player.increaseMaxHp(10, true);
    }

    public AbstractRelic makeCopy()
    {
        return new Pear();
    }

    public static final String ID = "Pear";
    private static final int HP_AMT = 10;
}
