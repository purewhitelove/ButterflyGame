// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Mango.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class Mango extends AbstractRelic
{

    public Mango()
    {
        super("Mango", "mango.png", AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.FLAT);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(14).append(LocalizedStrings.PERIOD).toString();
    }

    public void onEquip()
    {
        AbstractDungeon.player.increaseMaxHp(14, true);
    }

    public AbstractRelic makeCopy()
    {
        return new Mango();
    }

    public static final String ID = "Mango";
    private static final int HP_AMT = 14;
}
